# -*- coding: UTF-8 -*-


import pymysql

# 打开数据库连接
from Config import config

"""mysql初始化"""
class mysql_init:
    def __init__(self,section="MYSQL"):
        conf = config("conf/conf.ini");
        url = conf.getValue(section, "mysql_url");
        username = conf.getValue(section, "mysql_user");
        password = conf.getValue(section, "mysql_pwd");
        database = conf.getValue(section, "mysql_database");
        port = conf.getValue(section, "mysql_port");
        self.db = pymysql.connect(url,username, password, database,int(port), charset='utf8')
    """查询数据"""
    def query(self,select):
        cursor =  self.db.cursor();
        # 使用 execute()  方法执行 SQL 查询
        self.db.ping(reconnect=True)
        cursor.execute(select)
        return cursor.fetchall()
    def execute(self,select):
        cursor = self.db.cursor();
        # 使用 execute()  方法执行 SQL 查询
        self.db.ping(reconnect=True)
        cursor.execute(select)
    def close(self):
        self.db.close()

    def insertMany(self,select,args):
        cursor = self.db.cursor();
        # 使用 execute()  方法执行 SQL 查询
        self.db.ping(reconnect=True)
        cursor.executemany(select,args)
        self.db.commit();

    def clearTableData(self, table):
        cursor = self.db.cursor();
        cursor.execute("truncate table %s" % table);

if __name__ == "__main__":
    t = mysql_init();
    # sql = """SELECT
    #              a.mac,
    #                 a.sn,
    #                 b.user_id
    #             FROM
    #                 tb_user a ,
    #              resume_point b
    #             where
    #               a.auth_code = b.user_id
    #               and b.create_time<='2017-05-31'
    #             group by a.mac,a.sn""";
    c = t.query("select mac,sn from user_view_temp");
    for row in c:
        print row
    t.close();