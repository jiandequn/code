package com.ppfuns.service;

import com.ppfuns.dao.CleanLogDao;
import com.ppfuns.entity.ParentColumnEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: 简德群
 * Date: 2019/11/7
 * Time: 10:26
 * To change this template use File | Settings | File Templates.
 */
@Service
public class CleanLogService {
    @Autowired
    private CleanLogDao cleanLogDao;
    public Object getCleanLogData() {
        //获取栏目数据
         List<String> userIdList = cleanLogDao.getCleanUserIdList();
        //产品
        List<ParentColumnEntity> parentColumnList = cleanLogDao.getParentColumnIdList();
        Map<String,Object> map = new HashMap<>();
        map.put("userIdList",userIdList);
        map.put("parentColumnList",parentColumnList);
        return map;
    }

    public int clearDataWeek(String tableName, Integer week) {
        return  cleanLogDao.clearDataWeek(tableName,week);
    }
}
