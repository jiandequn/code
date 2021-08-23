package com.ppfuns.report.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ppfuns.report.mapper.AppDrawPrizeCountMonthMapper;
import com.ppfuns.report.entity.AppDrawPrizeCountMonth;
import com.ppfuns.report.service.AppDrawPrizeCountMonthService;
import org.springframework.stereotype.Service;

/**
 * 专区抽奖按月统计(AppDrawPrizeCountMonth)表服务实现类
 *
 * @author jdq
 * @since 2021-06-02 17:02:49
 */
@Service("appDrawPrizeCountMonthService")
public class AppDrawPrizeCountMonthServiceImpl extends ServiceImpl<AppDrawPrizeCountMonthMapper, AppDrawPrizeCountMonth> implements AppDrawPrizeCountMonthService {

}
