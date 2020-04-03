package com.yinduowang.installment.app.constant

/**
 * 接口业务流程统计
 * */
object Statistics {
    // 打开app登陆（登录/注册）
    val app_login_register_event = "app_login_register_event"
    // 登录页
    val app_login_event = "app_login_event"
    // 注册页
    val app_register_event = "app_register_event"
    // 进入认证中心
    val app_ac_event = "app_ac_event"
    // 点击“个人信息”按钮
    val app_ac_pi_click = "app_ac_pi_click"
    // 点击“紧急联系人”按钮
    val app_ac_ec_click = "app_ac_ec_click"
    // 点击“手机运营商”按钮
    val app_ac_mp_click = "app_ac_mp_click"
    // 点击“查看借款额度”
    val app_borrowingList_click = "app_borrowingList_click"
    // 银行认证弹窗
    val app_bc_event = "app_bc_event"
    // 点击“去认证”按钮
    val app_bc_go_click = "app_bc_go_click"
    // 点击“确认授权”按钮
    val app_bc_auth_click = "app_bc_auth_click"
    // 授权成功页面
    val app_bc_succeed_event = "app_bc_succeed_event"
    // 授权失败页面
    val app_bc_faild_event = "app_bc_faild_event"
    // 设置交易密码页面
    val app_tp_event = "app_tp_event"
    // 确认交易密码页面
    val app_tp_ok_click = "app_tp_ok_click"
    // 进入取现页面
    val app_bm_event = "app_bm_event"
    // 点击“确认申请”按钮
    val app_bm_apply_click = "app_bm_apply_click"
    // 输入交易密码弹窗
    val app_bm_tp_event = "app_bm_tp_event"
    // 借款成功页面
    val app_bm_succeed_event = "app_bm_succeed_event"
    // 借款失败页面
    val app_bm_faild_event = "app_bm_faild_event"
    // 点击立即借钱按钮
    val app_home_borrowMoney_click = "app_home_borrowMoney_click"
    // 立即借钱失败
    val app_home_borrowMoney_faild_event = "app_home_borrowMoney_faild_event"
    // 还款再次申请页面
    val app_home_show_from_repayment = "app_home_show_from_repayment"
}