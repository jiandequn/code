package com.ppfuns.report.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ppfuns.report.mapper.NoappContentTypeAlbumUserCountRankDayMapper;
import com.ppfuns.report.entity.NoappContentTypeAlbumUserCountRankDay;
import com.ppfuns.report.service.NoappContentTypeAlbumUserCountRankDayService;
import org.springframework.stereotype.Service;

/**
 * 所有专区类型下专辑播放用户数日排行榜(NoappContentTypeAlbumUserCountRankDay)表服务实现类
 *
 * @author jdq
 * @since 2021-07-06 17:03:15
 */
@Service("noappContentTypeAlbumUserCountRankDayService")
public class NoappContentTypeAlbumUserCountRankDayServiceImpl extends ServiceImpl<NoappContentTypeAlbumUserCountRankDayMapper, NoappContentTypeAlbumUserCountRankDay> implements NoappContentTypeAlbumUserCountRankDayService {

}
