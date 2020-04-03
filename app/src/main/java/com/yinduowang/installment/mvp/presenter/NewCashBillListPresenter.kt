package com.yinduowang.installment.mvp.presenter

import android.app.Application
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
import com.yinduowang.installment.mvp.contract.NewCashBillListContract
import com.yinduowang.installment.mvp.model.entity.BaseResponse
import com.yinduowang.installment.mvp.model.entity.NewCashBillListEntity
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber
import java.util.*
import javax.inject.Inject

/**
 * Description：账单明细（现金）
 * Author：田羽衡
 * Version：<v1.1，2019/11/1 >
 * LastEditTime：2019/11/1
 * LastEditors:
 * Deprecated： false
 */
@ActivityScope
class NewCashBillListPresenter
@Inject
constructor(model: NewCashBillListContract.Model, rootView: NewCashBillListContract.View) :
        BasePresenter<NewCashBillListContract.Model, NewCashBillListContract.View>(model, rootView) {
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
     * Description：获取账单详情列表
     * Author：田羽衡
     * Version：<v1.0，2019/11/1 10:04>
     * params:年月账单id
     * return：
     * LastEditTime：2019/11/1 10:04
     * Deprecated： false
     */
    fun getBillList(year: String, month: String, id: String) {
        mRootView.showLoading()
        val map = HashMap<String, String>()
        map["id"] = id
        map["year"] = year
        map["month"] = month
        map["type"] = "1"
        mModel.getBillList(SPUtils.getInstance().getString(SPConstant.TOKEN), SignUtils.getSign(map), id, year, month).compose(RxUtils.applySchedulers(mRootView)).subscribe(object : ErrorHandleSubscriber<BaseResponse<NewCashBillListEntity>>(mErrorHandler) {
            override fun onNext(t: BaseResponse<NewCashBillListEntity>) {

                if (t.code == Api.RequestSuccess) {
                    t.data?.let { it -> mRootView.getListSuccess(it) }
                } else {
                    ToastUtils.makeText(mApplication, t.msg)
                }
                mRootView.hideLoading()
            }

        })
    }
}
