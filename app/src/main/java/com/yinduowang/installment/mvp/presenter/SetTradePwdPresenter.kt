package com.yinduowang.installment.mvp.presenter

import android.app.Application
import android.text.TextUtils
import android.view.Gravity
import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.http.imageloader.ImageLoader
import com.jess.arms.integration.AppManager
import com.jess.arms.mvp.BasePresenter
import com.yinduowang.installment.app.constant.Api
import com.yinduowang.installment.app.utils.RxUtils
import com.yinduowang.installment.app.utils.ToastUtils
import com.yinduowang.installment.mvp.contract.SetTradePwdContract
import com.yinduowang.installment.mvp.model.entity.BaseResponse
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber
import javax.inject.Inject


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/23/2019 14:03
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
class SetTradePwdPresenter
@Inject
constructor(model: SetTradePwdContract.Model, rootView: SetTradePwdContract.View) :
        BasePresenter<SetTradePwdContract.Model, SetTradePwdContract.View>(model, rootView) {
    @Inject
    lateinit var mErrorHandler: RxErrorHandler
    @Inject
    lateinit var mApplication: Application
    @Inject
    lateinit var mImageLoader: ImageLoader
    @Inject
    lateinit var mAppManager: AppManager

    fun setPayPsw(newpw:String,
                  againnewpw: String, isSetted: Boolean) {
        mModel.setPayPsw(newpw, againnewpw, isSetted).compose(RxUtils.applySchedulers(mRootView))
              .subscribe(object : ErrorHandleSubscriber<BaseResponse<Any>>(mErrorHandler) {
                  override fun onNext(response: BaseResponse<Any>) {
                      ToastUtils.makeText(mApplication, response.msg, Gravity.CENTER)
                      if (TextUtils.equals(response.code, Api.RequestSuccess)) {
                          mRootView.returnSetPswWordSuccess()
                      } else {
                          mRootView.returnSetPswWordFail()
                      }
                  }
              })
    }
    fun changePayPsw(oldpw:String){
        mModel.changePayPsw(oldpw).compose(RxUtils.applySchedulers(mRootView))
                .subscribe(object : ErrorHandleSubscriber<BaseResponse<Any>>(mErrorHandler) {
                    override fun onNext(response: BaseResponse<Any>) {
                        if (TextUtils.equals(response.code, Api.RequestSuccess)) {
                            mRootView.returnSetPswWord(true)
                        }  else{
                            ToastUtils.makeText(mApplication, response.msg, Gravity.CENTER)
                            mRootView.returnSetPswWord(false)
                        }
                    }
                })
    }
    override fun onDestroy() {
        super.onDestroy();
    }
}
