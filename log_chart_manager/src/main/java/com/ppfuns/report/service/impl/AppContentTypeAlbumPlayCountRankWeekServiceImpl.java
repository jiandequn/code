package com.ppfuns.report.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ppfuns.report.mapper.AppContentTypeAlbumPlayCountRankWeekMapper;
import com.ppfuns.report.entity.AppContentTypeAlbumPlayCountRankWeek;
import com.ppfuns.report.service.AppContentTypeAlbumPlayCountRankWeekService;
import org.springframework.stereotype.Service;

/**
 * 专区类型下专辑播放次数周排行榜(AppContentTypeAlbumPlayCountRankWeek)表服务实现类
 *
 * @author jdq
 * @since 2021-07-06 16:58:14
 */
@Service("appContentTypeAlbumPlayCountRankWeekService")
public class AppContentTypeAlbumPlayCountRankWeekServiceImpl extends ServiceImpl<AppContentTypeAlbumPlayCountRankWeekMapper, AppContentTypeAlbumPlayCountRankWeek> implements AppContentTypeAlbumPlayCountRankWeekService {

}
