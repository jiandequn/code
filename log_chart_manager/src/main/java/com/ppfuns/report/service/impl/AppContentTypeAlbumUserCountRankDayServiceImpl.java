package com.ppfuns.report.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ppfuns.report.mapper.AppContentTypeAlbumUserCountRankDayMapper;
import com.ppfuns.report.entity.AppContentTypeAlbumUserCountRankDay;
import com.ppfuns.report.service.AppContentTypeAlbumUserCountRankDayService;
import org.springframework.stereotype.Service;

/**
 * 专区类型下专辑播放用户数日排行榜(AppContentTypeAlbumUserCountRankDay)表服务实现类
 *
 * @author jdq
 * @since 2021-07-06 16:58:15
 */
@Service("appContentTypeAlbumUserCountRankDayService")
public class AppContentTypeAlbumUserCountRankDayServiceImpl extends ServiceImpl<AppContentTypeAlbumUserCountRankDayMapper, AppContentTypeAlbumUserCountRankDay> implements AppContentTypeAlbumUserCountRankDayService {

}
