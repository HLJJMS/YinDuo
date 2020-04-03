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
import com.yinduowang.installment.mvp.contract.PayBackMoneyContract
import com.yinduowang.installment.mvp.model.entity.*
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber
import java.util.*
import javax.inject.Inject


/**
 * Description：我的-账单-还款金额
 * Author：田羽衡
 * Version：<v1.0，2019/11/1 >
 * LastEditTime：2019/11/1
 * LastEditors:
 * Deprecated： false
 */
@ActivityScope
class PayBackMoneyPresenter
@Inject
constructor(model: PayBackMoneyContract.Model, rootView: PayBackMoneyContract.View) :
        BasePresenter<PayBackMoneyContract.Model, PayBackMoneyContract.View>(model, rootView) {
    @Inject
    lateinit var mErrorHandler: RxErrorHandler
    @Inject
    lateinit var mApplication: Application
    @Inject
    lateinit var mImageLoader: ImageLoader
    @Inject
    lateinit var mAppManager: AppManager



    /**
     * Description：获取这个页面数据
     * Author：田羽衡
     * Version：<v1.0，2019/11/1 10:25>
     * params:
     * return：
     * LastEditTime：2019/11/1 10:25
     * Deprecated： false
     */

    // type 传值类型 在之前页面传递过来 区分1商城账单提前还款,2商城账单到期还款,3分期详情全部还款,4现金账单提前还款,5现金账单到期还款,6现金借款详情还款

    fun getPayBackDetailed(type: String, loanSumId: String, year: String, month: String, loanId: String) {
        mModel.getPayBackDetailed(type, loanSumId, year, month, loanId)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(object : ErrorHandleSubscriber<BaseResponse<PayBackMoney>>(mErrorHandler) {
                    override fun onNext(response: BaseResponse<PayBackMoney>) {
                        if (TextUtils.equals(response.code, Api.RequestSuccess)) {
                            response.data?.let { it ->
                                mRootView.returnPayBackDetailed(it)
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


    /**
     * Description：获取银行卡列表
     * Author：田羽衡
     * Version：<v1.0，2019/11/1 10:24>
     * params:
     * return：
     * LastEditTime：2019/11/1 10:24
     * Deprecated： false
     */
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

    //    loanId: String, fund: String, interest: String, serviceCharge: String, preRepayCharge: String, demurrage: String,  bankId: String,  repayType: String, year: String, mouth: String
   /**
    * Description：预还款（点击确认还款先走他）
    * Author：田羽衡
    * Version：<v1.0，2019/11/1 10:24>
    * params:
    * return：
    * LastEditTime：2019/11/1 10:24
    * Deprecated： false
    */

    fun prPayBack(payBackMoney: PayBackMoney, loanId: String, loanSumId: String) {


        val map = HashMap<String, String>()
        if (payBackMoney.type.equals("3")) {
            map["loanId"] = loanId
        } else {
            map["month"] = payBackMoney.month
            map["year"] = payBackMoney.year
        }
        if (payBackMoney.type.equals("2") || payBackMoney.type.equals("1")) {
            map["sumId"] = loanSumId
        }
        map["bankId"] = payBackMoney.bankId
        map["repayType"] = payBackMoney.type
        map["repayFund"] = payBackMoney.repaymentMoney.replace(",", "")
        val sign = SignUtils.getSign(map)
        map["sign"] = sign
        map["token"] = SPUtils.getInstance().getString("token")
        mModel.preRepayBack(map)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(object : ErrorHandleSubscriber<BaseResponse<PreRepayBack>>(mErrorHandler) {
                    override fun onNext(response: BaseResponse<PreRepayBack>) {
                        if (TextUtils.equals(response.code, Api.RequestSuccess)) {
                            if (response!!.data!!.status.equals("0")) {
                                confirmRepay(response.data!!.uniqueCode, response!!.data!!.repayFund.toString())
                            } else {
                                ToastUtils.makeText(mApplication, response.data!!.message)
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


    /**
     * Description：确认还款（上面接口成功后调用它）
     * Author：田羽衡
     * Version：<v1.0，2019/11/1 10:23>
     * params:
     * return：
     * LastEditTime：2019/11/1 10:23
     * Deprecated： false
     */
    fun confirmRepay(uniqueCode: String, repayFund: String) {
        val map = HashMap<String, String>()
        map["uniqueCode"] = uniqueCode
        map["repayFund"] = repayFund
        mModel.confirmRepay(uniqueCode, repayFund, SPUtils.getInstance().getString(SPConstant.TOKEN), SignUtils.getSign(map))
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(object : ErrorHandleSubscriber<BaseResponse<ConfirmRepayBean>>(mErrorHandler) {
                    override fun onNext(response: BaseResponse<ConfirmRepayBean>) {
                        if (TextUtils.equals(response.code, Api.RequestSuccess)) {
                            mRootView.payEnd(response.data!!.status)
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
