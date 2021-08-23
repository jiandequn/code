package com.ppfuns.report.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ppfuns.report.mapper.AppContentTypeAlbumUserCountRankWeekMapper;
import com.ppfuns.report.entity.AppContentTypeAlbumUserCountRankWeek;
import com.ppfuns.report.service.AppContentTypeAlbumUserCountRankWeekService;
import org.springframework.stereotype.Service;

/**
 * 专区类型下专辑播放用户数周排行榜(AppContentTypeAlbumUserCountRankWeek)表服务实现类
 *
 * @author jdq
 * @since 2021-07-06 16:58:15
 */
@Service("appContentTypeAlbumUserCountRankWeekService")
public class AppContentTypeAlbumUserCountRankWeekServiceImpl extends ServiceImpl<AppContentTypeAlbumUserCountRankWeekMapper, AppContentTypeAlbumUserCountRankWeek> implements AppContentTypeAlbumUserCountRankWeekService {

}
