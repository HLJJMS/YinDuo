package com.yinduowang.installment.mvp.model.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Tsing
 * on 2019/1/16
 */
public class RepayItemBean implements Serializable {


    /**
     * item : {"list":[],"banner":{"button":2,"status":0,"can_loan_time":-2,"is_show":0},"repayment_url":"","detail_url":"","count":0,"pay_title":"支持多种还款方式，方便快捷","pay_type":[{"isAuthentication":1,"title":"主动还款(银行卡)","img_url":"http://47.104.232.195:21111/credit/web/image/card/union_pay.png","link_url":"http://47.104.232.195:21111/credit/web/credit-web/repayment-process"},{"isAuthentication":2,"title":"到期自动扣款(银行卡)","img_url":"http://47.104.232.195:21111/credit/web/image/card/union_pay.png","link_url":"http://47.104.232.195:21111/credit/web/credit-web/repayment-process"},{"isAuthentication":3,"title":"支付宝还款","img_url":"http://47.104.232.195:21111/credit/web/image/card/alipay_card_info.png","link_url":"http://47.104.232.195:21111/credit/web/credit-web/repayment-process"}]}
     */

    private ItemBean item;

    public ItemBean getItem() {
        return item;
    }

    public void setItem(ItemBean item) {
        this.item = item;
    }

    public static class ItemBean implements Serializable{
        /**
         * list : []
         * banner : {"button":2,"status":0,"can_loan_time":-2,"is_show":0}
         * repayment_url :
         * detail_url :
         * count : 0
         * pay_title : 支持多种还款方式，方便快捷
         * pay_type : [{"isAuthentication":1,"title":"主动还款(银行卡)","img_url":"http://47.104.232.195:21111/credit/web/image/card/union_pay.png","link_url":"http://47.104.232.195:21111/credit/web/credit-web/repayment-process"},{"isAuthentication":2,"title":"到期自动扣款(银行卡)","img_url":"http://47.104.232.195:21111/credit/web/image/card/union_pay.png","link_url":"http://47.104.232.195:21111/credit/web/credit-web/repayment-process"},{"isAuthentication":3,"title":"支付宝还款","img_url":"http://47.104.232.195:21111/credit/web/image/card/alipay_card_info.png","link_url":"http://47.104.232.195:21111/credit/web/credit-web/repayment-process"}]
         */



        private BannerBean banner;
        private String repayment_url;
        private String detail_url;
        private int count;
        private String pay_title;
        private List<RepaymentListBean> list;
        private List<PayTypeBean> pay_type;

        public BannerBean getBanner() {
            return banner;
        }

        public void setBanner(BannerBean banner) {
            this.banner = banner;
        }

        public String getRepayment_url() {
            return repayment_url;
        }

        public void setRepayment_url(String repayment_url) {
            this.repayment_url = repayment_url;
        }

        public String getDetail_url() {
            return detail_url;
        }

        public void setDetail_url(String detail_url) {
            this.detail_url = detail_url;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getPay_title() {
            return pay_title;
        }

        public void setPay_title(String pay_title) {
            this.pay_title = pay_title;
        }


        public List<RepaymentListBean> getList() {
            return list;
        }

        public void setList(List<RepaymentListBean> list) {
            this.list = list;
        }

        public List<PayTypeBean> getPay_type() {
            return pay_type;
        }

        public void setPay_type(List<PayTypeBean> pay_type) {
            this.pay_type = pay_type;
        }

        public static class BannerBean implements Serializable{
            /**
             * button : 2
             * status : 0
             * can_loan_time : -2
             * is_show : 0
             */

            private int button; //授信状态 1:授信完成 2:授信中
            private int status; //借款审核状态 1:审核中 2:驳回
            private String can_loan_time; //再次借款时间间隔 0
            private String export_title; //被拒情况下按钮文本
            private String export_url; //被拒情况下按钮链接
            private int is_show = 0; //1代表显示"更多推荐"，0代表不显示
            private String button_name= ""; //“更多推荐”文字内容
            private String button_url= ""; //“更多推荐”下载url

            public String getExport_title() {
                return export_title;
            }

            public void setExport_title(String export_title) {
                this.export_title = export_title;
            }

            public String getExport_url() {
                return export_url;
            }

            public void setExport_url(String export_url) {
                this.export_url = export_url;
            }

            public String getButton_name() {
                return button_name;
            }

            public void setButton_name(String button_name) {
                this.button_name = button_name;
            }

            public String getButton_url() {
                return button_url;
            }

            public void setButton_url(String button_url) {
                this.button_url = button_url;
            }

            public int getButton() {
                return button;
            }

            public void setButton(int button) {
                this.button = button;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getCan_loan_time() {
                return can_loan_time;
            }

            public void setCan_loan_time(String can_loan_time) {
                this.can_loan_time = can_loan_time;
            }

            public int getIs_show() {
                return is_show;
            }

            public void setIs_show(int is_show) {
                this.is_show = is_show;
            }
        }

        public static class PayTypeBean implements Serializable{
            /**
             * isAuthentication : 1
             * title : 主动还款(银行卡)
             * img_url : http://47.104.232.195:21111/credit/web/image/card/union_pay.png
             * link_url : http://47.104.232.195:21111/credit/web/credit-web/repayment-process
             */

            private int type;
            private String title;
            private String img_url;
            private String link_url;

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getImg_url() {
                return img_url;
            }

            public void setImg_url(String img_url) {
                this.img_url = img_url;
            }

            public String getLink_url() {
                return link_url;
            }

            public void setLink_url(String link_url) {
                this.link_url = link_url;
            }
        }

        public static class RepaymentListBean implements Serializable{

            private String debt;//实际欠款金额
            private String principal;//借款本金
            private String counter_fee;//服务费
            private String receipts;//实际到账金额
            private String interests;//利息
            private String late_fee;//逾期还款滞纳金
            private String plan_repayment_time;//应还日期
            private String coupon_fee;//抵扣金额
            private String true_total_money;//已还金额
            private String is_overdue;//是否逾期
            private String overdue_day;//逾期天数
            private String text_tip;//文本提示
            private String status;
            private String url;//跳转h5页面url地址(立即还款)

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getDebt() {
                return debt;
            }

            public void setDebt(String debt) {
                this.debt = debt;
            }

            public String getPrincipal() {
                return principal;
            }

            public void setPrincipal(String principal) {
                this.principal = principal;
            }

            public String getCounter_fee() {
                return counter_fee;
            }

            public void setCounter_fee(String counter_fee) {
                this.counter_fee = counter_fee;
            }

            public String getReceipts() {
                return receipts;
            }

            public void setReceipts(String receipts) {
                this.receipts = receipts;
            }

            public String getInterests() {
                return interests;
            }

            public void setInterests(String interests) {
                this.interests = interests;
            }

            public String getLate_fee() {
                return late_fee;
            }

            public void setLate_fee(String late_fee) {
                this.late_fee = late_fee;
            }

            public String getPlan_repayment_time() {
                return plan_repayment_time;
            }

            public void setPlan_repayment_time(String plan_repayment_time) {
                this.plan_repayment_time = plan_repayment_time;
            }

            public String getCoupon_fee() {
                return coupon_fee;
            }

            public void setCoupon_fee(String coupon_fee) {
                this.coupon_fee = coupon_fee;
            }

            public String getTrue_total_money() {
                return true_total_money;
            }

            public void setTrue_total_money(String true_total_money) {
                this.true_total_money = true_total_money;
            }

            public String getIs_overdue() {
                return is_overdue;
            }

            public void setIs_overdue(String is_overdue) {
                this.is_overdue = is_overdue;
            }

            public String getOverdue_day() {
                return overdue_day;
            }

            public void setOverdue_day(String overdue_day) {
                this.overdue_day = overdue_day;
            }

            public String getText_tip() {
                return text_tip;
            }

            public void setText_tip(String text_tip) {
                this.text_tip = text_tip;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }
    }
}
