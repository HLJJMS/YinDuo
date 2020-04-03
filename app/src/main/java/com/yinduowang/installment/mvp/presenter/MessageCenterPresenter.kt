package com.yinduowang.installment.mvp.presenter

import android.app.Application
import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.http.imageloader.ImageLoader
import com.jess.arms.integration.AppManager
import com.jess.arms.mvp.BasePresenter
import com.yinduowang.installment.app.constant.Api
import com.yinduowang.installment.app.utils.RxUtils
import com.yinduowang.installment.app.utils.ToastUtils
import com.yinduowang.installment.mvp.contract.MessageCenterContract
import com.yinduowang.installment.mvp.model.entity.BaseResponse
import com.yinduowang.installment.mvp.model.entity.MessageCenterEntity
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber
import javax.inject.Inject



/**
 * Description：消息中心
 * Author：田羽衡
 * Version：<v1.0，2019/11/1 >
 * LastEditTime：2019/11/1
 * LastEditors:
 * Deprecated： false
 */
@ActivityScope
class MessageCenterPresenter
@Inject
constructor(model: MessageCenterContract.Model, rootView: MessageCenterContract.View) :
        BasePresenter<MessageCenterContract.Model, MessageCenterContract.View>(model, rootView) {
    @Inject
    lateinit var mErrorHandler: RxErrorHandler
    @Inject
    lateinit var mApplication: Application
    @Inject
    lateinit var mImageLoader: ImageLoader
    @Inject
    lateinit var mAppManager: AppManager

    /**
     * Description：获取消息列表
     * Author：田羽衡
     * Version：<v1.0，2019/11/1 10:00>
     * params:id：分页加载，传每页最一条数据的id，第一页传1
     * return：
     * LastEditTime：2019/11/1 10:00
     * Deprecated： false
     */


    fun getMessage(id: Int, releaseTime: String) {
        mModel.getMessage(id, releaseTime).compose(RxUtils.applySchedulersNoLoading(mRootView))
                .subscribe(object : ErrorHandleSubscriber<BaseResponse<MessageCenterEntity>>(mErrorHandler) {
                    override fun onNext(response: BaseResponse<MessageCenterEntity>) {
                        mRootView.refreshFinish()
                        if (response.code == Api.RequestSuccess) {
                            response.data?.let {
                                mRootView.getMessageSuccess(it.list)
                            }
                        } else {
                            ToastUtils.makeText(mApplication, response.msg)
                        }
                    }

                    override fun onError(t: Throwable) {
                        super.onError(t)
                        mRootView.refreshFinish()
                        mRootView.onErrorFail()
                    }
                })
    }

    override fun onDestroy() {
        super.onDestroy();
    }
}
