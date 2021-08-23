package com.ppfuns.report.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ppfuns.report.mapper.NoappAlbumDurationRankWeekMapper;
import com.ppfuns.report.entity.NoappAlbumDurationRankWeek;
import com.ppfuns.report.service.NoappAlbumDurationRankWeekService;
import org.springframework.stereotype.Service;

/**
 * 所有专区专辑播放时长周排行榜(NoappAlbumDurationRankWeek)表服务实现类
 *
 * @author jdq
 * @since 2021-07-14 15:21:58
 */
@Service("noappAlbumDurationRankWeekService")
public class NoappAlbumDurationRankWeekServiceImpl extends ServiceImpl<NoappAlbumDurationRankWeekMapper, NoappAlbumDurationRankWeek> implements NoappAlbumDurationRankWeekService {

}
