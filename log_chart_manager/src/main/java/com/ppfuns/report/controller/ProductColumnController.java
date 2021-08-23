package com.ppfuns.report.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ppfuns.report.entity.ProductColumn;
import com.ppfuns.report.service.IProductColumnService;
import com.ppfuns.util.ResultData;
import com.ppfuns.util.ResultPage;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * <p>
 * 现网产品统计 前端控制器
 * </p>
 *
 * @author jian.dq
 * @since 2020-09-15
 */
@RestController
@RequestMapping("/report/product-column")
public class ProductColumnController {
    private static final Logger logger = LoggerFactory
            .getLogger(ProductColumnController.class);
    @Autowired
    private IProductColumnService productColumnService;
    /**
     * 页面
     * @return ok/fail
     */
    @RequestMapping(value = "/toPage", method = RequestMethod.GET)
    public ModelAndView toPage() {
        logger.debug("权限列表！");
        ModelAndView mav = new ModelAndView("/report/product/product_column");
        return mav;
    }

    @RequestMapping("/getAll")
    public List getAll(Integer isEffective) {
        logger.debug("获取产品列表");
        QueryWrapper<ProductColumn> qw = new QueryWrapper();
        if(isEffective != null) qw.eq("is_effective",isEffective);
        qw.orderByAsc("seq");
        return this.productColumnService.list(qw);
    }
    @RequestMapping("/list")
    public ResultPage getList(ProductColumn productColumn, Integer page, Integer limit) {
        QueryWrapper<ProductColumn> qw = new QueryWrapper();
        if(StringUtils.isNotEmpty(productColumn.getColumnId())) qw.eq("column_id",productColumn.getColumnId());
        if(StringUtils.isNotEmpty(productColumn.getColumnName())) qw.like("column_name","%"+productColumn.getColumnName()+"%");
        if(StringUtils.isNotEmpty(productColumn.getUserType())) qw.eq("user_type",productColumn.getUserType());
        if(productColumn.getEffective() != null){
            qw.eq("is_effective",productColumn.getEffective());
        }
        Page<ProductColumn> pg =new Page(page,limit,true);
        return  ResultPage.fromMybatisPlusPage(productColumnService.page(pg,qw));
    }
    /**
     * 设置用户是否离职
     * @return ok/fail
     */
    @RequiresPermissions(value = { "product_column:setIsEffective"})
    @RequestMapping(value = "/setIsEffective", method = RequestMethod.POST)
    public ResultData setJobUser(@RequestParam("id") Integer id,
                                 @RequestParam("isEffective") boolean isEffective) {
        String msg = "";
        try {
            UpdateWrapper<ProductColumn> uw = new UpdateWrapper<>();
            uw.set("is_effective",isEffective);
            uw.eq("id",id);
            productColumnService.update(uw);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("设置异常！", e);
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
        return new ResultData("1","获取成功",this.productColumnService.getById(id));
    }
    /**
     * 设置用户[新增或更新]
     * @return ok/fail
     */
    @RequiresPermissions(value = { "product_column:add","product_column:update"},logical = Logical.OR)
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResultData save(ProductColumn productColumn) {
        try {
            if (null == productColumn) {
                return new ResultData("0","无更新信息");
            }
            this.productColumnService.saveOrUpdate(productColumn);
            return new ResultData();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("设置用户[新增或更新]异常！", e);
            return new ResultData("0","操作异常，请您稍后再试");
        }
    }
    /**
     * 删除用户
     * @return ok/fail
     */
    @RequiresPermissions(value = { "product_column:del"})
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    @ResponseBody
    public ResultData delUser(@RequestParam("id") Integer id) {
        logger.debug("删除！id:" + id);
        String msg = "";
        try {
            if (null == id) {
                logger.debug("删除不可用，结果=请求参数有误，请您稍后再试");
                return new ResultData("0","请求参数有误，请您稍后再试");
            }
            this.productColumnService.removeById(id);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("删除异常！", e);
            return new ResultData("0","操作异常，请您稍后再试");
        }
        return new ResultData();
    }
}
