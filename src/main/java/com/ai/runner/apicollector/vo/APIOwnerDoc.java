package com.ai.runner.apicollector.vo;

public class APIOwnerDoc {
    
    // hash值
    private int id;

    // 服务提供者
    private String owner;

    // 提供者分类
    private String ownerType;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
    
    

}
