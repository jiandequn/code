# coding:utf-8
import datetime
import subprocess

import sys


class RunPigScript:
    def __init__(self,startDate=None,endDate=None):
        print '启动Pig脚本'
        if startDate and endDate:
            if startDate >= endDate:
                print '时间范围不对'
                return
            self.__startDate = datetime.datetime.strptime(startDate, "%Y-%m-%d");
            self.__endDate = datetime.datetime.strptime(endDate, "%Y-%m-%d");
            self.__execDateData()
        else:
            self.__execLastWeekData()
        sql="pig -x mapreduce -p input='%s' -p startDate='%s' -p endDate='%s'  yn_hadoop.pig" %(self.__hadoopInputs,self.__startDate.strftime("%Y-%m-%d"),self.__endDate.strftime("%Y-%m-%d"))
        self.__actionLocalCmd(sql)

    def __actionLocalCmd(self,command):
        print "执行本地命令：%s" % command
        subp = subprocess.Popen(command, shell=True, stdout=subprocess.PIPE)
        c = subp.stdout.readline()
        while c:
            print c
            c = subp.stdout.readline()
        print subp.returncode
    def __get_last_week(self):  # 获取上一周时间
        week = datetime.date.today().weekday();
        monday = datetime.date.today() - datetime.timedelta(days=(week + 7))
        sunday = datetime.date.today() - datetime.timedelta(days=(week))
        return monday, sunday

    def __execLastWeekData(self):
        lastMonday,lastSunday = self.__get_last_week()
        self.__startDate = lastMonday
        self.__endDate = lastSunday
        self.__getHadoopInputs()
    def __execDateData(self):
        self.__getHadoopInputs()

    def __getHadoopInputs(self):
        print self.__startDate, self.__endDate
        i = 0;
        valMap={};
        while True:
            curDate = self.__startDate + datetime.timedelta(days=i);
            i = i + 1;
            if curDate >= self.__endDate: break;
            month = curDate.strftime("%m");
            d = curDate.strftime("%d");
            if valMap.has_key(curDate.year):#检查是否含有年份
                yearMap = valMap[curDate.year];
                if yearMap.has_key(month) :
                    dayArr = yearMap[month];
                    dayArr.append(d);
                    if len(dayArr) ==3 : dayArr.pop(1)
                    #判断是不是本月最后一天
                    if dayArr[0]=='01' and (curDate+ datetime.timedelta(days=1)).month != curDate.month:
                        yearMap[month]=[]

                else:yearMap[month]=[d]
            else:
                monthMap={};
                monthMap[month]=[d];
                valMap[curDate.year]=monthMap;
        print valMap;
        #/data/logs/2019/{10/{28,29,30},11/{01..04}}
        st="/data/logs/"
        if len(valMap) >=2:
            st +="{";
        ynum=0;
        for yk,mv in valMap.items():
            st += "%s" %yk;
            if len(mv) >0:
                st=st+"/{"
                mnum = 0;
                for mk,dayArr in mv.items():
                    st=st+mk;
                    if len(dayArr) == 2 :
                        st = st+"/{%s..%s}"%(dayArr[0],dayArr[1])
                    mnum = mnum + 1;
                    if mnum != len(mv): st += ","
                st+="}"

            ynum = ynum + 1;
            if ynum != len(valMap): st += ","
        if len(valMap) >= 2:
            st += "}"
        self.__hadoopInputs=st;
        print st;
        return st;

if __name__ == "__main__":
  argArr = sys.argv;
  if len(argArr)==3:
      RunPigScript(argArr[1], argArr[2])
  elif len(argArr) == 1: RunPigScript()
  else: print '参数不对'








