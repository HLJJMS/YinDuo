package com.yinduowang.installment.mvp.model.service

import com.yinduowang.installment.app.constant.Api
import com.yinduowang.installment.mvp.model.entity.*
import io.reactivex.Observable
import retrofit2.http.*

interface NewSettingService {

    @FormUrlEncoded
    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
    @POST(Api.FEED_BACK)
    fun submitFeedBack(@Field("token") token: String,
                       @Field("sign") sign: String,
                       @Field("content") content: String): Observable<BaseResponse<Any>>

    @FormUrlEncoded
    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
    @POST(Api.COLLECTION_COMPLAINS)
    fun collectionComplaint(@Field("token") token: String,
                            @Field("sign") sign: String, @Field("typeId") typeId: String,
                            @Field("content") content: String): Observable<BaseResponse<Any>>

    @GET(Api.GET_COLLECTION_TYPE)
    fun getCollectionType(): Observable<BaseResponse<CollectionEntity>>

    @FormUrlEncoded
    @POST(Api.CHANGE_PASSWORD)
    fun changePwd(@Field("token") token: String,
                  @Field("oldpw") oldpw: String,
                  @Field("newpw") newpw: String,
                  @Field("newpwagain") newpwagain: String,
                  @Field("sign") sign: String): Observable<BaseResponse<Any>>

    @FormUrlEncoded
    @POST(Api.VALIDATE_PAY_PASSWORD_SET)
    fun validatePayPswSet(@Field("token") token: String,
                          @Field("sign") sign: String): Observable<BaseResponse<Any>>

    @FormUrlEncoded
    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
    @POST(Api.FORGET_PAY_PASSWORD)
    fun forgetPayPsw(@Field("mobile") mobile: String,
                     @Field("authCode") authCode: String,
                     @Field("idCard") idCard: String,
                     @Field("realName") realName: String,
                     @Field("token") token: String,
                     @Field("sign") sign: String
    ): Observable<BaseResponse<Any>>


    @FormUrlEncoded
    @POST(Api.VALIDATE_PAY_PASSWORD)
    fun changePayPsw(@Field("token") token: String,
                     @Field("sign") sign: String,
                     @Field("oldpw") oldpw: String): Observable<BaseResponse<Any>>

    @FormUrlEncoded
    @POST(Api.SET_PAY_PASSWORD)
    fun setPayPsw(@Field("newpw") newpw: String,
                  @Field("againnewpw") againnewpw: String,
                  @Field("token") token: String,
                  @Field("sign") sign: String

    ): Observable<BaseResponse<Any>>

    @FormUrlEncoded
    @POST(Api.CHANGE_PAY_PASSWORD)
    fun setPayOldPsw(@Field("newpw") newpw: String,
                     @Field("againnewpw") againnewpw: String,
                     @Field("token") token: String,
                     @Field("sign") sign: String

    ): Observable<BaseResponse<Any>>
    @GET(Api.DELIVERY_ADDRESS_LIST)
    fun getDeliveryAddressList(@Query("token") token: String,
                               @Query("sign") sign: String
    ): Observable<BasePhpResponse<DeliveryAddressEntity>>

    @FormUrlEncoded
    @POST(Api.LOGIN_OUT)
    fun loginOut(@Field("token") token: String,
                 @Field("sign") sign: String
    ): Observable<BaseResponse<Any>>


    @FormUrlEncoded
    @POST(Api.DETAIL_ADDRESS)
    fun delAddress(@Field("token") token: String, @Field("id") id: String,
                   @Field("sign") sign: String
    ): Observable<BasePhpResponse<Any>>


    @FormUrlEncoded
    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
    @POST(Api.CHANGE_DELIVER_ADDRESS)
    fun changeOraddDeliveryAddress(@Field("id") id: String,

                                   @Field("province") province: String,
                                   @Field("city") city: String,
                                   @Field("area") area: String,
                                   @Field("address") address: String,
                                   @Field("mobile") mobile: String,
                                   @Field("name") name: String,
                                   @Field("is_default") is_default: String,
                                   @Field("token") token: String,
                                   @Field("sign") sign: String): Observable<BasePhpResponse<Any>>

    @GET(Api.CHANGE_DELIVER_ADDRESS)
    fun getDeliveryAddress(@Query("id") id: String, @Query("token") token: String,
                           @Query("sign") sign: String
    ): Observable<BasePhpResponse<DeliveryAddress>>
}
