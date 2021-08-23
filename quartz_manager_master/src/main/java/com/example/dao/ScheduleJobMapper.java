package com.example.dao;

import com.example.dto.ScheduleJobDTO;
import com.example.model.ScheduleJob;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ScheduleJobMapper {
    int deleteByPrimaryKey(Long jobId);

    int insert(ScheduleJob record);

    int insertSelective(ScheduleJob record);

    ScheduleJob selectByPrimaryKey(Long jobId);

    int updateByPrimaryKeySelective(ScheduleJob record);

    int updateByPrimaryKey(ScheduleJob record);

    List<ScheduleJob> getList(ScheduleJob scheduleJob);

    List<ScheduleJob> getListByPage(ScheduleJobDTO scheduleJobDTO);

    List<ScheduleJob> select(ScheduleJob job);
}