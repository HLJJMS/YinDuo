package com.yinduowang.installment.mvp.model.service;

import com.yinduowang.installment.app.constant.Api;
import com.yinduowang.installment.mvp.model.entity.BaseResponse;
import com.yinduowang.installment.mvp.model.entity.PhoneOperatorBean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Tsing
 * on 2019/3/6
 */
public interface PhoneOperatorService {
    @FormUrlEncoded
    @POST(Api.MOBILE_OPERATOR_CERTIFICATION)
    Observable<BaseResponse<PhoneOperatorBean>> ensureAuth(@Field("token") String token, @Field("sign") String sign, @Field("id") String id);
    @FormUrlEncoded
    @POST(Api.AUTHCODEAGAIN)
    Observable<BaseResponse> authcodeAgain(@Field("token") String token, @Field("sign") String sign);

    @FormUrlEncoded
    @POST(Api.CERTIFICATIONCODE)
    Observable<BaseResponse<PhoneOperatorBean>> certificationCode(@Field("token") String token, @Field("sign") String sign, @Field("processcode") String processcode, @Field("code") String code);
}
