package com.yinduowang.installment.mvp.presenter

import android.app.Application
import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.http.imageloader.ImageLoader
import com.jess.arms.integration.AppManager
import com.jess.arms.mvp.BasePresenter
import com.yinduowang.installment.app.constant.Api
import com.yinduowang.installment.app.utils.RxUtils
import com.yinduowang.installment.mvp.contract.BankCardContract
import com.yinduowang.installment.mvp.model.entity.BankCardListEntity
import com.yinduowang.installment.mvp.model.entity.BaseResponse
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber
import javax.inject.Inject


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/24/2019 14:09
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
class BankCardPresenter
@Inject
constructor(model: BankCardContract.Model, rootView: BankCardContract.View) :
        BasePresenter<BankCardContract.Model, BankCardContract.View>(model, rootView) {
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

    fun getBankCardList() {
        mModel.getBankCardList()
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(object : ErrorHandleSubscriber<BaseResponse<BankCardListEntity>>(mErrorHandler) {
                    override fun onNext(response: BaseResponse<BankCardListEntity>) {
                        if (response.code == Api.RequestSuccess) {
                            response.data?.let {
                                mRootView.getBankCardListSuccess(it.userBanks)
                            }
                        }
                    }
                })
    }
}
