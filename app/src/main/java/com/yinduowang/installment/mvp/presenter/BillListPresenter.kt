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
import com.yinduowang.installment.mvp.contract.BillListContract
import com.yinduowang.installment.mvp.model.entity.BaseResponse
import com.yinduowang.installment.mvp.model.entity.BillListEntity
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber
import java.util.*
import javax.inject.Inject



/**
 * Description：账单明细presenter
 * Author：田羽衡
 * Version：<v1.0，2019/11/1 >
 * LastEditTime：2019/11/1
 * LastEditors:
 * Deprecated： false
 */
@ActivityScope
class BillListPresenter
@Inject
constructor(model: BillListContract.Model, rootView: BillListContract.View) :
        BasePresenter<BillListContract.Model, BillListContract.View>(model, rootView) {
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

    fun getBillList(year: String, month: String, id: String , type:String) {
        val map = HashMap<String, String>()
        map["id"] = id
        map["year"] = year
        map["month"] = month
        map["type"] = type

        mModel.getBillList(SPUtils.getInstance().getString(SPConstant.TOKEN), SignUtils.getSign(map), id, year, month,type).compose(RxUtils.applySchedulers(mRootView)).subscribe(object : ErrorHandleSubscriber<BaseResponse<BillListEntity>>(mErrorHandler) {
            override fun onNext(t: BaseResponse<BillListEntity>) {

                if (t.code == Api.RequestSuccess) {
                    t.data?.let { it -> mRootView.getListSuccess(it) }
                } else {
                    ToastUtils.makeText(mApplication, t.msg)
                }
            }

        })
    }
}
