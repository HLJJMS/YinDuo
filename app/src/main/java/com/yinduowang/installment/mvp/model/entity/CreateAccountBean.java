package com.yinduowang.installment.mvp.model.entity;

import java.io.Serializable;

/**
 * Created by Tsing
 * on 2019/2/17
 */
public class CreateAccountBean implements Serializable {

    /**
     * status : 1
     * url : http://47.97.46.201:21113/Equity/Custody/appJump/su/c3NGZ0tERGpmRHFHMTdFK0pMcFhmMjk1L2h5TkZOS3JkZXBXTTUvL1lMUTNIRkNrSUtiVDAvWVJmNmM2U3BUWWNLbkozTFdsV0NMZmJUYnlRVlN2YnhIQXVQMFo5a1BITkZyZzF6ekxDNVJZb2oycUhpZ1ZPa3M4eDFLLzJDWE8=
     * debug_info : {"sql_count":"4","sql_time":"8ms"}
     */

    private int status;
    private String url;
    private DebugInfoBean debug_info;
    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public DebugInfoBean getDebug_info() {
        return debug_info;
    }

    public void setDebug_info(DebugInfoBean debug_info) {
        this.debug_info = debug_info;
    }

    public static class DebugInfoBean {
        /**
         * sql_count : 4
         * sql_time : 8ms
         */

        private String sql_count;
        private String sql_time;

        public String getSql_count() {
            return sql_count;
        }

        public void setSql_count(String sql_count) {
            this.sql_count = sql_count;
        }

        public String getSql_time() {
            return sql_time;
        }

        public void setSql_time(String sql_time) {
            this.sql_time = sql_time;
        }
    }
}
