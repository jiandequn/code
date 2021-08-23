package com.ppfuns.report.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ppfuns.report.mapper.AppDetailPageCountDayMapper;
import com.ppfuns.report.entity.AppDetailPageCountDay;
import com.ppfuns.report.service.AppDetailPageCountDayService;
import org.springframework.stereotype.Service;

/**
 * 按日统计详情访问(AppDetailPageCountDay)表服务实现类
 *
 * @author jdq
 * @since 2021-06-15 18:23:59
 */
@Service("appDetailPageCountDayService")
public class AppDetailPageCountDayServiceImpl extends ServiceImpl<AppDetailPageCountDayMapper, AppDetailPageCountDay> implements AppDetailPageCountDayService {

}
