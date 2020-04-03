package com.yinduowang.installment.mvp.model.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/1/16 0016.
 */

public class TabBarListEntity implements Serializable {

    /**
     * item : [{"title":"借款","tag":1,"image":"http://42.96.204.114/koudai/kdkj/credit/web/image/tag/v1/tab_loan.png","sel_image":"http://42.96.204.114/koudai/kdkj/credit/web/image/tag/v1/tab_loan_1.png","red_image":"","span_color":"#333300","sel_span_color":"#ffccff"},{"title":"还款","tag":2,"image":"http://42.96.204.114/koudai/kdkj/credit/web/image/tag/v1/tab_repayment.png","sel_image":"http://42.96.204.114/koudai/kdkj/credit/web/image/tag/v1/tab_repayment_1.png","red_image":"","span_color":"#333300","sel_span_color":"#ffccff"},{"title":"我的","tag":3,"image":"http://42.96.204.114/koudai/kdkj/credit/web/image/tag/v1/tab_mine.png","sel_image":"http://42.96.204.114/koudai/kdkj/credit/web/image/tag/v1/tab_mine_1.png","red_image":"http://42.96.204.114/koudai/kdkj/credit/web/image/tag/v1/tab_mine_2.png","span_color":"#333300","sel_span_color":"#ffccff"}]
     * bg_color : #ffccff
     */

    private String bg_color;//背景色
    private List<ItemBean> item;//tab信息

    public String getBg_color() {
        return bg_color;
    }

    public void setBg_color(String bg_color) {
        this.bg_color = bg_color;
    }

    public List<ItemBean> getItem() {
        return item;
    }

    public void setItem(List<ItemBean> item) {
        this.item = item;
    }

    public static class ItemBean implements Serializable{
        /**
         * title : 借款
         * tag : 1
         * image : http://42.96.204.114/koudai/kdkj/credit/web/image/tag/v1/tab_loan.png
         * sel_image : http://42.96.204.114/koudai/kdkj/credit/web/image/tag/v1/tab_loan_1.png
         * red_image :
         * span_color : #333300
         * sel_span_color : #ffccff
         */

        private String title;//标题
        private int tag;//排序 小数在前（1   2   3以此类推）
        private String image;//默认图片地址
        private String sel_image;//选择图片地址
        private String red_image;//
        private String span_color;//默认文字颜色
        private String sel_span_color;//选中文字颜色

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getTag() {
            return tag;
        }

        public void setTag(int tag) {
            this.tag = tag;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getSel_image() {
            return sel_image;
        }

        public void setSel_image(String sel_image) {
            this.sel_image = sel_image;
        }

        public String getRed_image() {
            return red_image;
        }

        public void setRed_image(String red_image) {
            this.red_image = red_image;
        }

        public String getSpan_color() {
            return span_color;
        }

        public void setSpan_color(String span_color) {
            this.span_color = span_color;
        }

        public String getSel_span_color() {
            return sel_span_color;
        }

        public void setSel_span_color(String sel_span_color) {
            this.sel_span_color = sel_span_color;
        }
    }
}
