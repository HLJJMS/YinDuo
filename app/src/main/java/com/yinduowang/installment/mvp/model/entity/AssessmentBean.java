package com.yinduowang.installment.mvp.model.entity;

import java.io.Serializable;

public class AssessmentBean implements Serializable {

    /**
     * bankCardNo : string
     * idName : string
     * idNo : string
     * memberId : string
     * phoneNo : string
     * preOrderNo : string
     */

    private String bankCardNo;
    private String idName;
    private String idNo;
    private String memberId;
    private String phoneNo;
    private String preOrderNo;

    public String getBankCardNo() {
        return bankCardNo;
    }

    public void setBankCardNo(String bankCardNo) {
        this.bankCardNo = bankCardNo;
    }

    public String getIdName() {
        return idName;
    }

    public void setIdName(String idName) {
        this.idName = idName;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getPreOrderNo() {
        return preOrderNo;
    }

    public void setPreOrderNo(String preOrderNo) {
        this.preOrderNo = preOrderNo;
    }
}