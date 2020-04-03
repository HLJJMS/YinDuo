package com.yinduowang.installment.mvp.presenter

import android.app.Application
import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.http.imageloader.ImageLoader
import com.jess.arms.integration.AppManager
import com.jess.arms.mvp.BasePresenter
import com.yinduowang.installment.app.constant.Api
import com.yinduowang.installment.app.utils.RxUtils
import com.yinduowang.installment.app.utils.ToastUtils
import com.yinduowang.installment.mvp.contract.AuthEmergencyContactContract
import com.yinduowang.installment.mvp.model.entity.AuthEmergencyContactEntity
import com.yinduowang.installment.mvp.model.entity.BaseResponse
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber
import javax.inject.Inject



/**
 * Description：紧急联系人presenter
 * Author：田羽衡
 * Version：<v1.0，2019/11/1 >
 * LastEditTime：2019/11/1
 * LastEditors:
 * Deprecated： false
 */
@ActivityScope
class AuthEmergencyContactPresenter
@Inject
constructor(model: AuthEmergencyContactContract.Model, rootView: AuthEmergencyContactContract.View) :
        BasePresenter<AuthEmergencyContactContract.Model, AuthEmergencyContactContract.View>(model, rootView) {
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

    fun saveContacts(map:HashMap<String,String>) {
        mModel.saveContacts(map)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(object : ErrorHandleSubscriber<BaseResponse<Any>>(mErrorHandler) {
                    override fun onNext(baseResponse: BaseResponse<Any>) {
                        if (baseResponse.code == Api.RequestSuccess) {
                            mRootView.success(baseResponse.msg)
                        } else {
                            ToastUtils.makeText(mApplication, baseResponse.msg)
                        }
                    }
                })
    }

    fun getData() {
        mModel.getData().compose(RxUtils.applySchedulers(mRootView))
                .subscribe(object : ErrorHandleSubscriber<BaseResponse<AuthEmergencyContactEntity>>(mErrorHandler) {
                    override fun onNext(baseResponse: BaseResponse<AuthEmergencyContactEntity>) {
                        if (baseResponse.code == Api.RequestSuccess) {
                            baseResponse.data?.let { it -> mRootView.getData(it) }
                        } else {
                            ToastUtils.makeText(mApplication, baseResponse.msg)
                        }
                    }
                })
    }
}
