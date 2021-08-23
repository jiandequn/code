package com.ppfuns.controller;

import com.alibaba.druid.util.StringUtils;
import com.ppfuns.entity.ResultData;
import com.ppfuns.entity.ResultPage;
import com.ppfuns.entity.SearchPage;
import com.ppfuns.entity.base.TableInfo;
import com.ppfuns.entity.table.ExportTableEntity;
import com.ppfuns.entity.table.TableColumnInfoEntity;
import com.ppfuns.service.ExportTableService;
import com.ppfuns.service.TableColumnInfoService;
import com.ppfuns.service.TableInfoService;
import com.ppfuns.utils.CSVUtils;
import com.ppfuns.utils.ZipUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: 简德群
 * Date: 2019/10/28
 * Time: 16:39
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/download")
public class DownFileController {
    @Autowired
    private TableInfoService tableInfoService;
    @Autowired
    private ExportTableService exportTableService;
    @Autowired
    private TableColumnInfoService tableColumnInfoService;
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/export/file/{tableName}")
    public void doExport(ModelMap model, @PathVariable String tableName,
                         HttpServletResponse response) throws Exception {
        //检查表的描述
        TableInfo tableInfo = tableInfoService.get(tableName);
        SearchPage<ExportTableEntity> searchPage  = new SearchPage<>();
        ExportTableEntity exportTableEntity = new ExportTableEntity();
        exportTableEntity.setTableId(tableInfo.getId());
        searchPage.setT(exportTableEntity);
        ResultPage<ExportTableEntity> resultPage = exportTableService.getList(searchPage);
        String fileName = null;
        List<TableColumnInfoEntity> fieldInfoList = null;
        if(resultPage.getRows().size() >0){
            exportTableEntity = resultPage.getRows().get(0);
            fileName = exportTableEntity.getName();//得到文件名
            //获取文件header头
            TableColumnInfoEntity columnInfoEntity = new TableColumnInfoEntity();
            columnInfoEntity.setExportTableId(exportTableEntity.getId());
            fieldInfoList = tableColumnInfoService.select(columnInfoEntity);
        } else{
            TableColumnInfoEntity tableColumnInfoEntity  =new TableColumnInfoEntity();
            fieldInfoList = tableInfoService.getTableColumnsInfoList(tableColumnInfoEntity);
        }
        //获取文件数据
        List<Map<String, String>> dataList = null;
        if(exportTableEntity == null || StringUtils.isEmpty(exportTableEntity.getQuerySql())){
            dataList = tableInfoService.getDataFromTable(tableName);
        }else {
            dataList = exportTableService.execQuery(exportTableEntity.getQuerySql(),tableName);
        }
        response.setContentType("application/csv;charset=UTF-8");
        response.setHeader("Content-Disposition",
                "attachment; filename=" + URLEncoder.encode(fileName.concat(new Date().getTime()+"").concat(".csv"), "UTF-8"));
        response.setHeader("Pragma", "no-cache");
        CSVUtils.responseCSVFile(dataList, fieldInfoList,response);
    }

    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/file/{exportTableId}")
    public void download(@PathVariable Integer exportTableId,
                         HttpServletResponse response, HttpSession session) throws Exception {
        try{
            //检查表的描述
            session.setAttribute("downloadStatus","success");
            ExportTableEntity entity = exportTableService.getById(exportTableId);
            TableInfo tableInfo = tableInfoService.getById(entity.getTableId());
            String fileName = entity.getName(); //文件名
            //获取文件header头
            TableColumnInfoEntity columnInfoEntity = new TableColumnInfoEntity();
            columnInfoEntity.setExportTableId(exportTableId);
            List<TableColumnInfoEntity> fieldInfoList = tableColumnInfoService.select(columnInfoEntity);

            //获取文件数据
            List<Map<String, String>> dataList = null;
            if(entity == null || StringUtils.isEmpty(entity.getQuerySql())){

                dataList = tableInfoService.getDataFromTable(tableInfo.getTableName());
            }else {
                dataList = exportTableService.execQuery(entity.getQuerySql(),tableInfo.getTableName());
            }
            response.setContentType("application/csv;charset=UTF-8");
            response.setHeader("Content-Disposition",
                    "attachment; filename=" + URLEncoder.encode(fileName.concat(new Date().getTime()+"").concat(".csv"), "UTF-8"));
            response.setHeader("Pragma", "no-cache");
//        File csvFile = CSVUtils.createCSVFile(dataList, fieldInfoList, "/home/tmp/", fileName);
            CSVUtils.responseCSVFile(dataList, fieldInfoList,response);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.removeAttribute("downloadStatus");
        }
    }
    @RequestMapping("/file/status")
    @ResponseBody
    public ResultData status(HttpSession session,String status){
        Object t =session.getAttribute("downloadStatus");
        if(!StringUtils.isEmpty(status)){
            session.setAttribute("downloadStatus","success");
        }else if(StringUtils.isEmpty(status) && t == null){
            return new ResultData("0","无正在下载内容");
        }
        return new ResultData();
    }

    /**
     * 下载打包好的csv文件(csv文件需要拆分)
     * @param exportTableIds
     * @param response
     * @param session
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/file/zip/{exportTableIds}")
    public ResultData download(@PathVariable String exportTableIds,
                         HttpServletResponse response, HttpSession session) throws Exception {

        try{
            //检查表的描述
            List<File> fileList = new ArrayList<>();
            for(String idStr : exportTableIds.split(",")){
                Integer exportTableId = Integer.valueOf(idStr);
                ExportTableEntity entity = exportTableService.getById(exportTableId);
                TableInfo tableInfo = tableInfoService.getById(entity.getTableId());
                String fileName = entity.getName(); //文件名
                //获取文件header头
                TableColumnInfoEntity columnInfoEntity = new TableColumnInfoEntity();
                columnInfoEntity.setExportTableId(exportTableId);
                List<TableColumnInfoEntity> fieldInfoList = tableColumnInfoService.select(columnInfoEntity);
                //获取文件数据
                if(entity == null || StringUtils.isEmpty(entity.getQuerySql())){
                    String querySql="select * from ${tableName} ";
                    entity.setQuerySql(querySql);
//                    dataList = tableInfoService.getDataFromTable(tableInfo.getTableName());
                }
                fileList.addAll(CSVUtils.getOutputStream(exportTableService,entity.getQuerySql(),tableInfo.getTableName(),fieldInfoList,fileName));
            }

            response.setContentType("application/x-msdownload;charset=UTF-8");
            response.setHeader("Content-Disposition",
                    "attachment; filename=" + URLEncoder.encode("日志数据".concat(new Date().getTime()+"").concat(".zip"), "UTF-8"));
            response.setHeader("Pragma", "no-cache");
            ZipUtils.toZip(fileList,response.getOutputStream());
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.removeAttribute("downloadStatus");
            return new ResultData();
        }
    }
}
