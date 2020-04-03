package com.yinduowang.installment.mvp.presenter

import android.app.Application
import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.http.imageloader.ImageLoader
import com.jess.arms.integration.AppManager
import com.jess.arms.mvp.BasePresenter
import com.yinduowang.installment.app.constant.Api
import com.yinduowang.installment.app.utils.RxUtils
import com.yinduowang.installment.mvp.contract.QuotaContract
import com.yinduowang.installment.mvp.model.entity.BaseResponse
import com.yinduowang.installment.mvp.model.entity.QuotaEntity
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber
import javax.inject.Inject


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/30/2019 19:17
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
class MyQuotaPresenter
@Inject
constructor(model: QuotaContract.Model, rootView: QuotaContract.View) :
        BasePresenter<QuotaContract.Model, QuotaContract.View>(model, rootView) {
    @Inject
    lateinit var mErrorHandler: RxErrorHandler
    @Inject
    lateinit var mApplication: Application
    @Inject
    lateinit var mImageLoader: ImageLoader
    @Inject
    lateinit var mAppManager: AppManager
     fun getMyQuota(){
       mModel.getMyQuota()
               .compose(RxUtils.applySchedulers(mRootView))
               .subscribe(object : ErrorHandleSubscriber<BaseResponse<QuotaEntity>>(mErrorHandler) {
           override fun onNext(response: BaseResponse<QuotaEntity>) {
               if (response.code == Api.RequestSuccess) {
                   response.data?.let {
                       mRootView.reutrnMyQuota(it)
                   }
               }
           }
       })
    }

    override fun onDestroy() {
        super.onDestroy();
    }
}
