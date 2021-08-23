package com.ppfuns.udtf;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDTF;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.PrimitiveObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.StructObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 首页
 * Created with IntelliJ IDEA.
 * User: 简德群
 * Date: 2020/2/26
 * Time: 9:37
 * To change this template use File | Settings | File Templates.
 */
public class FirstPageUDTF extends GenericUDTF {
    private static final Logger LOG = LoggerFactory.getLogger(FirstPageUDTF.class);
    private PrimitiveObjectInspector stringOI = null;
    private static String[] fieldNameArr = {"parentColumnId","columnId","afterColumnId","afterColumnCode","areaCode"};
    private final static String PARAM_SPLIT_SIGN = ";"; //参数拆分
    private final static String KEY_VALUE_SPLIT_SIGN = "="; //参数拆分
    @Override
    public StructObjectInspector initialize(ObjectInspector[] objectInspectors) throws UDFArgumentException {

        // 异常检测
        if (objectInspectors.length != 1) {
            throw new UDFArgumentException("AblumPlayUDTF() takes exactly one argument");
        }

        if(objectInspectors[0].getCategory()!=ObjectInspector.Category.PRIMITIVE&&((PrimitiveObjectInspector) objectInspectors[0]).getPrimitiveCategory() != PrimitiveObjectInspector.PrimitiveCategory.STRING) {
            throw new UDFArgumentException("NameParserGenericUDTF() takes a string as a parameter");
        }

        //输入
        stringOI = (PrimitiveObjectInspector) objectInspectors[0];

        // 输出
        List<String> fieldNames = new ArrayList<>();
        List<ObjectInspector> fieldOIs = new ArrayList<>();
        // 输出列名
        fieldNames.add("parentColumnId");
        fieldOIs.add(PrimitiveObjectInspectorFactory.javaStringObjectInspector);
        fieldNames.add("columnId");
        fieldOIs.add(PrimitiveObjectInspectorFactory.javaStringObjectInspector);
        fieldNames.add("afterColumnId");
        fieldOIs.add(PrimitiveObjectInspectorFactory.javaStringObjectInspector);
        fieldNames.add("afterColumnCode");
        fieldOIs.add(PrimitiveObjectInspectorFactory.javaStringObjectInspector);
        fieldNames.add("areaCode");
        fieldOIs.add(PrimitiveObjectInspectorFactory.javaStringObjectInspector);
        return ObjectInspectorFactory.getStandardStructObjectInspector(fieldNames, fieldOIs);
    }
    @Override
    public void process(Object[] record) throws HiveException {
        final String feature = stringOI.getPrimitiveJavaObject(record[0]).toString();
        Map<String,Object> map = getParamMap(feature);
        List<Object> list = new ArrayList();
        for(String key: fieldNameArr){
            list.add(map.get(key));
        }
        forward(list);
    }
    private Map<String,Object> getParamMap(String feature){
        Map<String,Object> map = new HashMap<>();
        String[] arr = feature.split(PARAM_SPLIT_SIGN);
        if(StringUtils.isNotEmpty(feature)){
            for(String a : arr){
                String[] arr1 = a.split(KEY_VALUE_SPLIT_SIGN,2);
                if("afterSpm".equals(arr1[0])){
                    String[] ns = arr1[1].split("\\.");
                    if(ns.length>1)  map.put("afterColumnCode",ns[1]);
                    if(ns.length>2) map.put("afterColumnId",ns[2]);
                    if(ns.length>3) map.put("contentId",ns[3]);
                    if(ns.length>4) map.put("timeStamp", ns[4]);
                    if(ns.length>5) map.put("areaCode",ns[5]);
                    continue;
                }
                map.put(arr1[0],arr1[1]);
            }
        }
        return map;
    }
    private void initSpm(String spm){
        if(StringUtils.isNotEmpty(spm)){

        }
    }
    @Override
    public void close() throws HiveException {

    }
}
