package com.ppfuns.report.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ppfuns.report.mapper.AppContentTypeAlbumPlayCountRankDayMapper;
import com.ppfuns.report.entity.AppContentTypeAlbumPlayCountRankDay;
import com.ppfuns.report.service.AppContentTypeAlbumPlayCountRankDayService;
import org.springframework.stereotype.Service;

/**
 * 专区类型下专辑播放次数日排行榜(AppContentTypeAlbumPlayCountRankDay)表服务实现类
 *
 * @author jdq
 * @since 2021-07-06 16:58:13
 */
@Service("appContentTypeAlbumPlayCountRankDayService")
public class AppContentTypeAlbumPlayCountRankDayServiceImpl extends ServiceImpl<AppContentTypeAlbumPlayCountRankDayMapper, AppContentTypeAlbumPlayCountRankDay> implements AppContentTypeAlbumPlayCountRankDayService {

}
