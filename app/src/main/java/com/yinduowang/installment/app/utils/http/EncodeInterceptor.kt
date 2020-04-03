package com.yinduowang.installment.app.utils.http

import okhttp3.Interceptor
import okhttp3.Response


class EncodeInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
                .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8")
                .build()
        return chain.proceed(request)
    }
}