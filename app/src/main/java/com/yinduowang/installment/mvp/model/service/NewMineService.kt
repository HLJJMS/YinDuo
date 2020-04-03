package com.yinduowang.installment.mvp.model.service

import com.yinduowang.installment.app.constant.Api
import com.yinduowang.installment.mvp.model.entity.BaseResponse
import com.yinduowang.installment.mvp.model.entity.MessageCenterEntity
import com.yinduowang.installment.mvp.model.entity.MineEntity
import com.yinduowang.installment.mvp.model.entity.QuotaEntity
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface NewMineService {

    @FormUrlEncoded
    @POST(Api.USERINOF_MY_INFO)
    fun myInfo(@Field("token") token: String, @Field("sign") sign: String): Observable<BaseResponse<MineEntity>>

    @FormUrlEncoded
    @POST(Api.GET_MESSAGE_CENTER)
    fun getMessageCenter(@Field("page") page: Int, @Field("releaseTime") releaseTime: String, @Field("token") token: String, @Field("sign") sign: String): Observable<BaseResponse<MessageCenterEntity>>
    @FormUrlEncoded
    @POST(Api.GET_MY_QUOTA)
    fun getMyQuota(@Field("token") token: String,@Field("sign") sign: String): Observable<BaseResponse<QuotaEntity>>
}