package com.ppfuns.report.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ppfuns.report.mapper.AppInletPositionCountDayMapper;
import com.ppfuns.report.entity.AppInletPositionCountDay;
import com.ppfuns.report.service.AppInletPositionCountDayService;
import org.springframework.stereotype.Service;

/**
 * 用户来源按日统计(AppInletPositionCountDay)表服务实现类
 *
 * @author jdq
 * @since 2021-05-25 09:56:29
 */
@Service("appInletPositionCountDayService")
public class AppInletPositionCountDayServiceImpl extends ServiceImpl<AppInletPositionCountDayMapper, AppInletPositionCountDay> implements AppInletPositionCountDayService {

}
