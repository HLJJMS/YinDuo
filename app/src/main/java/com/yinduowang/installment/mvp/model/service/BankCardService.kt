package com.yinduowang.installment.mvp.model.service

import com.yinduowang.installment.app.constant.Api
import com.yinduowang.installment.mvp.model.entity.*
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface BankCardService {

    @FormUrlEncoded
    @POST(Api.GET_BANK_CARD)
    fun getBankCardList(@Field("bankCardId") bankCardId: String,
                        @Field("token") token: String,
                        @Field("sign") sign: String): Observable<BaseResponse<BankCardListEntity>>

    @FormUrlEncoded
    @POST(Api.GET_USER_INFO)
    fun getUserInfo(@Field("token") token: String,
                    @Field("sign") sign: String): Observable<BaseResponse<VerificationUserInfoEntity>>

    @FormUrlEncoded
    @POST(Api.PRE_BIND)
    fun preBind(@Field("token") token: String,
                @Field("sign") sign: String,
                @Field("bankCardNo") bankCardNo: String,// 银行卡号
                @Field("mobileNo") mobileNo: String// 手机号
    ): Observable<BaseResponse<PreBindEntity>>

    @FormUrlEncoded
    @POST(Api.CONFIRM_BIND)
    fun confirmBind(@Field("token") token: String,
                    @Field("sign") sign: String,
                    @Field("uniqueCode") bankCardNo: String,// 预签约唯一码
                    @Field("authCode") mobileNo: String// 验证码
    ): Observable<BaseResponse<ConfirmBindEntity>>

}
