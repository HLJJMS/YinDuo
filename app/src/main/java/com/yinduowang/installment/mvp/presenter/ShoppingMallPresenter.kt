package com.yinduowang.installment.mvp.presenter

import android.app.Application
import android.text.TextUtils
import com.blankj.utilcode.util.SPUtils
import com.google.gson.Gson
import com.jess.arms.di.scope.FragmentScope
import com.jess.arms.http.imageloader.ImageLoader
import com.jess.arms.integration.AppManager
import com.jess.arms.mvp.BasePresenter
import com.yinduowang.installment.app.constant.Api
import com.yinduowang.installment.app.constant.Constant
import com.yinduowang.installment.app.utils.RxUtils
import com.yinduowang.installment.app.utils.ToastUtils
import com.yinduowang.installment.mvp.contract.ShoppingMallContract
import com.yinduowang.installment.mvp.model.entity.BaseResponse
import com.yinduowang.installment.mvp.model.entity.HomeShoppingMallEntity
import com.yinduowang.installment.mvp.model.entity.LoginMessage
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber
import javax.inject.Inject


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/25/2019 13:10
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
class ShoppingMallPresenter
@Inject
constructor(model: ShoppingMallContract.Model, rootView: ShoppingMallContract.View) :
        BasePresenter<ShoppingMallContract.Model, ShoppingMallContract.View>(model, rootView) {
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

    fun getShoppingMallData() {
        mModel.getShoppingMallData()
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(object : ErrorHandleSubscriber<HomeShoppingMallEntity>(mErrorHandler) {
                    override fun onNext(entity: HomeShoppingMallEntity) {
                        SPUtils.getInstance().put(Constant.getCacheHomeShoppingMall(mApplication), Gson().toJson(entity))
                        mRootView.getShoppingMallDataSuccess(entity, false)
                    }
                })
    }

    fun getLoginMessage() {
        mModel.getLoginMessage()
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(object : ErrorHandleSubscriber<BaseResponse<LoginMessage>>(mErrorHandler) {
                    override fun onNext(t: BaseResponse<LoginMessage>) {
                        if (TextUtils.equals(t.code, Api.RequestSuccess)) {
                            t.data?.let { it -> mRootView.getLoginMessageSuccess(it.title, it.message) }
                        } else {
                            ToastUtils.makeText(mApplication, t.msg)
                        }
                    }
                })
    }
}
