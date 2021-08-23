package com.ppfuns.report.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ppfuns.report.mapper.AppDrawPrizeCountWeekMapper;
import com.ppfuns.report.entity.AppDrawPrizeCountWeek;
import com.ppfuns.report.service.AppDrawPrizeCountWeekService;
import org.springframework.stereotype.Service;

/**
 * 专区抽奖按周统计(AppDrawPrizeCountWeek)表服务实现类
 *
 * @author jdq
 * @since 2021-06-02 17:03:32
 */
@Service("appDrawPrizeCountWeekService")
public class AppDrawPrizeCountWeekServiceImpl extends ServiceImpl<AppDrawPrizeCountWeekMapper, AppDrawPrizeCountWeek> implements AppDrawPrizeCountWeekService {

}
