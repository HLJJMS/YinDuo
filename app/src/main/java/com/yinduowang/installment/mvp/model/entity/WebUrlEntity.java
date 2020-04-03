package com.yinduowang.installment.mvp.model.entity;

public class WebUrlEntity {



    /**
     * name : 借款相关
     * aliasName : gtsdg
     * url : https://www.baidu.com/
     * version : 1
     * isAuthentication : 1
     */

    private String name;
    private String aliasName;
    private String url;
    private String version;
    private int type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAliasName() {
        return aliasName;
    }

    public void setAliasName(String aliasName) {
        this.aliasName = aliasName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
