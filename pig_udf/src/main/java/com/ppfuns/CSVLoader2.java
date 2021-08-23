package com.ppfuns;

/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Based on earlier version for Pig 0.3 which was Copyright 2009 James Kebinger
 * http://github.com/jkebinger/pig-user-defined-functions 
 * and on built-in PigStorage
 *
 */


import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputFormat;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.pig.FileInputLoadFunc;
import org.apache.pig.LoadPushDown;
import org.apache.pig.PigException;
import org.apache.pig.ResourceSchema;
import org.apache.pig.backend.executionengine.ExecException;
import org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.PigSplit;
import org.apache.pig.backend.hadoop.executionengine.mapReduceLayer.PigTextInputFormat;
import org.apache.pig.bzip2r.Bzip2TextInputFormat;
import org.apache.pig.data.DataByteArray;
import org.apache.pig.data.Tuple;
import org.apache.pig.data.TupleFactory;
import org.apache.pig.impl.logicalLayer.FrontendException;
import org.apache.pig.impl.util.CastUtils;
import org.apache.pig.impl.util.ObjectSerializer;
import org.apache.pig.impl.util.UDFContext;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.*;

/**
 * A load function based on PigStorage that implements part of the CSV "standard"
 * This loader properly supports double-quoted fields that contain commas and other
 * double-quotes escaped with backslashes.
 *
 * The following fields are all parsed as one tuple, per each line
 * "the man, he said ""hello"""
 * "one,two,three"
 *
 * This version supports pig 0.7+
 *
 */

public class CSVLoader2 extends FileInputLoadFunc implements LoadPushDown {

    @SuppressWarnings("rawtypes")
    protected RecordReader in = null;    

    protected static final Log LOG = LogFactory.getLog(CSVLoader2.class);
    private static final byte DOUBLE_QUOTE = '"';
    private static final byte FIELD_DEL = ',';
    private static final byte RECORD_DEL = '\n';
    private final static String PPFUNS_REPLACE_PREFIX = "^.*Collection events:";
    private final static String PPFUNS_REPLACE_SUFFIX = ":END(\n){0,1}$";
    private final static String PPFUNS_PARAM_SPLIT_SIGN = ";"; //参数拆分
    private final static String PPFUNS_KEY_VALUE_SPLIT_SIGN = "="; //k-v拆分
    long end = Long.MAX_VALUE;

    private ArrayList<Object> mProtoTuple = null;
    private TupleFactory mTupleFactory = TupleFactory.getInstance();

    private String signature;
    private String loadLocation;

    private boolean[] mRequiredColumns = null;

    private boolean mRequiredColumnsInitialized = false;

    public CSVLoader2() {
    }

    @Override
    public Tuple getNext() throws IOException {
        mProtoTuple = new ArrayList<Object>();
        if (!mRequiredColumnsInitialized) {
            if (signature != null) {
                Properties p = UDFContext.getUDFContext().getUDFProperties(this.getClass());
                LOG.info("mRequiredColumns的值="+p.getProperty(signature));
                mRequiredColumns = (boolean[])ObjectSerializer.deserialize(p.getProperty(signature));
            }
            mRequiredColumnsInitialized = true;
        }
        try {
            if (!in.nextKeyValue()) {
                return null;
            }                                                                                           
            Text text = (Text) in.getCurrentValue();
            Map<String,String> map = new HashMap<>();
            String vart = text.toString();
            if(vart != null){
                String t = vart.replaceFirst(PPFUNS_REPLACE_PREFIX,"").replaceFirst(PPFUNS_REPLACE_SUFFIX,""); //替换前缀和后缀
                String[] arr = t.split(PPFUNS_PARAM_SPLIT_SIGN);

                for(String a : arr){
                    String[] keyValue = a.split(PPFUNS_KEY_VALUE_SPLIT_SIGN,2);    //key-value
                    map.put(keyValue[0],keyValue[1]);
                }
            }
            ResourceSchema.ResourceFieldSchema[] fields = schema.getFields();
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
            return this.tupleFactory.newTupleNoCopy(this.mProtoTuple)；
    } catch (InterruptedException e) {
            e.printStackTrace();
        }

    private void readField(ByteBuffer buf, int fieldID) {
        if (mRequiredColumns==null || (mRequiredColumns.length>fieldID && mRequiredColumns[fieldID])) {
            byte[] bytes = new byte[buf.position()];
            buf.rewind();
            buf.get(bytes, 0, bytes.length); 
            mProtoTuple.add(new DataByteArray(bytes));
        }
        buf.clear();
    }

    @Override
    public void setLocation(String location, Job job) throws IOException {
        loadLocation = location;
        FileInputFormat.setInputPaths(job, location);        
    }

    @SuppressWarnings("rawtypes")
    @Override
    public InputFormat getInputFormat() throws IOException {
        if(loadLocation.endsWith(".bz2") || loadLocation.endsWith(".bz")) {
            return new Bzip2TextInputFormat();
        } else {
            return new PigTextInputFormat();
        }
    }

    @Override
    public void prepareToRead(@SuppressWarnings("rawtypes") RecordReader reader, PigSplit split)
    throws IOException {
        in = reader;
    }

    @Override
    public RequiredFieldResponse pushProjection(RequiredFieldList requiredFieldList) throws FrontendException {
        if (requiredFieldList == null)
            return null;
        if (requiredFieldList.getFields() != null)
        {
            int lastColumn = -1;
            for (RequiredField rf: requiredFieldList.getFields())
            {
                if (rf.getIndex()>lastColumn)
                {
                    lastColumn = rf.getIndex();
                }
            }
            mRequiredColumns = new boolean[lastColumn+1];
            for (RequiredField rf: requiredFieldList.getFields())
            {
                if (rf.getIndex()!=-1)
                    mRequiredColumns[rf.getIndex()] = true;
            }
            Properties p = UDFContext.getUDFContext().getUDFProperties(this.getClass());
            try {
                p.setProperty(signature, ObjectSerializer.serialize(mRequiredColumns));
            } catch (Exception e) {
                throw new RuntimeException("Cannot serialize mRequiredColumns");
            }
        }
        return new RequiredFieldResponse(true);
    }

    @Override
    public void setUDFContextSignature(String signature) {
        this.signature = signature; 
    }

    @Override
    public List<OperatorSet> getFeatures() {
        return Arrays.asList(OperatorSet.PROJECTION);
    }
}