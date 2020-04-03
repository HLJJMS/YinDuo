package com.yinduowang.installment.app

object BaseConfig {

    /**
     * 刘超本地
     * */
//    const val BASE_IP_JAVA = "http://47.105.78.210:17420/"// java基础地址 项目默认地址V1.0
//    const val BASE_IP_PHP = "http://192.168.50.118:28256/api/"// php基础本地地址
//    const val BASE_IP_AGREEMENT = "http://47.105.78.210:10042/"// 协议基础地址
//    const val BASE_IP_SHOP = "http://192.168.50.118:28256/api/"// 商城基础地址

    /**
     * 测试地址
     * */
//    const val BASE_IP_JAVA = "http://47.105.78.210:17420/"// java基础地址 项目默认地址V1.0
//    const val BASE_IP_PHP = "http://47.105.78.210:38356/api/"// php基础地址
//    const val BASE_IP_AGREEMENT = "http://47.105.78.210:10042/"// 协议基础地址
//    const val BASE_IP_SHOP = "http://47.105.78.210:10041/"// 商城基础地址

    /**
     * 正式地址
     * */
    const val BASE_IP_JAVA = "https://xffqcardapi.yinduoziben.net/"// java基础地址 项目默认地址
    const val BASE_IP_PHP = "https://xffqshopapi.yinduoziben.net/api/"// php基础地址
    const val BASE_IP_AGREEMENT = "http://47.99.54.31:10045/"// 协议基础地址
    const val BASE_IP_SHOP = "https://xffqshoph5.yinduoziben.net/"// 商城基础地址

    /**
     * 是否开启提测
     * */
    const val IS_OPEN_TEST = false

    /**
     * 白骑士
     */
    // 商户号
//    const val BAI_QI_SHI = "hjjm"
//    // 白骑士 false是生产环境 true是测试环境
//    const val BAI_QI_SHI_IS_TEST = false

    /**
     * bugly 热修复
     */
    // 正式
    const val BUGLY_KEY = "31b7729f10";
    const val BUGLY_IS_DEBUG = false
    // 测试
//    const val BUGLY_KEY = "718b497005"
//    const val BUGLY_IS_DEBUG = true

    /**
     * 高德地图定位
     * */
    const val AMAP_API_KEY = "61049bb2989ce71bfca489c9f8cc97dd"

    /**
     * 美洽
     * */
    const val MQ_API_KEY = "cf120b34aa7892a79d093551081a9252"

    /**
     * 友盟
     */
    const val UM_API_KEY = "5dc4c5f13fc195ab88000b95"
    const val UM_SECRET = "4833b0dce42496f6ee0b44684101890f"
}
