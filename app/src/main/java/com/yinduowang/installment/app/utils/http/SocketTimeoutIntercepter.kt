package com.yinduowang.installment.app.utils.http

import java.io.IOException
import java.util.concurrent.TimeUnit

import okhttp3.Interceptor
import okhttp3.Response

import com.yinduowang.installment.app.constant.Api.AUTHCODEAGAIN
import com.yinduowang.installment.app.constant.Api.CERTIFICATIONCODE
import com.yinduowang.installment.app.constant.Api.MOBILE_OPERATOR_CERTIFICATION
/**
 * 为特定接口 修改超时时间 拦截器
 * */
class SocketTimeoutIntercepter : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var chain = chain

        val request = chain.request()
        // 手机运营商认证  流程码确认 接口 连接超时时间为120秒
        if (request.url().encodedPath().contains(CERTIFICATIONCODE) || request.url().encodedPath().contains(MOBILE_OPERATOR_CERTIFICATION) || request.url().encodedPath().contains(AUTHCODEAGAIN)) {
            chain = chain.withConnectTimeout(120000, TimeUnit.MILLISECONDS)
            chain = chain.withReadTimeout(120000, TimeUnit.MILLISECONDS)
            chain = chain.withWriteTimeout(120000, TimeUnit.MILLISECONDS)
        }
        return chain.proceed(request)
    }
}
