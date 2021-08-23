package com.ppfuns.utils;

import com.ppfuns.entity.table.TableColumnInfoEntity;
import com.ppfuns.service.ExportTableService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 * User: 简德群
 * Date: 2019/10/28
 * Time: 17:42
 * To change this template use File | Settings | File Templates.
 */
public class CSVUtils {
    private final static String NEW_LINE_SEPARATOR = "\n";
    private final static int PAGE_SIZE = 200000;

    /**
     * 生成为CVS文件
     *
     * @param exportData      源数据List
     * @param tableFieldInfos csv文件的列表头map
     * @param outPutPath      文件路径
     * @param fileName        文件名称
     * @return
     */

    @SuppressWarnings("rawtypes")


    public static File createCSVFile(List<Map<String, String>> exportData, List<TableColumnInfoEntity> tableFieldInfos, String outPutPath,
                                     String fileName) {
        File csvFile = null;
        BufferedWriter csvFileOutputStream = null;
        try {
            File file = new File(outPutPath);
            if (!file.exists()) {
                file.mkdir();
            }
            //定义文件名格式并创建
            csvFile = File.createTempFile(fileName, ".csv", new File(outPutPath));
            System.out.println("csvFile：" + csvFile);
            // UTF-8使正确读取分隔符","
            //如果生产文件乱码，windows下用gbk，linux用UTF-8
//            String charSet="UTF-8";
//            if(System.getenv().containsKey("OS")){
//                if(System.getenv().get("OS").contains("Windows")){
//                    charSet = "gbk";
//                }
//            }
            csvFileOutputStream = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(
                    csvFile), "UTF-8"), 1024);
            System.out.println("csvFileOutputStream：" + csvFileOutputStream);
            // 写入文件头部
            StringBuffer sb = new StringBuffer(1024);
            for (TableColumnInfoEntity info : tableFieldInfos) {
                sb.append("\"");
                if (StringUtils.isEmpty(info.getColumnComment())) {
                    sb.append(info.getColumnName());
                } else {
                    sb.append(info.getColumnComment());
                }
                sb.append("\"");
                sb.append(",");
            }
            csvFileOutputStream.write(sb.substring(0, sb.length() - 1).concat("\n\r"));
            sb = null;

            // 写入文件内容
            for (Map<String, String> map : exportData) {
                StringBuffer stringBuffer = new StringBuffer();
                tableFieldInfos.forEach(info -> {
                    Object val = map.get(info.getColumnName()) == null ? "" : map.get(info.getColumnName());
                    stringBuffer.append("\"");
                    stringBuffer.append(val);
                    stringBuffer.append("\"");
                    stringBuffer.append(",");
                });
                csvFileOutputStream.write(stringBuffer.substring(0, stringBuffer.length() - 1).concat("\n\r"));
            }
            csvFileOutputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                csvFileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return csvFile;
    }

    public static void toCsvFile(List<Map<String, String>> exportData, List<TableColumnInfoEntity> tableFieldInfos, String fileName, HttpServletResponse response) {
        CSVFormat formator = CSVFormat.DEFAULT.withRecordSeparator(NEW_LINE_SEPARATOR);
        //创建FileWriter对象
        FileWriter fileWriter = null;
        CSVPrinter printer = null;
        try {
            fileWriter = new FileWriter(fileName);
            //创建CSVPrinter对象
            printer = new CSVPrinter(fileWriter, formator);
            //写入列头数据
            List<String> headerList = tableFieldInfos.stream().map(TableColumnInfoEntity::getColumnComment).collect(Collectors.toList());
            printer.printRecord(headerList);
            headerList = tableFieldInfos.stream().map(TableColumnInfoEntity::getColumnName).collect(Collectors.toList());
            //写入实体数据
            for (Map<String, String> map : exportData) {
                List<String> dataRow = headerList.stream().map(map::get).peek(s -> s = s == null ? "" : s).collect(Collectors.toList());
                printer.printRecord(dataRow);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fileWriter.flush();
                fileWriter.close();
                printer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void responseCSVFile(List<Map<String, String>> exportData, List<TableColumnInfoEntity> tableFieldInfos, HttpServletResponse response) {

        OutputStreamWriter out = null;
        try {
            if (System.getenv().containsKey("OS")) {
                if (System.getenv().get("OS").contains("Windows")) {
                    out = new OutputStreamWriter(response.getOutputStream());
                }
            }
            if (out == null) {
                out = new OutputStreamWriter(response.getOutputStream(), "UTF-8");
                out.write(new String(new byte[]{(byte) 0xEF, (byte) 0xBB, (byte) 0xBF}));
            }
            // 写入文件头部
            StringBuffer sb = new StringBuffer(1024);
            for (TableColumnInfoEntity info : tableFieldInfos) {
                sb.append("\"");
                if (StringUtils.isEmpty(info.getColumnComment())) {
                    sb.append(info.getColumnName());
                } else {
                    sb.append(info.getColumnComment());
                }
                sb.append("\"");
                sb.append(",");
            }
            out.write(sb.substring(0, sb.length() - 1).concat("\n"));
            sb = null;
            // 写入文件内容
            for (Map<String, String> map : exportData) {
                StringBuffer stringBuffer = new StringBuffer();
                tableFieldInfos.forEach(info -> {
                    Object val = map.get(info.getColumnName()) == null ? "" : map.get(info.getColumnName());
                    stringBuffer.append("\"");
                    stringBuffer.append(val);
                    stringBuffer.append("\"");
                    stringBuffer.append(",");
                });
                out.write(stringBuffer.substring(0, stringBuffer.length() - 1).concat("\n"));
            }
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

    public static List<File> getOutputStream(ExportTableService exportTableService,String querySql,String tableName, List<TableColumnInfoEntity> tableFieldInfos, String fileName) {
        // 写入文件头部
        int fileNum = 0;
        List<File> outputStreamList = new ArrayList<>();
        int fileSize = 0;
        StringBuffer header = new StringBuffer(1024);
        int tablefieldLen = tableFieldInfos.size();
        for (int i = 0; i < tablefieldLen; i++) {
            TableColumnInfoEntity info = tableFieldInfos.get(i);
            header.append("\"");
            if (StringUtils.isEmpty(info.getColumnComment())) {
                header.append(info.getColumnName());
            } else {
                header.append(info.getColumnComment());
            }
            header.append("\"");
            if (i < tablefieldLen - 1) {
                header.append(",");
            } else {
                header.append("\n");
            }
        }
        fileSize += header.toString().getBytes().length;
        // 写入文件内容
        File file = new File(fileName.concat(".csv"));
        OutputStreamWriter out = null;
        try {
            out = newOut(file, header.toString());
            outputStreamList.add(file);
            int count = exportTableService.execQueryCount(querySql,tableName);
            if(count>0){
                Integer pageIndex = 0;
                Integer pageSize = PAGE_SIZE;
                while(true){
                    if(pageIndex*pageSize>=count) break;
                    List<Map<String, String>> dataList = exportTableService.execQueryByPage(querySql,tableName,pageIndex*pageSize,pageSize);
                    for (Map<String, String> map : dataList) {
                        StringBuffer stringBuffer = new StringBuffer();
                        for (int i = 0; i < tablefieldLen; i++) {
                            TableColumnInfoEntity info = tableFieldInfos.get(i);
                            Object val = map.get(info.getColumnName()) == null ? "" : map.get(info.getColumnName());
                            stringBuffer.append("\"");
                            stringBuffer.append("\"");
                            stringBuffer.append(val);
                            if (i < tablefieldLen - 1) {
                                stringBuffer.append(",");
                            } else {
                                stringBuffer.append("\n");
                            }
                        }
                        fileSize += stringBuffer.toString().getBytes().length;
                        if (fileSize >= EntityProperties.DOWNLOAD_FILE_SIZE) {
                            fileSize = header.toString().getBytes().length+stringBuffer.toString().getBytes().length;
                            out.flush();
                            out.close();
                            file = new File(fileName.concat((++fileNum)+"").concat(".csv"));
                            out = newOut(file, header.toString());
                            outputStreamList.add(file);
                        }
                        out.write(stringBuffer.toString());
                    }
                    pageIndex = pageIndex+1;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return outputStreamList;
    }
    public static List<File> getOutputStream(List<Map<String, String>> exportData, List<TableColumnInfoEntity> tableFieldInfos, String fileName) {
        // 写入文件头部
        int fileNum = 0;
        List<File> outputStreamList = new ArrayList<>();
        int fileSize = 0;
        StringBuffer header = new StringBuffer(1024);
        int tablefieldLen = tableFieldInfos.size();
        for (int i = 0; i < tablefieldLen; i++) {
            TableColumnInfoEntity info = tableFieldInfos.get(i);
            header.append("\"");
            if (StringUtils.isEmpty(info.getColumnComment())) {
                header.append(info.getColumnName());
            } else {
                header.append(info.getColumnComment());
            }
            header.append("\"");
            if (i < tablefieldLen - 1) {
                header.append(",");
            } else {
                header.append("\n");
            }
        }
        fileSize += header.toString().getBytes().length;
        // 写入文件内容
        File file = new File(fileName.concat(".csv"));
        OutputStreamWriter out = null;
        try {
            out = newOut(file, header.toString());
            outputStreamList.add(file);
            for (Map<String, String> map : exportData) {
                StringBuffer stringBuffer = new StringBuffer();
                for (int i = 0; i < tablefieldLen; i++) {
                    TableColumnInfoEntity info = tableFieldInfos.get(i);
                    Object val = map.get(info.getColumnName()) == null ? "" : map.get(info.getColumnName());
                    stringBuffer.append("\"");
                    stringBuffer.append("\"");
                    stringBuffer.append(val);
                    if (i < tablefieldLen - 1) {
                        stringBuffer.append(",");
                    } else {
                        stringBuffer.append("\n");
                    }
                }
                fileSize += stringBuffer.toString().getBytes().length;
                if (fileSize >= EntityProperties.DOWNLOAD_FILE_SIZE) {
                    fileSize = header.toString().getBytes().length+stringBuffer.toString().getBytes().length;
                    out.flush();
                    out.close();
                    file = new File(fileName.concat((++fileNum)+"").concat(".csv"));
                    out = newOut(file, header.toString());
                    outputStreamList.add(file);
                }
                out.write(stringBuffer.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return outputStreamList;
    }

    private static OutputStreamWriter newOut(File file, String header) throws IOException {
        OutputStreamWriter out = null;
        OutputStream ops = new FileOutputStream(file);
        if (System.getenv().containsKey("OS")) {
            if (System.getenv().get("OS").contains("Windows")) {
                out = new OutputStreamWriter(ops);
            }
        }
        if (out == null) {
            out = new OutputStreamWriter(ops, "UTF-8");
            out.write(new String(new byte[]{(byte) 0xEF, (byte) 0xBB, (byte) 0xBF}));
        }
        // 写入文件头部
        // 写入文件内容
        out.write(header);
        return out;
    }

    private static void t(String header, StringBuffer body, String fileName) {
        OutputStream ops = null;
        OutputStreamWriter out = null;
        try {
            ops = new FileOutputStream(fileName.concat(".csv"));
            if (System.getenv().containsKey("OS")) {
                if (System.getenv().get("OS").contains("Windows")) {
                    out = new OutputStreamWriter(ops);
                }
            }
            if (out == null) {
                out = new OutputStreamWriter(ops, "UTF-8");
                out.write(new String(new byte[]{(byte) 0xEF, (byte) 0xBB, (byte) 0xBF}));
            }
            // 写入文件头部
            out.write(header);
            // 写入文件内容
            out.write(body.toString());
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

}

