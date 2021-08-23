package com.jshy.mr.common;

import com.google.common.base.Charsets;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.io.compress.CompressionCodecFactory;
import org.apache.hadoop.io.compress.SplittableCompressionCodec;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: 简德群
 * Date: 2019/10/11
 * Time: 14:19
 * To change this template use File | Settings | File Templates.
 */
public class LogInputFormat extends FileInputFormat<LongWritable, FlumeLogWritable>  {
    public LogInputFormat(){}
    @Override
    public RecordReader<LongWritable, FlumeLogWritable> createRecordReader(InputSplit inputSplit, TaskAttemptContext taskAttemptContext) throws IOException, InterruptedException {
        String delimiter = taskAttemptContext.getConfiguration().get("textinputformat.record.delimiter");
        byte[] recordDelimiterBytes = null;
        if(null != delimiter) {
            recordDelimiterBytes = delimiter.getBytes(Charsets.UTF_8);
        }

        return new LogLineRecordReader(recordDelimiterBytes);
    }

    protected boolean isSplitable(JobContext context, Path file) {
        CompressionCodec codec = (new CompressionCodecFactory(context.getConfiguration())).getCodec(file);
        return null == codec?true:codec instanceof SplittableCompressionCodec;
    }
}
