package com.yinduowang.installment.mvp.model.service;

import com.yinduowang.installment.app.constant.Api;
import com.yinduowang.installment.mvp.model.entity.AssessmentBean;
import com.yinduowang.installment.mvp.model.entity.AuthEmergencyBean;
import com.yinduowang.installment.mvp.model.entity.BannerBean;
import com.yinduowang.installment.mvp.model.entity.BaseResponse;
import com.yinduowang.installment.mvp.model.entity.CreateAccountBean;
import com.yinduowang.installment.mvp.model.entity.FloatButtonBean;
import com.yinduowang.installment.mvp.model.entity.FloatDetailedBean;
import com.yinduowang.installment.mvp.model.entity.GetRelationResponseBean;
import com.yinduowang.installment.mvp.model.entity.LoanApplyDetailsEntity;
import com.yinduowang.installment.mvp.model.entity.LoanConfirmApplyBean;
import com.yinduowang.installment.mvp.model.entity.LoanEntity;
import com.yinduowang.installment.mvp.model.entity.LoanSuccessBean;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by Tsing
 * on 2019/1/23
 */
public interface UploadLocationService {

    @FormUrlEncoded
    @POST(Api.SERVICR_URL_UPLOAD_LOCATION)
    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
    Observable<BaseResponse> uploadLocation(@Field("longitude") String longitude,
                                            @Field("latitude") String latitude,
                                            @Field("address") String address,
                                            @Field("client_type") String client_type,
                                            @Field("device_info") String device_info,
                                            @Field("app_version") String app_version,
                                            @Field("token") String token,
                                            @Field("sign") String sign);

    @GET(Api.SERVICE_URL_GETREATION_KEY)
    Observable<BaseResponse<GetRelationResponseBean>> getRelation();

    @FormUrlEncoded
    @POST(Api.CONTACTS_ALL)
    Observable<BaseResponse<AuthEmergencyBean>> getRelation(@Field("token") String token, @Field("sign") String sign);

    @FormUrlEncoded
    @POST(Api.SERVICE_URL_UPDATA_INFO)
    Observable<BaseResponse> uploadMsmToServer(@Field("data") String data, @Field("isAuthentication") String type);

    @FormUrlEncoded
    @POST(Api.GETUSERAPP)
    Observable<BaseResponse> upAppToServer(@Field("info") String info, @Field("token") String token, @Field("sign") String sign);

    @FormUrlEncoded
    @POST(Api.SAVEADDRESSBOOK)
    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
    Observable<BaseResponse> uploadAddressBookToServer(@Field("info") String info, @Field("token") String token, @Field("sign") String sign);

    @FormUrlEncoded
    @POST(Api.SAVEUSERSMS)
    Observable<BaseResponse> uploadMsmToServer(@Field("info") String info, @Field("token") String token, @Field("sign") String sign);

    @FormUrlEncoded
    @POST(Api.UPDATE)
    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
    Observable<BaseResponse> saveContacts(@Field("familyMobile") String mobile, @Field("familyName") String name,
                                          @Field("familyType") String type, @Field("otherType") String relation_spare,
                                          @Field("otherMobile") String mobile_spare, @Field("otherName") String name_spare, @Field("token") String token, @Field("sign") String sign);

    @FormUrlEncoded
    @POST(Api.LOAN_INDEX)
    Observable<BaseResponse<LoanEntity>> getLoan(@Field("userToken") String token);

    @FormUrlEncoded
    @POST(Api.LOAN_APPLY_DETAILS)
    Observable<BaseResponse<LoanApplyDetailsEntity>> loanApplyDetails(@Field("token") String token,
                                                                      @Field("sign") String sign,
                                                                      @Field("amount") String amount,
                                                                      @Field("loanConfigId") String loanConfigId);


    /**
     * 1安卓 2ios 3pc
     */
    @FormUrlEncoded
    @POST(Api.LOANCONFIRMAPPLY)
    Observable<BaseResponse<LoanConfirmApplyBean>> loanConfirmApply(
            @Field("token") String token,
            @Field("sign") String sign,
            @Field("loanConfigId") String loanConfigId,
            @Field("password") String pwd,
            @Field("loanAmount") String loanAmount,
            @Field("applyAddress") String applyAddress,
            @Field("applyAppid") String applyAppid);


    @GET(Api.GETBANNERS)
    Observable<BaseResponse<List<BannerBean>>> getBanner();

    @FormUrlEncoded
    @POST(Api.LOAN_BORROW_BBUTTON)
    Observable<BaseResponse<Map<String, String>>> verifyLoan(@Field("token") String token, @Field("sign") String sign, @Field("applyFund") String applyFund, @Field("loanConfigId") String loanConfigId);

    /**
     * @param coupon_id   没有默认传0
     * @param order_other 没有默认传0
     */
    @FormUrlEncoded
    @POST(Api.SERVICE_URL_REQUEST_LEND_KEY)
    Observable<BaseResponse> lendRequest(
            @Field("period") String period,
            @Field("money") String money,
            @Field("card_type") String card_type,
            @Field("pay_password") String pay_password,
            @Field("coupon_id") String coupon_id,
            @Field("order_other") String order_other
    );

    @FormUrlEncoded
    @POST(Api.SERVICE_URL_GET_LOAN_ACHIEVE_LOAN)
    Observable<BaseResponse<LoanSuccessBean>> applySuccess(@Field("order_id") String order_id);

    @POST(Api.SERVICE_URL_GET_CONFIRM_LOAN_ZAI_GAI)
    Observable<BaseResponse> getConfirmLoanZaiGai();

    @POST(Api.SERVICE_URL_CREATE_ACCOUNT)
    Observable<CreateAccountBean> createAccount();

    @FormUrlEncoded
    @POST(Api.SERVICE_URL_XIN_YAN_TOKEN)
    Observable<BaseResponse<AssessmentBean>> getAssessmentResult(@Field("productType") String product_type, @Field("token") String token, @Field("sign") String sign);

    @FormUrlEncoded
    @POST(Api.SAVE_FINGERPRINT)
    Observable<BaseResponse> saveFingerprint(
            @Field("fingerToken") String fingerToken,
            @Field("platformType") String platformType,
            @Field("token") String token,
            @Field("sign") String sign);

    @FormUrlEncoded
    @POST(Api.PAGE)
    Observable<BaseResponse> savePage(
            @Field("pageName") String pageName,
            @Field("clientType") String clientType,
            @Field("token") String token,
            @Field("deviceId") String deviceId);

    @FormUrlEncoded
    @POST(Api.ERROR)
    Observable<BaseResponse> saveError(@Field("errorInfo") String errorInfo, @Field("token") String token);

    //引流悬浮窗是否展示
    @FormUrlEncoded
    @POST(Api.FLOATBUTTONSTATUS)
    Observable<FloatButtonBean> getFloatButtonStatus(@Field("token") String token);

    //贷款超市产品列表
    @FormUrlEncoded
    @POST(Api.FLOATBUTTONLIST)
    Observable<FloatDetailedBean> getFloatDetailedList(@Field("page") int page, @Field("token") String token, @Field("ac_type") String ac_type);

    // ac_type 1首页点击 2还款页点击
    @FormUrlEncoded
    @POST(Api.FLOATCLICKAREA)
    Observable<BaseResponse> onFloatDetailedAreaClick(@Field("token") String token,
                                                      @Field("source_id") String sourceId,
                                                      @Field("ac_type") String ac_type,
                                                      @Field("isAuthentication") String type);

    //关闭提额弹窗
    @FormUrlEncoded
    @POST(Api.LOAN_LOAN_CLOSEJECT)
    Observable<BaseResponse> closEject(@Field("token") String token, @Field("sign") String sign);

    //还款界面拒绝点击申请埋点
    @FormUrlEncoded
    @POST(Api.FLOATBUTTONLIST)
    Observable<FloatDetailedBean> refusedOnClick(@Field("page") int page, @Field("token") String token, @Field("ac_type") String ac_type);
}
