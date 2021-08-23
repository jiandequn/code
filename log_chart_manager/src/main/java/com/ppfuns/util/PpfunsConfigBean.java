package com.ppfuns.util;

import com.alibaba.druid.util.StringUtils;
import com.ppfuns.report.entity.base.PpfunsEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2020/12/22.
 */
@Component
public class PpfunsConfigBean {
    @Value("${ppfuns.isRemote:false}")
    private Boolean isRemote;
    @Value("${ppfuns.shell.ip:}")
    private String remoteIp;
    @Value("${ppfuns.shell.port:}")
    private int remotePort;
    @Value("${ppfuns.shell.user:}")
    private String remoteUser;
    @Value("${ppfuns.shell.password:}")
    private String remotePassword;
    @Value("${ppfuns.hadoop.hdfs.path:/data/logs}")
    private String hdfsPath;
    @Value("${ppfuns.userType:OTT}")
    private String userType;

    public Boolean getRemote() {
        return isRemote;
    }

    public void setRemote(Boolean remote) {
        isRemote = remote;
    }

    public String getRemoteIp() {
        return remoteIp;
    }

    public void setRemoteIp(String remoteIp) {
        this.remoteIp = remoteIp;
    }

    public int getRemotePort() {
        return remotePort;
    }

    public void setRemotePort(int remotePort) {
        this.remotePort = remotePort;
    }

    public String getRemoteUser() {
        return remoteUser;
    }

    public void setRemoteUser(String remoteUser) {
        this.remoteUser = remoteUser;
    }

    public String getRemotePassword() {
        return remotePassword;
    }

    public void setRemotePassword(String remotePassword) {
        this.remotePassword = remotePassword;
    }

    public String getHdfsPath() {
        return hdfsPath;
    }

    public void setHdfsPath(String hdfsPath) {
        this.hdfsPath = hdfsPath;
    }

    @Autowired
    @Qualifier("thymeleafViewResolver")
    private void myViewConfig(ThymeleafViewResolver thymeleafViewResolver) {
        if (thymeleafViewResolver != null) {
            Map<String, Object> map = new HashMap<>();
            PpfunsEntity ppfunsEntity = new PpfunsEntity();
            ppfunsEntity.setUserType(userType);
            map.put("ppfuns",ppfunsEntity);
            thymeleafViewResolver.setStaticVariables(map);
        }
    }
}
