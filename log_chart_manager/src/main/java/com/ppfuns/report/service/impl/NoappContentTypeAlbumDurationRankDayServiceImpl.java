package com.ppfuns.report.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ppfuns.report.mapper.NoappContentTypeAlbumDurationRankDayMapper;
import com.ppfuns.report.entity.NoappContentTypeAlbumDurationRankDay;
import com.ppfuns.report.service.NoappContentTypeAlbumDurationRankDayService;
import org.springframework.stereotype.Service;

/**
 * 所有专区类型下专辑播放时长周排行榜(NoappContentTypeAlbumDurationRankDay)表服务实现类
 *
 * @author jdq
 * @since 2021-07-14 15:21:59
 */
@Service("noappContentTypeAlbumDurationRankDayService")
public class NoappContentTypeAlbumDurationRankDayServiceImpl extends ServiceImpl<NoappContentTypeAlbumDurationRankDayMapper, NoappContentTypeAlbumDurationRankDay> implements NoappContentTypeAlbumDurationRankDayService {

}
