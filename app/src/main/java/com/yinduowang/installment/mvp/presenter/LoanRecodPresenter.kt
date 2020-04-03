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

import com.yinduowang.installment.mvp.contract.LoanRecoderContract
import com.yinduowang.installment.mvp.model.entity.BaseResponse
import com.yinduowang.installment.mvp.model.entity.LoanRecordEntity
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/30/2019 20:22
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
class LoanRecodPresenter
@Inject
constructor(model: LoanRecoderContract.Model, rootView: LoanRecoderContract.View) :
        BasePresenter<LoanRecoderContract.Model, LoanRecoderContract.View>(model, rootView) {
    @Inject
    lateinit var mErrorHandler: RxErrorHandler
    @Inject
    lateinit var mApplication: Application
    @Inject
    lateinit var mImageLoader: ImageLoader
    @Inject
    lateinit var mAppManager: AppManager

    fun getLoanRecordList(id:Int, loanType:String){
        mModel.getLoanRecordList(id, loanType).
                compose(RxUtils.applySchedulersNoLoading(mRootView)).subscribe(object : ErrorHandleSubscriber<BaseResponse<LoanRecordEntity>>(mErrorHandler) {
            override fun onNext(response: BaseResponse<LoanRecordEntity>) {
                mRootView.finishRefresh()
                if (TextUtils.equals(response.code, Api.RequestSuccess)) {
                  response.data?.let { mRootView.returnLoanRecord(it) }
                }else{
                    ToastUtils.makeText(mApplication, response.msg)
                }

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
