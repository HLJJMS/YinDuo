package com.yinduowang.installment.mvp.presenter

import android.app.Application
import android.text.TextUtils

import com.jess.arms.integration.AppManager
import com.jess.arms.di.scope.FragmentScope
import com.jess.arms.mvp.BasePresenter
import com.jess.arms.http.imageloader.ImageLoader
import com.yinduowang.installment.app.constant.Api
import com.yinduowang.installment.app.utils.RxUtils
import com.yinduowang.installment.app.utils.ToastUtils
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import javax.inject.Inject

import com.yinduowang.installment.mvp.contract.DiscoverContract
import com.yinduowang.installment.mvp.model.entity.BaseResponse
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber


/**
 * @Description:
 * @Author:
 * @Date: 2019-11-13 11:28:50
 * @Version: appVersionName, 2019-11-13
 * @LastEditors:
 * @LastEditTime:
 * @Deprecated: false
 */
@FragmentScope
class DiscoverPresenter
@Inject
constructor(model: DiscoverContract.Model, rootView: DiscoverContract.View) :
        BasePresenter<DiscoverContract.Model, DiscoverContract.View>(model, rootView) {
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

    fun getServiceTel() {
        mModel.serviceTel()
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(object : ErrorHandleSubscriber<BaseResponse<String>>(mErrorHandler) {
                    override fun onNext(t: BaseResponse<String>) {
                        if (TextUtils.equals(t.code, Api.RequestSuccess)) {
                            mRootView.getTelSuccess(t.data.toString())
                        } else {
                            ToastUtils.makeText(mApplication, t.msg)
                        }

                    }
                })

    }
}
