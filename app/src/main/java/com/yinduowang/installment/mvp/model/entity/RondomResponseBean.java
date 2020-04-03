package com.yinduowang.installment.mvp.model.entity;

import java.io.Serializable;

/**
 * Created by wuyan on 2017/5/20.
 */

public class RondomResponseBean implements Serializable {
    private String random;

    public String getRandom() {
        return random;
    }

    public void setRandom(String random) {
        this.random = random;
    }
}
