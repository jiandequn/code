package com.ppfuns.report.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ppfuns.report.mapper.AppPrizeDetailCountDayMapper;
import com.ppfuns.report.entity.AppPrizeDetailCountDay;
import com.ppfuns.report.service.AppPrizeDetailCountDayService;
import org.springframework.stereotype.Service;

/**
 * 专区奖品的抽奖按日统计(AppPrizeDetailCountDay)表服务实现类
 *
 * @author jdq
 * @since 2021-06-15 18:47:50
 */
@Service("appPrizeDetailCountDayService")
public class AppPrizeDetailCountDayServiceImpl extends ServiceImpl<AppPrizeDetailCountDayMapper, AppPrizeDetailCountDay> implements AppPrizeDetailCountDayService {

}
