package com.jshy.mr.mysqlOutPut;

import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.RecordWriter;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.db.DBOutputFormat;
import org.apache.hadoop.mapreduce.lib.db.DBWritable;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;

/**
 * Created with IntelliJ IDEA.
 * User: 简德群
 * Date: 2019/11/14
 * Time: 10:28
 * To change this template use File | Settings | File Templates.
 */
public class DBFixOutPutFormat<K extends DBWritable, V> extends DBOutputFormat<K, V> {

    public String constructQuery1(SqlExecModelEnum modelEnum, String table, String[] fieldNames, String[] fieldFilterNames) {
        if(fieldNames == null) {
            throw new IllegalArgumentException("Field names may not be null");
        } else {
            StringBuilder query = new StringBuilder();
            if(modelEnum == SqlExecModelEnum.REPLACE){
                query.append("INSERT ");
            }else{
                query.append("REPLACE ");
            }
            query.append("INTO ").append(table);
            int i;
            if(fieldNames.length > 0 && fieldNames[0] != null) {
                query.append(" (");

                for(i = 0; i < fieldNames.length; ++i) {
                    query.append(fieldNames[i]);
                    if(i != fieldNames.length - 1) {
                        query.append(",");
                    }
                }

                query.append(")");
            }
            if(fieldFilterNames != null && fieldFilterNames.length>0){
                query.append("SELECT ");
            }else{
                query.append(" VALUES (");
            }

            for(i = 0; i < fieldNames.length; ++i) {
                query.append("?");
                if(i != fieldNames.length - 1) {
                    query.append(",");
                }
            }
            if(fieldFilterNames != null && fieldFilterNames.length>0){
                query.append(" FROM DUAL WHERE NOT EXISTS(SELECT 1 FROM ").append(table).append(" WHERE 1=1");
                for(i = 0; i < fieldFilterNames.length; ++i) {
                    query.append(" AND ").append(fieldFilterNames[i])
                            .append("=?");
                }
            }
            if(!this.dbProductName.startsWith("DB2") && !this.dbProductName.startsWith("ORACLE")) {
                query.append(");");
            } else {
                query.append(")");
            }
            System.out.println("执行sql语句："+query);
            return query.toString();
        }
    }
    public String constructQuery(SqlExecModelEnum modelEnum, String table, String[] fieldNames, String[] fieldFilterNames) {
        if(fieldNames == null) {
            throw new IllegalArgumentException("Field names may not be null");
        } else {
            StringBuilder query = new StringBuilder();
            query.append("INSERT ");
            query.append("INTO ").append(table);
            int i;
            if(fieldNames.length > 0 && fieldNames[0] != null) {
                query.append(" (");

                for(i = 0; i < fieldNames.length; ++i) {
                    query.append(fieldNames[i]);
                    if(i != fieldNames.length - 1) {
                        query.append(",");
                    }
                }

                query.append(")");
            }
            query.append(" VALUES (");
            for(i = 0; i < fieldNames.length; ++i) {
                query.append("?");
                if(i != fieldNames.length - 1) {
                    query.append(",");
                }
            }
            query.append(")");
            if(fieldFilterNames != null && fieldFilterNames.length>0){
                query.append(" ON DUPLICATE KEY UPDATE ");
                for(i = 0; i < fieldFilterNames.length; ++i) {
                    query.append(fieldFilterNames[i])
                            .append("=?");
                    if(i != fieldFilterNames.length - 1) {
                        query.append(",");
                    }
                }
            }
            if(!this.dbProductName.startsWith("DB2") && !this.dbProductName.startsWith("ORACLE")) {
                query.append(";");
            }
            System.out.println("执行sql语句："+query);
            return query.toString();
        }
    }

    public RecordWriter<K, V> getRecordWriter(TaskAttemptContext context) throws IOException {
        DBFixConfiguration dbConf = new DBFixConfiguration(context.getConfiguration());
        String sqlScript = dbConf.getOutputSqlScript();
        try {
            Connection ex = dbConf.getConnection();
            PreparedStatement statement = null;
            DatabaseMetaData dbMeta = ex.getMetaData();
            this.dbProductName = dbMeta.getDatabaseProductName().toUpperCase();
            if(StringUtils.isEmpty(sqlScript)){    //非自定义脚本；则启用字段备份模式
                String tableName = dbConf.getOutputTableName();
                String[] fieldNames = dbConf.getOutputFieldNames();
                if(fieldNames == null) {
                    fieldNames = new String[dbConf.getOutputFieldCount()];
                }
                String[] fieldFilterNames = dbConf.getOutputFieldFilterNames();
                SqlExecModelEnum modelEnum = dbConf.getOutputSqlExecModel();
                sqlScript = this.constructQuery(modelEnum,tableName, fieldNames,fieldFilterNames);
            }
            statement = ex.prepareStatement(sqlScript);
            return new DBFixOutPutFormat.DBRecordWriter(ex, statement);
        } catch (Exception var8) {
            throw new IOException(var8.getMessage());
        }
    }

    /**
     *
     * @param job  任务
     * @param tableName  表名称
     * @param fieldNames 字段
     * @param filterFieldNameList 过滤字段
     * @throws IOException
     */
    public static void setOutputAndFilter(Job job, String tableName,String[] fieldNames,String[] filterFieldNameList) throws IOException {
        if(fieldNames.length > 0 && fieldNames[0] != null) {
            DBFixConfiguration dbConf = setOutput(job, tableName, SqlExecModelEnum.INSERT_NO_EXISTS);
            dbConf.setOutputFieldNames(fieldNames);
            if(filterFieldNameList.length>0){
                dbConf.setOutputFieldFilterNames(filterFieldNameList);
            }
        } else {
            if(fieldNames.length <= 0) {
                throw new IllegalArgumentException("Field names must be greater than 0");
            }

            setOutput(job, tableName, fieldNames.length);
        }

    }
    @Deprecated
    public static void setOutput(Job job, String tableName,String... fieldNames) throws IOException {
        if(fieldNames.length > 0 && fieldNames[0] != null) {
            DBFixConfiguration dbConf = setOutput(job, tableName, SqlExecModelEnum.INSERT);
            dbConf.setOutputFieldNames(fieldNames);
        } else {
            if(fieldNames.length <= 0) {
                throw new IllegalArgumentException("Field names must be greater than 0");
            }

            setOutput(job, tableName, fieldNames.length);
        }

    }

    public static void setOutput(Job job, String tableName,String[] fieldNames,SqlExecModelEnum modelEnum) throws IOException {
        if(fieldNames.length > 0 && fieldNames[0] != null) {
            DBFixConfiguration dbConf = setOutput(job, tableName, modelEnum);
            dbConf.setOutputFieldNames(fieldNames);
        } else {
            if(fieldNames.length <= 0) {
                throw new IllegalArgumentException("Field names must be greater than 0");
            }

            setOutput(job, tableName, fieldNames.length);
        }

    }
    private static DBFixConfiguration setOutput(Job job, String tableName, SqlExecModelEnum modelEnum) throws IOException {
        job.setOutputFormatClass(DBFixOutPutFormat.class);
        job.setReduceSpeculativeExecution(false);
        DBFixConfiguration dbConf = new DBFixConfiguration(job.getConfiguration());
        dbConf.setOutputTableName(tableName);
        dbConf.setOutputSqlExecModel(modelEnum);
        return dbConf;
    }

    /**
     * 通过sql脚本直接操作
     * @param job
     * @param sqlScript
     * @return
     * @throws IOException
     */
    public static DBFixConfiguration setOutputBySqlScript(Job job,String sqlScript) throws IOException {
        job.setOutputFormatClass(DBFixOutPutFormat.class);
        job.setReduceSpeculativeExecution(false);
        DBFixConfiguration dbConf = new DBFixConfiguration(job.getConfiguration());
        if(sqlScript != null){
            dbConf.setOutputSqlScript(sqlScript);
        }
        return dbConf;
    }
}
