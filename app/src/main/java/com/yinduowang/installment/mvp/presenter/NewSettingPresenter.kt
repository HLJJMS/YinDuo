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
import com.yinduowang.installment.mvp.contract.SettingContract
import com.yinduowang.installment.mvp.model.entity.BaseResponse
import com.yinduowang.installment.mvp.model.entity.PerfectEntity
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber
import javax.inject.Inject


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/23/2019 10:50
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
class NewSettingPresenter
@Inject
constructor(model: SettingContract.Model, rootView: SettingContract.View) :
        BasePresenter<SettingContract.Model, SettingContract.View>(model, rootView) {
    @Inject
    lateinit var mErrorHandler: RxErrorHandler
    @Inject
    lateinit var mApplication: Application
    @Inject
    lateinit var mImageLoader: ImageLoader
    @Inject
    lateinit var mAppManager: AppManager
    fun getVaildatePswSet(){
        mModel.vaildatePswSet().compose(RxUtils.applySchedulers(mRootView))
                .subscribe(object : ErrorHandleSubscriber<BaseResponse<Any>>(mErrorHandler) {
                    override fun onNext(response: BaseResponse<Any>) {
                        if (TextUtils.equals(response.code, Api.RequestSuccess)) {
                            mRootView.returnPayPswIsSet(response.data.toString())
                        }  else{
                            ToastUtils.makeText(mApplication,response.msg)
                        }
                    }
                })
    }
    fun loginOut(){
        mModel.loginOut().compose(RxUtils.applySchedulers(mRootView))
                .subscribe(object : ErrorHandleSubscriber<BaseResponse<Any>>(mErrorHandler) {
                    override fun onNext(response: BaseResponse<Any>) {
                        if (response.code == Api.RequestSuccess) {
                            mRootView.logOut()
                        } else {
                            ToastUtils.makeText(mApplication, response.msg)
                        }
                    }
                })
    }
    fun perfect() {
        mModel.perfect()
                .compose(RxUtils.applySchedulersNoLoading(mRootView))
                .subscribe(object : ErrorHandleSubscriber<BaseResponse<PerfectEntity>>(mErrorHandler) {
                    override fun onNext(response: BaseResponse<PerfectEntity>) {
                        if (TextUtils.equals(response.code, Api.RequestSuccess)) {
                            response.data?.let { it -> mRootView.perfect(it) }
                        } else {
                            ToastUtils.makeText(mApplication, response.msg)
                        }
                    }
                })
    }
    override fun onDestroy() {
        super.onDestroy();
    }
}
