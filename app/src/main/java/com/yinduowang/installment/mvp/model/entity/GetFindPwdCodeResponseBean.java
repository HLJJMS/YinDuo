package com.yinduowang.installment.mvp.model.entity;

import java.io.Serializable;

/**
 * Created by wuyan on 2017/5/20.
 */

public class GetFindPwdCodeResponseBean implements Serializable {
  private  boolean real_verify_status;

    public boolean getReal_verify_status() {
        return real_verify_status;
    }

    public void setReal_verify_status(boolean real_verify_status) {
        this.real_verify_status = real_verify_status;
    }
}
