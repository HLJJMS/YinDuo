package com.yinduowang.installment.mvp.model.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Tsing
 * on 2019/3/8
 */
public class UserInfoAllBean implements Serializable {


    /**
     * username :
     * idCard :
     * education :
     * address : null:null:null:null:null:null
     * detailedAddress :
     * liveTime :
     * marital :
     * identificationFrontUrl : https://www.yinduowang.com/Public/new/images/i/n60.png
     * identificationReverseUrl : https://www.yinduowang.com/Public/new/images/i/n60.png
     * faceUrl : https://www.yinduowang.com/Public/new/images/i/n60.png
     * educationList : [{"id":0,"name":"博士"},{"id":1,"name":"硕士"},{"id":2,"name":"本科"},{"id":3,"name":"大专"},{"id":4,"name":"中转"},{"id":5,"name":"高中"},{"id":6,"name":"初中"},{"id":7,"name":"初中以下"},{"id":8,"name":"未知"}]
     * liveTimeList : [{"id":0,"name":"半年以内"},{"id":1,"name":"半年到一年"},{"id":2,"name":"一年以上"}]
     * maritalList : [{"id":0,"name":"未婚"},{"id":1,"name":"已婚未育"},{"id":2,"name":"已婚已育"},{"id":3,"name":"离异"},{"id":4,"name":"其它"}]
     * contactsList : [{"id":1,"name":"父亲"},{"id":2,"name":"母亲"},{"id":3,"name":"儿子"},{"id":4,"name":"女儿"},{"id":5,"name":"兄弟"},{"id":6,"name":"姐妹"},{"id":7,"name":"配偶"},{"id":8,"name":"同学"},{"id":9,"name":"亲戚"},{"id":10,"name":"同事"},{"id":11,"name":"朋友"},{"id":12,"name":"其他"}]
     * currentAddress :
     */

    private String username;
    private String idCard;
    private String education;
    private String address;
    private String detailedAddress;
    private String liveTime;
    private String marital="0";
    private String identificationFrontUrl;
    private String identificationReverseUrl;
    private String faceUrl;
    private String currentAddress;
    private List<EducationListBean> educationList;
    private List<LiveTimeListBean> liveTimeList;
    private List<MaritalListBean> maritalList;
    private List<ContactsListBean> contactsList;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDetailedAddress() {
        return detailedAddress;
    }

    public void setDetailedAddress(String detailedAddress) {
        this.detailedAddress = detailedAddress;
    }

    public String getLiveTime() {
        return liveTime;
    }

    public void setLiveTime(String liveTime) {
        this.liveTime = liveTime;
    }

    public String getMarital() {
        return marital;
    }

    public void setMarital(String marital) {
        this.marital = marital;
    }

    public String getIdentificationFrontUrl() {
        return identificationFrontUrl;
    }

    public void setIdentificationFrontUrl(String identificationFrontUrl) {
        this.identificationFrontUrl = identificationFrontUrl;
    }

    public String getIdentificationReverseUrl() {
        return identificationReverseUrl;
    }

    public void setIdentificationReverseUrl(String identificationReverseUrl) {
        this.identificationReverseUrl = identificationReverseUrl;
    }

    public String getFaceUrl() {
        return faceUrl;
    }

    public void setFaceUrl(String faceUrl) {
        this.faceUrl = faceUrl;
    }

    public String getCurrentAddress() {
        return currentAddress;
    }

    public void setCurrentAddress(String currentAddress) {
        this.currentAddress = currentAddress;
    }

    public List<EducationListBean> getEducationList() {
        return educationList;
    }

    public void setEducationList(List<EducationListBean> educationList) {
        this.educationList = educationList;
    }

    public List<LiveTimeListBean> getLiveTimeList() {
        return liveTimeList;
    }

    public void setLiveTimeList(List<LiveTimeListBean> liveTimeList) {
        this.liveTimeList = liveTimeList;
    }

    public List<MaritalListBean> getMaritalList() {
        return maritalList;
    }

    public void setMaritalList(List<MaritalListBean> maritalList) {
        this.maritalList = maritalList;
    }

    public List<ContactsListBean> getContactsList() {
        return contactsList;
    }

    public void setContactsList(List<ContactsListBean> contactsList) {
        this.contactsList = contactsList;
    }

    public static class EducationListBean {
        /**
         * id : 0
         * name : 博士
         */

        private int id;
        private String name;

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
    }

    public static class LiveTimeListBean {
        /**
         * id : 0
         * name : 半年以内
         */

        private int id;
        private String name;

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
    }

    public static class MaritalListBean {
        /**
         * id : 0
         * name : 未婚
         */

        private int id;
        private String name;

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
    }

    public static class ContactsListBean {
        /**
         * id : 1
         * name : 父亲
         */

        private int id;
        private String name;

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
    }
}
