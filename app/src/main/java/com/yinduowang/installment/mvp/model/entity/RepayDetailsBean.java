package com.yinduowang.installment.mvp.model.entity;

public class RepayDetailsBean {


    /**
     * againStatus : 1
     * buttonMessage : 1天后再尝试
     * canRepay : 0
     * doneFund : 10.00元
     * interest : 5.30元
     * isOverdue : 0
     * loanId : 125
     * message : 没有待还款账单
     * overdueDays : 10
     * overdueFund : 45.00元
     * principal : 50.00元
     * repayDate : 03/07
     * repayFund : 100.00
     * status : 4
     * totalNeedRepay : 3090.00元
     */

    private String againStatus; // 是否可再借 -1永不可借 0可借 1暂不可借 ,
    private String buttonMessage;// 按钮上的提示信息 ,
    private String canRepay;// 是否可以还款 0-不可以 1-可以 ,
    private String doneFund;// 已还金额 ,
    private String interest;// 利息 ,
    private String isOverdue;// 是否逾期 0-未逾期 1-已逾期 ,
    private int loanId;// 借款记录id ,
    private String[] message;// 提示信息 ,
    private int overdueDays;// 逾期天数 ,
    private String overdueFund;// 逾期金额 ,
    private String principal;// 本金 ,
    private String repayDate;// 还款日期 ,
    private String repayFund;// 待还金额 ,
    private String status;// 借款状态 0-未认证 1-无待还 2-借款审核中 3-借款未通过 4-有待还记录 ,
    private String totalNeedRepay;// 借款待还总金额

    public String getAgainStatus() {
        return againStatus;
    }

    public void setAgainStatus(String againStatus) {
        this.againStatus = againStatus;
    }

    public String getButtonMessage() {
        return buttonMessage;
    }

    public void setButtonMessage(String buttonMessage) {
        this.buttonMessage = buttonMessage;
    }

    public String getCanRepay() {
        return canRepay;
    }

    public void setCanRepay(String canRepay) {
        this.canRepay = canRepay;
    }

    public String getDoneFund() {
        return doneFund;
    }

    public void setDoneFund(String doneFund) {
        this.doneFund = doneFund;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public String getIsOverdue() {
        return isOverdue;
    }

    public void setIsOverdue(String isOverdue) {
        this.isOverdue = isOverdue;
    }

    public int getLoanId() {
        return loanId;
    }

    public void setLoanId(int loanId) {
        this.loanId = loanId;
    }

    public String[] getMessage() {
        return message;
    }

    public void setMessage(String[] message) {
        this.message = message;
    }

    public int getOverdueDays() {
        return overdueDays;
    }

    public void setOverdueDays(int overdueDays) {
        this.overdueDays = overdueDays;
    }

    public String getOverdueFund() {
        return overdueFund;
    }

    public void setOverdueFund(String overdueFund) {
        this.overdueFund = overdueFund;
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public String getRepayDate() {
        return repayDate;
    }

    public void setRepayDate(String repayDate) {
        this.repayDate = repayDate;
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

    public String getTotalNeedRepay() {
        return totalNeedRepay;
    }

    public void setTotalNeedRepay(String totalNeedRepay) {
        this.totalNeedRepay = totalNeedRepay;
    }
}
