package com.yinduowang.installment.mvp.model.entity

data class PreBindEntity(
    val message: String,
    val status: Int,// 预绑定状态 -1失败 1成功 ,
    val uniqueCode: String
)