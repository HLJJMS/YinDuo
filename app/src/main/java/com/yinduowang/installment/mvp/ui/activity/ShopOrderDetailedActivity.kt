package com.yinduowang.installment.mvp.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.blankj.utilcode.util.SPUtils
import com.jakewharton.rxbinding3.view.clicks
import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils
import com.jess.arms.utils.PermissionUtil
import com.tbruyelle.rxpermissions2.RxPermissions
import com.yinduowang.installment.R
import com.yinduowang.installment.app.constant.SPConstant
import com.yinduowang.installment.app.utils.*
import com.yinduowang.installment.app.utils.gosetting.PermissionSetting
import com.yinduowang.installment.app.widget.BaseDialog
import com.yinduowang.installment.di.component.DaggerShopOrderDetailedComponent
import com.yinduowang.installment.di.module.ShopOrderDetailedModule
import com.yinduowang.installment.mvp.contract.ShopOrderDetailedContract
import com.yinduowang.installment.mvp.model.entity.ShopOrderDetailedEntity
import com.yinduowang.installment.mvp.model.event.ShopAllOrderEvent
import com.yinduowang.installment.mvp.presenter.ShopOrderDetailedPresenter
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.activity_shop_order_detailed.*
import org.simple.eventbus.EventBus
import java.util.concurrent.TimeUnit


/**
 * ================================================
 * Description:商城订单详细
 * <p>
 * Created by MVPArmsTemplate on 08/01/2019 21:04
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */

class ShopOrderDetailedActivity : BaseActivity<ShopOrderDetailedPresenter>(), ShopOrderDetailedContract.View {


    // 商品详情标题 标题浮于web页面上方 web页面紧接状态栏
    val TYPE_TITLE_DETAIL = "detail"
    var total_money = ""
    val KEY_URL_NAME = "url"
    // web页面跳转标题类型
    val KEY_IS_AUTHENTICATION = "isAuthentication"
    private var status: Int = -3
    var o_id: String = ""
    var prodect_id: String = ""
    var tel: String = ""
    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerShopOrderDetailedComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .shopOrderDetailedModule(ShopOrderDetailedModule(this))
                .build()
                .inject(this)
    }


    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_shop_order_detailed //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    /**
     * 描  述：初始化控件信息
     * 修改人：张利超
     * 版本号：<v1.0，2019/10/30 16:08>
     * @param null
     * 返回值：
     * 修改时间：2019/10/30 16:08
     * 弃⽤： false
     */
    @SuppressLint("CheckResult")
    override fun initData(savedInstanceState: Bundle?) {
        o_id = intent.getStringExtra("orderId")
        titlebar.showTitleAndBack("订单详情")
        qmuiAddress.shadowElevation = ArmsUtils.dip2px(this, 8f)
        qmuiGoodsInfo.shadowElevation = ArmsUtils.dip2px(this, 8f)
        qmiuiOrderMoeny.shadowElevation = ArmsUtils.dip2px(this, 8f)
        qmuiShopOrder.shadowElevation = ArmsUtils.dip2px(this, 8f)
        //退换售后按钮
        tvRefundOrAfterSale.clicks().throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(Consumer {
            showAfterShopDialog()
        })
        //取消订单按钮
        tvCancleOrder.clicks().throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(Consumer {
            if (status == 2 || status == 3) {//进入查看物流页面
                var intent = Intent(this, MyLogisticsActivity::class.java)
                intent.putExtra("orderId", o_id)
                startActivity(intent)
            } else {//取消订单按钮
                showAlertView(o_id, "您确认取消此订单？", "0")
            }

        })
        //立即付款按钮
        tvConfirmPay.clicks().throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(Consumer {
            if (status == 2) {//确认收货
                showAlertView(o_id, "您确认收到此订单产品？", "1")
            } else if (status == 0) {//待付款  跳转付款界面
                goPaymentTypeActivity()
            } else if (status == -2 || status == -1 || status == -3) {//再次购买
                //转跳到商品详情页面
                OpenWebViewUtils.openWebViewGoodsDetails(this@ShopOrderDetailedActivity, prodect_id, true)
            }
        })
        //客服按钮
        llContactCustomer.clicks().throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(Consumer {
            //跳转至美洽客服
            mPresenter?.let {
                GotoMQChatUtils.gotoMQChat(this, SPUtils.getInstance().getString(SPConstant.NICK_NAME), it?.mErrorHandler)
            }

        })
        //拨打电话按钮
        llCallPhone.clicks().throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(Consumer {
            //跳转至打电话
            callPhoneAlert()

        })

    }

    /**
     * 描  述：返回此页面进行数据刷新调用此函数
     * 修改人：张利超
     * 版本号：<v1.0，2019/10/30 16:13>
     * @param null
     * 返回值：
     * 修改时间：2019/10/30 16:13
     * 弃⽤： false
     */
    override fun onResume() {
        super.onResume()
        mPresenter?.let { it.getShopOrderDetailed(o_id) }
    }


    /**
     * 描  述：拨打电话弹窗
     * 修改人：张利超
     * 版本号：<v1.0，2019/10/30 16:13>
     * @param null
     * 返回值：
     * 修改时间：2019/10/30 16:13
     * 弃⽤： false
     */
    private fun callPhoneAlert() {
        if (!TextUtils.isEmpty(tel)) {
            BaseDialog(this@ShopOrderDetailedActivity)
                    .builder()
                    .setCancelable(false)
                    .setTitle(tel)
                    .setBtnLeft("取消")
                    .setBtnRight("呼叫", object : BaseDialog.BaseDialogBuilder.BtnClickListener {
                        override fun onClick(dialog: BaseDialog) {
                            gotoCallPhone()
                        }
                    })
                    .create()
                    .show()
        }
    }

    /**
     * 描  述：是否申请售后弹窗
     * 修改人：张利超
     * 版本号：<v1.0，2019/10/30 16:14>
     * @param null
     * 返回值：
     * 修改时间：2019/10/30 16:14
     * 弃⽤： false
     */
    private fun showAfterShopDialog() {
        BaseDialog(this@ShopOrderDetailedActivity)
                .builder()
                .setCancelable(false)
                .setTitle(tel)
                .setBtnLeft("否")
                .setTitle("您确认申请退换/售后？")
                .setBtnRight("是", object : BaseDialog.BaseDialogBuilder.BtnClickListener {
                    override fun onClick(dialog: BaseDialog) {
                        mPresenter?.let { it.orderRetreat(o_id) }
                    }
                })
                .create()
                .show()
    }

    /**
     * 描  述：去打电话的逻辑首先请求权限然后执行隐式intent进行调用系统方法
     * 修改人：张利超
     * 版本号：<v1.0，2019/10/30 16:14>
     * @param null
     * 返回值：
     * 修改时间：2019/10/30 16:14
     * 弃⽤： false
     */
    private fun gotoCallPhone() {
        PermissionUtil.callPhone(object : PermissionUtil.RequestPermission {
            @SuppressLint("MissingPermission")
            override fun onRequestPermissionSuccess() {
                // 设置初始化显示页面
                var intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + tel.replace("-".toRegex(), "").trim({ it <= ' ' }).toString()))
                startActivity(intent)
            }

            override fun onRequestPermissionFailure(permissions: List<String>) {
                ToastUtils.makeText(this@ShopOrderDetailedActivity, "电话权限获取失败")
            }

            override fun onRequestPermissionFailureWithAskNeverAgain(permissions: List<String>) {
                PermissionSetting.toPermissionSetting(this@ShopOrderDetailedActivity)
            }
        },
                RxPermissions(this@ShopOrderDetailedActivity),
                mPresenter?.mErrorHandler
        )
    }


    /**
     * 描  述：订单取消或退货，刷新页面数据信息`
     * 修改人：张利超
     * 版本号：<v1.0，2019/10/30 16:15>
     * @param null
     * 返回值：
     * 修改时间：2019/10/30 16:15
     * 弃⽤： false
     */
    override fun refreshPageInfo() {
        EventBus.getDefault().post(ShopAllOrderEvent())
        mPresenter?.let { it.getShopOrderDetailed(o_id) }
    }

    //确认收货回调
    override fun comnfirmAccept() {
        EventBus.getDefault().post(ShopAllOrderEvent())
        startActivity(Intent(this, ShopConfirmReceivingGoodsActivity::class.java))
    }

    /**
     * 描  述：这里返回详细页面的全部信息此时进行数据初始化操作
     * 修改人：张利超
     * 版本号：<v1.0，2019/10/30 16:16>
     * @param null
     * 返回值：
     * 修改时间：2019/10/30 16:16
     * 弃⽤： false
     */
    override fun returnOrederDetailed(entity: ShopOrderDetailedEntity) {
//        -2已退货-1已取消0待付款(已下单)1待发货(已支付)2待收货(已发货)3已完成(已收货)
        if (entity.goods_list.size > 0) {
            prodect_id = entity.goods_list.get(0).id
        }
        status = entity.status
        tel = entity.tel
        tvName.text = entity.name
        tvPhone.text = StringUtil.changeMobile(entity.mobile)
        tvAddress.text = entity.province + entity.city + entity.area + entity.address
        //上方状态显示及下方按钮图标判断
        tvOrderStatusName.text = entity.status_name
        csBottom.visibility = View.VISIBLE
        when (entity.status) {
            //0待付款(已下单)
            0 -> {
                ivStatus.setImageResource(R.mipmap.ic_shop_to_wait)
                tvConfirmPay.visibility = View.VISIBLE
                tvCancleOrder.visibility = View.VISIBLE
                tvRefundOrAfterSale.visibility = View.GONE
                //判断支付方式是否显示
                tvPayStyleLeft.visibility = View.GONE
                tvPayStyleRight.visibility = View.GONE
            }
            //1待发货(已支付)
            1 -> {
                ivStatus.setImageResource(R.mipmap.ic_shop_to_wait)
                tvRefundOrAfterSale.visibility = View.VISIBLE
                tvConfirmPay.visibility = View.GONE
                tvCancleOrder.visibility = View.GONE
                tvPayStyleLeft.visibility = View.VISIBLE
                tvPayStyleRight.visibility = View.VISIBLE
            }
            //2待收货(已发货)
            2 -> {
                ivStatus.setImageResource(R.mipmap.ic_shop_to_wait)
                tvRefundOrAfterSale.visibility = View.VISIBLE
                tvConfirmPay.visibility = View.VISIBLE
                tvCancleOrder.visibility = View.VISIBLE
                tvConfirmPay.text = "确认收货"
                tvCancleOrder.text = "查看物流"
                tvCancleOrder.setTextColor(ArmsUtils.getColor(this, R.color.color_333333))
                tvPayStyleLeft.visibility = View.VISIBLE
                tvPayStyleRight.visibility = View.VISIBLE
            }
            //3已完成(已收货)
            3 -> {
                ivStatus.setImageResource(R.mipmap.ic_shop_to_success)
                tvRefundOrAfterSale.visibility = View.VISIBLE
                tvCancleOrder.visibility = View.VISIBLE
                tvConfirmPay.visibility = View.GONE
                tvCancleOrder.text = "查看物流"
                tvCancleOrder.setTextColor(ArmsUtils.getColor(this, R.color.color_333333))
                tvPayStyleLeft.visibility = View.VISIBLE
                tvPayStyleRight.visibility = View.VISIBLE
            }
            //审核中
            4 -> {
                csBottom.visibility = View.GONE
                ivStatus.setImageResource(R.mipmap.ic_shop_to_wait)
                tvRefundOrAfterSale.visibility = View.GONE
                tvCancleOrder.visibility = View.GONE
                tvConfirmPay.visibility = View.GONE
                tvPayStyleLeft.visibility = View.GONE
                tvPayStyleRight.visibility = View.GONE
            }
            //-1已取消
            -1 -> {
                ivStatus.setImageResource(R.mipmap.ic_shop_to_fialed)
                tvConfirmPay.visibility = View.VISIBLE
                tvConfirmPay.text = "再次购买"
                tvCancleOrder.visibility = View.GONE
                tvRefundOrAfterSale.visibility = View.GONE
                tvPayStyleLeft.visibility = View.GONE
                tvPayStyleRight.visibility = View.GONE
            }
            //-审核驳回
            -3 -> {
                ivStatus.setImageResource(R.mipmap.ic_shop_to_fialed)
                tvConfirmPay.visibility = View.VISIBLE
                tvConfirmPay.text = "再次购买"
                tvCancleOrder.visibility = View.GONE
                tvRefundOrAfterSale.visibility = View.GONE
                tvPayStyleLeft.visibility = View.GONE
                tvPayStyleRight.visibility = View.GONE
            }
        }
        //对图标进行非空判断
        entity.goods_list.firstOrNull()?.let {
            LoadImageUtils.showImage(this, it.thumb, ivGoodsPic)
            tvGoodsTitle.text = it.name
            it.av_names.firstOrNull()?.let { ib ->
                tvGoodsType.text = ib
            }

            var type = StringBuffer()
            for (item in entity.goods_list.get(0).av_names) {
                type.append(item).append(" ")
            }
            tvGoodsType.text = type.toString()
            if (it.price.length > 3) {
                tvTotalPrice.text = it.price.subSequence(0, it.price.length - 3)
                tvTotalPricePoint.text = it.price.subSequence(it.price.length - 3, it.price.length)
            }
            tvGoodsCounts.text = "×${it.number}"
        }
        //商品价格
        tvGoodsTotal.text = "¥ ${entity.goods_price}"
        tvGoodsMoveStyle.text = entity.fee
        tvOrderToatal.text = "¥ ${entity.total_money}"
        total_money = entity.total_money
        tvPayStyleRight.text = entity.type_name
        tvOrderNum.text = entity.sn
        tvPayOrderTime.text = entity.time

    }

    /**
     * 描  述：确定订单和收货共用此弹窗并且
     * 修改人：张利超
     * 版本号：<v1.0，2019/10/30 16:17>
     * @param type =0 取消订单弹窗，1 确定收货弹窗
     * 返回值：
     * 修改时间：2019/10/30 16:17
     * 弃⽤： false
     */
    private fun showAlertView(o_id: String, msg: String, type: String) {
        BaseDialog(this)
                .builder()
                .setCancelable(false)
                .setTitle(msg)
                .setBtnLeft("否")
                .setBtnRight("是", object : BaseDialog.BaseDialogBuilder.BtnClickListener {
                    override fun onClick(dialog: BaseDialog) {
                        if (type == "0") {
                            mPresenter?.let { it.cancelShopOrder(o_id) }
                        } else {
                            mPresenter?.let { it.confirmReceipt(o_id) }
                        }
                    }
                })
                .create()
                .show()
    }

    override fun showLoading() {
        LoadingUtils.show(this)
    }

    override fun hideLoading() {
        LoadingUtils.dismiss(this)
    }

    override fun showMessage(message: String) {
        ArmsUtils.snackbarText(message)
    }

    override fun launchActivity(intent: Intent) {
        ArmsUtils.startActivity(intent)
    }

    override fun killMyself() {
        finish()
    }

    /**
     * @Description: 跳转至付款方式
     * @Author: ${t田羽衡}
     * @Date: 20191030
     * @Version: 1.1
     * @LastEditors:田羽衡
     * @LastEditTime:20191030
     * @Deprecated: false
     */
    fun goPaymentTypeActivity() {
        val intent = Intent(this, PaymentTypeActivity::class.java)
        intent.putExtra("o_id", o_id)
        startActivity(intent)
    }

}
