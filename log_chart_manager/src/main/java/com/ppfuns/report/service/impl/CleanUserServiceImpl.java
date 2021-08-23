package com.ppfuns.report.service.impl;

import com.ppfuns.report.entity.CleanUser;
import com.ppfuns.report.mapper.CleanUserMapper;
import com.ppfuns.report.service.ICleanUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 日志中需清理用户ID表 服务实现类
 * </p>
 *
 * @author jian.dq
 * @since 2020-09-28
 */
@Service
public class CleanUserServiceImpl extends ServiceImpl<CleanUserMapper, CleanUser> implements ICleanUserService {

}
