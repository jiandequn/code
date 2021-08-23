package com.ppfuns.report.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ppfuns.report.mapper.AppAlbumDurationRankMonthMapper;
import com.ppfuns.report.entity.AppAlbumDurationRankMonth;
import com.ppfuns.report.service.AppAlbumDurationRankMonthService;
import org.springframework.stereotype.Service;

/**
 * 专区专辑播放时长月排行榜(AppAlbumDurationRankMonth)表服务实现类
 *
 * @author jdq
 * @since 2021-07-14 15:19:00
 */
@Service("appAlbumDurationRankMonthService")
public class AppAlbumDurationRankMonthServiceImpl extends ServiceImpl<AppAlbumDurationRankMonthMapper, AppAlbumDurationRankMonth> implements AppAlbumDurationRankMonthService {

}
