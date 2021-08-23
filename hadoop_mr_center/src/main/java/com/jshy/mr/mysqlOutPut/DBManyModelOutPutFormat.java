package com.jshy.mr.mysqlOutPut;

import org.apache.hadoop.classification.InterfaceStability;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.db.DBWritable;
import org.apache.hadoop.mapreduce.lib.output.FileOutputCommitter;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * mysql多模式数据自定义
 * Created with IntelliJ IDEA.
 * User: 简德群
 * Date: 2019/11/14
 * Time: 10:28
 * To change this template use File | Settings | File Templates.
 */
public class DBManyModelOutPutFormat<K extends DBWritable, V> extends OutputFormat<K, V> {
    private static final Logger LOG = LoggerFactory.getLogger(DBManyModelOutPutFormat.class);
    public String dbProductName = "DEFAULT";
    public DBManyModelOutPutFormat() {
    }
    @Override
    public void checkOutputSpecs(JobContext context) throws IOException, InterruptedException {
    }
    @Override
    public OutputCommitter getOutputCommitter(TaskAttemptContext context) throws IOException, InterruptedException {
        return new FileOutputCommitter(FileOutputFormat.getOutputPath(context), context);
    }
    public String constructQuery(String table, String[] fieldNames,String[] fieldFilterNames) {
        if(fieldNames == null) {
            throw new IllegalArgumentException("Field names may not be null");
        } else {
            StringBuilder query = new StringBuilder();
            query.append("INSERT INTO ").append(table);
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
            if(fieldFilterNames.length>0){
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
            if(fieldFilterNames.length>0){
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



    public static void setOutput(Job job, String tableName, int fieldCount) throws IOException {
        DBFixConfiguration dbConf = setOutput(job, tableName);
        dbConf.setOutputFieldCount(fieldCount);
    }

    public RecordWriter<K, V> getRecordWriter(TaskAttemptContext context) throws IOException {
        DBFixConfiguration dbConf = new DBFixConfiguration(context.getConfiguration());
        String tableName = dbConf.getOutputTableName();
        String[] fieldNames = dbConf.getOutputFieldNames();
        if(fieldNames == null) {
            fieldNames = new String[dbConf.getOutputFieldCount()];
        }
        String[] fieldFilterNames = dbConf.getOutputFieldFilterNames();
        try {
            Connection ex = dbConf.getConnection();
            PreparedStatement statement = null;
            DatabaseMetaData dbMeta = ex.getMetaData();
            this.dbProductName = dbMeta.getDatabaseProductName().toUpperCase();
            statement = ex.prepareStatement(this.constructQuery(tableName, fieldNames,fieldFilterNames));
            return new DBFixRecordWriter(ex, statement);
        } catch (Exception var8) {
            throw new IOException(var8.getMessage());
        }
    }

    public static void setOutputAndFilter(Job job, String tableName,String[] fieldNames,String[] filterFieldNameList) throws IOException {
        if(fieldNames.length > 0 && fieldNames[0] != null) {
            DBFixConfiguration dbConf = setOutput(job, tableName);
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
    private static DBFixConfiguration setOutput(Job job, String tableName) throws IOException {
        job.setOutputFormatClass(DBManyModelOutPutFormat.class);
        job.setReduceSpeculativeExecution(false);
        DBFixConfiguration dbConf = new DBFixConfiguration(job.getConfiguration());
        dbConf.setOutputTableName(tableName);
        return dbConf;
    }
    @InterfaceStability.Evolving
    public class DBFixRecordWriter extends RecordWriter<K, V> {
        private Connection connection;
        private PreparedStatement statement;

        public DBFixRecordWriter() throws SQLException {
        }

        public DBFixRecordWriter(Connection connection, PreparedStatement statement) throws SQLException {
            this.connection = connection;
            this.statement = statement;
            this.connection.setAutoCommit(false);
        }

        public Connection getConnection() {
            return this.connection;
        }

        public PreparedStatement getStatement() {
            return this.statement;
        }

        public void close(TaskAttemptContext context) throws IOException {
            try {
                this.statement.executeBatch();
                this.connection.commit();
            } catch (SQLException var13) {
                try {
                    this.connection.rollback();
                } catch (SQLException var12) {
                    DBManyModelOutPutFormat.LOG.warn(StringUtils.stringifyException(var12));
                }

                throw new IOException(var13.getMessage());
            } finally {
                try {
                    this.statement.close();
                    this.connection.close();
                } catch (SQLException var11) {
                    throw new IOException(var11.getMessage());
                }
            }

        }

        public void write(K key, V value) throws IOException {
            try {
                key.write(this.statement);
                this.statement.addBatch();
            } catch (SQLException var4) {
                var4.printStackTrace();
            }

        }
    }



}
