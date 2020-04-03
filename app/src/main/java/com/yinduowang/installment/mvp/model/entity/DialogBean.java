package com.yinduowang.installment.mvp.model.entity;


import java.io.Serializable;

public class DialogBean implements Serializable {
    private String id;
    private String img_url;
    public Active active;
    public CloseOrigin close_origin;

    public Active getActive() {
        return active;
    }

    public void setActive(Active active) {
        this.active = active;
    }

    public CloseOrigin getClose_origin() {
        return close_origin;
    }

    public void setClose_origin(CloseOrigin close_origin) {
        this.close_origin = close_origin;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public class Active {
        private String skip_code;
        private String url;

        public String getSkip_code() {
            return skip_code;
        }

        public void setSkip_code(String skip_code) {
            this.skip_code = skip_code;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public class CloseOrigin {
        private String x;
        private String y;

        public String getX() {
            return x;
        }

        public void setX(String x) {
            this.x = x;
        }

        public String getY() {
            return y;
        }

        public void setY(String y) {
            this.y = y;
        }
    }
}

