package com.jshy.mr.report.user_info;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapred.InvalidJobConfException;
import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.mapreduce.security.TokenCache;

import java.io.IOException;

/**
 * 改方法用于屏蔽对目录存在检查的
 * Created with IntelliJ IDEA.
 * User: 简德群
 * Date: 2019/11/14
 * Time: 15:39
 * To change this template use File | Settings | File Templates.
 */
public class TextOutput2Format<K, V> extends TextOutputFormat<K, V> {
    @Override
    public void checkOutputSpecs(JobContext job) throws IOException {
        Path outDir = getOutputPath(job);
        if(outDir == null) {
            throw new InvalidJobConfException("Output directory not set.");
        } else {
            TokenCache.obtainTokensForNamenodes(job.getCredentials(), new Path[]{outDir}, job.getConfiguration());
            if(outDir.getFileSystem(job.getConfiguration()).exists(outDir)) {
                System.out.println("Output directory " + outDir + " already exists");
//                throw new FileAlreadyExistsException();
            }
        }
    }
}
