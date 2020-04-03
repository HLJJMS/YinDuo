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
import com.yinduowang.installment.mvp.contract.ForgetPasswordContract
import com.yinduowang.installment.mvp.model.entity.BaseResponse
import com.yinduowang.installment.mvp.model.entity.VerifyCodeEntity
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber
import javax.inject.Inject


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/16/2019 13:52
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
class ForgetPasswordPresenter
@Inject
constructor(model: ForgetPasswordContract.Model, rootView: ForgetPasswordContract.View) :
        BasePresenter<ForgetPasswordContract.Model, ForgetPasswordContract.View>(model, rootView) {
    @Inject
    lateinit var mErrorHandler: RxErrorHandler
    @Inject
    lateinit var mApplication: Application
    @Inject
    lateinit var mImageLoader: ImageLoader
    @Inject
    lateinit var mAppManager: AppManager
    fun getVerifyCode(mobile:String,type:String){
        mModel.getVerifyCode(mobile,type).compose(RxUtils.applySchedulers(mRootView)).
                subscribe(object : ErrorHandleSubscriber<BaseResponse<VerifyCodeEntity>>(mErrorHandler) {
                    override fun onNext(response: BaseResponse<VerifyCodeEntity>) {
                         ToastUtils.makeText(mApplication, response.msg)
                        if (TextUtils.equals(response.code, Api.RequestSuccess))
                            mRootView.onVerifyCode()
                    }

                })

    }
    fun findBackPassword(mobile:String, authCode:String,password:String){
        mModel.findBackPassword(mobile, authCode, password)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(object : ErrorHandleSubscriber<BaseResponse<Any>>(mErrorHandler) {
                    override fun onNext(response: BaseResponse<Any>) {
                        if (TextUtils.equals(response.code, Api.RequestSuccess))
                            mRootView.onFindBackPasswordSuccess()
                        else
                            ToastUtils.makeText(mApplication, response.msg)
                    }

                })
    }

    override fun onDestroy() {
        super.onDestroy();
    }
}
