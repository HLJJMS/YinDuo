package com.yinduowang.installment.mvp.ui.activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.InputFilter
import android.text.InputType
import android.text.TextUtils
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import androidx.core.content.ContextCompat
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationListener
import com.blankj.utilcode.util.SPUtils
import com.jakewharton.rxbinding3.view.clicks
import com.jakewharton.rxbinding3.widget.textChanges
import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils
import com.jess.arms.utils.DeviceUtils
import com.jess.arms.utils.PermissionUtil
import com.tbruyelle.rxpermissions2.RxPermissions
import com.yinduowang.installment.R
import com.yinduowang.installment.app.constant.SPConstant
import com.yinduowang.installment.app.utils.*
import com.yinduowang.installment.app.utils.amap.LocationUtils
import com.yinduowang.installment.app.utils.gosetting.PermissionSetting
import com.yinduowang.installment.app.widget.AutomaticIntervalEditText
import com.yinduowang.installment.app.widget.popwindow.BottomPopupWindow
import com.yinduowang.installment.di.component.DaggerLoginComponent
import com.yinduowang.installment.di.module.LoginModule
import com.yinduowang.installment.mvp.contract.LoginContract
import com.yinduowang.installment.mvp.model.entity.NewLoginEntity
import com.yinduowang.installment.mvp.model.event.HomeBottomLoginEvent
import com.yinduowang.installment.mvp.presenter.LoginPresenter
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.activity_login.*
import org.simple.eventbus.EventBus


/**
 * ================================================
 * Description:注册
 * <p>
 * Created by MVPArmsTemplate on 07/16/2019 09:47
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
class LoginActivity : BaseActivity<LoginPresenter>(), LoginContract.View {


    var isChecked: Boolean = true
    private var loginType = ""
    private var locationUtils: LocationUtils? = null
    private var address = ""
    var kefuPopupWindow: BottomPopupWindow? = null
    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerLoginComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .loginModule(LoginModule(this))
                .build()
                .inject(this)
    }


    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_login //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }


    @SuppressLint("SetTextI18n", "CheckResult")
    override fun initData(savedInstanceState: Bundle?) {
        titlebar.showBlackBack()
        titlebar.setRightTextView("注册", View.OnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        })
        titlebar.setRightTextViewColorResources(R.color.colorAccent)
        titlebar.setBottomLineShow(false)
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
        //loginType 0验证码登录1密码登录
        intent.extras?.getString("loginType")?.let {
            loginType = intent.extras!!.getString("loginType").toString()
            if (it.equals("0")) {
                etDeliveryPhone.setTransformationMethod(HideReturnsTransformationMethod.getInstance())
                etDeliveryPhone.setSelection(etDeliveryPhone.text.toString().length)
                tv_forget_psw.visibility = View.GONE
                etDeliveryPhone.setText("")
                tv_psw_login.text = "密码登录"
                etDeliveryPhone.hint = "请输入短信验证码"
                etDeliveryPhone.filters = arrayOf(InputFilter.LengthFilter(6))
                tv_verfy_code.visibility = View.VISIBLE
                iv_psw_eye.visibility = View.GONE
                iv_psw_clear.visibility = View.GONE
                etDeliveryPhone.inputType = InputType.TYPE_CLASS_NUMBER
            } else {
                etDeliveryPhone.setTransformationMethod(PasswordTransformationMethod.getInstance())
                etDeliveryPhone.setSelection(etDeliveryPhone.text.toString().length)
                tv_forget_psw.visibility = View.VISIBLE
                tv_psw_login.text = "验证码登录"
                etDeliveryPhone.hint = "请输入登录密码"
                etDeliveryPhone.setText("")
                etDeliveryPhone.filters = arrayOf(InputFilter.LengthFilter(16))
                etDeliveryPhone.maxLines = 16
                tv_verfy_code.visibility = View.GONE
                iv_psw_eye.visibility = View.VISIBLE
                etDeliveryPhone.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            }
        }
        //设置版本号
        tv_version_code.text = "当前版本:${DeviceUtils.getVersionName(this)}"

        tv_forget_psw.setOnClickListener {
            startActivity(Intent(this, ForgetPasswordActivity::class.java))
        }
        //添加手机号及密码监听
        et_phone_number.textWatcher = object : AutomaticIntervalEditText.AutomaticIntervalTextWatcher() {
            override fun afterTextChanged(s: Editable?) {
                super.afterTextChanged(s)
                iv_phone_clear.visibility = if (s.isNullOrEmpty()) View.GONE else View.VISIBLE
                tv_title_phone.visibility = if (s.isNullOrEmpty()) View.GONE else View.VISIBLE
                tvBtn.isEnabled =
                        (et_phone_number.getRealText().length == 11 &&
                                if (tv_psw_login.text.toString().trim() == "验证码登录") {
                                    etDeliveryPhone.text.toString().trim().length in 6..20
                                } else {
                                    etDeliveryPhone.text.toString().trim().length == 6
                                })
            }
        }
        //添加密码监听
        etDeliveryPhone.textChanges()
                .subscribe(Consumer<CharSequence> { charSequence ->
                    tv_title_verify.visibility = if (TextUtils.isEmpty(charSequence)) View.GONE else View.VISIBLE
                    if (tv_psw_login.text.toString().equals("验证码登录")) {
                        iv_psw_clear.visibility = if (TextUtils.isEmpty(charSequence)) View.GONE else View.VISIBLE
                        tv_title_verify.text = "登录密码"
                        tvBtn.isEnabled = (et_phone_number.getRealText().length == 11 && charSequence.toString().trim().length in 6..20)
                    } else {
                        tv_title_verify.text = "验证码"
                        tvBtn.isEnabled = (et_phone_number.getRealText().length == 11 && charSequence.toString().trim().length == 6)
                    }
                })

        iv_phone_clear.clicks().subscribe(Consumer<Unit> {
            et_phone_number.setText("")
        })
        //获取验证码
        tv_verfy_code.clicks().subscribe(Consumer {
            if (TextUtils.isEmpty(et_phone_number.getRealText())) {
                ToastUtils.makeText(this, "请输入手机号码")
                return@Consumer
            }
            if (!StringUtil.isMobileNO(et_phone_number.getRealText())) {
                ToastUtils.makeText(this, "手机号码格式不正确")
                return@Consumer
            }
            mPresenter?.let { it.getVerifyCode(et_phone_number.getRealText(), "2") }
        })

        //密码眼睛的点击事件
        iv_psw_eye.clicks().subscribe(Consumer {
            if (isChecked) {
                //如果选中，显示密码
                etDeliveryPhone.setTransformationMethod(HideReturnsTransformationMethod.getInstance())
                isChecked = false
                etDeliveryPhone.setSelection(etDeliveryPhone.getText().toString().length)
                iv_psw_eye.setImageResource(R.mipmap.ic_eye)
            } else {
                //否则隐藏密码
                etDeliveryPhone.setTransformationMethod(PasswordTransformationMethod.getInstance())
                isChecked = true
                etDeliveryPhone.setSelection(etDeliveryPhone.getText().toString().length)
                iv_psw_eye.setImageResource(R.mipmap.ic_eye_close)
            }
        })
        iv_psw_clear.clicks().subscribe(Consumer {

            etDeliveryPhone.setText("")
        })
        //验证码密码切换逻辑
        tv_psw_login.clicks().subscribe(Consumer<Unit> {
            if (tv_psw_login.text.toString().equals("密码登录")) {
                etDeliveryPhone.setTransformationMethod(PasswordTransformationMethod.getInstance())
                etDeliveryPhone.setSelection(etDeliveryPhone.text.toString().length)
                tv_forget_psw.visibility = View.VISIBLE
                tv_psw_login.text = "验证码登录"
                etDeliveryPhone.hint = "请输入登录密码"
                etDeliveryPhone.setText("")
                etDeliveryPhone.filters = arrayOf(InputFilter.LengthFilter(16))
                etDeliveryPhone.maxLines = 16
                tv_verfy_code.visibility = View.GONE
                iv_psw_eye.visibility = View.VISIBLE
                etDeliveryPhone.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            } else {
                etDeliveryPhone.setTransformationMethod(HideReturnsTransformationMethod.getInstance())
                etDeliveryPhone.setSelection(etDeliveryPhone.text.toString().length)
                tv_forget_psw.visibility = View.GONE
                etDeliveryPhone.setText("")
                tv_psw_login.text = "密码登录"
                etDeliveryPhone.hint = "请输入短信验证码"
                etDeliveryPhone.filters = arrayOf(InputFilter.LengthFilter(6))
                tv_verfy_code.visibility = View.VISIBLE
                iv_psw_eye.visibility = View.GONE
                iv_psw_clear.visibility = View.GONE
                etDeliveryPhone.inputType = InputType.TYPE_CLASS_NUMBER
            }
        })
        tvBtn.clicks().subscribe(Consumer {
            if (!StringUtil.isMobileNO(et_phone_number.getRealText())) {
                ToastUtils.makeText(this, "手机号码格式不正确")
                return@Consumer
            }
            if (tv_psw_login.text.toString().equals("密码登录"))
                mPresenter?.let { it.verifyLogin(et_phone_number.getRealText(), etDeliveryPhone.text.toString().trim(), address) }
            else mPresenter?.let { it.passwordLogin(et_phone_number.getRealText(), etDeliveryPhone.text.toString().trim(), address) }
        })
        //联系客服
        tvCustomerService.clicks().subscribe(Consumer {
            mPresenter?.getServiceTel()
        })
//        判断手机号焦点控制显示隐藏
        et_phone_number.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                //                有交点
                if (et_phone_number.getRealText().isEmpty()) {
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
        etDeliveryPhone.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                //                有交点
                if (etDeliveryPhone.text.length > 0 && !tv_psw_login.text.toString().equals("密码登录")) {
                    iv_psw_clear.visibility = View.VISIBLE
                } else {
                    iv_psw_clear.visibility = View.GONE
                }
            } else {
                //              无焦点
                iv_psw_clear.visibility = View.GONE
            }
        }

    }


    internal var timer: CountDownTimer = object : CountDownTimer((60 * 1000 + 500).toLong(), 1000) {
        override fun onTick(millisUntilFinished: Long) {
            if (millisUntilFinished / 1000 == 0L) {
                onFinish()
                return
            }
            tv_verfy_code.text = "重新发送(${(millisUntilFinished / 1000).toString()})"
            tv_verfy_code.setTextColor(ContextCompat.getColor(baseContext, R.color.color_ADADAD))
            tv_verfy_code.setClickable(false)
        }

        override fun onFinish() {
            tv_verfy_code.setText("获取验证码")
            tv_verfy_code.setTextColor(ContextCompat.getColor(baseContext, R.color.color_FF7213))
            tv_verfy_code.setClickable(true)
        }
    }

    override fun onVerifyCode() {
        timer.start()
    }

    override fun onLoginReturn(loginEntity: NewLoginEntity) {
        SPUtils.getInstance().put(SPConstant.USER_ID, loginEntity.userId)
        SPUtils.getInstance().put(SPConstant.NICK_NAME, loginEntity.nickName)
        SPUtils.getInstance().put(SPConstant.TOKEN, loginEntity.token)
        SPUtils.getInstance().put(SPConstant.TOKEN_EXPIRE, loginEntity.tokenExpire)
        SPUtils.getInstance().put(SPConstant.REFRESH_TOKEN, loginEntity.refreshToken)
        SPUtils.getInstance().put(SPConstant.REFRESH_TOKEN_EXPIRE, loginEntity.refreshTokenExpire)
        // 更新主页商城 未登录横幅显示
        EventBus.getDefault().post(HomeBottomLoginEvent(true))
        // 判断是否显示商城页面
        if (loginEntity.vipPopStatus.equals("0")) {
//            EventBus.getDefault().post(ShowHomeShopEvent())
            val intent = Intent(this, RegisterSuccessActivity::class.java)
            intent.putExtra("vipStatus", loginEntity.vipStatus)
            intent.putExtra("vipText", loginEntity.vipText)
            startActivity(intent)
        }
        killMyself()
    }

    fun showPopupwindow(tel: String) {
        kefuPopupWindow = BottomPopupWindow(this@LoginActivity, true)
        kefuPopupWindow!!.setOneButtontText("在线联系客服")
        kefuPopupWindow!!.setTwoButtontText("拨打电话：" + tel)
        kefuPopupWindow!!.setOnButtonClick(
                object : BottomPopupWindow.ButtonClick {
                    override fun setClick(id: Int) {
                        if (id == BottomPopupWindow.ONE_BUTTON) {
                            mPresenter?.let {
                                GotoMQChatUtils.gotoMQChatCustomized(this@LoginActivity, PhoneStatusUtils.getIMEI(), it?.mErrorHandler)
                            }
                        } else if (id == BottomPopupWindow.TWO_BUTTON) {
                            requestsForPermissionsCall(tel)
                        }
                    }
                })
        kefuPopupWindow!!.show()
    }

    //    popupwindow弹出后半透明
    private fun bgAlpha(f: Float) {
        val layoutParams = window.attributes
        layoutParams.alpha = f
        window.attributes = layoutParams
    }

    //    打电话权限
    fun requestsForPermissionsCall(phoneNum: String) {
        PermissionUtil.requestPermission(
                object : PermissionUtil.RequestPermission {
                    override fun onRequestPermissionSuccess() {
                        // 设置初始化显示页面
                        var intent = Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse("tel:" + phoneNum));
                        startActivity(intent);
                    }

                    override fun onRequestPermissionFailure(permissions: List<String>) {
                        requestsForPermissionsCall(phoneNum)
                    }

                    override fun onRequestPermissionFailureWithAskNeverAgain(permissions: List<String>) {
                        PermissionSetting.toPermissionSetting(this@LoginActivity)
                    }
                },
                RxPermissions(this),
                mPresenter?.mErrorHandler,
                Manifest.permission.CALL_PHONE
        )
    }


    //    获取电话成功
    override fun getTelSuccess(tel: String) {
        showPopupwindow(tel)
    }

    override fun showLoading() {
        LoadingUtils.show(this)
    }

    override fun hideLoading() {
        LoadingUtils.dismiss(this)
    }

    override fun onDestroy() {
        locationUtils?.let {
            it.destroyLocation()
        }
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
