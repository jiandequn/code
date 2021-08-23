package com.jshy.mr.common;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.CombineFileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.CombineFileRecordReader;
import org.apache.hadoop.mapreduce.lib.input.CombineFileRecordReaderWrapper;
import org.apache.hadoop.mapreduce.lib.input.CombineFileSplit;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: 简德群
 * Date: 2019/10/21
 * Time: 16:30
 * To change this template use File | Settings | File Templates.
 */
public class CombineLogInputFormat extends CombineFileInputFormat<LongWritable, FlumeLogWritable> {
    public CombineLogInputFormat(){}
    public RecordReader<LongWritable, FlumeLogWritable> createRecordReader(InputSplit split, TaskAttemptContext context) throws IOException {
        return new CombineFileRecordReader((CombineFileSplit)split, context, CombineLogInputFormat.LogRecordReaderWrapper.class);
    }

    private static class LogRecordReaderWrapper extends CombineFileRecordReaderWrapper<LongWritable, FlumeLogWritable> {
        public LogRecordReaderWrapper(CombineFileSplit split, TaskAttemptContext context, Integer idx) throws IOException, InterruptedException {
            super(new LogInputFormat(), split, context, idx);
        }
    }
}
