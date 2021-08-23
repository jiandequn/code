#!/usr/bin/env python
# -*- coding: utf-8 -*-
__author__ = "jiandq"
import paramiko
# 打开数据库连接
from Config import config
"""mysql初始化"""
class SSHCommand:
    def __init__(self,section="SSH"):
        conf = config("conf/conf.ini");
        host = conf.getValue(section, "host");
        username = conf.getValue(section, "username");
        password = conf.getValue(section, "password");
        port = conf.getValue(section, "port");
        #创建SSHClient实例对象
        self.transport = paramiko.Transport((host, int(port)))
        self.transport.connect(username=username, password=password);
        self.ssh = None
        self.chan = None
        self.sftp = None
    def cmd(self,command):
        # 创建目录
        if self.ssh is None:
            self.ssh = paramiko.SSHClient()
            self.ssh._transport = self.transport
            self.chan = self.ssh.invoke_shell()
            self.chan.settimeout(90000)
            result = self.chan.recv(1024)
            print result
        # stdin, stdout, stderr=self.ssh.exec_command(command,bufsize=0)
        self.chan.send(command+'\n')
        results = self.chan.recv(1024)
        #  循环获取数据
        while True:
            if results:
                print results
            if results.endswith('# ') or results.endswith('$ '):
                break
            results = self.chan.recv(1024)

        #stdin, stdout, stderr = self.ssh.exec_command("pwd")

    def upload(self, local_path, target_path):
        #连接，上传
        # file_name = self.create_file()
        self.sftp = paramiko.SFTPClient.from_transport(self.transport)
        # 将location.py 上传至服务器 /tmp/test.py
        self.sftp.put(local_path, target_path);

    def download(self, remote_path, local_path):
        self.sftp = paramiko.SFTPClient.from_transport(self.transport)
        try:
            self.sftp.get(remote_path, local_path)
        except Exception, e:
         print e

    def close(self):
        if not self.chan is None:
            self.chan.close();
        if not self.sftp is None:
            self.sftp.close();
        if not self.ssh is None:
            self.ssh.close();
if __name__ == "__main__":
    SSHCommand().cmd();

