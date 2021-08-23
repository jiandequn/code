package com.ppfuns.sys.shiro;

import org.apache.shiro.authz.Permission;

/**
 * Created with IntelliJ IDEA.
 * User: 简德群
 * Date: 2019/5/22
 * Time: 16:19
 * To change this template use File | Settings | File Templates.
 */
public class MyPermission implements Permission {
    @Override
    public boolean implies(Permission permission) {
        return false;
    }
}
