package com.ppfuns.report.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ppfuns.report.mapper.AppPrizeDetailCountMonthMapper;
import com.ppfuns.report.entity.AppPrizeDetailCountMonth;
import com.ppfuns.report.service.AppPrizeDetailCountMonthService;
import org.springframework.stereotype.Service;

/**
 * 专区奖品的抽奖按月统计(AppPrizeDetailCountMonth)表服务实现类
 *
 * @author jdq
 * @since 2021-06-15 18:48:18
 */
@Service("appPrizeDetailCountMonthService")
public class AppPrizeDetailCountMonthServiceImpl extends ServiceImpl<AppPrizeDetailCountMonthMapper, AppPrizeDetailCountMonth> implements AppPrizeDetailCountMonthService {

}
