package com.jshy.mr.report.user_stay_duration;

import com.jshy.mr.PpfunsMain;
import com.jshy.mr.common.CombineLogInputFormat;
import com.jshy.mr.model.ConfigEntity;
import com.jshy.mr.model.StatisticalMethodEnum;
import com.jshy.mr.mysqlOutPut.DBFixConfiguration;
import com.jshy.mr.mysqlOutPut.DBFixOutPutFormat;
import com.jshy.mr.mysqlOutPut.SqlExecModelEnum;
import com.jshy.mr.utils.CommonUtils;
import com.jshy.mr.utils.DateUtils;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.db.DBOutputFormat;
import org.apache.hadoop.mapreduce.lib.jobcontrol.ControlledJob;
import org.apache.hadoop.mapreduce.lib.jobcontrol.JobControl;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 按时间段统计历史播放记录日志文件
 */
public class UserStayDurationApp  extends Configured implements Tool {
    static final String OUT_PATH = "user_stay_duration";
    private String tableName;

    public static void main(String[] args) throws Exception {
        PpfunsMain.initConfig(args); //初始化配置
        String[] args1 = PpfunsMain.initConfig(args);   //初始化配置参数
        int exitCode = ToolRunner.run(new UserStayDurationApp(), args1);
        System.exit(exitCode);
    }
    @Override
    public int run(String[] strings) throws Exception {
        exce(PpfunsMain.configEntity);
        return 1;
    }
    public void exce(ConfigEntity configEntity) {
        try{
            String tableFix = StatisticalMethodEnum.get(CommonUtils.statisticalMethod(configEntity.getConfiguration())).getName();
            Path outPath = new Path(configEntity.getHadoop().getOutput()+ "/"+OUT_PATH.concat("/").concat(tableFix).concat("/").concat(LocalDate.now().format(DateUtils.DATE_FORMATTER_SLANTINGBAR)));
            tableName =OUT_PATH.concat("_").concat(tableFix);
            configEntity.getLogTime().delFilePaths(outPath);
            configEntity.getLogTime().validateInputPath();
            //建立job1和job2的从属关系
            String jobName = this.getClass().getSimpleName().concat(LocalDateTime.now().format(DateUtils.TIME_FORMATTER_NO_SIGN));
            ControlledJob controlledJob1 = addJob1(jobName.concat("_1"),configEntity,outPath);
            JobControl jc = new JobControl("统计用户留存时间长");
            jc.addJob(controlledJob1);
//            jc.addJob(controlledJob3);
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
        job.setMapperClass(UserStayDurationMapper.class);
        job.setCombinerClass(UserStayDurationReducer.class);
        job.setReducerClass(UserStayDuration2Reducer.class);
        job.setMapOutputKeyClass(UserStayDurationWritable.class);
        job.setMapOutputValueClass(LongWritable.class);
        job.setOutputKeyClass(UserStayDurationWritable.class);
        job.setOutputValueClass(NullWritable.class);
        job.setInputFormatClass(CombineLogInputFormat.class);
        CombineLogInputFormat.setInputDirRecursive(job,true);
        CombineLogInputFormat.setMaxInputSplitSize(job, configEntity.getHadoop().getMaxInputSplitSize());
        CombineLogInputFormat.setMinInputSplitSize(job, configEntity.getHadoop().getMinInputSplitSize());
        CombineLogInputFormat.setInputPaths(job,configEntity.getLogTime().inputPath());
        job.setOutputFormatClass(DBOutputFormat.class);
//        if(StringUtils.isNotEmpty(configEntity.getDb().getDriverPath())){
//            Path path = new Path(configEntity.getDb().getDriverPath());
//            if(configEntity.getFileSystem().exists(path)){
//                System.out.println("配置了mysql驱动路径："+configEntity.getDb().getDriverPath());
//                job.addFileToClassPath(path);
//                DistributedCache.addFileToClassPath(path,getConf());
//            }
//        }
        DBFixConfiguration.configureDB(job.getConfiguration(),configEntity.getDb().getDriver(),configEntity.getDb().getHost(),
                configEntity.getDb().getUsername(),configEntity.getDb().getPassword());
        List<String> fieldNames = CommonUtils.getSqlDateField(configEntity.getConfiguration());
        fieldNames.addAll(CommonUtils.getUserTypeField());
        fieldNames.addAll(CommonUtils.getSqlUserField());
        fieldNames.add("stay_duration");
        String[] fields = new String[fieldNames.size()];
        fieldNames.toArray(fields);
        DBFixOutPutFormat.setOutput(job,tableName,fields, SqlExecModelEnum.REPLACE);
        ControlledJob controlledJob = new ControlledJob(job.getConfiguration());
        controlledJob.setJob(job);
        return controlledJob;
    }

}