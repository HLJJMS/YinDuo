package com.yinduowang.installment.mvp.presenter

import android.app.Application
import android.text.TextUtils
import com.blankj.utilcode.util.SPUtils

import com.jess.arms.integration.AppManager
import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.mvp.BasePresenter
import com.jess.arms.http.imageloader.ImageLoader
import com.yinduowang.installment.app.constant.Api
import com.yinduowang.installment.app.constant.SPConstant
import com.yinduowang.installment.app.utils.RxUtils
import com.yinduowang.installment.app.utils.SignUtils
import com.yinduowang.installment.app.utils.ToastUtils
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import javax.inject.Inject

import com.yinduowang.installment.mvp.contract.MyLogisticsContract
import com.yinduowang.installment.mvp.model.entity.BasePhpResponse
import com.yinduowang.installment.mvp.model.entity.GetLogisticsEntity
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber
import org.xml.sax.ErrorHandler




/**
 * Description：物流信息
 * Author：田羽衡
 * Version：<v1.0，2019/11/1 >
 * LastEditTime：2019/11/1
 * LastEditors:
 * Deprecated： false
 */
@ActivityScope
class MyLogisticsPresenter
@Inject
constructor(model: MyLogisticsContract.Model, rootView: MyLogisticsContract.View) :
        BasePresenter<MyLogisticsContract.Model, MyLogisticsContract.View>(model, rootView) {
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

    /**
     * Description：物流信息查询
     * Author：田羽衡
     * Version：<v1.0，2019/10/30 17:56>
     * params:地址id
     * return：GetLogisticsEntity
     * LastEditTime：2019/10/30 17:56
     * Deprecated： false
     */
    fun getData(id: String) {
        val map = HashMap<String, String>()
        map["o_id"] = id
        mModel.logisticsDetail(id, SPUtils.getInstance().getString(SPConstant.TOKEN), SignUtils.getSign(map))
                .compose(RxUtils.applySchedulers(mRootView))
                .safeSubscribe(object : ErrorHandleSubscriber<BasePhpResponse<GetLogisticsEntity>>(mErrorHandler) {
                    override fun onNext(t: BasePhpResponse<GetLogisticsEntity>) {
                        if (TextUtils.equals(t.status, Api.RequestPhpSuccess)) {
                            t.data?.let { mRootView.success(it) }
                        } else {
                            ToastUtils.makeText(mApplication, t.msg)
                        }
                    }

                })
    }
}
