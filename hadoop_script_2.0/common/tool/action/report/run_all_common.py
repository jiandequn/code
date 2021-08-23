#!/usr/bin/env python
# -*- coding: utf-8 -*-

from common.tool.action.report.base.day import DayBuilder
from common.tool.action.report.base.month import MonthBuilder
from common.tool.action.report.base.week import WeekBuilder
day_step="4,8,9,10,11,12,13,14,15,16,17"
week_step="1,2,3,4,5,6,7,8,9"
month_step="1,2"
class RunAllCommon(DayBuilder,WeekBuilder,MonthBuilder):
    def start_day(self):
        # 需分析文件已Load完；对日期范围内数据数据进行分区和分时间存储
        param={
            4:["report/app/area_visit_count/app_area_visit_count_day.hive","report/app/area_visit_count/export_app_area_visit_count_day.sqoop",
               "report/no_app/area_visit_count/noapp_area_visit_count_day.hive","report/no_app/area_visit_count/export_no_app_area_visit_count_day.sqoop"],
            8:["report/app/rank/bookmark/bookmark_album_rank_day.hive","report/app/rank/bookmark/export_bookmark_rank.sqoop","report/app/rank/bookmark/export_bookmark_rank_day.sqoop"],
            9:["report/app/rank/search/search_album_rank_day.hive","report/app/rank/search/export_search_album_rank.sqoop","report/app/rank/search/export_search_album_rank_day.sqoop"],
            10:["report/app/detail_page/user_day.hive","report/app/detail_page/export_detail_page_visit_user.sqoop","report/app/detail_page/export_detail_page_visit_user_day.sqoop"],
            11:["report/app/label/subscribe_label_count.hive","report/app/label/export_subscribe_label_count.sqoop"],
            12:["report/app/page_stay_time/length_of_stay_day.hive","report/app/page_stay_time/export_app_stay_duration.sqoop",
                "report/app/page_stay_time/export_app_stay_duration_day.sqoop","report/app/page_stay_time/export_column_stay_duration.sqoop",
                "report/app/page_stay_time/export_column_stay_duration_day.sqoop"],
            13:["report/app/user_source/day.hive","report/app/user_source/export_app_inlet_position_count_day.sqoop","report/app/user_source/export_app_inlet_count_day.sqoop"],
            14:["report/app/rank/play_album/album_rank_day.hive","report/app/rank/play_album/export_album_play_count_rank_day.sqoop","report/app/rank/play_album/export_album_user_count_rank_day.sqoop"],
            15:["report/app/rank/play_video/video_rank_day.hive","report/app/rank/play_video/export_video_play_count_rank_day.sqoop","report/app/rank/play_video/export_video_user_count_rank_day.sqoop"],
            16:["report/update_area_to_user/updateAreaToUser.hive","report/update_area_to_user/exportAreaToUser.sqoop"],
            17: ["report/app/album_content_type/day.hive",
                 "report/app/album_content_type/export_day.sqoop"],
        }
        self.__run(param);

    def start_week(self):
        param={
            1:["report/app/rank/play_album/album_rank_week.hive","report/app/rank/play_album/export_album_play_count_rank_week.sqoop","report/app/rank/play_album/export_album_user_count_rank_week.sqoop"],
            2:["report/app/rank/play_video/video_rank_week.hive","report/app/rank/play_video/export_video_play_count_rank_week.sqoop","report/app/rank/play_video/export_video_user_count_rank_week.sqoop"],
            3:["report/app/area_visit_count/app_area_visit_count_week.hive","report/app/area_visit_count/export_app_area_visit_count_week.sqoop",
               "report/no_app/area_visit_count/noapp_area_visit_count_week.hive","report/no_app/area_visit_count/export_no_app_area_visit_count_week.sqoop"],
            4:["report/app/rank/bookmark/bookmark_album_rank_week.hive","report/app/rank/bookmark/export_bookmark_rank_week.sqoop"],
            5:["report/app/rank/search/search_album_rank_week.hive","report/app/rank/search/export_search_album_rank_week.sqoop"],
            6:["report/app/page_stay_time/length_of_stay_week.hive","report/app/page_stay_time/export_app_stay_duration_week.sqoop","report/app/page_stay_time/export_column_stay_duration_week.sqoop"],
            7:["report/app/detail_page/user_week.hive","report/app/detail_page/export_detail_page_visit_user_week.sqoop"],
            8:["report/app/user_source/week.hive","report/app/user_source/export_app_inlet_position_count_week.sqoop","report/app/user_source/export_app_inlet_count_week.sqoop"],
            9: ["report/app/album_content_type/week.hive",
                 "report/app/album_content_type/export_week.sqoop"],
        }
        self.__run(param);

    def start_month(self):
        param = {
            1: ["report/app/area_visit_count/app_area_visit_count_month.hive","report/app/area_visit_count/export_app_area_visit_count_month.sqoop",
                "report/no_app/area_visit_count/noapp_area_visit_count_month.hive","report/no_app/area_visit_count/export_no_app_area_visit_count_month.sqoop"],
            2: ["report/app/album_content_type/month.hive",
                 "report/app/album_content_type/export_month.sqoop"],
        }
        self.__run(param);

    def __run(self,param):
        for idx in param.keys():
            if self._logic_validate(idx):
                filelist = param[idx];
                for filestr in filelist:
                    if filestr.endswith('sqoop'):
                        self._action._sqoopUtils.execScrpit(fileName=filestr,arr={"startDate": self._action._startDate.strftime("%Y-%m-%d"),
                                                "endDate": self._action._endDate.strftime("%Y-%m-%d")});
                    elif filestr.endswith('hive'):
                        self._action._hiveUtils.exceFile(filePath=filestr,
                                                hived={"startDate": self._action._startDate.strftime("%Y-%m-%d"),
                                                       "endDate": self._action._endDate.strftime("%Y-%m-%d")});
