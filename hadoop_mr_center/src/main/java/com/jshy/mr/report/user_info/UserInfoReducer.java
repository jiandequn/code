package com.jshy.mr.report.user_info;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;

import java.io.IOException;
import java.util.Iterator;

/**
 * 输出最近的用户信息
 * Created with IntelliJ IDEA.
 * User: 简德群
 * Date: 2019/9/19
 * Time: 10:06
 * To change this template use File | Settings | File Templates.
 */
public class UserInfoReducer extends Reducer<UserInfoWritable, Text, UserInfoWritable, NullWritable>{
    private MultipleOutputs outputs;
    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        outputs = new MultipleOutputs(context);
    }

    @Override
    protected void reduce(UserInfoWritable key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        Iterator<Text> it = values.iterator();
        String value = it.next().toString();
        Text text1;
        while (it.hasNext()){
            text1 = it.next();
            if(text1.toString().compareTo(value)<0){
                value = text1.toString();
            }
        }
        key.setCreateTime(value);
        outputs.write(key, NullWritable.get(),value.substring(0,10).replaceAll("-","/").concat("/userinfo"));
//        outputs.write("userInfo",key,text,);
    }
    @Override
    protected void cleanup(Context context) throws IOException, InterruptedException {
        outputs.close();
    }

}


