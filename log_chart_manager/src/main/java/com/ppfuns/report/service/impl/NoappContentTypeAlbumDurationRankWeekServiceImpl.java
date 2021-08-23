package com.ppfuns.report.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ppfuns.report.mapper.NoappContentTypeAlbumDurationRankWeekMapper;
import com.ppfuns.report.entity.NoappContentTypeAlbumDurationRankWeek;
import com.ppfuns.report.service.NoappContentTypeAlbumDurationRankWeekService;
import org.springframework.stereotype.Service;

/**
 * 所有专区类型下专辑播放时长周排行榜(NoappContentTypeAlbumDurationRankWeek)表服务实现类
 *
 * @author jdq
 * @since 2021-07-14 15:22:00
 */
@Service("noappContentTypeAlbumDurationRankWeekService")
public class NoappContentTypeAlbumDurationRankWeekServiceImpl extends ServiceImpl<NoappContentTypeAlbumDurationRankWeekMapper, NoappContentTypeAlbumDurationRankWeek> implements NoappContentTypeAlbumDurationRankWeekService {

}
