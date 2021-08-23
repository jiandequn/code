package com.ppfuns;

import org.apache.commons.cli.*;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.compress.BZip2Codec;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.io.compress.GzipCodec;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.pig.*;
import org.apache.pig.backend.executionengine.ExecException;
import org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.PigSplit;
import org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.PigTextInputFormat;
import org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.PigTextOutputFormat;
import org.apache.pig.backend.hadoop.executionengine.physicalLayer.relationalOperators.POStore;
import org.apache.pig.builtin.JsonMetadata;
import org.apache.pig.bzip2r.Bzip2TextInputFormat;
import org.apache.pig.data.DataByteArray;
import org.apache.pig.data.DataType;
import org.apache.pig.data.Tuple;
import org.apache.pig.data.TupleFactory;
import org.apache.pig.impl.logicalLayer.FrontendException;
import org.apache.pig.impl.logicalLayer.schema.Schema;
import org.apache.pig.impl.util.*;

import java.io.IOException;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: 简德群
 * Date: 2020/1/10
 * Time: 15:51
 * To change this template use File | Settings | File Templates.
 */
public class PpfunsStorage extends FileInputLoadFunc implements StoreFuncInterface, LoadPushDown, LoadMetadata, StoreMetadata, OverwritableStoreFunc {
    protected RecordReader in;
    protected RecordWriter writer;
    protected final Log mLog;
    protected String udfcSignature;
    private static final String PPFUNS_SIGNATURE = "pig.ppfunsloader.schema";
    private ArrayList<Object> mProtoTuple;
    private TupleFactory mTupleFactory;
    private String loadLocation;
    boolean isSchemaOn;
    boolean dontLoadSchema;
    boolean overwriteOutput;
    private byte fieldDel;
    protected ResourceSchema schema;
    protected LoadCaster caster;
    protected boolean[] mRequiredColumns;
    private boolean mRequiredColumnsInitialized;
    private boolean tagFile;
    private static final String TAG_SOURCE_FILE = "tagFile";
    private boolean tagPath;
    private static final String TAG_SOURCE_PATH = "tagPath";
    private Path sourcePath;
    private boolean bzipinput_usehadoops;

    private final static String PPFUNS_REPLACE_PREFIX = "^.*Collection events:";
    private final static String PPFUNS_REPLACE_SUFFIX = ":END(\n){0,1}$";
    private final static String PPFUNS_PARAM_SPLIT_SIGN = ";"; //参数拆分
    private final static String PPFUNS_KEY_VALUE_SPLIT_SIGN = "="; //k-v拆分
    public PpfunsStorage(){
        this(",");
    }
    public PpfunsStorage(String delimiter) {
        this(delimiter, "");
    }
    public PpfunsStorage(String delimiter,String options){
        this.in = null;
        this.writer = null;
        this.mLog = LogFactory.getLog(this.getClass());
        this.fieldDel = 9;
        this.mProtoTuple = null;
        this.mTupleFactory = TupleFactory.getInstance();
        this.isSchemaOn = false;
        this.dontLoadSchema = false;
        this.overwriteOutput = false;
        this.mRequiredColumns = null;
        this.mRequiredColumnsInitialized = false;
        this.tagFile = false;
        this.tagPath = false;
        this.sourcePath = null;
        this.fieldDel = StorageUtil.parseFieldDel(delimiter);
        Options validOptions = this.populateValidOptions();
        String[] optsArr = options.split(" ");

        try {
            GnuParser e = new GnuParser();
            CommandLine formatter1 = e.parse(validOptions, optsArr);
            this.isSchemaOn = formatter1.hasOption("schema");
            if(formatter1.hasOption("overwrite")) {
                String value = formatter1.getOptionValue("overwrite");
                if("true".equalsIgnoreCase(value)) {
                    this.overwriteOutput = true;
                }
            }

            this.dontLoadSchema = formatter1.hasOption("noschema");
            this.tagFile = formatter1.hasOption("tagFile");
            this.tagPath = formatter1.hasOption("tagPath");
            if(formatter1.hasOption("tagsource")) {
                this.mLog.warn("\'-tagsource\' is deprecated. Use \'-tagFile\' instead.");
                this.tagFile = true;
            }

        } catch (ParseException var8) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("PpfunsStorage(\',\', \'[options]\')", validOptions);
            throw new RuntimeException(var8);
        }
    }


    public void setLocation(String location, Job job) throws IOException {
        this.loadLocation = location;
        FileInputFormat.setInputPaths(job, location);
        this.bzipinput_usehadoops = job.getConfiguration().getBoolean("pig.bzip.use.hadoop.inputformat", true);
    }
    public InputFormat getInputFormat() {
        if((this.loadLocation.endsWith(".bz2") || this.loadLocation.endsWith(".bz")) && !this.bzipinput_usehadoops) {
            this.mLog.info("Using Bzip2TextInputFormat");
            return new Bzip2TextInputFormat();
        } else {
            this.mLog.info("Using PigTextInputFormat");
            return new PigTextInputFormat();
        }
    }
    public void prepareToRead(RecordReader reader, PigSplit split) {
        this.in = reader;
        if(this.tagFile || this.tagPath) {
            this.sourcePath = ((FileSplit)split.getWrappedSplit()).getPath();
        }

    }

    @Override
    public Tuple getNext() throws IOException {
        this.mProtoTuple = new ArrayList();
        if(!this.mRequiredColumnsInitialized) {
            if(this.udfcSignature != null) {
                Properties e = UDFContext.getUDFContext().getUDFProperties(this.getClass(),new String[]{udfcSignature});
                this.mRequiredColumns = (boolean[])((boolean[]) ObjectSerializer.deserialize(e.getProperty(this.PPFUNS_SIGNATURE)));
            }

            this.mRequiredColumnsInitialized = true;
        }

        if(this.tagFile) {
            this.mProtoTuple.add(new DataByteArray(this.sourcePath.getName()));
        } else if(this.tagPath) {
            this.mProtoTuple.add(new DataByteArray(this.sourcePath.toString()));
        }

        try {
            boolean var9 = this.in.nextKeyValue();
            if(!var9) {
                return null;
            } else {
                Text var10 = (Text)this.in.getCurrentValue();
                String vart = var10.toString();
                if(vart != null){
                    String t = vart.replaceFirst(PPFUNS_REPLACE_PREFIX,"").replaceFirst(PPFUNS_REPLACE_SUFFIX,""); //替换前缀和后缀
                    String[] arr = t.split(PPFUNS_PARAM_SPLIT_SIGN);
                    Map<String,String> map = new HashMap<>();
                    for(String a : arr){
                        String[] keyValue = a.split(PPFUNS_KEY_VALUE_SPLIT_SIGN,2);    //key-value
                        map.put(keyValue[0],keyValue[1]);
                    }
                    addTupleValue2(map);
                }
                Tuple var12 = this.mTupleFactory.newTupleNoCopy(this.mProtoTuple);
                return var12;
            }
        } catch (InterruptedException var8) {
            short errCode = 6018;
            String errMsg = "Error while reading input";
            throw new ExecException(errMsg, errCode, (byte)16, var8);
        }
    }
    private void addTupleValue2(Map<String,String> map) throws IOException {
        if(this.caster == null) {
            this.caster = this.getLoadCaster();
        }
        if(this.schema == null) listSchema();
        if(this.schema != null) {
            ResourceSchema.ResourceFieldSchema[] var8 = this.schema.getFields();
            int index;
            for (index = 0; index < var8.length; ++index) {
                String value = map.get(var8[index].getName());
                if(!StringUtils.isEmpty(value)){
                    Object list = CastUtils.convertToType(this.caster, value.getBytes(), var8[index], var8[index].getType());
                    mLog.info("转换后的值：key"+var8[index].getName()+";value="+list);
                    mProtoTuple.add(list);
                }else {
                    mProtoTuple.add(null);
                }

            }
        }
    }
    public void putNext(Tuple f) throws IOException {
        try {
            this.writer.write((Object)null, f);
        } catch (InterruptedException var3) {
            throw new IOException(var3);
        }
    }

    public RequiredFieldResponse pushProjection(RequiredFieldList requiredFieldList) throws FrontendException {
        if(requiredFieldList == null) {
            mLog.info("RequiredFieldList字段列表NULL");
            return null;
        } else {
            if(requiredFieldList.getFields() != null) {
                int lastColumn = -1;
                Iterator p = requiredFieldList.getFields().iterator();

                RequiredField e;
                while(p.hasNext()) {
                    e = (RequiredField)p.next();
                    mLog.info("RequiredFieldList字段列表11：alias="+e.getAlias()+";index="+e.getIndex()+";type="+e.getIndex());
                    if(e.getIndex() > lastColumn) {
                        lastColumn = e.getIndex();
                    }
                }

                this.mRequiredColumns = new boolean[lastColumn + 1];
                p = requiredFieldList.getFields().iterator();

                while(p.hasNext()) {
                    e = (RequiredField)p.next();
                    mLog.info("RequiredFieldList字段列表：alias="+e.getAlias()+";index="+e.getIndex()+";type="+e.getIndex());
                    if(e.getIndex() != -1) {
                        this.mRequiredColumns[e.getIndex()] = true;
                    }
                }

                Properties p1 = UDFContext.getUDFContext().getUDFProperties(this.getClass());

                try {
                    p1.setProperty(this.PPFUNS_SIGNATURE, ObjectSerializer.serialize(this.mRequiredColumns));
                } catch (Exception var5) {
                    throw new RuntimeException("Cannot serialize mRequiredColumns");
                }
            }

            return new RequiredFieldResponse(true);
        }
    }
    public boolean equals(Object obj) {
        return obj instanceof PpfunsStorage?this.equals((PpfunsStorage)obj):false;
    }

    public boolean equals(PpfunsStorage other) {
        return this.fieldDel == other.fieldDel;
    }
    private Options populateValidOptions() {
        Options validOptions = new Options();
        validOptions.addOption("schema", false, "Loads / Stores the schema of the relation using a hidden JSON file.");
        validOptions.addOption("noschema", false, "Disable attempting to load data schema from the filesystem.");
        validOptions.addOption("tagFile", false, "Appends input source file name to beginning of each tuple.");
        validOptions.addOption("tagPath", false, "Appends input source file path to beginning of each tuple.");
        validOptions.addOption("tagsource", false, "Appends input source file name to beginning of each tuple.");
        Option overwrite = new Option("overwrite", "Overwrites the destination.");
        overwrite.setLongOpt("overwrite");
        overwrite.setOptionalArg(true);
        overwrite.setArgs(1);
        overwrite.setArgName("overwrite");
        validOptions.addOption(overwrite);
        return validOptions;
    }
    public OutputFormat getOutputFormat() {
        return new PigTextOutputFormat(this.fieldDel);
    }

    public void prepareToWrite(RecordWriter writer) {
        this.writer = writer;
    }

    public void setStoreLocation(String location, Job job) throws IOException {
        job.getConfiguration().set("mapred.textoutputformat.separator", "");
        FileOutputFormat.setOutputPath(job, new Path(location));
        if("true".equals(job.getConfiguration().get("output.compression.enabled"))) {
            FileOutputFormat.setCompressOutput(job, true);
            String codec = job.getConfiguration().get("output.compression.codec");

            try {
                FileOutputFormat.setOutputCompressorClass(job, (Class<? extends CompressionCodec>) Class.forName(codec));
            } catch (ClassNotFoundException var5) {
                throw new RuntimeException("Class not found: " + codec);
            }
        } else {
            this.setCompression(new Path(location), job);
        }

    }

    private void setCompression(Path path, Job job) {
        String location = path.getName();
        if(!location.endsWith(".bz2") && !location.endsWith(".bz")) {
            if(location.endsWith(".gz")) {
                FileOutputFormat.setCompressOutput(job, true);
                FileOutputFormat.setOutputCompressorClass(job, GzipCodec.class);
            } else {
                FileOutputFormat.setCompressOutput(job, false);
            }
        } else {
            FileOutputFormat.setCompressOutput(job, true);
            FileOutputFormat.setOutputCompressorClass(job, BZip2Codec.class);
        }

    }

    public void checkSchema(ResourceSchema s) throws IOException {
    }

    public String relToAbsPathForStoreLocation(String location, Path curDir) throws IOException {
        return LoadFunc.getAbsolutePath(location, curDir);
    }

    public int hashCode() {
        return this.fieldDel;
    }

    public void setUDFContextSignature(String signature) {
        this.udfcSignature = signature;
    }

    public List<OperatorSet> getFeatures() {
        return Arrays.asList(new OperatorSet[]{OperatorSet.PROJECTION});
    }

    public void setStoreFuncUDFContextSignature(String signature) {
    }

    public void cleanupOnFailure(String location, Job job) throws IOException {
        StoreFunc.cleanupOnFailureImpl(location, job);
    }

    public void cleanupOnSuccess(String location, Job job) throws IOException {
    }
    private ResourceSchema.ResourceFieldSchema addFieldSchema(String name){
        Schema.FieldSchema c = new Schema.FieldSchema(name, DataType.CHARARRAY);
        ResourceSchema.ResourceFieldSchema fs =new ResourceSchema.ResourceFieldSchema(c);
        return fs;
    }
    private void listSchema(){
        String f = "eventsType:chararray,mac:chararray,sn:chararray,userId:chararray,userType:chararray,parentColumnId:chararray,columnId:chararray,nowSpm:chararray,afterSpm:chararray,posName:chararray,contentId:chararray,contentType:chararray,operateType:chararray,keyWord:chararray,createTime:datetime";
        String[] params =  f.split(",");
        ResourceSchema.ResourceFieldSchema[] list =  new ResourceSchema.ResourceFieldSchema[params.length];
        int i=0;
        for(String s : params){
            String[] kv = s.split(":");
            Schema.FieldSchema c = new Schema.FieldSchema(kv[0],  DataType.findTypeByName(kv[1]));
            ResourceSchema.ResourceFieldSchema fs =new ResourceSchema.ResourceFieldSchema(c);
            list[i++]=fs;
        }
        schema = new ResourceSchema();
        schema.setFields(list);
    }
    public ResourceSchema getSchema(String location, Job job) throws IOException {
        listSchema();
        if(schema != null) return schema;
        mLog.info("getSchema方法下dontLoadSchema="+dontLoadSchema);
        if(!this.dontLoadSchema) {
            this.schema = (new JsonMetadata()).getSchema(location, job, this.isSchemaOn);
            mLog.info("getSchema方法下signature="+udfcSignature+";isSchemaOn="+isSchemaOn);
            if(this.schema !=null){
                for(String t : this.schema.fieldNames()){
                    mLog.info("getSchema方法下schema="+t);
                }
            }
            if(this.udfcSignature != null && this.schema != null) {
                if(this.tagFile) {
                    this.schema = Utils.getSchemaWithInputSourceTag(this.schema, "INPUT_FILE_NAME");
                } else if(this.tagPath) {
                    this.schema = Utils.getSchemaWithInputSourceTag(this.schema, "INPUT_FILE_PATH");
                }

                Properties p = UDFContext.getUDFContext().getUDFProperties(this.getClass(), new String[]{this.udfcSignature});
                p.setProperty(this.udfcSignature + ".schema", this.schema.toString());
            }
        }

        return this.schema;
    }

    public ResourceStatistics getStatistics(String location, Job job) throws IOException {
        return null;
    }

    public void setPartitionFilter(Expression partitionFilter) throws IOException {
    }

    public String[] getPartitionKeys(String location, Job job) throws IOException {
        return null;
    }

    public void storeSchema(ResourceSchema schema, String location, Job job) throws IOException {
        if(this.isSchemaOn) {
            JsonMetadata metadataWriter = new JsonMetadata();
            byte recordDel = 10;
            metadataWriter.setFieldDel(this.fieldDel);
            metadataWriter.setRecordDel(recordDel);
            metadataWriter.storeSchema(schema, location, job);
        }

    }

    public void storeStatistics(ResourceStatistics stats, String location, Job job) throws IOException {
    }

    public boolean shouldOverwrite() {
        return this.overwriteOutput;
    }

    public void cleanupOutput(POStore store, Job job) throws IOException {
        Configuration conf = job.getConfiguration();
        String output = conf.get("mapred.output.dir");
        Path outputPath = null;
        if(output != null) {
            outputPath = new Path(output);
        }

        FileSystem fs = outputPath.getFileSystem(conf);

        try {
            fs.delete(outputPath, true);
        } catch (Exception var8) {
            this.mLog.warn("Could not delete output " + output);
        }

    }

    public static void main(String[] args) {
        System.out.println(new PpfunsStorage().getClass());
    }
}
