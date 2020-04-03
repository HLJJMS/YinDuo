package com.yinduowang.installment.mvp.model.service

import com.yinduowang.installment.app.constant.Api
import com.yinduowang.installment.mvp.model.entity.BaseResponse
import com.yinduowang.installment.mvp.model.entity.ConfirmRepayBean
import com.yinduowang.installment.mvp.model.entity.PayBackMoney
import com.yinduowang.installment.mvp.model.entity.PreRepayBack
import io.reactivex.Observable
import retrofit2.http.*

interface NewRepaymentService {

    @FormUrlEncoded
    @POST(Api.PAY_BACK_DETAILED)
    fun getPayBackDetailed(@Field("token") token: String, @Field("sign") sign: String, @FieldMap() map: HashMap<String, String>): Observable<BaseResponse<PayBackMoney>>

    @FormUrlEncoded
    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
    @POST(Api.PRE_PAY_BACK)
    fun preRepayBack(@FieldMap params: java.util.HashMap<String, String>): Observable<BaseResponse<PreRepayBack>>


    @FormUrlEncoded
    @POST(Api.CONFIRM_REPAY)
    fun confirmRepay(@Field("uniqueCode") uniqueCode: String, @Field("repayFund") repayFund: String, @Field("token") token: String, @Field("sign") sign: String): Observable<BaseResponse<ConfirmRepayBean>>

}