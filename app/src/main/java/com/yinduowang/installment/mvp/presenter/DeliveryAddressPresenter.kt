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
import com.yinduowang.installment.app.utils.RxUtils
import com.yinduowang.installment.app.utils.SignUtils
import com.yinduowang.installment.app.utils.ToastUtils
import com.yinduowang.installment.mvp.contract.DeliveryAddressContract
import com.yinduowang.installment.mvp.model.entity.BasePhpResponse
import com.yinduowang.installment.mvp.model.entity.DeliveryAddressEntity
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber
import java.util.HashMap
import javax.inject.Inject


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/23/2019 13:56
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
class DeliveryAddressPresenter
@Inject
constructor(model: DeliveryAddressContract.Model, rootView: DeliveryAddressContract.View) :
        BasePresenter<DeliveryAddressContract.Model, DeliveryAddressContract.View>(model, rootView) {
    @Inject
    lateinit var mErrorHandler: RxErrorHandler
    @Inject
    lateinit var mApplication: Application
    @Inject
    lateinit var mImageLoader: ImageLoader
    @Inject
    lateinit var mAppManager: AppManager

    fun getDeliveryAddressList() {
        mModel.getDeliveryAddressList()
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(object : ErrorHandleSubscriber<BasePhpResponse<DeliveryAddressEntity>>(mErrorHandler) {
                    override fun onNext(response: BasePhpResponse<DeliveryAddressEntity>) {
                        if (TextUtils.equals(response.status, Api.RequestPhpSuccess)) {
                            response.data?.let { it ->
                                mRootView.returnDeliveryAddressList(it.address_list)
                            }
                        } else {
                            ToastUtils.makeText(mApplication, response.msg)
                        }

                    }
                }
                )
    }


    fun delAddress(id: String) {
        val map = HashMap<String, String>()
        map["id"] = id
        mModel.delAddress(SPUtils.getInstance().getString(SPConstant.TOKEN), id, SignUtils.getSign(map)).compose(RxUtils.applySchedulers(mRootView))
                .subscribe(object : ErrorHandleSubscriber<BasePhpResponse<Any>>(mErrorHandler) {
                    override fun onNext(t: BasePhpResponse<Any>) {
                        if (TextUtils.equals(t.status, Api.RequestPhpSuccess)) {
                            mRootView.delAddressSuccess()
                        } else {
                            ToastUtils.makeText(mApplication, t.msg)
                        }
                    }

                })


    }

    override fun onDestroy() {
        super.onDestroy();
    }


}
