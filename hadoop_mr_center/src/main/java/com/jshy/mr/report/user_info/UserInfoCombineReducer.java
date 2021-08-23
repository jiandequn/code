package com.jshy.mr.report.user_info;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

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
public class UserInfoCombineReducer extends Reducer<UserInfoWritable, Text, UserInfoWritable, Text>{
     Text text = new Text();
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
        text.set(value);
        context.write(key,text);
    }


}


