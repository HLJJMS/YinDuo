package com.yinduowang.installment.mvp.model.entity

data class BankCardListEntity(
        val bankCardSum: String,
        val stateType: String,
        val userBanks: List<UserBank>
)

data class UserBank(
        val bankName: String,
        val bankCard: String,
        val startCardNo: String,
        val endCardNo: String,
        val defaultCard: String//1：默认，其他非默认
        ,val id: String

)