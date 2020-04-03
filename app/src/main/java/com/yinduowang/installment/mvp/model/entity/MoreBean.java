package com.yinduowang.installment.mvp.model.entity;

import java.io.Serializable;
import java.util.List;

public class MoreBean implements Serializable {

    /**
     * item : {"invite_code":"IF_G3W","default_fund":0,"credit_info":{"card_title":"","card_subtitle":"CASH CARD","card_amount":240000,"card_no":"5178 2526 8837 1453","card_apr":0.65,"card_late_apr":4,"card_money_min":100000,"card_money_max":240000,"card_period_min":7,"card_period_max":30,"card_color":"#62c132","card_type":1,"card_used_amount":120000,"card_locked_amount":0,"card_unused_amount":120000},"card_info":{"card_id":"4","bank_id":"7","bank_name":"建设银行","card_no":"6217003200003003206","main_card":"1","ic_phone":"18219290272","card_no_end":"3206"},"card_url":"http://47.104.232.195:21111/credit/web/loan/card-list?clientType=wap","verify_info":{"authentication_total":5,"real_verify_status":1,"real_work_status":0,"real_contact_status":1,"real_bind_bank_card_status":1,"real_jxl_status":1,"real_zmxy_status":1,"real_alipay_status":0,"real_more_status":0,"verify_loan_pass":1,"real_pay_pwd_status":1,"real_taobao_status":0,"real_accredit_status":0,"real_verfy_base":5,"real_verfy_senior":0,"real_verfy_more":0,"real_online_bank_status":0,"real_accumulation_fund":0,"real_social_security":0,"authentication_pass":5},"ic_phone":"151****1111","share_title":"","share_body":"1分钟认证，20分钟到账，无抵押，纯信用贷。时下最流行的移动贷款APP。国内首批利用大数据、人工智能实现风控审批的信贷服务平台。","url":"http://47.104.232.195:21111/credit/web/credit-web/help-center","share_logo":"http://47.104.232.195:21111/credit/web/logo_share.png","share_url":"http://47.104.232.195:21111/frontend/web/act/light-loan?name=%2A%E6%98%86&invite_code=IF_G3W&source_tag=fenxiang","red_pack_total":0,"item_list":[{"title":"借款记录","subtitle":"","group":1,"tag":1,"logo":"http://47.104.232.195:21111/credit/web/image/tag/loan_record.png","url":"http://47.104.232.195:21111/credit/web/loan/card-list?clientType=wap"},{"title":"完善资料","subtitle":"","group":1,"tag":2,"logo":"http://47.104.232.195:21111/credit/web/image/tag/loan_perfect.png"},{"title":"收款银行卡","subtitle":"建设银行(3206)","group":1,"tag":3,"logo":"http://47.104.232.195:21111/credit/web/image/tag/loan_card.png","url":"http://47.104.232.195:21111/credit/web/loan/card-list?clientType=wap"},{"title":"帮助中心","subtitle":"","group":2,"tag":4,"url":"http://47.104.232.195:21111/credit/web/credit-web/help-center","logo":"http://47.104.232.195:21111/credit/web/image/tag/loan_help.png"},{"title":"消息中心","subtitle":"","group":2,"tag":6,"url":"http://47.104.232.195:21111/credit/web/credit-web/result-message?clientType=wap","logo":"http://47.104.232.195:21111/credit/web/image/tag/message.png"},{"title":"设置","subtitle":"","group":2,"tag":8,"logo":"http://47.104.232.195:21111/credit/web/image/tag/setting.png"}]}
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
         * invite_code : IF_G3W
         * default_fund : 0
         * credit_info : {"card_title":"","card_subtitle":"CASH CARD","card_amount":240000,"card_no":"5178 2526 8837 1453","card_apr":0.65,"card_late_apr":4,"card_money_min":100000,"card_money_max":240000,"card_period_min":7,"card_period_max":30,"card_color":"#62c132","card_type":1,"card_used_amount":120000,"card_locked_amount":0,"card_unused_amount":120000}
         * card_info : {"card_id":"4","bank_id":"7","bank_name":"建设银行","card_no":"6217003200003003206","main_card":"1","ic_phone":"18219290272","card_no_end":"3206"}
         * card_url : http://47.104.232.195:21111/credit/web/loan/card-list?clientType=wap
         * verify_info : {"authentication_total":5,"real_verify_status":1,"real_work_status":0,"real_contact_status":1,"real_bind_bank_card_status":1,"real_jxl_status":1,"real_zmxy_status":1,"real_alipay_status":0,"real_more_status":0,"verify_loan_pass":1,"real_pay_pwd_status":1,"real_taobao_status":0,"real_accredit_status":0,"real_verfy_base":5,"real_verfy_senior":0,"real_verfy_more":0,"real_online_bank_status":0,"real_accumulation_fund":0,"real_social_security":0,"authentication_pass":5}
         * ic_phone : 151****1111
         * share_title : 
         * share_body : 1分钟认证，20分钟到账，无抵押，纯信用贷。时下最流行的移动贷款APP。国内首批利用大数据、人工智能实现风控审批的信贷服务平台。
         * url : http://47.104.232.195:21111/credit/web/credit-web/help-center
         * share_logo : http://47.104.232.195:21111/credit/web/logo_share.png
         * share_url : http://47.104.232.195:21111/frontend/web/act/light-loan?name=%2A%E6%98%86&invite_code=IF_G3W&source_tag=fenxiang
         * red_pack_total : 0
         * item_list : [{"title":"借款记录","subtitle":"","group":1,"tag":1,"logo":"http://47.104.232.195:21111/credit/web/image/tag/loan_record.png"},{"title":"完善资料","subtitle":"","group":1,"tag":2,"logo":"http://47.104.232.195:21111/credit/web/image/tag/loan_perfect.png"},{"title":"收款银行卡","subtitle":"建设银行(3206)","group":1,"tag":3,"logo":"http://47.104.232.195:21111/credit/web/image/tag/loan_card.png","url":"http://47.104.232.195:21111/credit/web/loan/card-list?clientType=wap"},{"title":"帮助中心","subtitle":"","group":2,"tag":4,"url":"http://47.104.232.195:21111/credit/web/credit-web/help-center","logo":"http://47.104.232.195:21111/credit/web/image/tag/loan_help.png"},{"title":"消息中心","subtitle":"","group":2,"tag":6,"url":"http://47.104.232.195:21111/credit/web/credit-web/result-message?clientType=wap","logo":"http://47.104.232.195:21111/credit/web/image/tag/message.png"},{"title":"设置","subtitle":"","group":2,"tag":8,"logo":"http://47.104.232.195:21111/credit/web/image/tag/setting.png"}]
         */

        private String invite_code;//邀请码
        private int default_fund;
        private CreditInfoBean credit_info;
        private String card_url;//卡链接
        private VerifyInfoBean verify_info;
        private String phone;
        private String share_title;
        private String share_body;
        private String url;
        private String share_logo;
        private String share_url;
        private int red_pack_total;
        private List<ItemListBean> item_list;

        public String getInvite_code() {
            return invite_code;
        }

        public void setInvite_code(String invite_code) {
            this.invite_code = invite_code;
        }

        public int getDefault_fund() {
            return default_fund;
        }

        public void setDefault_fund(int default_fund) {
            this.default_fund = default_fund;
        }

        public CreditInfoBean getCredit_info() {
            return credit_info;
        }

        public void setCredit_info(CreditInfoBean credit_info) {
            this.credit_info = credit_info;
        }

        public String getCard_url() {
            return card_url;
        }

        public void setCard_url(String card_url) {
            this.card_url = card_url;
        }

        public VerifyInfoBean getVerify_info() {
            return verify_info;
        }

        public void setVerify_info(VerifyInfoBean verify_info) {
            this.verify_info = verify_info;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getShare_title() {
            return share_title;
        }

        public void setShare_title(String share_title) {
            this.share_title = share_title;
        }

        public String getShare_body() {
            return share_body;
        }

        public void setShare_body(String share_body) {
            this.share_body = share_body;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getShare_logo() {
            return share_logo;
        }

        public void setShare_logo(String share_logo) {
            this.share_logo = share_logo;
        }

        public String getShare_url() {
            return share_url;
        }

        public void setShare_url(String share_url) {
            this.share_url = share_url;
        }

        public int getRed_pack_total() {
            return red_pack_total;
        }

        public void setRed_pack_total(int red_pack_total) {
            this.red_pack_total = red_pack_total;
        }

        public List<ItemListBean> getItem_list() {
            return item_list;
        }

        public void setItem_list(List<ItemListBean> item_list) {
            this.item_list = item_list;
        }

        public static class CreditInfoBean implements Serializable{
            /**
             * card_title : 
             * card_subtitle : CASH CARD
             * card_amount : 240000
             * card_no : 5178 2526 8837 1453
             * card_apr : 0.65
             * card_late_apr : 4
             * card_money_min : 100000
             * card_money_max : 240000
             * card_period_min : 7
             * card_period_max : 30
             * card_color : #62c132
             * card_type : 1
             * card_used_amount : 120000
             * card_locked_amount : 0
             * card_unused_amount : 120000
             */

            private String card_title;
            private String card_subtitle;
            private String card_amount;
            private String card_no;
            private double card_apr;
            private int card_late_apr;
            private int card_money_min;
            private int card_money_max;
            private int card_period_min;
            private int card_period_max;
            private String card_color;
            private int card_type;
            private int card_used_amount;
            private int card_locked_amount;
            private int card_unused_amount;

            public String getCard_title() {
                return card_title;
            }

            public void setCard_title(String card_title) {
                this.card_title = card_title;
            }

            public String getCard_subtitle() {
                return card_subtitle;
            }

            public void setCard_subtitle(String card_subtitle) {
                this.card_subtitle = card_subtitle;
            }

            public String getCard_amount() {
                return card_amount;
            }

            public void setCard_amount(String card_amount) {
                this.card_amount = card_amount;
            }

            public String getCard_no() {
                return card_no;
            }

            public void setCard_no(String card_no) {
                this.card_no = card_no;
            }

            public double getCard_apr() {
                return card_apr;
            }

            public void setCard_apr(double card_apr) {
                this.card_apr = card_apr;
            }

            public int getCard_late_apr() {
                return card_late_apr;
            }

            public void setCard_late_apr(int card_late_apr) {
                this.card_late_apr = card_late_apr;
            }

            public int getCard_money_min() {
                return card_money_min;
            }

            public void setCard_money_min(int card_money_min) {
                this.card_money_min = card_money_min;
            }

            public int getCard_money_max() {
                return card_money_max;
            }

            public void setCard_money_max(int card_money_max) {
                this.card_money_max = card_money_max;
            }

            public int getCard_period_min() {
                return card_period_min;
            }

            public void setCard_period_min(int card_period_min) {
                this.card_period_min = card_period_min;
            }

            public int getCard_period_max() {
                return card_period_max;
            }

            public void setCard_period_max(int card_period_max) {
                this.card_period_max = card_period_max;
            }

            public String getCard_color() {
                return card_color;
            }

            public void setCard_color(String card_color) {
                this.card_color = card_color;
            }

            public int getCard_type() {
                return card_type;
            }

            public void setCard_type(int card_type) {
                this.card_type = card_type;
            }

            public int getCard_used_amount() {
                return card_used_amount;
            }

            public void setCard_used_amount(int card_used_amount) {
                this.card_used_amount = card_used_amount;
            }

            public int getCard_locked_amount() {
                return card_locked_amount;
            }

            public void setCard_locked_amount(int card_locked_amount) {
                this.card_locked_amount = card_locked_amount;
            }

            public int getCard_unused_amount() {
                return card_unused_amount;
            }

            public void setCard_unused_amount(int card_unused_amount) {
                this.card_unused_amount = card_unused_amount;
            }
        }



        public static class VerifyInfoBean implements Serializable{
            /**
             * authentication_total : 5
             * real_verify_status : 1
             * real_work_status : 0
             * real_contact_status : 1
             * real_bind_bank_card_status : 1
             * real_jxl_status : 1
             * real_zmxy_status : 1
             * real_alipay_status : 0
             * real_more_status : 0
             * verify_loan_pass : 1
             * real_pay_pwd_status : 1
             * real_taobao_status : 0
             * real_accredit_status : 0
             * real_verfy_base : 5
             * real_verfy_senior : 0
             * real_verfy_more : 0
             * real_online_bank_status : 0
             * real_accumulation_fund : 0
             * real_social_security : 0
             * authentication_pass : 5
             */

            private int authentication_total;
            private int real_verify_status;
            private int real_work_status;
            private int real_contact_status;
            private int real_bind_bank_card_status;
            private int real_jxl_status;
            private int real_zmxy_status;
            private int real_alipay_status;
            private int real_more_status;
            private int verify_loan_pass;
            private int real_pay_pwd_status;
            private int real_taobao_status;
            private int real_accredit_status;
            private int real_verfy_base;
            private int real_verfy_senior;
            private int real_verfy_more;
            private int real_online_bank_status;
            private int real_accumulation_fund;
            private int real_social_security;
            private int authentication_pass;

            public int getAuthentication_total() {
                return authentication_total;
            }

            public void setAuthentication_total(int authentication_total) {
                this.authentication_total = authentication_total;
            }

            public int getReal_verify_status() {
                return real_verify_status;
            }

            public void setReal_verify_status(int real_verify_status) {
                this.real_verify_status = real_verify_status;
            }

            public int getReal_work_status() {
                return real_work_status;
            }

            public void setReal_work_status(int real_work_status) {
                this.real_work_status = real_work_status;
            }

            public int getReal_contact_status() {
                return real_contact_status;
            }

            public void setReal_contact_status(int real_contact_status) {
                this.real_contact_status = real_contact_status;
            }

            public int getReal_bind_bank_card_status() {
                return real_bind_bank_card_status;
            }

            public void setReal_bind_bank_card_status(int real_bind_bank_card_status) {
                this.real_bind_bank_card_status = real_bind_bank_card_status;
            }

            public int getReal_jxl_status() {
                return real_jxl_status;
            }

            public void setReal_jxl_status(int real_jxl_status) {
                this.real_jxl_status = real_jxl_status;
            }

            public int getReal_zmxy_status() {
                return real_zmxy_status;
            }

            public void setReal_zmxy_status(int real_zmxy_status) {
                this.real_zmxy_status = real_zmxy_status;
            }

            public int getReal_alipay_status() {
                return real_alipay_status;
            }

            public void setReal_alipay_status(int real_alipay_status) {
                this.real_alipay_status = real_alipay_status;
            }

            public int getReal_more_status() {
                return real_more_status;
            }

            public void setReal_more_status(int real_more_status) {
                this.real_more_status = real_more_status;
            }

            public int getVerify_loan_pass() {
                return verify_loan_pass;
            }

            public void setVerify_loan_pass(int verify_loan_pass) {
                this.verify_loan_pass = verify_loan_pass;
            }

            public int getReal_pay_pwd_status() {
                return real_pay_pwd_status;
            }

            public void setReal_pay_pwd_status(int real_pay_pwd_status) {
                this.real_pay_pwd_status = real_pay_pwd_status;
            }

            public int getReal_taobao_status() {
                return real_taobao_status;
            }

            public void setReal_taobao_status(int real_taobao_status) {
                this.real_taobao_status = real_taobao_status;
            }

            public int getReal_accredit_status() {
                return real_accredit_status;
            }

            public void setReal_accredit_status(int real_accredit_status) {
                this.real_accredit_status = real_accredit_status;
            }

            public int getReal_verfy_base() {
                return real_verfy_base;
            }

            public void setReal_verfy_base(int real_verfy_base) {
                this.real_verfy_base = real_verfy_base;
            }

            public int getReal_verfy_senior() {
                return real_verfy_senior;
            }

            public void setReal_verfy_senior(int real_verfy_senior) {
                this.real_verfy_senior = real_verfy_senior;
            }

            public int getReal_verfy_more() {
                return real_verfy_more;
            }

            public void setReal_verfy_more(int real_verfy_more) {
                this.real_verfy_more = real_verfy_more;
            }

            public int getReal_online_bank_status() {
                return real_online_bank_status;
            }

            public void setReal_online_bank_status(int real_online_bank_status) {
                this.real_online_bank_status = real_online_bank_status;
            }

            public int getReal_accumulation_fund() {
                return real_accumulation_fund;
            }

            public void setReal_accumulation_fund(int real_accumulation_fund) {
                this.real_accumulation_fund = real_accumulation_fund;
            }

            public int getReal_social_security() {
                return real_social_security;
            }

            public void setReal_social_security(int real_social_security) {
                this.real_social_security = real_social_security;
            }

            public int getAuthentication_pass() {
                return authentication_pass;
            }

            public void setAuthentication_pass(int authentication_pass) {
                this.authentication_pass = authentication_pass;
            }
        }

        public static class ItemListBean implements Serializable{
            /**
             * title : 借款记录
             * subtitle :
             * group : 1
             * tag : 1
             * logo : http://47.104.232.195:21111/credit/web/image/tag/loan_record.png
             * url : http://47.104.232.195:21111/credit/web/loan/card-list?clientType=wap
             */

            private String title;
            private String subtitle;
            private int group;
            private int tag;
            private String logo;
            private String url;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getSubtitle() {
                return subtitle;
            }

            public void setSubtitle(String subtitle) {
                this.subtitle = subtitle;
            }

            public int getGroup() {
                return group;
            }

            public void setGroup(int group) {
                this.group = group;
            }

            public int getTag() {
                return tag;
            }

            public void setTag(int tag) {
                this.tag = tag;
            }

            public String getLogo() {
                return logo;
            }

            public void setLogo(String logo) {
                this.logo = logo;
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
