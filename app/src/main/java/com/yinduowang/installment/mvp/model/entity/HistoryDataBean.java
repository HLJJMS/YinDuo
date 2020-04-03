package com.yinduowang.installment.mvp.model.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Tsing
 * on 2019/3/7
 */
public class HistoryDataBean implements Serializable {

    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * id : 2
         * loanConfigId : 1
         * auditFlowId : 1
         * userId : 52
         * loanNumber : null
         * loanType : 1
         * loanFund : 1503.0
         * repaymentFund : 0.0
         * loanDays : 7
         * cycleType : 天
         * applicationTime : 1552320000
         * applicationDealTime : 1552390740
         * repaymentTime : null
         * repaymentPeriods : null
         * overdueDays : null
         * demurrage : 0.0
         * loanRate : 0.35
         * loanStringerest : 0.35
         * prepaymentServiceCharge : 0.0
         * repaymentStringerest : 0.0
         * platformServiceChargeRate : 9.0
         * platformServiceCharge : 135.27
         * informationServiceChargeRate : 9.3
         * informationServiceCharge : 139.77
         * passServiceChargeRate : 5.4
         * passServiceCharge : 81.16
         * accountServiceChargeRate : 6.3
         * accountServiceCharge : 94.68
         * serviceCharge : 450.88
         * repaymentServiceCharge : 0.0
         * deductionFund : 0.0
         * receivedFund : 1503.0
         * receivedCardNo : 6217000003120000001
         * isDelay : 1
         * delayDays : 0
         * dealStatus : 3
         * created : 1552390740
         * updated : null
         * totalFundPayable : 1959.14
         * overdueRate : 5.0
         * loanMode : 1
         * repaymentType : 1
         * deductStatus : 0
         * deductCount : 0
         * isDeduction : 0
         * failureDesc : null
         * beheadFund : null
         */

        private int id;
        private String loanConfigId;
        private String auditFlowId;
        private String userId;
        private String loanNumber;
        private String loanType;
        private String loanFund;
        private String repaymentFund;
        private String loanDays;
        private String cycleType;
        private String applicationTime;
        private String applicationDealTime;
        private String repaymentTime;
        private String repaymentPeriods;
        private String overdueDays;
        private String demurrage;
        private String loanRate;
        private String loanStringerest;
        private String prepaymentServiceCharge;
        private String repaymentStringerest;
        private String platformServiceChargeRate;
        private String platformServiceCharge;
        private String informationServiceChargeRate;
        private String informationServiceCharge;
        private String passServiceChargeRate;
        private String passServiceCharge;
        private String accountServiceChargeRate;
        private String accountServiceCharge;
        private String serviceCharge;
        private String repaymentServiceCharge;
        private String deductionFund;
        private String receivedFund;
        private String receivedCardNo;
        private String isDelay;
        private String delayDays;
        private int dealStatus;
        private String created;
        private String updated;
        private String totalFundPayable;
        private String overdueRate;
        private String loanMode;
        private String repaymentType;
        private String deductStatus;
        private String deductCount;
        private String isDeduction;
        private String failureDesc;
        private String beheadFund;
        private String overDate;

        public String getOverDate() {
            return overDate;
        }

        public void setOverDate(String overDate) {
            this.overDate = overDate;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getLoanConfigId() {
            return loanConfigId;
        }

        public void setLoanConfigId(String loanConfigId) {
            this.loanConfigId = loanConfigId;
        }

        public String getAuditFlowId() {
            return auditFlowId;
        }

        public void setAuditFlowId(String auditFlowId) {
            this.auditFlowId = auditFlowId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getLoanNumber() {
            return loanNumber;
        }

        public void setLoanNumber(String loanNumber) {
            this.loanNumber = loanNumber;
        }

        public String getLoanType() {
            return loanType;
        }

        public void setLoanType(String loanType) {
            this.loanType = loanType;
        }

        public String getLoanFund() {
            return loanFund;
        }

        public void setLoanFund(String loanFund) {
            this.loanFund = loanFund;
        }

        public String getRepaymentFund() {
            return repaymentFund;
        }

        public void setRepaymentFund(String repaymentFund) {
            this.repaymentFund = repaymentFund;
        }

        public String getLoanDays() {
            return loanDays;
        }

        public void setLoanDays(String loanDays) {
            this.loanDays = loanDays;
        }

        public String getCycleType() {
            return cycleType;
        }

        public void setCycleType(String cycleType) {
            this.cycleType = cycleType;
        }

        public String getApplicationTime() {
            return applicationTime;
        }

        public void setApplicationTime(String applicationTime) {
            this.applicationTime = applicationTime;
        }

        public String getApplicationDealTime() {
            return applicationDealTime;
        }

        public void setApplicationDealTime(String applicationDealTime) {
            this.applicationDealTime = applicationDealTime;
        }

        public String getRepaymentTime() {
            return repaymentTime;
        }

        public void setRepaymentTime(String repaymentTime) {
            this.repaymentTime = repaymentTime;
        }

        public String getRepaymentPeriods() {
            return repaymentPeriods;
        }

        public void setRepaymentPeriods(String repaymentPeriods) {
            this.repaymentPeriods = repaymentPeriods;
        }

        public String getOverdueDays() {
            return overdueDays;
        }

        public void setOverdueDays(String overdueDays) {
            this.overdueDays = overdueDays;
        }

        public String getDemurrage() {
            return demurrage;
        }

        public void setDemurrage(String demurrage) {
            this.demurrage = demurrage;
        }

        public String getLoanRate() {
            return loanRate;
        }

        public void setLoanRate(String loanRate) {
            this.loanRate = loanRate;
        }

        public String getLoanStringerest() {
            return loanStringerest;
        }

        public void setLoanStringerest(String loanStringerest) {
            this.loanStringerest = loanStringerest;
        }

        public String getPrepaymentServiceCharge() {
            return prepaymentServiceCharge;
        }

        public void setPrepaymentServiceCharge(String prepaymentServiceCharge) {
            this.prepaymentServiceCharge = prepaymentServiceCharge;
        }

        public String getRepaymentStringerest() {
            return repaymentStringerest;
        }

        public void setRepaymentStringerest(String repaymentStringerest) {
            this.repaymentStringerest = repaymentStringerest;
        }

        public String getPlatformServiceChargeRate() {
            return platformServiceChargeRate;
        }

        public void setPlatformServiceChargeRate(String platformServiceChargeRate) {
            this.platformServiceChargeRate = platformServiceChargeRate;
        }

        public String getPlatformServiceCharge() {
            return platformServiceCharge;
        }

        public void setPlatformServiceCharge(String platformServiceCharge) {
            this.platformServiceCharge = platformServiceCharge;
        }

        public String getInformationServiceChargeRate() {
            return informationServiceChargeRate;
        }

        public void setInformationServiceChargeRate(String informationServiceChargeRate) {
            this.informationServiceChargeRate = informationServiceChargeRate;
        }

        public String getInformationServiceCharge() {
            return informationServiceCharge;
        }

        public void setInformationServiceCharge(String informationServiceCharge) {
            this.informationServiceCharge = informationServiceCharge;
        }

        public String getPassServiceChargeRate() {
            return passServiceChargeRate;
        }

        public void setPassServiceChargeRate(String passServiceChargeRate) {
            this.passServiceChargeRate = passServiceChargeRate;
        }

        public String getPassServiceCharge() {
            return passServiceCharge;
        }

        public void setPassServiceCharge(String passServiceCharge) {
            this.passServiceCharge = passServiceCharge;
        }

        public String getAccountServiceChargeRate() {
            return accountServiceChargeRate;
        }

        public void setAccountServiceChargeRate(String accountServiceChargeRate) {
            this.accountServiceChargeRate = accountServiceChargeRate;
        }

        public String getAccountServiceCharge() {
            return accountServiceCharge;
        }

        public void setAccountServiceCharge(String accountServiceCharge) {
            this.accountServiceCharge = accountServiceCharge;
        }

        public String getServiceCharge() {
            return serviceCharge;
        }

        public void setServiceCharge(String serviceCharge) {
            this.serviceCharge = serviceCharge;
        }

        public String getRepaymentServiceCharge() {
            return repaymentServiceCharge;
        }

        public void setRepaymentServiceCharge(String repaymentServiceCharge) {
            this.repaymentServiceCharge = repaymentServiceCharge;
        }

        public String getDeductionFund() {
            return deductionFund;
        }

        public void setDeductionFund(String deductionFund) {
            this.deductionFund = deductionFund;
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

        public String getIsDelay() {
            return isDelay;
        }

        public void setIsDelay(String isDelay) {
            this.isDelay = isDelay;
        }

        public String getDelayDays() {
            return delayDays;
        }

        public void setDelayDays(String delayDays) {
            this.delayDays = delayDays;
        }

        public int getDealStatus() {
            return dealStatus;
        }

        public void setDealStatus(int dealStatus) {
            this.dealStatus = dealStatus;
        }

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }

        public String getUpdated() {
            return updated;
        }

        public void setUpdated(String updated) {
            this.updated = updated;
        }

        public String getTotalFundPayable() {
            return totalFundPayable;
        }

        public void setTotalFundPayable(String totalFundPayable) {
            this.totalFundPayable = totalFundPayable;
        }

        public String getOverdueRate() {
            return overdueRate;
        }

        public void setOverdueRate(String overdueRate) {
            this.overdueRate = overdueRate;
        }

        public String getLoanMode() {
            return loanMode;
        }

        public void setLoanMode(String loanMode) {
            this.loanMode = loanMode;
        }

        public String getRepaymentType() {
            return repaymentType;
        }

        public void setRepaymentType(String repaymentType) {
            this.repaymentType = repaymentType;
        }

        public String getDeductStatus() {
            return deductStatus;
        }

        public void setDeductStatus(String deductStatus) {
            this.deductStatus = deductStatus;
        }

        public String getDeductCount() {
            return deductCount;
        }

        public void setDeductCount(String deductCount) {
            this.deductCount = deductCount;
        }

        public String getIsDeduction() {
            return isDeduction;
        }

        public void setIsDeduction(String isDeduction) {
            this.isDeduction = isDeduction;
        }

        public String getFailureDesc() {
            return failureDesc;
        }

        public void setFailureDesc(String failureDesc) {
            this.failureDesc = failureDesc;
        }

        public String getBeheadFund() {
            return beheadFund;
        }

        public void setBeheadFund(String beheadFund) {
            this.beheadFund = beheadFund;
        }
    }
}
