package com.springmvc.entity;

import java.math.BigDecimal;
import java.util.Date;

public class SysUser {
    private Short userid;

    private String userguid;

    private String username;

    private String loginname;

    private String password;

    private Date lasttime;

    private String recordstatus;

    private BigDecimal createuserid;

    private Date createdate;

    private BigDecimal modifyuserid;

    private Date modifydate;

    public SysUser(Short userid, String userguid, String username, String loginname, String password, Date lasttime, String recordstatus, BigDecimal createuserid, Date createdate, BigDecimal modifyuserid, Date modifydate) {
        this.userid = userid;
        this.userguid = userguid;
        this.username = username;
        this.loginname = loginname;
        this.password = password;
        this.lasttime = lasttime;
        this.recordstatus = recordstatus;
        this.createuserid = createuserid;
        this.createdate = createdate;
        this.modifyuserid = modifyuserid;
        this.modifydate = modifydate;
    }

    public Short getUserid() {
        return userid;
    }

    public String getUserguid() {
        return userguid;
    }

    public String getUsername() {
        return username;
    }

    public String getLoginname() {
        return loginname;
    }

    public String getPassword() {
        return password;
    }

    public Date getLasttime() {
        return lasttime;
    }

    public String getRecordstatus() {
        return recordstatus;
    }

    public BigDecimal getCreateuserid() {
        return createuserid;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public BigDecimal getModifyuserid() {
        return modifyuserid;
    }

    public Date getModifydate() {
        return modifydate;
    }

    public SysUser(){

    }

    public void setUserid(Short userid) {
        this.userid = userid;
    }

    public void setUserguid(String userguid) {
        this.userguid = userguid;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setLasttime(Date lasttime) {
        this.lasttime = lasttime;
    }

    public void setRecordstatus(String recordstatus) {
        this.recordstatus = recordstatus;
    }

    public void setCreateuserid(BigDecimal createuserid) {
        this.createuserid = createuserid;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public void setModifyuserid(BigDecimal modifyuserid) {
        this.modifyuserid = modifyuserid;
    }

    public void setModifydate(Date modifydate) {
        this.modifydate = modifydate;
    }
}