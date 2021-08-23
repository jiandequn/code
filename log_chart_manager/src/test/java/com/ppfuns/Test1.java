package com.ppfuns;
import com.ppfuns.report.entity.CollectBean;
import org.apache.commons.lang3.StringUtils;
import org.python.core.*;
import org.python.util.PythonInterpreter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Administrator on 2020/12/18.
 */
public class Test1 {
    private static String getText(){
        return "eventsType=COLUMN_TYPE_PRODUCT;eventsName=产品;area=B;mac=08-67-4E-C2-65-14;sn=12113500E05FF008674EC26514;userId=13144715;ca=null;userType=vod;parentColumnId=6029;columnId=6039;nowSpm=6029.PAGE_YNGDJSJPB.6039.0.1599227062451;afterSpm=6029.PAGE_YNGDJSJPB_GGW_1.0;columnCodeName=云南广电聚视界;pos=PAGE_RECOMMEND;posName=推荐Tab;createTime=2020-09-04 21:44:22\n" +
                "\n" +
                "eventsType=COLUMN_TYPE_TAB;eventsName=页签选项卡;area=B;mac=08-67-4E-C2-65-14;sn=12113500E05FF008674EC26514;userId=13144715;ca=null;userType=vod;parentColumnId=6029;columnId=8197;nowSpm=6029.PAGE_FREE.8197.0.1599227078268;afterSpm=6029.PAGE_YNGDJSJPB.6039.0.1599227062451;columnCodeName=免费专区Tab;pos=PAGE_FREE;posName=免费专区Tab;createTime=2020-09-04 21:44:38\n" +
                "\n" +
                "eventsType=COLUMN_TYPE_TAB;eventsName=页签选项卡;area=B;mac=08-67-4E-C2-65-14;sn=12113500E05FF008674EC26514;userId=13144715;ca=null;userType=vod;parentColumnId=6029;columnId=6247;nowSpm=6029.PAGE_SERIES.6247.0.1599227081587;afterSpm=6029.PAGE_FREE.8197.0.1599227078268;columnCodeName=剧集-电视剧Tab;pos=PAGE_SERIES;posName=剧集-电视剧Tab;createTime=2020-09-04 21:44:41\n" +
                "\n" +
                "eventsType=COLUMN_TYPE_TAB;eventsName=页签选项卡;area=B;mac=08-67-4E-C2-65-14;sn=12113500E05FF008674EC26514;userId=13144715;ca=null;userType=vod;parentColumnId=6029;columnId=6793;nowSpm=6029.PAGE_CHILDREN.6793.0.1599227089409;afterSpm=6029.PAGE_MOVIE.6435.0.1599227085479;columnCodeName=童趣-少儿Tab;pos=PAGE_CHILDREN;posName=童趣-少儿Tab;createTime=2020-09-04 21:44:49\n" +
                "\n" +
                "eventsType=COLUMN_TYPE_TAB;eventsName=页签选项卡;area=B;mac=08-67-4E-C2-65-14;sn=12113500E05FF008674EC26514;userId=13144715;ca=null;userType=vod;parentColumnId=6029;columnId=9083;nowSpm=6029.PAGE_HWJC.9083.0.1599227092239;afterSpm=6029.PAGE_CHILDREN.6793.0.1599227089409;columnCodeName=海外剧场-Tab;pos=PAGE_HWJC;posName=海外剧场-Tab;createTime=2020-09-04 21:44:52\n" +
                "\n" +
                "eventsType=AUTH_PRODUCT;eventsName=内容鉴权;area=B;mac=08674EC26514;sn=12113500E05FF008674EC26514;userId=13144715;ca=91110101635B8511;userType=vod;parentColumnId=6029;contentId=19729;videoId=0;contentType=1;productCode=-1;thirdProductCode=-1;result=false;message=鉴权未通过;operateType=auth_product;createTime=2020-09-04 21:45:39\n" +
                "\n" +
                "eventsType=COLUMN_TYPE_HISTORY_POINT;eventsName=新增点播记录;area=B;mac=08674EC26514;sn=12113500E05FF008674EC26514;userId=13144715;ca=null;userType=vod;parentColumnId=6029;albumId=19729;videoId=108375;operateType=add;timePosition=0;createTime=2020-09-04 21:45:55\n" +
                "\n" +
                "eventsType=COLUMN_TYPE_HISTORY_POINT;eventsName=获取点播记录;area=B;mac=08674EC26514;sn=12113500E05FF008674EC26514;userId=13144715;ca=null;userType=vod;parentColumnId=6029;albumId=15249;operateType=get;createTime=2020-09-04 21:47:34\n" +
                "\n" +
                "eventsType=COLUMN_TYPE_TAB;eventsName=页签选项卡;area=B;mac=08-67-4E-C2-65-14;sn=12113500E05FF008674EC26514;userId=13144715;ca=null;userType=vod;parentColumnId=6029;columnId=6247;nowSpm=6029.PAGE_SERIES.6247.0.1599227324369;afterSpm=6029.PAGE_YNGDJSJPB.6039.0.1599227316976;columnCodeName=剧集-电视剧Tab;pos=PAGE_SERIES;posName=剧集-电视剧Tab;createTime=2020-09-04 21:48:44\n" +
                "\n" +
                "eventsType=COLUMN_TYPE_PRODUCT;eventsName=产品;area=B;mac=08-67-4E-C2-65-14;sn=12113500E05FF008674EC26514;userId=13144715;ca=null;userType=vod;parentColumnId=6029;columnId=6039;nowSpm=6029.PAGE_YNGDJSJPB.6039.0.1599227316976;afterSpm=6029.PAGE_YNGDJSJPB_GGW_1.0;columnCodeName=云南广电聚视界;pos=PAGE_RECOMMEND;posName=推荐Tab;createTime=2020-09-04 21:48:36\n" +
                "\n" +
                "eventsType=AUTH_PRODUCT;eventsName=内容鉴权;area=B;mac=08674EC26514;sn=12113500E05FF008674EC26514;userId=13144715;ca=91110101635B8511;userType=vod;parentColumnId=6029;contentId=11299;videoId=0;contentType=1;productCode=SP1563847294402;thirdProductCode=PT20190731100641096;result=true;message=鉴权通过;operateType=auth_product;createTime=2020-09-04 21:49:05\n" +
                "\n" +
                "eventsType=COLUMN_TYPE_TAB;eventsName=页签选项卡;area=B;mac=08-67-4E-C2-65-14;sn=12113500E05FF008674EC26514;userId=13144715;ca=null;userType=vod;parentColumnId=6029;columnId=6435;nowSpm=6029.PAGE_MOVIE.6435.0.1599227085479;afterSpm=6029.PAGE_SERIES.6247.0.1599227081587;columnCodeName=电影Tab;pos=PAGE_MOVIE;posName=电影Tab;createTime=2020-09-04 21:44:45\n" +
                "\n" +
                "eventsType=PAGE_ALBUM_DETAILS;eventsName=专辑详情;area=B;mac=08674EC26514;sn=12113500E05FF008674EC26514;userId=13144715;ca=null;userType=vod;parentColumnId=6029;columnId=9085;contentId=19729;contentType=1;nowSpm=6029.PAGE_COMIC.9085.19729.1599227138976;afterSpm=6029.PAGE_COMIC.9085.0.1599227092239;pos=PAGE_COMIC;posName=动漫-二次元Tab;createTime=2020-09-04 21:45:38\n" +
                "\n" +
                "eventsType=COLUMN_TYPE_PRODUCT;eventsName=产品;area=B;mac=08-67-4E-C2-65-14;sn=12113500E05FF008674EC26514;userId=13144715;ca=null;userType=vod;parentColumnId=6029;columnId=6039;nowSpm=6029.PAGE_YNGDJSJPB.6039.0.1599227200980;afterSpm=6029.PAGE_YNGDJSJPB_GGW_1.0;columnCodeName=云南广电聚视界;pos=PAGE_RECOMMEND;posName=推荐Tab;createTime=2020-09-04 21:46:40\n" +
                "\n" +
                "eventsType=COLUMN_TYPE_TAB;eventsName=页签选项卡;area=B;mac=08-67-4E-C2-65-14;sn=12113500E05FF008674EC26514;userId=13144715;ca=null;userType=vod;parentColumnId=6029;columnId=9083;nowSpm=6029.PAGE_HWJC.9083.0.1599227208760;afterSpm=6029.PAGE_YNGDJSJPB.6039.0.1599227200980;columnCodeName=海外剧场-Tab;pos=PAGE_HWJC;posName=海外剧场-Tab;createTime=2020-09-04 21:46:48\n" +
                "\n" +
                "eventsType=AUTH_PRODUCT;eventsName=内容鉴权;area=B;mac=08674EC26514;sn=12113500E05FF008674EC26514;userId=13144715;ca=91110101635B8511;userType=vod;parentColumnId=6029;contentId=15249;videoId=0;contentType=1;productCode=-1;thirdProductCode=-1;result=false;message=鉴权未通过;operateType=auth_product;createTime=2020-09-04 21:47:34\n" +
                "\n" +
                "eventsType=PAGE_ALBUM_DETAILS;eventsName=专辑详情;area=B;mac=08674EC26514;sn=12113500E05FF008674EC26514;userId=13144715;ca=null;userType=vod;parentColumnId=6029;columnId=9087;contentId=15249;contentType=1;nowSpm=6029.PAGE_COMIC.9087.15249.1599227253935;afterSpm=6029.PAGE_COMIC.9087.0.1599227208760;pos=PAGE_COMIC;posName=动漫-二次元Tab;createTime=2020-09-04 21:47:33\n" +
                "\n" +
                "eventsType=COLUMN_TYPE_TAB;eventsName=页签选项卡;area=B;mac=08-67-4E-C2-65-14;sn=12113500E05FF008674EC26514;userId=13144715;ca=null;userType=vod;parentColumnId=6029;columnId=9083;nowSpm=6029.PAGE_HWJC.9083.0.1599227326510;afterSpm=6029.PAGE_SERIES.6247.0.1599227324369;columnCodeName=海外剧场-Tab;pos=PAGE_HWJC;posName=海外剧场-Tab;createTime=2020-09-04 21:48:46\n" +
                "\n" +
                "eventsType=COLUMN_TYPE_HISTORY_POINT;eventsName=新增点播记录;area=B;mac=08674EC26514;sn=12113500E05FF008674EC26514;userId=13144715;ca=null;userType=vod;parentColumnId=6029;albumId=11299;videoId=16847;operateType=add;timePosition=0;createTime=2020-09-04 21:49:18\n" +
                "\n" +
                "eventsType=PAGE_ALBUM_DETAILS;eventsName=专辑详情;area=B;mac=08674EC26514;sn=12113500E05FF008674EC26514;userId=13144715;ca=null;userType=vod;parentColumnId=6029;columnId=9097;contentId=11299;contentType=1;nowSpm=6029.PAGE_COMIC.9097.11299.1599227344866;afterSpm=6029.PAGE_COMIC.9097.0.1599227326510;pos=PAGE_COMIC;posName=动漫-二次元Tab;createTime=2020-09-04 21:49:04\n" +
                "\n" +
                "eventsType=AUTH_PRODUCT;eventsName=内容鉴权;area=B;mac=08674EC26514;sn=12113500E05FF008674EC26514;userId=13144715;ca=91110101635B8511;userType=vod;parentColumnId=6029;contentId=11299;videoId=0;contentType=1;productCode=SP1563847294402;thirdProductCode=PT20190731100641096;result=true;message=缓存鉴权通过;operateType=auth_product;createTime=2020-09-04 22:02:42\n" +
                "\n" +
                "eventsType=PAGE_ALBUM_DETAILS;eventsName=专辑详情;area=B;mac=08674EC26514;sn=12113500E05FF008674EC26514;userId=13144715;ca=null;userType=vod;parentColumnId=6029;columnId=9097;contentId=11299;contentType=1;nowSpm=6029.PAGE_COMIC.9097.11299.1599228162352;afterSpm=6029.PAGE_COMIC.9097.11299.1599227344866;pos=PAGE_COMIC;posName=动漫-二次元Tab;createTime=2020-09-04 22:02:42\n" +
                "\n" +
                "eventsType=COLUMN_TYPE_HISTORY_POINT;eventsName=获取点播记录;area=B;mac=08674EC26514;sn=12113500E05FF008674EC26514;userId=13144715;ca=null;userType=vod;parentColumnId=6029;albumId=11299;operateType=get;createTime=2020-09-04 22:02:42\n" +
                "\n" +
                "eventsType=COLUMN_TYPE_HISTORY_POINT;eventsName=获取点播记录;area=B;mac=08674EC26514;sn=12113500E05FF008674EC26514;userId=13144715;ca=null;userType=vod;parentColumnId=6029;albumId=11299;operateType=get;createTime=2020-09-04 22:10:45\n" +
                "\n" +
                "eventsType=AUTH_PRODUCT;eventsName=内容鉴权;area=B;mac=08674EC26514;sn=12113500E05FF008674EC26514;userId=13144715;ca=91110101635B8511;userType=vod;parentColumnId=6029;contentId=11299;videoId=0;contentType=1;productCode=SP1563847294402;thirdProductCode=PT20190731100641096;result=true;message=缓存鉴权通过;operateType=auth_product;createTime=2020-09-04 22:10:44\n" +
                "\n" +
                "eventsType=COLUMN_TYPE_HISTORY_POINT;eventsName=获取点播记录;area=B;mac=08674EC26514;sn=12113500E05FF008674EC26514;userId=13144715;ca=null;userType=vod;parentColumnId=6029;albumId=11299;operateType=get;createTime=2020-09-04 22:18:35\n" +
                "\n" +
                "eventsType=PAGE_ALBUM_DETAILS;eventsName=专辑详情;area=B;mac=08674EC26514;sn=12113500E05FF008674EC26514;userId=13144715;ca=null;userType=vod;parentColumnId=6029;columnId=9097;contentId=11299;contentType=1;nowSpm=6029.PAGE_COMIC.9097.11299.1599229115342;afterSpm=6029.PAGE_COMIC.9097.11299.1599228644463;pos=PAGE_COMIC;posName=动漫-二次元Tab;createTime=2020-09-04 22:18:35\n" +
                "\n" +
                "eventsType=COLUMN_TYPE_HISTORY_POINT;eventsName=新增点播记录;area=B;mac=08674EC26514;sn=12113500E05FF008674EC26514;userId=13144715;ca=null;userType=vod;parentColumnId=6029;albumId=11299;videoId=16853;operateType=add;timePosition=0;createTime=2020-09-04 22:18:41\n" +
                "\n" +
                "eventsType=AUTH_PRODUCT;eventsName=内容鉴权;area=B;mac=08674EC26514;sn=12113500E05FF008674EC26514;userId=13144715;ca=91110101635B8511;userType=vod;parentColumnId=6029;contentId=11299;videoId=0;contentType=1;productCode=SP1563847294402;thirdProductCode=PT20190731100641096;result=true;message=缓存鉴权通过;operateType=auth_product;createTime=2020-09-04 22:28:55\n" +
                "\n" +
                "eventsType=PAGE_ALBUM_DETAILS;eventsName=专辑详情;area=B;mac=08674EC26514;sn=12113500E05FF008674EC26514;userId=13144715;ca=null;userType=vod;parentColumnId=6029;columnId=9097;contentId=11299;contentType=1;nowSpm=6029.PAGE_COMIC.9097.11299.1599229735521;afterSpm=6029.PAGE_COMIC.9097.11299.1599229115342;pos=PAGE_COMIC;posName=动漫-二次元Tab;createTime=2020-09-04 22:28:55\n" +
                "\n" +
                "eventsType=COLUMN_TYPE_HISTORY_POINT;eventsName=新增点播记录;area=B;mac=08674EC26514;sn=12113500E05FF008674EC26514;userId=13144715;ca=null;userType=vod;parentColumnId=6029;albumId=11299;videoId=16833;operateType=add;timePosition=0;createTime=2020-09-04 22:29:02\n" +
                "\n" +
                "eventsType=COLUMN_TYPE_HISTORY_POINT;eventsName=获取点播记录;area=B;mac=08674EC26514;sn=12113500E05FF008674EC26514;userId=13144715;ca=null;userType=vod;parentColumnId=6029;albumId=11299;operateType=get;createTime=2020-09-04 22:38:59\n" +
                "\n" +
                "eventsType=PAGE_ALBUM_DETAILS;eventsName=专辑详情;area=B;mac=08674EC26514;sn=12113500E05FF008674EC26514;userId=13144715;ca=null;userType=vod;parentColumnId=6029;columnId=9097;contentId=11299;contentType=1;nowSpm=6029.PAGE_COMIC.9097.11299.1599230338652;afterSpm=6029.PAGE_COMIC.9097.11299.1599229735521;pos=PAGE_COMIC;posName=动漫-二次元Tab;createTime=2020-09-04 22:38:58\n" +
                "\n" +
                "eventsType=AUTH_PRODUCT;eventsName=内容鉴权;area=B;mac=08674EC26514;sn=12113500E05FF008674EC26514;userId=13144715;ca=91110101635B8511;userType=vod;parentColumnId=6029;contentId=11299;videoId=0;contentType=1;productCode=SP1563847294402;thirdProductCode=PT20190731100641096;result=true;message=缓存鉴权通过;operateType=auth_product;createTime=2020-09-04 22:38:59\n" +
                "\n" +
                "eventsType=COLUMN_TYPE_HISTORY_POINT;eventsName=获取点播记录;area=B;mac=08674EC26514;sn=12113500E05FF008674EC26514;userId=13144715;ca=null;userType=vod;parentColumnId=6029;albumId=11299;operateType=get;createTime=2020-09-04 22:48:16\n" +
                "\n" +
                "eventsType=PAGE_ALBUM_DETAILS;eventsName=专辑详情;area=B;mac=08674EC26514;sn=12113500E05FF008674EC26514;userId=13144715;ca=null;userType=vod;parentColumnId=6029;columnId=9097;contentId=11299;contentType=1;nowSpm=6029.PAGE_COMIC.9097.11299.1599230895201;afterSpm=6029.PAGE_COMIC.9097.11299.1599230338652;pos=PAGE_COMIC;posName=动漫-二次元Tab;createTime=2020-09-04 22:48:15\n" +
                "\n" +
                "eventsType=COLUMN_TYPE_HISTORY_POINT;eventsName=新增点播记录;area=B;mac=08674EC26514;sn=12113500E05FF008674EC26514;userId=13144715;ca=null;userType=vod;parentColumnId=6029;albumId=11299;videoId=16837;operateType=add;timePosition=0;createTime=2020-09-04 22:48:21\n" +
                "\n" +
                "eventsType=PAGE_ALBUM_DETAILS;eventsName=专辑详情;area=B;mac=08674EC26514;sn=12113500E05FF008674EC26514;userId=13144715;ca=null;userType=vod;parentColumnId=6029;columnId=9097;contentId=11299;contentType=1;nowSpm=6029.PAGE_COMIC.9097.11299.1599231473086;afterSpm=6029.PAGE_COMIC.9097.11299.1599230895201;pos=PAGE_COMIC;posName=动漫-二次元Tab;createTime=2020-09-04 22:57:53\n" +
                "\n" +
                "eventsType=COLUMN_TYPE_HISTORY_POINT;eventsName=获取点播记录;area=B;mac=08674EC26514;sn=12113500E05FF008674EC26514;userId=13144715;ca=null;userType=vod;parentColumnId=6029;albumId=11299;operateType=get;createTime=2020-09-04 22:57:53\n" +
                "\n" +
                "eventsType=COLUMN_TYPE_HISTORY_POINT;eventsName=新增点播记录;area=B;mac=08674EC26514;sn=12113500E05FF008674EC26514;userId=13144715;ca=null;userType=vod;parentColumnId=6029;albumId=11299;videoId=16839;operateType=add;timePosition=0;createTime=2020-09-04 22:57:59\n" +
                "\n" +
                "eventsType=AUTH_PRODUCT;eventsName=内容鉴权;area=B;mac=08674EC26514;sn=12113500E05FF008674EC26514;userId=13144715;ca=91110101635B8511;userType=vod;parentColumnId=6029;contentId=11299;videoId=0;contentType=1;productCode=SP1563847294402;thirdProductCode=PT20190731100641096;result=true;message=缓存鉴权通过;operateType=auth_product;createTime=2020-09-04 22:57:53\n" +
                "\n" +
                "eventsType=COLUMN_TYPE_HISTORY_POINT;eventsName=新增点播记录;area=B;mac=08674EC26514;sn=12113500E05FF008674EC26514;userId=13144715;ca=null;userType=vod;parentColumnId=6029;albumId=11299;videoId=16849;operateType=add;timePosition=0;createTime=2020-09-04 22:02:48\n" +
                "\n" +
                "eventsType=PAGE_ALBUM_DETAILS;eventsName=专辑详情;area=B;mac=08674EC26514;sn=12113500E05FF008674EC26514;userId=13144715;ca=null;userType=vod;parentColumnId=6029;columnId=9097;contentId=11299;contentType=1;nowSpm=6029.PAGE_COMIC.9097.11299.1599228644463;afterSpm=6029.PAGE_COMIC.9097.11299.1599228162352;pos=PAGE_COMIC;posName=动漫-二次元Tab;createTime=2020-09-04 22:10:44\n" +
                "\n" +
                "eventsType=COLUMN_TYPE_HISTORY_POINT;eventsName=新增点播记录;area=B;mac=08674EC26514;sn=12113500E05FF008674EC26514;userId=13144715;ca=null;userType=vod;parentColumnId=6029;albumId=11299;videoId=16851;operateType=add;timePosition=0;createTime=2020-09-04 22:10:51\n" +
                "\n" +
                "eventsType=AUTH_PRODUCT;eventsName=内容鉴权;area=B;mac=08674EC26514;sn=12113500E05FF008674EC26514;userId=13144715;ca=91110101635B8511;userType=vod;parentColumnId=6029;contentId=11299;videoId=0;contentType=1;productCode=SP1563847294402;thirdProductCode=PT20190731100641096;result=true;message=缓存鉴权通过;operateType=auth_product;createTime=2020-09-04 22:18:35\n" +
                "\n" +
                "eventsType=COLUMN_TYPE_HISTORY_POINT;eventsName=获取点播记录;area=B;mac=08674EC26514;sn=12113500E05FF008674EC26514;userId=13144715;ca=null;userType=vod;parentColumnId=6029;albumId=11299;operateType=get;createTime=2020-09-04 22:28:56\n" +
                "\n" +
                "eventsType=COLUMN_TYPE_HISTORY_POINT;eventsName=新增点播记录;area=B;mac=08674EC26514;sn=12113500E05FF008674EC26514;userId=13144715;ca=null;userType=vod;parentColumnId=6029;albumId=11299;videoId=16835;operateType=add;timePosition=0;createTime=2020-09-04 22:39:03\n" +
                "\n" +
                "eventsType=AUTH_PRODUCT;eventsName=内容鉴权;area=B;mac=08674EC26514;sn=12113500E05FF008674EC26514;userId=13144715;ca=91110101635B8511;userType=vod;parentColumnId=6029;contentId=11299;videoId=0;contentType=1;productCode=SP1563847294402;thirdProductCode=PT20190731100641096;result=true;message=缓存鉴权通过;operateType=auth_product;createTime=2020-09-04 22:48:15\n" +
                "\n" +
                "eventsType=COLUMN_TYPE_HISTORY_POINT;eventsName=获取点播记录;area=B;mac=08674EC26514;sn=12113500E05FF008674EC26514;userId=13144715;ca=null;userType=vod;parentColumnId=6029;albumId=11299;operateType=get;createTime=2020-09-04 23:08:12\n" +
                "\n" +
                "eventsType=AUTH_PRODUCT;eventsName=内容鉴权;area=B;mac=08674EC26514;sn=12113500E05FF008674EC26514;userId=13144715;ca=91110101635B8511;userType=vod;parentColumnId=6029;contentId=11299;videoId=0;contentType=1;productCode=SP1563847294402;thirdProductCode=PT20190731100641096;result=true;message=缓存鉴权通过;operateType=auth_product;createTime=2020-09-04 23:18:46\n" +
                "\n" +
                "eventsType=COLUMN_TYPE_HISTORY_POINT;eventsName=新增点播记录;area=B;mac=08674EC26514;sn=12113500E05FF008674EC26514;userId=13144715;ca=null;userType=vod;parentColumnId=6029;albumId=11299;videoId=16827;operateType=add;timePosition=0;createTime=2020-09-04 23:18:53\n" +
                "\n" +
                "eventsType=PAGE_ALBUM_DETAILS;eventsName=专辑详情;area=B;mac=08674EC26514;sn=12113500E05FF008674EC26514;userId=13144715;ca=null;userType=vod;parentColumnId=6029;columnId=9097;contentId=11299;contentType=1;nowSpm=6029.PAGE_COMIC.9097.11299.1599233398559;afterSpm=6029.PAGE_COMIC.9097.11299.1599232726368;pos=PAGE_COMIC;posName=动漫-二次元Tab;createTime=2020-09-04 23:29:58\n" +
                "\n" +
                "eventsType=PAGE_ALBUM_DETAILS;eventsName=专辑详情;area=B;mac=08674EC26514;sn=12113500E05FF008674EC26514;userId=13144715;ca=null;userType=vod;parentColumnId=6029;columnId=9097;contentId=11299;contentType=1;nowSpm=6029.PAGE_COMIC.9097.11299.1599233972800;afterSpm=6029.PAGE_COMIC.9097.11299.1599233398559;pos=PAGE_COMIC;posName=动漫-二次元Tab;createTime=2020-09-04 23:39:32\n" +
                "\n" +
                "eventsType=COLUMN_TYPE_HISTORY_POINT;eventsName=新增点播记录;area=B;mac=08674EC26514;sn=12113500E05FF008674EC26514;userId=13144715;ca=null;userType=vod;parentColumnId=6029;albumId=11299;videoId=16831;operateType=add;timePosition=0;createTime=2020-09-04 23:39:37\n" +
                "\n" +
                "eventsType=COLUMN_TYPE_HISTORY_POINT;eventsName=获取点播记录;area=B;mac=08674EC26514;sn=12113500E05FF008674EC26514;userId=13144715;ca=null;userType=vod;parentColumnId=6029;albumId=11299;operateType=get;createTime=2020-09-04 23:49:23\n" +
                "\n" +
                "eventsType=AUTH_PRODUCT;eventsName=内容鉴权;area=B;mac=08674EC26514;sn=12113500E05FF008674EC26514;userId=13144715;ca=91110101635B8511;userType=vod;parentColumnId=6029;contentId=11299;videoId=0;contentType=1;productCode=SP1563847294402;thirdProductCode=PT20190731100641096;result=true;message=缓存鉴权通过;operateType=auth_product;createTime=2020-09-04 23:49:23\n" +
                "\n" +
                "eventsType=COLUMN_TYPE_HISTORY_POINT;eventsName=新增点播记录;area=B;mac=08674EC26514;sn=12113500E05FF008674EC26514;userId=13144715;ca=null;userType=vod;parentColumnId=6029;albumId=11299;videoId=16821;operateType=add;timePosition=0;createTime=2020-09-04 23:49:29\n" +
                "\n" +
                "eventsType=AUTH_PRODUCT;eventsName=内容鉴权;area=B;mac=08674EC26514;sn=12113500E05FF008674EC26514;userId=13144715;ca=91110101635B8511;userType=vod;parentColumnId=6029;contentId=11299;videoId=0;contentType=1;productCode=SP1563847294402;thirdProductCode=PT20190731100641096;result=true;message=缓存鉴权通过;operateType=auth_product;createTime=2020-09-04 23:59:30\n" +
                "\n" +
                "eventsType=AUTH_PRODUCT;eventsName=内容鉴权;area=B;mac=08674EC26514;sn=12113500E05FF008674EC26514;userId=13144715;ca=91110101635B8511;userType=vod;parentColumnId=6029;contentId=11299;videoId=0;contentType=1;productCode=SP1563847294402;thirdProductCode=PT20190731100641096;result=true;message=缓存鉴权通过;operateType=auth_product;createTime=2020-09-04 23:08:12\n" +
                "\n" +
                "eventsType=PAGE_ALBUM_DETAILS;eventsName=专辑详情;area=B;mac=08674EC26514;sn=12113500E05FF008674EC26514;userId=13144715;ca=null;userType=vod;parentColumnId=6029;columnId=9097;contentId=11299;contentType=1;nowSpm=6029.PAGE_COMIC.9097.11299.1599232091939;afterSpm=6029.PAGE_COMIC.9097.11299.1599231473086;pos=PAGE_COMIC;posName=动漫-二次元Tab;createTime=2020-09-04 23:08:11\n" +
                "\n" +
                "eventsType=COLUMN_TYPE_HISTORY_POINT;eventsName=新增点播记录;area=B;mac=08674EC26514;sn=12113500E05FF008674EC26514;userId=13144715;ca=null;userType=vod;parentColumnId=6029;albumId=11299;videoId=16841;operateType=add;timePosition=0;createTime=2020-09-04 23:08:17\n" +
                "\n" +
                "eventsType=PAGE_ALBUM_DETAILS;eventsName=专辑详情;area=B;mac=08674EC26514;sn=12113500E05FF008674EC26514;userId=13144715;ca=null;userType=vod;parentColumnId=6029;columnId=9097;contentId=11299;contentType=1;nowSpm=6029.PAGE_COMIC.9097.11299.1599232726368;afterSpm=6029.PAGE_COMIC.9097.11299.1599232091939;pos=PAGE_COMIC;posName=动漫-二次元Tab;createTime=2020-09-04 23:18:46\n" +
                "\n" +
                "eventsType=COLUMN_TYPE_HISTORY_POINT;eventsName=获取点播记录;area=B;mac=08674EC26514;sn=12113500E05FF008674EC26514;userId=13144715;ca=null;userType=vod;parentColumnId=6029;albumId=11299;operateType=get;createTime=2020-09-04 23:18:46\n" +
                "\n" +
                "eventsType=COLUMN_TYPE_HISTORY_POINT;eventsName=获取点播记录;area=B;mac=08674EC26514;sn=12113500E05FF008674EC26514;userId=13144715;ca=null;userType=vod;parentColumnId=6029;albumId=11299;operateType=get;createTime=2020-09-04 23:29:59\n" +
                "\n" +
                "eventsType=AUTH_PRODUCT;eventsName=内容鉴权;area=B;mac=08674EC26514;sn=12113500E05FF008674EC26514;userId=13144715;ca=91110101635B8511;userType=vod;parentColumnId=6029;contentId=11299;videoId=0;contentType=1;productCode=SP1563847294402;thirdProductCode=PT20190731100641096;result=true;message=缓存鉴权通过;operateType=auth_product;createTime=2020-09-04 23:29:58\n" +
                "\n" +
                "eventsType=COLUMN_TYPE_HISTORY_POINT;eventsName=新增点播记录;area=B;mac=08674EC26514;sn=12113500E05FF008674EC26514;userId=13144715;ca=null;userType=vod;parentColumnId=6029;albumId=11299;videoId=16829;operateType=add;timePosition=0;createTime=2020-09-04 23:30:05\n" +
                "\n" +
                "eventsType=COLUMN_TYPE_HISTORY_POINT;eventsName=获取点播记录;area=B;mac=08674EC26514;sn=12113500E05FF008674EC26514;userId=13144715;ca=null;userType=vod;parentColumnId=6029;albumId=11299;operateType=get;createTime=2020-09-04 23:39:33\n" +
                "\n" +
                "eventsType=AUTH_PRODUCT;eventsName=内容鉴权;area=B;mac=08674EC26514;sn=12113500E05FF008674EC26514;userId=13144715;ca=91110101635B8511;userType=vod;parentColumnId=6029;contentId=11299;videoId=0;contentType=1;productCode=SP1563847294402;thirdProductCode=PT20190731100641096;result=true;message=缓存鉴权通过;operateType=auth_product;createTime=2020-09-04 23:39:33\n" +
                "\n" +
                "eventsType=PAGE_ALBUM_DETAILS;eventsName=专辑详情;area=B;mac=08674EC26514;sn=12113500E05FF008674EC26514;userId=13144715;ca=null;userType=vod;parentColumnId=6029;columnId=9097;contentId=11299;contentType=1;nowSpm=6029.PAGE_COMIC.9097.11299.1599234563351;afterSpm=6029.PAGE_COMIC.9097.11299.1599233972800;pos=PAGE_COMIC;posName=动漫-二次元Tab;createTime=2020-09-04 23:49:23\n" +
                "\n" +
                "eventsType=COLUMN_TYPE_HISTORY_POINT;eventsName=新增点播记录;area=B;mac=08674EC26514;sn=12113500E05FF008674EC26514;userId=13144715;ca=null;userType=vod;parentColumnId=6029;albumId=11299;videoId=16823;operateType=add;timePosition=0;createTime=2020-09-04 23:59:34\n";
    }

    private static List<String> getList(){
        String[] t = getText().split("\\n");
        return Arrays.asList(t).stream().filter(s->!s.isEmpty()).collect(Collectors.toList());
    }
    private static void run(){
        Process proc = null;
        try {
            proc = Runtime.getRuntime().exec("hadoop fs -cat /data2/logs/2020/09/04/*/*");
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line = null;
            PyArray pyArray = new PyArray(PyType.fromClass(PyString.class));
            while ((line = in.readLine()) != null) {
                System.out.println(line);
                pyArray.append(new PyString(line));
            }
            in.close();
            proc.waitFor();
            PythonInterpreter interpreter = new PythonInterpreter();
            interpreter.execfile("G:\\code\\hadoop_script_2.0\\validateUserData3.py");

            // 第一个参数为期望获得的函数（变量）的名字，第二个参数为期望返回的对象类型
            PyFunction pyFunction = interpreter.get("getHadoopLineData", PyFunction.class);
            int a = 5, b = 10;
            //调用函数，如果函数需要参数，在Java中必须先将参数转化为对应的“Python类型”

            PyObject pyobj = pyFunction.__call__(pyArray);
//        PyObject pyobj = pyFunction.__call__(new PyInteger(a), new PyInteger(b));
            System.out.println("the anwser is: " + pyobj);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return;
    }
    private static void tt(){
        PyArray pyArray = new PyArray(PyType.fromClass(PyString.class));
        for(String line : getText().split("\\n")){
            if(!StringUtils.isEmpty(line)){
                PyString pyString = new PyString(line);
                pyArray.__add__(pyString);
            }
        }
        PythonInterpreter interpreter = new PythonInterpreter();
        interpreter.execfile("G:\\code\\hadoop_script_2.0\\validateUserData3.py");
        // 第一个参数为期望获得的函数（变量）的名字，第二个参数为期望返回的对象类型
        PyFunction pyFunction = interpreter.get("getHadoopLineData", PyFunction.class);
        int a = 5, b = 10;
        //调用函数，如果函数需要参数，在Java中必须先将参数转化为对应的“Python类型”

        PyObject pyobj = pyFunction.__call__(pyArray);
//        PyObject pyobj = pyFunction.__call__(new PyInteger(a), new PyInteger(b));
        System.out.println("the anwser is: " + pyobj);
    }
    public static void main(String[] args) {
        System.out.println(StringUtils.removeAll(null, ":|-"));
        System.out.println(StringUtils.upperCase(StringUtils.removeAll("C8:8f:26-5a-B174", ":|-")));

    }
    private String t(){
        int i=1;
        try{
            return "a"+(i++);
        }catch (Exception e){
            return "b"+(i++);
        }finally {
            return "c"+(i++);
        }
    }
    public static void tt2(){
        List<Map<String,String>> contentList = new ArrayList<>();
        Map<CollectBean,List> CollectBeanMap = new HashMap<CollectBean,List>();
        for(String line:getList()){
            Map<String,String> map = new HashMap<String,String>();
            for(String content :line.split(";")){
                String[] s = content.split("=",0);
                map.put(s[0],s[1]);
            }
            CollectBean collectBean = new CollectBean(map.get("userId"),map.get("userType"),map.get("parentColumnId"),map.get("area"));
            if(CollectBeanMap.containsKey(collectBean)){
                CollectBeanMap.get(collectBean).add(map);
            }else {
                List<Map> list =new ArrayList<>();
                list.add(map);
                CollectBeanMap.put(collectBean,list);
            }
            contentList.add(map);
        }
//        CollectBeanMap.forEach((k,v)->{
//            v.stream().sorted()
//        });
        Map<String,List<Map>> m=contentList.stream().collect(Collectors.groupingBy(a->a.get("userId")+"_"+a.get("userType")+"_"+a.get("parentColumnId")+"_"+a.get("area")));
        m.forEach((k,v)->{
            System.out.println(k);
        });
        Map<String,List<Map>> m1=contentList.stream().collect(Collectors.groupingBy(a->a.get("userId")+"_"+a.get("userType")+"_"+a.get("parentColumnId")+"_"+a.get("area")));
        m.forEach((k,v)->{
            System.out.println(k);
        });
    }
    public static void tt3(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<Map<String,String>> list = getList().parallelStream().map(s->{
            Map<String,String> map = new HashMap<String, String>();
            for(String kv : s.split(";")){
                String[] t = kv.split("=",0);
                map.put(t[0],t[1]);
            }
            if(map.containsKey("nowSpm")){
                String[] spm=map.get("nowSpm").split("\\.");
                if(spm.length>=5){
                    map.put("seq",spm[4]);
                }else{
                    try {
                        map.put("seq", String.valueOf(dateFormat.parse(map.get("createTime"))));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }else{
                switch(map.get("eventsType")){
                    case "AUTH_PRODUCT":map.put("seq","4");break;
                    case "COLUMN_TYPE_HISTORY_POINT":
                        if(map.containsKey("operateType")){
                            if(map.get("operateType") == "add"){
                                if(map.get("timePosition") == "0")map.put("seq","6");
                                else{map.put("seq","1");} // # timePosition 非0
                            }else{map.put("seq","5");} // # operateType=get
                        }else{
                            try {
                                map.put("seq",String.valueOf(dateFormat.parse(map.get("createTime"))));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    default:
                        try {
                            map.put("seq",String.valueOf(dateFormat.parse(map.get("createTime"))));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                }
            }
            return map;
        }).collect(Collectors.toList());
        Map<String,List<Map>> userMap = list.stream().collect(Collectors.groupingBy(s->
                s.get("userId")+"_"+s.get("userType")+"_"+s.get("parentColumnId")+"_"+s.get("area")));
        //对用户下操作时间进行分组
        Map<CollectBean,List<Map<String,String>>> userMap2 = list.stream().collect(Collectors.groupingBy(s -> {
            return new CollectBean(s.get("userId"), s.get("userType"), s.get("parentColumnId"), s.get("area"));
        }));
        List<CollectBean> collectBeans = new ArrayList<>();
        userMap2.forEach((k,v)->{
            //对v进行重排序
            Map<String, List<Map<String, String>>> timeMap = v.stream().collect(Collectors.groupingBy(s -> s.get("createTime")));
            List<String> timeSortList = timeMap.keySet().stream().sorted().collect(Collectors.toList());
            k.setTimeSortList(timeSortList);
            //对map进行排序
            timeMap.forEach((k1,v1)->{
                Collections.sort(v1, new Comparator<Map<String, String>>() {
                    public int compare(Map<String, String> o1, Map<String, String> o2) {
                        String sort1=o1.get("seq");
                        String sort2=o2.get("seq");
                        return sort1.compareTo(sort2);
                    }
                });
                k.addSortTimeMap(k1,v1);
            });
            collectBeans.add(k);
        });
        //排好序了，遍历结果
        collectBeans.forEach(s->{
            System.out.println(s);

        });

    }
}
