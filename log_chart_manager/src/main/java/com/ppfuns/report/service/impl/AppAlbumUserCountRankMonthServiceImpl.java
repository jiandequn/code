package com.ppfuns.report.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ppfuns.report.mapper.AppAlbumUserCountRankMonthMapper;
import com.ppfuns.report.entity.AppAlbumUserCountRankMonth;
import com.ppfuns.report.service.AppAlbumUserCountRankMonthService;
import org.springframework.stereotype.Service;

/**
 * 专区专辑播放用户数月排行榜(AppAlbumUserCountRankMonth)表服务实现类
 *
 * @author jdq
 * @since 2021-07-06 17:00:41
 */
@Service("appAlbumUserCountRankMonthService")
public class AppAlbumUserCountRankMonthServiceImpl extends ServiceImpl<AppAlbumUserCountRankMonthMapper, AppAlbumUserCountRankMonth> implements AppAlbumUserCountRankMonthService {

}
