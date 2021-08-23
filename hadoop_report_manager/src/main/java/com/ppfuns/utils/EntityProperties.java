package com.ppfuns.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 简德群
 * Date: 2019/11/26
 * Time: 11:19
 * To change this template use File | Settings | File Templates.
 */
@Component
public class EntityProperties {
    @Value("${ppfuns.database}")
    private String database;
    @Value("#{'${ppfuns.hadoop.class}'.split(',')}")
    private List<String> hadoopRunClazz;
    @Value("${ppfuns.hadoop.jar}")
    private String ppfunsHadoopJar;
    @Value("${ppfuns.hadoop.param:}")
    private String ppfunsHadoopParam;
    @Value("${ppfuns.shell.flag:false}")
    private Boolean ppfunsShellFlag;
    @Value("${ppfuns.shell.ip:}")
    private String ppfunsShellIp;
    @Value("${ppfuns.shell.user:}")
    private String ppfunsShellUser;
    @Value("${ppfuns.shell.password:}")
    private String ppfunsShellPwd;
    @Value("${ppfuns.shell.port:22}")
    private int ppfunsShellPort;
    @Value("#{'${ppfuns.shell.command:}'.split(',')}")
    private List<String> ppfunsShellCmd;

    public static Integer DOWNLOAD_FILE_SIZE;
    @Value("${download.file.size:157286400}")
    public void setDownloadFileSize(Integer downloadFileSize){
        DOWNLOAD_FILE_SIZE = downloadFileSize;
    }
    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getPpfunsHadoopJar() {
        return ppfunsHadoopJar;
    }

    public void setPpfunsHadoopJar(String ppfunsHadoopJar) {
        this.ppfunsHadoopJar = ppfunsHadoopJar;
    }
    public String getPpfunsHadoopParam() {
        return ppfunsHadoopParam;
    }

    public void setPpfunsHadoopParam(String ppfunsHadoopParam) {
        this.ppfunsHadoopParam = ppfunsHadoopParam;
    }

    public List<String> getHadoopRunClazz() {
        return hadoopRunClazz;
    }

    public void setHadoopRunClazz(List<String> hadoopRunClazz) {
        this.hadoopRunClazz = hadoopRunClazz;
    }

    public String getPpfunsShellIp() {
        return ppfunsShellIp;
    }

    public void setPpfunsShellIp(String ppfunsShellIp) {
        this.ppfunsShellIp = ppfunsShellIp;
    }

    public String getPpfunsShellUser() {
        return ppfunsShellUser;
    }

    public void setPpfunsShellUser(String ppfunsShellUser) {
        this.ppfunsShellUser = ppfunsShellUser;
    }

    public String getPpfunsShellPwd() {
        return ppfunsShellPwd;
    }

    public void setPpfunsShellPwd(String ppfunsShellPwd) {
        this.ppfunsShellPwd = ppfunsShellPwd;
    }

    public int getPpfunsShellPort() {
        return ppfunsShellPort;
    }

    public void setPpfunsShellPort(int ppfunsShellPort) {
        this.ppfunsShellPort = ppfunsShellPort;
    }

    public Boolean getPpfunsShellFlag() {
        return ppfunsShellFlag;
    }

    public void setPpfunsShellFlag(Boolean ppfunsShellFlag) {
        this.ppfunsShellFlag = ppfunsShellFlag;
    }

    public List<String> getPpfunsShellCmd() {
        return ppfunsShellCmd;
    }

    public void setPpfunsShellCmd(List<String> ppfunsShellCmd) {
        this.ppfunsShellCmd = ppfunsShellCmd;
    }

}
