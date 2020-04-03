package com.yinduowang.installment.mvp.model.service

import com.yinduowang.installment.app.constant.Api
import com.yinduowang.installment.mvp.model.entity.AuthEmergencyContactEntity
import com.yinduowang.installment.mvp.model.entity.BaseResponse
import com.yinduowang.installment.mvp.model.entity.PerfectEntity
import com.yinduowang.installment.mvp.model.entity.UserInfoAllEntity
import io.reactivex.Observable
import retrofit2.http.*

interface AuthenticationCenterService {

    @FormUrlEncoded
    @POST(Api.PERFECT_NEW)
    fun perfect(@Field("token") token: String,
                @Field("sign") sign: String
    ): Observable<BaseResponse<PerfectEntity>>


    @FormUrlEncoded
    @POST(Api.GET_USER_INFO_ALL)
    fun getUserInfoAll(@Field("token") token: String,
                       @Field("sign") sign: String
    ): Observable<BaseResponse<UserInfoAllEntity>>

    // 阿里云实人认证 获取RPBasicToken
    @FormUrlEncoded
    @POST(Api.VERIFY_RP_BASIC_TOKEN)
    fun getVerifyRPBasicToken(@Field("token") token: String, @Field("sign") sign: String): Observable<BaseResponse<Map<String, String>>>

    // 阿里云实人认证 获取RPBasicToken
    @FormUrlEncoded
    @POST(Api.VERIFY_RP_BASIC_BACK)
    fun callbackVerifyRPBasicToken(@Field("token") token: String, @Field("sign") sign: String, @Field("aliyunToken") aliyunToken: String): Observable<BaseResponse<Map<String, String>>>

    // 修改个人信息j
    @FormUrlEncoded
    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
    @POST(Api.USERINFO_UPSERINFO)
    fun upadateUserinfo(@Field("token") token: String, @Field("sign") sign: String, @FieldMap hashMap: HashMap<String,String>): Observable<BaseResponse<Any>>

    @FormUrlEncoded
    @POST(Api.CONTACTS_ALL)
    fun getData(@Field("token") token: String, @Field("sign") sign: String): Observable<BaseResponse<AuthEmergencyContactEntity>>

    @FormUrlEncoded
    @POST(Api.UPDATE)
    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
    fun saveContacts(@FieldMap hashMap: HashMap<String, String>, @Field("token") token: String, @Field("sign") sign: String): Observable<BaseResponse<Any>>


    @FormUrlEncoded
    @POST(Api.OPEN_ACCOUNT)
    fun openAccount(@Field("token") token: String, @Field("sign") sign: String): Observable<BaseResponse<Map<String, String>>>


    @FormUrlEncoded
    @POST(Api.GET_QUOTA)
    fun getQuota(@Field("token") token: String, @Field("sign") sign: String): Observable<BaseResponse<Any>>
}