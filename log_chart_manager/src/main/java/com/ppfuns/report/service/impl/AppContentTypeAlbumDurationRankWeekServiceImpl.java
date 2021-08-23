package com.ppfuns.report.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ppfuns.report.mapper.AppContentTypeAlbumDurationRankWeekMapper;
import com.ppfuns.report.entity.AppContentTypeAlbumDurationRankWeek;
import com.ppfuns.report.service.AppContentTypeAlbumDurationRankWeekService;
import org.springframework.stereotype.Service;

/**
 * 专区类型下专辑播放时长周排行榜(AppContentTypeAlbumDurationRankWeek)表服务实现类
 *
 * @author jdq
 * @since 2021-07-14 15:20:46
 */
@Service("appContentTypeAlbumDurationRankWeekService")
public class AppContentTypeAlbumDurationRankWeekServiceImpl extends ServiceImpl<AppContentTypeAlbumDurationRankWeekMapper, AppContentTypeAlbumDurationRankWeek> implements AppContentTypeAlbumDurationRankWeekService {

}
