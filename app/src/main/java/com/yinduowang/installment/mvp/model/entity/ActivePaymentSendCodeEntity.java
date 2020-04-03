package com.yinduowang.installment.mvp.model.entity;

public class ActivePaymentSendCodeEntity {

    /**
     * status : -1
     * uniqueCode :
     * message : 认证信息不匹配
     */

    private String channelType;//还款渠道
    private String loanId;//借款表id
    private String message;// 提示信息
    private String repayFund;//还款金额
    private String status;//状态 -1发送失败 0发送成功
    private String uniqueCode;//预支付唯一码

    public String getChannelType() {
        return channelType;
    }

    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRepayFund() {
        return repayFund;
    }

    public void setRepayFund(String repayFund) {
        this.repayFund = repayFund;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUniqueCode() {
        return uniqueCode;
    }

    public void setUniqueCode(String uniqueCode) {
        this.uniqueCode = uniqueCode;
    }
}