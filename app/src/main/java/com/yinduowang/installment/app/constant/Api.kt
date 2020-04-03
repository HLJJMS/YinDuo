/*
 * Copyright 2017 JessYan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, softwarecredit-app/hot-dot
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.yinduowang.installment.app.constant

import com.yinduowang.installment.app.BaseConfig
import com.yinduowang.installment.app.BaseConfig.BASE_IP_JAVA
import com.yinduowang.installment.app.BaseConfig.BASE_IP_PHP
import com.yinduowang.installment.app.BaseConfig.BASE_IP_SHOP

/**
 * ================================================
 * 存放一些与 API 有关的东西,如请求地址,请求码等
 *
 *
 * Created by JessYan on 08/05/2016 11:25
 * [Contact me](mailto:jess.yan.effort@gmail.com)
 * [Follow me](https://github.com/JessYanCoding)
 * ================================================
 */
object Api {
    const val RequestSuccess = "200"
    const val RequestPhpSuccess = "1"
    const val BASE_URL = BASE_IP_JAVA + "api/"

    /**  ↓↓↓↓PHP接口相关↓↓↓↓ **/
    // 收货地址同类表 get  设置默认地址post
    const val DELIVERY_ADDRESS_LIST = BASE_IP_PHP + "member/address_index"
    // 设置-POST 添加或者修改收货地址—GET 获取地址详情
    const val CHANGE_DELIVER_ADDRESS = BASE_IP_PHP + "member/address_detail"
    // 获取我的订单列表
    const val GET_SHOP_ORDER_RECORD = BASE_IP_PHP + "order/index"
    // 获取订单详细
    const val GET_SHOP_ORDER_DETAILED = BASE_IP_PHP + "order/detail"
    // 确认订单
    const val BUY = BASE_IP_PHP + "order/buy"
    // 确认收货
    const val CONFIRMN_RECEIPT = BASE_IP_PHP + "order/confirm"
    // 获取取消订单POST /
    const val CANCEL_SHOP_ORDER = BASE_IP_PHP + "order/cancel"
    // 退换售后
    const val ORDER_RETREAT = BASE_IP_PHP + "order/retreat"
    // 删除地址/
    const val DETAIL_ADDRESS = BASE_IP_PHP + "member/address_del"
    // 物流信息
    const val LOGISTICS = BASE_IP_PHP + "order/logistics"
    // 商城首页
    const val HOME_SHOPPING_MALL = BASE_IP_PHP + "index/index"
    // 悬浮窗是否显示
    const val FLOATBUTTONSTATUS = BASE_IP_PHP + "Api/tan"
    // 获取悬浮窗列表
    const val FLOATBUTTONLIST = BASE_IP_PHP + "Api/index"
    // 点击列表埋点
    const val FLOATCLICKAREA = BASE_IP_PHP + "market/click"// ac_type 1首页点击 2还款页点击

    const val ORDER_PAY = BASE_IP_PHP + "order/pay"
    const val MARKET_INDEX = BASE_IP_PHP + "market/index"

    // 商城首页 分类 更多
    const val CLASSIFICATION_CATEGORY = BASE_IP_SHOP + "classification/category"
    // 商城首页 分类 专区
    const val CLASSIFICATION_GOODS_LIST = BASE_IP_SHOP + "classification/goodsList"
    // 商城首页 详情
    const val CLASSIFICATION_GOODS_DETAIL = BASE_IP_SHOP + "classification/goodsDetail"
    // 专区
    const val CLASSIFICATION_DISTRICT = BASE_IP_SHOP + "classification/district"
    /**  ↑↑↑↑PHP接口相关↑↑↑↑ **/

    /**  ↓↓↓↓java接口相关↓↓↓↓ **/
    // 校验是否还款中
    const val REPAY_PAGE_CHECK_REPAY = "repaypage/checkRepay"
    // 首页登录提示语
    const val LOGIN_MESSAGE = "vip/getLoginMessage"
    // 额度验证（确认订单的时候）
    const val CHECK_QUOTA = "quota/checkQuota"
    // vip弹窗状态变更
    const val VIP_CLOSE_VIPPOP = "vip/closeVipPop"
    // 获取验证码
    const val GET_VERIFY_CODE = "authCode/send"
    // 短信登录
    const val VERIFY_LOGIN = "login/sms"
    // 密码登录
    const val PASSWORD_LOGIN = "login/pwd"
    // 找回登录密码
    const val FIND_BACK_PASSWORD = "password/retrievepw"
    // 意见反馈
    const val FEED_BACK = "feedback/savefeedback"
    // 催收投诉
    const val COLLECTION_COMPLAINS = "complaint/savecomplaint"
    // 获取投诉类型
    const val GET_COLLECTION_TYPE = "complaint/getcomplaint"
    // 修改登录密码
    const val CHANGE_PASSWORD = "password/updatepw"
    // 设置 验证是否设置交易密码
    const val VALIDATE_PAY_PASSWORD_SET = "transactionpw/validatepasswordset"
    // 设置 忘记交易密码
    const val FORGET_PAY_PASSWORD = "transactionpw/retrievetransactionpw"
    // 设置 第一次设置走交易密码
    const val SET_PAY_PASSWORD = "transactionpw/setuptransactionpw"
    // 设置 校验原交密码正误
    const val VALIDATE_PAY_PASSWORD = "transactionpw/updatetransactionpw"
    // 设置 修改交易密码
    const val CHANGE_PAY_PASSWORD = "transactionpw/alerttransactionpw"
    // 获取个人信息
    const val GET_USER_INFO_ALL = "userinfo/getuserinfoall"
    // 查询用户完善状态
    const val PERFECT_NEW = "perfect"
    // MAIN APP升级接口
    const val APP_UPDATA = "support/getversion"
    // 客服电话
    const val SERVICE_TEL = "support/serviceTel"
    // 首页 我的验证用户是否为停用
    const val IS_AVAILABLE = "userinfo/isAvailable"
    // 验证是否为提测版本
    const val PROTOCOL_GET_LOGIN_BANNER_INFO = "protocol/getLoginBannerInfo"
    // 首页 我的
    const val USERINOF_MY_INFO = "userinfo/myInfo"
    // 设置借款记录
    const val LOAN_RECORD_LIST = "loan/record/loanRecordList"
    // 首页 现金借款详情
    const val CASH_LOAN_DETAILED = "loan/record/loanCashDetails"
    // 我的 还款记录
    const val RAY_BACK_RECORD = "payment/paymentrecordlist"
    // 银行卡列表
    const val GET_BANK_CARD = "bankCard/getBankCard"
    // 我的—消息列表
    const val GET_MESSAGE_CENTER = "unread/unreadlist"
    // 设置-退出登录
    const val LOGIN_OUT = "login/logout"
    // 我的—我的额度
    const val GET_MY_QUOTA = "quota/getmyquota"
    // 我的—我的额度_额度计算中
    const val GET_QUOTA = "quota/getquota"
    // 首页 借款
    const val LOAN_INDEX = "loan/index"
    // 首页 法大大签约按钮
    const val SIGN_CONTRACT = "protocol/signContract"
    // 首页 借款按钮弹出toast
    const val CONFIRM_APPLY_BUTTOM = "loan/checkstatus"
    // 首页 借款按钮弹出toast
    const val LOAN_BANNERS = "banner/getBanners"
    // 首页 借款详细页
    const val LOAN_APPLY_PAGE = "loan/applymoney"
    // 首页 借钱按钮
    const val CONFIRM_LOAN = "loan/affirmapply"
    // 首页 借款 点击按钮后 判断能否借款
    const val LOAN_BORROW_BBUTTON = "loan/borrowButton"
    // 首页 借款 点击按钮后 判断能否借款
    const val LOAN_APPLY_DETAILS = "loan/loanapplydetails"
    // 首页 借款 关闭额度弹窗
    const val LOAN_LOAN_CLOSEJECT = "loan/closeEject"
    // 首页 banner
    const val GETBANNERS = "banner/getBanners"
    // 注册
    const val REGISTER = "register"
    // 手机运营商认证  初始化认证
    const val MOBILE_OPERATOR_CERTIFICATION = "mobileoperator/certification"
    // 上传 短信
    const val SAVEUSERSMS = "userinfocollect/saveUserSms"
    // 上传通讯录
    const val SAVEADDRESSBOOK = "userinfocollect/saveAddressBook"
    // app列表
    const val GETUSERAPP = "userinfocollect/getuserapp"
    const val CONTACTS_ALL = "contacts/all"
    // 保存紧急联系人
    const val UPDATE = "contacts/update"
    // 上饶开户
    const val OPEN_ACCOUNT = "openaccount/open"
    // 手机运营商认证  再次发送验证码
    const val AUTHCODEAGAIN = "mobileoperator/authcodeagain"
    // 手机运营商认证  流程吗确认
    const val CERTIFICATIONCODE = "mobileoperator/certificationcode"
    // 抱负代扣验证码
    const val PRIBIND = "bankCard/preBind"
    // 抱负初始化接口
    const val GETBANKCARD = "bankCard/getBankCard"
    // 宝付初获取页面新接口
    const val GETBANKCARD_NEW = "bankCard/baofooAuthIndex"
    // 确认绑定抱负
    const val CONFIRMBIND = "bankCard/confirmBind"
    const val GET_USER_INFO = "userinfo/getuserinfo"
    // 预绑定（发送验证码）
    const val PRE_BIND = "bankCard/preBind"
    const val CONFIRM_BIND = "bankCard/confirmBind"
    // 阿里云 实人认证 获取token
    const val VERIFY_RP_BASIC_TOKEN = "faveVerify/verifyRPBasicToken"
    // 阿里云 实人认证 认证成功回调
    const val VERIFY_RP_BASIC_BACK = "faveVerify/verifyRPBasicBack"
    // 修改个人信息
    const val USERINFO_UPSERINFO = "userinfo/upuserinfo "
    // 上传定位信息
    const val SERVICR_URL_UPLOAD_LOCATION = "userinfocollect/saveUserGps"
    // 首页 借款 点击按钮后 判断能否借款
    const val LOANCONFIRMAPPLY = "loan/loanconfirmapply"
    // 智能评估 获取商户号
    const val SERVICE_URL_XIN_YAN_TOKEN = "perfect/bmwXinYanPreOrder"
    // 保存用户指纹记录 1-h5  2-android  3-ios
    const val SAVE_FINGERPRINT = "userinfocollect/saveFingerprint"
    // 页面统计接口   终端类型 （1、ios 2、安卓 3、其他）
    const val PAGE = "statistic/page"
    // app报错信息记录
    const val ERROR = "statistic/error"
    //获取本人关系
    const val SERVICE_URL_GETREATION_KEY = "credit-card/get-contacts"
    // 上传通讯录 短信 app列表
    const val SERVICE_URL_UPDATA_INFO = "credit-info/up-load-contents"
    // 申请借款
    const val SERVICE_URL_REQUEST_LEND_KEY = "credit-loan/apply-loan"
    // 借款成功
    const val SERVICE_URL_GET_LOAN_ACHIEVE_LOAN = "credit-loan/achieve-loan"
    // 判断开户认证
    const val SERVICE_URL_GET_CONFIRM_LOAN_ZAI_GAI = "credit-loan/get-confirm-loan-zai-gai"
    // 获取开户地址接口
    const val SERVICE_URL_CREATE_ACCOUNT = "yindo/create-account"
    const val LOAN_SHOP_DETAILS = BASE_URL + "loan/record/loanShopDetails"
    // 账单(商城账单和现金账单合并)
    const val BILLS = "bill/stages/index"
    // 商城全部账单
    const val SHOP_ALL_BILLS = "bill/stages/allBill"
    // 现金账单
    const val CASH_BILLS = "cashBill/overview"
    // 分期详情
    const val STAGE_DETAILS = "bill/stages/stageDetails"
    // 账单详情(商城和现金通用)
    const val BILL_DETAILS = "bill/stages/billDetails"
    // 还款接口
    const val PAY_BACK_DETAILED = "repaypage/repaydetail"
    // 确认支付（预支付）
    const val PRE_PAY_BACK = "manualRepay/preRepay"
    // 确认支付
    const val CONFIRM_REPAY = "manualRepay/confirmRepay"
    /**  ↑↑↑↑java接口相关↑↑↑↑ **/

    /**  ↓↓↓↓协议接口相关↓↓↓↓ **/
    // 《小银号网服务协议》
    const val AGREEMENT_USER_REISTER_PROTOCOL = BaseConfig.BASE_IP_AGREEMENT + "protocol/user-register-protocol"
    // 《借款协议》
    const val AGREEMENT_PLATFORM_SERVICE = BaseConfig.BASE_IP_AGREEMENT + "protocol/platform-service"
    // 《借款咨询服务协议》
    const val AGREEMENT_LOAN_COUNSELING = BaseConfig.BASE_IP_AGREEMENT + "protocol/loan-counseling"
    // 《个人消费借款合同》
    const val AGREEMENT_INDIVIDUAL_CONSUMPTION = BaseConfig.BASE_IP_AGREEMENT + "protocol/individual-consumption-loan-contract"
    // 《平台服务协议》
    const val AGREEMENT_USER_SERVICE_PROTOCOL = BaseConfig.BASE_IP_AGREEMENT + "protocol/user-service-protocol"
    // 《授权扣款 委托书》
    const val AGREEMENT_ENTRUST_DEDUCTION_PROXY = BaseConfig.BASE_IP_AGREEMENT + "protocol/entrust-deduction-proxy"
    // 《VIP会员增值服务协议》
    const val VIP_PROXY = BaseConfig.BASE_IP_AGREEMENT + "protocol/addService"
    // 《电子签章及存证授权委托书》
    const val AGREEMENT_ELECTRONIC_SIGNATURE_AND_EXISTING_EVIDENCE_PROXY = BaseConfig.BASE_IP_AGREEMENT + "protocol/electronic-signature-and-existing-evidence-proxy"
    // 《征信查询授权书》
    const val AGREEMENT_CREDIT_QUERY_PROXY = BaseConfig.BASE_IP_AGREEMENT + "protocol/credit-query-proxy"
    // 《个人信息使用和第三方机构数据查询授权书》
    const val AGREEMENT_PERSONAL_INFO_AND_DATA_QUERY_PROXY = BaseConfig.BASE_IP_AGREEMENT + "protocol/personal-info-and-data-query-proxy"
    // 《运营商授权协议》
    const val AGREEMENT_OPERATOR_AUTHORIZATION_PROTOCOL = BaseConfig.BASE_IP_AGREEMENT + "protocol/operator-authorization-protocol"
    // 《隐私协议》
    const val AGREEMENT_PRIVACY_PROTOCOL = BaseConfig.BASE_IP_AGREEMENT + "protocol/privacy-protocol"
    //《信用服务协议》
    const val AGREEMENT_SHOP_CREDIT = BaseConfig.BASE_IP_AGREEMENT + "protocol/credit-service-protocol"
    // 《失信风险警示》
    const val AGREEMENT_LOST_LETTER = BaseConfig.BASE_IP_AGREEMENT + "protocol/breakPromise-protocol"
    // 帮助中心
    const val HELP_CENTER = BaseConfig.BASE_IP_AGREEMENT + "helpCenter/helpCenter"
    // VIP详情页
    const val VIP_DETAIL = BaseConfig.BASE_IP_AGREEMENT + "vip/vipdetail"
    /**  ↑↑↑↑协议接口相关↑↑↑↑ **/
}
