package com.yinduowang.installment.mvp.model.entity;

import java.io.Serializable;

/**
 * Created by Tsing
 * on 2019/2/16
 */
public class GetEdBean implements Serializable {


    /**
     * message : 亲，正在努力为您开通 高级会员！此过程预计需要1-2分钟，请您耐心等待哦！
     * footer : {"title":" 高级会员开通中...","status":1,"card_type":2}
     */

    private String message;
    private FooterBean footer;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public FooterBean getFooter() {
        return footer;
    }

    public void setFooter(FooterBean footer) {
        this.footer = footer;
    }

    public static class FooterBean {
        /**
         * title :  高级会员开通中...
         * status : 1
         * card_type : 2
         */

        private String title;
        private int status;
        private int card_type;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getCard_type() {
            return card_type;
        }

        public void setCard_type(int card_type) {
            this.card_type = card_type;
        }
    }
}
