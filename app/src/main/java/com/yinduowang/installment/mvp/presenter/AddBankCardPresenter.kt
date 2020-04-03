package com.yinduowang.installment.mvp.presenter

import android.app.Application
import android.text.TextUtils

import com.jess.arms.integration.AppManager
import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.mvp.BasePresenter
import com.jess.arms.http.imageloader.ImageLoader
import com.yinduowang.installment.app.constant.Api
import com.yinduowang.installment.app.utils.RxUtils
import com.yinduowang.installment.app.utils.ToastUtils
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import javax.inject.Inject

import com.yinduowang.installment.mvp.contract.AddBankCardContract
import com.yinduowang.installment.mvp.model.entity.*
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/24/2019 14:11
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
class AddBankCardPresenter
@Inject
constructor(model: AddBankCardContract.Model, rootView: AddBankCardContract.View) :
        BasePresenter<AddBankCardContract.Model, AddBankCardContract.View>(model, rootView) {
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

    fun getUserInfo() {
        mModel.getUserInfo()
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(object : ErrorHandleSubscriber<BaseResponse<VerificationUserInfoEntity>>(mErrorHandler) {
                    override fun onNext(response: BaseResponse<VerificationUserInfoEntity>) {
                        if (TextUtils.equals(response.code, Api.RequestSuccess)) {
                            response.data?.let { it -> mRootView.getUserInfo(it) }
                        } else {
                            ToastUtils.makeText(mApplication, response.msg)
                        }
                    }
                })
    }

    fun preBind(bankCardNo: String, mobileNo: String) {
        mModel.preBind(bankCardNo, mobileNo)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(object : ErrorHandleSubscriber<BaseResponse<PreBindEntity>>(mErrorHandler) {
                    override fun onNext(response: BaseResponse<PreBindEntity>) {
                        if (TextUtils.equals(response.code, Api.RequestSuccess)) {
                            response.data?.let { it -> mRootView.preBind(it) }
                        } else {
                            ToastUtils.makeText(mApplication, response.msg)
                        }
                    }
                })
    }

    fun confirmBind(uniqueCode: String, authCode: String) {
        mModel.confirmBind(uniqueCode, authCode)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(object : ErrorHandleSubscriber<BaseResponse<ConfirmBindEntity>>(mErrorHandler) {
                    override fun onNext(response: BaseResponse<ConfirmBindEntity>) {
                        if (TextUtils.equals(response.code, Api.RequestSuccess)) {
                            response.data?.let { it -> mRootView.confirmBind(it) }
                        } else {
                            ToastUtils.makeText(mApplication, response.msg)
                        }
                    }
                })
    }
}
