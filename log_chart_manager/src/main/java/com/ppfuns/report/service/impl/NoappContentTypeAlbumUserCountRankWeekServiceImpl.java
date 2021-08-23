package com.ppfuns.report.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ppfuns.report.mapper.NoappContentTypeAlbumUserCountRankWeekMapper;
import com.ppfuns.report.entity.NoappContentTypeAlbumUserCountRankWeek;
import com.ppfuns.report.service.NoappContentTypeAlbumUserCountRankWeekService;
import org.springframework.stereotype.Service;

/**
 * 所有专区类型下专辑播放用户数周排行榜(NoappContentTypeAlbumUserCountRankWeek)表服务实现类
 *
 * @author jdq
 * @since 2021-07-06 17:03:16
 */
@Service("noappContentTypeAlbumUserCountRankWeekService")
public class NoappContentTypeAlbumUserCountRankWeekServiceImpl extends ServiceImpl<NoappContentTypeAlbumUserCountRankWeekMapper, NoappContentTypeAlbumUserCountRankWeek> implements NoappContentTypeAlbumUserCountRankWeekService {

}
