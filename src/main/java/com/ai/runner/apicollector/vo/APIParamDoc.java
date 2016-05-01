package com.ai.runner.apicollector.vo;

public class APIParamDoc {

    // hash值
    private int id;

    // 参数类型
    private String className;

    // 参数名
    private String name;

    // 参数注释
    private String commentText;

    // 参数版本
    private String version;

    // 是否可以展开属性
    private boolean canUnfold;

    // 是否包含泛型参数
    private boolean generic;

    // 泛型参数信息
    private APIParamDoc[] genericAPIParamDocs;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
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

    public boolean isGeneric() {
        return generic;
    }

    public void setGeneric(boolean generic) {
        this.generic = generic;
    }

    public APIParamDoc[] getGenericAPIParamDocs() {
        return genericAPIParamDocs;
    }

    public void setGenericAPIParamDocs(APIParamDoc[] genericAPIParamDocs) {
        this.genericAPIParamDocs = genericAPIParamDocs;
    }

}
