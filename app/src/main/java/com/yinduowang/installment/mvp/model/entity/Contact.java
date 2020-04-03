package com.yinduowang.installment.mvp.model.entity;

/**
 * 通讯录
 * <p>
 * Created by cjiang on 17-8-29.
 */

public class Contact {
    private String mobile;//联系人的电话
    private String name;//联系人的姓名

    private String user_id;//用户id

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

}
