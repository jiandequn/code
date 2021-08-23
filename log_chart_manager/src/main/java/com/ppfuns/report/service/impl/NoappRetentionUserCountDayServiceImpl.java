package com.ppfuns.report.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ppfuns.report.mapper.NoappRetentionUserCountDayMapper;
import com.ppfuns.report.entity.NoappRetentionUserCountDay;
import com.ppfuns.report.service.NoappRetentionUserCountDayService;
import org.springframework.stereotype.Service;

/**
 * 所有专区留存用户数统计(NoappRetentionUserCountDay)表服务实现类
 *
 * @author jdq
 * @since 2021-07-06 17:03:50
 */
@Service("noappRetentionUserCountDayService")
public class NoappRetentionUserCountDayServiceImpl extends ServiceImpl<NoappRetentionUserCountDayMapper, NoappRetentionUserCountDay> implements NoappRetentionUserCountDayService {

}
