package com.yinduowang.installment.app.utils

import com.blankj.utilcode.util.SPUtils

import com.yinduowang.installment.app.constant.SPConstant

object ClearLoginDataUtil {
    /**
     * 清除登录状态
     */
    fun clearLoginStatus() {
        SPUtils.getInstance().remove(SPConstant.USER_ID)
        SPUtils.getInstance().remove(SPConstant.TOKEN)
        SPUtils.getInstance().remove(SPConstant.NICK_NAME)
        SPUtils.getInstance().remove(SPConstant.TOKEN_EXPIRE)
        SPUtils.getInstance().remove(SPConstant.REFRESH_TOKEN)
        SPUtils.getInstance().remove(SPConstant.REFRESH_TOKEN_EXPIRE)

    }
}
