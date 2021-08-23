package com.ppfuns.report.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ppfuns.report.entity.AlbumContentType;
import com.ppfuns.report.service.AlbumContentTypeService;
import org.springframework.web.bind.annotation.*;
import com.ppfuns.report.utils.QueryConditionsUtils;
import com.ppfuns.util.ResultPage;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.List;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.alibaba.excel.EasyExcel;

/**
 * (AlbumContentType)表控制层
 *
 * @author jdq
 * @since 2021-07-07 15:50:01
 */
@RestController
@RequestMapping("/report/album_content_type")
public class AlbumContentTypeController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private AlbumContentTypeService albumContentTypeService;

    @RequestMapping(value = "/toPage", method = RequestMethod.GET)
    public ModelAndView toPage() {
        logger.debug("权限列表！");
        ModelAndView mav = new ModelAndView("/report/app/album_content_type");
        return mav;
    }

    @RequestMapping("list")
    public ResultPage getList(Integer page, Integer limit, String orderby) {
        QueryWrapper<AlbumContentType> qw = new QueryWrapper();
        QueryConditionsUtils.formatOrderBy(qw, orderby, null);
        if (page == null || limit == null) {
            List list = this.albumContentTypeService.list(qw);
            return new ResultPage(list.size(), list);
        }
        Page<AlbumContentType> pg = new Page(page, limit, true);
        return ResultPage.fromMybatisPlusPage(albumContentTypeService.page(pg, qw));
    }

    /**
     * 分页查询所有数据
     *
     * @param page             分页对象
     * @param albumContentType 查询实体
     * @return 所有数据
     */
    @GetMapping
    public R selectAll(Page<AlbumContentType> page, AlbumContentType albumContentType) {
        return success(this.albumContentTypeService.page(page, new QueryWrapper<>(albumContentType)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public R selectOne(@PathVariable Serializable id) {
        return success(this.albumContentTypeService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param albumContentType 实体对象
     * @return 新增结果
     */
    @PostMapping
    public R insert(@RequestBody AlbumContentType albumContentType) {
        return success(this.albumContentTypeService.save(albumContentType));
    }

    /**
     * 修改数据
     *
     * @param albumContentType 实体对象
     * @return 修改结果
     */
    @PutMapping
    public R update(@RequestBody AlbumContentType albumContentType) {
        return success(this.albumContentTypeService.updateById(albumContentType));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public R delete(@RequestParam("idList") List<Long> idList) {
        return success(this.albumContentTypeService.removeByIds(idList));
    }
}
