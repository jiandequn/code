package com.ppfuns.report.utils;

import com.ppfuns.report.entity.base.CountBean;
import com.ppfuns.report.entity.base.SerieBean;
import net.sf.ehcache.search.aggregator.Count;
import org.apache.commons.collections.CollectionUtils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

public class ChartUtils {
    static Map<String,Map<String,String>> countTypeMap = new HashMap<>();
    static Map<String,CountBean> countBeanMap = new HashMap<>();
    static{
        Map<String,String> userMap = new HashMap<>();
        userMap.put("text","用户数");
        userMap.put("format","{value} ".concat("人"));
        Map<String,String> durationMap = new HashMap<>();
        durationMap.put("text","播放时长");
        durationMap.put("format","{value} ".concat("时"));
        Map<String,String> playCountMap = new HashMap<>();
        playCountMap.put("text","播放次数");
        playCountMap.put("format","{value} ".concat("次"));
        Map<String,String> ratioMap = new HashMap<>();
        ratioMap.put("text","占比");
        ratioMap.put("format","{value} ".concat("%"));
        Map<String,String> preUserPlayCountMap = new HashMap<>();
        preUserPlayCountMap.put("text","人均点播次数");
        preUserPlayCountMap.put("format","{value} ".concat("次"));
        Map<String,String> visitCountCountMap = new HashMap<>();
        preUserPlayCountMap.put("text","访问次数");
        visitCountCountMap.put("format","{value} ".concat("次"));
        Map<String,String> countMap = new HashMap<>();
        countMap.put("text","数量");
        countMap.put("format","{value} ".concat("数量"));
        countTypeMap.put("user",userMap);
        countTypeMap.put("duration",durationMap);
        countTypeMap.put("playCount",playCountMap);
        countTypeMap.put("ratio",ratioMap);
        countTypeMap.put("preUserPlayCount",preUserPlayCountMap);
        countTypeMap.put("visitCount",visitCountCountMap);
        countTypeMap.put("count",countMap);

        countBeanMap.put("userCount",new CountBean("用户数","userCount","user","人","用户数"));
        countBeanMap.put("visitUserCount",new CountBean("访问用户数","visitUserCount","user","人","用户数"));
        countBeanMap.put("pageUserCount",new CountBean("访问用户数","pageUserCount","user","人","用户数"));
        countBeanMap.put("playUserCount",new CountBean("播放用户数","playUserCount","user","人","用户数"));
        countBeanMap.put("playCount",new CountBean("点播次数","playCount","playCount","次","播放次数"));
        countBeanMap.put("duration",new CountBean("播放时长","duration","duration","时","播放时长"){
            public Object parseVal(Object val) {
                if(val == null)return 0;
                return BigDecimal.valueOf((Long) val).divide(BigDecimal.valueOf(3600),2,1);
            }
        });
        countBeanMap.put("addUserCount",new CountBean("新增用户数","addUserCount","user","人","用户数"));
        countBeanMap.put("totalVisitUserCount",new CountBean("累计访问用户数","totalVisitUserCount","user","人","用户数"));
        countBeanMap.put("visitCount",new CountBean("访问次数","visitCount","visitCount","次","访问次数"));
        countBeanMap.put("user2dayCount",new CountBean("2日留存","user2dayCount","user","人","用户数"));
        countBeanMap.put("user3dayCount",new CountBean("3日留存","user3dayCount","user","人","用户数"));
        countBeanMap.put("addUser2dayCount",new CountBean("2日新增留存","addUser2dayCount","user","人","用户数"));
        countBeanMap.put("perUserPlayCount",new CountBean("人均点播次数","perUserPlayCount","preUserPlayCount","次数","人均点播次数"));
        countBeanMap.put("ratio",new CountBean("同比","ratio","ratio","%","同比"));
    }
    private static List<Field> getFields(Class clazz, List<String> list){
        return list.stream().map(s -> {
            try {
                Field declaredField = clazz.getDeclaredField(s);
                declaredField.setAccessible(true);
                return declaredField;
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
            return null;
        }).collect(Collectors.toList());
    }
    private static Map<String,Integer> getYAxisSortNum(List<String> countFields, Map<String, CountBean> countBeanMap){
        Map<String,Integer> numflag = new LinkedHashMap<>();
        Integer yAxis=0;
        for(String s : countFields){
            if(!numflag.containsKey(countBeanMap.get(s).getType())){
                numflag.put(countBeanMap.get(s).getType(),yAxis++);
            }
        }
        return numflag;
    }
    public static Map<String,Object> getChart(List beanList,List<String> cateFields,List<String> countFields,List<String> fieldTypeList,Map<String,CountBean> countBeanMap){
        Map<String,Object> result = new HashMap<>();
        List categories = new ArrayList<>();
        List series = new ArrayList<>();
        if(CollectionUtils.isEmpty(beanList)){
            return result;
        }

        //自定义Y轴的显示数据顺序
        Map<String,Integer> numflag = getYAxisSortNum(countFields,countBeanMap);
        Map<String, SerieBean> c = new HashMap<>();
        //初始化Series格式
        for(int i=0;i<countFields.size();i++){
            List dataList = new ArrayList();
            CountBean countBean = countBeanMap.get(countFields.get(i));
            int yAxis=numflag.keySet().size()==1?0:numflag.get(countBean.getType());//如果只有一种显示数据类型就不用在serie中显示yAxis
            String type=SerieBean.COLUMN;
            if(!CollectionUtils.isEmpty(fieldTypeList)){
                type= fieldTypeList.size()>i?fieldTypeList.get(i):SerieBean.SPLINE;
            }
            SerieBean serieBean = new SerieBean(countBean.getName(),type , yAxis, countBean.getFormat(), dataList);
            c.put(countBean.getValue(),serieBean);
        }

        if(beanList.get(0) instanceof Map){
            for (Object o : beanList) {
            }
            beanList.forEach(s->{
                Map<String,Object> map = (Map<String, Object>) s;
                StringJoiner cateStr= new StringJoiner("-");
                cateFields.forEach(t->{
                    cateStr.add(map.get(t).toString());
                });
                categories.add(cateStr.toString());
                countFields.forEach(t->{
                    Object o = map.get(t);
                    c.get(t).getData().add(countBeanMap.get(t).parseVal(o));
                });
            });
        }else{
            Class clazz = beanList.get(0).getClass();
            List<Field> cateFieldList = getFields(clazz,cateFields);
            List<Field> countFieldList = getFields(clazz,countFields);
            //给Series填充数据
            beanList.forEach(s->{
                StringJoiner cateStr= new StringJoiner("-");
                cateFieldList.forEach(t->{
                    try {
                        cateStr.add(t.get(s).toString());
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                });
                categories.add(cateStr.toString());
                countFieldList.forEach(t->{
                    try {
                        Object o = t.get(s);
                        //进行值转换
                        c.get(t.getName()).getData().add(countBeanMap.get(t.getName()).parseVal(o));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                });
            });
        }

        c.forEach((k,v)->{
            series.add(v.getSerieMap());
        });
        result.put("series",series);
        result.put("categories",categories);
        result.put("yAxis",parseYAxis(numflag));
        return result;
    }

    public static Map<String,Object> getChart(List beanList,List<String> cateFields,List<String> countFields,List<String> fieldTypeList){
        return getChart(beanList,cateFields,countFields,fieldTypeList,countBeanMap);
    }
    @Deprecated
    public static Map<String,Object> getChart2(List beanList,List<String> cateFields,List<String> countFields,List<String> fieldTypeList){
        Map<String,Object> result = new HashMap<>();
        List categories = new ArrayList<>();
        List series = new ArrayList<>();
        if(CollectionUtils.isEmpty(beanList)){
            return result;
        }
        Class clazz = beanList.get(0).getClass();
        List<Field> cateFieldList = getFields(clazz,cateFields);
        List<Field> countFieldList = getFields(clazz,countFields);
        //自定义Y轴的显示数据顺序
        Map<String,Integer> numflag = getYAxisSortNum(countFields,countBeanMap);
        Map<String, SerieBean> c = new HashMap<>();
        //初始化Series格式
        for(int i=0;i<countFields.size();i++){
            List dataList = new ArrayList();
            CountBean countBean = countBeanMap.get(countFields.get(i));
            int yAxis=numflag.keySet().size()==1?0:numflag.get(countBean.getType());//如果只有一种显示数据类型就不用在serie中显示yAxis
            String type=SerieBean.COLUMN;
            if(!CollectionUtils.isEmpty(fieldTypeList)){
                type= fieldTypeList.size()>i?fieldTypeList.get(i):SerieBean.SPLINE;
            }
            SerieBean serieBean = new SerieBean(countBean.getName(),type , yAxis, countBean.getFormat(), dataList);
            c.put(countBean.getValue(),serieBean);
        }
        //给Series填充数据
        beanList.forEach(s->{
            StringJoiner cateStr= new StringJoiner("-");
            cateFieldList.forEach(t->{
                try {
                    cateStr.add(t.get(s).toString());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            });
            categories.add(cateStr.toString());
            countFieldList.forEach(t->{
                try {
                    Object o = t.get(s);
                    //进行值转换
                    c.get(t.getName()).getData().add(countBeanMap.get(t.getName()).parseVal(o));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            });
        });

        c.forEach((k,v)->{
            series.add(v.getSerieMap());
        });
        result.put("series",series);
        result.put("categories",categories);
        result.put("yAxis",parseYAxis(numflag));
        return result;
    }
    public static Object parseYAxis(Map<String,Integer> flag){
        if(flag.keySet().size()==1){
            Map<String,Object> yAixsMap = new HashMap<>();
            yAixsMap.put("min",0);
            Map<String,String> titleMap = new HashMap<>();
            String k = flag.keySet().iterator().next();
            titleMap.put("text",countTypeMap.get(k).get("text"));
            yAixsMap.put("title",titleMap);
            Map<String,Object> labelMap = new HashMap<>();
            labelMap.put("format",countTypeMap.get(k).get("format"));
            yAixsMap.put("labels",labelMap);
            return yAixsMap;
        }
        List<Map<String,Object>> yAxisList = new ArrayList<>();
        if(flag.keySet().size()>1){
            flag.forEach((k,v)->{
                Map<String,Object> yAixsMap = new HashMap<>();
                yAixsMap.put("gridLineWidth",0);
                Map<String,String> titleMap = new HashMap<>();
                titleMap.put("text",countTypeMap.get(k).get("text"));
//            Map<String,String> styleMap = new HashMap<>();
//            styleMap.put("color","#434348");
//            titleMap.put("style",styleMap);
                yAixsMap.put("title",titleMap);
                Map<String,Object> labelMap = new HashMap<>();
                labelMap.put("format",countTypeMap.get(k).get("format"));
                yAixsMap.put("labels",labelMap);
                yAixsMap.put("opposite",v%2==0?true:false);//Y轴的各个类型数据展示
                yAxisList.add(yAixsMap);
            });
        }
        return yAxisList;
    }
}
