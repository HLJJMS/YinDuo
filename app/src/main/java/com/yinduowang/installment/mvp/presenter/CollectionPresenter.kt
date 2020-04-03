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
import com.yinduowang.installment.mvp.contract.CollectionContract
import com.yinduowang.installment.mvp.model.entity.BaseResponse
import com.yinduowang.installment.mvp.model.entity.CollectionEntity
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber
import javax.inject.Inject


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/23/2019 13:52
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
class CollectionPresenter
@Inject
constructor(model: CollectionContract.Model, rootView: CollectionContract.View) :
        BasePresenter<CollectionContract.Model, CollectionContract.View>(model, rootView) {
    @Inject
    lateinit var mErrorHandler: RxErrorHandler
    @Inject
    lateinit var mApplication: Application
    @Inject
    lateinit var mImageLoader: ImageLoader
    @Inject
    lateinit var mAppManager: AppManager

    fun submitCollectionComplains(typeId: String, content: String) {
        mModel.collectionComplaint(typeId, content).compose(RxUtils.applySchedulers(mRootView)).subscribe(object : ErrorHandleSubscriber<BaseResponse<Any>>(mErrorHandler) {
            override fun onNext(response: BaseResponse<Any>) {
                ToastUtils.makeText(mApplication, response.msg)
                if (TextUtils.equals(response.code, Api.RequestSuccess)) {
                    mRootView.killMyself()
                }

            }
        })
    }
     fun getCollectionComplains(){
         mModel.getCollectionType().compose(RxUtils.applySchedulersNoLoading(mRootView)).subscribe(object : ErrorHandleSubscriber<BaseResponse<CollectionEntity>>(mErrorHandler) {
             override fun onNext(response: BaseResponse<CollectionEntity>) {
                 if (TextUtils.equals(response.code, Api.RequestSuccess)) {
                   response.data?.let {
                       mRootView.returnColletionYtpe(it)}
                 }
             }
         })
     }

    override fun onDestroy() {
        super.onDestroy();
    }
}
