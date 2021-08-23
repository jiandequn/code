package com.ppfuns.report.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ppfuns.report.mapper.NoappCountDayMapper;
import com.ppfuns.report.entity.NoappCountDay;
import com.ppfuns.report.service.NoappCountDayService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * 所有专区日统计(NoappCountDay)表服务实现类
 *
 * @author jdq
 * @since 2021-07-12 17:28:09
 */
@Service("noappCountDayService")
public class NoappCountDayServiceImpl extends ServiceImpl<NoappCountDayMapper, NoappCountDay> implements NoappCountDayService {

    @Override
    public void gengerateData(String startDate, String endDate) {
        String cur = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        if(cur.compareTo(endDate)<0){
            endDate = cur;
        }
        if(endDate.compareTo(startDate)<=0){
            return;
        }
        //1.分组查询专区日访问用户统计包括播放次数，访问用户数，播放时长，播放用户数，新增用户数
        List<NoappCountDay> appCountDayList = this.baseMapper.selectSourceData(startDate, endDate);
        appCountDayList.forEach(s->{
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("user_type",s.getUserType());
            queryWrapper.eq("t_date",s.gettDate());
            if(this.baseMapper.selectCount(queryWrapper)>0){
                this.baseMapper.update(s,queryWrapper);
            }else {
                this.baseMapper.insert(s);
            }
        });
        //统计日累计访用户数
        LocalDate sdate = LocalDate.parse(startDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate eDate = LocalDate.parse(endDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        while (sdate.compareTo(eDate)<0){
            String cDate = sdate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            sdate = sdate.plusDays(1);
            List<NoappCountDay> appCountDayList1 = this.baseMapper.selectTotalVisitUserCount(sdate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            appCountDayList1.forEach(s->{
                s.settDate(cDate);
                QueryWrapper queryWrapper = new QueryWrapper();
                queryWrapper.eq("user_type",s.getUserType());
                queryWrapper.eq("t_date",s.gettDate());
                if(this.baseMapper.selectCount(queryWrapper)>0){
                    UpdateWrapper<NoappCountDay> wrapper = new UpdateWrapper();
                    wrapper.set("total_visit_user_count",s.getTotalVisitUserCount());
                    wrapper.eq("user_type",s.getUserType());
                    wrapper.eq("t_date",s.gettDate());
                    this.baseMapper.update(null,wrapper);
                }else {
                    this.baseMapper.insert(s);
                }
            });

        }
    }

    @Override
    public List mergeList(QueryWrapper<NoappCountDay> qw, int countFlag) {
        return this.baseMapper.mergeList(qw,countFlag);
    }

    @Override
    public IPage mergePage(Page<NoappCountDay> pg, QueryWrapper<NoappCountDay> qw, int countFlag) {
        return this.baseMapper.mergePage(pg,qw,countFlag);
    }
}
