package com.yinduowang.installment.mvp.ui.activity

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.integration.AppManager
import com.jess.arms.utils.ArmsUtils
import com.yinduowang.installment.R
import com.yinduowang.installment.app.utils.ToastUtils
import com.yinduowang.installment.di.component.DaggerAuthorizationResultsComponent
import com.yinduowang.installment.di.module.AuthorizationResultsModule
import com.yinduowang.installment.mvp.contract.AuthorizationResultsContract
import com.yinduowang.installment.mvp.presenter.AuthorizationResultsPresenter
import kotlinx.android.synthetic.main.activity_authorization_results.*


/**
 * ================================================
 * Description:宝付授权结果页面
 * <p>
 * Created by MVPArmsTemplate on 08/12/2019 20:23
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
class AuthorizationResultsActivity : BaseActivity<AuthorizationResultsPresenter>(), AuthorizationResultsContract.View, View.OnClickListener {


    private var status = ""

    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerAuthorizationResultsComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .authorizationResultsModule(AuthorizationResultsModule(this))
                .build()
                .inject(this)
    }


    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_authorization_results //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }


    override fun initData(savedInstanceState: Bundle?) {
        intent.getStringExtra("status")?.let {
            status = intent.getStringExtra("status")
        }
        titleBar.showTitleAndBack("授权结果", View.OnClickListener {
            if (!TextUtils.equals("2", status)) {
                AppManager.getAppManager().killActivity(BaofuWithholdActivity::class.java)
                AppManager.getAppManager().killActivity(CommWebViewActivity::class.java)
            }
            finish()
        })
        tvBtnSuccess.setOnClickListener(this)
        tvBtnError.setOnClickListener(this)



        when (status) {
            "1" -> {
                clSuccess.visibility = View.VISIBLE
                clError.visibility = View.GONE
            }
            "2" -> {
                ToastUtils.makeText(this, intent.getStringExtra("errorMsg"))
                clSuccess.visibility = View.GONE
                clError.visibility = View.VISIBLE
            }
        }
    }

    override fun onBackPressed() {
        if (!TextUtils.equals("2", status)) {
            AppManager.getAppManager().killActivity(BaofuWithholdActivity::class.java)
            AppManager.getAppManager().killActivity(CommWebViewActivity::class.java)
        }
        super.onBackPressed()
    }

    override fun onClick(v: View?) {
        if (!TextUtils.equals("2", status)) {
            AppManager.getAppManager().killActivity(BaofuWithholdActivity::class.java)
            AppManager.getAppManager().killActivity(CommWebViewActivity::class.java)
        }
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
}
