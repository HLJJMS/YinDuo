package com.yinduowang.installment.mvp.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import androidx.core.content.ContextCompat
import android.text.Editable
import android.text.TextUtils
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationListener
import com.blankj.utilcode.util.SPUtils
import com.jakewharton.rxbinding3.view.clicks
import com.jakewharton.rxbinding3.widget.textChanges
import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.integration.AppManager
import com.jess.arms.utils.ArmsUtils
import com.jess.arms.utils.DeviceUtils
import com.yinduowang.installment.R
import com.yinduowang.installment.app.constant.Api
import com.yinduowang.installment.app.constant.SPConstant
import com.yinduowang.installment.app.utils.LoadingUtils
import com.yinduowang.installment.app.utils.StringUtil
import com.yinduowang.installment.app.utils.ToastUtils
import com.yinduowang.installment.app.utils.amap.LocationUtils
import com.yinduowang.installment.app.widget.AutomaticIntervalEditText
import com.yinduowang.installment.di.component.DaggerRegisterComponent
import com.yinduowang.installment.di.module.RegisterModule
import com.yinduowang.installment.mvp.contract.RegisterContract
import com.yinduowang.installment.mvp.model.entity.RegisterEntity
import com.yinduowang.installment.mvp.model.event.HomeBottomLoginEvent
import com.yinduowang.installment.mvp.model.event.ShowHomeShopEvent
import com.yinduowang.installment.mvp.presenter.RegisterPresenter
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.activity_register.*
import org.simple.eventbus.EventBus
import java.util.concurrent.TimeUnit


/**
 * ================================================
 * Description:注册
 * <p>
 * Created by MVPArmsTemplate on 07/16/2019 14:09
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
class RegisterActivity : BaseActivity<RegisterPresenter>(), RegisterContract.View {


    var isChecked: Boolean = true
    var checkAgreement: Boolean = false
    private var locationUtils: LocationUtils? = null
    private var address = ""
    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerRegisterComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .registerModule(RegisterModule(this))
                .build()
                .inject(this)
    }


    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_register //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }


    @SuppressLint("CheckResult", "SetTextI18n")
    override fun initData(savedInstanceState: Bundle?) {
        titlebar.showBlackBack()
        titlebar.showRightTextButton("登录")
        titlebar.setRightTextViewColorResources(R.color.colorAccent)
        titlebar.setBottomLineShow(false)
        tv_version_code.text = "当前版本:${DeviceUtils.getVersionName(this)}"
        //gps初始化
        if (locationUtils == null) {
            locationUtils = LocationUtils()
        }
        //gps回调
        locationUtils?.initLocation(this, object : AMapLocationListener {
            override fun onLocationChanged(p0: AMapLocation?) {
                if (p0 != null) {
                    if (p0.errorCode == 0)
                        address = p0.address
                }
            }

        })
        locationUtils?.startLocation()
        //添加手机号及密码监听
        etDeliveryName.textWatcher = object : AutomaticIntervalEditText.AutomaticIntervalTextWatcher() {
            override fun afterTextChanged(s: Editable?) {
                super.afterTextChanged(s)
                iv_phone_clear.visibility = if (s.isNullOrEmpty()) View.GONE else View.VISIBLE
                tv_title_phone.visibility = if (s.isNullOrEmpty()) View.GONE else View.VISIBLE
                tvBtn.isEnabled = (checkAgreement && etDeliveryName.getRealText().length == 11 &&
                        etDeliveryPhone.text.toString().trim().length == 6 && etDeliveryArea.text.toString().trim().length in 6..20)
            }
        }
        //验证码密码监听
        etDeliveryPhone.textChanges()
                .subscribe(Consumer<CharSequence> { charSequence ->
                    tv_title_verfiy.visibility = if (TextUtils.isEmpty(charSequence)) View.GONE else View.VISIBLE
                    tvBtn.isEnabled = (checkAgreement && etDeliveryName.getRealText().length == 11 &&
                            charSequence.toString().trim().length == 6 && etDeliveryArea.text.toString().trim().length in 6..20)
                })
        iv_phone_clear.clicks().subscribe(Consumer<Unit> {
            etDeliveryName.setText("")
        })
        //密码监听
        etDeliveryArea.textChanges()
                .subscribe(Consumer<CharSequence> { charSequence ->
                    tv_title_psw.visibility = if (TextUtils.isEmpty(charSequence)) View.GONE else View.VISIBLE
                    iv_password_clear.visibility = if (TextUtils.isEmpty(charSequence)) View.GONE else View.VISIBLE
                    tvBtn.isEnabled = (checkAgreement && etDeliveryName.getRealText().length == 11 && etDeliveryPhone.text.toString().trim().length == 6 && charSequence.toString().trim().length in 6..20)
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
        //获取验证码
        tv_get_verify_code.clicks().subscribe(Consumer {
            if (TextUtils.isEmpty(etDeliveryName.getRealText())) {
                ToastUtils.makeText(this, "请输入手机号码")
                return@Consumer
            }
            if (!StringUtil.isMobileNO(etDeliveryName.getRealText())) {
                ToastUtils.makeText(this, "手机号码格式不正确")
                return@Consumer
            }
            mPresenter?.let { it.getVerifyCode(etDeliveryName.getRealText(), "1") }

        })
        tvBtn.clicks().subscribe(Consumer {
            if (!StringUtil.isMobileNO(etDeliveryName.getRealText())) {
                ToastUtils.makeText(this, "手机号码格式不正确")
                return@Consumer
            }
            mPresenter?.register(etDeliveryName.getRealText(), etDeliveryPhone.text.toString().trim(),
                    etDeliveryArea.text.toString().trim(), address)
        })
        ivCheckDefault.clicks().subscribe(Consumer {
            if (!checkAgreement) {
                checkAgreement = true
                ivCheckDefault.setImageResource(R.mipmap.ic_circle_press)
            } else {
                checkAgreement = false
                ivCheckDefault.setImageResource(R.mipmap.ic_circle_normal)
            }
            tvBtn.isEnabled = (checkAgreement && etDeliveryName.getRealText().length == 11 && etDeliveryPhone.text.toString().trim().length == 6 && etDeliveryArea.text.toString().trim().length in 6..20)
        })
        tv_agreement_hiht.clicks().subscribe(Consumer {
            if (!checkAgreement) {
                checkAgreement = true
                ivCheckDefault.setImageResource(R.mipmap.ic_circle_press)
            } else {
                checkAgreement = false
                ivCheckDefault.setImageResource(R.mipmap.ic_circle_normal)
            }
            tvBtn.isEnabled = (checkAgreement && etDeliveryName.getRealText().length == 11 && etDeliveryPhone.text.toString().trim().length == 6 && etDeliveryArea.text.toString().trim().length in 6..20)
        })
        tv_agreement.clicks()
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe {
                    val intent = Intent(this, CommWebViewActivity::class.java)
                    intent.putExtra(CommWebViewActivity.KEY_URL_NAME, Api.AGREEMENT_USER_REISTER_PROTOCOL)
                    intent.putExtra(CommWebViewActivity.KEY_JAVASCRIPT_TYPE, CommWebViewActivity.TYPE_JAVASCRIPT_JEL_LE_ME)
                    intent.putExtra(CommWebViewActivity.KEY_IS_AUTHENTICATION, CommWebViewActivity.TYPE_TITLE_NORMAL)
                    startActivity(intent)
                }


        // 判断手机号焦点控制显示隐藏
        etDeliveryName.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                // 有交点
                if (etDeliveryName.getRealText().isEmpty()) {
                    iv_phone_clear.visibility = View.GONE
                } else {
                    iv_phone_clear.visibility = View.VISIBLE
                }
            } else {
                // 无焦点
                iv_phone_clear.visibility = View.GONE
            }
        }

        // 判断密码焦点控制显示隐藏
        etDeliveryArea.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                // 有交点
                if (etDeliveryArea.text.length > 0) {
                    iv_password_clear.visibility = View.VISIBLE
                } else {
                    iv_password_clear.visibility = View.GONE
                }
            } else {
                // 无焦点
                iv_password_clear.visibility = View.GONE
            }
        }


    }

    //计时器定时
    internal var timer: CountDownTimer = object : CountDownTimer((60 * 1000 + 500).toLong(), 1000) {
        override fun onTick(millisUntilFinished: Long) {
            if (millisUntilFinished / 1000 == 0L) {
                onFinish()
                return
            }
            tv_get_verify_code.text = "重新发送(${(millisUntilFinished / 1000).toString()})"
            tv_get_verify_code.setTextColor(ContextCompat.getColor(baseContext, R.color.color_ADADAD))
            tv_get_verify_code.isClickable = false
        }

        override fun onFinish() {
            tv_get_verify_code.text = "重新获取"
            tv_get_verify_code.setTextColor(ContextCompat.getColor(baseContext, R.color.color_FF7213))
            tv_get_verify_code.isClickable = true
        }
    }

    //验证码返回
    override fun onVerifyCode() {
        timer.start()
    }

    override fun onRegisterReturn(response: RegisterEntity) {
        EventBus.getDefault().post(HomeBottomLoginEvent(true))
        SPUtils.getInstance().put(SPConstant.USER_ID, response.userId)
        SPUtils.getInstance().put(SPConstant.NICK_NAME, response.nickName)
        SPUtils.getInstance().put(SPConstant.TOKEN, response.token)
        SPUtils.getInstance().put(SPConstant.TOKEN_EXPIRE, response.tokenExpire)
        SPUtils.getInstance().put(SPConstant.REFRESH_TOKEN, response.refreshToken)
        SPUtils.getInstance().put(SPConstant.REFRESH_TOKEN_EXPIRE, response.refreshTokenExpire)
        if (response.vipPopStatus == "0") {
            val intent = Intent(this, RegisterSuccessActivity::class.java)
            intent.putExtra("vipStatus", response.vipStatus)
            intent.putExtra("vipText", response.vipText)
            intent.putExtra("isRegister", true)
            startActivity(intent)
        } else {
            // 跳转首页并展示商城
            EventBus.getDefault().post(ShowHomeShopEvent())
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
        AppManager.getAppManager().killActivity(LoginActivity::class.java)
        finish()
    }


    override fun onDestroy() {
        locationUtils?.let {
            it.destroyLocation()
        }
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
