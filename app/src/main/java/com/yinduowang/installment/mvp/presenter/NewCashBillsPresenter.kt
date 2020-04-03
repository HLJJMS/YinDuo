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
import com.yinduowang.installment.mvp.contract.NewCashBillsContract
import com.yinduowang.installment.mvp.model.entity.BaseResponse
import com.yinduowang.installment.mvp.model.entity.NewCashBillsEntity
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber
import javax.inject.Inject


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/03/2019 09:19
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
class NewCashBillsPresenter
@Inject
constructor(model: NewCashBillsContract.Model, rootView: NewCashBillsContract.View) :
        BasePresenter<NewCashBillsContract.Model, NewCashBillsContract.View>(model, rootView) {
    @Inject
    lateinit var mErrorHandler: RxErrorHandler
    @Inject
    lateinit var mApplication: Application
    @Inject
    lateinit var mImageLoader: ImageLoader
    @Inject
    lateinit var mAppManager: AppManager

    fun getCashBills(year: String, month: String,
                     flag: String) {
        mModel.getCashBills(year, month, flag)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(object : ErrorHandleSubscriber<BaseResponse<NewCashBillsEntity>>(mErrorHandler) {
                    override fun onNext(response: BaseResponse<NewCashBillsEntity>) {
                        if (TextUtils.equals(response.code, Api.RequestSuccess)) {
                            response.data?.let { it ->
                                mRootView.returnCashBillsInfo(it)
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
     * 检查是否可以还款
     *
     * @param type 1分期账单提前还款,2分期账单到期还款,3分期详情全部还款
     * */
    fun checkRepay(type: String, loanSumId: String) {
        mModel.checkRepay(type, loanSumId).compose(RxUtils.applySchedulers(mRootView))
                .subscribe(object : ErrorHandleSubscriber<BaseResponse<Any>>(mErrorHandler) {
                    override fun onNext(response: BaseResponse<Any>) {
                        if (TextUtils.equals(response.code, Api.RequestSuccess)) {
                            mRootView.canRepay()
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
