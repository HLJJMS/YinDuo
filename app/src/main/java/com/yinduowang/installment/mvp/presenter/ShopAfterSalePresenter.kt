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
import com.yinduowang.installment.mvp.contract.ShopAfterSaleContract
import com.yinduowang.installment.mvp.model.entity.BasePhpResponse
import com.yinduowang.installment.mvp.model.entity.ShopOrderEntity
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber
import javax.inject.Inject
/**
 * Description：售后、退还
 * Author：田羽衡
 * Version：<v1.0，2019/11/1 >
 * LastEditTime：2019/11/1
 * LastEditors:
 * Deprecated： false
 */
@ActivityScope
class ShopAfterSalePresenter
@Inject
constructor(model: ShopAfterSaleContract.Model, rootView: ShopAfterSaleContract.View) :
        BasePresenter<ShopAfterSaleContract.Model, ShopAfterSaleContract.View>(model, rootView) {
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
    /**
     * Description：数据获取
     * Author：田羽衡
     * Version：<v1.0，2019/10/31 10:33>
     * params:
     * return：
     * LastEditTime：2019/10/31 10:33
     * Deprecated： false
     */
    fun getPayBackRecordList(page: String) {
        mModel.getList(page, "1", "10").compose(RxUtils.applySchedulersNoLoading(mRootView)).subscribe(object : ErrorHandleSubscriber<BasePhpResponse<ShopOrderEntity>>(mErrorHandler) {
            override fun onNext(response: BasePhpResponse<ShopOrderEntity>) {
                if (TextUtils.equals(response.status, Api.RequestPhpSuccess)) {
                    response.data?.let { mRootView.getListSuccess(it.order_list) }
                } else {
                    ToastUtils.makeText(mApplication, response.msg)
                    mRootView.getListFail()
                }

            }

            override fun onError(t: Throwable) {
                super.onError(t)
                mRootView.getListFail()
            }
        })
    }
}
