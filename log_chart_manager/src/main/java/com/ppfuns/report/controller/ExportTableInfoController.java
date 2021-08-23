package com.ppfuns.report.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ppfuns.report.entity.ExportTableInfo;
import com.ppfuns.report.entity.TableColumnInfo;
import com.ppfuns.report.entity.TableInfo;
import com.ppfuns.report.service.IExportTableInfoService;
import com.ppfuns.report.service.ITableColumnInfoService;
import com.ppfuns.report.service.ITableInfoService;
import com.ppfuns.report.utils.CSVUtils;
import com.ppfuns.report.utils.ExcelUtils;
import com.ppfuns.report.utils.ZipUtils;
import com.ppfuns.sys.entity.LoginUser;
import com.ppfuns.util.ResultData;
import com.ppfuns.util.ResultPage;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.net.URLEncoder;
import java.util.*;

/**
 * <p>
 * 导出table维护信息 前端控制器
 * </p>
 *
 * @author jian.dq
 * @since 2020-10-20
 */
@RestController
@RequestMapping("/report/export-table-info")
public class ExportTableInfoController {
    @Autowired
    private IExportTableInfoService iExportTableInfoService;
    @Autowired
    private ITableColumnInfoService iTableColumnInfoService;
    @Autowired
    private ITableInfoService iTableInfoService;

    /**
     * 页面
     * @return ok/fail
     */
    @RequestMapping(value = "/toPage", method = RequestMethod.GET)
    public ModelAndView toPage() {
        ModelAndView mav = new ModelAndView("/report/product/export_table_info");
        return mav;
    }
    @RequestMapping("/list")
    public ResultPage getList(ExportTableInfo tAreaInfo, Integer page, Integer limit) {
        QueryWrapper<ExportTableInfo> qw = new QueryWrapper();
        if(StringUtils.isNotEmpty(tAreaInfo.getName())) qw.like("name","%"+tAreaInfo.getName()+"%");
        Page<ExportTableInfo> pg =new Page(page,limit,true);
        return  ResultPage.fromMybatisPlusPage(iExportTableInfoService.page(pg,qw));
    }
    /**
     * 设置用户是否离职
     * @return ok/fail
     */
    @RequiresPermissions(value = { "export_table_info:setIsEffective"})
    @RequestMapping(value = "/setIsEffective", method = RequestMethod.POST)
    public ResultData setIsEffective(@RequestParam("id") Integer id,
                                     @RequestParam("isEffective") boolean isEffective) {
        String msg = "";
        try {
            UpdateWrapper<ExportTableInfo> uw = new UpdateWrapper<>();
            uw.set("is_effective",isEffective);
            uw.eq("id",id);
            iExportTableInfoService.update(uw);
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
        return new ResultData("1","获取成功",this.iExportTableInfoService.getById(id));
    }
    /**
     * 设置用户[新增或更新]
     * @return ok/fail
     */
    @RequiresPermissions(value = { "export_table_info:add","export_table_info:update"},logical = Logical.OR)
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResultData save(ExportTableInfo exportTableInfo,String columnInfos) {
        try {
            if (null == exportTableInfo) {
                return new ResultData("0","无更新信息");
            }
            if(exportTableInfo.getId() == null){
                exportTableInfo.setCreateTime(new Date());
            }
            this.iExportTableInfoService.saveOrUpdate(exportTableInfo);
            ObjectMapper objectMapper = new ObjectMapper();
            JavaType javaType = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, TableColumnInfo.class);
            List<TableColumnInfo> list = objectMapper.readValue(columnInfos,javaType);
            QueryWrapper<TableColumnInfo> qw = new QueryWrapper<>();
            qw.eq("export_table_id",exportTableInfo.getId());
            this.iTableColumnInfoService.remove(qw);
            list.forEach(s->{
                s.setExportTableId(exportTableInfo.getId());
                s.setCreateTime(new Date());
                this.iTableColumnInfoService.save(s);
            });
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
    @RequiresPermissions(value = { "export_table_info:del"})
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public ResultData del(@RequestParam("id") Integer id) {
        String msg = "";
        try {
            if (null == id) {
                return new ResultData("0","请求参数有误，请您稍后再试");
            }
            this.iExportTableInfoService.removeById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultData("0","操作异常，请您稍后再试");
        }
        return new ResultData();
    }

    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/download/file/{exportTableId}")
    public void download(@PathVariable Integer exportTableId,String downloadType,
                         HttpServletResponse response, HttpSession session) throws Exception {
        try{
            //检查表的描述
            session.setAttribute("downloadStatus","suc");
            ExportTableInfo entity = iExportTableInfoService.getById(exportTableId);
            TableInfo tableInfo = iTableInfoService.getById(entity.getTableId());
            String fileName = entity.getName(); //文件名

            //获取文件header头
            TableColumnInfo columnInfoEntity = new TableColumnInfo();
            columnInfoEntity.setExportTableId(exportTableId);
            QueryWrapper<TableColumnInfo> qw = new QueryWrapper<>(columnInfoEntity);
            List<TableColumnInfo> fieldInfoList = iTableColumnInfoService.list(qw);
            //获取文件数据
            List<Map<String, String>> dataList = null;
            if(entity == null || com.alibaba.druid.util.StringUtils.isEmpty(entity.getQuerySql())){
                dataList = iExportTableInfoService.getDataFromTable(tableInfo.getTableName());
            }else {
                dataList = iExportTableInfoService.execQuery(entity.getQuerySql(),tableInfo.getTableName());
            }
//        File csvFile = CSVUtils.createCSVFile(dataList, fieldInfoList, "/home/tmp/", fileName);
            if(StringUtils.equals("csv",downloadType)){
                CSVUtils.responseCSVFile(entity.getName(),dataList, fieldInfoList,response);
            }else if(StringUtils.equals("excel",downloadType)){
                ExcelUtils.responseFile(fileName,dataList, fieldInfoList,response);
            }else{
                if("csv".equals(entity.getFileFormat())){
                    CSVUtils.responseCSVFile(entity.getName(),dataList, fieldInfoList,response);
                }else{
                    ExcelUtils.responseFile(fileName,dataList, fieldInfoList,response);
                }
            }


        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.removeAttribute("downloadStatus");
        }
    }

    /**
     * 下载打包好的csv文件(csv文件需要拆分)
     * @param exportTableIds
     * @param response
     * @param session
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/download/file/zip/{exportTableIds}")
    public ResultData download(@PathVariable String exportTableIds,String downloadType,
                               HttpServletResponse response, HttpSession session) throws Exception {
        ResultData resultData =new ResultData();
        try{
            session.setAttribute("downloadStatus","suc");
            //检查表的描述
            List<File> fileList = new ArrayList<>();
            LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
            StringJoiner path =new StringJoiner(File.separator);
            path.add("tmp").add(user==null?"0":user.getId()+"").add(new Date().getTime()+"").add("");
            for(String idStr : exportTableIds.split(",")){
                Integer exportTableId = Integer.valueOf(idStr);
                ExportTableInfo entity = iExportTableInfoService.getById(exportTableId);
                TableInfo tableInfo = iTableInfoService.getById(entity.getTableId());
                String fileName = entity.getName(); //文件名
                //获取文件header头
                TableColumnInfo columnInfoEntity = new TableColumnInfo();
                columnInfoEntity.setExportTableId(exportTableId);
                QueryWrapper<TableColumnInfo> qw = new QueryWrapper<>(columnInfoEntity);
                List<TableColumnInfo> fieldInfoList = iTableColumnInfoService.list(qw);
                //获取文件数据
                if(entity == null || StringUtils.isEmpty(entity.getQuerySql())){
                    String querySql="select * from ${tableName} ";
                    entity.setQuerySql(querySql);
                }
                if(StringUtils.equals("csv",downloadType)){
                    fileList.addAll(CSVUtils.getOutputStream(iExportTableInfoService,entity.getQuerySql(),tableInfo.getTableName(),fieldInfoList,path+fileName));
                }else if(StringUtils.equals("excel",downloadType)){
                    fileList.addAll(ExcelUtils.getOutputStream(iExportTableInfoService,entity.getQuerySql(),tableInfo.getTableName(),fieldInfoList,path+fileName));
                }else{
                    if("csv".equals(entity.getFileFormat())){
                        fileList.addAll(CSVUtils.getOutputStream(iExportTableInfoService,entity.getQuerySql(),tableInfo.getTableName(),fieldInfoList,path+fileName));
                    }else{
                        fileList.addAll(ExcelUtils.getOutputStream(iExportTableInfoService,entity.getQuerySql(),tableInfo.getTableName(),fieldInfoList,path+fileName));
                    }
                }
            }
            response.setContentType("application/x-msdownload;charset=UTF-8");
            response.setHeader("Content-Disposition",
                    "attachment; filename=" + URLEncoder.encode("日志数据".concat(new Date().getTime()+"").concat(".zip"), "UTF-8"));
            response.setHeader("Pragma", "no-cache");
            ZipUtils.toZip(fileList,response.getOutputStream());
        }catch (Exception e){
            resultData.setCode("-1");
            resultData.setMsg("下载失败");
            resultData.setResult(e);
            e.printStackTrace();
        }finally {
            session.removeAttribute("downloadStatus");
            return resultData;
        }
    }


    /**
     * 获取文件下载状态
     * @return ok/fail
     */
    @RequestMapping(value = "/download/file/status")
    public ResultData downloadStatus( HttpSession session) {
        Object t =session.getAttribute("downloadStatus");
        if(t== null){
            return new ResultData("0","无正在下载内容");
        }
        return new ResultData();
    }
}
