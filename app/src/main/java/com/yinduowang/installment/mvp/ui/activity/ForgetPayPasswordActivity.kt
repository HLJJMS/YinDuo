package com.yinduowang.installment.mvp.ui.activity

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.text.InputFilter
import android.text.Spanned
import android.text.TextUtils
import android.view.View
import android.view.View.GONE
import androidx.core.content.ContextCompat
import com.blankj.utilcode.util.SPUtils
import com.jakewharton.rxbinding3.view.clicks
import com.jakewharton.rxbinding3.widget.textChanges
import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils
import com.yinduowang.installment.R
import com.yinduowang.installment.app.constant.SPConstant
import com.yinduowang.installment.app.utils.LoadingUtils
import com.yinduowang.installment.app.utils.StringUtil
import com.yinduowang.installment.di.component.DaggerForgetPayPasswordComponent
import com.yinduowang.installment.di.module.ForgetPayPasswordModule
import com.yinduowang.installment.mvp.contract.ForgetPayPasswordContract
import com.yinduowang.installment.mvp.presenter.ForgetPayPasswordPresenter
import com.yinduowang.installment.mvp.ui.activity.SetTradePwdActivity.Companion.FORGET_PASSWORD_RESULT
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.activity_forget_pay_password.*
import java.util.concurrent.TimeUnit
import java.util.regex.Pattern

/**
 * @Description:找回支付密码
 * @Author:张利超
 * @Date: 2019-11-1 9:11:28
 * @Version: v1.0, 2019-11-1
 * @LastEditors:张利超
 * @LastEditTime:2019-11-1 9:11:28
 * @Deprecated: false
 */
class ForgetPayPasswordActivity : BaseActivity<ForgetPayPasswordPresenter>(), ForgetPayPasswordContract.View {

    //intentType:0,从设置密码进入的，1是从借款页密码弹窗进入的
    var intentType = ""
    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerForgetPayPasswordComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .forgetPayPasswordModule(ForgetPayPasswordModule(this))
                .build()
                .inject(this)
    }


    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_forget_pay_password //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    //下一步成功回调//0,从设置密码进入的，1是从借款页密码弹窗进入的
    override fun onNextSuccess() {
        if (intentType.equals("0")) {
            setResult(FORGET_PASSWORD_RESULT)
            killMyself()
        } else {
            val intent = Intent(this, SetTradePwdActivity::class.java)
            intent.putExtra("payPswType", "0")
            intent.putExtra("isFinish", "0")
            startActivity(intent)
        }
    }

    /**
     * @description ：初始化控件信息
     * @lastEditor  ：张利超
     * @version     ：v1.0 ,2019/11/1
     * @param null
     * @return      ：
     * @deprecated  ： false
     */
    override fun initData(savedInstanceState: Bundle?) {
        intent.extras?.getString("intentType")?.let {
            intentType = it
        }
        layoutTitle.showTitleAndBack("找回支付密码")
        tv_pay_back_money.text = StringUtil.changeMobile(SPUtils.getInstance().getString(SPConstant.NICK_NAME))
        //获取验证码
        tvVerifyCode.clicks().throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(Consumer {
            tvVerifyCode.text = "正在发送"
            mPresenter?.let { it.getVerifyCode(SPUtils.getInstance().getString(SPConstant.NICK_NAME), "6") }
        })
        //真实名字文字监听
        etRealName.textChanges()
                .subscribe(Consumer<CharSequence> { charSequence ->
                    ivNameclear.visibility = if (TextUtils.isEmpty(charSequence)) View.GONE else View.VISIBLE
                    tvNext.isEnabled = etRealName.text.toString().trim().length > 0 && etIdCard.text.toString().trim().length > 0 && etVerifyCode.text.toString().trim().length > 0

                })
        //监听是否聚焦判断清除键盘显隐
        etRealName.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                ivNameclear.visibility = GONE
            }else {
                if(!etRealName.text.toString().equals("")){
                    ivNameclear.visibility =View.VISIBLE
                }
            }
        }
        ///省份证号文字监听
        etIdCard.textChanges()
                .subscribe(Consumer<CharSequence> { charSequence ->
                    ivIdCardClear.visibility = if (TextUtils.isEmpty(charSequence)) View.GONE else View.VISIBLE
                    tvNext.isEnabled = etRealName.text.toString().trim().length > 0 && etIdCard.text.toString().trim().length > 0 && etVerifyCode.text.toString().trim().length > 0
                })
        //监听是否聚焦判断清除键盘显隐
        etIdCard.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                ivIdCardClear.visibility = GONE
            }else{
                if(!etIdCard.text.toString().equals("")){
                    ivIdCardClear.visibility =View.VISIBLE
                }
            }
        }
        //验证码文字监听
        etVerifyCode.textChanges()
                .subscribe(Consumer<CharSequence> { charSequence ->
                    tvNext.isEnabled = etRealName.text.toString().trim().length > 0 && etIdCard.text.toString().trim().length > 0 && etVerifyCode.text.toString().trim().length > 0
                })
        //清除按钮点击事件处理
        ivNameclear.clicks().subscribe(Consumer {
            etRealName.setText("")
        })
        //清除按钮点击事件处理
        ivIdCardClear.clicks().subscribe(Consumer {
            etIdCard.setText("")
        })
        //下一步击事件处理
        tvNext.clicks().throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(Consumer {
            mPresenter?.let {
                it.forgetPayPsw(SPUtils.getInstance().getString(SPConstant.NICK_NAME),
                        etVerifyCode.text.toString().trim(), etIdCard.text.toString().trim(), etRealName.text.toString().trim())
            }
        })

        //省份证过滤只显示0-9和x
        etIdCard!!.filters = arrayOf<InputFilter>(object : InputFilter {
            override//source:即将输入的内容 dest：原来输入的内容
            fun filter(source: CharSequence, start: Int, end: Int, dest: Spanned, dstart: Int, dend: Int): CharSequence? {
                val sourceContent = source.toString()
                val lastInputContent = dest.toString()
                val number = Pattern.compile("^[0-9]*\$")
                val endNumber = Pattern.compile("^[x0-9X]*\$")
                if (dstart < 17 && number.matcher(sourceContent).matches()) {
                    return sourceContent
                } else if (dstart == 17 && endNumber.matcher(sourceContent).matches()) {
                    if (sourceContent.equals("x")) {
                        return "X";
                    } else {
                        return sourceContent;
                    }
                } else {
                    return "";
                }
            }
        })


    }

    override fun onVerifyCode() {
        timer.start()
    }

    /**
     * @description ：获取验证码定时器初始化
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
            tvVerifyCode.text = "${(millisUntilFinished / 1000).toString()}秒后重发"
            tvVerifyCode.setTextColor(ContextCompat.getColor(baseContext, R.color.color_ADADAD))
            tvVerifyCode.setClickable(false)
        }

        override fun onFinish() {
            tvVerifyCode.setText("重新发送")
            tvVerifyCode.setTextColor(ContextCompat.getColor(baseContext, R.color.colorAccent))
            tvVerifyCode.setClickable(true)
        }
    }


    override fun showLoading() {
        LoadingUtils.show(this)
    }

    override fun hideLoading() {
        LoadingUtils.dismiss(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.cancel()
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
