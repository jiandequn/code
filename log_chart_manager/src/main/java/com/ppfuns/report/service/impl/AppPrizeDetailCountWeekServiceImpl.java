package com.ppfuns.report.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ppfuns.report.mapper.AppPrizeDetailCountWeekMapper;
import com.ppfuns.report.entity.AppPrizeDetailCountWeek;
import com.ppfuns.report.service.AppPrizeDetailCountWeekService;
import org.springframework.stereotype.Service;

/**
 * 专区奖品的抽奖按周统计(AppPrizeDetailCountWeek)表服务实现类
 *
 * @author jdq
 * @since 2021-06-15 18:48:35
 */
@Service("appPrizeDetailCountWeekService")
public class AppPrizeDetailCountWeekServiceImpl extends ServiceImpl<AppPrizeDetailCountWeekMapper, AppPrizeDetailCountWeek> implements AppPrizeDetailCountWeekService {

}
