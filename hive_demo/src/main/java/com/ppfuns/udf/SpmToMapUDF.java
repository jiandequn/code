package com.ppfuns.udf;

import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorConverters;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;

import java.util.LinkedHashMap;

/**
 * Created by Administrator on 2020/4/28.
 */
@Description(
        name = "spm_to_map",
        value = "_FUNC_(nowSpm, afterSpm) - Creates a map by parsing aferSpm and nowSpm text ;return map('before_column_id':val,'':before_column_code,'stay_duration':val3)",
        extended = "函数利用对两个spm拆分，然后对时间点取差集"
)
public class SpmToMapUDF extends GenericUDF {
    private final LinkedHashMap<Object, Object> ret = new LinkedHashMap();
    private transient ObjectInspectorConverters.Converter soi_text;
    private transient ObjectInspectorConverters.Converter soi_text2;
    static final String default_split = "\\.";
    @Override
    public ObjectInspector initialize(ObjectInspector[] objectInspectors) throws UDFArgumentException {
        this.soi_text = ObjectInspectorConverters.getConverter(objectInspectors[0], PrimitiveObjectInspectorFactory.javaStringObjectInspector);
        this.soi_text2 = ObjectInspectorConverters.getConverter(objectInspectors[1], PrimitiveObjectInspectorFactory.javaStringObjectInspector);
       return  ObjectInspectorFactory.getStandardMapObjectInspector(PrimitiveObjectInspectorFactory.javaStringObjectInspector, PrimitiveObjectInspectorFactory.javaStringObjectInspector);
    }

    @Override
    public Object evaluate(DeferredObject[] deferredObjects) throws HiveException {
        this.ret.clear();
        String nowSpm = (String)this.soi_text.convert(deferredObjects[0].get());
        String afterSpm = (String)this.soi_text2.convert(deferredObjects[1].get());
        if(nowSpm == null || afterSpm == null) {
            return this.ret;
        } else {
            String[] nowArr = nowSpm.split(default_split);
            String[] afterArr = afterSpm.split(default_split);
            if(nowArr.length<5 || afterArr.length<5) return this.ret;
            if(nowArr[4].length() == 13 && afterArr[4].length()==13){
                long timeStamp = Long.parseLong(nowArr[4])-Long.parseLong(afterArr[4]);
                ret.put("stay_duration",timeStamp);
            }


            ret.put("before_column_id",afterArr[2]);
            ret.put("before_column_code",afterArr[1]);
            return this.ret;
        }
    }

    @Override
    public String getDisplayString(String[] children) {
        assert children.length <= 2;
        return this.getStandardDisplayString("spm_to_map", children, ",");
    }
}
