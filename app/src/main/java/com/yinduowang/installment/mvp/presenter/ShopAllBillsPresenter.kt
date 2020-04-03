package com.yinduowang.installment.mvp.presenter

import android.app.Application
import android.text.TextUtils

import com.jess.arms.integration.AppManager
import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.mvp.BasePresenter
import com.jess.arms.http.imageloader.ImageLoader
import com.yinduowang.installment.app.constant.Api
import com.yinduowang.installment.app.utils.RxUtils
import com.yinduowang.installment.app.utils.ToastUtils
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import javax.inject.Inject

import com.yinduowang.installment.mvp.contract.ShopAllBillsContract
import com.yinduowang.installment.mvp.model.entity.BaseResponse
import com.yinduowang.installment.mvp.model.entity.ShopAllBillsEntity
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/03/2019 14:21
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
class ShopAllBillsPresenter
@Inject
constructor(model: ShopAllBillsContract.Model, rootView: ShopAllBillsContract.View) :
        BasePresenter<ShopAllBillsContract.Model, ShopAllBillsContract.View>(model, rootView) {
    @Inject
    lateinit var mErrorHandler: RxErrorHandler
    @Inject
    lateinit var mApplication: Application
    @Inject
    lateinit var mImageLoader: ImageLoader
    @Inject
    lateinit var mAppManager: AppManager

    fun getShopAllBills(year: String, flag: String, type: String) {
        mModel.getShopAllBills(year, flag , type)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(object : ErrorHandleSubscriber<BaseResponse<ShopAllBillsEntity>>(mErrorHandler) {
                    override fun onNext(response: BaseResponse<ShopAllBillsEntity>) {
                        if (TextUtils.equals(response.code, Api.RequestSuccess)) {
                            response.data?.let { it ->
                                mRootView.returnBackAllBills(it)
                            }
                        } else {
                            ToastUtils.makeText(mApplication, response.msg)
                        }
                    }

                    override fun onError(t: Throwable) {
                        super.onError(t)
                    }
                })
    }

    override fun onDestroy() {
        super.onDestroy();
    }
}
