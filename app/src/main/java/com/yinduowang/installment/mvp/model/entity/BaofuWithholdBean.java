package com.yinduowang.installment.mvp.model.entity;

public class BaofuWithholdBean {


    /**
     * bankName : 建设银行
     * bankIdNo : CCB
     * cardNo : 6217000003120000001
     * mobile : 17300031201
     * remark : 备注
     * 1.借款通过申请后，我们会将您的所有借款项发放到该张银行卡；
     * 2.若申请重新绑卡，则新卡将被激活为收款银行卡；
     * 3.未完成借款期间不允许更换银行卡；
     * realName : 何建东
     * idCard : 110101199003076640
     * message : 您有一笔借款未结清,暂不能更换银行卡
     * status : 1
     */

    private String bankName;
    private String bankIdNo;
    private String cardNo;
    private String mobile;
    private String remark;
    private String realName;
    private String idCard;
    private String message;
    private String status;//0 可更换银行卡 1 不能更换
    private String bindType;//绑定类型  1 单渠道  2 双渠道  (代扣授权 顶部view  显示（2）隐藏（1）)
    private String bindSchedule;//绑定进度 0-未绑定 1-已绑定一种渠道 2-已绑定两种渠道
    private String[] channelInfo;
    private String bindingCardNo;

    public String getBindingCardNo() {
        return bindingCardNo;
    }

    public void setBindingCardNo(String bindingCardNo) {
        this.bindingCardNo = bindingCardNo;
    }

    public String[] getChannelInfo() {
        return channelInfo;
    }

    public void setChannelInfo(String[] channelInfo) {
        this.channelInfo = channelInfo;
    }

    public String getBindType() {
        return bindType;
    }

    public void setBindType(String bindType) {
        this.bindType = bindType;
    }

    public String getBindSchedule() {
        return bindSchedule;
    }

    public void setBindSchedule(String bindSchedule) {
        this.bindSchedule = bindSchedule;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankIdNo() {
        return bankIdNo;
    }

    public void setBankIdNo(String bankIdNo) {
        this.bankIdNo = bankIdNo;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
