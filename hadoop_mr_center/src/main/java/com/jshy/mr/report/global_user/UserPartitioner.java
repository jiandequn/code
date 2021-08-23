package com.jshy.mr.report.global_user;

import org.apache.hadoop.mapreduce.Partitioner;

/**
 * Created with IntelliJ IDEA.
 * User: 简德群
 * Date: 2019/11/15
 * Time: 17:53
 * To change this template use File | Settings | File Templates.
 */
public class UserPartitioner<K,V> extends Partitioner<K,V> {
    @Override
    public int getPartition(K key, V value, int numReduceTasks) {
        return key.hashCode();
    }
}
