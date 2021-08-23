package com.ppfuns.report.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ppfuns.report.mapper.NoappContentTypeAlbumDurationRankMonthMapper;
import com.ppfuns.report.entity.NoappContentTypeAlbumDurationRankMonth;
import com.ppfuns.report.service.NoappContentTypeAlbumDurationRankMonthService;
import org.springframework.stereotype.Service;

/**
 * 所有专区类型下专辑播放时长月排行榜(NoappContentTypeAlbumDurationRankMonth)表服务实现类
 *
 * @author jdq
 * @since 2021-07-14 15:21:59
 */
@Service("noappContentTypeAlbumDurationRankMonthService")
public class NoappContentTypeAlbumDurationRankMonthServiceImpl extends ServiceImpl<NoappContentTypeAlbumDurationRankMonthMapper, NoappContentTypeAlbumDurationRankMonth> implements NoappContentTypeAlbumDurationRankMonthService {

}
