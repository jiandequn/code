# -*- coding: utf-8 -*-
import json
import os
import subprocess
import sys
import yaml

__author__ = "jiandequn"
# yaml.warnings({'YAMLLoadWarning':False})
# 由于官方提示load方法存在安全漏洞，所以读取文件时会报错。加上warning忽略，就不会显示警告
class Gen:
    def __init__(self, argArr):
        params=self._convertParam(argArr);
        self.flag=self._vidateParam(params)
        if self.flag:
            self.jobs = params["jobs"].split(",")
            del params["jobs"]
            self.flowId = params["flowId"]
            del params["flowId"]
            self.nodes=self._getYamlNode();
        self.job_params=self._getJobParams(params);
        self._runned_name={}
    def _getYamlNode(self):
        yaml.warnings({'YAMLLoadWarning': False})
        f = open('%s.flow' % self.flowId, 'r', buffering=0)  # 打开yaml文件
        cfg = f.read()
        d = yaml.load_all(cfg)  # 将数据转换成python字典行驶输出，存在多个文件时，用load_all，单个的时候load就可以
        nodes = []
        for data in d:
            nodes = data["nodes"]
        f.close()
        return nodes;
    def _vidateParam(self,params):
        if not params.has_key("jobs"):
            print "没有传jobs=xxx值"
            return False
        if not params.has_key("flowId"):
            print "flowId=xxx值，值为day,month,week等"
            return False;

        return True
    def _convertParam(self, argArr):
        params = {};
        for arg in argArr:
            arr = str.split(arg, "=");
            if len(arr) == 2:
                params[arr[0]] = arr[1];
        return params;
    def _getJobParams(self,params):
        job_param = "";
        if len(params) > 0:
            kvarr = []
            for k, v in params.items():
                kvarr.append(k + "=" + v)
            job_param = " ".join(kvarr)
        return job_param
    def _actionLocalCmd(self,command):
        command = command.replace("${job_params}", self.job_params)
        print "执行本地命令：%s" % command
        subp = subprocess.Popen(command, shell=True, stdout=subprocess.PIPE, cwd=os.getcwd())
        c = subp.stdout.readline()
        while c:
            print c
            c = subp.stdout.readline()
        print subp.returncode

    def _run_exec(self,cur_node):
        # 执行当前name
        if cur_node == None:
            return;
        self._runned_name[cur_node["name"]] = cur_node;  # 已执行的
        self._actionLocalCmd(cur_node["config"]["command"])
        # 查找子依赖的类进行处理
        for node in self.nodes:
            if node.has_key("dependsOn"):
                dependOnAllRun = True;
                flag = False
                for dependOn in node["dependsOn"]:
                    if not self._runned_name.has_key(dependOn):
                        dependOnAllRun = False;
                    if dependOn == cur_node["name"]:  # 需要执行的
                        flag = True;
                if flag and dependOnAllRun:  # 树状结构下都依赖都执行了,才会执行子接口
                    self._run_exec(node)
    def start(self):
        if not self.flag:return
        print "任务组%s 的执行参数：%s \n\t" % (",".join(self.jobs),self.job_params)
        for run_name in self.jobs:
            for node in self.nodes:
                if node["name"] == run_name:  # 需要执行的
                    self._run_exec(node);
                    break;


"""{'type': 'command', 'config': {'command': 'sh auto_generate_date.sh day'}, 'name': 'start_job'}"""
argArr = sys.argv;
a=Gen(argArr)
a.start()

