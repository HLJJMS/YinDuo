package com.yinduowang.installment.mvp.model.entity

data class PerfectEntity(
        val accountOpenStatus: String?,// 开户状态0已完善，-1未完善
        val bankCardStatus: String?,// 收款银行卡是否完善0已完善，-1未完善
        val bankStatus: String?,// 银行是否完善 0已完善，-1未完善 1去认证
        val contactStatus: String?,// 紧急联系人是否完善 0已完善，-1未完善 1去认证
        val creditCeiling: String?,// 信贷额度
//        1.1废弃
//        val operatorStatus: String?,// 手机运营商是否设置 0已完善，-1未完善 1去认证
        val userInfoStatus: String?,// 个人信息是否完善 0已完善，-1未完善 1去认证
        val isAcquired: String?,// 是否点击获取额度按钮，0未获取，1以获取
        val creditCeilingType: String?// 0 未获取到额度,1 获取到
)

