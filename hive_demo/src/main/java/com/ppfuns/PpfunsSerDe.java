package com.ppfuns;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hive.common.type.*;
import org.apache.hadoop.hive.serde2.AbstractSerDe;
import org.apache.hadoop.hive.serde2.SerDeException;
import org.apache.hadoop.hive.serde2.SerDeSpec;
import org.apache.hadoop.hive.serde2.SerDeStats;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.StructObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.AbstractPrimitiveJavaObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.typeinfo.*;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 实现对自定义数据行的解析成表结构
 * Created with IntelliJ IDEA.
 * User: 简德群
 * Date: 2020/2/27
 * Time: 14:24
 * To change this template use File | Settings | File Templates.
 */
@SerDeSpec(
        schemaProps = {"columns", "columns.types"}
)
public class PpfunsSerDe extends AbstractSerDe {
    public static final Logger LOG = LoggerFactory.getLogger(PpfunsSerDe.class);
    public static final String INPUT_REGEX = "^.*- Collection events:(.*):END$";
    public static final String INPUT_REGEX_KEY_VALUE = "=";
    public static final String INPUT_SPLIT = ";";
    Pattern inputPattern;
    StructObjectInspector rowOI;
    List<Object> row;
    List<TypeInfo> columnTypes;
    Object[] outputFields;
    Text outputRowText;
    boolean alreadyLoggedNoMatch = false;
    boolean alreadyLoggedPartialMatch = false;
    long unmatchedRowsCount = 0L;
    long partialMatchedRowsCount = 0L;
    Pattern inputPatternKeyVlaue;
    List<String> columnNames;

    public PpfunsSerDe() {
    }
    public void init(){
        this.inputPattern = Pattern.compile(INPUT_REGEX, 32 + (false ? 2 : 0));
        this.inputPatternKeyVlaue = Pattern.compile(INPUT_REGEX_KEY_VALUE, 32 + (false ? 2 : 0));
    }
    public void initialize(Configuration conf, Properties tbl) throws SerDeException {
        String columnNameProperty = tbl.getProperty("columns");
        String columnTypeProperty = tbl.getProperty("columns.types");
        LOG.warn("元素columns=="+columnNameProperty);   //不区分大小写
        LOG.warn("元素columns.types=="+columnTypeProperty);
        boolean inputRegexIgnoreCase = "true".equalsIgnoreCase(tbl.getProperty("input.regex.case.insensitive"));
        this.inputPattern = Pattern.compile(INPUT_REGEX, 32 + (inputRegexIgnoreCase ? 2 : 0));
        this.inputPatternKeyVlaue = Pattern.compile(INPUT_REGEX_KEY_VALUE, 32 + (inputRegexIgnoreCase ? 2 : 0));
        String columnNameDelimiter = tbl.containsKey("column.name.delimiter") ? tbl.getProperty("column.name.delimiter") : String.valueOf(',');
        columnNames = Arrays.asList(columnNameProperty.split(columnNameDelimiter));
        columnNames.forEach(s->{
            String[] ar = s.split("_");
            s = ar[0];
            for(int i=1;i<ar.length;i++){
                s = s+ar[i];
            }
        });
        this.columnTypes = TypeInfoUtils.getTypeInfosFromTypeString(columnTypeProperty);
        assert columnNames.size() == this.columnTypes.size();
        int numColumns = columnNames.size();
        ArrayList columnOIs = new ArrayList(columnNames.size());
        int c;
        for (c = 0; c < numColumns; ++c) {
            TypeInfo typeInfo = (TypeInfo) this.columnTypes.get(c);
            if (!(typeInfo instanceof PrimitiveTypeInfo)) {
                throw new SerDeException(this.getClass().getName() + " doesn\'t allow column [" + c + "] named " + (String) columnNames.get(c) + " with type " + this.columnTypes.get(c));
            }
            PrimitiveTypeInfo pti = (PrimitiveTypeInfo) this.columnTypes.get(c);
            AbstractPrimitiveJavaObjectInspector oi = PrimitiveObjectInspectorFactory.getPrimitiveJavaObjectInspector(pti);
            columnOIs.add(oi);
        }
        this.rowOI = ObjectInspectorFactory.getStandardStructObjectInspector(columnNames, columnOIs, Lists.newArrayList(Splitter.on('\u0000').split(tbl.getProperty("columns.comments"))));
        this.row = new ArrayList(numColumns);

        for (c = 0; c < numColumns; ++c) {
            this.row.add((Object) null);
        }

        this.outputFields = new Object[numColumns];
        this.outputRowText = new Text();

    }

    public ObjectInspector getObjectInspector() throws SerDeException {
        return this.rowOI;
    }

    public Class<? extends Writable> getSerializedClass() {
        return Text.class;
    }

    private void setRow(int c, String t) {
        TypeInfo typeInfo = (TypeInfo) this.columnTypes.get(c);
        PrimitiveTypeInfo pti = (PrimitiveTypeInfo) typeInfo;
        switch (pti.getPrimitiveCategory()) {
            case STRING:
                row.set(c, t);
                break;
            case BYTE:
                Byte b;
                b = Byte.valueOf(t);
                row.set(c,b);
                break;
            case SHORT:
                Short s;
                s = Short.valueOf(t);
                row.set(c,s);
                break;
            case INT:
                Integer i;
                i = Integer.valueOf(t);
                row.set(c, i);
                break;
            case LONG:
                Long l;
                l = Long.valueOf(t);
                row.set(c, l);
                break;
            case FLOAT:
                Float f;
                f = Float.valueOf(t);
                row.set(c,f);
                break;
            case DOUBLE:
                Double d;
                d = Double.valueOf(t);
                row.set(c,d);
                break;
            case BOOLEAN:
                Boolean bool;
                bool = Boolean.valueOf(t);
                row.set(c, bool);
                break;
            case TIMESTAMP:
                Timestamp ts;
                ts = Timestamp.valueOf(t);
                row.set(c, ts);
                break;
            case DATE:
                Date date;
                date = Date.valueOf(t);
                row.set(c, date);
                break;
            case DECIMAL:
                HiveDecimal bd = HiveDecimal.create(t);
                row.set(c, bd);
                break;
            case CHAR:
                HiveChar hc = new HiveChar(t, ((CharTypeInfo) typeInfo).getLength());
                row.set(c, hc);
                break;
            case VARCHAR:
                HiveVarchar hv = new HiveVarchar(t, ((VarcharTypeInfo)typeInfo).getLength());
                row.set(c, hv);
                break;
            default:
                try {
                    throw new SerDeException("Unsupported type " + typeInfo);
                } catch (SerDeException e1) {
                    ++this.partialMatchedRowsCount;
                    if (!this.alreadyLoggedPartialMatch) {
                        LOG.warn("" + this.partialMatchedRowsCount + " partially unmatched rows are found,  cannot find group " + c + ": " + t);
                        this.alreadyLoggedPartialMatch = true;
                    }

                    this.row.set(c, (Object) null);
                }
        }
    }

    public Object deserialize(Writable blob) throws SerDeException {
        Text rowText = (Text) blob;
        for (int i = 0; i < columnNames.size(); i++) {  //把数组置空
            this.row.set(i,null);
        }
        ++this.unmatchedRowsCount;
        Matcher m = this.inputPattern.matcher(rowText.toString());
        if(m.groupCount() != 1){
            throw new SerDeException("Number of matching groups doesn\'t match the number of columns");
        }else if (!m.matches()){
            if (!this.alreadyLoggedNoMatch) {
                LOG.warn("" + this.unmatchedRowsCount + " unmatched rows are found: " + rowText);
                this.alreadyLoggedNoMatch = true;
            }
            return null;
        }else {
            String text =m.group(1);
            for(String s : text.split(INPUT_SPLIT)){
                String[] kv = s.split(INPUT_REGEX_KEY_VALUE,2);
                int index = columnNames.indexOf(kv[0].toLowerCase());
                if (index >= 0) {
                    this.setRow(index, kv[1]);
                } else {
                    LOG.warn("第" + this.unmatchedRowsCount + "行 找不到配置的表字段 :" + s+";");
                }
            }
            return this.row;
        }
    }

    public Writable serialize(Object obj, ObjectInspector objInspector) throws SerDeException {
        throw new UnsupportedOperationException("Regex SerDe doesn\'t support the serialize() method");
    }

    public SerDeStats getSerDeStats() {
        return null;
    }
}
