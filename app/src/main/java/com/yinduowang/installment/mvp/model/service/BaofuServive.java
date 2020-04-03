package com.yinduowang.installment.mvp.model.service;

import com.yinduowang.installment.app.constant.Api;
import com.yinduowang.installment.mvp.model.entity.BaofuWithholdBeanNew;
import com.yinduowang.installment.mvp.model.entity.BaseResponse;
import com.yinduowang.installment.mvp.model.entity.PreBindBean;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface BaofuServive {


    @FormUrlEncoded
    @POST(Api.PRIBIND)
    Observable<BaseResponse<PreBindBean>> getCode(
            @Field("bankCardNo") String cardno,
            @Field("mobileNo") String mobileno,
            @Field("sign") String sign,
            @Field("token") String token);


    @FormUrlEncoded
    @POST(Api.GETBANKCARD_NEW)
    Observable<BaseResponse<BaofuWithholdBeanNew>> getDate(@Field("sign") String sign, @Field("token") String token);

    @FormUrlEncoded
    @POST(Api.CONFIRMBIND)
    Observable<BaseResponse<Map<String, String>>> submit(@Field("sign") String sign, @Field("token") String token,
                                                         @Field("uniqueCode") String uniqueCode, @Field("authCode") String authCode);

}
