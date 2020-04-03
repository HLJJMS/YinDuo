package com.yinduowang.installment.mvp.model.entity;

import java.io.Serializable;

public class OnlineUpdateEntity implements Serializable {


    /**
     * id :
     * newVersion : 2
     * newVersionName : 2
     * newVersionDescription : 多添点东西
     * apkUrl : http://www.baidu.com
     * appid : 2
     * minVersion : 1
     * isUpdate : 1
     * testDownloadUrl : http://www.baidu.com
     * isAuthentication : 1
     * status : 0
     */

    private String id;
    private String newVersion;
    private String newVersionName;
    private String newVersionDescription;
    private String apkUrl;
    private String appid;
    private String minVersion;
    private String isUpdate;
    private String testDownloadUrl;
    private String type;
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNewVersion() {
        return newVersion;
    }

    public void setNewVersion(String newVersion) {
        this.newVersion = newVersion;
    }

    public String getNewVersionName() {
        return newVersionName;
    }

    public void setNewVersionName(String newVersionName) {
        this.newVersionName = newVersionName;
    }

    public String getNewVersionDescription() {
        return newVersionDescription;
    }

    public void setNewVersionDescription(String newVersionDescription) {
        this.newVersionDescription = newVersionDescription;
    }

    public String getApkUrl() {
        return apkUrl;
    }

    public void setApkUrl(String apkUrl) {
        this.apkUrl = apkUrl;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getMinVersion() {
        return minVersion;
    }

    public void setMinVersion(String minVersion) {
        this.minVersion = minVersion;
    }

    public String getIsUpdate() {
        return isUpdate;
    }

    public void setIsUpdate(String isUpdate) {
        this.isUpdate = isUpdate;
    }

    public String getTestDownloadUrl() {
        return testDownloadUrl;
    }

    public void setTestDownloadUrl(String testDownloadUrl) {
        this.testDownloadUrl = testDownloadUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
