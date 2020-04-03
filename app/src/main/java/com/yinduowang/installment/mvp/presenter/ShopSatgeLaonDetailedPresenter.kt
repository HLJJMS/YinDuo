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

import com.yinduowang.installment.mvp.contract.ShopSatgeLaonDetailedContract
import com.yinduowang.installment.mvp.model.entity.BaseResponse
import com.yinduowang.installment.mvp.model.entity.LoanShopDetailsEntity
import com.yinduowang.installment.mvp.model.entity.VerifyCodeEntity
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/31/2019 14:25
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
class ShopSatgeLaonDetailedPresenter
@Inject
constructor(model: ShopSatgeLaonDetailedContract.Model, rootView: ShopSatgeLaonDetailedContract.View) :
        BasePresenter<ShopSatgeLaonDetailedContract.Model, ShopSatgeLaonDetailedContract.View>(model, rootView) {
    @Inject
    lateinit var mErrorHandler: RxErrorHandler
    @Inject
    lateinit var mApplication: Application
    @Inject
    lateinit var mImageLoader: ImageLoader
    @Inject
    lateinit var mAppManager: AppManager

    /**
     * Description：获取数据
     * Author：田羽衡
     * Version：<v1.0，2019/10/31 13:57>
     * params:
     * return：
     * LastEditTime：2019/10/31 13:57
     * Deprecated： false
     */
    public fun getData(token: String, id: String, sign: String) {
        mModel.getData(token, id, sign)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(object : ErrorHandleSubscriber<BaseResponse<LoanShopDetailsEntity>>(mErrorHandler) {
                    override fun onNext(t: BaseResponse<LoanShopDetailsEntity>) {

                        if (TextUtils.equals(t.code, Api.RequestSuccess)) {
                            t.data?.let { it -> mRootView.setData(it) }
                        } else {
                            ToastUtils.makeText(mApplication, t.msg)
                        }

                    }

                });
    }


    override fun onDestroy() {
        super.onDestroy();
    }
}
