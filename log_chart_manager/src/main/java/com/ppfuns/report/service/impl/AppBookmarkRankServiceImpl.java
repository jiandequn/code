package com.ppfuns.report.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ppfuns.report.mapper.AppBookmarkRankMapper;
import com.ppfuns.report.entity.AppBookmarkRank;
import com.ppfuns.report.service.AppBookmarkRankService;
import org.springframework.stereotype.Service;

/**
 * 专区总收藏排行榜(AppBookmarkRank)表服务实现类
 *
 * @author jdq
 * @since 2021-06-15 18:05:18
 */
@Service("appBookmarkRankService")
public class AppBookmarkRankServiceImpl extends ServiceImpl<AppBookmarkRankMapper, AppBookmarkRank> implements AppBookmarkRankService {

}
