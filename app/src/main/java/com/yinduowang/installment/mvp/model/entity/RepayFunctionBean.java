package com.yinduowang.installment.mvp.model.entity;

import java.util.List;

public class RepayFunctionBean {


    /**
     * alipayUrl : www.123.com
     * bankName : 建设银行
     * cardSuffix : 1234
     * loanId : 12
     * message : 备注1.部分银行卡
     * repayFund : 1200
     */

    private String alipayUrl;
    private String bankName;
    private String cardSuffix;
    private int loanId;
    private List<String> message;
    private double repayFund;
    private String totalNeedRepayFund;// (string, optional): 借款待还总额
    private String currentNeedRepayFund ;//(string, optional): 本期待还金额 ,
    private String canAlter;// (string, optional): 还款金额是否可修改 0-不可修改 1-可修改 ,
    private String toastMessage;
    private String minRepayFund;

    public String getMinRepayFund() {
        return minRepayFund;
    }

    public void setMinRepayFund(String minRepayFund) {
        this.minRepayFund = minRepayFund;
    }

    public String getToastMessage() {
        return toastMessage;
    }

    public void setToastMessage(String toastMessage) {
        this.toastMessage = toastMessage;
    }

    public String getCanAlter() {
        return canAlter;
    }

    public void setCanAlter(String canAlter) {
        this.canAlter = canAlter;
    }

    public String getCurrentNeedRepayFund() {
        return currentNeedRepayFund;
    }

    public void setCurrentNeedRepayFund(String currentNeedRepayFund) {
        this.currentNeedRepayFund = currentNeedRepayFund;
    }

    public String getTotalNeedRepayFund() {
        return totalNeedRepayFund;
    }

    public void setTotalNeedRepayFund(String totalNeedRepayFund) {
        this.totalNeedRepayFund = totalNeedRepayFund;
    }

    public String getAlipayUrl() {
        return alipayUrl;
    }

    public void setAlipayUrl(String alipayUrl) {
        this.alipayUrl = alipayUrl;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getCardSuffix() {
        return cardSuffix;
    }

    public void setCardSuffix(String cardSuffix) {
        this.cardSuffix = cardSuffix;
    }

    public int getLoanId() {
        return loanId;
    }

    public void setLoanId(int loanId) {
        this.loanId = loanId;
    }

    public List<String> getMessage() {
        return message;
    }

    public void setMessage(List<String> message) {
        this.message = message;
    }

    public double getRepayFund() {
        return repayFund;
    }

    public void setRepayFund(int repayFund) {
        this.repayFund = repayFund;
    }
}
