package com.yinduowang.installment.mvp.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils
import com.yinduowang.installment.R
import com.yinduowang.installment.app.utils.LoadImageUtils
import com.yinduowang.installment.di.component.DaggerApplyLoanSuccessComponent
import com.yinduowang.installment.di.module.ApplyLoanSuccessModule
import com.yinduowang.installment.mvp.contract.ApplyLoanSuccessContract
import com.yinduowang.installment.mvp.presenter.ApplyLoanSuccessPresenter
import kotlinx.android.synthetic.main.activity_apply_loan_success.*


/**
 * @Description:借款-申请借款-申请成功页面
 * @Author: 张利超
 * @Date: 2019-10-31 10:26:52
 * @Version: v1.0, 2019-10-31
 * @LastEditors:张利超
 * @LastEditTime: 2019-10-31 10:26:52
 * @Deprecated: false
 */
class ApplyLoanSuccessActivity : BaseActivity<ApplyLoanSuccessPresenter>(), ApplyLoanSuccessContract.View {
    var isVip = ""
    var url = ""
    var loanId = ""
    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerApplyLoanSuccessComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .applyLoanSuccessModule(ApplyLoanSuccessModule(this))
                .build()
                .inject(this)
    }


    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_apply_loan_success //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    /**
     * @description ：初始页面控件及初始值
     * @lastEditor  ：张利超
     * @version     ：v1.0 ,2019/11/1
     * @param null
     * @return      ：
     * @deprecated  ： false
     */
    override fun initData(savedInstanceState: Bundle?) {
        titleBar.showTitleAndBack("申请成功")
        //获取是否是vip
        intent.extras?.getString("isVip")?.let {
            isVip = intent?.extras!!.getString("isVip").toString()
        }
        //获取图片的url地址
        intent.extras?.getString("url")?.let {
            url = intent.extras!!.getString("url").toString()
        }
        //获取是否是vip
        intent.extras?.getString("loanId")?.let {
            loanId = intent?.extras!!.getString("loanId").toString()
        }
        //确认按钮进入借款详情页面
        tvConfirm.setOnClickListener {
            var intent = Intent(this, CashLoanDetailedActivity::class.java)
            intent.putExtra("loanId", loanId.toInt())
            intent.putExtra("type", "3")
            intent.putExtra("backType", "1")
            startActivity(intent)
            killMyself()
        }
        //如果是vip则展示图片不是则隐藏
        if (isVip.equals("0")) {
            ivVip.visibility = View.VISIBLE
            if (!"".equals(url))
                LoadImageUtils.showImage(this, url, ivVip)
        } else {
            ivVip.visibility = View.GONE
        }
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
}
