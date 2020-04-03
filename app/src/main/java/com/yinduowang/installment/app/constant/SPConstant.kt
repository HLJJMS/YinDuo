package com.yinduowang.installment.app.constant

/**
 * SharedPreferences存储key值
 * */
object SPConstant {

    // 是否是提测版本
    const val IS_TEST_VERSION = "is_test_version"

    //是否同意隐私政策
    const val AGREE_TO_PRIVACY_POLICY = "Agree_to_Privacy_Policy"

    const val UPLOAD_DATA_TIME = "upload_data_time"

    const val USER_ID = "userId"
    // 手机号
    const val NICK_NAME = "nickName"
    // 登录标识
    const val TOKEN = "token"
    const val TOKEN_EXPIRE = "tokenExpire"
    const val REFRESH_TOKEN = "refreshToken"
    const val REFRESH_TOKEN_EXPIRE = "refreshTokenExpire"
    const val FLOAT_BUTTON_TYPE = "float_button_type"
    //记录用户从登录状态切换为退出登录状态
    const val SHARE_TAG_USER_LOGIN_TO_SIGNOUT = "login_to_sign_out"
    // 是否是第一次登录 判断显示引导页
    const val KEY_IS_NOT_FIRST_RUN = "is_not_first_run"
    // 保存配置 热修复初始化 是否是开发设备配置字段
    const val SHARED_PRE_IS_DEVELOPMENT_DEVICE = "isDevelopmentDevice"
    // PHP切换java接口 需要将本地用户信息清除
    const val INSTALL_APP_FROM_PHP_TO_JAVA = "install_app_from_php_to_java"

    /**
     * 协议的type对应：
     * 1、注册页协议
     * 2、认证中心协议
     * 3、借款详情协议
     * 4、还款详情协议
     * 5、运营商认证协议
     * 6、宝付授权协议
     * 7、帮助中心web
     * 8、消息中心web
     * 9、还款帮助web
     * 10、运营商忘记密码web
     * 11、支付宝退款web
     */

    const val WEB_URL_VERSION = "web_url_version"
    // 1、注册页协议
    const val WEB_URL_TYPE_ONE_DATA = "web_url_type_one_data"
    // 2、认证中心协议
    const val WEB_URL_TYPE_TWO_FIRST_DATA = "web_url_type_two_first_data"
    const val WEB_URL_TYPE_TWO_SECOND_DATA = "web_url_type_two_second_data"
    const val WEB_URL_TYPE_TWO_THIRD_DATA = "web_url_type_two_third_data"
    // 3、借款详情协议
    const val WEB_URL_TYPE_THREE_FIRST_DATA = "web_url_type_three_first_data"
    const val WEB_URL_TYPE_THREE_SECOND_DATA = "web_url_type_three_second_data"
    const val WEB_URL_TYPE_THREE_THIRD_DATA = "web_url_type_three_third_data"
    // 4、还款详情协议
    const val WEB_URL_TYPE_FOUR_FIRST_DATA = "web_url_type_four_first_data"
    const val WEB_URL_TYPE_FOUR_SECOND_DATA = "web_url_type_four_second_data"
    const val WEB_URL_TYPE_FOUR_THIRD_DATA = "web_url_type_four_third_data"
    // 5、运营商认证协议
    const val WEB_URL_TYPE_FIVE_DATA = "web_url_type_five_data"
    // 6、宝付授权协议
    const val WEB_URL_TYPE_SIX_DATA = "web_url_type_six_data"
    // 7、帮助中心web
    const val WEB_URL_TYPE_SEVEN_DATA = "web_url_type_seven_data"
    // 8、消息中心web
    const val WEB_URL_TYPE_EIGHT_DATA = "web_url_type_eight_data"
    // 9、还款帮助web
    const val WEB_URL_TYPE_NINE_DATA = "web_url_type_nine_data"
    // 10、运营商忘记密码web
    const val WEB_URL_TYPE_TEN_DATA = "web_url_type_ten_data"
    // 11、支付宝退款web
    const val WEB_URL_TYPE_ELEVEN_DATA = "web_url_type_eleven_data"
}
