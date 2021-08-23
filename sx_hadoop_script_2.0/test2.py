# -*- coding: utf-8 -*-
import json
import os
import subprocess
import sys
import requests
import yaml
# yaml.warnings({'YAMLLoadWarning':False})
#由于官方提示load方法存在安全漏洞，所以读取文件时会报错。加上warning忽略，就不会显示警告
yaml.warnings({'YAMLLoadWarning':False})
f=open('day.flow','r',buffering=0)      #打开yaml文件
cfg=f.read()
d=yaml.load_all(cfg)     #将数据转换成python字典行驶输出，存在多个文件时，用load_all，单个的时候load就可以
nodes=[]
for data in d:
    nodes=data["nodes"]
f.close()
argArr = sys.argv;
run_name=argArr[1]
url = 'https://192.168.200.138:8443'
"""{'type': 'command', 'config': {'command': 'sh auto_generate_date.sh day'}, 'name': 'start_job'}"""
runned_name={};
def actionLocalCmd(command):
    print "执行本地命令：%s" %command
    subp = subprocess.Popen(command, shell=True, stdout=subprocess.PIPE,cwd=os.getcwd())
    c = subp.stdout.readline()
    while c:
        print c
        c = subp.stdout.readline()
    print subp.returncode
def run_exec(cur_node):
    #执行当前name
    if cur_node == None:
        return;
    shell_command = cur_node["config"]["command"]
    runned_name[cur_node["name"]] = cur_node;  # 已执行的
    print "执行:%s"%cur_node["name"]
    actionLocalCmd(cur_node["config"]["command"])
    #查找子依赖的类进行处理
    for node in nodes:
        if node.has_key("dependsOn"):
            dependOnAllRun=True;
            flag=False
            for dependOn in node["dependsOn"]:
                if not runned_name.has_key(dependOn):
                    dependOnAllRun=False;
                if dependOn == cur_node["name"]:  # 需要执行的
                    flag=True;
            if flag and dependOnAllRun:#树状结构下都依赖都执行了,才会执行子接口
                run_exec(node)

def start():
    for node in nodes:
        if node["name"] == run_name:  # 需要执行的
            run_exec(node);
            break;
start()
sessionid=None
def loginApi():
    # curl -k -X POST --data "action=login&username=azkaban&password=azkaban" https://192.168.200.138:8443
    postdatas = {"action": "login", "username": "azkaban", "password": "azkaban"}
    req=requests.post("https://192.168.200.138:8443", data=postdatas, verify=False)
    c = req.content
    k = json.loads(c)
    return k
##执行一个工作流
# projectname:项目名称
# flowid:flow名称
# settings:可选参数字典
#   disabled(可选):此次执行禁用的作业名称列表。格式化为JSON数组字符串。如：["job_name_1","job_name_2","job_name_N"]
#   successEmails(可选):执行成功发送的邮件列表。多个邮箱用[,|;|\s+]分隔。如：zh@163.com,zh@126.com
#   failureEmails(可选):执行成功发送的邮件列表。多个邮箱用[,|;|\s+]分隔。如：zh@163.com,zh@126.com
#   successEmailsOverride(可选):是否使用系统默认电子邮件设置覆盖成功邮件。值：true, false
#   failureEmailsOverride(可选):是否使用系统默认电子邮件设置覆盖失败邮件。值：true, false
#   notifyFailureFirst(可选):是否在第一次失败时发送电子邮件通知。值：true, false
#   notifyFailureLast(可选):是否在最后失败时发送电子邮件通知。值：true, false
#   failureAction(可选):如果发生错误，如何操作。值：finishcurrent、cancelimmediately、finishpossible
#   concurrentOption(可选):如果不需要任何详细信息，请使用ignore。值：ignore, pipeline, skip
#   flowOverride[flowProperty](可选):用指定的值重写指定的流属性。如：flowoverride[failure.email]=abc@163.com
def Execute_Flow(settings):
    sessionid= loginApi()["session.id"]
    postdata = {'session.id': sessionid, 'ajax': 'executeFlow', 'project': "sx", 'flow': "day","jobid":"clean_user_job"}
    print postdata
    for key in settings:
        postdata[key] = settings[key]
    fetch_url = url + '/executor?ajax=executeFlow'
    print fetch_url
    r = requests.get(fetch_url, postdata, verify=False).json()
    return r

