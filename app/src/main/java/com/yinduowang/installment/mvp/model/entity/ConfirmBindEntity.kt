package com.yinduowang.installment.mvp.model.entity

data class ConfirmBindEntity(
    val msg: String,
    val status: Int//处理状态 1、处理中 2、失败 3、成功 4、需要继续绑定其他渠道（再次调用预绑定接口）
)