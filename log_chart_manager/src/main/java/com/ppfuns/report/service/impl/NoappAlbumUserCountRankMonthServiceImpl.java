package com.ppfuns.report.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ppfuns.report.mapper.NoappAlbumUserCountRankMonthMapper;
import com.ppfuns.report.entity.NoappAlbumUserCountRankMonth;
import com.ppfuns.report.service.NoappAlbumUserCountRankMonthService;
import org.springframework.stereotype.Service;

/**
 * 所有专区专辑播放用户数月排行榜(NoappAlbumUserCountRankMonth)表服务实现类
 *
 * @author jdq
 * @since 2021-07-08 11:39:39
 */
@Service("noappAlbumUserCountRankMonthService")
public class NoappAlbumUserCountRankMonthServiceImpl extends ServiceImpl<NoappAlbumUserCountRankMonthMapper, NoappAlbumUserCountRankMonth> implements NoappAlbumUserCountRankMonthService {

}
