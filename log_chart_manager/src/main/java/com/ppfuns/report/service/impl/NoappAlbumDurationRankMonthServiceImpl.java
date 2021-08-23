package com.ppfuns.report.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ppfuns.report.mapper.NoappAlbumDurationRankMonthMapper;
import com.ppfuns.report.entity.NoappAlbumDurationRankMonth;
import com.ppfuns.report.service.NoappAlbumDurationRankMonthService;
import org.springframework.stereotype.Service;

/**
 * 所有专区专辑播放时长月排行榜(NoappAlbumDurationRankMonth)表服务实现类
 *
 * @author jdq
 * @since 2021-07-14 15:21:58
 */
@Service("noappAlbumDurationRankMonthService")
public class NoappAlbumDurationRankMonthServiceImpl extends ServiceImpl<NoappAlbumDurationRankMonthMapper, NoappAlbumDurationRankMonth> implements NoappAlbumDurationRankMonthService {

}
