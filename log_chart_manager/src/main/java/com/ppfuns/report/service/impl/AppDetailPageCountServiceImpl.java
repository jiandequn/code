package com.ppfuns.report.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ppfuns.report.mapper.AppDetailPageCountMapper;
import com.ppfuns.report.entity.AppDetailPageCount;
import com.ppfuns.report.service.AppDetailPageCountService;
import org.springframework.stereotype.Service;

/**
 * 累计统计详情访问(AppDetailPageCount)表服务实现类
 *
 * @author jdq
 * @since 2021-06-15 18:23:23
 */
@Service("appDetailPageCountService")
public class AppDetailPageCountServiceImpl extends ServiceImpl<AppDetailPageCountMapper, AppDetailPageCount> implements AppDetailPageCountService {

}
