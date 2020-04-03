package com.yinduowang.installment.app.utils

import com.blankj.utilcode.util.SPUtils

object SignUtils {
    fun getSign(params: Map<String, String>): String {
        val lStr = StringUtil.createLinkString(params)
        val md5Str = lStr + SPUtils.getInstance().getString("token")
        return MD5Util.getMD5String(md5Str)
    }
}
