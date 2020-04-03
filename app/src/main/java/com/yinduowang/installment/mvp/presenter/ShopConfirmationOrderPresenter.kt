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
import com.yinduowang.installment.mvp.contract.ShopConfirmationOrderContract
import com.yinduowang.installment.mvp.model.entity.BasePhpResponse
import com.yinduowang.installment.mvp.model.entity.ConfirmationOrder
import com.yinduowang.installment.mvp.model.entity.DeliveryAddress
import com.yinduowang.installment.mvp.model.entity.OrderFee
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber
import java.util.*
import javax.inject.Inject


/**
 * Description：商品订单详情确认
 * Author：田羽衡
 * Version：<v1.0，2019/11/1 >
 * LastEditTime：2019/11/1
 * LastEditors:
 * Deprecated： false
 */
@ActivityScope
class ShopConfirmationOrderPresenter
@Inject
constructor(model: ShopConfirmationOrderContract.Model, rootView: ShopConfirmationOrderContract.View) :
        BasePresenter<ShopConfirmationOrderContract.Model, ShopConfirmationOrderContract.View>(model, rootView) {
    @Inject
    lateinit var mErrorHandler: RxErrorHandler
    @Inject
    lateinit var mApplication: Application
    @Inject
    lateinit var mImageLoader: ImageLoader
    @Inject
    lateinit var mAppManager: AppManager
    //    商品售罄
    val PRODECT_NULL = "2"
    //    商品下架
    val PRODECT_DOWN = "3"
    //    商品被删除
    val PRODECT_DEL = "4"
    var status = ""


    /*
  * 描述：〈 获取地址详细信息〉
  * 修改⼈：〈田羽衡〉
  * 版本号：<1.1，20191024>
  * 修改时间  20191024
  * 弃⽤： <false>
  */
    fun getAddressDetail(id: String) {
        val map = HashMap<String, String>()
        map["id"] = id
        mModel.getDeliveryAddress(id, SPUtils.getInstance().getString(SPConstant.TOKEN), SignUtils.getSign(map))
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(object : ErrorHandleSubscriber<BasePhpResponse<DeliveryAddress>>(mErrorHandler) {
                    override fun onNext(t: BasePhpResponse<DeliveryAddress>) {

                        if (TextUtils.equals(t.status, Api.RequestPhpSuccess)) {
                            t.data?.let { it ->
                                mRootView.addressSuccess(t.data!!)
                            }
                        } else if (TextUtils.equals(t.status, "2")) {
                            ToastUtils.makeText(mApplication, t.msg)
                            mRootView.addressFail()
                        } else {
                            ToastUtils.makeText(mApplication, t.msg)
                            mRootView.addressFail()
                        }

                    }

                })
    }

    /*
      * 描述：〈提交订单〉
      * 修改⼈：〈田羽衡〉
      * 版本号：<1.1，20191024>
      * 修改时间  20191024
      * 弃⽤： <false>
      */
    fun goBuy(price: String, g_id: String, af_id: String, as_id: String, number: String, ma_id: String) {
        val map = HashMap<String, String>()
        map["price"] = price
        map["g_id"] = g_id
        map["af_id"] = af_id
        map["as_id"] = as_id
        map["number"] = number
        map["ma_id"] = ma_id
        mModel.goBuy(price, g_id, af_id, as_id, number, SPUtils.getInstance().getString(SPConstant.TOKEN), ma_id, SignUtils.getSign(map))
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(object : ErrorHandleSubscriber<BasePhpResponse<ConfirmationOrder>>(mErrorHandler) {
                    override fun onNext(t: BasePhpResponse<ConfirmationOrder>) {
                        when (t.status) {
                            Api.RequestPhpSuccess -> {
                                t.data?.let { it ->
                                    mRootView.confirmationOrder(t!!.data!!.o_id)
                                }
                            }
                            PRODECT_NULL -> {
                                ToastUtils.makeText(mApplication, t.msg)
                                mRootView.backShopDetail()
                            }
                            PRODECT_DOWN -> {
                                ToastUtils.makeText(mApplication, t.msg)
                                mRootView.backShopDetail()
                            }
                            PRODECT_DEL -> {
                                // 跳转至商品不存在页（现在为H5，所以就返回h5面刷新）
                                ToastUtils.makeText(mApplication, t.msg)
                                mRootView.backShopDetail()
                            }
                            else -> {
                                ToastUtils.makeText(mApplication, t.msg)
//                                mRootView.backShopDetail()
                            }
                        }
                    }

                })
    }

    /**
     * Description   获取订单信息
     * Author  田羽衡
     * Version  <v1.0，2019/11/5 15:38>
     * params
     * return
     * LastEditTime  2019/11/5 15:38
     * Deprecated   false
     */
    fun getGoBuyMessage(price: String, g_id: String, af_id: String, as_id: String, number: String, ma_id: String,cycle: String) {
        val map = HashMap<String, String>()
        map["price"] = price
        map["g_id"] = g_id
        map["af_id"] = af_id
        map["as_id"] = as_id
        map["number"] = number
        map["ma_id"] = ma_id
        map["cycle"] = cycle
        mModel.getGoBuy(price, g_id, af_id, as_id, number, SPUtils.getInstance().getString(SPConstant.TOKEN), ma_id,cycle, SignUtils.getSign(map))
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(object : ErrorHandleSubscriber<BasePhpResponse<OrderFee>>(mErrorHandler) {
                    override fun onNext(t: BasePhpResponse<OrderFee>) {
                        if (t.status.equals(Api.RequestPhpSuccess)) {
                            t.data?.let { mRootView.getGoBuyMessageSuccess(it) }
                        } else {
                            ToastUtils.makeText(mApplication, t.msg)
                        }
                    }

                })
    }


    override fun onDestroy() {
        super.onDestroy();
    }
}
