package com.ppfuns.report.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ppfuns.report.mapper.AlbumContentTypeMapper;
import com.ppfuns.report.entity.AlbumContentType;
import com.ppfuns.report.service.AlbumContentTypeService;
import org.springframework.stereotype.Service;

/**
 * (AlbumContentType)表服务实现类
 *
 * @author jdq
 * @since 2021-07-07 15:50:01
 */
@Service("albumContentTypeService")
public class AlbumContentTypeServiceImpl extends ServiceImpl<AlbumContentTypeMapper, AlbumContentType> implements AlbumContentTypeService {

}
