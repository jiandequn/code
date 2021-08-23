package com.jshy.mr.utils;

import com.jshy.mr.model.ConfigEntity;
import org.apache.commons.lang3.StringUtils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: 简德群
 * Date: 2019/10/8
 * Time: 17:18
 * To change this template use File | Settings | File Templates.
 */
public class DatePathUtils {
    /**
     *   获取前一天的数据
     * @return
     */
    public static List<String>  getDay(){
        List<String> pathList = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate localDate = LocalDate.now();
        LocalDate currentWeekDay = localDate.minusDays(1);//获取前一天
        String dir = currentWeekDay.format(formatter);
        pathList.add(dir);
        pathList.add(localDate.format(formatter));
        return pathList;
    }

    /**
     * 处理周数据
     */
    public static List<String>  getCurrentWeek(){
        List<String> pathList = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate localDate = LocalDate.now();
        int weekDay = localDate.getDayOfWeek().getValue();
        final LocalDate monday= localDate.minusDays(weekDay-1);
        for(int i=0;i<7;i++){
            LocalDate currentWeekDay = monday.plusDays(i);//获取上个月第一天
            String timedir =currentWeekDay.format(formatter);
            pathList.add(timedir);
        }
        return pathList;
    }
    public static List<String>  getLastWeek(){
        List<String> pathList = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate localDate = LocalDate.now();
        int weekDay = localDate.getDayOfWeek().getValue();
        LocalDate lastSunDate= localDate.minusDays(weekDay+6);//获取上周第一天
        for(int i=0;i<=7;i++){
            LocalDate currentWeekDay = lastSunDate.plusDays(i);
            String timedir =currentWeekDay.format(formatter);
            pathList.add(timedir);
        }
        return pathList;
    }
    /**
     * 处理上个月数据
     */
    public static List<String>  getMonth(){
        List<String> pathList = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM");
        LocalDate localDate = LocalDate.now();
        int monthDay = localDate.getDayOfMonth();
        if(monthDay == 1){
            // 取本月最后一天，再也不用计算是28，29，30还是31：
            LocalDate lastMonthDate = localDate.minusMonths(1); //获取上一个月
            String monthDir = lastMonthDate.format(formatter);
            pathList.add(monthDir);
        }
        return pathList;
    }

    /**
     *  y:2019;m:2019-02;d:2019-02-03;time:2019-03-04~2019-03-05
     //y:年份   yyyy
     //m:年份+月份  yyyy-MM
     //d:年份+月份+天 yyyy-MM-dd
     //周：当前周数的某个日期 yyyy-MM-dd
     //time：开始时间 和结束时间  yyyy-MM-dd,yyyy-MM-dd
     * @param time
     */
    public static void convertTime(String time){
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
            switch (k[0]){
                case "y":
                    if(k[1].matches("^20[0-9]{2}$")){    //验证月份
                        String path = k[1].replace("-","/");
                        System.out.println(path);
                        break;
                    }
                    System.err.println(ConfigEntity.TIME_PATTERN+"参数异常:"+time);
                    System.exit(1);
                case "m":
                    if(k[1].matches("^20[0-9]{2}-((0[1-9]{1})|(1[0-2]{1}))$")){    //验证月份
                       String path = k[1].replace("-","/");
                        System.out.println(path);
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
                        String path = localDate.getYear()+"/"+localDate.get(weekFields.weekOfYear()) ;
                        System.out.println(path);
                        break;
                    }
                    System.err.println(ConfigEntity.TIME_PATTERN+"参数异常:"+time);
                    System.exit(1);
                case "d":
                    if(k[1].matches("^20[0-9]{2}-((0[1-9]{1})|(1[0-2]{1}))-[0-9]{2}$")){
                        //验证日期
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                        LocalDate localDate =LocalDate.parse(k[1],formatter);
                        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                        String path = localDate.format(formatter1);
                        System.out.println(path);
                        break;
                    }
                    System.err.println(ConfigEntity.TIME_PATTERN+"参数异常:"+time);
                    System.exit(1);
                case "time":
                    if(k[1].matches("^20[0-9]{2}-((0[1-9]{1})|(1[0-2]{1}))-[0-9]{2}~20[0-9]{2}-((0[1-9]{1})|(1[0-2]{1}))-[0-9]{2}$")){
                        //验证日期
                        Set<String> pathSet = new HashSet<>();
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
                        for(long i=1;i<=days;i++){
                            LocalDate cur = startDate.plusDays(i);
                            if((cur.getMonth().getValue()==startDate.getMonth().getValue() && cur.getYear()==startDate.getYear())
                                    ||(cur.getMonth().getValue()==endDate.getMonth().getValue() && cur.getYear()==endDate.getYear())){
                                pathSet.add(cur.format(dayformatter));
                            }else{
                                pathSet.add(cur.format(monthformatter));
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

    public static void main(String[] args) {
        getCurrentWeek().forEach(System.out::println);
        System.out.println("========================");
        getLastWeek().forEach(System.out::println);
    }
}
