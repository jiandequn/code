package com.ppfuns.report.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ppfuns.report.mapper.NoappContentTypeAlbumUserCountRankMonthMapper;
import com.ppfuns.report.entity.NoappContentTypeAlbumUserCountRankMonth;
import com.ppfuns.report.service.NoappContentTypeAlbumUserCountRankMonthService;
import org.springframework.stereotype.Service;

/**
 * 所有专区类型下专辑播放用户数月排行榜(NoappContentTypeAlbumUserCountRankMonth)表服务实现类
 *
 * @author jdq
 * @since 2021-07-06 17:03:15
 */
@Service("noappContentTypeAlbumUserCountRankMonthService")
public class NoappContentTypeAlbumUserCountRankMonthServiceImpl extends ServiceImpl<NoappContentTypeAlbumUserCountRankMonthMapper, NoappContentTypeAlbumUserCountRankMonth> implements NoappContentTypeAlbumUserCountRankMonthService {

}
