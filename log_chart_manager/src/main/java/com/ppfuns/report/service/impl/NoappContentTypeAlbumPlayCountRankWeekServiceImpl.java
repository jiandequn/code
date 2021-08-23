package com.ppfuns.report.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ppfuns.report.mapper.NoappContentTypeAlbumPlayCountRankWeekMapper;
import com.ppfuns.report.entity.NoappContentTypeAlbumPlayCountRankWeek;
import com.ppfuns.report.service.NoappContentTypeAlbumPlayCountRankWeekService;
import org.springframework.stereotype.Service;

/**
 * 所有专区类型下专辑播放次数周排行榜(NoappContentTypeAlbumPlayCountRankWeek)表服务实现类
 *
 * @author jdq
 * @since 2021-07-06 17:03:14
 */
@Service("noappContentTypeAlbumPlayCountRankWeekService")
public class NoappContentTypeAlbumPlayCountRankWeekServiceImpl extends ServiceImpl<NoappContentTypeAlbumPlayCountRankWeekMapper, NoappContentTypeAlbumPlayCountRankWeek> implements NoappContentTypeAlbumPlayCountRankWeekService {

}
