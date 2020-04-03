package com.yinduowang.installment.mvp.model.service

import com.yinduowang.installment.app.constant.Api
import com.yinduowang.installment.mvp.model.entity.BaseResponse
import com.yinduowang.installment.mvp.model.entity.LoanShopDetailsEntity
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface LoanShopDetailsService {
    @FormUrlEncoded
    @POST(Api.LOAN_SHOP_DETAILS)
    fun getData(@Field("token") token: String, @Field("id") id: String, @Field("sign") sign: String): Observable<BaseResponse<LoanShopDetailsEntity>>
}