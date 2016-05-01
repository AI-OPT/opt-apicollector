package com.ai.runner.apicollector.vo;

/**
 * API文档信息 Date: 2015年10月11日 <br>
 * Copyright (c) 2015 asiainfo.com <br>
 * 
 * @author zhangchao
 */
public class APIDoc {

    // hash值，由类名+方法+版本计算
    private int id;

    // 服务编码
    private String apiCode;

    // 服务类名
    private String interfaceName;

    // 服务方法名
    private String methodName;

    // 服务提供者
    private String owner;

    // 提供者分类
    private String ownerType;

    // 服务版本
    private String version;

    // 服务坐标分组标识
    private String groupId;

    // 服务构件标识
    private String artifactId;

    // 简要说明
    private String briefComment;

    // 详细说明
    private String detailComment;

    // 自动发布时间
    private String publishDate;

    // 服务作者
    private String author;

    // 服务返回参数信息
    private APIParamDoc returnAPIParamDoc;

    // 服务入参信息
    private APIParamDoc[] inAPIParamDocs;

    // 是否支持REST访问
    private boolean restSupported;

    // REST地址相对路径
    private String restRelativeURL;

    // REST服务的请求方式
    private String restMethod;

    // 服务签名异常信息,多个以逗号区分
    private String exceptions;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public void setArtifactId(String artifactId) {
        this.artifactId = artifactId;
    }

    public String getBriefComment() {
        return briefComment;
    }

    public void setBriefComment(String briefComment) {
        this.briefComment = briefComment;
    }

    public String getDetailComment() {
        return detailComment;
    }

    public void setDetailComment(String detailComment) {
        this.detailComment = detailComment;
    }

    public APIParamDoc getReturnAPIParamDoc() {
        return returnAPIParamDoc;
    }

    public void setReturnAPIParamDoc(APIParamDoc returnAPIParamDoc) {
        this.returnAPIParamDoc = returnAPIParamDoc;
    }

    public APIParamDoc[] getInAPIParamDocs() {
        return inAPIParamDocs;
    }

    public void setInAPIParamDocs(APIParamDoc[] inAPIParamDocs) {
        this.inAPIParamDocs = inAPIParamDocs;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getOwnerType() {
        return ownerType;
    }

    public void setOwnerType(String ownerType) {
        this.ownerType = ownerType;
    }

    public String getApiCode() {
        return apiCode;
    }

    public void setApiCode(String apiCode) {
        this.apiCode = apiCode;
    }

    public boolean isRestSupported() {
        return restSupported;
    }

    public void setRestSupported(boolean restSupported) {
        this.restSupported = restSupported;
    }

    public String getRestRelativeURL() {
        return restRelativeURL;
    }

    public void setRestRelativeURL(String restRelativeURL) {
        this.restRelativeURL = restRelativeURL;
    }

    public String getRestMethod() {
        return restMethod;
    }

    public void setRestMethod(String restMethod) {
        this.restMethod = restMethod;
    }

    public String getExceptions() {
        return exceptions;
    }

    public void setExceptions(String exceptions) {
        this.exceptions = exceptions;
    }

}
