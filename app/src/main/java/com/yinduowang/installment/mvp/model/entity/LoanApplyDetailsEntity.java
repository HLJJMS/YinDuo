package com.yinduowang.installment.mvp.model.entity;

import java.io.Serializable;

public class LoanApplyDetailsEntity implements Serializable {

    /**
     * loanAmount : 100
     * loanDays : 7(天)
     * loanRateAmount : 1000.00
     * serviceAmount : 2000.00
     * returnAmount : 100.00
     * shouldReturnAmount : 100
     * returnDate : null
     * returnBank : 中国银行(尾号9325)
     * loanConfigId : 1
     * serviceAmountDetails : {"platformServiceChargeAmount":"500.00","informationServiceChargeAmount":"500.00","passServiceChargeAmount":"500.00","accountServiceChargeAmount":"500.00"}
     */

    private String loanAmount;
    private String loanDays;
    private String loanRateAmount;
    private String serviceAmount;
    private String returnAmount;
    private String shouldReturnAmount;
    private String returnDate="";
    private String returnBank;
    private String loanConfigId;
    private String serviceAmountMessage;
    private ServiceAmountDetailsBean serviceAmountDetails;


    public String getServiceAmountMessage() {
        return serviceAmountMessage;
    }

    public void setServiceAmountMessage(String serviceAmountMessage) {
        this.serviceAmountMessage = serviceAmountMessage;
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

    public String getShouldReturnAmount() {
        return shouldReturnAmount;
    }

    public void setShouldReturnAmount(String shouldReturnAmount) {
        this.shouldReturnAmount = shouldReturnAmount;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public String getReturnBank() {
        return returnBank;
    }

    public void setReturnBank(String returnBank) {
        this.returnBank = returnBank;
    }

    public String getLoanConfigId() {
        return loanConfigId;
    }

    public void setLoanConfigId(String loanConfigId) {
        this.loanConfigId = loanConfigId;
    }

    public ServiceAmountDetailsBean getServiceAmountDetails() {
        return serviceAmountDetails;
    }

    public void setServiceAmountDetails(ServiceAmountDetailsBean serviceAmountDetails) {
        this.serviceAmountDetails = serviceAmountDetails;
    }

    public static class ServiceAmountDetailsBean {
        /**
         * platformServiceChargeAmount : 500.00
         * informationServiceChargeAmount : 500.00
         * passServiceChargeAmount : 500.00
         * accountServiceChargeAmount : 500.00
         */

        private String platformServiceChargeAmount;
        private String informationServiceChargeAmount;
        private String passServiceChargeAmount;
        private String accountServiceChargeAmount;

        public String getPlatformServiceChargeAmount() {
            return platformServiceChargeAmount;
        }

        public void setPlatformServiceChargeAmount(String platformServiceChargeAmount) {
            this.platformServiceChargeAmount = platformServiceChargeAmount;
        }

        public String getInformationServiceChargeAmount() {
            return informationServiceChargeAmount;
        }

        public void setInformationServiceChargeAmount(String informationServiceChargeAmount) {
            this.informationServiceChargeAmount = informationServiceChargeAmount;
        }

        public String getPassServiceChargeAmount() {
            return passServiceChargeAmount;
        }

        public void setPassServiceChargeAmount(String passServiceChargeAmount) {
            this.passServiceChargeAmount = passServiceChargeAmount;
        }

        public String getAccountServiceChargeAmount() {
            return accountServiceChargeAmount;
        }

        public void setAccountServiceChargeAmount(String accountServiceChargeAmount) {
            this.accountServiceChargeAmount = accountServiceChargeAmount;
        }
    }
}
