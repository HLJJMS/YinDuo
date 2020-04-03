package com.yinduowang.installment.mvp.model.event;

/**
 * Created by Tsing
 * on 2019/1/15
 */
public class LoginEvent {
    private boolean isLogin;

    public LoginEvent(boolean isLogin) {
        this.isLogin = isLogin;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }
}
