package com.yinduowang.installment.mvp.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.jakewharton.rxbinding3.view.clicks
import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils
import com.yinduowang.installment.R
import com.yinduowang.installment.di.component.DaggerPayBackMoneyResultComponent
import com.yinduowang.installment.di.module.PayBackMoneyResultModule
import com.yinduowang.installment.mvp.contract.PayBackMoneyResultContract
import com.yinduowang.installment.mvp.presenter.PayBackMoneyResultPresenter
import kotlinx.android.synthetic.main.activity_pay_back_money_result.*
import java.util.concurrent.TimeUnit

/**
 * Description：还款结果
 * Author：田羽衡
 * Version：<v1.0，2019/11/1 >
 * LastEditTime：2019/11/1
 * LastEditors:
 * Deprecated： false
 */

class PayBackMoneyResultActivity : BaseActivity<PayBackMoneyResultPresenter>(), PayBackMoneyResultContract.View {
    var code: String? = ""
    var rmb: String? = ""
    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerPayBackMoneyResultComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .payBackMoneyResultModule(PayBackMoneyResultModule(this))
                .build()
                .inject(this)
    }


    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_pay_back_money_result //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }


    override fun initData(savedInstanceState: Bundle?) {
        titleBar2.showTitleAndBack(R.string.pay_back_money_result, object : View.OnClickListener {
            override fun onClick(v: View?) {
                //返回拦截判断是否进我的还是商城首页
                val mIntent = Intent(this@PayBackMoneyResultActivity, MainActivity::class.java)
                mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                if (!intent.getBooleanExtra("goMy", false)) {
                    mIntent.putExtra("goShop", "shop")
                } else {
                    mIntent.putExtra("goMy", true)
                }
                startActivity(mIntent)
                finish()
            }

        })
        intent.getStringExtra("code")?.let { code = it }
        intent.getStringExtra("RMB")?.let { rmb = it }
        tvRepayRmb.text = "还款金额 " + rmb + "元"
//        处理状态 1、处理中 2、失败 3、成功
        if (code.equals("1")) {
            bankDoing()
        } else if (code.equals("2")) {
            payFail()
        } else if (code.equals("3")) {
            paySuccess()
        }
        if (!intent.getBooleanExtra("goMy", false)) {
            tvConfirm.clicks()
                    .throttleFirst(500, TimeUnit.MILLISECONDS)
                    .subscribe {
                        val intent = Intent(this, MainActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                        intent.putExtra("goShop", "shop")
                        startActivity(intent)
                        finish()
                    }
            tvPayOk.clicks()
                    .throttleFirst(500, TimeUnit.MILLISECONDS)
                    .subscribe {
                        val intent = Intent(this, MainActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                        intent.putExtra("goShop", "shop")
                        startActivity(intent)
                        finish()
                    }
        } else {
            tvConfirm.clicks()
                    .throttleFirst(500, TimeUnit.MILLISECONDS)
                    .subscribe {
                        val intent = Intent(this, MainActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                        intent.putExtra("goMy", true)
                        startActivity(intent)
                        finish()
                    }
            tvPayOk.clicks()
                    .throttleFirst(500, TimeUnit.MILLISECONDS)
                    .subscribe {
                        val intent = Intent(this, MainActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                        intent.putExtra("goMy", true)
                        startActivity(intent)
                        finish()
                    }
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        //返回拦截判断是否进我的还是商城首页
        val mIntent = Intent(this@PayBackMoneyResultActivity, MainActivity::class.java)
        mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        if (!intent.getBooleanExtra("goMy", false)) {
            mIntent.putExtra("goShop", "shop")
        } else {
            mIntent.putExtra("goMy", true)
        }
        startActivity(mIntent)
        finish()
    }

    override fun showLoading() {

    }

    override fun hideLoading() {

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
     * Description：   支付失败
     * Author：田羽衡
     * Version：<v1.0，2019/11/1 10:27>
     * params:
     * return：
     * LastEditTime：2019/11/1 10:27
     * Deprecated： false
     */
    fun payFail() {
        groupPay.visibility = View.VISIBLE
        groupBank.visibility = View.GONE
        tvPayResultTxt.setText("还款失败")
        tvPayResultTxt.setPadding(0, ArmsUtils.dip2px(this, 8f), 0, 0)
        var lp = imgResult.layoutParams as ConstraintLayout.LayoutParams
        lp.setMargins(0, ArmsUtils.dip2px(this, 76f), 0, 0)
        lp.width = ArmsUtils.dip2px(this, 147f)
        lp.height = ArmsUtils.dip2px(this, 120f)
        imgResult.layoutParams = lp
        imgResult.setImageResource(R.mipmap.ic_pay_fail)


    }



    /**
     * Description： 支付成功
     * Author：田羽衡
     * Version：<v1.0，2019/11/1 10:27>
     * params:
     * return：
     * LastEditTime：2019/11/1 10:27
     * Deprecated： false
     */
    fun paySuccess() {
        groupPay.visibility = View.VISIBLE
        groupBank.visibility = View.GONE


    }

    /**
     * Description：银行在处理
     * Author：田羽衡
     * Version：<v1.0，2019/11/1 10:27>
     * params:
     * return：
     * LastEditTime：2019/11/1 10:27
     * Deprecated： false
     */
    fun bankDoing() {
        groupPay.visibility = View.GONE
        groupBank.visibility = View.VISIBLE


    }


}
