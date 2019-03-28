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
}