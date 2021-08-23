package com.ppfuns.report.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ppfuns.report.mapper.AppInletCountDayMapper;
import com.ppfuns.report.entity.AppInletCountDay;
import com.ppfuns.report.service.AppInletCountDayService;
import org.springframework.stereotype.Service;

/**
 * 用户来源按日统计(AppInletCountDay)表服务实现类
 *
 * @author jdq
 * @since 2021-05-25 09:51:37
 */
@Service("appInletCountDayService")
public class AppInletCountDayServiceImpl extends ServiceImpl<AppInletCountDayMapper, AppInletCountDay> implements AppInletCountDayService {

}
