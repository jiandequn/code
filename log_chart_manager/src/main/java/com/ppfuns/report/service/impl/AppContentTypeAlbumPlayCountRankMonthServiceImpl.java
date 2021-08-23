package com.ppfuns.report.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ppfuns.report.mapper.AppContentTypeAlbumPlayCountRankMonthMapper;
import com.ppfuns.report.entity.AppContentTypeAlbumPlayCountRankMonth;
import com.ppfuns.report.service.AppContentTypeAlbumPlayCountRankMonthService;
import org.springframework.stereotype.Service;

/**
 * 专区类型下专辑播放次数月排行榜(AppContentTypeAlbumPlayCountRankMonth)表服务实现类
 *
 * @author jdq
 * @since 2021-07-06 16:58:14
 */
@Service("appContentTypeAlbumPlayCountRankMonthService")
public class AppContentTypeAlbumPlayCountRankMonthServiceImpl extends ServiceImpl<AppContentTypeAlbumPlayCountRankMonthMapper, AppContentTypeAlbumPlayCountRankMonth> implements AppContentTypeAlbumPlayCountRankMonthService {

}
