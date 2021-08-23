package com.ppfuns.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ppfuns.sys.entity.SysOperationLog;
import com.ppfuns.sys.mapper.OperationLogMapper;
import com.ppfuns.sys.service.IOperationLogService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统操作日志 服务实现类
 * </p>
 *
 * @author jian.dq
 * @since 2020-03-09
 */
@Service
public class OperationLogServiceImpl extends ServiceImpl<OperationLogMapper, SysOperationLog> implements IOperationLogService {

}
