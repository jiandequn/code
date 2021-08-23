package com.ppfuns.report.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ppfuns.report.mapper.AppAlbumDurationRankDayMapper;
import com.ppfuns.report.entity.AppAlbumDurationRankDay;
import com.ppfuns.report.service.AppAlbumDurationRankDayService;
import org.springframework.stereotype.Service;

/**
 * 专区专辑播放时长周排行榜(AppAlbumDurationRankDay)表服务实现类
 *
 * @author jdq
 * @since 2021-07-14 15:19:00
 */
@Service("appAlbumDurationRankDayService")
public class AppAlbumDurationRankDayServiceImpl extends ServiceImpl<AppAlbumDurationRankDayMapper, AppAlbumDurationRankDay> implements AppAlbumDurationRankDayService {

}
