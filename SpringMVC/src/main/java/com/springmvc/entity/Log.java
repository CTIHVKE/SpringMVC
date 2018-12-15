package com.springmvc.entity;

public class Log {
    private String logGuid;

    private String logTime;

    private String logUser;

    private String logTitle;

    private String logContent;

    public Log(String logGuid, String logTime, String logUser, String logTitle, String logContent) {
        this.logGuid = logGuid;
        this.logTime = logTime;
        this.logUser = logUser;
        this.logTitle = logTitle;
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