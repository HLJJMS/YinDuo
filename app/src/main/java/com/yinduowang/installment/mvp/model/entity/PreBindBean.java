package com.yinduowang.installment.mvp.model.entity;

public class PreBindBean {

    /**
     * status : -1
     * uniqueCode :
     * message : 认证信息不匹配
     */

    private int status;
    private String uniqueCode;
    private String message;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUniqueCode() {
        return uniqueCode;
    }

    public void setUniqueCode(String uniqueCode) {
        this.uniqueCode = uniqueCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}