package com.jshy.mr;

import com.jshy.mr.model.ConfigEntity;
import com.jshy.mr.utils.CommonUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.util.GenericOptionsParser;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: 简德群
 * Date: 2019/9/27
 * Time: 14:32
 * To change this template use File | Settings | File Templates.
 */
public class PpfunsMain  extends Configured implements Tool {
    public final static ConfigEntity configEntity = new ConfigEntity();
    public static void main(String[] args) throws Exception {
        String[] args1 = initConfig(args);   //初始化配置参数
        System.out.println(CommonUtils.toStringFormat(args1));
        GenericOptionsParser parser = new GenericOptionsParser(configEntity.getConfiguration(), args1);
        int exitCode =0;
        for(Class clazz : configEntity.runClass){
            exitCode = ToolRunner.run((Tool) clazz.newInstance(), args1);
        }
        System.exit(exitCode);
    }
    @Override
    public int run(String[] strings) throws Exception {
        configEntity.setConfiguration(getConf());
        //-XX:-UseGCOverheadLimit -Xms512m -Xmx2048m -verbose:gc -Xloggc:/tmp/mapreduceTask.gc
        GenericOptionsParser parser = new GenericOptionsParser(configEntity.getConfiguration(), strings);
        return 1;
    }

    public static String[] initConfig(String[] args) throws Exception {
        //先读取文件配置
        boolean readResourceFlag = true;
        Map<String,String> map = new HashMap<String,String>();
        List<String> confList = new ArrayList<>();
        for (String arg : args) {
            System.out.println(arg);
            String[] ar = arg.split("=",2);
            if(ConfigEntity.HADOOP_INPUT.equals(ar[0])
                    || ConfigEntity.HADOOP_OUTPUT.equals(ar[0])
                    || ConfigEntity.HADOOP_TEMP_OUTPUT.equals(ar[0])
                    || ConfigEntity.DB_DRIVER.equals(ar[0])
                    || ConfigEntity.DB_DRIVER_PATH.equals(ar[0])
                    || ConfigEntity.DB_HOST.equals(ar[0])
                    || ConfigEntity.DB_USERNAME.equals(ar[0])
                    || ConfigEntity.DB_PASSWORD.equals(ar[0])
                    || ConfigEntity.TIME_PATTERN.equals(ar[0])
                    || ConfigEntity.HADOOP_MAX_INPUT_SPLITE_SIZE.equals(ar[0])
                    || ConfigEntity.PPFUNS_RUN_CLASS.equals(ar[0])
                    || ConfigEntity.FORMATT_API_URL.equals(ar[0])){
                  map.put(ar[0],ar[1]);
                continue;
            }
            if("path".equals(ar[0])){
                 readResourceFlag =false;
                 readFile(ar[1]);continue;
            }
           confList.add(arg);
        }
        if(readResourceFlag){
            readFile(null);
        }
        configEntity.init(map);
        String[] t = new String[confList.size()];
        return  confList.toArray(t);
    }
    private static void readFile(String file) throws IOException {
        InputStream inputStream = null;
        try {
            if(StringUtils.isEmpty(file)){
                String priorityfile = System.getProperty("user.dir").concat("/base.properties");
                if(new File(priorityfile).exists()){
                     file = priorityfile;
                }else{
                    file =  PpfunsMain.class.getClassLoader().getResource("config/base.properties").getPath();
                }
            }
            System.out.println("文件地址路径："+file);
            inputStream = new FileInputStream(file);
            Properties properties = new Properties();
            properties.load(inputStream);
            configEntity.init(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(inputStream != null) inputStream.close();
        }
    }


}
