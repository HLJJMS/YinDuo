package com.yinduowang.installment.mvp.model.entity;

import java.util.List;

public class ReimbursementBannerBean {

    private List<DataListBean> data_list;

    public List<DataListBean> getData_list() {
        return data_list;
    }

    public void setData_list(List<DataListBean> data_list) {
        this.data_list = data_list;
    }

    public static class DataListBean {
        /**
         * source_id : 1
         * thumb : http://47.97.46.201:22019/Public/uploads/20190316/5c8cbdf1c57c9.png
         * url : http://www.baidu.com
         */

        private String source_id;
        private String thumb;
        private String url;

        public String getSource_id() {
            return source_id;
        }

        public void setSource_id(String source_id) {
            this.source_id = source_id;
        }

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
