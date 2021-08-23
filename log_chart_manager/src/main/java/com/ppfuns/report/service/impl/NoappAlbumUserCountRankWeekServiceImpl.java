package com.ppfuns.report.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ppfuns.report.mapper.NoappAlbumUserCountRankWeekMapper;
import com.ppfuns.report.entity.NoappAlbumUserCountRankWeek;
import com.ppfuns.report.service.NoappAlbumUserCountRankWeekService;
import org.springframework.stereotype.Service;

/**
 * 所有专区专辑播放用户数周排行榜(NoappAlbumUserCountRankWeek)表服务实现类
 *
 * @author jdq
 * @since 2021-07-08 11:39:40
 */
@Service("noappAlbumUserCountRankWeekService")
public class NoappAlbumUserCountRankWeekServiceImpl extends ServiceImpl<NoappAlbumUserCountRankWeekMapper, NoappAlbumUserCountRankWeek> implements NoappAlbumUserCountRankWeekService {

}
