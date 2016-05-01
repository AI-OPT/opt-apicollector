package com.ai.runner.apicollector.vo;

public class APIClassFieldDoc {

    private int id;

    private String commentText;

    private String className;

    private String paramName;

    private int pid;

    // 版本
    private String version;

    // 是否可以展开属性
    private boolean canUnfold;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public boolean isCanUnfold() {
        return canUnfold;
    }

    public void setCanUnfold(boolean canUnfold) {
        this.canUnfold = canUnfold;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }
    
    

}
