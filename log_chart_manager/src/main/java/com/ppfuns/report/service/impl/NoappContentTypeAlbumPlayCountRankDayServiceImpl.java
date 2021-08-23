package com.ppfuns.report.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ppfuns.report.mapper.NoappContentTypeAlbumPlayCountRankDayMapper;
import com.ppfuns.report.entity.NoappContentTypeAlbumPlayCountRankDay;
import com.ppfuns.report.service.NoappContentTypeAlbumPlayCountRankDayService;
import org.springframework.stereotype.Service;

/**
 * 所有专区类型下专辑播放次数日排行榜(NoappContentTypeAlbumPlayCountRankDay)表服务实现类
 *
 * @author jdq
 * @since 2021-07-06 17:03:14
 */
@Service("noappContentTypeAlbumPlayCountRankDayService")
public class NoappContentTypeAlbumPlayCountRankDayServiceImpl extends ServiceImpl<NoappContentTypeAlbumPlayCountRankDayMapper, NoappContentTypeAlbumPlayCountRankDay> implements NoappContentTypeAlbumPlayCountRankDayService {

}
