package com.yinduowang.installment.mvp.presenter

import android.app.Application
import android.text.TextUtils
import com.blankj.utilcode.util.SPUtils
import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.http.imageloader.ImageLoader
import com.jess.arms.integration.AppManager
import com.jess.arms.mvp.BasePresenter
import com.yinduowang.installment.app.constant.Api
import com.yinduowang.installment.app.constant.SPConstant
import com.yinduowang.installment.app.utils.IpAddressUtils
import com.yinduowang.installment.app.utils.RxUtils
import com.yinduowang.installment.app.utils.ToastUtils
import com.yinduowang.installment.mvp.contract.CommWebViewContract
import com.yinduowang.installment.mvp.model.entity.BaseResponse
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber
import javax.inject.Inject


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/24/2019 13:20
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
class CommWebViewPresenter
@Inject
constructor(model: CommWebViewContract.Model, rootView: CommWebViewContract.View) :
        BasePresenter<CommWebViewContract.Model, CommWebViewContract.View>(model, rootView) {
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
                        } else {
                            ToastUtils.makeText(mApplication, response.msg)
                        }
                    }
                })
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

    fun onFloatDetailedClick(sourceId: String, ac_type: String, type: String) {
        val token = SPUtils.getInstance().getString(SPConstant.TOKEN)
        mModel.onFloatDetailedAreaClick(token, sourceId, ac_type, type, IpAddressUtils.getIPAddress(mApplication.getApplicationContext()))
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(object : ErrorHandleSubscriber<BaseResponse<Any>>(mErrorHandler) {
                    override fun onNext(baseResponse: BaseResponse<Any>) {
                        System.out.println("+++++++++++++++++++++" + baseResponse.code)
                    }
                })
    }
}
