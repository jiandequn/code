package com.ppfuns.report.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ppfuns.report.mapper.NoappAlbumUserCountRankDayMapper;
import com.ppfuns.report.entity.NoappAlbumUserCountRankDay;
import com.ppfuns.report.service.NoappAlbumUserCountRankDayService;
import org.springframework.stereotype.Service;

/**
 * 所有专区专辑播放用户数月排行榜(NoappAlbumUserCountRankDay)表服务实现类
 *
 * @author jdq
 * @since 2021-07-08 14:43:46
 */
@Service("noappAlbumUserCountRankDayService")
public class NoappAlbumUserCountRankDayServiceImpl extends ServiceImpl<NoappAlbumUserCountRankDayMapper, NoappAlbumUserCountRankDay> implements NoappAlbumUserCountRankDayService {

}
