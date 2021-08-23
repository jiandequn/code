package com.ppfuns.report.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ppfuns.report.mapper.AppContentTypeAlbumDurationRankDayMapper;
import com.ppfuns.report.entity.AppContentTypeAlbumDurationRankDay;
import com.ppfuns.report.service.AppContentTypeAlbumDurationRankDayService;
import org.springframework.stereotype.Service;

/**
 * 专区类型下专辑播放时长周排行榜(AppContentTypeAlbumDurationRankDay)表服务实现类
 *
 * @author jdq
 * @since 2021-07-14 15:20:45
 */
@Service("appContentTypeAlbumDurationRankDayService")
public class AppContentTypeAlbumDurationRankDayServiceImpl extends ServiceImpl<AppContentTypeAlbumDurationRankDayMapper, AppContentTypeAlbumDurationRankDay> implements AppContentTypeAlbumDurationRankDayService {

}
