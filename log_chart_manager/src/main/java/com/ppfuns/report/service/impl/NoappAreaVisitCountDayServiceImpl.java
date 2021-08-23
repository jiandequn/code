package com.ppfuns.report.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ppfuns.report.entity.AppAreaVisitCountDay;
import com.ppfuns.report.entity.NoappAreaVisitCountDay;
import com.ppfuns.report.entity.NoappCountDay;
import com.ppfuns.report.entity.ProductColumn;
import com.ppfuns.report.mapper.NoappAreaVisitCountDayMapper;
import com.ppfuns.report.service.INoappAreaVisitCountDayService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ppfuns.report.service.IUserInfoService;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 所有专区按天统计信息 服务实现类
 * </p>
 *
 * @author jian.dq
 * @since 2020-10-23
 */
@Service
public class NoappAreaVisitCountDayServiceImpl extends ServiceImpl<NoappAreaVisitCountDayMapper, NoappAreaVisitCountDay> implements INoappAreaVisitCountDayService {
    @Resource
    private IUserInfoService iUserInfoService;
    @Override
    public List areaList(QueryWrapper<NoappAreaVisitCountDay> qw) {
        return this.baseMapper.areaList(qw);
    }

    @Override
    public IPage areaPage(Page<NoappAreaVisitCountDay> pg, QueryWrapper<NoappAreaVisitCountDay> qw) {
        return this.baseMapper.areaPage(pg, qw);
    }

    @Override
    public void gengerateData(String startDateStr, String endDateStr) {
        String cur = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        if(cur.compareTo(endDateStr)<0){
            endDateStr = cur;
        }
        if(endDateStr.compareTo(startDateStr)<=0){
            return;
        }
        //查询专区区域新增用户数 addUserCount
        Map<String, String> params = new HashMap<>();
        params.put("startDate", startDateStr);
        params.put("endDate", endDateStr);
        List<AppAreaVisitCountDay> dataList = iUserInfoService.getAreaIncreaseUserCountList(params);
        dataList.forEach(t -> {
            NoappAreaVisitCountDay bean = new NoappAreaVisitCountDay();
            bean.setAddUserCount(t.getAddUserCount());
            bean.setAreaCode(t.getAreaCode());
            bean.setUserType(t.getUserType());
            bean.settDate(t.gettDate());
            if (!StringUtils.isEmpty(t.getAreaCode())) {
                QueryWrapper qw = new QueryWrapper();
                qw.eq("user_type", t.getUserType());
                qw.eq("t_date", t.gettDate());
                qw.eq("area_code", t.getAreaCode());
                if (this.baseMapper.selectCount(qw) > 0) {
                    this.baseMapper.update(bean, qw);
                } else {
                    this.baseMapper.insert(bean);
                }
            }
        });
        //统计日累计访问用户数
        params.remove("startDate");
        params.remove("endDate");
        LocalDate sdate = LocalDate.parse(startDateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate eDate = LocalDate.parse(endDateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        while (sdate.compareTo(eDate) < 0) {
            params.put("endDate", sdate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            List<AppAreaVisitCountDay> appCountDayList1 = this.iUserInfoService.selectTotalAreaVisitUserCount(params);
            appCountDayList1.forEach(t -> {
                NoappAreaVisitCountDay bean = new NoappAreaVisitCountDay();
                bean.setTotalUserCount(t.getTotalUserCount());
                bean.setAreaCode(t.getAreaCode());
                bean.setUserType(t.getUserType());
                bean.settDate(t.gettDate());
                QueryWrapper queryWrapper = new QueryWrapper();
                queryWrapper.eq("user_type", t.getUserType());
                queryWrapper.eq("t_date", t.gettDate());
                queryWrapper.eq("area_code", t.getAreaCode());
                if (this.baseMapper.selectCount(queryWrapper) > 0) {
                    UpdateWrapper<NoappAreaVisitCountDay> wrapper = new UpdateWrapper();
                    wrapper.set("total_user_count", t.getTotalUserCount());
                    wrapper.eq("user_type", t.getUserType());
                    wrapper.eq("t_date", t.gettDate());
                    wrapper.eq("area_code", t.getAreaCode());
                    this.baseMapper.update(null, wrapper);
                } else {
                    this.baseMapper.insert(bean);
                }
            });
            sdate = sdate.plusDays(1);
        }


    }
}
