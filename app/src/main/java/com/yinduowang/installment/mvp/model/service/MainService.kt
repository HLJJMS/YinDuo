package com.yinduowang.installment.mvp.model.service

import com.yinduowang.installment.app.constant.Api
import com.yinduowang.installment.mvp.model.entity.AppVersionEntity
import com.yinduowang.installment.mvp.model.entity.BaseResponse
import io.reactivex.Observable
import retrofit2.http.*

interface MainService {
    @GET(Api.APP_UPDATA)
    fun getAppUpdataInfo(@Query("id") id: String): Observable<BaseResponse<AppVersionEntity>>

    @FormUrlEncoded
    @POST(Api.IS_AVAILABLE)
    fun isAvailable(@Field("token") token: String, @Field("sign") sign: String): Observable<BaseResponse<Any>>

    @FormUrlEncoded
    @POST(Api.PROTOCOL_GET_LOGIN_BANNER_INFO)
    fun isTestVersion(
            @Field("mobile_type") mobile_type: String,
            @Field("channel") channel: String): Observable<BaseResponse<Map<String, String>>>
}

