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
import com.yinduowang.installment.mvp.contract.ChangeDliveryAddressContract
import com.yinduowang.installment.mvp.model.entity.BasePhpResponse
import com.yinduowang.installment.mvp.model.entity.DeliveryAddress
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber
import javax.inject.Inject


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/29/2019 14:00
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
class ChangeDliveryAddressPresenter
@Inject
constructor(model: ChangeDliveryAddressContract.Model, rootView: ChangeDliveryAddressContract.View) :
        BasePresenter<ChangeDliveryAddressContract.Model, ChangeDliveryAddressContract.View>(model, rootView) {
    @Inject
    lateinit var mErrorHandler: RxErrorHandler
    @Inject
    lateinit var mApplication: Application
    @Inject
    lateinit var mImageLoader: ImageLoader
    @Inject
    lateinit var mAppManager: AppManager

    fun changeDeliveryOrChangeAddress(id:String,country:String
                                   ,province:String,city:String
                                   , area:String, address:String
                                   ,mobile:String, name:String
                                   , is_default:String) {
        mModel.changeOraddDeliveryAddress(id, country, province, city, area, address, mobile, name, is_default)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(object : ErrorHandleSubscriber<BasePhpResponse<Any>>(mErrorHandler) {
                    override fun onNext(response: BasePhpResponse<Any>) {
                        if (TextUtils.equals(response.status, Api.RequestPhpSuccess)) {
                            ToastUtils.makeText(mApplication, response.msg)
                            mRootView.returnChangeSuccess()
                        } else {
                            ToastUtils.makeText(mApplication, response.msg)
                        }

                    }
                }
                )}

    fun getDeliveryAddress(id:String) {
        mRootView.showLoading()
        mModel.getDeliveyAddress(id)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(object : ErrorHandleSubscriber<BasePhpResponse<DeliveryAddress>>(mErrorHandler) {
                    override fun onNext(response: BasePhpResponse<DeliveryAddress>) {
                        if (TextUtils.equals(response.status,Api.RequestPhpSuccess)) {
                        response.data?.let {
                            mRootView.returnDelivery(it)
                        }
                        } else {
                            ToastUtils.makeText(mApplication, response.msg)
                        }
                        mRootView.hideLoading()
                        mRootView.buttonEnableFalse()
                    }
                }
                )}
    override fun onDestroy() {
        super.onDestroy();
    }
}
