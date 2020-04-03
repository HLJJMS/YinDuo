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
import com.yinduowang.installment.mvp.contract.NewCashInstalmentDetailContract
import com.yinduowang.installment.mvp.model.entity.BaseResponse
import com.yinduowang.installment.mvp.model.entity.NewCashInstalmentDetailEntity
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber
import java.util.*
import javax.inject.Inject

/**
 * Description：分期详情（现金）
 * Author：田羽衡
 * Version：<v1.0，2019/11/1 >
 * LastEditTime：2019/11/1
 * LastEditors:
 * Deprecated： false
 */
@ActivityScope
class NewCashInstalmentDetailPresenter
@Inject
constructor(model: NewCashInstalmentDetailContract.Model, rootView: NewCashInstalmentDetailContract.View) :
        BasePresenter<NewCashInstalmentDetailContract.Model, NewCashInstalmentDetailContract.View>(model, rootView) {
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
     * Description：获取列表数据
     * Author：田羽衡
     * Version：<v1.0，2019/11/1 10:05>
     * params:id分期id
     * return：
     * LastEditTime：2019/11/1 10:05
     * Deprecated： false
     */

    fun getList(id: String) {
        val map = HashMap<String, String>()
        map["id"] = id
        mModel.getList(SPUtils.getInstance().getString(SPConstant.TOKEN), SignUtils.getSign(map), id)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(object : ErrorHandleSubscriber<BaseResponse<NewCashInstalmentDetailEntity>>(mErrorHandler) {
                    override fun onNext(t: BaseResponse<NewCashInstalmentDetailEntity>) {
                        if (t.code == Api.RequestSuccess) {
                            t.data?.let { mRootView.getListSuccess(it) }
                        } else {
                            mRootView.getListFail(t.msg)
                        }
                    }

                })
    }

    /**
     * Description：检查是否可以还款
     * Author：田羽衡
     * Version：<v1.0，2019/11/1 10:05>
     * params:type 1分期账单提前还款,2分期账单到期还款,3分期详情全部还款
     * return：
     * LastEditTime：2019/11/1 10:05
     * Deprecated： false
     */

    fun checkRepay(type: String, loanId: String) {
        mModel.checkRepay(type, loanId).compose(RxUtils.applySchedulers(mRootView))
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
}
