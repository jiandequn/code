package com.ppfuns.report.mapper;

import com.ppfuns.report.entity.TableInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jian.dq
 * @since 2020-10-20
 */
public interface TableInfoMapper extends BaseMapper<TableInfo> {
    @Select("SELECT\n" +
            "\tTABLE_NAME AS tableName,\n" +
            "\tTABLE_COMMENT AS tableComment,\n" +
            "\tCREATE_TIME AS createTime\n" +
            "FROM\n" +
            "\tinformation_schema.TABLES\n" +
            "WHERE\n" +
            "\tTABLE_SCHEMA = (SELECT DATABASE())\n" +
            "ORDER BY\n" +
            "\tCREATE_TIME DESC;")
    List<TableInfo> getAllTableInDb();
}
