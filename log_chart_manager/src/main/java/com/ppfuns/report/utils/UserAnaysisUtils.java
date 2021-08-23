package com.ppfuns.report.utils;

import com.ppfuns.report.entity.CollectBean;
import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class UserAnaysisUtils {
    public static List<CollectBean> getUserAnaysisList(List<String> lines, String uniqueSelect){
        Map<CollectBean, List<Map<String, String>>> userMap = new HashMap<>();
        lines.forEach(line -> {
            if (line.contains("eventsType=") && line.contains("createTime=")) {
                line = line.replace("\u001B[01;31m\u001B[K", "").replace("\u001B[m\u001B[K", "");
                Map<String, String> content = formatMap(line);
                CollectBean collectBean = new CollectBean(content.get(uniqueSelect), content.get("userType"), content.get("parentColumnId"), content.get("area"));
                if (userMap.containsKey(collectBean)) {
                    userMap.get(collectBean).add(content);
                } else {
                    List<Map<String, String>> list = new ArrayList<>();
                    list.add(content);
                    userMap.put(collectBean, list);
                }
            }
        });
        List<CollectBean> collectBeanList = getCollectBeanList(userMap);
//        collectBeanList.forEach(s->{
//        });
        return collectBeanList;
    }
    private static Map<String, String> formatMap(String line) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Map<String, String> map = new LinkedHashMap<String, String>();
        for (String kv : line.split(";")) {
            String[] t = kv.split("=", 0);
            map.put(t[0], t[1]);
        }
        if(map.containsKey("mac")){
            map.put("mac", StringUtils.upperCase(StringUtils.removeAll(map.get("mac"),"^:|-$")));
        }
        if (map.containsKey("nowSpm")) {
            String[] spm = map.get("nowSpm").split("\\.");
            if (spm.length >= 5) {
                map.put("seq", spm[4]);
            } else {
                try {
                    map.put("seq", String.valueOf(dateFormat.parse(map.get("createTime"))));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            switch (map.get("eventsType")) {
                case "AUTH_PRODUCT":
                    map.put("seq", "4");
                    break;
                case "COLUMN_TYPE_HISTORY_POINT":
                    if (map.containsKey("operateType")) {
                        if ("add".equals(map.get("operateType"))) {
                            if (map.get("timePosition") == "0") map.put("seq", "6");
                            else {
                                map.put("seq", "1");
                            } // # timePosition 非0
                        } else {
                            map.put("seq", "5");
                        } // # operateType=get
                    } else {
                        try {
                            map.put("seq", String.valueOf(dateFormat.parse(map.get("createTime"))));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                default:
                    try {
                        map.put("seq", String.valueOf(dateFormat.parse(map.get("createTime"))));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
            }
        }
        return map;
    }
    private static List<CollectBean> getCollectBeanList(Map<CollectBean, List<Map<String, String>>> userMap) {
        List<CollectBean> collectBeans = new ArrayList<>();
        userMap.forEach((k, v) -> {
            //对v进行重排序
            Map<String, List<Map<String, String>>> timeMap = v.stream().collect(Collectors.groupingBy(s -> s.get("createTime")));
            List<String> timeSortList = timeMap.keySet().stream().sorted().collect(Collectors.toList());
            k.setTimeSortList(timeSortList);
            //对map进行排序
            timeMap.forEach((k1, v1) -> {
                Collections.sort(v1, new Comparator<Map<String, String>>() {
                    public int compare(Map<String, String> o1, Map<String, String> o2) {
                        String sort1 = o1.get("seq");
                        String sort2 = o2.get("seq");
                        return sort1.compareTo(sort2);
                    }
                });
                k.addSortTimeMap(k1, v1);
            });
            collectBeans.add(k);
        });
        return collectBeans;
    }

    private static void validate(List<CollectBean> collectBeans){
        collectBeans.forEach(s->{
            s.getTimeSortList().forEach(t->{
                List<Map<String, String>> list = s.getSortTimeMap().get(t);
                list.forEach(a->{
                    if(a.containsKey("afterSpm") || a.containsKey("nowSpm")){

                    }
                });
            });
        });
    }
}
