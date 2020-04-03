package com.yinduowang.installment.mvp.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.text.TextUtils
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import androidx.core.content.ContextCompat
import com.jakewharton.rxbinding3.view.clicks
import com.jakewharton.rxbinding3.widget.textChanges
import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils
import com.jess.arms.utils.DeviceUtils
import com.yinduowang.installment.R
import com.yinduowang.installment.app.utils.LoadingUtils
import com.yinduowang.installment.app.utils.StringUtil
import com.yinduowang.installment.app.utils.ToastUtils
import com.yinduowang.installment.app.widget.AutomaticIntervalEditText
import com.yinduowang.installment.di.component.DaggerForgetPasswordComponent
import com.yinduowang.installment.di.module.ForgetPasswordModule
import com.yinduowang.installment.mvp.contract.ForgetPasswordContract
import com.yinduowang.installment.mvp.presenter.ForgetPasswordPresenter
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.activity_forget_password.*


/**
 * @Description:忘记登录密码
 * @Author:张利超
 * @Date: 2019-11-1 9:11:28
 * @Version: v1.0, 2019-11-1
 * @LastEditors:张利超
 * @LastEditTime:2019-11-1 9:11:28
 * @Deprecated: false
 */

class ForgetPasswordActivity : BaseActivity<ForgetPasswordPresenter>(), ForgetPasswordContract.View {
    override fun onFindBackPasswordSuccess() {
        startActivity(Intent(this, LoginActivity::class.java).putExtra("loginType", "1"))
    }

    var isChecked: Boolean = false
    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerForgetPasswordComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .forgetPasswordModule(ForgetPasswordModule(this))
                .build()
                .inject(this)
    }


    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_forget_password //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }


    @SuppressLint("CheckResult", "SetTextI18n")
    override fun initData(savedInstanceState: Bundle?) {

        titlebar.showTitleAndBack("密码找回")
        titlebar.showBlackBack()
        titlebar.setBottomLineShow(false)

        tv_version_code.text = "当前版本:${DeviceUtils.getVersionName(this)}"
        //添加手机号及密码监听
        etDeliveryName.textWatcher = object : AutomaticIntervalEditText.AutomaticIntervalTextWatcher() {
            override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {
                iv_phone_clear.visibility = if (TextUtils.isEmpty(charSequence)) View.GONE else View.VISIBLE
                tv_title_phone.visibility = if (TextUtils.isEmpty(charSequence)) View.GONE else View.VISIBLE
                tvSubmit.isEnabled = (etDeliveryName.getRealText().length == 11 &&
                        etDeliveryPhone.text.toString().trim().length == 6 &&
                        etDeliveryArea.text.toString().trim().length in 6..20)
            }
        }
        //验证码密码监听
        etDeliveryPhone.textChanges()
                .subscribe(Consumer<CharSequence> { charSequence ->
                    tv_title_verfiy.visibility = if (TextUtils.isEmpty(charSequence)) View.GONE else View.VISIBLE
                    tvSubmit.isEnabled = (etDeliveryName.getRealText().length == 11 &&
                            charSequence.toString().trim().length == 6 &&
                            etDeliveryArea.text.toString().trim().length in 6..20)

                })
        iv_phone_clear.clicks().subscribe(Consumer<Unit> {
            etDeliveryName.setText("")
        })
        //获取验证码
        tv_get_verify_code.clicks().subscribe(Consumer {
            if (TextUtils.isEmpty(etDeliveryName.getRealText().trim())) {
                ToastUtils.makeText(this, "请输入手机号码")
                return@Consumer
            }
            if (!StringUtil.isMobileNO(etDeliveryName.getRealText().trim())) {
                ToastUtils.makeText(this, "手机号码格式不正确")
                return@Consumer
            }
            mPresenter?.let { it.getVerifyCode(etDeliveryName.getRealText().trim(), "3") }
        })
        //密码监听
        etDeliveryArea.textChanges()
                .subscribe(Consumer<CharSequence> { charSequence ->
                    tv_title_psw.visibility = if (TextUtils.isEmpty(charSequence)) View.GONE else View.VISIBLE
                    iv_password_clear.visibility = if (TextUtils.isEmpty(charSequence)) View.GONE else View.VISIBLE
                    tvSubmit.isEnabled = (etDeliveryName.getRealText().trim().length == 11 &&
                            etDeliveryPhone.text.toString().trim().length == 6 &&
                            charSequence.toString().trim().length in 6..20)
                })
        //密码眼睛的点击事件
        iv_password_eye.clicks().subscribe(Consumer {
            if (isChecked) {
                //如果选中，显示密码
                etDeliveryArea.setTransformationMethod(HideReturnsTransformationMethod.getInstance())
                isChecked = false
                etDeliveryArea.setSelection(etDeliveryArea.text.toString().length)
                iv_password_eye.setImageResource(R.mipmap.ic_eye)
            } else {
                //否则隐藏密码
                etDeliveryArea.setTransformationMethod(PasswordTransformationMethod.getInstance())
                isChecked = true
                etDeliveryArea.setSelection(etDeliveryArea.text.toString().length)
                iv_password_eye.setImageResource(R.mipmap.ic_eye_close)
            }
        })
        iv_password_clear.clicks().subscribe(Consumer {
            etDeliveryArea.setText("")
        })
        tvSubmit.clicks().subscribe(Consumer {
            if (!StringUtil.isMobileNO(etDeliveryName.getRealText().trim())) {
                ToastUtils.makeText(this, "手机号码格式不正确")
                return@Consumer
            }
            mPresenter?.let { it.findBackPassword(etDeliveryName.getRealText().trim(),
                    etDeliveryPhone.text.toString().trim(), etDeliveryArea.text.toString().trim()) }
        })


        //        判断手机号焦点控制显示隐藏
        etDeliveryName.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                //                有交点
                if (etDeliveryName.getRealText().isEmpty()) {
                    iv_phone_clear.visibility = View.GONE
                } else {
                    iv_phone_clear.visibility = View.VISIBLE
                }
            } else {
                //              无焦点
                iv_phone_clear.visibility = View.GONE
            }
        }

        //        判断密码焦点控制显示隐藏
        etDeliveryArea.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                //                有交点
                if (etDeliveryArea.text.length > 0) {
                    iv_password_clear.visibility = View.VISIBLE
                } else {
                    iv_password_clear.visibility = View.GONE
                }
            } else {
                //              无焦点
                iv_password_clear.visibility = View.GONE
            }
        }


    }

    /**
     * @description ：初始化获取验证码计时器
     * @lastEditor  ：张利超
     * @version     ：v1.0 ,2019/11/1
     * @param null
     * @return      ：
     * @deprecated  ： false
     */
    internal var timer: CountDownTimer = object : CountDownTimer((60 * 1000 + 500).toLong(), 1000) {
        override fun onTick(millisUntilFinished: Long) {
            if (millisUntilFinished / 1000 == 0L) {
                onFinish()
                return
            }
            tv_get_verify_code.text = "重新发送(${(millisUntilFinished / 1000).toString()})"
            tv_get_verify_code.setTextColor(ContextCompat.getColor(baseContext, R.color.color_ADADAD))
            tv_get_verify_code.setClickable(false)
        }

        override fun onFinish() {
            tv_get_verify_code.setText("获取验证码")
            tv_get_verify_code.setTextColor(ContextCompat.getColor(baseContext, R.color.color_FF7213))
            tv_get_verify_code.setClickable(true)
        }

    }

    //验证码返回
    override fun onVerifyCode() {
        timer.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.cancel()
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
