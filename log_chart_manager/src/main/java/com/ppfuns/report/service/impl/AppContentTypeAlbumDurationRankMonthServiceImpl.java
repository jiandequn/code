package com.ppfuns.report.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ppfuns.report.mapper.AppContentTypeAlbumDurationRankMonthMapper;
import com.ppfuns.report.entity.AppContentTypeAlbumDurationRankMonth;
import com.ppfuns.report.service.AppContentTypeAlbumDurationRankMonthService;
import org.springframework.stereotype.Service;

/**
 * 专区类型下专辑播放时长月排行榜(AppContentTypeAlbumDurationRankMonth)表服务实现类
 *
 * @author jdq
 * @since 2021-07-14 15:20:46
 */
@Service("appContentTypeAlbumDurationRankMonthService")
public class AppContentTypeAlbumDurationRankMonthServiceImpl extends ServiceImpl<AppContentTypeAlbumDurationRankMonthMapper, AppContentTypeAlbumDurationRankMonth> implements AppContentTypeAlbumDurationRankMonthService {

}
