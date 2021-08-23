package com.ppfuns.report.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ppfuns.report.entity.TAreaInfo;
import com.ppfuns.report.service.ITAreaInfoService;
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

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jian.dq
 * @since 2020-09-28
 */
@RestController
@RequestMapping("/report/t-area-info")
public class TAreaInfoController {
    @Autowired
    private ITAreaInfoService itAreaInfoService;
    /**
     * 页面
     * @return ok/fail
     */
    @RequestMapping(value = "/toPage", method = RequestMethod.GET)
    public ModelAndView toPage() {
        ModelAndView mav = new ModelAndView("/report/product/area_info");
        return mav;
    }
    @RequestMapping("/list")
    public ResultPage getList(TAreaInfo tAreaInfo, Integer page, Integer limit) {
        QueryWrapper<TAreaInfo> qw = new QueryWrapper();
        if(StringUtils.isNotEmpty(tAreaInfo.getAreaName())) qw.like("area_name","%"+tAreaInfo.getAreaName()+"%");
        if(StringUtils.isNotEmpty(tAreaInfo.getAreaNo())) qw.eq("area_no",tAreaInfo.getAreaNo());
        if(StringUtils.isNotEmpty(tAreaInfo.getCityName())) qw.like("city_name","%"+tAreaInfo.getCityName()+"%");
        if(StringUtils.isNotEmpty(tAreaInfo.getCityNo())) qw.eq("area_no",tAreaInfo.getCityNo());
        if(StringUtils.isNotEmpty(tAreaInfo.getType())) qw.eq("type",tAreaInfo.getType());
        Page<TAreaInfo> pg =new Page(page,limit,true);
        return  ResultPage.fromMybatisPlusPage(itAreaInfoService.page(pg,qw));
    }
    @RequestMapping("/getAll")
    public List<TAreaInfo> getAll(TAreaInfo tAreaInfo) {
        QueryWrapper<TAreaInfo> qw = new QueryWrapper();
        if(StringUtils.isNotEmpty(tAreaInfo.getAreaName())) qw.like("area_name","%"+tAreaInfo.getAreaName()+"%");
        if(StringUtils.isNotEmpty(tAreaInfo.getAreaNo())) qw.eq("area_no",tAreaInfo.getAreaNo());
        if(StringUtils.isNotEmpty(tAreaInfo.getCityName())) qw.like("city_name","%"+tAreaInfo.getCityName()+"%");
        if(StringUtils.isNotEmpty(tAreaInfo.getCityNo())) qw.eq("area_no",tAreaInfo.getCityNo());
        if(StringUtils.isNotEmpty(tAreaInfo.getType())) qw.eq("type",tAreaInfo.getType());
        return this.itAreaInfoService.list(qw);
    }
    /**
     * 设置用户是否离职
     * @return ok/fail
     */
    @RequiresPermissions(value = { "clean_user:setIsEffective"})
    @RequestMapping(value = "/setIsEffective", method = RequestMethod.POST)
    public ResultData setIsEffective(@RequestParam("id") Integer id,
                                     @RequestParam("isEffective") boolean isEffective) {
        String msg = "";
        try {
            UpdateWrapper<TAreaInfo> uw = new UpdateWrapper<>();
            uw.set("is_effective",isEffective);
            uw.eq("id",id);
            itAreaInfoService.update(uw);
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
        return new ResultData("1","获取成功",this.itAreaInfoService.getById(id));
    }
    /**
     * 设置用户[新增或更新]
     * @return ok/fail
     */
    @RequiresPermissions(value = { "clean_user:add","product_column:update"},logical = Logical.OR)
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResultData save(TAreaInfo tAreaInfo) {
        try {
            if (null == tAreaInfo) {
                return new ResultData("0","无更新信息");
            }
            this.itAreaInfoService.saveOrUpdate(tAreaInfo);
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
    @RequiresPermissions(value = { "clean_user:del"})
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public ResultData del(@RequestParam("id") Integer id) {
        String msg = "";
        try {
            if (null == id) {
                return new ResultData("0","请求参数有误，请您稍后再试");
            }
            this.itAreaInfoService.removeById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultData("0","操作异常，请您稍后再试");
        }
        return new ResultData();
    }
}
