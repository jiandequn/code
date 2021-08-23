package com.ppfuns.utils;

import com.alibaba.druid.util.StringUtils;
import com.ppfuns.entity.table.RunClassEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 简德群
 * Date: 2019/11/27
 * Time: 14:37
 * To change this template use File | Settings | File Templates.
 */
public class HadoopUtils {
    private final static Logger logger = LoggerFactory.getLogger(HadoopUtils.class);
    public static void run(List<RunClassEntity> runClazzes, String timePattern, String threadName, EntityProperties entityProperties){
        new Thread("tableInfo") {
            @Override
            public void run() {
                runClazzes.forEach(runClassEntity -> {
                    exec(runClassEntity,timePattern,threadName,entityProperties);
                });
            }
            private void exec(RunClassEntity runClazz, String timePattern, String threadName, EntityProperties entityProperties){
                StringBuilder sb = new StringBuilder("hadoop jar ");
                sb.append(entityProperties.getPpfunsHadoopJar());
                if(!StringUtils.isEmpty(entityProperties.getPpfunsHadoopParam())){
                    sb.append(" ").append(entityProperties.getPpfunsHadoopParam());
                }
                if(!StringUtils.isEmpty(runClazz.getName())){
                    if(runClazz.getName().split(",").length != entityProperties.getHadoopRunClazz().size()){
                        sb.append(" ").append("ppfuns.run.class=").append(runClazz.getName());
                    }
                }
                if(!StringUtils.isEmpty(timePattern)){
                    sb.append(" ").append("time.pattern=").append(RunTypeEnum.get(runClazz.getRunType()).getName()).append(":").append(timePattern);
                }
                logger.info("执行脚本命令:"+sb.toString());
                if(entityProperties.getPpfunsShellFlag()){
                    List<String> list = new ArrayList<String>();
                    if(!CollectionUtils.isEmpty(entityProperties.getPpfunsShellCmd())) list.addAll(entityProperties.getPpfunsShellCmd());
                    if(list.size() ==1 && StringUtils.isEmpty(list.get(0))) list.remove(0);
                    list.add(sb.toString());
                    ShellCommandUtils.remoteShell(entityProperties.getPpfunsShellIp(),entityProperties.getPpfunsShellPort(),
                            entityProperties.getPpfunsShellUser(),entityProperties.getPpfunsShellPwd(),list);
                }else{
                    ShellCommandUtils.localExec(sb.toString(), threadName);
                }
            }
        }.start();
    }
}
