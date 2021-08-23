package com.jshy.mr.utils;

import com.jshy.mr.model.BaseTimeEntity;
import com.jshy.mr.model.StatisticalMethodEnum;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;

/**
 * Created with IntelliJ IDEA.
 * User: 简德群
 * Date: 2019/9/27
 * Time: 10:12
 * To change this template use File | Settings | File Templates.
 */
public class DateUtils {

    public final static DateTimeFormatter DATE_FORMATTER_SLANTINGBAR  = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    public final static DateTimeFormatter DATE_FORMATTER_HORIZONTAL  = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public final static DateTimeFormatter TIME_FORMATTER_NO_SIGN = DateTimeFormatter.ofPattern("yyyyMMddHHmmss"); //无标志
    private final static DateTimeFormatter formatter =
            DateTimeFormatter
                    .ofPattern("yyyy-MM-dd HH:mm:ss");
    public static int STATISTICAL_METHOD = StatisticalMethodEnum.WEEK.getId();
    /**
     * 得到的是周几
     * @param time
     * @return  周几
     */
    public static int dayOfWeek(String time){
        LocalDateTime parsed = LocalDateTime.parse(time, formatter);
        return parsed.getDayOfWeek().getValue();
    }

    /**
     * 获取当前年份的第几周（从星期一开始）
     * @param time
     * @return 当前年份的第几周
     */
    public static int weekOfYear(String time){
        LocalDateTime parsed = LocalDateTime.parse(time, formatter);
        //使用DateTimeFormatter获取当前周数
        WeekFields weekFields = WeekFields.of(DayOfWeek.MONDAY,1);
        return  parsed.get(weekFields.weekOfYear()) ;
    }

    /**
     * 获取月份
     * @param time
     * @return 月份
     */
    public static int month(String time){
        LocalDateTime parsed = LocalDateTime.parse(time, formatter);
        return parsed.getMonthValue();
    }
    /**
     * 获取月的第几天
     * @param time
     * @return 月份
     */
    public static int dayOfMonth(String time){
        LocalDateTime parsed = LocalDateTime.parse(time, formatter);
        return parsed.getDayOfMonth();
    }
    /**
     * 获取季度
     * @param time
     * @return  季度
     */
    public static int quarter(String time){
        LocalDateTime parsed = LocalDateTime.parse(time, formatter);
        return parsed.getMonthValue()/4+1;
    }
    /**
     * 获取年份
     * @param time
     * @return 年份
     */
    public static int year(String time){
        LocalDateTime parsed = LocalDateTime.parse(time, formatter);
        return parsed.getYear();
    }
    public static void parseTimeBase(BaseTimeEntity timeBaseEntity,String createTime){
        LocalDateTime parsed = LocalDateTime.parse(createTime, formatter);
        if(STATISTICAL_METHOD == StatisticalMethodEnum.WEEK.getId()){
            parsed = parsed.minusDays(parsed.getDayOfWeek().getValue()-1);
            timeBaseEntity.setYear(parsed.getYear());
            WeekFields weekFields = WeekFields.of(DayOfWeek.MONDAY,1);
            timeBaseEntity.setWeek(parsed.get(weekFields.weekOfYear()));
        }else if(STATISTICAL_METHOD == StatisticalMethodEnum.DAY.getId()) {
            timeBaseEntity.setYear(parsed.getYear());
            timeBaseEntity.setMonth(parsed.getMonthValue());
            timeBaseEntity.setDay(parsed.getDayOfMonth());
        }else if(STATISTICAL_METHOD == StatisticalMethodEnum.YEAR.getId()) {
            timeBaseEntity.setYear(parsed.getYear());
        }else if(STATISTICAL_METHOD == StatisticalMethodEnum.QUARTER.getId()) {
            timeBaseEntity.setYear(parsed.getYear());
            timeBaseEntity.setQuarter(parsed.getMonthValue()/4+1);
        }else if(STATISTICAL_METHOD == StatisticalMethodEnum.MONTH.getId()) {
            timeBaseEntity.setYear(parsed.getYear());
            timeBaseEntity.setMonth(parsed.getMonthValue());
        } else if(STATISTICAL_METHOD == StatisticalMethodEnum.NO_TIME.getId()) {

        }else{
            System.out.println("不存在定义的统计方式");
        }
    }
    public static void main(String[] args) {
        LocalDateTime parsed = LocalDateTime.parse("2020-01-06 00:00:00", formatter);
        LocalDateTime t = parsed.minusDays(parsed.getDayOfWeek().getValue()-1) ;
        System.out.println(parsed.getDayOfWeek().getValue());
        System.out.println(parsed.getDayOfYear()/7);
        System.out.println(month("2019-09-27 00:00:00"));
        System.out.println(quarter("2019-04-27 00:00:00"));
        System.out.println(weekOfYear("2019-12-31 00:00:00"));
    }
}
