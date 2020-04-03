package com.yinduowang.installment.mvp.model.entity;

public class CarRepayButtonBean {


    /**
     * status : null
     * message : null
     * loanId : null
     * repayFund : null
     */

    private String status;
    private String message;
    private String loanId;
    private String repayFund;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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

    public String getRepayFund() {
        return repayFund;
    }

    public void setRepayFund(String repayFund) {
        this.repayFund = repayFund;
    }
}
