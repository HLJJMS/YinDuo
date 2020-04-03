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
import com.yinduowang.installment.mvp.contract.AuthenticationCenterContract
import com.yinduowang.installment.mvp.model.entity.BaseResponse
import com.yinduowang.installment.mvp.model.entity.PerfectEntity
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber
import javax.inject.Inject


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/24/2019 14:10
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
class AuthenticationCenterPresenter
@Inject
constructor(model: AuthenticationCenterContract.Model, rootView: AuthenticationCenterContract.View) :
        BasePresenter<AuthenticationCenterContract.Model, AuthenticationCenterContract.View>(model, rootView) {
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

    fun perfect() {
        mModel.perfect()
                .compose(RxUtils.applySchedulersNoLoading(mRootView))
                .subscribe(object : ErrorHandleSubscriber<BaseResponse<PerfectEntity>>(mErrorHandler) {
                    override fun onNext(response: BaseResponse<PerfectEntity>) {
                        mRootView.finishRefresh()
                        if (TextUtils.equals(response.code, Api.RequestSuccess)) {
                            if (response.data?.creditCeilingType != "1") {
                                mRootView.requestAgain()
                            }
                            response.data?.let { it -> mRootView.perfect(it) }
                        } else {
                            mRootView.requestAgain()
//                            ToastUtils.makeText(mApplication, response.msg)
                        }
                    }

                    override fun onError(t: Throwable) {
//                        super.onError(t)
                        mRootView.requestAgain()
                    }
                })
    }

    fun openAccount() {
        mModel.openAccount()
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(object : ErrorHandleSubscriber<BaseResponse<Map<String, String>>>(mErrorHandler) {
                    override fun onNext(response: BaseResponse<Map<String, String>>) {
                        if (TextUtils.equals(response.code, Api.RequestSuccess)) {
                            response.data?.let { it ->
                                var url = it["url"]
                                if (url != null) {
                                    mRootView.openAccountResult(url)
                                }
                            }
                            //如果data为空则进行跳转到宝付页面
                            if (response.data == null) {
                                mRootView.jumpBaofu()
                            }
                        } else {
                            ToastUtils.makeText(mApplication, response.msg)
                        }
                    }
                })
    }

    fun getQuota() {
        mModel.getQuota()
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(object : ErrorHandleSubscriber<BaseResponse<Any>>(mErrorHandler) {
                    override fun onNext(t: BaseResponse<Any>) {
                        if (TextUtils.equals(t.code, Api.RequestSuccess)) {
                            perfect()
                        } else {
                            ToastUtils.makeText(mApplication, t.msg)
                        }
                    }

                })

    }
}
