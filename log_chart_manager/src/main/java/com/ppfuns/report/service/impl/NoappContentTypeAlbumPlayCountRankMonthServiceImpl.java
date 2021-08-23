package com.ppfuns.report.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ppfuns.report.mapper.NoappContentTypeAlbumPlayCountRankMonthMapper;
import com.ppfuns.report.entity.NoappContentTypeAlbumPlayCountRankMonth;
import com.ppfuns.report.service.NoappContentTypeAlbumPlayCountRankMonthService;
import org.springframework.stereotype.Service;

/**
 * 所有专区类型下专辑播放次数月排行榜(NoappContentTypeAlbumPlayCountRankMonth)表服务实现类
 *
 * @author jdq
 * @since 2021-07-06 17:03:14
 */
@Service("noappContentTypeAlbumPlayCountRankMonthService")
public class NoappContentTypeAlbumPlayCountRankMonthServiceImpl extends ServiceImpl<NoappContentTypeAlbumPlayCountRankMonthMapper, NoappContentTypeAlbumPlayCountRankMonth> implements NoappContentTypeAlbumPlayCountRankMonthService {

}
