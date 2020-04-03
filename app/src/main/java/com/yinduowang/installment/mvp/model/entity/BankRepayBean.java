package com.yinduowang.installment.mvp.model.entity;

import java.io.Serializable;

/**
 * Created by Tsing
 * on 2019/3/7
 */
public class BankRepayBean implements Serializable {


    /**
     * cardNo : 6222023602058313081
     * fund : 1500
     * mobileNo : 13712345678
     * planId : 12
     * realName : 阿萨德
     */

    private String cardNo;
    private String fund;
    private String mobileNo;
    private int loanId;
    private String realName;

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getFund() {
        return fund;
    }

    public void setFund(String fund) {
        this.fund = fund;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public int getLoanId() {
        return loanId;
    }

    public void setLoanId(int loanId) {
        this.loanId = loanId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }
}
