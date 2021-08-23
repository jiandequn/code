package com.ppfuns.report.service.impl;

import com.ppfuns.report.entity.ProductColumn;
import com.ppfuns.report.mapper.ProductColumnMapper;
import com.ppfuns.report.service.IProductColumnService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 现网产品统计 服务实现类
 * </p>
 *
 * @author jian.dq
 * @since 2020-09-15
 */
@Service
public class ProductColumnServiceImpl extends ServiceImpl<ProductColumnMapper, ProductColumn> implements IProductColumnService {
}
