package com.yinduowang.installment.mvp.ui.activity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.jakewharton.rxbinding3.view.clicks
import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils
import com.yinduowang.installment.R
import com.yinduowang.installment.app.utils.LoadingUtils
import com.yinduowang.installment.di.component.DaggerChangePasswordComponent
import com.yinduowang.installment.di.module.ChangePasswordModule
import com.yinduowang.installment.mvp.contract.ChangePasswordContract
import com.yinduowang.installment.mvp.presenter.ChangePasswordPresenter
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.activity_change_password.*
import java.util.concurrent.TimeUnit


/**
 * ================================================
 * Description:修改登录密码
 * <p>
 * Created by MVPArmsTemplate on 07/23/2019 13:53
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */

class ChangePasswordActivity : BaseActivity<ChangePasswordPresenter>(), ChangePasswordContract.View {
    private var isOk1: Boolean = false
    private var isOk2: Boolean = false
    private var isOk3: Boolean = false
    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerChangePasswordComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .changePasswordModule(ChangePasswordModule(this))
                .build()
                .inject(this)
    }


    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_change_password //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }


    override fun initData(savedInstanceState: Bundle?) {
        layoutTitle.showTitleAndBack("修改登录密码")
        etRealName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable) {
                tvNext.isEnabled=etRealName.text.toString().trim().length>5&&etVerifyCode.text.toString().trim().length>5&&etIdCard.text.toString().trim().length>5

            }
        })
        etVerifyCode.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable) {
                tvNext.isEnabled=etRealName.text.toString().trim().length>5&&etVerifyCode.text.toString().trim().length>5&&etIdCard.text.toString().trim().length>5
            }
        })
        etIdCard.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable) {
                tvNext.isEnabled=etRealName.text.toString().trim().length>5&&etVerifyCode.text.toString().trim().length>5&&etIdCard.text.toString().trim().length>5
            }
        })
        tvNext.clicks().throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(Consumer {
            val oldPwd = etRealName.text.toString().trim { it <= ' ' }
            val pwd = etIdCard.text.toString().trim { it <= ' ' }
            val confirmPwd = etVerifyCode.text.toString().trim { it <= ' ' }
            mPresenter?.let{it.changePsw(oldPwd, pwd, confirmPwd)}
        })
        tvForget.clicks().throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(Consumer {
            startActivity(Intent(this, ForgetPasswordActivity::class.java))
        })
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
