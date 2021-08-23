package com.ppfuns.report.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ppfuns.report.entity.TableInfo;
import com.ppfuns.report.service.ITableInfoService;
import com.ppfuns.util.ResultData;
import com.ppfuns.util.ResultPage;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jian.dq
 * @since 2020-10-20
 */
@RestController
@RequestMapping("/report/table-info")
public class TableInfoController {
    @Autowired
    private ITableInfoService iTableInfoService;
    @RequestMapping("/getAllTableInDb")
    public List<TableInfo> getAllTableInDb(Integer page, Integer limit) {
        Page<TableInfo> pg =new Page(page,limit,true);
        return iTableInfoService.getAllTableInDb();
    }
    /**
     * 页面
     * @return ok/fail
     */
    @RequestMapping(value = "/toPage", method = RequestMethod.GET)
    public ModelAndView toPage() {
        ModelAndView mav = new ModelAndView("/report/product/table_info");
        return mav;
    }
    @RequestMapping("/list")
    public ResultPage getList(TableInfo tableInfo, Integer page, Integer limit) {
        QueryWrapper<TableInfo> qw = new QueryWrapper();
        if(StringUtils.isNotEmpty(tableInfo.getTableName())) qw.like("table_name","%"+tableInfo.getTableName()+"%");
        if(StringUtils.isNotEmpty(tableInfo.getTableComment())) qw.like("city_name","%"+tableInfo.getTableComment()+"%");
        Page<TableInfo> pg =new Page(page,limit,true);
        return  ResultPage.fromMybatisPlusPage(iTableInfoService.page(pg,qw));
    }
    /**
     * 设置用户是否离职
     * @return ok/fail
     */
    @RequiresPermissions(value = { "table_info:setIsEffective"})
    @RequestMapping(value = "/setIsEffective", method = RequestMethod.POST)
    public ResultData setIsEffective(@RequestParam("id") Integer id,
                                     @RequestParam("isEffective") boolean isEffective) {
        String msg = "";
        try {
            UpdateWrapper<TableInfo> uw = new UpdateWrapper<>();
            uw.set("is_effective",isEffective);
            uw.eq("id",id);
            iTableInfoService.update(uw);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultData("0","操作异常，请您稍后再试！");
        }
        return new ResultData();
    }
    /**
     * 获取用户信息
     * @return ok/fail
     */
    @RequestMapping(value = "/get")
    public ResultData get(@RequestParam("id") Integer id) {
        return new ResultData("1","获取成功",this.iTableInfoService.getById(id));
    }
    /**
     * 设置用户[新增或更新]
     * @return ok/fail
     */
    @RequiresPermissions(value = { "table_info:add","table_info:update"},logical = Logical.OR)
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResultData save(TableInfo tableInfo) {
        try {
            if (null == tableInfo) {
                return new ResultData("0","无更新信息");
            }
            if(tableInfo.getId() == null){
                tableInfo.setCreateTime(new Date());
            }
            this.iTableInfoService.saveOrUpdate(tableInfo);
            return new ResultData();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultData("0","操作异常，请您稍后再试");
        }
    }
    /**
     * 删除用户
     * @return ok/fail
     */
    @RequiresPermissions(value = { "table_info:del"})
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public ResultData del(@RequestParam("id") Integer id) {
        String msg = "";
        try {
            if (null == id) {
                return new ResultData("0","请求参数有误，请您稍后再试");
            }
            this.iTableInfoService.removeById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultData("0","操作异常，请您稍后再试");
        }
        return new ResultData();
    }

    @RequestMapping("/select")
    public List<TableInfo> getAllTableInDb(TableInfo tableInfo) {
        QueryWrapper<TableInfo> qw = new QueryWrapper(tableInfo);
        return iTableInfoService.list();
    }
}
