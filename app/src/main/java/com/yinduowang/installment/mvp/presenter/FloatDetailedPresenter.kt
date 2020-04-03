package com.yinduowang.installment.mvp.presenter

import android.app.Application
import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.http.imageloader.ImageLoader
import com.jess.arms.integration.AppManager
import com.jess.arms.mvp.BasePresenter
import com.yinduowang.installment.app.utils.RxUtils
import com.yinduowang.installment.mvp.contract.FloatDetailedContract
import com.yinduowang.installment.mvp.model.entity.LoanBannerEntity
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber
import javax.inject.Inject


/**
 * @Description:
 * @Author:
 * @Date: 2019-10-23 10:11:28
 * @Version: appVersionName, 2019-10-23
 * @LastEditors:张利超
 * @LastEditTime:2019-11-1 10:11:28
 * @Deprecated: false
 */
@ActivityScope
class FloatDetailedPresenter
@Inject
constructor(model: FloatDetailedContract.Model, rootView: FloatDetailedContract.View) :
        BasePresenter<FloatDetailedContract.Model, FloatDetailedContract.View>(model, rootView) {
    @Inject
    lateinit var mErrorHandler: RxErrorHandler
    @Inject
    lateinit var mApplication: Application
    @Inject
    lateinit var mImageLoader: ImageLoader
    @Inject
    lateinit var mAppManager: AppManager

    fun getFloatDetailedList(page: Int) {
        mModel.getFloatDetailedList(page.toString(), "1")
                .compose(RxUtils.applySchedulersNoLoading(mRootView))
                .subscribe(object : ErrorHandleSubscriber<LoanBannerEntity>(mErrorHandler) {
                    override fun onNext(Response: LoanBannerEntity) {
                        mRootView.onFinishRefresh()
                        if (Response != null) {
                            mRootView.onResponseData(Response)
                        }
                    }

                    override fun onError(t: Throwable) {
                        super.onError(t)
                        mRootView.onFinishRefresh()
                    }
                })
    }
    override fun onDestroy() {
        super.onDestroy();
    }
}
