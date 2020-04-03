package com.yinduowang.installment.mvp.model.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RepayBean {


    /**
     * status : 1
     * message : ["您目前没有待还款的账单哦~"]
     * loanId : null
     * isOverdue : null
     * repayFund : null
     * repayDate : null
     * principal : null
     * deductionFund : null
     * doneFund : null
     * overdueDays : null
     * overdueFund : null
     * canRepay : null
     */

    private String againStatus="";// 是否可再借 -1永不可借 0可借 1暂不可借 ,
    private String buttonMessage="";//(string, optional): 按钮上的提示信息（目前只有审核失败且暂不可借时有值） ,
    private int status;
    private String auditLoanId="";
    private String isOverdue="";
    private String repayFund="";
    private String repayDate="";
    private String principal="";
    private String deductionFund;
    private String doneFund;
    private String overdueDays;
    private String overdueFund;
    private String canRepay;
    private List<String> message;
    private List<LoansBean> loans;
    private String totalCredit;// (string, optional): 用户总额度 ,
    private String usedCredit ;//(string, optional): 用户已用额度
    private String maxCredit;// (string, optional): 最高可借 ,

    public String getMaxCredit() {
        return maxCredit;
    }

    public void setMaxCredit(String maxCredit) {
        this.maxCredit = maxCredit;
    }

    public String getTotalCredit() {
        return totalCredit;
    }

    public void setTotalCredit(String totalCredit) {
        this.totalCredit = totalCredit;
    }

    public String getUsedCredit() {
        return usedCredit;
    }

    public void setUsedCredit(String usedCredit) {
        this.usedCredit = usedCredit;
    }

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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getLoanId() {
        return auditLoanId;
    }

    public void setLoanId(String loanId) {
        this.auditLoanId = loanId;
    }

    public String getIsOverdue() {
        return isOverdue;
    }

    public void setIsOverdue(String isOverdue) {
        this.isOverdue = isOverdue;
    }

    public String getRepayFund() {
        return repayFund;
    }

    public void setRepayFund(String repayFund) {
        this.repayFund = repayFund;
    }

    public String getRepayDate() {
        return repayDate;
    }

    public void setRepayDate(String repayDate) {
        this.repayDate = repayDate;
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public String getDeductionFund() {
        return deductionFund;
    }

    public void setDeductionFund(String deductionFund) {
        this.deductionFund = deductionFund;
    }

    public String getDoneFund() {
        return doneFund;
    }

    public void setDoneFund(String doneFund) {
        this.doneFund = doneFund;
    }

    public String getOverdueDays() {
        return overdueDays;
    }

    public void setOverdueDays(String overdueDays) {
        this.overdueDays = overdueDays;
    }

    public String getOverdueFund() {
        return overdueFund;
    }

    public void setOverdueFund(String overdueFund) {
        this.overdueFund = overdueFund;
    }

    public String getCanRepay() {
        return canRepay;
    }

    public void setCanRepay(String canRepay) {
        this.canRepay = canRepay;
    }

    public List<String> getMessage() {
        return message;
    }

    public void setMessage(List<String> message) {
        this.message = message;
    }

    public List<LoansBean> getLoans() {
        return loans;
    }

    public void setLoans(List<LoansBean> loans) {
        this.loans = loans;
    }

    public static class LoansBean {
        /**
         * status : 2
         * loanId : 122
         * repayDate : 2019年4月6日
         * repayFund : 1000.00
         */

        @SerializedName("status")
        private String statusX;
        @SerializedName("loanId")
        private String loanIdX;
        @SerializedName("repayDate")
        private String repayDateX;
        @SerializedName("repayFund")
        private String repayFundX;

        public String getStatusX() {
            return statusX;
        }

        public void setStatusX(String statusX) {
            this.statusX = statusX;
        }

        public String getLoanIdX() {
            return loanIdX;
        }

        public void setLoanIdX(String loanIdX) {
            this.loanIdX = loanIdX;
        }

        public String getRepayDateX() {
            return repayDateX;
        }

        public void setRepayDateX(String repayDateX) {
            this.repayDateX = repayDateX;
        }

        public String getRepayFundX() {
            return repayFundX;
        }

        public void setRepayFundX(String repayFundX) {
            this.repayFundX = repayFundX;
        }
    }
}
