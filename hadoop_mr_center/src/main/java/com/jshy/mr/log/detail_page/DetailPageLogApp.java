package com.jshy.mr.log.detail_page;

import com.jshy.mr.PpfunsMain;
import com.jshy.mr.common.CombineLogInputFormat;
import com.jshy.mr.model.ConfigEntity;
import com.jshy.mr.utils.DateUtils;
import com.jshy.mr.utils.HttpClientUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.db.DBConfiguration;
import org.apache.hadoop.mapreduce.lib.db.DBOutputFormat;
import org.apache.hadoop.mapreduce.lib.jobcontrol.ControlledJob;
import org.apache.hadoop.mapreduce.lib.jobcontrol.JobControl;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import java.time.LocalDateTime;

/**
 * 按时间段统计历史播放记录日志文件
 */
public class DetailPageLogApp extends Configured implements Tool {
    static final String OUT_PATH = "detail_page_log";
    private String tableName = OUT_PATH;
    public static void main(String[] args) throws Exception {
        PpfunsMain.initConfig(args); //初始化配置
        String[] args1 = PpfunsMain.initConfig(args);   //初始化配置参数
        int exitCode = ToolRunner.run(new DetailPageLogApp(), args1);
        System.exit(exitCode);
    }
    @Override
    public int run(String[] strings) throws Exception {
        exce(PpfunsMain.configEntity);
        return 1;
    }
    public void exce(ConfigEntity configEntity) {
        try{
            //建立去重重复用户的数据
            String url =configEntity.getConfiguration().get(ConfigEntity.FORMATT_API_URL).concat(tableName);
            System.out.println("调用接口:"+url);
            if(!"success".equals(HttpClientUtils.doGet(url))){
                System.out.println(DetailPageLogApp.class+"接口初始化失败，表："+tableName);
                return;
            }
            configEntity.getLogTime().validateInputPath();
            String jobName = this.getClass().getSimpleName().concat(LocalDateTime.now().format(DateUtils.TIME_FORMATTER_NO_SIGN));
            ControlledJob controlledJob3 = addMysqlJob(jobName.concat("_mysql"),configEntity);
            JobControl jc = new JobControl("日志文件");
            jc.addJob(controlledJob3);
            Thread jobThread = new Thread(jc);
            jobThread.start();
            // 当jc没有执行完成时，线程等待
            while(!jc.allFinished()){
                Thread.sleep(500);
            }
            jc.stop();
            url =configEntity.getConfiguration().get(ConfigEntity.FORMATT_API_URL).concat("update/").concat(tableName);
            System.out.println("调用更新接口:"+url);
            if(!"success".equals(HttpClientUtils.doGet(url))){
                System.out.println(this.getClass()+"更新接口初始化失败，表："+tableName);
            }
            System.out.println("执行完成"+this.getClass());
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public ControlledJob addMysqlJob(String jobName,ConfigEntity configEntity) throws Exception {
        //job2通过用户数
        Job job = Job.getInstance(configEntity.getConfiguration(),jobName);
        job.setJarByClass(this.getClass());
        job.setMapperClass(DetailPageLogMapper.class);
        job.setMapOutputKeyClass(DetailPageLogWritable.class);
        job.setMapOutputValueClass(NullWritable.class);
        job.setOutputKeyClass(DetailPageLogWritable.class);
        job.setOutputValueClass(NullWritable.class);
        job.setInputFormatClass(CombineLogInputFormat.class);
        CombineLogInputFormat.setInputDirRecursive(job,true);
        CombineLogInputFormat.setMaxInputSplitSize(job, configEntity.getHadoop().getMaxInputSplitSize());
        CombineLogInputFormat.setMinInputSplitSize(job, configEntity.getHadoop().getMinInputSplitSize());
        CombineLogInputFormat.setInputPaths(job,configEntity.getLogTime().inputPath());
        job.setOutputFormatClass(DBOutputFormat.class);
        if(StringUtils.isNotEmpty(configEntity.getDb().getDriverPath())){
            Path path = new Path(configEntity.getDb().getDriverPath());
            if(configEntity.getFileSystem().exists(path)){
                job.addFileToClassPath(path);
            }
        }
        DBConfiguration.configureDB(job.getConfiguration(),configEntity.getDb().getDriver(),configEntity.getDb().getHost(),
                configEntity.getDb().getUsername(),configEntity.getDb().getPassword());
        String[] fieldName = {"parent_column_id","user_type","mac","sn","user_id","column_id","album_id","content_type","after_column_id","after_column_code","area_code","create_time"};
        DBOutputFormat.setOutput(job,tableName,fieldName);
        ControlledJob controlledJob = new ControlledJob(job.getConfiguration());
        controlledJob.setJob(job);
        return controlledJob;
    }
}