package com.yinduowang.installment.mvp.model.service

import com.yinduowang.installment.app.constant.Api
import com.yinduowang.installment.mvp.model.entity.*

import io.reactivex.Observable
import retrofit2.http.*


interface NewCashBillsService {
    @FormUrlEncoded
    @POST(Api.STAGE_DETAILS)
    fun getList(@Field("token") token: String, @Field("sign") sign: String, @Field("id") id: String): Observable<BaseResponse<NewCashInstalmentDetailEntity>>
    @FormUrlEncoded
    @POST(Api.BILLS)
    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
    fun getShopBills(@Field("token") token: String, @Field("sign") sign: String,
                     @Field("year") year: String, @Field("month") month: String,@Field("type") type: String,
                     @Field("flag") flag: String): Observable<BaseResponse<ShopBillsEntity>>

    @FormUrlEncoded
    @POST(Api.CASH_BILLS)
    fun getCashBills(@Field("token") token: String, @Field("sign") sign: String): Observable<BaseResponse<CashBillsEntity>>


    @FormUrlEncoded
    @POST(Api.BILL_DETAILS)
    fun getBillList(@Field("token") token: String, @Field("sign") sign: String,@Field("id") id: String,@Field("year") year: String,@Field("month") month: String,@Field("type") type: String): Observable<BaseResponse<NewCashBillListEntity>>

    @FormUrlEncoded
    @POST(Api.SHOP_ALL_BILLS)
    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
    fun getShopAllBills(@Field("token") token: String, @Field("sign") sign: String, @Field("year") year: String, @Field("flag") flag: String): Observable<BaseResponse<ShopAllBillsEntity>>
}
