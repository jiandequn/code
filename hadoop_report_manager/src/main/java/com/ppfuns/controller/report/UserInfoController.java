package com.ppfuns.controller.report;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ppfuns.dao.UserInfoDao;
import com.ppfuns.entity.ResultPage;
import com.ppfuns.entity.report.UserInfoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Created with IntelliJ IDEA.
 * User: 简德群
 * Date: 2019/10/29
 * Time: 14:24
 * To change this template use File | Settings | File Templates.
 */
@RestController
@RequestMapping("/user/info")
public class UserInfoController {
    @Autowired
    private UserInfoDao userInfoDao;

    @RequestMapping("/list")
    public ResultPage getList(String startDate,String endDate,Integer page,Integer rows) {
        QueryWrapper qw = new QueryWrapper();
        Optional.ofNullable(startDate).ifPresent(t->qw.ge("create_time",startDate));
        Optional.ofNullable(endDate).ifPresent(t->qw.lt("create_time",endDate));
        qw.orderByAsc("create_time");
        Page<UserInfoEntity> pg =new Page(page,rows,true);
        return  ResultPage.fromMybatisPlusPage(userInfoDao.selectPage(pg,qw));
    }
    @GetMapping("/exportFile")
    public void export(HttpServletRequest request, HttpServletResponse response,String startDate,String endDate)
            throws IOException {
        ServletOutputStream out = response.getOutputStream();
        String fileName = new String((new SimpleDateFormat("yyyy-MM-dd").format(new Date()))
                .getBytes(), "UTF-8");
        response.setContentType("multipart/form-data");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition", "attachment;filename="+fileName+".xlsx");
        QueryWrapper qw = new QueryWrapper();
        Optional.ofNullable(startDate).ifPresent(t->qw.ge("create_time",startDate));
        Optional.ofNullable(endDate).ifPresent(t->qw.lt("create_time",endDate));
        qw.orderByAsc("create_time");
        List data = this.userInfoDao.selectList(qw);
        EasyExcel.write(out, UserInfoEntity.class).sheet("用户信息").doWrite(data);
    }
}
