package com.ppfuns.report.utils;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.Calendar;
import java.util.StringJoiner;

/**
 * Created by Administrator on 2020/9/24.
 */
public class DateUtils {

    public static LocalDate getSun(String date){
        LocalDate sDate = LocalDate.parse(date);
        LocalDate sunDay =sDate.plusDays(7-sDate.getDayOfWeek().getValue());
        return sunDay;
    }
    public static int[] getWeekAndYear(String datestr){
        LocalDate date = LocalDate.parse(datestr);
        WeekFields weekFields = WeekFields.of(DayOfWeek.MONDAY,1);
        LocalDate d= date.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)).plusDays(3);
        LocalDate checkDate=LocalDate.of(d.getYear(),1,1).with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)).plusDays(3);
        return new int[]{d.getYear(),checkDate.get(weekFields.weekOfYear())==1?d.get(weekFields.weekOfYear()):d.get(weekFields.weekOfYear())-1};
    }
    public static LocalDate getMondayByWEEKOFYEAR(String week){
        String[] arr= week.split("-");
        int y=Integer.valueOf(arr[0]);
        int w=Integer.valueOf(arr[1]);
        WeekFields weekFields = WeekFields.of(DayOfWeek.MONDAY,1);
        LocalDate checkDate=LocalDate.of(y,1,4).with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        System.out.println(checkDate.get(weekFields.weekOfYear()));
        int cur = checkDate.get(weekFields.weekOfYear())-1;
        LocalDate localDate = checkDate.plusWeeks(w - cur);
        return localDate;
    }
    public static String getYesterday(){
        LocalDate date = LocalDate.now();
        LocalDate yesterday =date.plusDays(-1);
        return yesterday.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public static void main(String[] args) {
        int[] st= getWeekAndYear("2021-07-15");
        System.out.println(st[1]);
        System.out.println();
    }
}
