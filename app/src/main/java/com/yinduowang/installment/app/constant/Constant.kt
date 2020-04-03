package com.yinduowang.installment.app.constant

import android.Manifest
import android.content.Context
import com.jess.arms.utils.DeviceUtils

object Constant {

    const val SHARE_TAG_REAL = "isReal" //是否实名

    const val DEFAULT_MILLISECONDS = 30000L


    val permissions = arrayOf(
            Manifest.permission.INTERNET,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.CHANGE_WIFI_STATE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION)
    val permissions_record_audio = arrayOf(Manifest.permission.RECORD_AUDIO)
    val permissions_call_phone = arrayOf(Manifest.permission.CALL_PHONE)


    //操作的key值
    const val TAG_OPERATE_LEND = "LEND"

    // 全局参数
    const val TAG_MOBILE_TYPE = "mobile_type"
    const val TAG_VERSION_NUMBER = "version_number"
    // 全局参数 设备类型 android
    const val TAG_MOBILE_TYPE_ANDROID = "android"
    // 是否是第一次登录 判断显示引导页
    const val CODE_GET_CONTACT = 110
    const val CODE_GET_CONTACT2 = 111

    fun getCacheHomeShoppingMall(context: Context): String {
        return "cache_home_shopping_mall_" + DeviceUtils.getVersionCode(context)
    }

    const val TAG_LOADING_UTILS = "request_loading"
    // 延时时间
    const val DELAY_TIME = 100
}