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
import com.yinduowang.installment.app.utils.MD5Util
import com.yinduowang.installment.app.utils.RxUtils
import com.yinduowang.installment.app.utils.SignUtils
import com.yinduowang.installment.app.utils.ToastUtils
import com.yinduowang.installment.mvp.contract.PaymentTypeContract
import com.yinduowang.installment.mvp.model.entity.BasePhpResponse
import com.yinduowang.installment.mvp.model.entity.BaseResponse
import com.yinduowang.installment.mvp.model.entity.CheckQuotaEntity
import com.yinduowang.installment.mvp.model.entity.GetOrderPay
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber
import java.util.*
import javax.inject.Inject


/**
 * Description：支付方式Presenter
 * Author：田羽衡
 * Version：<v1.0，2019/11/1 >
 * LastEditTime：2019/11/1
 * LastEditors:
 * Deprecated： false
 */
@ActivityScope
class PaymentTypePresenter
@Inject
constructor(model: PaymentTypeContract.Model, rootView: PaymentTypeContract.View) :
        BasePresenter<PaymentTypeContract.Model, PaymentTypeContract.View>(model, rootView) {
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

    /*
     * 描述：〈检查额度〉
     * 修改⼈：〈田羽衡〉
     * 版本号：<1.1，20191029>
     * 修改时间  20191029
     * 弃⽤： <false>
     */
    fun goCheckQuota() {
        mRootView.showLoading()
        mModel.goCheckQuota(SPUtils.getInstance().getString(SPConstant.TOKEN), MD5Util.getMD5String(SPUtils.getInstance().getString(SPConstant.TOKEN)))
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(object : ErrorHandleSubscriber<BaseResponse<CheckQuotaEntity>>(mErrorHandler) {
                    override fun onNext(t: BaseResponse<CheckQuotaEntity>) {
                        if (TextUtils.equals(t.code, Api.RequestSuccess)) {
                            //    0正常 1未认证 2额度计算中
                            mRootView.checkQuotaSuccess(t.data?.status.toString(), t.data?.buttonMsg.toString(), t.data?.pageMsg.toString(), t.data?.valueAddedService.toString())
                        }
                        mRootView.hideLoading()
                    }


                })

    }

    /**
     * Description：获取订单详情
     * Author：田羽衡
     * Version：<v1.0，2019/10/31 8:38>
     * params:订单id 期数
     * return：
     * LastEditTime：2019/10/31 8:38
     * Deprecated： false
     */
    fun getOrderDetail(id: String, cycle: String) {
        mRootView.showLoading()
        val map = HashMap<String, String>()
        map["o_id"] = id
        map["cycle"] = cycle
        mModel.getShopDetailForPay(id, cycle, SPUtils.getInstance()
                .getString(SPConstant.TOKEN), SignUtils.getSign(map)).compose(RxUtils.applySchedulers(mRootView))
                .subscribe(object : ErrorHandleSubscriber<BasePhpResponse<GetOrderPay>>(mErrorHandler) {
                    override fun onNext(t: BasePhpResponse<GetOrderPay>) {
                        if (TextUtils.equals(t.status, Api.RequestPhpSuccess)) {
                            t.data?.let { it ->
                                mRootView.getShopOredeDetailSuccess(t.data!!)
                                goCheckQuota()
                            }
                        } else if (t.status.equals("2")) {
//                                订单变化弹窗
                            mRootView.dialogAndGoOrderList(t.msg)
                        } else {
                            ToastUtils.makeText(mApplication, t.msg)
                        }
                        mRootView.hideLoading()
                    }

                })
    }


    /**
     * Description：支付接口( 0数据或系统错误1成功2密码错误3密码错5次4存在逾期账单5库存不足6订单发生变化)
     * Author：田羽衡
     * Version：<v1.0，2019/10/31 8:36>
     * params:订单ID，全部金额，分期，密码，config_id（又上个接口提供）
     * return：
     * LastEditTime：2019/10/31 8:36
     * Deprecated： false
     */
    fun postPay(o_id: String, total_money: String, cycle: String, password: String, config_id: String) {
        var money = total_money.replace(",", "")
        money = money.replace(".", "")
        val map = HashMap<String, String>()
        map["cycle"] = cycle
        map["o_id"] = o_id
        map["password"] = password
        map["total_money"] = money
        map["config_id"] = config_id
        mModel.postPay(o_id, SPUtils.getInstance().getString(SPConstant.TOKEN), money, cycle, password, config_id, SignUtils.getSign(map)).compose(RxUtils.applySchedulers(mRootView))
                .safeSubscribe(object : ErrorHandleSubscriber<BasePhpResponse<*>>(mErrorHandler) {
                    override fun onNext(t: BasePhpResponse<*>) {
                        var txt = t.msg
                        if (TextUtils.equals(t.status, "0")) {
                            t.data?.let { it ->
                                ToastUtils.makeText(mApplication, t.msg)
                            }
                        } else if (TextUtils.equals(t.status, "1")) {
                            mRootView.postPaySuccess()

                        } else if (TextUtils.equals(t.status, "2")) {
                            mRootView.showPassWordErrorFive(txt)

                        } else if (TextUtils.equals(t.status, "3")) {
                            mRootView.showPassWordError(txt)

                        } else if (TextUtils.equals(t.status, "4")) {
                            mRootView.toastAndGoOlderLlist(t.msg)

                        } else if (TextUtils.equals(t.status, "5")) {
                            mRootView.dialogAndGoOrderList(txt)


                        } else if (TextUtils.equals(t.status, "6")) {
                            mRootView.dialogAndGoOrderList(txt)


                        } else if (TextUtils.equals(t.status, "7")) {
                            mRootView.dialogAndGoOrderList(txt)


                        } else if (TextUtils.equals(t.status, "8")) {
                            ToastUtils.makeText(mApplication, t.msg)

                        } else if (TextUtils.equals(t.status, "9")) {
                            ToastUtils.makeText(mApplication, t.msg)

                        } else if (TextUtils.equals(t.status, "10")) {
                            ToastUtils.makeText(mApplication, t.msg)

                        } else if (TextUtils.equals(t.status, "11")) {
                            mRootView.showOverdue(txt)
                        } else if (TextUtils.equals(t.status, "12")) {
                            mRootView.dialogAndGoOrderList(t.msg)
                        }
                    }

                })
    }

}

//    2 错误次数已达上限，请明天再试
//    3 支付密码不正确
//    4 您暂时不能购买，请过一段时间再来尝试
//    5 商品信息发生变化
//    6 商品库存不足
//    7 商品价格发生变化
//    8 您申请的借款金额已超过可用额度
//    9 今日额度已抢完
//    10 您有一笔业务正在审核中，请等待审核
//    11 您有一笔账单已逾期，请及时还款。以免影响您的信用和正常消费
//    12 订单变化弹窗