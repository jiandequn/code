package com.ppfuns.report.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ppfuns.report.mapper.AppDetailPageAlbumContentTypeCountWeekMapper;
import com.ppfuns.report.entity.AppDetailPageAlbumContentTypeCountWeek;
import com.ppfuns.report.service.AppDetailPageAlbumContentTypeCountWeekService;
import org.springframework.stereotype.Service;

/**
 * 按周统计专辑详情页专辑类型访问(AppDetailPageAlbumContentTypeCountWeek)表服务实现类
 *
 * @author jdq
 * @since 2021-07-28 16:22:17
 */
@Service("appDetailPageAlbumContentTypeCountWeekService")
public class AppDetailPageAlbumContentTypeCountWeekServiceImpl extends ServiceImpl<AppDetailPageAlbumContentTypeCountWeekMapper, AppDetailPageAlbumContentTypeCountWeek> implements AppDetailPageAlbumContentTypeCountWeekService {

}
