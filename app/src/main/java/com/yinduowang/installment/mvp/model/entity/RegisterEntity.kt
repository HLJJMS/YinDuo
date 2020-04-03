package com.yinduowang.installment.mvp.model.entity

data class RegisterEntity(
        val authCodeMsg: String,
        val isAuthCodeCorrect: Int,
        val isRegister: Int,
        val nickName: String,
        val refreshToken: String,
        val refreshTokenExpire: String,
        val registerMsg: String,
        val token: String,
        val tokenExpire: String,
        val userId: String,
        val vipPopStatus: String,
        val vipStatus: String,
        val vipText: String
)