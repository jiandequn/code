package com.ppfuns;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ppfuns.report.entity.AppRetentionUserCountDay;
import com.ppfuns.report.utils.QueryConditionsUtils;
import net.sf.jsqlparser.expression.AnyType;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with IntelliJ IDEA.
 * User: 简德群
 * Date: 2020/3/30
 * Time: 12:25
 * To change this template use File | Settings | File Templates.
 */
public class Test {

    public static void main(String[] args) throws NoSuchFieldException {
       Map<String,String> map = new ConcurrentHashMap<>(10);
        System.out.println(map.toString());
        System.out.println(0x7fffffff);
        //        LocalDate date = LocalDate.parse("2020-01-01");
//        WeekFields weekFields = WeekFields.of(DayOfWeek.MONDAY,1);
//        LocalDate d= date.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)).plusDays(3);
//        System.out.println(d.get(weekFields.weekOfYear()));
//        System.out.println(d.getYear());
//        System.out.println(getWeek("2020-01-01"));
//        int[] d = getWeekAndYear("2021-05-09");
//        System.out.println(d[0]+"-"+d[1]);
//        d = getWeekAndYear("2021-01-01");
//        System.out.println(d[0]+"-"+d[1]);
//        d = getWeekAndYear("2020-01-01");
//        System.out.println(d[0]+"-"+d[1]);
    }
    public static int[] getWeekAndYear(String datestr){
        LocalDate date = LocalDate.parse(datestr);
        WeekFields weekFields = WeekFields.of(DayOfWeek.MONDAY,1);
        LocalDate d= date.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)).plusDays(3);
        LocalDate checkDate=LocalDate.of(d.getYear(),1,1).with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)).plusDays(3);
        return new int[]{d.getYear(),checkDate.get(weekFields.weekOfYear())==1?d.get(weekFields.weekOfYear()):d.get(weekFields.weekOfYear())-1};
    }
    public void tes(){
        final String a="a";
        final String b="sjofjsd";
        String c= a+b;
        System.out.println(c);
        String d="a2";
        String f="sjofjsd2";
        String e= d+f;
        System.out.println(c);
    }
}

