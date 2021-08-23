package com.jshy.mr.common;

import com.jshy.mr.utils.CommonUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.Seekable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.compress.*;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.CompressedSplitLineReader;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.lib.input.SplitLineReader;
import org.apache.hadoop.mapreduce.lib.input.UncompressedSplitLineReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: 简德群
 * Date: 2019/10/11
 * Time: 14:21
 * To change this template use File | Settings | File Templates.
 */
public class LogLineRecordReader extends RecordReader<LongWritable, FlumeLogWritable> {
    private static final Logger LOG = LoggerFactory.getLogger(LogLineRecordReader.class);
    public static final String MAX_LINE_LENGTH = "mapreduce.input.linerecordreader.line.maxlength";
    private long start;
    private long pos;
    private long end;
    private SplitLineReader in;
    private FSDataInputStream fileIn;
    private Seekable filePosition;
    private int maxLineLength;
    private LongWritable key;
    private Text tempValue;
    private FlumeLogWritable value;
    private boolean isCompressedInput;
    private Decompressor decompressor;
    private byte[] recordDelimiterBytes;

    public LogLineRecordReader() {
    }

    public LogLineRecordReader(byte[] recordDelimiter) {
        this.recordDelimiterBytes = recordDelimiter;
    }

    public void initialize(InputSplit genericSplit, TaskAttemptContext context) throws IOException {
        FileSplit split = (FileSplit)genericSplit;
        Configuration job = context.getConfiguration();
        this.maxLineLength = job.getInt("mapreduce.input.linerecordreader.line.maxlength", 2147483647);
        this.start = split.getStart();
        this.end = this.start + split.getLength();
        Path file = split.getPath();
        FileSystem fs = file.getFileSystem(job);
        this.fileIn = fs.open(file);
        CompressionCodec codec = (new CompressionCodecFactory(job)).getCodec(file);
        if(null != codec) {
            this.isCompressedInput = true;
            this.decompressor = CodecPool.getDecompressor(codec);
            if(codec instanceof SplittableCompressionCodec) {
                SplitCompressionInputStream cIn = ((SplittableCompressionCodec)codec).createInputStream(this.fileIn, this.decompressor, this.start, this.end, SplittableCompressionCodec.READ_MODE.BYBLOCK);
                this.in = new CompressedSplitLineReader(cIn, job, this.recordDelimiterBytes);
                this.start = cIn.getAdjustedStart();
                this.end = cIn.getAdjustedEnd();
                this.filePosition = cIn;
            } else {
                if(this.start != 0L) {
                    throw new IOException("Cannot seek in " + codec.getClass().getSimpleName() + " compressed stream");
                }

                this.in = new SplitLineReader(codec.createInputStream(this.fileIn, this.decompressor), job, this.recordDelimiterBytes);
                this.filePosition = this.fileIn;
            }
        } else {
            this.fileIn.seek(this.start);
            this.in = new UncompressedSplitLineReader(this.fileIn, job, this.recordDelimiterBytes, split.getLength());
            this.filePosition = this.fileIn;
        }

        if(this.start != 0L) {
            this.start += (long)this.in.readLine(new Text(), 0, this.maxBytesToConsume(this.start));
        }

        this.pos = this.start;
    }

    private int maxBytesToConsume(long pos) {
        return this.isCompressedInput?2147483647:(int)Math.max(Math.min(2147483647L, this.end - pos), (long)this.maxLineLength);
    }

    private long getFilePosition() throws IOException {
        long retVal;
        if(this.isCompressedInput && null != this.filePosition) {
            retVal = this.filePosition.getPos();
        } else {
            retVal = this.pos;
        }

        return retVal;
    }

    private int skipUtfByteOrderMark() throws IOException {
        int newMaxLineLength = (int)Math.min(3L + (long)this.maxLineLength, 2147483647L);
        int newSize = this.in.readLine(this.tempValue, newMaxLineLength, this.maxBytesToConsume(this.pos));
        this.pos += (long)newSize;
        int textLength = this.tempValue.getLength();
        byte[] textBytes = this.tempValue.getBytes();
        if(textLength >= 3 && textBytes[0] == -17 && textBytes[1] == -69 && textBytes[2] == -65) {
            LOG.info("Found UTF-8 BOM and skipped it");
            textLength -= 3;
            newSize -= 3;
            if(textLength > 0) {
                textBytes = this.tempValue.copyBytes();
                this.tempValue.set(textBytes, 3, textLength);
            } else {
                this.tempValue.clear();
            }
        }

        return newSize;
    }

    public boolean nextKeyValue() throws IOException {
        if(this.key == null) {
            this.key = new LongWritable();
        }

        this.key.set(this.pos);
        if(this.tempValue == null) {
            this.tempValue = new Text();
            this.value = new FlumeLogWritable();
        }

        int newSize = 0;

        while(this.getFilePosition() <= this.end || this.in.needAdditionalRecordAfterSplit()) {
            if(this.pos == 0L) {
                newSize = this.skipUtfByteOrderMark();
            } else {
                newSize = this.in.readLine(this.tempValue, this.maxLineLength, this.maxBytesToConsume(this.pos));
                this.pos += (long)newSize;
            }

            if(newSize == 0 || newSize < this.maxLineLength) {
                break;
            }

            LOG.info("Skipped line of size " + newSize + " at pos " + (this.pos - (long)newSize));
        }

        if(newSize == 0) {
            this.key = null;
            this.value = null;
            this.tempValue = null;
            return false;
        } else {
            return true;
        }
    }

    public LongWritable getCurrentKey() {
        return this.key;
    }

    public FlumeLogWritable getCurrentValue() {
        if(tempValue != null && StringUtils.isNotEmpty(tempValue.toString())){
            this.value = CommonUtils.getFlumeLogWritable(tempValue.toString(),value);
        }
        return this.value;
    }

    public float getProgress() throws IOException {
        return this.start == this.end?0.0F:Math.min(1.0F, (float)(this.getFilePosition() - this.start) / (float)(this.end - this.start));
    }

    public synchronized void close() throws IOException {
        try {
            if(this.in != null) {
                this.in.close();
            }
        } finally {
            if(this.decompressor != null) {
                CodecPool.returnDecompressor(this.decompressor);
                this.decompressor = null;
            }

        }

    }
}
