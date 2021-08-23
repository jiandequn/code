package com.ppfuns.report.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ppfuns.report.mapper.AppDetailPageAlbumContentTypeCountDayMapper;
import com.ppfuns.report.entity.AppDetailPageAlbumContentTypeCountDay;
import com.ppfuns.report.service.AppDetailPageAlbumContentTypeCountDayService;
import org.springframework.stereotype.Service;

/**
 * 按日统计专辑详情页专辑类型访问(AppDetailPageAlbumContentTypeCountDay)表服务实现类
 *
 * @author jdq
 * @since 2021-07-28 16:22:17
 */
@Service("appDetailPageAlbumContentTypeCountDayService")
public class AppDetailPageAlbumContentTypeCountDayServiceImpl extends ServiceImpl<AppDetailPageAlbumContentTypeCountDayMapper, AppDetailPageAlbumContentTypeCountDay> implements AppDetailPageAlbumContentTypeCountDayService {

}
