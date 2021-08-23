package com.jshy.mr.report.user_info;

import com.jshy.mr.PpfunsMain;
import com.jshy.mr.common.CombineLogInputFormat;
import com.jshy.mr.model.ConfigEntity;
import com.jshy.mr.model.StatisticalMethodEnum;
import com.jshy.mr.mysqlOutPut.DBFixConfiguration;
import com.jshy.mr.mysqlOutPut.DBFixOutPutFormat;
import com.jshy.mr.utils.DateUtils;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.jobcontrol.ControlledJob;
import org.apache.hadoop.mapreduce.lib.jobcontrol.JobControl;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;
import org.apache.hadoop.mapreduce.lib.partition.HashPartitioner;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * 按时间段统计历史播放记录日志文件
 */
public class UserInfoApp extends Configured implements Tool {
    static final String OUT_PATH = "user_info";
    static final String OUT_PATH_FORMAT="visit";//浏览用户数
    private String tableName = OUT_PATH;
    public static void main(String[] args) throws Exception {
        String[] args1 = PpfunsMain.initConfig(args);   //初始化配置参数
        int exitCode = ToolRunner.run(new UserInfoApp(), args1);
        System.exit(exitCode);
    }
    @Override
    public int run(String[] strings) throws Exception {
        exce(PpfunsMain.configEntity);
        return 1;
    }
    public void test(ConfigEntity configEntity){
        try{
            String outPathStr = configEntity.getHadoop().getOutput()+ "/"+OUT_PATH.concat("/").concat(OUT_PATH_FORMAT);
            Path outPath = new Path(outPathStr);
            configEntity.getLogTime().validateInputPath();
            //删掉过滤日期下的内容
            Path[] outPaths = getOutPutDir(configEntity,outPathStr);
            //建立job1和job2的从属关系
            String jobName = this.getClass().getSimpleName().concat(LocalDateTime.now().format(DateUtils.TIME_FORMATTER_NO_SIGN));
            ControlledJob controlledJob3 = addMysqlJob(jobName.concat("_mysql"),configEntity,outPaths);
            JobControl jc = new JobControl("统计专区用户数");
            jc.addJob(controlledJob3);
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
    public void exce(ConfigEntity configEntity) {
        try{
            String outPathStr = configEntity.getHadoop().getOutput()+ "/"+OUT_PATH.concat("/").concat(OUT_PATH_FORMAT);
            Path outPath = new Path(outPathStr);
            configEntity.getLogTime().validateInputPath();
            //删掉过滤日期下的内容
            Path[] outPaths = getOutPutDir(configEntity,outPathStr);
            configEntity.getLogTime().delFilePaths(outPaths);
            //建立job1和job2的从属关系
            String jobName = this.getClass().getSimpleName().concat(LocalDateTime.now().format(DateUtils.TIME_FORMATTER_NO_SIGN));
            ControlledJob controlledJob1 = addJob1(jobName.concat("_1"),configEntity,outPath);
            ControlledJob controlledJob3 = addMysqlJob(jobName.concat("_mysql"),configEntity,outPaths);
            controlledJob3.addDependingJob(controlledJob1);
            JobControl jc = new JobControl("统计专区用户数");
            jc.addJob(controlledJob1);
            jc.addJob(controlledJob3);
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
    public ControlledJob addJob1(String jobName,ConfigEntity configEntity,Path output) throws IOException {

        Job job = Job.getInstance(configEntity.getConfiguration(),jobName);
        job.setJarByClass(this.getClass());
        job.setMapperClass(UserInfoMapper.class);
        job.setPartitionerClass(HashPartitioner.class);
        System.out.println("配置的mapreduce.job.reduces="+configEntity.getConfiguration().get("mapreduce.job.reduces"));
        job.setNumReduceTasks(3);
        job.setCombinerClass(UserInfoCombineReducer.class);
        job.setReducerClass(UserInfoReducer.class);
        job.setMapOutputKeyClass(UserInfoWritable.class);
        job.setMapOutputValueClass(Text.class);
        job.setOutputKeyClass(UserInfoWritable.class);
        job.setOutputValueClass(NullWritable.class);
        job.setInputFormatClass(CombineLogInputFormat.class);
        job.setOutputFormatClass(TextOutput2Format.class);
        CombineLogInputFormat.setInputDirRecursive(job,true);
        CombineLogInputFormat.setMaxInputSplitSize(job, configEntity.getHadoop().getMaxInputSplitSize());
        CombineLogInputFormat.setMinInputSplitSize(job, configEntity.getHadoop().getMinInputSplitSize());
        CombineLogInputFormat.setInputPaths(job,configEntity.getLogTime().inputPath());
//        job.getConfiguration().get("mapreduce.output.basename", "user_info");
        MultipleOutputs.addNamedOutput(job, "userInfo", TextOutput2Format.class, UserInfoWritable.class, Text.class);
        TextOutput2Format.setOutputPath(job,output);
//        LazyOutputFormat.setOutputFormatClass(job, OrcOutputFormat.class);
        ControlledJob controlledJob = new ControlledJob(job.getConfiguration());
        controlledJob.setJob(job);
        return controlledJob;
    }
    public ControlledJob addMysqlJob(String jobName,ConfigEntity configEntity,Path[] inputPath) throws Exception {
        //job2通过用户数
        Job job = Job.getInstance(configEntity.getConfiguration(),jobName);
        job.setJarByClass(this.getClass());
        job.setMapperClass(UserInfo2Mapper.class);
        job.setCombinerClass(UserInfoCombineReducer.class);
        job.setReducerClass(UserInfo2Reducer.class);
        job.setMapOutputKeyClass(UserInfoWritable.class);
        job.setMapOutputValueClass(Text.class);
        job.setOutputKeyClass(UserInfoWritable.class);
        job.setOutputValueClass(NullWritable.class);
        FileInputFormat.setInputDirRecursive(job,true);
        FileInputFormat.setInputPaths(job,inputPath);
        job.setOutputFormatClass(DBFixOutPutFormat.class);
//        if(StringUtils.isNotEmpty(configEntity.getDb().getDriverPath())){
//            Path path = new Path(configEntity.getDb().getDriverPath());
//            if(configEntity.getFileSystem().exists(path)){
//                job.addFileToClassPath(path);
//            }
//        }
        DBFixConfiguration.configureDB(job.getConfiguration(),configEntity.getDb().getDriver(),configEntity.getDb().getHost(),
                configEntity.getDb().getUsername(),configEntity.getDb().getPassword());

//        String[] fields = new String[]{"parent_column_id","user_type","mac","sn","user_id","create_time"};
//        String[] filterFields = new String[]{"parent_column_id","user_type","mac","sn","user_id"};
//        DBFixOutPutFormat.setOutputAndFilter(job,tableName,fields,filterFields);
        String sqlScrpit = "INSERT INTO user_info (parent_column_id, user_type, mac, sn, user_id,area_code, create_time) VALUES (?,?,?,?,?,?,?) ON DUPLICATE KEY UPDATE create_time=IF(create_time<?,create_time,?),area_code=IF(LENGTH(?) and LENGTH(?)<3,?,area_code)";
//              +",area_code=IF(LENGTH(?) and LENGTH(?)<3,IF(FIND_IN_SET(?,area_code),area_code,CONCAT_WS(',',IF(LENGTH(area_code),area_code,NULL),?)),area_code)";
        DBFixOutPutFormat.setOutputBySqlScript(job,sqlScrpit);
        ControlledJob controlledJob = new ControlledJob(job.getConfiguration());
        controlledJob.setJob(job);
        return controlledJob;
    }
    public Path[] getOutPutDir(ConfigEntity configEntity,String outPut) throws IOException, URISyntaxException {
        Path[] paths=null;
        String[] valiateTimeArr = configEntity.getLogTime().getValTime();
        if(configEntity.getLogTime().getStatisticalMethod() == StatisticalMethodEnum.YEAR.getId()){
            paths = new Path[1];
            paths[0]=new Path(outPut+"/"+valiateTimeArr[0]);
        }else if(configEntity.getLogTime().getStatisticalMethod() == StatisticalMethodEnum.WEEK.getId()
                ||configEntity.getLogTime().getStatisticalMethod() == StatisticalMethodEnum.DAY.getId()
                || configEntity.getLogTime().getStatisticalMethod() == StatisticalMethodEnum.MONTH.getId()
                || configEntity.getLogTime().getStatisticalMethod() == StatisticalMethodEnum.NO_TIME.getId()){
            paths= neetPath(valiateTimeArr[0],valiateTimeArr[1],outPut);
        }

        return paths;
    }
    private Path[] neetPath(String startDateStr,String endDateStr,String outPut){
        List<Path> pathList = new ArrayList<>();
        DateTimeFormatter dayformatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        if(startDateStr.length() == 7){
            startDateStr = startDateStr.concat("-01");
        }
        if(endDateStr.length() == 7){
            endDateStr = endDateStr.concat("-01");
        }
        startDateStr =startDateStr.replaceAll("-","/");
        endDateStr =endDateStr.replaceAll("-","/");
        LocalDate startDate =LocalDate.parse(startDateStr,dayformatter);
        LocalDate endDate =LocalDate.parse(endDateStr,dayformatter);
        long days = endDate.toEpochDay() - startDate.toEpochDay();
        for(int i=0;i<=days;i++){
            String path = startDate.plusDays(i).format(dayformatter) ;
            if(path.compareTo(endDateStr)<0){
                pathList.add(new Path(outPut + "/" + path));
            }
        }
        Path[] paths = new Path[pathList.size()];
        pathList.toArray(paths);
        return paths;
    }
}