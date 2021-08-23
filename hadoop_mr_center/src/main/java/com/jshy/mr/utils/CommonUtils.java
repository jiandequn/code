package com.jshy.mr.utils;

import com.jshy.mr.common.FlumeLogWritable;
import com.jshy.mr.model.ConfigEntity;
import com.jshy.mr.model.StatisticalMethodEnum;
import org.apache.hadoop.conf.Configuration;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: 简德群
 * Date: 2019/9/19
 * Time: 14:23
 * To change this template use File | Settings | File Templates.
 */
public class CommonUtils {
    private final static String REPLACE_PREFIX = "^.*Collection events:";
    private final static String REPLACE_SUFFIX = ":END(\n){0,1}$";
    private final static String PARAM_SPLIT_SIGN = ";"; //参数拆分
    private final static String KEY_VALUE_SPLIT_SIGN = "="; //参数拆分
    private static Field[] flumeLogsFields = FlumeLogWritable.class.getDeclaredFields();
    public static String getText(String text){
        String t = text.replaceFirst(REPLACE_PREFIX,"").replaceFirst(REPLACE_SUFFIX,""); //替换前缀和后缀
        //转成key-value
        return t;
    }
    public static Map<String,String> getHashMap(String text){
        String t = getText(text);
        String[] arr = t.split(PARAM_SPLIT_SIGN);
        Map<String,String> map = new HashMap<>();
        for(String a : arr){
            String[] arr1 = a.split(KEY_VALUE_SPLIT_SIGN,2);
            map.put(arr1[0],arr1[1]);
        }
        return map;
    }
    public static FlumeLogWritable getFlumeLogWritable(String text,FlumeLogWritable writable){
        String t = getText(text);
        String[] arr = t.split(PARAM_SPLIT_SIGN);
        try {
            if(writable == null){
                writable = new FlumeLogWritable();
            }else{
                for(Field field : flumeLogsFields){
                    field.setAccessible(true);
                    field.set(writable,"");
                }
            }
            for(String a : arr){
                String[] keyValue = a.split(KEY_VALUE_SPLIT_SIGN,2);
                Field field =FlumeLogWritable.class.getDeclaredField(keyValue[0]);
                field.setAccessible(true);
                field.set(writable,keyValue[1]);

            }
        } catch (NoSuchFieldException e) {
            System.err.println("找不到指定参数:"+text);
        } catch (IllegalAccessException e) {
            System.err.println("找不到指定参数的值:"+text);
        }
        return writable;
    }
    public static String toStringFormat(Object ... o){
       StringBuffer stringBuffer = new StringBuffer(o.length*2);
        for(int i=0;i<o.length;i++){
            stringBuffer.append(o[i].toString());
            if(i<o.length-1){
                stringBuffer.append("\t");
            }
        }
        return stringBuffer.toString();
    }

    /**
     * 得到统计方式 （按周，日，年，月，季度）
     * @param configuration
     * @return
     */
    public static int statisticalMethod(Configuration configuration){
        return configuration.getInt(ConfigEntity.PPFUNS_STATISTICAL_METHOD, StatisticalMethodEnum.WEEK.getId());
    }
    public static List<String> getSqlDateField(Configuration configuration){
        List<String> list= new ArrayList<>();
        int flag = statisticalMethod(configuration);
        if(StatisticalMethodEnum.YEAR.getId() == flag){
            list.add(StatisticalMethodEnum.YEAR.getName());
        }else if(StatisticalMethodEnum.QUARTER.getId() == flag){
            list.add(StatisticalMethodEnum.YEAR.getName());
            list.add(StatisticalMethodEnum.QUARTER.getName());
        }else if(StatisticalMethodEnum.MONTH.getId() == flag){
            list.add(StatisticalMethodEnum.YEAR.getName());
            list.add(StatisticalMethodEnum.MONTH.getName());
        }else if(StatisticalMethodEnum.WEEK.getId() == flag){
            list.add(StatisticalMethodEnum.YEAR.getName());
            list.add(StatisticalMethodEnum.WEEK.getName());
        }else if(StatisticalMethodEnum.DAY.getId() == flag){
            list.add(StatisticalMethodEnum.DAY.getName());
        }
        return list;
    }

    public static List<String> getSqlDateField(int flag){
        List<String> list= new ArrayList<>();
        if(StatisticalMethodEnum.YEAR.getId() == flag){
            list.add(StatisticalMethodEnum.YEAR.getName());
        }else if(StatisticalMethodEnum.QUARTER.getId() == flag){
            list.add(StatisticalMethodEnum.YEAR.getName());
            list.add(StatisticalMethodEnum.QUARTER.getName());
        }else if(StatisticalMethodEnum.MONTH.getId() == flag){
            list.add(StatisticalMethodEnum.YEAR.getName());
            list.add(StatisticalMethodEnum.MONTH.getName());
        }else if(StatisticalMethodEnum.WEEK.getId() == flag){
            list.add(StatisticalMethodEnum.YEAR.getName());
            list.add(StatisticalMethodEnum.WEEK.getName());
        }else if(StatisticalMethodEnum.DAY.getId() == flag){
            list.add(StatisticalMethodEnum.DAY.getName());
        }
        return list;
    }
    public static List<String> getUserTypeField(){
        List<String> list= new ArrayList<>();
        list.add("parent_column_id");
        list.add("user_type");
        return list;
    }
    public static List<String> getSqlUserField(){
        List<String> list= new ArrayList<>();
        list.add("mac");
        list.add("sn");
        list.add("user_id");
        return list;
    }
}
