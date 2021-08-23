package com.ppfuns.report.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ppfuns.report.mapper.NoappAlbumDurationRankDayMapper;
import com.ppfuns.report.entity.NoappAlbumDurationRankDay;
import com.ppfuns.report.service.NoappAlbumDurationRankDayService;
import org.springframework.stereotype.Service;

/**
 * 所有专区专辑播放时长周排行榜(NoappAlbumDurationRankDay)表服务实现类
 *
 * @author jdq
 * @since 2021-07-14 15:21:57
 */
@Service("noappAlbumDurationRankDayService")
public class NoappAlbumDurationRankDayServiceImpl extends ServiceImpl<NoappAlbumDurationRankDayMapper, NoappAlbumDurationRankDay> implements NoappAlbumDurationRankDayService {

}
