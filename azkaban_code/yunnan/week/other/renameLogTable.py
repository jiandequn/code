# -*- coding: UTF-8 -*-
import sys

import pymysql
reload(sys)
sys.setdefaultencoding('utf-8')
class mysql_init:
    def __init__(self, username, password, url, database, port=3306):
        self.db = pymysql.connect(url, username, password, database, port, charset='utf8')
    def query(self, select):
        cursor = self.db.cursor();
        # 使用 execute()  方法执行 SQL 查询
        self.db.ping(reconnect=True)
        cursor.execute(select)
        return cursor.fetchall()

    def execute(self, select):
        cursor = self.db.cursor();
        # 使用 execute()  方法执行 SQL 查询
        self.db.ping(reconnect=True)
        cursor.execute(select)

    def close(self):
        self.db.close()

if __name__ == "__main__":
    argArr = sys.argv;
    params = {};
    for arg in argArr:
        arr = str.split(arg, "=");
        if len(arr) == 2:
            params[arr[0]] = arr[1];
    #验证配置信息

    tableArr = [("login_page_log", u"首页访问日志"), ("detail_page_log", u"详情页日志"), ("album_play_log", u"播放记录日志")];
    if params.has_key("startDate") and params.has_key("endDate"):
        startDate = params["startDate"]
        endDate = params["endDate"]
    else:
        raise Exception("not found params for startDate and endDate;endDate and startDate is must.", "startDate,endDate")
    if params.has_key("mysqlUrl"):
        mysqlUrl = params["mysqlUrl"]
    else:
        raise Exception("not found param for mysqlUrl,mysqlUrl is must.", "mysqlUrl")
    if params.has_key("mysqlPwd"):
        mysqlPwd = params["mysqlPwd"]
    else:
        raise Exception("not found param for mysqlPwd,mysqlPwd is must.", "mysqlPwd")
    if params.has_key("mysqlName"):
        mysqlName = params["mysqlName"]
    else:
        raise Exception("not found param for mysqlName,mysqlName is must.", "mysqlName")
    if params.has_key("mysqlDb"):
        mysqlDb = params["mysqlDb"]
    else:
        raise Exception("not found param for mysqlDb,mysqlDb is must.", "mysqlDb")
    if params.has_key("mysqlPort"):
        mysqlPort = int(params["mysqlPort"])
    else:
        mysqlPort=3306
    m = mysql_init(username=mysqlName, password=mysqlPwd, url=mysqlUrl, database=mysqlDb, port=mysqlPort)
    for at in tableArr:
        t = at[0];
        tname = at[1];
        t_val = m.query("select SUBSTR(min(create_time),1,10),SUBSTR(max(create_time),1,10) from %s" % t);
        if len(t_val) == 1 and t_val[0][0] is not None and t_val[0][1] is not None:
            print t_val;
            sdate = t_val[0][0].replace("-", "");
            edate = t_val[0][1].replace("-", "");
            renameTable = "%s_%s_%s" % (t, sdate, edate);
            # 检查表是否已存在，如果存在drop table
            m.execute("drop table IF EXISTS %s" % renameTable);
            # 增加描述信息
            renameTableSql = "rename table %s to %s;" % (t, renameTable);
            print u"重命名表%s为 %s;" % (t, renameTable)
            m.execute(renameTableSql);  # 重命名表
            m.execute("ALTER TABLE %s COMMENT '%s%s_%s';" % (renameTable, tname, sdate, edate))
            createTableSql = "create table %s select * from %s where 1=2" % (t, renameTable);
            m.execute(createTableSql);  # 创建表结构
            m.execute("ALTER TABLE %s COMMENT '%s%s_%s';" % (t, tname, startDate, endDate))
        else:
            print u"表%s 未查到记录，无需重新创建表" %t
    m.close()
