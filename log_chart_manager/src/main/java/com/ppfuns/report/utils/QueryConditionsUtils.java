package com.ppfuns.report.utils;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.StringJoiner;
import java.util.stream.Stream;

public class QueryConditionsUtils<T> {
    public void formatWeek(QueryWrapper<T> qw, String startDate, String endDate){
        if(!StringUtils.isEmpty(startDate) && StringUtils.isEmpty(endDate)){
            int[] yw=DateUtils.getWeekAndYear(startDate);
            qw.ge("y",yw[0]);
            qw.ge("w",yw[1]);
        }
        if(StringUtils.isEmpty(startDate) && !StringUtils.isEmpty(endDate)){
            int[] yw=DateUtils.getWeekAndYear(endDate);
            qw.ge("y",yw[0]);
            qw.ge("w",yw[1]);
        }
        if(!StringUtils.isEmpty(startDate) && !StringUtils.isEmpty(endDate)){
            int[] syw=DateUtils.getWeekAndYear(startDate);
            int[] eyw=DateUtils.getWeekAndYear(endDate);
            if(syw[0]==eyw[0]){
                qw.eq("y",syw[0]);
                        qw.ge("w",syw[1]);
                        qw.le("w",eyw[1]);
            }else{
                qw.and(w->w.and(w1->w1.eq("y",syw[0]).ge("w",syw[1])).or(w1->w1.gt("y",syw[0])));
                qw.and(w->w.and(w1->w1.eq("y",eyw[0]).le("w",eyw[1])).or(w1->w1.lt("y",eyw[0])));
            }
        }

    }


    public void formatMonth(QueryWrapper<T> qw, String dateMonth){
        if(!StringUtils.isEmpty(dateMonth)){
            String[] td =dateMonth.split(" ");
            String startMonth = td[0];
            String endMonth = td[2];
            String[] sm = startMonth.split("-");
            String[] em = endMonth.split("-");
            if(sm[0].equals(em[0])){
                qw.eq("y",sm[0]).ge("m",sm[1]).le("m",em[1]);
            }else{
                qw.and(w->w.and(w1->w1.eq("y",sm[0]).ge("m",sm[1])).or(w1->w1.gt("y",sm[0])));
                qw.and(w->w.and(w1->w1.eq("y",em[0]).le("m",em[1])).or(w1->w1.lt("y",em[0])));
            }
        }
    }
    /**
     * sql排序转换
     * @param qw
     * @param orderby
     * @param alisTable
     */
    public static String formatOrderBy(QueryWrapper qw,String orderby,String alisTable){
        StringJoiner sj = new StringJoiner(",");
        if(!StringUtils.isEmpty(orderby)){
            Stream.of(orderby.split(",")).forEach(s->{
                String tField = StringUtils.isEmpty(alisTable)?humpToLine(s.split(" |:")[0]):alisTable.concat(".").concat(humpToLine(s.split(" ")[0]));
                if(StringUtils.endsWithIgnoreCase(s,"desc")){
                    if(qw!=null){
                        qw.orderByDesc(tField);
                    }
                    sj.add(tField+" desc");
                }else if(StringUtils.endsWithIgnoreCase(s,"asc")){
                    if(qw!=null){
                        qw.orderByAsc(tField);
                    }
                    sj.add(tField+" asc");
                }
            });
        }
        return sj.toString();
    }
    public static void formatOrderByWithClazz(QueryWrapper qw,String orderby,Class clazz,String alisTable){
        if(clazz ==null){
            formatOrderBy(qw,orderby,alisTable);
        }else{
            if(StringUtils.isEmpty(orderby)){
                return;
            }
            Stream.of(orderby.split(",")).forEach(s->{
                String[] td=s.split(" |:");
                Field declaredField = null;
                String tField=null;
                try {
                    declaredField = clazz.getDeclaredField(td[0]);
                    if(declaredField != null){
                        TableField annotation = declaredField.getAnnotation(TableField.class);
                        if(annotation != null){
                            if(!StringUtils.isEmpty(annotation.value())){
                                tField = annotation.value();
                            }
                        }
                    }
                    if(StringUtils.isEmpty(tField)){
                        tField = humpToLine(td[0]);
                    }
                    tField = StringUtils.isEmpty(alisTable)?tField:alisTable.concat(".").concat(tField);
                    if("desc".equalsIgnoreCase(td[1])){
                        qw.orderByDesc(tField);
                    }else if("asc".equalsIgnoreCase(td[1])){
                        qw.orderByAsc(tField);
                    }
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }
            });
        }
    }
    /***
     * 驼峰转下划线(简单写法
     *
     **/
    public static String humpToLine(String javaClassField){
        return javaClassField.replaceAll("[A-Z]", "_$0").toLowerCase();
//        Matcher matcher = humpPattern.matcher(str);
//        StringBuffer sb = new StringBuffer();
//        while (matcher.find()) {
//            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
//        }
//        matcher.appendTail(sb);
//        return sb.toString();
    }

}
