package com.yinduowang.installment.mvp.ui.activity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import androidx.core.content.ContextCompat
import com.jakewharton.rxbinding3.view.clicks
import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.integration.AppManager
import com.jess.arms.utils.ArmsUtils
import com.yinduowang.installment.R
import com.yinduowang.installment.app.utils.LoadingUtils
import com.yinduowang.installment.app.utils.ToastUtils
import com.yinduowang.installment.di.component.DaggerSetTradePwdComponent
import com.yinduowang.installment.di.module.SetTradePwdModule
import com.yinduowang.installment.mvp.contract.SetTradePwdContract
import com.yinduowang.installment.mvp.presenter.SetTradePwdPresenter
import kotlinx.android.synthetic.main.activity_set_trade_pwd.*
import java.util.concurrent.TimeUnit


/**
 * @Description:我的-设置-修改或者设置交易密码
 * @Author: 张利超
 * @Date: 2019-11-1 10:26:52
 * @Version: v1.0, 2019-11-1
 * @LastEditors:张利超
 * @LastEditTime: 2019-11-1 10:26:52
 * @Deprecated: false
 */
class SetTradePwdActivity : BaseActivity<SetTradePwdPresenter>(), SetTradePwdContract.View {


    var isFinish = ""
    //判断是否已经设置过密码true为已经设置过则走修改接口不是则走设置接口
    var isSetted = true
    companion object {
        val FORGET_PASSWORD_REQUEST = 1000
        val FORGET_PASSWORD_RESULT = 1001
    }

    /**
     * @description ：设置密码失败返回从新设置密码页
     * @lastEditor  ：张利超
     * @version     ：v1.0 ,2019/11/1
     * @param null
     * @return      ：
     * @deprecated  ： false
     */
    override fun returnSetPswWordFail() {
        tvDesc.text = "请设置6位支付密码"
        edPwdKey.setText("")
        view1.setBackgroundResource(R.color.color_333333)
        edPwdKey1.visibility = View.INVISIBLE
        edPwdKey2.visibility = View.INVISIBLE
        edPwdKey3.visibility = View.INVISIBLE
        edPwdKey4.visibility = View.INVISIBLE
        edPwdKey5.visibility = View.INVISIBLE
        isfirstInput = true
        isOldPsw = false
    }

    /**
     * @description ：设置密码成功回调
     * @lastEditor  ：张利超
     * @version     ：v1.0 ,2019/11/1
     * @param null
     * @return      ：
     * @deprecated  ： false
     */
    override fun returnSetPswWordSuccess() {
        //成功之后如果是从输入密码页面进入则回到输入密码页
        if (isFinish.equals("0")) {
            AppManager.getAppManager().killActivity(ForgetPayPasswordActivity::class.java)
        }
        killMyself()
    }

    /**
     * @description ：验证码原密码回调成功进行下一项设置新密码
     * @lastEditor  ：张利超
     * @version     ：v1.0 ,2019/11/1
     * @param isSuccess：验证是否成功
     * @return      ：
     * @deprecated  ： false
     */
    override fun returnSetPswWord(isSuccess: Boolean) {
        if (isSuccess) {
            tvDesc.text = "请设置6位支付密码"
            layoutTitle.showTitleAndBack(" 设置支付密码")
            tvTip.visibility = View.VISIBLE
            edPwdKey.setText("")
            view1.setBackgroundResource(R.color.color_333333)
            edPwdKey1.visibility = View.INVISIBLE
            edPwdKey2.visibility = View.INVISIBLE
            edPwdKey3.visibility = View.INVISIBLE
            edPwdKey4.visibility = View.INVISIBLE
            edPwdKey5.visibility = View.INVISIBLE
            edPwdKey6.visibility = View.INVISIBLE
            isOldPsw = false
        } else {
            tvDesc.text = "请输入原支付密码"
            edPwdKey.setText("")
            view1.setBackgroundResource(R.color.color_333333)
            edPwdKey1.visibility = View.INVISIBLE
            edPwdKey2.visibility = View.INVISIBLE
            edPwdKey3.visibility = View.INVISIBLE
            edPwdKey4.visibility = View.INVISIBLE
            edPwdKey5.visibility = View.INVISIBLE
            edPwdKey6.visibility = View.INVISIBLE
            isOldPsw = true
        }
    }

    //初始页面类型是判断修改密码还是设置密码
    var payPswType: String = ""
    //同样校验设置新密码还是确认新密码
    var oldPassword: String = ""
    //是否第一次输入密码判断是否走确认支付密码
    var isfirstInput: Boolean = true
    var isOldPsw: Boolean = true
    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerSetTradePwdComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .setTradePwdModule(SetTradePwdModule(this))
                .build()
                .inject(this)
    }


    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_set_trade_pwd //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    /**
     * @description ：初始化控件界面
     * @lastEditor  ：张利超
     * @version     ：v1.0 ,2019/11/1
     * @param null
     * @return      ：
     * @deprecated  ： false
     */
    override fun initData(savedInstanceState: Bundle?) {
        //上页面传来的验证密码类型
        intent.getStringExtra("payPswType")?.let {
            payPswType = it
        }
        intent.getStringExtra("isFinish")?.let {
            isFinish = it
        }
        //通过payPswType 判断是修改支付密码页还是设置支付密码
        if (TextUtils.equals(payPswType, "1")) {
            layoutTitle.showTitleAndBack("修改支付密码")
            tvDesc.text = "请输入原支付密码"
            isOldPsw = true
            isSetted = true
            tvTip.visibility = View.GONE
            tvForgetPayPsw.visibility = View.VISIBLE
        } else {
            isOldPsw = false
            isSetted = false
            layoutTitle.showTitleAndBack(" 设置支付密码")
            tvDesc.text = "请设置6位支付密码"
        }
        //如果是从申请借款页或者商城需要密码忘记密码进入此页需要
        if ("0".equals(isFinish)) {
            isSetted = true
        }
        //忘记密码
        tvForgetPayPsw.clicks().throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe {
                    val inten = Intent(this, ForgetPayPasswordActivity::class.java)
                    inten.putExtra("intentType", "0")
                    startActivityForResult(inten, FORGET_PASSWORD_REQUEST)
                }
        setListener()
    }

    /**
     * @description ：进入忘记密码页面之后返回此页回调
     * @lastEditor  ：张利超
     * @version     ：v1.0 ,2019/11/1
     * @param null
     * @return      ：
     * @deprecated  ： false
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == FORGET_PASSWORD_REQUEST && resultCode == FORGET_PASSWORD_RESULT) {
            isOldPsw = false
            tvTip.visibility = View.VISIBLE
            layoutTitle.showTitleAndBack(" 设置支付密码")
            tvDesc.text = "请设置6位支付密码"
        }
    }

    /**
     * @description ：设置输入密码监听设置密码和修改密码确认密码复用一个监听
     * @lastEditor  ：张利超
     * @version     ：v1.0 ,2019/11/1
     * @param null
     * @return      ：
     * @deprecated  ： false
     */
    private fun setListener() {
        edPwdKey.setFocusable(true);
        edPwdKey.setFocusableInTouchMode(true);
        edPwdKey.requestFocus();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        edPwdKey.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                //分别判断输入数字来显示
                edPwdKey1.visibility = if (s.toString().trim().length > 0) View.VISIBLE else View.INVISIBLE
                if (s.toString().trim().length == 1||s.toString().trim().length == 0) {
                    view1.setBackgroundResource(R.color.color_333333)
                    edPwdKey1.setTextColor(ContextCompat.getColor(this@SetTradePwdActivity, R.color.black))
                } else {
                    view1.setBackgroundResource(R.color.color_D0D0D0)
                    edPwdKey1.setTextColor(ContextCompat.getColor(this@SetTradePwdActivity, R.color.color_D0D0D0))
                }
                edPwdKey2.visibility = if (s.toString().trim().length > 1) View.VISIBLE else View.INVISIBLE
                if (s.toString().trim().length == 2) {
                    view2.setBackgroundResource(R.color.color_333333)
                    edPwdKey2.setTextColor(ContextCompat.getColor(this@SetTradePwdActivity, R.color.black))
                } else {
                    view2.setBackgroundResource(R.color.color_D0D0D0)
                    edPwdKey2.setTextColor(ContextCompat.getColor(this@SetTradePwdActivity, R.color.color_D0D0D0))
                }
                edPwdKey3.visibility = if (s.toString().trim().length > 2) View.VISIBLE else View.INVISIBLE
                if (s.toString().trim().length == 3) {
                    view3.setBackgroundResource(R.color.color_333333)
                    edPwdKey3.setTextColor(ContextCompat.getColor(this@SetTradePwdActivity, R.color.black))
                } else {
                    view3.setBackgroundResource(R.color.color_D0D0D0)
                    edPwdKey3.setTextColor(ContextCompat.getColor(this@SetTradePwdActivity, R.color.color_D0D0D0))
                }
                edPwdKey4.visibility = if (s.toString().trim().length > 3) View.VISIBLE else View.INVISIBLE
                if (s.toString().trim().length == 4) {
                    view4.setBackgroundResource(R.color.color_333333)
                    edPwdKey4.setTextColor(ContextCompat.getColor(this@SetTradePwdActivity, R.color.black))
                } else {
                    view4.setBackgroundResource(R.color.color_D0D0D0)
                    edPwdKey4.setTextColor(ContextCompat.getColor(this@SetTradePwdActivity, R.color.color_D0D0D0))
                }
                edPwdKey5.visibility = if (s.toString().trim().length > 4) View.VISIBLE else View.INVISIBLE
                if (s.toString().trim().length == 5) {
                    view5.setBackgroundResource(R.color.color_333333)
                    edPwdKey5.setTextColor(ContextCompat.getColor(this@SetTradePwdActivity, R.color.black))
                } else {
                    view5.setBackgroundResource(R.color.color_D0D0D0)
                    edPwdKey5.setTextColor(ContextCompat.getColor(this@SetTradePwdActivity, R.color.color_D0D0D0))
                }
                edPwdKey6.visibility = if (s.toString().trim().length > 5) View.VISIBLE else View.INVISIBLE
                if (s.toString().trim().length == 6) {
                    view6.setBackgroundResource(R.color.color_333333)
                    edPwdKey6.setTextColor(ContextCompat.getColor(this@SetTradePwdActivity, R.color.black))
                } else {
                    view6.setBackgroundResource(R.color.color_D0D0D0)
                    edPwdKey6.setTextColor(ContextCompat.getColor(this@SetTradePwdActivity, R.color.color_D0D0D0))
                }
                if (s.toString().trim().length == 6) {
                    //设置密码 之前未设置过
                    if (isOldPsw) {
                        mPresenter?.let { it.changePayPsw(s.toString()) }
                    } else {
                        //第一次设置密码
                        if (isfirstInput) {
                            //第一次输入
                            oldPassword = s.toString().trim()
                            isfirstInput = false
                            tvDesc.text = "确认6位支付密码"
                            edPwdKey.removeTextChangedListener(this)
                            edPwdKey.setText("")
                            edPwdKey.addTextChangedListener(this)
//                            edPwdKey.postDelayed(Runnable {
                            view1.setBackgroundResource(R.color.color_333333)
                                edPwdKey1.visibility = View.INVISIBLE
                                edPwdKey2.visibility = View.INVISIBLE
                                edPwdKey3.visibility = View.INVISIBLE
                                edPwdKey4.visibility = View.INVISIBLE
                                edPwdKey5.visibility = View.INVISIBLE
                                edPwdKey6.visibility = View.INVISIBLE
                                view6.setBackgroundResource(R.color.color_D0D0D0)
//                               },100)
                        } else {
                            //第二次确认密码掉修改密码或者设置密码接口
                            if (TextUtils.equals(oldPassword, s.toString())) {
                                //第二次输入成功
                                mPresenter?.let { it.setPayPsw(oldPassword, oldPassword, isSetted) }
                            } else {
                                //第二次输入失败
                                oldPassword = ""
                                isfirstInput = true
                                //重置状态保证回到第一次输入
                                edPwdKey.removeTextChangedListener(this)
                                edPwdKey.setText("")
                                edPwdKey.addTextChangedListener(this)
                                view1.setBackgroundResource(R.color.color_333333)
                                edPwdKey1.visibility = View.INVISIBLE
                                edPwdKey2.visibility = View.INVISIBLE
                                edPwdKey3.visibility = View.INVISIBLE
                                edPwdKey4.visibility = View.INVISIBLE
                                edPwdKey5.visibility = View.INVISIBLE
                                edPwdKey6.visibility = View.INVISIBLE
                                view6.setBackgroundResource(R.color.color_D0D0D0)
                                tvDesc.text = "请设置6位支付密码"
                                ToastUtils.makeText(this@SetTradePwdActivity, "两次密码不一致，请重新输入", Gravity.CENTER)
                            }


                        }

                    }
                }

            }

            override fun afterTextChanged(s: Editable) {

            }
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
