package com.jshy.mr.model;

import com.jshy.mr.log.album_play.AlbumPlayLogApp;
import com.jshy.mr.log.detail_page.DetailPageLogApp;
import com.jshy.mr.log.first_page.FirstPageLogApp;
import com.jshy.mr.report.global_user.GlobalUserApp;
import com.jshy.mr.report.user_info.UserInfoApp;
import com.jshy.mr.report.user_stay_duration.UserStayDurationApp;
import com.jshy.mr.utils.DatePathUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.IOException;
import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 * User: 简德群
 * Date: 2019/9/27
 * Time: 15:12
 * To change this template use File | Settings | File Templates.
 */
public class ConfigEntity implements Serializable {
    public final static String HADOOP_INPUT ="hadoop.input";
    public final static String HADOOP_OUTPUT ="hadoop.output";
    public final static String HADOOP_TEMP_OUTPUT ="hadoop.temp_output";
    public final static String DB_HOST ="db.host";
    public final static String DB_USERNAME ="db.username";
    public final static String DB_PASSWORD ="db.password";
    public final static String DB_DRIVER ="db.driver";
    public final static String DB_DRIVER_PATH="db.driver.path";
    public final static String TIME_PATTERN="time.pattern";  //
    public final static String HADOOP_MAX_INPUT_SPLITE_SIZE="hadoop.maxInputSplitSize";
    public final static String HADOOP_MIN_INPUT_SPLITE_SIZE="hadoop.minInputSplitSize";
    public final static String FORMATT_API_URL="format.api.url";
    public final static String PPFUNS_STATISTICAL_METHOD = "ppfuns.statistical.method";  //统计方式
        public final static String PPFUNS_RUN_CLASS="ppfuns.run.class";
    private HadoopProperties hadoop;
    private DbProperties db;
    private LogTimeDirProperties logTime; //时间统计
    private Map<String,String> commonProperties = new HashMap<>();
    FileSystem fileSystem;
    private Configuration conf = new Configuration();
    public List<Class> runClass = new ArrayList<>();

    public Configuration getConfiguration(){
        if(logTime != null){
            logTime.initValTime();
            conf.setInt(PPFUNS_STATISTICAL_METHOD,logTime.statisticalMethod);
            conf.set("canshu.validateTime",logTime.valTime[0]+","+logTime.valTime[1]);
        }
        if(commonProperties.size()>0){
             commonProperties.forEach((k,v)->conf.set(k,v));
        }
        conf.set("mapred.child.java.opts","-XX:-UseGCOverheadLimit -Xms256m -Xmx1024m -verbose:gc -Xloggc:/tmp/mapreduceTask.gc");
        return conf;
    }
    public FileSystem getFileSystem() throws URISyntaxException, IOException {
        if(fileSystem == null){
            System.out.println("验证输入路径:"+hadoop.getInput());
            fileSystem = FileSystem.get(new URI(hadoop.getInput()),getConfiguration());

        }
        return fileSystem;
    }
    public void init(Properties properties){
        hadoop = new HadoopProperties(properties.getProperty(HADOOP_INPUT),properties.getProperty(HADOOP_OUTPUT),properties.getProperty(HADOOP_TEMP_OUTPUT));
        db = new DbProperties(properties.getProperty(DB_DRIVER),properties.getProperty(DB_HOST),
                properties.getProperty(DB_USERNAME),properties.getProperty(DB_PASSWORD),properties.getProperty(DB_DRIVER_PATH));
        logTime = new LogTimeDirProperties(DatePathUtils.getLastWeek());
        String runclasses = properties.getProperty(PPFUNS_RUN_CLASS);
        if(StringUtils.isEmpty(runclasses)){
            runClass.add(UserInfoApp.class);
            runClass.add(GlobalUserApp.class);
            runClass.add(AlbumPlayLogApp.class);
            runClass.add(DetailPageLogApp.class);
            runClass.add(FirstPageLogApp.class);
            runClass.add(UserStayDurationApp.class);
        }
        if( properties.containsKey(FORMATT_API_URL)){
            commonProperties.put(FORMATT_API_URL, properties.getProperty(FORMATT_API_URL));
        }
    }

    public void init(Map<String, String> map) {
        hadoop = hadoop != null?hadoop:new HadoopProperties();
        db = db != null?db:new DbProperties();
        map.keySet().forEach(key->{
            switch (key){
                case HADOOP_INPUT : hadoop.setInput(map.get(HADOOP_INPUT));break;
                case HADOOP_OUTPUT : hadoop.setOutput(map.get(HADOOP_OUTPUT));break;
                case HADOOP_TEMP_OUTPUT : hadoop.setTempOutput(map.get(HADOOP_TEMP_OUTPUT));break;
                case HADOOP_MAX_INPUT_SPLITE_SIZE: hadoop.setMaxInputSplitSize(map.get(HADOOP_MAX_INPUT_SPLITE_SIZE));break;
                case HADOOP_MIN_INPUT_SPLITE_SIZE: hadoop.setMinInputSplitSize(map.get(HADOOP_MIN_INPUT_SPLITE_SIZE));break;
                case DB_DRIVER : db.setDriver(map.get(DB_DRIVER));break;
                case DB_DRIVER_PATH : db.setDriverPath(map.get(DB_DRIVER_PATH)); break;
                case DB_HOST : db.setHost(map.get(DB_HOST));break;
                case DB_USERNAME : db.setUsername(map.get(DB_USERNAME));break;
                case DB_PASSWORD : db.setPassword(map.get(DB_PASSWORD));break;
                case FORMATT_API_URL:commonProperties.put(FORMATT_API_URL,map.get(FORMATT_API_URL));break;
                case TIME_PATTERN :convertTime(map.get(TIME_PATTERN));break;
                case PPFUNS_RUN_CLASS:addRunClass(map.get(PPFUNS_RUN_CLASS));break;
//                case END_DATE : logTimeDir.setEndDate(map.get(END_DATE));break;
            }
        });
    }
    public DbProperties getDb() {
        return db;
    }

    public void setDb(DbProperties db) {
        this.db = db;
    }

    public HadoopProperties getHadoop() {
        return hadoop;
    }

    public void setHadoop(HadoopProperties hadoop) {
        this.hadoop = hadoop;
    }

    public LogTimeDirProperties getLogTime() {
        return logTime;
    }

    public void setLogTime(LogTimeDirProperties logTime) {
        this.logTime = logTime;
    }

     private void addRunClass(String runclasses){
         if(!StringUtils.isEmpty(runclasses)){
             try {
                 runClass.clear();
                 String[] classess = runclasses.split(",");
                 for(String clazz : classess){
                     runClass.add(Class.forName(clazz));
                 }
             } catch (ClassNotFoundException e) {
                 e.printStackTrace();
                 System.exit(1);
             }

         }



     }
    private void convertTime(String time){
        if(StringUtils.isEmpty(time)){
            System.out.println(ConfigEntity.TIME_PATTERN+"为空");
            System.exit(1);
            return;
        }
        String[] arr =time.split(";");
        for(String a : arr){
            String[] k = a.split(":");
            if(k.length <=1){
                System.err.println(ConfigEntity.TIME_PATTERN+"参数异常:"+time);
                System.exit(1);
            }
            if(StringUtils.isEmpty(k[0]) || StringUtils.isEmpty(k[1])){
                System.err.println(ConfigEntity.TIME_PATTERN+"参数异常:"+time);
                System.exit(1);
            }
            logTime = new LogTimeDirProperties();
            switch (k[0]){
                case "y":
                    if(k[1].matches("^20[0-9]{2}$")){    //验证年份
                        String path = k[1];
                        logTime.setStatisticalMethod(StatisticalMethodEnum.YEAR.getId());
                        logTime.addPathList(path);
                        break;
                    }
                    System.err.println(ConfigEntity.TIME_PATTERN+"参数异常:"+time);
                    System.exit(1);
                case "m":
                    if(k[1].matches("^20[0-9]{2}-((0[1-9]{1})|(1[0-2]{1}))$")){    //验证月份
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
                        String[] timeArr = k[1].split("-");
                        LocalDate localDate =LocalDate.of(Integer.valueOf(timeArr[0]),Integer.valueOf(timeArr[1]),1);
                        String path = k[1].replace("-","/");
                        logTime.setStatisticalMethod(StatisticalMethodEnum.MONTH.getId());
                        logTime.addPathList(path);
                        logTime.addPathList(localDate.with(TemporalAdjusters.firstDayOfNextMonth()).format(DateTimeFormatter.ofPattern("yyyy/MM/dd")));
                        break;
                    }
                    System.err.println(ConfigEntity.TIME_PATTERN+"参数异常:"+time);
                    System.exit(1);
                case "w":
                    if(k[1].matches("^20[0-9]{2}-((0[1-9]{1})|(1[0-2]{1}))-[0-9]{2}$")){
                        //验证日期
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                        LocalDate localDate =LocalDate.parse(k[1],formatter);
                        WeekFields weekFields = WeekFields.of(DayOfWeek.MONDAY,1);
                        int weekOfDay =localDate.getDayOfWeek().getValue();
                        LocalDate firstDate = localDate.minusDays(weekOfDay-1);
//                        LocalDate lastDate = localDate.plusDays(7-weekOfDay);
//                        addValTime(firstDate.format(formatter),localDate.plusDays(8-weekOfDay).format(formatter));
                        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                        for(int i=0;i<=7;i++){
                            String path = firstDate.plusDays(i).format(formatter1) ;
                            logTime.setStatisticalMethod(StatisticalMethodEnum.WEEK.getId());
                            logTime.addPathList(path);
                        }
                        break;
                    }
                    System.err.println(ConfigEntity.TIME_PATTERN+"参数异常:"+time);
                    System.exit(1);
                case "d":
                    if(k[1].matches("^20[0-9]{2}-((0[1-9]{1})|(1[0-2]{1}))-[0-9]{2}~20[0-9]{2}-((0[1-9]{1})|(1[0-2]{1}))-[0-9]{2}$")){
                        //验证日期
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                        String[] dateStr = k[1].split("~");
                        LocalDate startDate =LocalDate.parse(dateStr[0],formatter);
                        LocalDate endDate =LocalDate.parse(dateStr[1],formatter);
//                        addValTime(dateStr[0],dateStr[1]);
                        long days = endDate.toEpochDay() - startDate.toEpochDay();
                        if( days <= 0){
                            System.err.println(ConfigEntity.TIME_PATTERN+"参数异常，时间范围不对:"+time);
                            System.exit(1);
                        }
                        DateTimeFormatter dayformatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                        DateTimeFormatter monthformatter = DateTimeFormatter.ofPattern("yyyy/MM");
                        String path = "";
                        logTime.setStatisticalMethod(StatisticalMethodEnum.DAY.getId());
                        for(long i=0;i<=days;i++){
                            LocalDate cur = startDate.plusDays(i);
                            if((cur.getMonth().getValue()==startDate.getMonth().getValue() && cur.getYear()==startDate.getYear())
                                    ||(cur.getMonth().getValue()==endDate.getMonth().getValue() && cur.getYear()==endDate.getYear())){
                                path = cur.format(dayformatter);
                            }else{
                                path = cur.format(monthformatter);
                            }
                            logTime.addPathList(path);
                        }
                        break;
                    }
                    System.err.println(ConfigEntity.TIME_PATTERN+"参数异常:"+time);
                    System.exit(1);
                case "time":
                    if(k[1].matches("^20[0-9]{2}-((0[1-9]{1})|(1[0-2]{1}))-[0-9]{2}~20[0-9]{2}-((0[1-9]{1})|(1[0-2]{1}))-[0-9]{2}$")){
                        //验证日期
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                        String[] dateStr = k[1].split("~");
                        LocalDate startDate =LocalDate.parse(dateStr[0],formatter);
                        LocalDate endDate =LocalDate.parse(dateStr[1],formatter);
                        long days = endDate.toEpochDay() - startDate.toEpochDay();
                        if( days <= 0){
                            System.err.println(ConfigEntity.TIME_PATTERN+"参数异常，时间范围不对:"+time);
                            System.exit(1);
                        }
                        DateTimeFormatter dayformatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                        DateTimeFormatter monthformatter = DateTimeFormatter.ofPattern("yyyy/MM");
                        for(long i=0;i<=days;i++){
                            LocalDate cur = startDate.plusDays(i);
                            if((cur.getMonth().getValue()==startDate.getMonth().getValue() && cur.getYear()==startDate.getYear())
                                    ||(cur.getMonth().getValue()==endDate.getMonth().getValue() && cur.getYear()==endDate.getYear())){
                                logTime.addPathList(cur.format(dayformatter));
                            }else{
                                logTime.addPathList(cur.format(monthformatter));
                            }

                        }
                        break;
                    }
                    System.err.println(ConfigEntity.TIME_PATTERN+"参数异常:"+time);
                    System.exit(1);
                default:
                    System.err.println(ConfigEntity.TIME_PATTERN+"无效参数:"+time);
                    System.exit(1);
            }
        }
    }

    public void setConfiguration(Configuration conf) {
        this.conf = conf;
    }

    public class HadoopProperties implements Serializable{
        private String input;
        private String output;
        private String tempOutput;
        private List<String> inputs = new ArrayList();
        private long maxInputSplitSize = 1024*1024*200;
        private long minInputSplitSize = 1024*1024*50;
        public HadoopProperties(){
        }
        public HadoopProperties(String input,String output,String tempOutput){
            this.input = input;
            this.output = output;
            this.tempOutput = tempOutput;
        }

       public List<String> getInputs() {
           return inputs;
       }

       public HadoopProperties setInputs(String input) {
           this.inputs.add(this.input+"/"+input);
           return this;
       }

        public long getMaxInputSplitSize() {
            return maxInputSplitSize;
        }

        public void setMaxInputSplitSize(String maxInputSplitSize) {
            this.maxInputSplitSize = Long.valueOf(maxInputSplitSize);
        }

        public long getMinInputSplitSize() {
            return minInputSplitSize;
        }

        public void setMinInputSplitSize(String minInputSplitSize) {
            this.minInputSplitSize = Long.valueOf(minInputSplitSize);
        }

        public String getInput() {
            return input;
        }

        public void setInput(String input) {
            this.input = input;
        }

        public String getOutput() {
            return output;
        }

        public void setOutput(String output) {
            this.output = output;
        }

       public String getTempOutput() {
           return tempOutput;
       }

       public void setTempOutput(String tempOutput) {
           this.tempOutput = tempOutput;
       }
   }

   public class DbProperties implements Serializable{
        private String host;
        private String username;
        private String password;
        private String driver;
        private String driverPath;
        public DbProperties(String driver,String host,String username,String password,String driverPath){
            this.host = host;
            this.username = username;
            this.password = password;
            this.driver = driver;
            this.driverPath = driverPath;
        }
        public DbProperties(){
        }
        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getDriver() {
            return driver;
        }

        public void setDriver(String driver) {
            this.driver = driver;
        }

       public String getDriverPath() {
           return driverPath;
       }

       public void setDriverPath(String driverPath) {
           this.driverPath = driverPath;
       }
   }
    public class LogTimeDirProperties implements Serializable{
        private List<String> pathList = new ArrayList<>();
        private boolean isEffictive = false;   //是否启动
        private boolean isAuto = true; //默认自动
        private int statisticalMethod = StatisticalMethodEnum.WEEK.getId();

        private String[] valTime = new String[2]; //验证文件内容是否符合时间区间内
        private Path[] paths;
        private void initValTime(){
            String last = pathList.get(pathList.size()-1);
            valTime[0] = pathList.get(0).replaceAll("/","-");
            String[] t = last.split("/");
            if(isEffictive){
                if(t.length == 1){
                    valTime[1] = (Integer.valueOf(pathList.get(0))+1)+"";
                }else {
                    valTime[1] = pathList.get(pathList.size()-1).replaceAll("/","-");
                }
            }
        }
        public String[] getValTime(){
            if(valTime[0] == null){
                initValTime();
            }
            return valTime;
        }
        public LogTimeDirProperties(List<String> pathList){
            this.pathList = pathList;
            if(!CollectionUtils.isEmpty(this.pathList)){
                isEffictive=true;
            }
        }
        public LogTimeDirProperties(){
        }

        public int getStatisticalMethod() {
            return statisticalMethod;
        }

        public void setStatisticalMethod(int statisticalMethod) {
            this.statisticalMethod = statisticalMethod;
        }

        public List<String> getPathList() {
            return pathList;
        }

        public void setPathList(List<String> pathList) {
            this.pathList = pathList;
        }
        public void addPathList(String path) {
            if(this.pathList.contains(path)){
                return;
            }
            this.pathList.add(path);
            if(!this.isEffictive){
                this.isEffictive = true;
                this.isAuto = false;
            }
        }

        public boolean isEffictive() {
            return isEffictive;
        }

        public void setEffictive(boolean effictive) {
            isEffictive = effictive;
        }
        public Path[] inputPath(){
            if(paths == null){
                try {
                    validateInputPath();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            }
             return paths;
        }

        public boolean validateInputPath() throws IOException, URISyntaxException {
            if(paths != null){
                return true;
            }
            Integer len = pathList.size();
            List<Path> list = pathList.stream().limit(pathList.size()-1).map(s-> new Path(getHadoop().getInput().concat("/").concat(s))
            ).collect(Collectors.toList());
//            for(int i=0;i<len-1;i++){
//                Path path=  new Path(getHadoop().getInput().concat("/").concat(pathList.get(i)));
//                paths[i] = path;
//                if(!getFileSystem().exists(path)){
//                    System.err.println("文件目录不存在:"+path);
//                    System.exit(1);
//                }
//            }
            String lastPath = pathList.get(len-1);
            Integer sepLen = lastPath.split("/").length;
            //解决当天flume写入文件导致报错的问题；
            if(sepLen == 1){ //年份
                Integer lastYear = Integer.valueOf(lastPath)+1;
                list.add( new Path(getHadoop().getInput().concat("/").concat(lastYear+"").concat("/01/01/00")));
                list.add( new Path(getHadoop().getInput().concat("/").concat(lastYear+"").concat("/01/01/01")));
                list.add( new Path(getHadoop().getInput().concat("/").concat(lastYear+"").concat("/01/01/02")));
            }else if(sepLen ==2) {   //月份
                list.add( new Path(getHadoop().getInput().concat("/").concat(lastPath).concat("/01/00")));
                list.add( new Path(getHadoop().getInput().concat("/").concat(lastPath).concat("/01/01")));
                list.add( new Path(getHadoop().getInput().concat("/").concat(lastPath).concat("/01/02")));
            }else if(sepLen == 3){
                list.add( new Path(getHadoop().getInput().concat("/").concat(lastPath).concat("/00")));
                list.add( new Path(getHadoop().getInput().concat("/").concat(lastPath).concat("/01")));
                list.add( new Path(getHadoop().getInput().concat("/").concat(lastPath).concat("/02")));
            }else{
                list.add( new Path(getHadoop().getInput().concat("/").concat(lastPath)));
            }

            for(Path path : list){
                if(!getFileSystem().exists(path)){
                    System.err.println("文件目录不存在:"+path);
                    System.exit(1);
                }
                System.out.println("文件检查目录:"+path);
            }
            paths = new  Path[list.size()];
            list.toArray(paths);
            return true;
        }
       public void delFilePaths(Path... paths) throws URISyntaxException, IOException {
            for(Path path : paths){
                if (getFileSystem().exists(path)) {
                    getFileSystem().delete(path, true);
                    System.out.println("删除hdfs目录数据:"+path);
                }
            }
        }
    }
}

