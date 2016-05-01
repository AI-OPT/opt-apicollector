package com.ai.runner.apicollector.vo;

import java.util.List;

/**
 * API内的类文档信息 Date: 2015年10月11日 <br>
 * Copyright (c) 2015 asiainfo.com <br>
 * 
 * @author zhangchao
 */
public class APIClassDoc {

    private String owner;

    private String ownerType;

    private String artifactId;

    // 是否是服务接口类
    private boolean isInterface;

    private String belong;

    // 类名
    private String className;

    private String name;

    // 类注释
    private String commentText;

    // 类HashCode
    private int id;

    // 版本
    private String version;

    // 是否可以展开属性
    private boolean canUnfold;

    // 类的属性字段列表
    private List<APIClassFieldDoc> classFields;

    public boolean isInterface() {
        return isInterface;
    }

    public void setInterface(boolean isInterface) {
        this.isInterface = isInterface;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<APIClassFieldDoc> getClassFields() {
        return classFields;
    }

    public void setClassFields(List<APIClassFieldDoc> classFields) {
        this.classFields = classFields;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBelong() {
        return belong;
    }

    public void setBelong(String belong) {
        this.belong = belong;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getOwnerType() {
        return ownerType;
    }

    public void setOwnerType(String ownerType) {
        this.ownerType = ownerType;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public void setArtifactId(String artifactId) {
        this.artifactId = artifactId;
    }

}
