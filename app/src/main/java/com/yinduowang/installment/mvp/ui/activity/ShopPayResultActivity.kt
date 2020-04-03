package com.yinduowang.installment.mvp.ui.activity

import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import android.view.View
import com.jakewharton.rxbinding3.view.clicks
import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.integration.AppManager
import com.jess.arms.utils.ArmsUtils
import com.yinduowang.installment.R
import com.yinduowang.installment.app.utils.LoadingUtils
import com.yinduowang.installment.di.component.DaggerShopPayResultComponent
import com.yinduowang.installment.di.module.ShopPayResultModule
import com.yinduowang.installment.mvp.contract.ShopPayResultContract
import com.yinduowang.installment.mvp.model.event.ShopAllOrderEvent
import com.yinduowang.installment.mvp.presenter.ShopPayResultPresenter
import kotlinx.android.synthetic.main.activity_shop_pay_result.*
import org.simple.eventbus.EventBus
import java.util.concurrent.TimeUnit

/**
 * Description：商城付款结果
 * Author：田羽衡
 * Version：<v1.0，2019/11/1 >
 * LastEditTime：2019/11/1
 * LastEditors:
 * Deprecated： false
 */
class ShopPayResultActivity : BaseActivity<ShopPayResultPresenter>(), ShopPayResultContract.View {
    //    总金额
    var rmbTotal = ""
    //    判断失败成功用
    var type = ""
    //    订单id
    var id = ""

    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerShopPayResultComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .shopPayResultModule(ShopPayResultModule(this))
                .build()
                .inject(this)
    }


    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_shop_pay_result //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }


    override fun initData(savedInstanceState: Bundle?) {
        titleBarPayResult.showTitleAndBack("付款结果", R.mipmap.ic_back,View.OnClickListener {
            EventBus.getDefault().post(ShopAllOrderEvent())
            finish()
        })
        intent.getStringExtra("o_id").let { id = it }
        intent.getStringExtra("type").let { type = it }
        intent.getStringExtra("rmbTotal").let { rmbTotal = it }
        tvRmb.text = rmbTotal
        if (type.equals("success")) {
            tvResult.text = "付款成功"
            ivNike.setImageResource(R.mipmap.ic_nike_green)
            tvPayTypeTxt.text = "付款方式：授信额度支付"
            tvPayTypeSuccessTxt.visibility = View.VISIBLE
            tvUp.text = "返回商城"
            //  进入商城（首页）
            tvUp.clicks()
                    .throttleFirst(500, TimeUnit.MILLISECONDS)
                    .subscribe {
                        val intent = Intent(this, MainActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                        intent.putExtra("goShop", "shop")
                        startActivity(intent)
                        finish()
                    }
//            进入我的订单全部订单
            tvDown.clicks()
                    .throttleFirst(500, TimeUnit.MILLISECONDS)
                    .subscribe {
                        AppManager.getAppManager().killActivity(ShopAllOrderActivity::class.java)
                        AppManager.getAppManager().killActivity(ShopOrderDetailedActivity::class.java)
                        val intent = Intent(this@ShopPayResultActivity, ShopAllOrderActivity::class.java)
                        intent.putExtra("orderType", "99")
                        intent.putExtra("intentType", "ShopPayResultActivity")
                        startActivity(intent)
                        finish()
                    }
        } else {
            tvResult.text = "等待付款"
            ivNike.setImageResource(R.mipmap.ic_mark)
            tvPayTypeTxt.text = "订单将为您保留，请尽快完成付款"
            tvPayTypeSuccessTxt.visibility = View.GONE
            tvUp.text = "重新付款"
//          进入支付方式
            tvUp.clicks()
                    .throttleFirst(500, TimeUnit.MILLISECONDS)
                    .subscribe {
                        var intent = Intent(this, PaymentTypeActivity::class.java)
                        intent.putExtra("o_id", id)
                        intent.putExtra("rmb", rmbTotal)
                        intent.putExtra("intentType", "ShopPayResultActivity")
                        startActivity(intent)
                        finish()
                    }
//            进入我的订单代付款
            tvDown.clicks()
                    .throttleFirst(500, TimeUnit.MILLISECONDS)
                    .subscribe {
                        AppManager.getAppManager().killActivity(ShopAllOrderActivity::class.java)
                        AppManager.getAppManager().killActivity(ShopOrderDetailedActivity::class.java)
                        val intent = Intent(this@ShopPayResultActivity, ShopAllOrderActivity::class.java)
                        intent.putExtra("orderType", "0")
                        intent.putExtra("intentType", "ShopPayResultActivity")
                        startActivity(intent)
                        finish()
                    }
        }
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
}
