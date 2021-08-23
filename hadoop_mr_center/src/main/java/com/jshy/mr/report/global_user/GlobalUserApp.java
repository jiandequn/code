package com.jshy.mr.report.global_user;

import com.jshy.mr.PpfunsMain;
import com.jshy.mr.common.CombineLogInputFormat;
import com.jshy.mr.model.ConfigEntity;
import com.jshy.mr.model.StatisticalMethodEnum;
import com.jshy.mr.mysqlOutPut.DBFixConfiguration;
import com.jshy.mr.mysqlOutPut.DBFixOutPutFormat;
import com.jshy.mr.utils.CommonUtils;
import com.jshy.mr.utils.DateUtils;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.jobcontrol.ControlledJob;
import org.apache.hadoop.mapreduce.lib.jobcontrol.JobControl;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 按时间段统计历史播放记录日志文件
 */
public class GlobalUserApp extends Configured implements Tool {
    static final String OUT_PATH = "global_user";
    private String tableName = OUT_PATH;
    public static void main(String[] args) throws Exception {
        PpfunsMain.initConfig(args); //初始化配置
        String[] args1 = PpfunsMain.initConfig(args);   //初始化配置参数
        int exitCode = ToolRunner.run(new GlobalUserApp(), args1);
        System.exit(exitCode);
    }
    @Override
    public int run(String[] strings) throws Exception {
        exce(PpfunsMain.configEntity);
        return 1;
    }
    public void test(ConfigEntity configEntity) throws Exception {
        String tableFix = StatisticalMethodEnum.get(CommonUtils.statisticalMethod(configEntity.getConfiguration())).getName();
        Path outPath = new Path(configEntity.getHadoop().getOutput()+ "/"+OUT_PATH.concat("/").concat("/").concat(LocalDate.now().format(DateUtils.DATE_FORMATTER_SLANTINGBAR)));
        tableName =OUT_PATH.concat("_").concat(tableFix);
        configEntity.getLogTime().validateInputPath();
        //建立job1和job2的从属关系
        String jobName = this.getClass().getSimpleName().concat(LocalDateTime.now().format(DateUtils.TIME_FORMATTER_NO_SIGN));
        ControlledJob controlledJob3 = addMysqlJob(jobName.concat("_mysql"),configEntity,outPath);
        JobControl jc = new JobControl("统计专区用户数");
        jc.addJob(controlledJob3);
        Thread jobThread = new Thread(jc);
        jobThread.start();
        // 当jc没有执行完成时，线程等待
        while(!jc.allFinished()){
            Thread.sleep(500);
        }
        jc.stop();
    }
    public void exce(ConfigEntity configEntity) {
        try{
            String tableFix = StatisticalMethodEnum.get(CommonUtils.statisticalMethod(configEntity.getConfiguration())).getName();
            Path outPath = new Path(configEntity.getHadoop().getOutput()+ "/"+OUT_PATH.concat("/").concat("/").concat(LocalDate.now().format(DateUtils.DATE_FORMATTER_SLANTINGBAR)));
            tableName =OUT_PATH.concat("_").concat(tableFix);
            configEntity.getLogTime().delFilePaths(outPath);
            configEntity.getLogTime().validateInputPath();
            //建立job1和job2的从属关系
            String jobName = this.getClass().getSimpleName().concat(LocalDateTime.now().format(DateUtils.TIME_FORMATTER_NO_SIGN));
            ControlledJob controlledJob1 = addJob1(jobName.concat("_1"),configEntity,outPath);
            ControlledJob controlledJob2= addMysqlJob(jobName.concat("_mysql"),configEntity,outPath);
            controlledJob2.addDependingJob(controlledJob1);
            JobControl jc = new JobControl("统计专区用户数");
            jc.addJob(controlledJob1);
            jc.addJob(controlledJob2);
            Thread jobThread = new Thread(jc);
            jobThread.start();
            // 当jc没有执行完成时，线程等待
            while(!jc.allFinished()){
                Thread.sleep(500);
            }
            jc.stop();
            System.out.println("执行完成"+this.getClass());
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public ControlledJob addJob1(String jobName,ConfigEntity configEntity,Path output) throws Exception {
        Job job = Job.getInstance(configEntity.getConfiguration(),jobName);
        job.setJarByClass(this.getClass());
        job.setMapperClass(GlobalUserMapper.class);
        job.setCombinerClass(GlobalUserReducer.class);
        job.setReducerClass(GlobalUserReducer.class);
        job.setMapOutputKeyClass(GlobalUserWritable.class);
        job.setMapOutputValueClass(NullWritable.class);
        job.setOutputKeyClass(GlobalUserWritable.class);
        job.setOutputValueClass(NullWritable.class);
        job.setInputFormatClass(CombineLogInputFormat.class);
        CombineLogInputFormat.setInputDirRecursive(job,true);
        CombineLogInputFormat.setMaxInputSplitSize(job, configEntity.getHadoop().getMaxInputSplitSize());
        CombineLogInputFormat.setMinInputSplitSize(job, configEntity.getHadoop().getMinInputSplitSize());
        CombineLogInputFormat.setInputPaths(job,configEntity.getLogTime().inputPath());
        FileOutputFormat.setOutputPath(job,output);
        ControlledJob controlledJob = new ControlledJob(job.getConfiguration());
        controlledJob.setJob(job);
        return controlledJob;
    }
    public ControlledJob addMysqlJob(String jobName, ConfigEntity configEntity, Path inputPath) throws Exception {
        //job2通过用户数
        Job job = Job.getInstance(configEntity.getConfiguration(),jobName);
        job.setJarByClass(this.getClass());
        job.setMapperClass(GlobalUser2Mapper.class);
        job.setReducerClass(GlobalUser2Reducer.class);
        job.setMapOutputKeyClass(GlobalUserWritable.class);
        job.setMapOutputValueClass(LongWritable.class);
        job.setOutputKeyClass(GlobalUserWritable.class);
        job.setOutputValueClass(NullWritable.class);
        FileInputFormat.setInputDirRecursive(job,true);
        FileInputFormat.setInputPaths(job,inputPath);
        job.setOutputFormatClass(DBFixOutPutFormat.class);
//        if(StringUtils.isNotEmpty(configEntity.getDb().getDriverPath())){
//            Path path = new Path(configEntity.getDb().getDriverPath());
//            if(configEntity.getFileSystem().exists(path)){
//                job.addFileToClassPath(path);
//                System.out.println("配置了mysql驱动路径："+configEntity.getDb().getDriverPath());
//            }
//        }
        DBFixConfiguration.configureDB(job.getConfiguration(),configEntity.getDb().getDriver(),configEntity.getDb().getHost(),
                configEntity.getDb().getUsername(),configEntity.getDb().getPassword());
        List<String> fieldNames = CommonUtils.getSqlDateField(configEntity.getConfiguration());
        fieldNames.addAll(CommonUtils.getUserTypeField());
        fieldNames.add("user_count");
        String[] fields = new String[fieldNames.size()];
        fieldNames.toArray(fields);
        DBFixOutPutFormat.setOutputAndFilter(job,tableName,fields, new String[]{"user_count"});
        ControlledJob controlledJob = new ControlledJob(job.getConfiguration());
        controlledJob.setJob(job);
        return controlledJob;
    }
}