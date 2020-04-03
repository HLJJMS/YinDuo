package com.yinduowang.installment.mvp.model.entity;

import java.util.List;

public class AuthEmergencyBean {

    /**
     * contacts : {"familyType":"1","familyName":"钉钉DING消息","familyMobile":"02131772129","otherType":"1","otherName":"刘","otherMobile":"15910860609"}
     * dictionary : {"regions":null,"educationList":[{"id":0,"name":"博士"},{"id":1,"name":"硕士"},{"id":2,"name":"本科"},{"id":3,"name":"大专"},{"id":4,"name":"中专"},{"id":5,"name":"高中"},{"id":6,"name":"初中"},{"id":7,"name":"初中以下"},{"id":8,"name":"未知"}],"liveTimeList":[{"id":0,"name":"半年以内"},{"id":1,"name":"半年到一年"},{"id":2,"name":"一年以上"}],"maritalList":[{"id":0,"name":"未婚"},{"id":1,"name":"已婚未育"},{"id":2,"name":"已婚已育"},{"id":3,"name":"离异"},{"id":4,"name":"其它"}],"familyContactsList":[{"id":1,"name":"父亲"},{"id":2,"name":"母亲"},{"id":3,"name":"儿子"},{"id":4,"name":"女儿"},{"id":5,"name":"兄弟"},{"id":6,"name":"姐妹"},{"id":7,"name":"配偶"}],"otherContactsList":[{"id":8,"name":"同学"},{"id":9,"name":"亲戚"},{"id":10,"name":"同事"},{"id":11,"name":"朋友"},{"id":12,"name":"其他"}]}
     */

    private ContactsBean contacts;
    private DictionaryBean dictionary;

    public ContactsBean getContacts() {
        return contacts;
    }

    public void setContacts(ContactsBean contacts) {
        this.contacts = contacts;
    }

    public DictionaryBean getDictionary() {
        return dictionary;
    }

    public void setDictionary(DictionaryBean dictionary) {
        this.dictionary = dictionary;
    }

    public static class ContactsBean {
        /**
         * familyType : 1
         * familyName : 钉钉DING消息
         * familyMobile : 02131772129
         * otherType : 1
         * otherName : 刘
         * otherMobile : 15910860609
         */

        private String familyType;
        private String familyName;
        private String familyMobile;
        private String otherType;
        private String otherName;
        private String otherMobile;

        public String getFamilyType() {
            return familyType;
        }

        public void setFamilyType(String familyType) {
            this.familyType = familyType;
        }

        public String getFamilyName() {
            return familyName;
        }

        public void setFamilyName(String familyName) {
            this.familyName = familyName;
        }

        public String getFamilyMobile() {
            return familyMobile;
        }

        public void setFamilyMobile(String familyMobile) {
            this.familyMobile = familyMobile;
        }

        public String getOtherType() {
            return otherType;
        }

        public void setOtherType(String otherType) {
            this.otherType = otherType;
        }

        public String getOtherName() {
            return otherName;
        }

        public void setOtherName(String otherName) {
            this.otherName = otherName;
        }

        public String getOtherMobile() {
            return otherMobile;
        }

        public void setOtherMobile(String otherMobile) {
            this.otherMobile = otherMobile;
        }
    }

    public static class DictionaryBean {
        /**
         * regions : null
         * educationList : [{"id":0,"name":"博士"},{"id":1,"name":"硕士"},{"id":2,"name":"本科"},{"id":3,"name":"大专"},{"id":4,"name":"中专"},{"id":5,"name":"高中"},{"id":6,"name":"初中"},{"id":7,"name":"初中以下"},{"id":8,"name":"未知"}]
         * liveTimeList : [{"id":0,"name":"半年以内"},{"id":1,"name":"半年到一年"},{"id":2,"name":"一年以上"}]
         * maritalList : [{"id":0,"name":"未婚"},{"id":1,"name":"已婚未育"},{"id":2,"name":"已婚已育"},{"id":3,"name":"离异"},{"id":4,"name":"其它"}]
         * familyContactsList : [{"id":1,"name":"父亲"},{"id":2,"name":"母亲"},{"id":3,"name":"儿子"},{"id":4,"name":"女儿"},{"id":5,"name":"兄弟"},{"id":6,"name":"姐妹"},{"id":7,"name":"配偶"}]
         * otherContactsList : [{"id":8,"name":"同学"},{"id":9,"name":"亲戚"},{"id":10,"name":"同事"},{"id":11,"name":"朋友"},{"id":12,"name":"其他"}]
         */

        private Object regions;
        private List<EducationListBean> educationList;
        private List<LiveTimeListBean> liveTimeList;
        private List<MaritalListBean> maritalList;
        private List<FamilyContactsListBean> familyContactsList;
        private List<OtherContactsListBean> otherContactsList;

        public Object getRegions() {
            return regions;
        }

        public void setRegions(Object regions) {
            this.regions = regions;
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

        public List<FamilyContactsListBean> getFamilyContactsList() {
            return familyContactsList;
        }

        public void setFamilyContactsList(List<FamilyContactsListBean> familyContactsList) {
            this.familyContactsList = familyContactsList;
        }

        public List<OtherContactsListBean> getOtherContactsList() {
            return otherContactsList;
        }

        public void setOtherContactsList(List<OtherContactsListBean> otherContactsList) {
            this.otherContactsList = otherContactsList;
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

        public static class FamilyContactsListBean {
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

        public static class OtherContactsListBean {
            /**
             * id : 8
             * name : 同学
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
}
