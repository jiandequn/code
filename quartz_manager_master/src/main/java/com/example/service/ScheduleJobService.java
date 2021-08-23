package com.example.service;

import com.example.dao.ScheduleJobMapper;
import com.example.dto.ScheduleJobDTO;
import com.example.model.ScheduleJob;
import com.example.utils.DateUtil;
import com.example.utils.PageDataResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 简德群
 * Date: 2019/5/17
 * Time: 16:42
 * To change this template use File | Settings | File Templates.
 */
@Service
public class ScheduleJobService {
    @Autowired
    private ScheduleJobMapper scheduleJobMapper;
    public int deleteByPrimaryKey(Long jobId){
        return scheduleJobMapper.deleteByPrimaryKey(jobId);
    }

    public int insert(ScheduleJob record){
        return scheduleJobMapper.insert(record);
    }

    public int insertSelective(ScheduleJob record){
             return scheduleJobMapper.insertSelective(record);
    }

    public ScheduleJob selectByPrimaryKey(Long jobId){
         return scheduleJobMapper.selectByPrimaryKey(jobId);
    }

    public int updateByPrimaryKeySelective(ScheduleJob record){
        return scheduleJobMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(ScheduleJob record){
         return scheduleJobMapper.updateByPrimaryKey(record);
    }

    public List<ScheduleJob> getList(ScheduleJob scheduleJob) {
        return scheduleJobMapper.getList(scheduleJob);
    }

    public PageDataResult getListByPage(ScheduleJobDTO scheduleJobDTO, Integer page, Integer limit) {
        // 时间处理
        if (null != scheduleJobDTO) {
            if (StringUtils.isNotEmpty(scheduleJobDTO.getInsertTimeStart())
                    && StringUtils.isEmpty(scheduleJobDTO.getInsertTimeEnd())) {
                scheduleJobDTO.setInsertTimeEnd(DateUtil.format(new Date()));
            } else if (StringUtils.isEmpty(scheduleJobDTO.getInsertTimeStart())
                    && StringUtils.isNotEmpty(scheduleJobDTO.getInsertTimeEnd())) {
                scheduleJobDTO.setInsertTimeStart(DateUtil.format(new Date()));
            }
            if (StringUtils.isNotEmpty(scheduleJobDTO.getInsertTimeStart())
                    && StringUtils.isNotEmpty(scheduleJobDTO.getInsertTimeEnd())) {
                if (scheduleJobDTO.getInsertTimeEnd().compareTo(
                        scheduleJobDTO.getInsertTimeStart()) < 0) {
                    String temp = scheduleJobDTO.getInsertTimeStart();
                    scheduleJobDTO
                            .setInsertTimeStart(scheduleJobDTO.getInsertTimeEnd());
                    scheduleJobDTO.setInsertTimeEnd(temp);
                }
            }
        }
        PageDataResult pdr = new PageDataResult();
        PageHelper.startPage(page, limit);
        List<ScheduleJob> list = scheduleJobMapper.getListByPage(scheduleJobDTO);
        // 获取分页查询后的数据
        PageInfo<ScheduleJob> pageInfo = new PageInfo<>(list);
        // 设置获取到的总记录数total：
        pdr.setTotals(Long.valueOf(pageInfo.getTotal()).intValue());
        pdr.setList(list);
        return pdr;
    }

    public List<ScheduleJob> select(ScheduleJob job) {
        return scheduleJobMapper.select(job);
    }
}
