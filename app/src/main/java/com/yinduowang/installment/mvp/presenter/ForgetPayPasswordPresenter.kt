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
import com.yinduowang.installment.mvp.contract.ForgetPayPasswordContract
import com.yinduowang.installment.mvp.model.entity.BaseResponse
import com.yinduowang.installment.mvp.model.entity.VerifyCodeEntity
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber
import javax.inject.Inject


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/25/2019 10:54
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
class ForgetPayPasswordPresenter
@Inject
constructor(model: ForgetPayPasswordContract.Model, rootView: ForgetPayPasswordContract.View) :
        BasePresenter<ForgetPayPasswordContract.Model, ForgetPayPasswordContract.View>(model, rootView) {
    @Inject
    lateinit var mErrorHandler: RxErrorHandler
    @Inject
    lateinit var mApplication: Application
    @Inject
    lateinit var mImageLoader: ImageLoader
    @Inject
    lateinit var mAppManager: AppManager

    fun forgetPayPsw( mobile:String,
                      authCode:String,
                      idCard:String,
                      realName:String){
        mModel.forgetPayPsw(mobile, authCode, idCard, realName).
                compose(RxUtils.applySchedulers(mRootView)).
                subscribe(object : ErrorHandleSubscriber<BaseResponse<Any>>(mErrorHandler) {
            override fun onNext(beanBaseResponse: BaseResponse<Any>) {
                if (TextUtils.equals(beanBaseResponse.code, Api.RequestSuccess)){
                    mRootView.onNextSuccess()
                }else{
                    ToastUtils.makeText(mApplication, beanBaseResponse.msg)
                }
            }

        })
    }
    fun getVerifyCode(mobile: String, type: String) {
        mModel.getVerifyCode(mobile, type)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(object : ErrorHandleSubscriber<BaseResponse<VerifyCodeEntity>>(mErrorHandler) {
                    override fun onNext(response: BaseResponse<VerifyCodeEntity>) {
                        ToastUtils.makeText(mApplication, response.msg)
                        if (TextUtils.equals(response.code, Api.RequestSuccess))
                            mRootView.onVerifyCode()
                    }
                })
    }
    override fun onDestroy() {
        super.onDestroy();
    }
}
