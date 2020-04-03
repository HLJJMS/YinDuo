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
import com.yinduowang.installment.mvp.contract.LendConfirmLoanContract
import com.yinduowang.installment.mvp.model.entity.BankCardListEntity
import com.yinduowang.installment.mvp.model.entity.BaseResponse
import com.yinduowang.installment.mvp.model.entity.ConfirmEntity
import com.yinduowang.installment.mvp.model.entity.ConfirmLoanEntity
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber
import javax.inject.Inject


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/27/2019 17:44
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
class LendConfirmLoanPresenter
@Inject
constructor(model: LendConfirmLoanContract.Model, rootView: LendConfirmLoanContract.View) :
        BasePresenter<LendConfirmLoanContract.Model, LendConfirmLoanContract.View>(model, rootView) {
    @Inject
    lateinit var mErrorHandler: RxErrorHandler
    @Inject
    lateinit var mApplication: Application
    @Inject
    lateinit var mImageLoader: ImageLoader
    @Inject
    lateinit var mAppManager: AppManager

    fun getLoanApplyPage(loanFund: String) {
        mModel.getLoanApplyPage(loanFund).compose(RxUtils.applySchedulers(mRootView))
                .subscribe(object : ErrorHandleSubscriber<BaseResponse<ConfirmLoanEntity>>(mErrorHandler) {
                    override fun onNext(response: BaseResponse<ConfirmLoanEntity>) {
                        if (TextUtils.equals(response.code, Api.RequestSuccess)) {
                            response.data?.let {
                                mRootView.getLoanApplyPage(it)
                            }
                        } else {
                            ToastUtils.makeText(mApplication, response.msg)
                        }
                    }

                })
    }

    fun confirmApplyLoan(password: String, loanPurposeId: String, loanMoney: String, type: String, stage: String, bankId: String, configId: String, lonaDays: String) {
        mModel.confirmApplyLoan(password, loanPurposeId, loanMoney, type, stage, bankId, configId, lonaDays).compose(RxUtils.applySchedulers(mRootView))
                .subscribe(object : ErrorHandleSubscriber<BaseResponse<ConfirmEntity>>(mErrorHandler) {
                    override fun onNext(response: BaseResponse<ConfirmEntity>) {
                        response.let {
                            mRootView.returnConfirmLoan(it)
                        }

                    }

                })
    }

    fun getBankCardList(bankCardId: String) {
        mModel.getBankCardList(bankCardId)
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

    fun getVaildatePswSet() {
        mModel.vaildatePswSet().compose(RxUtils.applySchedulersNoLoading(mRootView))
                .subscribe(object : ErrorHandleSubscriber<BaseResponse<Any>>(mErrorHandler) {
                    override fun onNext(response: BaseResponse<Any>) {
                        if (TextUtils.equals(response.code, Api.RequestSuccess)) {
                            mRootView.returnPayPswIsSet(response.data.toString())
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
