package com.ppfuns.utils;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.*;

import java.sql.Connection;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: 简德群
 * Date: 2019/11/26
 * Time: 11:32
 * To change this template use File | Settings | File Templates.
 */

//@Component
@Intercepts({
        @Signature(
                type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class
        })
})
public class MybatisInterceptor implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object o) {
        return Plugin.wrap(o,this);
    }

    @Override
    public void setProperties(Properties properties) {
        properties.forEach((k,v)-> System.out.println(k+":"+v));
        System.out.println("");
    }
}
