package com.yinduowang.installment.mvp.model.service

import com.yinduowang.installment.app.constant.Api
import com.yinduowang.installment.mvp.model.entity.*
import io.reactivex.Observable
import retrofit2.http.*

interface ShopService {

    @GET(Api.GET_SHOP_ORDER_RECORD)
    fun getShopOrderRecord(@Query("limit") limit: String, @Query("page") page: String,
                           @Query("status") status: String, @Query("token") token: String, @Query("sign") sign: String
    ): Observable<BasePhpResponse<ShopOrderEntity>>


    //    退换售后
    @GET(Api.GET_SHOP_ORDER_RECORD)
    fun getAfterOrder(@Query("limit") limit: String, @Query("page") page: String,
                      @Query("retreat_status") status: String, @Query("token") token: String, @Query("sign") sign: String
    ): Observable<BasePhpResponse<ShopOrderEntity>>


    @GET(Api.GET_SHOP_ORDER_DETAILED)
    fun getShopOrderDetailed(@Query("o_id") o_id: String, @Query("token") token: String,
                             @Query("sign") sign: String
    ): Observable<BasePhpResponse<ShopOrderDetailedEntity>>

    @FormUrlEncoded
    @POST(Api.CANCEL_SHOP_ORDER)
    fun cancelShopOrder(@Field("o_id") o_id: String, @Field("token") token: String,
                        @Field("sign") sign: String
    ): Observable<BasePhpResponse<Any>>

    @FormUrlEncoded
    @POST(Api.ORDER_RETREAT)
    fun orderRetreat(@Field("o_id") o_id: String, @Field("token") token: String,
                     @Field("sign") sign: String
    ): Observable<BasePhpResponse<Any>>

    //    确认订单
    @FormUrlEncoded
    @POST(Api.BUY)
    fun shopBuy(@Field("price") price: String, @Field("g_id") g_id: String, @Field("af_id") af_id: String,
                @Field("as_id") as_id: String, @Field("number") number: String, @Field("token") token: String, @Field("ma_id") ma_id: String, @Field("sign") sign: String
    ): Observable<BasePhpResponse<ConfirmationOrder>>

    //获取订单邮费
    @GET(Api.BUY)
    fun getShopBuy(@Query("price") price: String, @Query("g_id") g_id: String, @Query("af_id") af_id: String,
                   @Query("as_id") as_id: String, @Query("number") number: String, @Query("token") token: String, @Query("ma_id") ma_id: String,@Query("cycle") cycle: String,  @Query("sign") sign: String
    ): Observable<BasePhpResponse<OrderFee>>

    //确认收货
    @FormUrlEncoded
    @POST(Api.CONFIRMN_RECEIPT)
    fun confirmReceipt(@Field("o_id") o_id: String, @Field("token") token: String,
                       @Field("sign") sign: String
    ): Observable<BasePhpResponse<Any>>

    //    获取订单信息
    @GET(Api.ORDER_PAY)
    fun getShopDetailForPay(@Query("o_id") o_id: String, @Query("cycle") cycle: String, @Query("token") token: String,
                            @Query("sign") sign: String
    ): Observable<BasePhpResponse<GetOrderPay>>

    //    支付订单
    @FormUrlEncoded
    @POST(Api.ORDER_PAY)
    fun postPay(@Field("o_id") o_id: String, @Field("token") token: String, @Field("total_money") total_money: String, @Field("cycle") cycle: String, @Field("password") password: String, @Field("config_id") config_id: String,
                @Field("sign") sign: String
    ): Observable<BasePhpResponse<Any>>


    //    获取物流信息
    @GET(Api.LOGISTICS)
    fun logisticsDetail(@Query("o_id") o_id: String, @Query("token") token: String,
                        @Query("sign") sign: String
    ): Observable<BasePhpResponse<GetLogisticsEntity>>

    //    确认订单（额度验证）
    @FormUrlEncoded
    @POST(Api.CHECK_QUOTA)
    fun checkQuota(@Field("token") token: String, @Field("sign") sign: String): Observable<BaseResponse<CheckQuotaEntity>>
}