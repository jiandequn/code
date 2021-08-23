package com.ppfuns;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputFormat;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.pig.*;
import org.apache.pig.ResourceSchema.ResourceFieldSchema;
import org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.PigSplit;
import org.apache.pig.builtin.FuncUtils;
import org.apache.pig.builtin.JsonMetadata;
import org.apache.pig.data.Tuple;
import org.apache.pig.data.TupleFactory;
import org.apache.pig.impl.util.CastUtils;
import org.apache.pig.impl.util.UDFContext;
import org.apache.pig.impl.util.Utils;
import org.apache.pig.parser.ParserException;
import org.codehaus.jackson.JsonFactory;

import java.io.IOException;
import java.util.*;

/**
 * A loader for data stored using {@link PpfunsLoader3}.  This is not a generic
 * JSON loader. It depends on the schema being stored with the data when
 * conceivably you could write a loader that determines the schema from the
 * JSON.
 */
public class PpfunsLoader3 extends LoadFunc implements LoadMetadata {
    protected static final Log LOG = LogFactory.getLog(PpfunsLoader3.class);
    protected RecordReader reader = null;
    protected ResourceSchema schema = null;
    private String udfcSignature = null;
    private TupleFactory tupleFactory = TupleFactory.getInstance();
    private final static String PPFUNS_REPLACE_PREFIX = "^.*Collection events:";
    private final static String PPFUNS_REPLACE_SUFFIX = ":END(\n){0,1}$";
    private final static String PPFUNS_PARAM_SPLIT_SIGN = ";"; //参数拆分
    private final static String PPFUNS_KEY_VALUE_SPLIT_SIGN = "="; //k-v拆分
    private static final String SCHEMA_SIGNATURE = "pig.ppfunsloader.schema";
    protected LoadCaster caster;
    private ArrayList<Object> mProtoTuple = null;
    public PpfunsLoader3() {
    }


    @Override
    public void setLocation(String location, Job job) throws IOException {
        // Tell our input format where we will be reading from
        FileInputFormat.setInputPaths(job, location);
    }

    @Override
    @SuppressWarnings("unchecked")
    public InputFormat getInputFormat() throws IOException {
        // We will use TextInputFormat, the default Hadoop input format for
        // text.  It has a LongWritable key that we will ignore, and the value
        // is a Text (a string writable) that the JSON data is in.
        return new TextInputFormat();
    }

    @Override
    public LoadCaster getLoadCaster() throws IOException {
        // We do not expect to do casting of byte arrays, because we will be
        // returning typed data.
        return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void prepareToRead(RecordReader reader, PigSplit split)
            throws IOException {
        this.reader = reader;

        // Get the schema string from the UDFContext object.
        UDFContext udfc = UDFContext.getUDFContext();
        Properties p =
                udfc.getUDFProperties(this.getClass(), new String[]{udfcSignature});
        String strSchema = p.getProperty(SCHEMA_SIGNATURE);
        LOG.info("strSchema的值;"+strSchema);
        if (strSchema == null) {
            throw new IOException("Could not find schema in UDF context");
        }
        // Parse the schema from the string stored in the properties object.
        schema = new ResourceSchema(Utils.getSchemaFromString(strSchema));
//        jsonFactory = new JsonFactory();
        if(schema == null)  LOG.info("schema的值为NUll");
        else {
            for(String name: schema.fieldNames()) LOG.info("schema参数值：name="+name);
        }

    }

    @Override
    public Tuple getNext() throws IOException {
        if(this.caster == null) {
            this.caster = this.getLoadCaster();
        }

        Map<String,String> map = new HashMap<>();
        try {
            if (!reader.nextKeyValue()) return null;
            Text val = null;
            // Get the current value.  We don't use the key.
            val = (Text)reader.getCurrentValue();
            String vart = val.toString();
            if(vart != null){
                String t = vart.replaceFirst(PPFUNS_REPLACE_PREFIX,"").replaceFirst(PPFUNS_REPLACE_SUFFIX,""); //替换前缀和后缀
                String[] arr = t.split(PPFUNS_PARAM_SPLIT_SIGN);

                for(String a : arr){
                    String[] keyValue = a.split(PPFUNS_KEY_VALUE_SPLIT_SIGN,2);    //key-value
                    map.put(keyValue[0],keyValue[1]);
                }
            }
        } catch (InterruptedException ie) {
            throw new IOException(ie);
        }

        ResourceFieldSchema[] fields = schema.getFields();
        int index;
        for (index = 0; index < fields.length; ++index) {
            String value = map.get(fields[index].getName());
            if (!StringUtils.isEmpty(value)) {
                Object list = CastUtils.convertToType(this.caster, value.getBytes(), fields[index], fields[index].getType());
                LOG.info("转换后的值：key" + fields[index].getName() + ";value=" + list);
                mProtoTuple.add(list);
            } else {
                mProtoTuple.add(null);
            }
        }
        return this.tupleFactory.newTupleNoCopy(this.mProtoTuple);
    }


    @Override
    public void setUDFContextSignature(String signature) {
        udfcSignature = signature;
    }

    @Override
    public ResourceSchema getSchema(String location, Job job)
            throws IOException {
        ResourceSchema s = (new JsonMetadata()).getSchema(location, job, true);
        if (s == null) {
            Properties fieldSchemas = UDFContext.getUDFContext().getUDFProperties(this.getClass(), new String[]{this.udfcSignature});
            String tupleIdx = fieldSchemas.getProperty(this.SCHEMA_SIGNATURE);
            if(tupleIdx == null) {
                this.LOG.info(String.format("参考值：tupleIndex=%s;signature=%s",tupleIdx,this.udfcSignature));
            }

            try {
                this.schema = new ResourceSchema(Utils.getSchemaFromString(tupleIdx));
            } catch (ParserException var7) {
                this.LOG.error("Unable to parse serialized schema " + tupleIdx, var7);
            }
            throw new IOException("Unable to parse schema found in file in " + location);
        }

        // Now that we have determined the schema, store it in our
        // UDFContext properties object so we have it when we need it on the
        // backend
//        UDFContext udfc = UDFContext.getUDFContext();
//        Properties p =
//                udfc.getUDFProperties(this.getClass(), new String[]{udfcSignature});
//        p.setProperty(SCHEMA_SIGNATURE, s.toString());

        return s;
    }

    @Override
    public ResourceStatistics getStatistics(String location, Job job)
            throws IOException {
        // We don't implement this one.
        return null;
    }

    @Override
    public String[] getPartitionKeys(String location, Job job)
            throws IOException {
        // We don't have partitions
        return null;
    }

    @Override
    public void setPartitionFilter(Expression partitionFilter)
            throws IOException {
        // We don't have partitions
    }

    @Override
    public List<String> getShipFiles() {
        List<String> cacheFiles = new ArrayList<String>();
        Class[] classList = new Class[] {JsonFactory.class};
        return FuncUtils.getShipFiles(classList);
    }
}
