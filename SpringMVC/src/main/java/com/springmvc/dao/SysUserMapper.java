package com.springmvc.dao;

import com.springmvc.entity.SysUser;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface SysUserMapper {
    @Delete({
        "delete from SYS_USER",
        "where USERID = #{userid,jdbcType=DECIMAL}"
    })
    int deleteByPrimaryKey(Short userid);

    @Insert({
        "insert into SYS_USER (USERID, USERGUID, ",
        "USERNAME, LOGINNAME, ",
        "PASSWORD, LASTTIME, ",
        "RECORDSTATUS, CREATEUSERID, ",
        "CREATEDATE, MODIFYUSERID, ",
        "MODIFYDATE)",
        "values (#{userid,jdbcType=DECIMAL}, #{userguid,jdbcType=VARCHAR}, ",
        "#{username,jdbcType=VARCHAR}, #{loginname,jdbcType=VARCHAR}, ",
        "#{password,jdbcType=VARCHAR}, #{lasttime,jdbcType=TIMESTAMP}, ",
        "#{recordstatus,jdbcType=VARCHAR}, #{createuserid,jdbcType=DECIMAL}, ",
        "#{createdate,jdbcType=TIMESTAMP}, #{modifyuserid,jdbcType=DECIMAL}, ",
        "#{modifydate,jdbcType=TIMESTAMP})"
    })
    int insert(SysUser record);

    int insertSelective(SysUser record);

    @Select({
        "select",
        "USERID, USERGUID, USERNAME, LOGINNAME, PASSWORD, LASTTIME, RECORDSTATUS, CREATEUSERID, ",
        "CREATEDATE, MODIFYUSERID, MODIFYDATE",
        "from SYS_USER",
        "where USERID = #{userid,jdbcType=DECIMAL}"
    })
    @ResultMap("BaseResultMap")
    SysUser selectByPrimaryKey(Short userid);


    @Select({
            "select",
            "USERID, USERGUID, USERNAME, LOGINNAME, PASSWORD, LASTTIME, RECORDSTATUS, CREATEUSERID, ",
            "CREATEDATE, MODIFYUSERID, MODIFYDATE",
            "from SYS_USER",
            "where LOGINNAME = #{loginname,jdbcType=VARCHAR}"
    })
    @ResultMap("BaseResultMap")
    SysUser selectByLoginname(String loginname);

    int updateByPrimaryKeySelective(SysUser record);

    @Update({
        "update SYS_USER",
        "set USERGUID = #{userguid,jdbcType=VARCHAR},",
          "USERNAME = #{username,jdbcType=VARCHAR},",
          "LOGINNAME = #{loginname,jdbcType=VARCHAR},",
          "PASSWORD = #{password,jdbcType=VARCHAR},",
          "LASTTIME = #{lasttime,jdbcType=TIMESTAMP},",
          "RECORDSTATUS = #{recordstatus,jdbcType=VARCHAR},",
          "CREATEUSERID = #{createuserid,jdbcType=DECIMAL},",
          "CREATEDATE = #{createdate,jdbcType=TIMESTAMP},",
          "MODIFYUSERID = #{modifyuserid,jdbcType=DECIMAL},",
          "MODIFYDATE = #{modifydate,jdbcType=TIMESTAMP}",
        "where USERID = #{userid,jdbcType=DECIMAL}"
    })
    int updateByPrimaryKey(SysUser record);
}