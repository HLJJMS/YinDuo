package com.yinduowang.installment.mvp.presenter

import android.app.Application
import android.text.TextUtils
import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.http.imageloader.ImageLoader
import com.jess.arms.integration.AppManager
import com.jess.arms.mvp.BasePresenter
import com.yinduowang.installment.app.BaseConfig
import com.yinduowang.installment.app.constant.Api
import com.yinduowang.installment.app.utils.RxUtils
import com.yinduowang.installment.mvp.contract.SplashContract
import com.yinduowang.installment.mvp.model.entity.BaseResponse
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber
import javax.inject.Inject


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/11/2019 16:00
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
class SplashPresenter
@Inject
constructor(model: SplashContract.Model, rootView: SplashContract.View) :
        BasePresenter<SplashContract.Model, SplashContract.View>(model, rootView) {
    @Inject
    lateinit var mErrorHandler: RxErrorHandler
    @Inject
    lateinit var mApplication: Application
    @Inject
    lateinit var mImageLoader: ImageLoader
    @Inject
    lateinit var mAppManager: AppManager


    override fun onDestroy() {
        super.onDestroy()
    }

    fun isTestVersion() {
        // 不走提审接口 默认为不需要提审
        if (BaseConfig.IS_OPEN_TEST) {
            mModel.isTestVersion()
                    .compose(RxUtils.applySchedulersNoLoading(mRootView))
                    .subscribe(object : ErrorHandleSubscriber<BaseResponse<Map<String, String>>>(mErrorHandler) {
                        override fun onNext(t: BaseResponse<Map<String, String>>) {
                            if (TextUtils.equals(t.code, Api.RequestSuccess)) {
                                val status = t.data?.get("status")
                                if (status == "0") {
                                    mRootView.isTestVersion(true)
                                } else {
                                    mRootView.isTestVersion(false)
                                }
                            }
                        }
                    })
        } else {
            mRootView.isTestVersion(false)
        }
    }
}
