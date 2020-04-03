package com.yinduowang.installment.mvp.model.entity;

import java.io.Serializable;
import java.util.List;

public class LoanEntity implements Serializable {

    /**
     * maxAmount : 1200.0
     * minAmount : 100.0
     * availableCredit : 1200.0
     * rate : 10.0
     * days : 7
     * status : 0
     * fundArray : [100,200,300,400,500,600,700,800,900,1000,1100,1200]
     * maxVersion : 1
     * loanConfigId : 1
     */

    private int maxAmount;
    private int minAmount;
    private int availableCredit;
    private double rate;
    private int days;
    private int status;
    private String maxVersion;
    private String loanConfigId;
    private List<Integer> fundArray;
    private String buttonStatus;// (string, optional): 立即借款按钮状态 0-不可按 1-可按 ,
    private String buttonMessage;
    private int eject;// 额度弹窗是否显示
    private String toastFund;
    public int getEject() {
        return eject;
    }

    public void setEject(int eject) {
        this.eject = eject;
    }

    public String getToastFund() {
        return toastFund;
    }

    public void setToastFund(String toastFund) {
        this.toastFund = toastFund;
    }


    public String getButtonMessage() {
        return buttonMessage;
    }

    public void setButtonMessage(String buttonMessage) {
        this.buttonMessage = buttonMessage;
    }

    public String getButtonStatus() {
        return buttonStatus;
    }

    public void setButtonStatus(String buttonStatus) {
        this.buttonStatus = buttonStatus;
    }

    public int getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(int maxAmount) {
        this.maxAmount = maxAmount;
    }

    public int getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(int minAmount) {
        this.minAmount = minAmount;
    }

    public int getAvailableCredit() {
        return availableCredit;
    }

    public void setAvailableCredit(int availableCredit) {
        this.availableCredit = availableCredit;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMaxVersion() {
        return maxVersion;
    }

    public void setMaxVersion(String maxVersion) {
        this.maxVersion = maxVersion;
    }

    public String getLoanConfigId() {
        return loanConfigId;
    }

    public void setLoanConfigId(String loanConfigId) {
        this.loanConfigId = loanConfigId;
    }

    public List<Integer> getFundArray() {
        return fundArray;
    }

    public void setFundArray(List<Integer> fundArray) {
        this.fundArray = fundArray;
    }
}
