package com.yinduowang.installment.mvp.model.service

import com.yinduowang.installment.app.constant.Api
import com.yinduowang.installment.app.constant.Api.FLOATCLICKAREA
import com.yinduowang.installment.app.constant.Api.MARKET_INDEX
import com.yinduowang.installment.mvp.model.entity.*
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface NewLoanService {
    @FormUrlEncoded
    @POST(Api.LOAN_INDEX)
    fun getLoan(@Field("userToken") token: String): Observable<BaseResponse<NewLoanEntity>>

    @FormUrlEncoded
    @POST(Api.SIGN_CONTRACT)
    fun getSignContract(@Field("token") token: String,
                        @Field("sign") sign: String, @Field("id") id: String): Observable<BaseResponse<Any>>
    @FormUrlEncoded
    @POST(Api.CONFIRM_LOAN)
    fun confirmApplyLoan(@FieldMap() hashMap: HashMap<String,String> ,@Field("token") token: String,
                         @Field("sign") sign: String
    ): Observable<BaseResponse<ConfirmEntity>>
    @FormUrlEncoded
    @POST(Api.CONFIRM_APPLY_BUTTOM)
    fun confirmApply(@Field("userToken") token: String, @Field("loanMoney") loanFund: String): Observable<BaseResponse<Any>>

    @FormUrlEncoded
    @POST(Api.MARKET_INDEX)
    fun getLoanBanner(@Field("page") page: String, @Field("ac_type") ac_type: String, @Field("token") token: String): Observable<LoanBannerEntity>
    @FormUrlEncoded
    @POST(Api.LOAN_APPLY_PAGE)
    fun getLoanApplyPage(@Field("token") token: String, @Field("loanFund") loanFund: String, @Field("sign") sign: String): Observable<BaseResponse<ConfirmLoanEntity>>
    @FormUrlEncoded
    @POST(Api.LOAN_APPLY_PAGE)
    fun getBankCard(@Field("token") token: String,@Field("sign") sign: String): Observable<BaseResponse<ConfirmLoanEntity>>
    @FormUrlEncoded
    @POST(Api.LOAN_RECORD_LIST)
    fun getLoanRecordList(@Field("id") id: Int,@Field("loadType") loadType: String,@Field("token")
    token: String,@Field("sign") sign: String): Observable<BaseResponse<LoanRecordEntity>>

    @FormUrlEncoded
    @POST(Api.CASH_LOAN_DETAILED)
    fun getCashLoanDetailed(@Field("id") id: Int,@Field("token") token: String,@Field("sign") sign: String):
            Observable<BaseResponse<CashLoanRecordEntity>>
    @FormUrlEncoded
    @POST(Api.RAY_BACK_RECORD)
    fun getPayBackRecord(@Field("id") id: String,@Field("type") type:String,@Field("token") token: String,@Field("sign") sign: String):
            Observable<BaseResponse<PayBackRecordEntity>>

    //贷款超市产品列表
    @FormUrlEncoded
    @POST(MARKET_INDEX)
    fun getFloatDetailedList(@Field("page") page: String, @Field("ac_type") ac_type: String, @Field("token") token: String, @Field("sign") sign: String): Observable<LoanBannerEntity>

    // ac_type 1首页点击 2还款页点击
    @FormUrlEncoded
    @POST(FLOATCLICKAREA)
    fun onFloatDetailedAreaClick(@Field("token") token: String,
                                 @Field("source_id") sourceId: String,
                                 @Field("ac_type") ac_type: String,
                                 @Field("type") type: String,
                                 @Field("ip") ip: String): Observable<BaseResponse<Any>>
}

