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
import com.yinduowang.installment.mvp.contract.ShopAllOrderContract
import com.yinduowang.installment.mvp.model.entity.BasePhpResponse
import com.yinduowang.installment.mvp.model.entity.ShopOrderEntity
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber
import javax.inject.Inject


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/01/2019 13:59
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
class ShopAllOrderPresenter
@Inject
constructor(model: ShopAllOrderContract.Model, rootView: ShopAllOrderContract.View) :
        BasePresenter<ShopAllOrderContract.Model, ShopAllOrderContract.View>(model, rootView) {
    @Inject
    lateinit var mErrorHandler: RxErrorHandler
    @Inject
    lateinit var mApplication: Application
    @Inject
    lateinit var mImageLoader: ImageLoader
    @Inject
    lateinit var mAppManager: AppManager

    fun getPayBackRecordList(id: String, type: String) {
        mModel.getShopOrderRecord(id, type, "10").compose(RxUtils.applySchedulersNoLoading(mRootView)).subscribe(object : ErrorHandleSubscriber<BasePhpResponse<ShopOrderEntity>>(mErrorHandler) {
            override fun onNext(response: BasePhpResponse<ShopOrderEntity>) {
                mRootView.finishRefresh()
                if (TextUtils.equals(response.status, Api.RequestPhpSuccess)) {
                    response.data?.let { mRootView.returnShopOrderRecord(it.order_list) }
                } else {
                    ToastUtils.makeText(mApplication, response.msg)
                }

            }

            override fun onError(t: Throwable) {
                super.onError(t)
                mRootView.finishRefresh()
            }
        })
    }

    fun cancelShopOrder(o_id: String, orderType: String) {
        mModel.cancelShopOrder(o_id).compose(RxUtils.applySchedulersNoLoading(mRootView)).subscribe(object : ErrorHandleSubscriber<BasePhpResponse<Any>>(mErrorHandler) {
            override fun onNext(response: BasePhpResponse<Any>) {
                ToastUtils.makeText(mApplication, response.msg)
                if (TextUtils.equals(response.status, Api.RequestPhpSuccess)) {
                    if (orderType.equals("99"))
                        mRootView.setOnDataItemChange(o_id)
                    else
                        mRootView.setOnRefresh()
                } else if (TextUtils.equals(response.status, "2")) {
                    mRootView.setOnRefresh()
                }

            }
        })
    }

    fun confirmation(o_id: String) {
        mModel.confirmation(o_id).compose(RxUtils.applySchedulersNoLoading(mRootView)).subscribe(object : ErrorHandleSubscriber<BasePhpResponse<Any>>(mErrorHandler) {
            override fun onNext(response: BasePhpResponse<Any>) {
                if (TextUtils.equals(response.status, Api.RequestPhpSuccess)) {
                    ToastUtils.makeText(mApplication, response.msg)
                    mRootView.receivingGoodsSuccess()
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
