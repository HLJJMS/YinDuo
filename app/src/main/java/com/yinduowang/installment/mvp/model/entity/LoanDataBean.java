package com.yinduowang.installment.mvp.model.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Tsing
 * on 2019/3/7
 */
public class LoanDataBean implements Serializable {


    /**
     * loan : {"id":3,"loanConfigId":1,"auditFlowId":null,"userId":40,"loanNumber":null,"loanType":1,"loanFund":100,"repaymentFund":0,"loanDays":7,"cycleType":"天","applicationTime":1551937127,"applicationDealTime":1551937127,"repaymentTime":null,"repaymentPeriods":null,"overdueDays":null,"demurrage":0,"loanRate":10,"loanInterest":10,"prepaymentServiceCharge":0,"repaymentInterest":0,"platformServiceChargeRate":0,"platformServiceCharge":0,"informationServiceChargeRate":0,"informationServiceCharge":0,"passServiceChargeRate":0,"passServiceCharge":0,"accountServiceChargeRate":0,"accountServiceCharge":0,"serviceCharge":0,"repaymentServiceCharge":0,"deductionFund":0,"receivedFund":0,"receivedCardNo":null,"isDelay":0,"delayDays":null,"dealStatus":1,"created":null,"updated":null,"totalFundPayable":null,"overdueRate":null,"loanMode":0,"repaymentType":0,"deductStatus":0,"deductCount":0}
     * planMap : {"repaymentFund":"200.00","successTime":"","paidFund":"-100.00","repaymentTime":"2019-03-07"}
     * statusList : [{"msg":"申请借款100.00,期限7天,利息10.00元,服务费0.00元","time":"2019-03-07 13:38","title":"申请提交成功"},{"msg":"已进入风控审核状态,请您耐心等待","time":"当前","title":"审核中"}]
     */
    private String currentState;
    private LoanBean loan;
    private List<PlanMapBean> planMap;
    private String canRepay;
    private String stage;
    private List<StatusListBean> statusList;

    public String getCurrentState() {
        return currentState;
    }

    public void setCurrentState(String currentState) {
        this.currentState = currentState;
    }

    public LoanBean getLoan() {
        return loan;
    }

    public void setLoan(LoanBean loan) {
        this.loan = loan;
    }

    public List<PlanMapBean> getPlanMap() {
        return planMap;
    }

    public void setPlanMap(List<PlanMapBean> planMap) {
        this.planMap = planMap;
    }

    public String getCanRepay() {
        return canRepay;
    }

    public void setCanRepay(String canRepay) {
        this.canRepay = canRepay;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public List<StatusListBean> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<StatusListBean> statusList) {
        this.statusList = statusList;
    }

    public static class LoanBean {

        /**
         * loanFund : 1500.00(元)
         * loanDays : 7(天)
         * applicationTime : 1970-01-19
         * loanInterest : 0.35(元)
         * serviceCharge : 450.00(元)
         * receivedFund : 1500.00(元)
         * receivedCardNo : 建设银行(尾号0092)
         */

        private String loanFund;
        private String loanDays;
        private String applicationTime;
        private String loanInterest;
        private String serviceCharge;
        private String receivedFund;
        private String receivedCardNo;
        private String id;
        private String dealStatus;
        private String repaymentFund;
        private String paidFund;

        public String getRepaymentFund() {
            return repaymentFund;
        }

        public void setRepaymentFund(String repaymentFund) {
            this.repaymentFund = repaymentFund;
        }

        public String getPaidFund() {
            return paidFund;
        }

        public void setPaidFund(String paidFund) {
            this.paidFund = paidFund;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDealstatus() {
            return dealStatus;
        }

        public void setDealstatus(String dealstatus) {
            this.dealStatus = dealstatus;
        }

        public String getLoanFund() {
            return loanFund;
        }

        public void setLoanFund(String loanFund) {
            this.loanFund = loanFund;
        }

        public String getLoanDays() {
            return loanDays;
        }

        public void setLoanDays(String loanDays) {
            this.loanDays = loanDays;
        }

        public String getApplicationTime() {
            return applicationTime;
        }

        public void setApplicationTime(String applicationTime) {
            this.applicationTime = applicationTime;
        }

        public String getLoanInterest() {
            return loanInterest;
        }

        public void setLoanInterest(String loanInterest) {
            this.loanInterest = loanInterest;
        }

        public String getServiceCharge() {
            return serviceCharge;
        }

        public void setServiceCharge(String serviceCharge) {
            this.serviceCharge = serviceCharge;
        }

        public String getReceivedFund() {
            return receivedFund;
        }

        public void setReceivedFund(String receivedFund) {
            this.receivedFund = receivedFund;
        }

        public String getReceivedCardNo() {
            return receivedCardNo;
        }

        public void setReceivedCardNo(String receivedCardNo) {
            this.receivedCardNo = receivedCardNo;
        }
    }

    public static class PlanMapBean implements Serializable{
        /**
         * repaymentFund : 200.00
         * successTime :
         * paidFund : -100.00
         * repaymentTime : 2019-03-07
         */

        private String repaymentFund;
        private String principal;
        private String interest;
        private String demurrage;
        private String repaid_principal;
        private String repaid_interest;
        private String repaid_demurrage;
        private String cur_stage;
        private String status;
        private String repaymentState;
        private String repaymentTime;


        public String getRepaymentFund() {
            return repaymentFund;
        }

        public void setRepaymentFund(String repaymentFund) {
            this.repaymentFund = repaymentFund;
        }

        public String getPrincipal() {
            return principal;
        }

        public void setPrincipal(String principal) {
            this.principal = principal;
        }

        public String getInterest() {
            return interest;
        }

        public void setInterest(String interest) {
            this.interest = interest;
        }

        public String getDemurrage() {
            return demurrage;
        }

        public void setDemurrage(String demurrage) {
            this.demurrage = demurrage;
        }

        public String getRepaid_principal() {
            return repaid_principal;
        }

        public void setRepaid_principal(String repaid_principal) {
            this.repaid_principal = repaid_principal;
        }

        public String getRepaid_interest() {
            return repaid_interest;
        }

        public void setRepaid_interest(String repaid_interest) {
            this.repaid_interest = repaid_interest;
        }

        public String getRepaid_demurrage() {
            return repaid_demurrage;
        }

        public void setRepaid_demurrage(String repaid_demurrage) {
            this.repaid_demurrage = repaid_demurrage;
        }

        public String getCur_stage() {
            return cur_stage;
        }

        public void setCur_stage(String cur_stage) {
            this.cur_stage = cur_stage;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getRepaymentState() {
            return repaymentState;
        }

        public void setRepaymentState(String repaymentState) {
            this.repaymentState = repaymentState;
        }

        public String getRepaymentTime() {
            return repaymentTime;
        }

        public void setRepaymentTime(String repaymentTime) {
            this.repaymentTime = repaymentTime;
        }
    }

    public static class StatusListBean {
        /**
         * msg : 申请借款100.00,期限7天,利息10.00元,服务费0.00元
         * time : 2019-03-07 13:38
         * title : 申请提交成功
         */

        private String msg;
        private String time;
        private String title;

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
