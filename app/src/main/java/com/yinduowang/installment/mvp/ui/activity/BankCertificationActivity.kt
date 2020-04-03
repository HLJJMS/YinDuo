package com.yinduowang.installment.mvp.ui.activity

import android.content.Intent
import android.os.Bundle

import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils

import com.yinduowang.installment.di.component.DaggerBankCertificationComponent
import com.yinduowang.installment.di.module.BankCertificationModule
import com.yinduowang.installment.mvp.contract.BankCertificationContract
import com.yinduowang.installment.mvp.presenter.BankCertificationPresenter

import com.yinduowang.installment.R


/**
 * ================================================
 * Description:银行认证
 * <p>
 * Created by MVPArmsTemplate on 08/03/2019 14:55
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
class BankCertificationActivity : BaseActivity<BankCertificationPresenter>(), BankCertificationContract.View {

    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerBankCertificationComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .bankCertificationModule(BankCertificationModule(this))
                .build()
                .inject(this)
    }


    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_bank_certification //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }


    override fun initData(savedInstanceState: Bundle?) {

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
