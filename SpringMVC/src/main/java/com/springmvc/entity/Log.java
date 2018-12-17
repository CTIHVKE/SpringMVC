package com.springmvc.entity;

public class Log {

    private String logGuid;

    private String logTime;

    private String logUser;

    private String logTitle;

    private String logContent;

    public Log() {
        this.logGuid = logGuid;
        this.logTime = logTime;
        this.logUser = logUser;
        this.logTitle = logTitle;
        this.logContent = logContent;
    }

    public void setLogGuid(String logGuid) {
        this.logGuid = logGuid;
    }

    public void setLogTime(String logTime) {
        this.logTime = logTime;
    }

    public void setLogUser(String logUser) {
        this.logUser = logUser;
    }

    public void setLogTitle(String logTitle) {
        this.logTitle = logTitle;
    }

    public void setLogContent(String logContent) {
        this.logContent = logContent;
    }

    public String getLogGuid() {
        return logGuid;
    }

    public String getLogTime() {
        return logTime;
    }

    public String getLogUser() {
        return logUser;
    }

    public String getLogTitle() {
        return logTitle;
    }

    public String getLogContent() {
        return logContent;
    }
}