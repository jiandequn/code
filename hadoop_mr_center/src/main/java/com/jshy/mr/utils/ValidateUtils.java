package com.jshy.mr.utils;

import com.jshy.mr.common.FlumeLogWritable;
import com.jshy.mr.entity.CleanEntity;
import com.jshy.mr.entity.base.ResultData;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

/**
 * 字段验证器
 * Created with IntelliJ IDEA.
 * User: 简德群
 * Date: 2019/9/25
 * Time: 15:27
 * To change this template use File | Settings | File Templates.
 */
public class ValidateUtils {
    private static List<String> parentColumnIdList = Arrays.asList("38","6029");  //父类产品ID
    public final static List<String> parentColumnCodeList = Arrays.asList("38.PAGE_YNGDJSJ.0.0","6029.PAGE_YNGDJSJPB.0.0");  //父类产品ID
    public static CleanEntity cleanEntity = null;
    private final static Integer a = new Integer(0);
    public static synchronized void initCleanEntity(Configuration conf){
        String apiUrl = conf.get("format.api.url");
        synchronized (a){
            if(cleanEntity == null){
                    String t =HttpClientUtils.doGet(apiUrl+"/clean/log/condition");
                    ResultData<CleanEntity> resultData = null;
                    if(StringUtils.isNotEmpty(t)){
                        ObjectMapper om = new ObjectMapper();//把读取的json转化为对象
                        try {
                            resultData = om.readValue(t, new TypeReference<ResultData<CleanEntity>>() {});
                            cleanEntity = (CleanEntity) resultData.getData();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

//                        resultData = new Gson().fromJson(t,ResultData.class);
                    }
                    String validateTimes = conf.get("canshu.validateTime");
                    System.out.println("初始化过滤事件参数:"+validateTimes);
                    cleanEntity.setValTime(validateTimes.split(","));
            }
        }
    }
    private static final DateFormat DF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static boolean isMac(String mac){
        if(StringUtils.isEmpty(mac)){
            return false;
        }
        if(mac.matches("(([A-Fa-f0-9]{2}-)|([A-Fa-f0-9]{2}:)|([A-Fa-f0-9]{2})){5}[A-Fa-f0-9]{2}")){
              return true;
        }
       return false;
    }
    public static boolean isUserType(String userType){
       return "vod".equalsIgnoreCase(userType);
    }

    public static boolean isSn(String sn){
        if(StringUtils.isEmpty(sn)){
            return false;
        }
        return true;
    }
    public static boolean isTime(String createTime){
        try {
            DF.parse(createTime);
            return true;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }
    public static boolean validateCreateTime(String createTime,String[] valTime){
        if(StringUtils.isEmpty(createTime)){
                return false;
        }
        if(createTime.compareTo(valTime[0])>=0
                && createTime.compareTo(valTime[1])<=0){
            return true;
        }
        return false;
    }
    public static boolean validate(FlumeLogWritable writable){
        String userId = writable.getUserId();
        if( !ValidateUtils.isMac(writable.getMac())
                || StringUtils.isEmpty(writable.getSn())
                || StringUtils.isEmpty(userId)
                || UserTypeEnum.isNotExist(writable.getUserType())
                || EventsTypeEnum.isNotExist(writable.getEventsType())
                ){
            return false;
        }
        writable.setUserType(writable.getUserType().toUpperCase());
        //清理用户ID
        if(cleanEntity.getUserIdList().stream().anyMatch(s -> s.equals(userId))) return false;
        if(StringUtils.isNotEmpty(writable.getParentColumnId())){
            if(!cleanEntity.getParentColumnList().stream().anyMatch(parentColumnEntity -> writable.getParentColumnId().equals(parentColumnEntity.getColumnId()))){
                 return false;
            }
        }
//        if(flag == false) return flag;
        return validateCreateTime(writable.getCreateTime(),cleanEntity.getValTime());
    }
    public static void main(String[] args) {
        System.out.println(isMac("AC:4A:FE:A5:8A:8E"));
    }
}
