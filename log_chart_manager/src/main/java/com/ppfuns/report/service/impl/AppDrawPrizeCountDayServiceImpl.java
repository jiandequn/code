package com.ppfuns.report.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ppfuns.report.mapper.AppDrawPrizeCountDayMapper;
import com.ppfuns.report.entity.AppDrawPrizeCountDay;
import com.ppfuns.report.service.AppDrawPrizeCountDayService;
import org.springframework.stereotype.Service;

/**
 * 专区抽奖按日统计(AppDrawPrizeCountDay)表服务实现类
 *
 * @author jdq
 * @since 2021-06-02 16:58:44
 */
@Service("appDrawPrizeCountDayService")
public class AppDrawPrizeCountDayServiceImpl extends ServiceImpl<AppDrawPrizeCountDayMapper, AppDrawPrizeCountDay> implements AppDrawPrizeCountDayService {

}
