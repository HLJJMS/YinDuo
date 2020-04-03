package com.yinduowang.installment.mvp.model.entity;

import java.io.Serializable;

/**
 * 各种认证状态
 */
public class PerectEntity implements Serializable {

    /**
     * creditCeiling : 0
     * userInfoStatus : -1
     * contactStatus : -1
     * bankCardStatus : -1
     * liveAuthenticationStatus : -1
     * sesameCreditStatus : -1
     * payPasswordStatus : -1
     * operatorStatus : -1
     * verifyingCount : 0
     * title : 完成基础认证，即可享有专属额度
     * xinyanZntzaStatus : -1
     * xinyanCjldStatus : -1
     */

    private int creditCeiling;
    private int userInfoStatus;
    private int contactStatus;
    private int bankCardStatus;
    private int liveAuthenticationStatus;
    private int sesameCreditStatus;
    private int payPasswordStatus;
    private int operatorStatus;
    private int verifyingCount;
    private String title;
    private int xinyanZntzaStatus;
    private int xinyanCjldStatus;

    public int getCreditCeiling() {
        return creditCeiling;
    }

    public void setCreditCeiling(int creditCeiling) {
        this.creditCeiling = creditCeiling;
    }

    public int getUserInfoStatus() {
        return userInfoStatus;
    }

    public void setUserInfoStatus(int userInfoStatus) {
        this.userInfoStatus = userInfoStatus;
    }

    public int getContactStatus() {
        return contactStatus;
    }

    public void setContactStatus(int contactStatus) {
        this.contactStatus = contactStatus;
    }

    public int getBankCardStatus() {
        return bankCardStatus;
    }

    public void setBankCardStatus(int bankCardStatus) {
        this.bankCardStatus = bankCardStatus;
    }

    public int getLiveAuthenticationStatus() {
        return liveAuthenticationStatus;
    }

    public void setLiveAuthenticationStatus(int liveAuthenticationStatus) {
        this.liveAuthenticationStatus = liveAuthenticationStatus;
    }

    public int getSesameCreditStatus() {
        return sesameCreditStatus;
    }

    public void setSesameCreditStatus(int sesameCreditStatus) {
        this.sesameCreditStatus = sesameCreditStatus;
    }

    public int getPayPasswordStatus() {
        return payPasswordStatus;
    }

    public void setPayPasswordStatus(int payPasswordStatus) {
        this.payPasswordStatus = payPasswordStatus;
    }

    public int getOperatorStatus() {
        return operatorStatus;
    }

    public void setOperatorStatus(int operatorStatus) {
        this.operatorStatus = operatorStatus;
    }

    public int getVerifyingCount() {
        return verifyingCount;
    }

    public void setVerifyingCount(int verifyingCount) {
        this.verifyingCount = verifyingCount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getXinyanZntzaStatus() {
        return xinyanZntzaStatus;
    }

    public void setXinyanZntzaStatus(int xinyanZntzaStatus) {
        this.xinyanZntzaStatus = xinyanZntzaStatus;
    }

    public int getXinyanCjldStatus() {
        return xinyanCjldStatus;
    }

    public void setXinyanCjldStatus(int xinyanCjldStatus) {
        this.xinyanCjldStatus = xinyanCjldStatus;
    }
}
