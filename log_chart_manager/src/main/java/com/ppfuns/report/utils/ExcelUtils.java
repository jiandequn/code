package com.ppfuns.report.utils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.WriteTable;
import com.ppfuns.report.entity.TableColumnInfo;
import com.ppfuns.report.service.IExportTableInfoService;
import com.ppfuns.sys.entity.LoginUser;
import org.apache.commons.io.IOUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Administrator on 2020/12/24.
 */
public class ExcelUtils {
    public static void responseFile(String fileName, List<Map<String, String>> exportData, List<TableColumnInfo> tableFieldInfos, HttpServletResponse response) {
        OutputStream out = null;
        try {
//            response.setContentType("application/;charset=UTF-8");
//            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            response.setHeader("Content-Disposition",
                    "attachment; filename=" + URLEncoder.encode(fileName.concat(new Date().getTime()+"").concat(".xlsx"), "UTF-8"));
            response.setHeader("Pragma", "no-cache");
            File file = getFile(fileName, tableFieldInfos, exportData);
            response.addHeader("Content-Length", "" + file.length());
//            InputStream fis = new BufferedInputStream(new FileInputStream(file));
//            byte[] buffer = new byte[fis.available()];
            out = new BufferedOutputStream(response.getOutputStream());
            InputStream inputStream = new FileInputStream(file);
            IOUtils.copy(inputStream, out);
//            out.write(buffer);
            // 写入文件头部
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private static File getFile(String fileName, List<TableColumnInfo> tableFieldInfos, List<Map<String, String>> exportData){
        LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        StringJoiner path =new StringJoiner(File.separator);
        path.add("tmp").add(user==null?"0":user.getId()+"").add(new Date().getTime()+"").add(fileName+".xlsx");
        File file = new File(path.toString());
        if(!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }
        List<List<String>> head = tableFieldInfos.stream().map(s -> {
            if (StringUtils.isEmpty(s.getColumnComment())) {
                return Arrays.asList(s.getColumnName());
            }
            return Arrays.asList(s.getColumnComment());
        }).collect(Collectors.toList());
        WriteSheet writeSheet = EasyExcel.writerSheet().head(head).build();
        ExcelWriter excelWriter = EasyExcel.write(file).excelType(ExcelTypeEnum.XLSX).build();
        WriteTable build = EasyExcel.writerTable(0).needHead(false).build();
        List<List<String>> body = exportData.stream().map(s -> {
            List<String> line = new ArrayList<String>();
            tableFieldInfos.forEach(a ->{
                Object t = s.get(a.getColumnName()) == null ? "" : s.get(a.getColumnName());
                line.add(t.toString());
            });
            return line;
        }).collect(Collectors.toList());
        excelWriter.write(body,writeSheet,build);
        excelWriter.finish();
        System.out.println("文件路径："+file.getAbsolutePath());
        return file;
    }
    public static List<File> getOutputStream(IExportTableInfoService iExportTableInfoService, String querySql, String tableName, List<TableColumnInfo> tableFieldInfos, String fileName) {
        // 写入文件头部
        List<Map<String, String>> maps = iExportTableInfoService.execQuery(querySql, tableName);
        List<List<String>> head = tableFieldInfos.stream().map(s -> {
            if (StringUtils.isEmpty(s.getColumnComment())) {
                return Arrays.asList(s.getColumnName());
            }
            return Arrays.asList(s.getColumnComment());
        }).collect(Collectors.toList());
        WriteSheet writeSheet = EasyExcel.writerSheet().head(head).build();
        File file = new File(fileName.concat(".xlsx"));
        if(!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }
        ExcelWriter excelWriter = EasyExcel.write(file).excelType(ExcelTypeEnum.XLSX).build();
        WriteTable build = EasyExcel.writerTable(0).needHead(false).build();
        List<List<String>> body = maps.stream().map(s -> {
            List<String> line = new ArrayList<String>();
            tableFieldInfos.forEach(a -> {
                Object obj = s.get(a.getColumnName()) == null ? "" : s.get(a.getColumnName());
                line.add(obj.toString());
            });
            return line;
        }).collect(Collectors.toList());
        excelWriter.write(body,writeSheet,build);
        excelWriter.finish();
        List<File> outputStreamList = new ArrayList<>();
        outputStreamList.add(file);
        return outputStreamList;
    }

}
