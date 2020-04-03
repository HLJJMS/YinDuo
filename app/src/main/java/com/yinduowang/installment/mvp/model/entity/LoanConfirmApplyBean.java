package com.yinduowang.installment.mvp.model.entity;

import java.io.Serializable;

public class LoanConfirmApplyBean implements Serializable {


    /**
     * status : 0
     * message : 申请成功
     * loanId : 17
     * loanAmount : 1500.00
     * loanDays : 7
     * loanRateAmount : 5.25
     * serviceAmount : 450.00
     * returnAmount : 1500.00
     * returnBankInfo : 建设银行（尾号0092）
     * expireRepayFund : 1955.25
     * planRepayDate : 2019-03-20
     */

    private int status;
    private String message;
    private String loanId;
    private String loanAmount;
    private String loanDays;
    private String loanRateAmount;
    private String serviceAmount;
    private String returnAmount;
    private String returnBankInfo;
    private String expireRepayFund;
    private String planRepayDate;
    private String applyDate;

    public String getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(String applyDate) {
        this.applyDate = applyDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    public String getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(String loanAmount) {
        this.loanAmount = loanAmount;
    }

    public String getLoanDays() {
        return loanDays;
    }

    public void setLoanDays(String loanDays) {
        this.loanDays = loanDays;
    }

    public String getLoanRateAmount() {
        return loanRateAmount;
    }

    public void setLoanRateAmount(String loanRateAmount) {
        this.loanRateAmount = loanRateAmount;
    }

    public String getServiceAmount() {
        return serviceAmount;
    }

    public void setServiceAmount(String serviceAmount) {
        this.serviceAmount = serviceAmount;
    }

    public String getReturnAmount() {
        return returnAmount;
    }

    public void setReturnAmount(String returnAmount) {
        this.returnAmount = returnAmount;
    }

    public String getReturnBankInfo() {
        return returnBankInfo;
    }

    public void setReturnBankInfo(String returnBankInfo) {
        this.returnBankInfo = returnBankInfo;
    }

    public String getExpireRepayFund() {
        return expireRepayFund;
    }

    public void setExpireRepayFund(String expireRepayFund) {
        this.expireRepayFund = expireRepayFund;
    }

    public String getPlanRepayDate() {
        return planRepayDate;
    }

    public void setPlanRepayDate(String planRepayDate) {
        this.planRepayDate = planRepayDate;
    }
}
