package com.jshy.mr.mysqlOutPut;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.mapreduce.lib.db.DBConfiguration;

/**
 * Created with IntelliJ IDEA.
 * User: 简德群
 * Date: 2019/11/14
 * Time: 10:31
 * To change this template use File | Settings | File Templates.
 */
public class DBFixConfiguration extends DBConfiguration{
    public static final String OUTPUT_FIELD_FILTER_NAMES_PROPERTY = "mapreduce.jdbc.output.field.filter.names"; //定义过滤条件
    public static final String OUTPUT_SQL_SCRIPT = "mapreduce.jdbc.output.sql.script";  //自定义sql脚本
    public static final String OUT_SQL_EXEC_MODEL =  "mapreduce.jdbc.output.sql.exec.model"; //定义执行模式

    public DBFixConfiguration(Configuration job) {
        super(job);
    }
    public String[] getOutputFieldFilterNames() {
        return this.getConf().getStrings(OUTPUT_FIELD_FILTER_NAMES_PROPERTY);
    }

    public void setOutputFieldFilterNames(String... fieldNames) {
        this.getConf().setStrings(OUTPUT_FIELD_FILTER_NAMES_PROPERTY, fieldNames);
    }
    public String getOutputSqlScript() {
        return this.getConf().get(OUTPUT_SQL_SCRIPT);
    }

    public void setOutputSqlScript(String sqlScript) {
        this.getConf().set(OUTPUT_SQL_SCRIPT, sqlScript);
    }
    public SqlExecModelEnum getOutputSqlExecModel() {
        return this.getConf().getEnum(OUT_SQL_EXEC_MODEL,SqlExecModelEnum.INSERT);
    }
    public void setOutputSqlExecModel(SqlExecModelEnum modelEnum) {
//        this.getConf().set(OUT_SQL_EXEC_MODEL, modelEnum.getName());
        this.getConf().setEnum(OUT_SQL_EXEC_MODEL,modelEnum);
    }

}
