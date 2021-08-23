package com.ppfuns;

import org.apache.commons.cli.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputFormat;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.RecordWriter;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.pig.LoadCaster;
import org.apache.pig.LoadFunc;
import org.apache.pig.ResourceSchema;
import org.apache.pig.backend.executionengine.ExecException;
import org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.PigSplit;
import org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.PigTextInputFormat;
import org.apache.pig.bzip2r.Bzip2TextInputFormat;
import org.apache.pig.data.DataByteArray;
import org.apache.pig.data.Tuple;
import org.apache.pig.data.TupleFactory;
import org.apache.pig.impl.util.CastUtils;
import org.apache.pig.impl.util.ObjectSerializer;
import org.apache.pig.impl.util.UDFContext;
import org.apache.pig.impl.util.Utils;
import org.apache.pig.parser.ParserException;

import java.io.IOException;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: 简德群
 * Date: 2020/1/10
 * Time: 15:51
 * To change this template use File | Settings | File Templates.
 */
public class PpfunsLoader extends LoadFunc {
    protected RecordReader in;
    protected RecordWriter writer;
    protected final Log mLog;
    protected String signature;
    private ArrayList<Object> mProtoTuple;
    private TupleFactory mTupleFactory;
    private String loadLocation;
    boolean isSchemaOn;
    boolean dontLoadSchema;
    boolean overwriteOutput;
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
    public PpfunsLoader(){
        this("");
    }
    public PpfunsLoader(String options){
        this.in = null;
        this.writer = null;
        this.mLog = LogFactory.getLog(this.getClass());
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
    @Override
    public void setLocation(String location, Job job) throws IOException {
        this.loadLocation = location;
        FileInputFormat.setInputPaths(job, location);
        this.bzipinput_usehadoops = job.getConfiguration().getBoolean("pig.bzip.use.hadoop.inputformat", true);
    }

    @Override
    public InputFormat getInputFormat() throws IOException {
        if((this.loadLocation.endsWith(".bz2") || this.loadLocation.endsWith(".bz")) && !this.bzipinput_usehadoops) {
            this.mLog.info("Using Bzip2TextInputFormat");
            return new Bzip2TextInputFormat();
        } else {
            this.mLog.info("Using PigTextInputFormat");
            return new PigTextInputFormat();
        }
    }

    @Override
    public void prepareToRead(RecordReader recordReader, PigSplit pigSplit) throws IOException {
        this.in = recordReader;
        if(this.tagFile || this.tagPath) {
            this.sourcePath = ((FileSplit)pigSplit.getWrappedSplit()).getPath();
        }
    }

    @Override
    public Tuple getNext() throws IOException {
        this.mProtoTuple = new ArrayList();
        mLog.info("mRequiredColumnsInitialized="+mRequiredColumnsInitialized+";signature"+signature);
        if(!this.mRequiredColumnsInitialized) {
            if(this.signature != null) {
                Properties e = UDFContext.getUDFContext().getUDFProperties(this.getClass());
                mLog.info("mRequiredColumns="+e.getProperty(this.signature));
                this.mRequiredColumns = (boolean[])((boolean[]) ObjectSerializer.deserialize(e.getProperty(this.signature)));
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
                        mLog.info("参数:key="+keyValue[0]+";value="+keyValue[1]);
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
    private void print(){
        Properties fieldSchemas1 = UDFContext.getUDFContext().getUDFProperties(this.getClass());
        Iterator it2 = fieldSchemas1.entrySet().iterator();
        while (it2.hasNext()){
            mLog.info("getNext下："+it2.next());
        }
        Properties fieldSchemas = UDFContext.getUDFContext().getUDFProperties(this.getClass(), new String[]{this.signature});
        Iterator it = fieldSchemas.entrySet().iterator();
        while (it.hasNext()){
            mLog.info("getNext下："+it.next());
        }
    }
    private void addTupleValue2(Map<String,String> map) throws IOException {
        if(this.caster == null) {
            this.caster = this.getLoadCaster();
        }
        mLog.info("准备执行:addTupleValue2");
         print();
        if(this.signature != null && this.schema == null) {
            Properties fieldSchemas = UDFContext.getUDFContext().getUDFProperties(this.getClass(), new String[]{this.signature});
            Iterator it = fieldSchemas.entrySet().iterator();
            while (it.hasNext()){
                mLog.info("getNext下："+it.next());
            }
            String tupleIdx = fieldSchemas.getProperty(this.signature + ".schema");
            try {
                this.schema = new ResourceSchema(Utils.getSchemaFromString(tupleIdx));
            } catch (ParserException var7) {
                this.mLog.error("Unable to parse serialized schema " + tupleIdx, var7);
            }
        }
        if(this.schema != null) {
            ResourceSchema.ResourceFieldSchema[] var8 = this.schema.getFields();
            int index;
            for (index = 0; index < var8.length; ++index) {
                String value = map.get(var8[index].getName());
                Object list = CastUtils.convertToType(this.caster, new DataByteArray(value).get(), var8[index], var8[index].getType());
                mLog.info("转换后的值：key"+var8[index].getName()+";value="+list);
                mProtoTuple.add(list);
            }
        }
    }
    private void addTupleValue(ArrayList<Object> tuple, byte[] buf, int start, int end) {
        tuple.add(this.readField(buf, start, end));
    }

    protected DataByteArray readField(byte[] bytes, int start, int end) {
        return start == end?null:new DataByteArray(bytes, start, end);
    }
    private Tuple applySchema(Tuple tup) throws IOException {
        if(this.caster == null) {
            this.caster = this.getLoadCaster();
        }

        if(this.signature != null && this.schema == null) {
            Properties fieldSchemas = UDFContext.getUDFContext().getUDFProperties(this.getClass(), new String[]{this.signature});
            String tupleIdx = fieldSchemas.getProperty(this.signature + ".schema");
            if(tupleIdx == null) {
                return tup;
            }

            try {
                this.schema = new ResourceSchema(Utils.getSchemaFromString(tupleIdx));
            } catch (ParserException var7) {
                this.mLog.error("Unable to parse serialized schema " + tupleIdx, var7);
            }
        }

        if(this.schema != null) {
            ResourceSchema.ResourceFieldSchema[] var8 = this.schema.getFields();
            int var9 = 0;

            int lastindex;
            for(lastindex = 0; lastindex < var8.length; ++lastindex) {
                if(this.mRequiredColumns == null || this.mRequiredColumns.length > lastindex && this.mRequiredColumns[lastindex]) {
                    if(var9 >= tup.size()) {
                        tup.append((Object)null);
                    }

                    Object list = null;
                    if(tup.get(var9) != null) {
                        byte[] i = ((DataByteArray)tup.get(var9)).get();
                        list = CastUtils.convertToType(this.caster, i, var8[lastindex], var8[lastindex].getType());
                        tup.set(var9, list);
                    }

                    ++var9;
                }
            }

            if(tup.size() > var8.length) {
                lastindex = tup.size() - 1;
                List var10 = tup.getAll();

                for(int var11 = lastindex; var11 >= var8.length; --var11) {
                    var10.remove(var11);
                }

                tup = this.mTupleFactory.newTupleNoCopy(var10);
            }
        }

        return tup;
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
}
