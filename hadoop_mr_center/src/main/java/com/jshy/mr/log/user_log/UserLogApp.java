package com.jshy.mr.log.user_log;

import com.jshy.mr.PpfunsMain;
import com.jshy.mr.common.CombineLogInputFormat;
import com.jshy.mr.common.FlumeLogWritable;
import com.jshy.mr.model.ConfigEntity;
import com.jshy.mr.utils.DateUtils;
import com.jshy.mr.utils.HttpClientUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.db.DBConfiguration;
import org.apache.hadoop.mapreduce.lib.db.DBOutputFormat;
import org.apache.hadoop.mapreduce.lib.jobcontrol.ControlledJob;
import org.apache.hadoop.mapreduce.lib.jobcontrol.JobControl;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 按时间段统计历史播放记录日志文件
 */
public class UserLogApp {
    static final String OUT_PATH = "original_record/log";
    static String tableName = "original_record_log";

    public static void main(String[] args) throws Exception {
        PpfunsMain.initConfig(args); //初始化配置
        run(PpfunsMain.configEntity);
    }
    public static void run(ConfigEntity configEntity) {
        try{
            String url =configEntity.getConfiguration().get(ConfigEntity.FORMATT_API_URL).concat(tableName);
            System.out.println("调用接口:"+url);
            if(!"success".equals(HttpClientUtils.doGet(url))){
                System.out.println(UserLogApp.class+"接口初始化失败，表："+tableName);
                return;
            }
            Path tempOutPath = new Path(configEntity.getHadoop().getTempOutput()+ "/"+OUT_PATH);
            Path outPath = new Path(configEntity.getHadoop().getOutput()+ "/"+OUT_PATH.concat("/").concat(LocalDate.now().format(DateUtils.DATE_FORMATTER_SLANTINGBAR)));
            configEntity.getLogTime().delFilePaths(tempOutPath,outPath);
            configEntity.getLogTime().validateInputPath();
            //建立job1和job2的从属关系
            String jobName = UserLogApp.class.getSimpleName().concat(LocalDateTime.now().format(DateUtils.TIME_FORMATTER_NO_SIGN));
            ControlledJob controlledJob3 = addMysqlJob(jobName.concat("_mysql"),configEntity);
            JobControl jc = new JobControl("获取Log日志文件");
//            jc.addJob(controlledJob1);
//            jc.addJob(controlledJob2);
            jc.addJob(controlledJob3);
            Thread jobThread = new Thread(jc);
            jobThread.start();
            // 当jc没有执行完成时，线程等待
            while(!jc.allFinished()){
                Thread.sleep(500);
            }
            jc.stop();
            System.out.println("执行完成"+UserLogApp.class);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public static ControlledJob addMysqlJob(String jobName,ConfigEntity configEntity) throws Exception {
        //job2通过用户数
        Job job = Job.getInstance(configEntity.getConfiguration(),jobName);
        job.setJarByClass(UserLogApp.class);
        job.setMapperClass(UserLogMapper.class);
        job.setReducerClass(UserLogSqlReducer.class);
        job.setMapOutputKeyClass(FlumeLogWritable.class);
        job.setMapOutputValueClass(NullWritable.class);
        job.setOutputKeyClass(UserLogDbWritable.class);
        job.setOutputValueClass(NullWritable.class);
        job.setInputFormatClass(CombineLogInputFormat.class);
        CombineLogInputFormat.setInputDirRecursive(job,true);
        CombineLogInputFormat.setMaxInputSplitSize(job, configEntity.getHadoop().getMaxInputSplitSize());
        CombineLogInputFormat.setMinInputSplitSize(job, configEntity.getHadoop().getMinInputSplitSize());
        CombineLogInputFormat.setInputPaths(job, configEntity.getLogTime().inputPath());
        FileOutputFormat.setOutputPath(job, new Path(configEntity.getHadoop().getOutput()+"/"+OUT_PATH));
        job.setOutputFormatClass(DBOutputFormat.class);
        if(StringUtils.isNotEmpty(configEntity.getDb().getDriverPath())){
            Path path = new Path(configEntity.getDb().getDriverPath());
            if(configEntity.getFileSystem().exists(path)){
                job.addFileToClassPath(path);
            }
        }
        DBConfiguration.configureDB(job.getConfiguration(),configEntity.getDb().getDriver(),configEntity.getDb().getHost(),
                configEntity.getDb().getUsername(),configEntity.getDb().getPassword());
        String[] fieldName = {"events_type", "mac", "sn", "user_id",
                "user_type", "parent_column_id",
                "column_id", "content_id", "content_type",
                "operate_type", "time_position", "create_time",
                "now_spm", "after_spm", "pos", "pos_name",
                "key_word", "code", "message", "product_id",
                "is_effective", "resource_id"};
        DBOutputFormat.setOutput(job,tableName,fieldName);
        ControlledJob controlledJob = new ControlledJob(job.getConfiguration());
        controlledJob.setJob(job);
        return controlledJob;
    }
}