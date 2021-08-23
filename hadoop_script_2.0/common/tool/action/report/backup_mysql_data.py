#!/usr/bin/env python
# -*- coding: utf-8 -*-
from common.tool.action.report.base.day import DayBuilder
from common.utils import Constant

day_step="0";
class BackupMysqlData(DayBuilder):
    def start_day(self):
        if not self._logic_validate(0): return;
        print "备份mysql数据之album,video,content_type,labels等数据"
        contentDb = {"db_url": Constant.CONTENT_CENTER_MANAGER_URL,
                     "db_username": Constant.CONTENT_CENTER_MANAGER_USERNAME,
                     "db_pwdfile": Constant.CONTENT_CENTER_MANAGER_PWDFILE};
        userDb = {"db_url": Constant.USER_CENTER_MANAGER_URL,
                  "db_username": Constant.USER_CENTER_MANAGER_USERNAME,
                  "db_pwdfile": Constant.USER_CENTER_MANAGER_PWDFILE};
        self._action._sqoopUtils.execScrpit(fileName="backupMysql/album/importAlbum.sqoop", arr={}, dbconf=contentDb);
        self._action._sqoopUtils.execScrpit(fileName="backupMysql/album/import_video_to_init.sqoop", arr={}, dbconf=contentDb);
        self._action._sqoopUtils.execScrpit(fileName="backupMysql/album/importAlbumLabel.sqoop", arr={}, dbconf=contentDb);
        self._action._sqoopUtils.execScrpit(fileName="backupMysql/album/importAlbumContentType.sqoop", arr={}, dbconf=userDb);
        self._action._sqoopUtils.execScrpit(fileName="backupMysql/column/import_column_management_to_init.sqoop", arr={},
                                   dbconf=contentDb);
        self._action._hiveUtils.exceFile(filePath="backupMysql/backup.hive")
        if Constant.conf.getValue("OTHER", "yn_task") == "1":
            self._action._sqoopUtils.execScrpit(fileName="backupMysql/yunnan/import_short_video_to_init.sqoop", arr={}, dbconf=contentDb);
            self._action._hiveUtils.exceFile(filePath="backupMysql/yunnan/backup_short_video.hive")