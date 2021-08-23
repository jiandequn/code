package com.ppfuns.util;

import com.jcraft.jsch.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 * User: 简德群
 * Date: 2019/11/26
 * Time: 14:57
 * To change this template use File | Settings | File Templates.
 */
public class ShellCommandUtils {
    private final static Logger logger = LoggerFactory.getLogger(ShellCommandUtils.class);

    public static void chmod(String script, String cmd) throws IOException, InterruptedException {
        logger.info("解决脚本没有执行权限逻辑start");
        //解决脚本没有执行权限
        ProcessBuilder builder = new ProcessBuilder("/bin/chmod", "755", script);
        Process process = builder.start();
        process.waitFor();
        logger.info("解决脚本没有执行权限逻辑end");

//
    }

    public static void localExec(String cmd, String threadName) {
        logger.info("开始执行runtime的脚本start");
        Process ps = null;
        try {
            ps = Runtime.getRuntime().exec(cmd);
            //处理InputStream的线程，获取进程的标准输入流
            Thread t = new Thread(new InputStreamThread(ps.getInputStream()), threadName.concat("_suc"));
            t.start();
            Thread t1 = new Thread(new InputStreamThread(ps.getErrorStream()), threadName.concat("_err"));
            t1.start();
            //等待shell脚本结果
            int execStatus = ps.waitFor();
            logger.info("执行runtime的脚本end");
            logger.info("shell脚本执行结果--execStatus =" + execStatus);
            logger.info("返回值为result=" + execStatus);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    /**
     * 只能单指令执行
     * @param ip
     * @param port
     * @param username
     * @param password
     * @param command
     *
     */
    public static void remoteExec(String ip, Integer port, String username, String password, String command) {
        JSch jsch = new JSch();
        Session session = null;
        Channel channel = null;
        ChannelExec channelExec = null;
        try {
            jsch.getSession(username, ip, port);
            session = jsch.getSession(username, ip, port);
            session.setPassword(password);
            Properties sshConfig = new Properties();
            sshConfig.put("StrictHostKeyChecking", "no");
            session.setConfig(sshConfig);
            session.connect(60 * 1000);
            logger.info("Session connected!");
            // 打开执行shell指令的通道
            logger.info("执行远程Shell命令 command - > {}", command);
                channel = session.openChannel("exec");//只能执行一条指令（也可执行符合指令）
                channelExec = (ChannelExec) channel;
                channelExec.setCommand(command);
                channel.setInputStream(null);
                channelExec.setErrStream(System.err);
                channel.connect();
                InputStream in = channelExec.getInputStream();
                InputStreamReader isr = new InputStreamReader(in, StandardCharsets.UTF_8);
                BufferedReader reader = new BufferedReader(isr,16);
                reader.lines().forEach(logger::info);
                isr.close();
                in.close();
        } catch (JSchException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (channelExec != null && channelExec.isConnected()) {
                channelExec.disconnect();
            }
            if (channel != null && channel.isConnected()) {
                channel.disconnect();
            }
            if (session != null && session.isConnected()) {
                session.disconnect();
            }
        }

    }

    public static List<String> getResultByRemoteExec(String ip, Integer port, String username, String password, String command) {
        List<String> resultLines = null;
        JSch jsch = new JSch();
        Session session = null;
        Channel channel = null;
        ChannelExec channelExec = null;
        try {
            jsch.getSession(username, ip, port);
            session = jsch.getSession(username, ip, port);
            session.setPassword(password);
            Properties sshConfig = new Properties();
            sshConfig.put("StrictHostKeyChecking", "no");
            session.setConfig(sshConfig);
            session.connect(60 * 1000);
            logger.info("Session connected!");
            // 打开执行shell指令的通道
            ChannelShell channelShell = (ChannelShell) session.openChannel("shell");
            InputStream inputStream = channelShell.getInputStream();//从远端到达的数据  都能从这个流读取到
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"),1024);
            channelShell.setPty(true);
            channelShell.connect();
            OutputStream outputStream = channelShell.getOutputStream();//写入该流的数据  都将发送到远程端
            //使用PrintWriter 就是为了使用println 这个方法
            //好处就是不需要每次手动给字符加\n
            PrintWriter printWriter = new PrintWriter(outputStream);
            printWriter.println(command);
            printWriter.println("exit");//为了结束本次交互
            printWriter.flush();//把缓冲区的数据强行输出
            resultLines = bufferedReader.lines().collect(Collectors.toList());
            bufferedReader.close();
            outputStream.close();
            inputStream.close();
            channelShell.disconnect();
            session.disconnect();
            System.out.println("DONE");
        } catch (JSchException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (channelExec != null && channelExec.isConnected()) {
                channelExec.disconnect();
            }
            if (channel != null && channel.isConnected()) {
                channel.disconnect();
            }
            if (session != null && session.isConnected()) {
                session.disconnect();
            }
            return resultLines;
        }
    }
    /**
     * 多指令执行shell
     * @param ip
     * @param port
     * @param username
     * @param password
     * @param cmdList
     */
    public static void remoteShell(String ip, Integer port, String username, String password, List<String> cmdList) {
        JSch jsch = new JSch();
        Session session = null;
        Channel channel = null;
        ChannelExec channelExec = null;
        try {
            jsch.getSession(username, ip, port);
            session = jsch.getSession(username, ip, port);
            session.setPassword(password);
            Properties sshConfig = new Properties();
            sshConfig.put("StrictHostKeyChecking", "no");
            session.setConfig(sshConfig);
            session.connect(60 * 1000);
            logger.info("Session connected!");
            // 打开执行shell指令的通道
            ChannelShell channelShell = (ChannelShell) session.openChannel("shell");
            InputStream inputStream = channelShell.getInputStream();//从远端到达的数据  都能从这个流读取到
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"),1024);
            channelShell.setPty(true);
            channelShell.connect();
            OutputStream outputStream = channelShell.getOutputStream();//写入该流的数据  都将发送到远程端
            //使用PrintWriter 就是为了使用println 这个方法
            //好处就是不需要每次手动给字符加\n
            PrintWriter printWriter = new PrintWriter(outputStream);
            cmdList.forEach(cmd->printWriter.println(cmd));
            printWriter.println("exit");//为了结束本次交互
            printWriter.flush();//把缓冲区的数据强行输出
            bufferedReader.lines().forEach(logger::info);
            bufferedReader.close();
            outputStream.close();
            inputStream.close();
            channelShell.disconnect();
            session.disconnect();
            System.out.println("DONE");
        } catch (JSchException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (channelExec != null && channelExec.isConnected()) {
                channelExec.disconnect();
            }
            if (channel != null && channel.isConnected()) {
                channel.disconnect();
            }
            if (session != null && session.isConnected()) {
                session.disconnect();
            }
        }

    }
    public static void main(String[] args) {
//        List<String> list =  new ArrayList<String>();
//        list.add("pwd;ls;cd /usr/local/hadoop-3.1.2;ls");
//        list.add("ls");
//        list.add("cd /usr/local/hadoop-3.1.2");
//        list.add("su hadoop");
//        list.add("ls");
//        list.add("hadoop fs -cat /data2/logs/2020/12/22/17/*");
//        remoteShell("10.131.45.120",22,"root","ynry@120)",list);
         remoteExec("10.131.45.120",22,"root","ynry@120)","hadoop fs -cat /data2/logs/2020/12/22/17/*");

        List<String> lines = getResultByRemoteExec("10.131.45.120",22,"root","ynry@120)","hadoop fs -cat /data2/logs/2020/12/22/17/*");
        lines.forEach(System.out::print);
    }
}

class InputStreamThread implements Runnable{
    private final static Logger logger = LoggerFactory.getLogger(InputStreamThread.class);
    private InputStream inputStream = null;
    public InputStreamThread(InputStream inputStream){
          this.inputStream = inputStream;
    }
    @Override
    public void run() {
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        try {
            String line = null;
            while ((line = br.readLine()) != null) {
                if (line != null){
                    logger.info("hadoop执行结果输出："+line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally{
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
