package com.yinduowang.installment.mvp.model.entity;

import java.io.Serializable;

public class MineInfoEvent implements Serializable {
    /**
     * nickname : 13900000021
     * bannkInfo :
     * bannkName :
     * cardNo :
     * amount :
     * status :
     */

    private String nickname;
    private String bannkInfo;
    private String bannkName;
    private String cardNo;
    private String amount;
    private String status;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getBannkInfo() {
        return bannkInfo;
    }

    public void setBannkInfo(String bannkInfo) {
        this.bannkInfo = bannkInfo;
    }

    public String getBannkName() {
        return bannkName;
    }

    public void setBannkName(String bannkName) {
        this.bannkName = bannkName;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
