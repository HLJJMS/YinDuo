package com.yinduowang.installment.mvp.model.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Tsing
 * on 2019/1/28
 */
public class PersonDetailBean implements Serializable {

    /**
     * item : {"id":11454,"name":"杨昆","id_number":"222424198902064938","degrees":3,"marriage":1,"address":"转瞬即逝的时候","live_period":1,"address_distinct":"黑龙江 哈尔滨 南岗区","longitude":"Optional(126.68554741753472)","latitude":"Optional(45.76535563151042)","real_verify_status":1,"face_recognition_picture":"http://diandai.oss-cn-shanghai.aliyuncs.com/pictures/face_recognition/11454/F4057EBB2996A.jpg","id_number_z_picture":"http://diandai.oss-cn-shanghai.aliyuncs.com/pictures/idcard/11454/7F2AE8331D079.jpg","id_number_f_picture":"http://diandai.oss-cn-shanghai.aliyuncs.com/pictures/idcard/11454/E6A6404A2439F.jpg","degrees_all":[{"degrees":1,"name":"博士"},{"degrees":2,"name":"硕士"},{"degrees":3,"name":"本科"},{"degrees":4,"name":"大专"},{"degrees":5,"name":"中专"},{"degrees":6,"name":"高中"},{"degrees":7,"name":"初中"},{"degrees":8,"name":"初中以下"},{"degrees":9,"name":"未知"}],"marriage_all":[{"marriage":1,"name":"未婚"},{"marriage":2,"name":"已婚未育"},{"marriage":3,"name":"已婚已育"},{"marriage":4,"name":"离异"},{"marriage":100,"name":"其他"}],"live_time_type_all":[{"live_time_type":1,"name":"半年以内"},{"live_time_type":2,"name":"半年到一年"},{"live_time_type":3,"name":"一年以上"}]}
     */

    private ItemBean item;

    public ItemBean getItem() {
        return item;
    }

    public void setItem(ItemBean item) {
        this.item = item;
    }

    public static class ItemBean {
        /**
         * id : 11454
         * name : 杨昆
         * id_number : 222424198902064938
         * degrees : 3
         * marriage : 1
         * address : 转瞬即逝的时候
         * live_period : 1
         * address_distinct : 黑龙江 哈尔滨 南岗区
         * longitude : Optional(126.68554741753472)
         * latitude : Optional(45.76535563151042)
         * real_verify_status : 1
         * face_recognition_picture : http://diandai.oss-cn-shanghai.aliyuncs.com/pictures/face_recognition/11454/F4057EBB2996A.jpg
         * id_number_z_picture : http://diandai.oss-cn-shanghai.aliyuncs.com/pictures/idcard/11454/7F2AE8331D079.jpg
         * id_number_f_picture : http://diandai.oss-cn-shanghai.aliyuncs.com/pictures/idcard/11454/E6A6404A2439F.jpg
         * degrees_all : [{"degrees":1,"name":"博士"},{"degrees":2,"name":"硕士"},{"degrees":3,"name":"本科"},{"degrees":4,"name":"大专"},{"degrees":5,"name":"中专"},{"degrees":6,"name":"高中"},{"degrees":7,"name":"初中"},{"degrees":8,"name":"初中以下"},{"degrees":9,"name":"未知"}]
         * marriage_all : [{"marriage":1,"name":"未婚"},{"marriage":2,"name":"已婚未育"},{"marriage":3,"name":"已婚已育"},{"marriage":4,"name":"离异"},{"marriage":100,"name":"其他"}]
         * live_time_type_all : [{"live_time_type":1,"name":"半年以内"},{"live_time_type":2,"name":"半年到一年"},{"live_time_type":3,"name":"一年以上"}]
         */

        private int id;
        private String name;
        private String id_number;
        private String degrees;
        private String marriage;
        private String address;
        private String live_period;
        private String address_distinct;
        private String longitude;
        private String latitude;
        private int real_verify_status;
        private String face_recognition_picture;
        private String id_number_z_picture;
        private String id_number_f_picture;
        private List<DegreesAllBean> degrees_all;
        private List<MarriageAllBean> marriage_all;
        private List<LiveTimeTypeAllBean> live_time_type_all;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId_number() {
            return id_number;
        }

        public void setId_number(String id_number) {
            this.id_number = id_number;
        }



        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getDegrees() {
            return degrees;
        }

        public void setDegrees(String degrees) {
            this.degrees = degrees;
        }

        public String getMarriage() {
            return marriage;
        }

        public void setMarriage(String marriage) {
            this.marriage = marriage;
        }

        public String getLive_period() {
            return live_period;
        }

        public void setLive_period(String live_period) {
            this.live_period = live_period;
        }

        public String getAddress_distinct() {
            return address_distinct;
        }

        public void setAddress_distinct(String address_distinct) {
            this.address_distinct = address_distinct;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public int getReal_verify_status() {
            return real_verify_status;
        }

        public void setReal_verify_status(int real_verify_status) {
            this.real_verify_status = real_verify_status;
        }

        public String getFace_recognition_picture() {
            return face_recognition_picture;
        }

        public void setFace_recognition_picture(String face_recognition_picture) {
            this.face_recognition_picture = face_recognition_picture;
        }

        public String getId_number_z_picture() {
            return id_number_z_picture;
        }

        public void setId_number_z_picture(String id_number_z_picture) {
            this.id_number_z_picture = id_number_z_picture;
        }

        public String getId_number_f_picture() {
            return id_number_f_picture;
        }

        public void setId_number_f_picture(String id_number_f_picture) {
            this.id_number_f_picture = id_number_f_picture;
        }

        public List<DegreesAllBean> getDegrees_all() {
            return degrees_all;
        }

        public void setDegrees_all(List<DegreesAllBean> degrees_all) {
            this.degrees_all = degrees_all;
        }

        public List<MarriageAllBean> getMarriage_all() {
            return marriage_all;
        }

        public void setMarriage_all(List<MarriageAllBean> marriage_all) {
            this.marriage_all = marriage_all;
        }

        public List<LiveTimeTypeAllBean> getLive_time_type_all() {
            return live_time_type_all;
        }

        public void setLive_time_type_all(List<LiveTimeTypeAllBean> live_time_type_all) {
            this.live_time_type_all = live_time_type_all;
        }

        public static class DegreesAllBean {
            /**
             * degrees : 1
             * name : 博士
             */

            private String degrees;
            private String name;

            public String getDegrees() {
                return degrees;
            }

            public void setDegrees(String degrees) {
                this.degrees = degrees;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }

        public static class MarriageAllBean {
            /**
             * marriage : 1
             * name : 未婚
             */

            private String marriage;
            private String name;

            public String getMarriage() {
                return marriage;
            }

            public void setMarriage(String marriage) {
                this.marriage = marriage;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }

        public static class LiveTimeTypeAllBean {
            /**
             * live_time_type : 1
             * name : 半年以内
             */

            private String live_time_type;
            private String name;

            public String getLive_time_type() {
                return live_time_type;
            }

            public void setLive_time_type(String live_time_type) {
                this.live_time_type = live_time_type;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}
