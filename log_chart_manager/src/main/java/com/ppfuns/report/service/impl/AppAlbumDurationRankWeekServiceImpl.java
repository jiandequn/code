package com.ppfuns.report.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ppfuns.report.mapper.AppAlbumDurationRankWeekMapper;
import com.ppfuns.report.entity.AppAlbumDurationRankWeek;
import com.ppfuns.report.service.AppAlbumDurationRankWeekService;
import org.springframework.stereotype.Service;

/**
 * 专区专辑播放时长周排行榜(AppAlbumDurationRankWeek)表服务实现类
 *
 * @author jdq
 * @since 2021-07-14 15:18:59
 */
@Service("appAlbumDurationRankWeekService")
public class AppAlbumDurationRankWeekServiceImpl extends ServiceImpl<AppAlbumDurationRankWeekMapper, AppAlbumDurationRankWeek> implements AppAlbumDurationRankWeekService {

}
