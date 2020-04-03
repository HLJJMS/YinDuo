package com.yinduowang.installment.mvp.model.service

import com.yinduowang.installment.app.constant.Api
import com.yinduowang.installment.mvp.model.entity.*
import io.reactivex.Observable
import retrofit2.http.*

interface NewLoginRegisterService {

    @FormUrlEncoded
    @POST(Api.GET_VERIFY_CODE)
    fun getVerifyCode(@Field("mobile") mobile: String, @Field("type") type: String): Observable<BaseResponse<VerifyCodeEntity>>

    @FormUrlEncoded
    @POST(Api.REGISTER)
    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
    fun register(@Field("mobile") mobile: String, @Field("authCode") authCode: String, @Field("password") password: String,
                 @Field("ip") ip: String, @Field("gps") gps: String, @Field("phoneModel") phoneModel: String,
                 @Field("clientType") clientType: String): Observable<BaseResponse<RegisterEntity>>

    @FormUrlEncoded
    @POST(Api.VERIFY_LOGIN)
    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
    fun verifyLogin(@Field("mobile") mobile: String, @Field("authCode") authCode: String,
                    @Field("ip") ip: String, @Field("gps") gps: String, @Field("phoneModel") phoneModel: String): Observable<BaseResponse<NewLoginEntity>>

    @FormUrlEncoded
    @POST(Api.PASSWORD_LOGIN)
    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
    fun passwordLogin(@Field("mobile") mobile: String, @Field("password") password: String,
                      @Field("ip") ip: String, @Field("gps") gps: String, @Field("phoneModel") phoneModel: String): Observable<BaseResponse<NewLoginEntity>>

    @FormUrlEncoded
    @POST(Api.FIND_BACK_PASSWORD)
    fun findBackPassword(@Field("mobileNo") mobile: String, @Field("authCode") authCode: String, @Field("newpw") newpw: String): Observable<BaseResponse<Any>>

    @GET(Api.SERVICE_TEL)
    fun serviceTel(): Observable<BaseResponse<String>>


    @GET(Api.LOGIN_MESSAGE)
    fun getLoginMessage(): Observable<BaseResponse<LoginMessage>>

    @FormUrlEncoded
    @POST(Api.VIP_CLOSE_VIPPOP)
    fun closeVipPop(@Field("token") token: String, @Field("sign") sign: String): Observable<BaseResponse<Any>>

    @GET(Api.HOME_SHOPPING_MALL)
    fun getShoppingMallData(@Query("token") token: String): Observable<HomeShoppingMallEntity>
}
