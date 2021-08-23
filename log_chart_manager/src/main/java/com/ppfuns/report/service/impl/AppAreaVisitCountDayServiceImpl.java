package com.ppfuns.report.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ppfuns.report.entity.AppAreaVisitCountDay;
import com.ppfuns.report.entity.NoappCountDay;
import com.ppfuns.report.entity.ProductColumn;
import com.ppfuns.report.mapper.AppAreaVisitCountDayMapper;
import com.ppfuns.report.service.IAppAreaVisitCountDayService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ppfuns.report.service.IProductColumnService;
import com.ppfuns.report.service.IUserInfoService;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import javax.annotation.Resource;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 专区按天统计信息 服务实现类
 * </p>
 *
 * @author jian.dq
 * @since 2020-10-19
 */
@Service
public class AppAreaVisitCountDayServiceImpl extends ServiceImpl<AppAreaVisitCountDayMapper, AppAreaVisitCountDay> implements IAppAreaVisitCountDayService {
    @Autowired
    private IProductColumnService iProductColumnService;
    @Resource
    private IUserInfoService iUserInfoService;
    @Override
    public List areaList(QueryWrapper<AppAreaVisitCountDay> qw) {
        return this.baseMapper.areaList(qw);
    }

    @Override
    public IPage areaPage(Page<AppAreaVisitCountDay> pg, QueryWrapper<AppAreaVisitCountDay> qw) {
        return this.baseMapper.areaPage(pg,qw);
    }

    @Override
    public void gengerateData(String startDateStr, String endDateStr) {
//        Date from = Date.from(LocalDateTime.parse(endDateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd")).toInstant());
        Date from = Date.from(LocalDate.parse(endDateStr).atStartOfDay().toInstant(ZoneOffset.ofHours(8)));
        ProductColumn pc = new ProductColumn();
        pc.setEffective(true);
        iProductColumnService.list(new QueryWrapper<>(pc)).forEach(s->{
           //查询可以处理时间
            String edateStr=endDateStr;
            if(s.getEndDate() != null){
                //如果专区结束时间在endDate之前，不能处理到endDate,需要进行endDate处理
                if(s.getEndDate().before(from)){
                    edateStr=s.getEndDate().toInstant().atZone(ZoneOffset.systemDefault()).toLocalDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                }
            }
            if(startDateStr.compareTo(edateStr)>=0){
                return;
            }
            //查询专区区域新增用户数 addUserCount
            Map<String,String> params= new HashMap<>();
            params.put("startDate",startDateStr);
            params.put("endDate",edateStr);
            params.put("parentColumnId",s.getColumnId());
            params.put("userType",s.getUserType());
           List<AppAreaVisitCountDay> dataList= iUserInfoService.getAreaIncreaseUserCountList(params);
           dataList.forEach(t->{
               if(!StringUtils.isEmpty(t.getAreaCode())){
                   QueryWrapper qw = new QueryWrapper();
                   qw.eq("parent_column_id",t.getParentColumnId());
                   qw.eq("user_type",t.getUserType());
                   qw.eq("t_date",t.gettDate());
                   qw.eq("area_code",t.getAreaCode());
                   if(this.baseMapper.selectCount(qw)>0){
                        this.baseMapper.update(t,qw);
                   }else{
                       this.baseMapper.insert(t);
                   }
               }
           });
            //统计日累计访问用户数
            params.remove("startDate");
            params.remove("endDate");
            LocalDate sdate = LocalDate.parse(startDateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            LocalDate eDate = LocalDate.parse(edateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            while (sdate.compareTo(eDate)<0){
                params.put("endDate",sdate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                List<AppAreaVisitCountDay> appCountDayList1 = this.iUserInfoService.selectTotalAreaVisitUserCount(params);
                appCountDayList1.forEach(t->{
                    QueryWrapper queryWrapper = new QueryWrapper();
                    queryWrapper.eq("user_type",t.getUserType());
                    queryWrapper.eq("t_date",t.gettDate());
                    queryWrapper.eq("area_code",t.getAreaCode());
                    queryWrapper.eq("parent_column_id",t.getParentColumnId());
                    if(this.baseMapper.selectCount(queryWrapper)>0){
                        UpdateWrapper<AppAreaVisitCountDay> wrapper = new UpdateWrapper();
                        wrapper.set("total_user_count",t.getTotalUserCount());
                        wrapper.eq("user_type",s.getUserType());
                        wrapper.eq("t_date",t.gettDate());
                        wrapper.eq("area_code",t.getAreaCode());
                        wrapper.eq("parent_column_id",t.getParentColumnId());
                        this.baseMapper.update(null,wrapper);
                    }else{
                        this.baseMapper.insert(t);
                    }
                });
                sdate = sdate.plusDays(1);
            }
        });

    }
}
