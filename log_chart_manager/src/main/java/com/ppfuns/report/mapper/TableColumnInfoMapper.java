package com.ppfuns.report.mapper;

import com.ppfuns.report.entity.TableColumnInfo;
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
public interface TableColumnInfoMapper extends BaseMapper<TableColumnInfo> {
    @Select("  SELECT\n" +
            "        COLUMN_NAME AS columnName,\n" +
            "        COLUMN_COMMENT AS columnComment\n" +
            "    FROM\n" +
            "        information_schema. COLUMNS\n" +
            "    WHERE\n" +
            "        TABLE_SCHEMA = (SELECT DATABASE())\n" +
            "    AND TABLE_NAME = (select table_name from table_info where id=#{tableId})")
    List<TableColumnInfo> getSelectTableField(Integer tableId);
}
