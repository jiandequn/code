package com.ppfuns.report.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ppfuns.report.mapper.AppContentTypeAlbumUserCountRankMonthMapper;
import com.ppfuns.report.entity.AppContentTypeAlbumUserCountRankMonth;
import com.ppfuns.report.service.AppContentTypeAlbumUserCountRankMonthService;
import org.springframework.stereotype.Service;

/**
 * 专区类型下专辑播放用户数月排行榜(AppContentTypeAlbumUserCountRankMonth)表服务实现类
 *
 * @author jdq
 * @since 2021-07-06 16:58:15
 */
@Service("appContentTypeAlbumUserCountRankMonthService")
public class AppContentTypeAlbumUserCountRankMonthServiceImpl extends ServiceImpl<AppContentTypeAlbumUserCountRankMonthMapper, AppContentTypeAlbumUserCountRankMonth> implements AppContentTypeAlbumUserCountRankMonthService {

}
