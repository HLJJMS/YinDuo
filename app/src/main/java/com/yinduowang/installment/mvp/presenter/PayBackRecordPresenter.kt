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
import com.yinduowang.installment.mvp.contract.PayBackRecordContract
import com.yinduowang.installment.mvp.model.entity.BaseResponse
import com.yinduowang.installment.mvp.model.entity.PayBackRecordEntity
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber
import javax.inject.Inject


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/01/2019 10:04
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
class PayBackRecordPresenter
@Inject
constructor(model: PayBackRecordContract.Model, rootView: PayBackRecordContract.View) :
        BasePresenter<PayBackRecordContract.Model, PayBackRecordContract.View>(model, rootView) {
    @Inject
    lateinit var mErrorHandler: RxErrorHandler
    @Inject
    lateinit var mApplication: Application
    @Inject
    lateinit var mImageLoader: ImageLoader
    @Inject
    lateinit var mAppManager: AppManager


    fun getPayBackRecordList(id: String, type: String) {
        mModel.getPayBackRecordList(id, type).compose(RxUtils.applySchedulersNoLoading(mRootView)).subscribe(object : ErrorHandleSubscriber<BaseResponse<PayBackRecordEntity>>(mErrorHandler) {
            override fun onNext(response: BaseResponse<PayBackRecordEntity>) {
                mRootView.finishRefresh()
                if (TextUtils.equals(response.code, Api.RequestSuccess)) {
                    response.data?.let { mRootView.returnPayBackRecord(it) }
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

    override fun onDestroy() {
        super.onDestroy();
    }
}
