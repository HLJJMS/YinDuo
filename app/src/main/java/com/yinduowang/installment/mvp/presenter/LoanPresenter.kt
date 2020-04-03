package com.yinduowang.installment.mvp.presenter

import android.app.Application
import android.text.TextUtils
import com.jess.arms.di.scope.FragmentScope
import com.jess.arms.http.imageloader.ImageLoader
import com.jess.arms.integration.AppManager
import com.jess.arms.mvp.BasePresenter
import com.yinduowang.installment.app.constant.Api
import com.yinduowang.installment.app.utils.RxUtils
import com.yinduowang.installment.app.utils.ToastUtils
import com.yinduowang.installment.mvp.contract.LoanContract
import com.yinduowang.installment.mvp.model.entity.BaseResponse
import com.yinduowang.installment.mvp.model.entity.LoanBannerEntity
import com.yinduowang.installment.mvp.model.entity.NewLoanEntity
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber
import javax.inject.Inject


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/17/2019 13:27
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
class LoanPresenter
@Inject
constructor(model: LoanContract.Model, rootView: LoanContract.View) :
        BasePresenter<LoanContract.Model, LoanContract.View>(model, rootView) {
    @Inject
    lateinit var mErrorHandler: RxErrorHandler
    @Inject
    lateinit var mApplication: Application
    @Inject
    lateinit var mImageLoader: ImageLoader
    @Inject
    lateinit var mAppManager: AppManager

    fun getLoanInfo() {
        mModel.getLoanInfo1().compose(RxUtils.applySchedulersNoLoading(mRootView))
                .subscribe(object : ErrorHandleSubscriber<BaseResponse<NewLoanEntity>>(mErrorHandler) {
                    override fun onNext(response: BaseResponse<NewLoanEntity>) {
                        mRootView.finishRefresh()
                        if (TextUtils.equals(response.code, Api.RequestSuccess)) {
                            response.data?.let {
                                mRootView.returnLoanInfo(it)
                            }
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

    fun getBottomList(page: String) {
//        println("++++++++++"+LoanBannerEntity("1","2",null).toString())
        mModel.getLoanBanner(page, "2").
                compose(RxUtils.applySchedulersNoLoading(mRootView))
                .subscribe(object : ErrorHandleSubscriber<LoanBannerEntity>(mErrorHandler) {
                    override fun onNext(response: LoanBannerEntity) {
                        response?.let {
                                mRootView.returnBottomList(it)
                            }
                    }

                })
    }

    fun getSignContract(id: String) {
        mModel.getSignContract(id).compose(RxUtils.applySchedulersNoLoading(mRootView))
                .subscribe(object : ErrorHandleSubscriber<BaseResponse<Any>>(mErrorHandler) {
                    override fun onNext(response: BaseResponse<Any>) {
                        mRootView.finishRefresh()
                        if (TextUtils.equals(response.code, Api.RequestSuccess)) {
                            response.data?.let {
                                mRootView.returnSignContract(response)
                            }
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
    fun confirmApply(loanMoney: String) {
        mModel.confirmApply(loanMoney).compose(RxUtils.applySchedulers(mRootView))
                .subscribe(object : ErrorHandleSubscriber<BaseResponse<Any>>(mErrorHandler) {
                    override fun onNext(response: BaseResponse<Any>) {
                        mRootView.finishRefresh()
                        mRootView.returnConfirmApply(response)
                    }

                    override fun onError(t: Throwable) {
                        super.onError(t)
                        mRootView.finishRefresh()
                    }
                })
    }

    override fun onDestroy() {
        super.onDestroy();
    }
}
