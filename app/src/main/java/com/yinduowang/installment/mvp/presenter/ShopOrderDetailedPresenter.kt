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
import com.yinduowang.installment.mvp.contract.ShopOrderDetailedContract
import com.yinduowang.installment.mvp.model.entity.BasePhpResponse
import com.yinduowang.installment.mvp.model.entity.ShopOrderDetailedEntity
import com.yinduowang.installment.mvp.model.event.ShopAllOrderEvent
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber
import org.simple.eventbus.EventBus
import javax.inject.Inject


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/01/2019 21:04
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
class ShopOrderDetailedPresenter
@Inject
constructor(model: ShopOrderDetailedContract.Model, rootView: ShopOrderDetailedContract.View) :
        BasePresenter<ShopOrderDetailedContract.Model, ShopOrderDetailedContract.View>(model, rootView) {
    @Inject
    lateinit var mErrorHandler: RxErrorHandler
    @Inject
    lateinit var mApplication: Application
    @Inject
    lateinit var mImageLoader: ImageLoader
    @Inject
    lateinit var mAppManager: AppManager

    fun getShopOrderDetailed(o_id: String) {
        mModel.getShopOrderDetailed(o_id).compose(RxUtils.applySchedulers(mRootView)).subscribe(object : ErrorHandleSubscriber<BasePhpResponse<ShopOrderDetailedEntity>>(mErrorHandler) {
            override fun onNext(response: BasePhpResponse<ShopOrderDetailedEntity>) {
                if (TextUtils.equals(response.status, Api.RequestPhpSuccess)) {
                    response.data?.let {
                        mRootView.returnOrederDetailed(it)
                    }
                } else {
                    ToastUtils.makeText(mApplication, response.msg)
                }

            }

        })
    }

    fun cancelShopOrder(o_id: String) {
        mModel.cancelShopOrder(o_id).compose(RxUtils.applySchedulers(mRootView)).subscribe(object : ErrorHandleSubscriber<BasePhpResponse<Any>>(mErrorHandler) {
            override fun onNext(response: BasePhpResponse<Any>) {
                mRootView.refreshPageInfo()
                if (TextUtils.equals(response.status, Api.RequestPhpSuccess)) {
                } else {
                    ToastUtils.makeText(mApplication, response.msg)
                }

            }
        })
    }

    fun orderRetreat(o_id: String) {
        mModel.orderRetreat(o_id).compose(RxUtils.applySchedulers(mRootView)).subscribe(object : ErrorHandleSubscriber<BasePhpResponse<Any>>(mErrorHandler) {
            override fun onNext(response: BasePhpResponse<Any>) {
                if (TextUtils.equals(response.status, Api.RequestPhpSuccess)) {
                    EventBus.getDefault().post(ShopAllOrderEvent())
//                    mRootView.refreshPageInfo()
                } else {
                    ToastUtils.makeText(mApplication, response.msg)
                }
            }
        })
    }

    fun confirmReceipt(o_id: String) {
        mModel.confirmReceipt(o_id).compose(RxUtils.applySchedulers(mRootView)).subscribe(object : ErrorHandleSubscriber<BasePhpResponse<Any>>(mErrorHandler) {
            override fun onNext(response: BasePhpResponse<Any>) {
                if (TextUtils.equals(response.status, Api.RequestPhpSuccess)) {
                    mRootView.comnfirmAccept()
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
