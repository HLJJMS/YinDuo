package com.yinduowang.installment.mvp.model.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Tsing
 * on 2019/3/7
 */
public class PaymentHistoryDataBean implements Serializable {

    private List<ListBean> paymentRecordlist ;

    public List<ListBean> getList() {
        return paymentRecordlist ;
    }

    public void setList(List<ListBean> list) {
        this.paymentRecordlist  = list;
    }

    public static class ListBean {

        /**
         * repaymentDate (string, optional): 还款时间 ,
         * repaymentFund (string, optional): 还款总额 ,
         * repaymentType (string, optional): 还款类型
         */
        private String recordId;
        private String repaymentDate;
        private String repaymentFund;
        private String repaymentType;

        public String getRecordId() {
            return recordId;
        }

        public void setRecordId(String recordId) {
            this.recordId = recordId;
        }

        public String getRepaymentDate() {
            return repaymentDate;
        }

        public void setRepaymentDate(String repaymentDate) {
            this.repaymentDate = repaymentDate;
        }

        public String getRepaymentFund() {
            return repaymentFund;
        }

        public void setRepaymentFund(String repaymentFund) {
            this.repaymentFund = repaymentFund;
        }

        public String getRepaymentType() {
            return repaymentType;
        }

        public void setRepaymentType(String repaymentType) {
            this.repaymentType = repaymentType;
        }
    }
}
