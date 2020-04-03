package com.yinduowang.installment.mvp.presenter

import android.app.Application
import android.text.TextUtils
import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.http.imageloader.ImageLoader
import com.jess.arms.integration.AppManager
import com.jess.arms.mvp.BasePresenter
import com.yinduowang.installment.app.constant.Api
import com.yinduowang.installment.app.utils.RxUtils
import com.yinduowang.installment.app.utils.ToastUtils
import com.yinduowang.installment.mvp.contract.PersonalDetailContract
import com.yinduowang.installment.mvp.model.entity.BaseResponse
import com.yinduowang.installment.mvp.model.entity.UserInfoAllEntity
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber
import javax.inject.Inject


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/30/2019 09:00
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
class PersonalDetailPresenter
@Inject
constructor(model: PersonalDetailContract.Model, rootView: PersonalDetailContract.View) :
        BasePresenter<PersonalDetailContract.Model, PersonalDetailContract.View>(model, rootView) {
    @Inject
    lateinit var mErrorHandler: RxErrorHandler
    @Inject
    lateinit var mApplication: Application
    @Inject
    lateinit var mImageLoader: ImageLoader
    @Inject
    lateinit var mAppManager: AppManager


    override fun onDestroy() {
        super.onDestroy();
    }

    fun getUserInfoAll() {
        mModel.getUserInfoAll()
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(object : ErrorHandleSubscriber<BaseResponse<UserInfoAllEntity>>(mErrorHandler) {
                    override fun onNext(response: BaseResponse<UserInfoAllEntity>) {
                        if (TextUtils.equals(response.code, Api.RequestSuccess)) {
                            response.data?.let { it -> mRootView.getUserInfoAll(it) }
                        } else {
                            ToastUtils.makeText(mApplication, response.msg)
                        }
                    }
                })
    }

    fun getVerifyRPBasicToken() {
        mModel.getVerifyRPBasicToken()
                .compose(RxUtils.applySchedulers<BaseResponse<Map<String, String>>>(mRootView))
                .subscribe(object : ErrorHandleSubscriber<BaseResponse<Map<String, String>>>(mErrorHandler) {
                    override fun onNext(response: BaseResponse<Map<String, String>>) {
                        if (response.code == "200") {
                            response.data?.let { it ->
                                var token = it["aliyunToken"]
                                if (token != null) {
                                    if (token.isNotBlank() && token.isNotEmpty()) {
                                        mRootView.onVerifyRPBasicTokenResult(token)
                                    }
                                } else {
                                    ToastUtils.makeText(mApplication, response.msg)
                                }
                            }
                        } else {
                            ToastUtils.makeText(mApplication, response.msg)
                        }
                    }
                })
    }

    fun callbackVerifyRPBasic(aliyunToken: String) {
        mModel.callbackVerifyRPBasic(aliyunToken)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(object : ErrorHandleSubscriber<BaseResponse<Map<String, String>>>(mErrorHandler) {
                    override fun onNext(response: BaseResponse<Map<String, String>>) {
                        if (response.code == "200") {
                            response.data?.let { it ->
                                mRootView.callBackVerifyRPBasicResult(it)
                            }
                        } else {
                            ToastUtils.makeText(mApplication, response.msg)
                        }
                    }
                })
    }

    fun upadateUserinfo(map: HashMap<String, String>) {
        mModel.upadateUserinfo(map)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(object : ErrorHandleSubscriber<BaseResponse<Any>>(mErrorHandler) {
                    override fun onNext(response: BaseResponse<Any>) {
                        if (response.code == "200") {
                            mRootView.upadateUserinfoResult()
                        } else {
                            ToastUtils.makeText(mApplication, response.msg)
                        }
                    }
                })
    }
}
