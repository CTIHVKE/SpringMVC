package com.springmvc.dao;

import com.springmvc.entity.Log;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface LogMapper {
    @Delete({
        "delete from LOG",
        "where LOG_GUID = #{logGuid,jdbcType=CHAR}"
    })
    int deleteByPrimaryKey(String logGuid);

    @Insert({
        "insert into LOG (LOG_GUID, LOG_TIME, ",
        "LOG_USER, LOG_TITLE, ",
        "LOG_CONTENT)",
        "values (#{logGuid,jdbcType=CHAR}, #{logTime,jdbcType=VARCHAR}, ",
        "#{logUser,jdbcType=VARCHAR}, #{logTitle,jdbcType=VARCHAR}, ",
        "#{logContent,jdbcType=VARCHAR})"
    })
    int insert(Log record);

    int insertSelective(Log record);

    @Select({
        "select",
        "LOG_GUID, LOG_TIME, LOG_USER, LOG_TITLE, LOG_CONTENT",
        "from LOG",
        "where LOG_GUID = #{logGuid,jdbcType=CHAR}"
    })
    @ResultMap("BaseResultMap")
    Log selectByPrimaryKey(String logGuid);

    int updateByPrimaryKeySelective(Log record);

    @Update({
        "update LOG",
        "set LOG_TIME = #{logTime,jdbcType=VARCHAR},",
          "LOG_USER = #{logUser,jdbcType=VARCHAR},",
          "LOG_TITLE = #{logTitle,jdbcType=VARCHAR},",
          "LOG_CONTENT = #{logContent,jdbcType=VARCHAR}",
        "where LOG_GUID = #{logGuid,jdbcType=CHAR}"
    })
    int updateByPrimaryKey(Log record);
}