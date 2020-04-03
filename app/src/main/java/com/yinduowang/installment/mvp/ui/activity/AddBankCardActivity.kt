package com.yinduowang.installment.mvp.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import androidx.core.content.ContextCompat
import com.jakewharton.rxbinding3.view.clicks
import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils
import com.yinduowang.installment.R
import com.yinduowang.installment.app.utils.LoadingUtils
import com.yinduowang.installment.app.utils.StringUtil
import com.yinduowang.installment.app.utils.ToastUtils
import com.yinduowang.installment.di.component.DaggerAddBankCardComponent
import com.yinduowang.installment.di.module.AddBankCardModule
import com.yinduowang.installment.mvp.contract.AddBankCardContract
import com.yinduowang.installment.mvp.model.entity.ConfirmBindEntity
import com.yinduowang.installment.mvp.model.entity.PreBindEntity
import com.yinduowang.installment.mvp.model.entity.VerificationUserInfoEntity
import com.yinduowang.installment.mvp.presenter.AddBankCardPresenter
import com.yinduowang.installment.mvp.ui.activity.LendConfirmLoanActivity.Companion.RESULT_CODE_ADD_BANK_SUCCESS
import kotlinx.android.synthetic.main.activity_add_bank_card.*
import java.util.concurrent.TimeUnit


/**
 * ================================================
 * Description:添加银行卡
 * <p>
 * Created by MVPArmsTemplate on 07/24/2019 14:11
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
class AddBankCardActivity : BaseActivity<AddBankCardPresenter>(), AddBankCardContract.View {


    var uniqueCode: String = ""

    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerAddBankCardComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .addBankCardModule(AddBankCardModule(this))
                .build()
                .inject(this)
    }


    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_add_bank_card //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    override fun initData(savedInstanceState: Bundle?) {
        titleBar.showTitleAndBack(R.string.add_bankcard)
        initView()
        mPresenter?.getUserInfo()
    }

    @SuppressLint("CheckResult")
    fun initView() {
        etFillBankcardNumber.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                setBtnEnt()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })
        etFillPhoneNumber.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                setBtnEnt()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })
        etVerificationCode.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                setBtnEnt()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })
        // 获取验证码
        tvClickToGet
                .clicks()
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe {
                    if (etFillBankcardNumber.text.toString().isEmpty()) {
                        ToastUtils.makeText(this, R.string.please_fill_bankcard_number)
                        return@subscribe
                    }
                    if (etFillBankcardNumber.text.toString().trim().length != 19 && etFillBankcardNumber.text.toString().trim().length != 16) {
                        ToastUtils.makeText(this, R.string.bank_card_number_can_only_be_16_or_19_digits)
                        return@subscribe
                    }
                    if (etFillPhoneNumber.text.toString().isEmpty()) {
                        ToastUtils.makeText(this, R.string.please_fill_phone_number)
                        return@subscribe
                    }
                    if (!StringUtil.isMobileNO(etFillPhoneNumber.text.toString())) {
                        ToastUtils.makeText(this, R.string.incorrect_format_of_mobile_phone_number)
                        return@subscribe
                    }
                    mPresenter?.preBind(etFillBankcardNumber.text.toString(), etFillPhoneNumber.text.toString())

                }
        // 绑卡按钮
        tvBindingCard
                .clicks()
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe {
                    if (TextUtils.isEmpty(uniqueCode)) {
                        ToastUtils.makeText(this, "请先获取验证码")
                        return@subscribe
                    }
                    mPresenter?.confirmBind(uniqueCode, etVerificationCode.text.toString())
                }
        // 获取绑卡人姓名
        mPresenter?.getUserInfo()
    }

    fun setBtnEnt() {
        tvBindingCard.isEnabled = etFillBankcardNumber.text.toString().isNotEmpty() &&
                etFillPhoneNumber.text.toString().isNotEmpty() &&
                etVerificationCode.text.toString().isNotEmpty() &&
                tvCardholder.text.isNotEmpty()
    }

    override fun getUserInfo(verificationUserInfoEntity: VerificationUserInfoEntity) {
        tvCardholder.text = verificationUserInfoEntity.username
    }

    override fun preBind(preBindEntity: PreBindEntity) {
        uniqueCode = preBindEntity.uniqueCode
        ToastUtils.makeText(this, preBindEntity.message)
        if (preBindEntity.status == 1) {
            timer.start()
        }
    }

    override fun confirmBind(confirmBindEntity: ConfirmBindEntity) {
        ToastUtils.makeText(this, confirmBindEntity.msg)
        if (confirmBindEntity.status == 3 || confirmBindEntity.status == 1) {
               setResult(RESULT_CODE_ADD_BANK_SUCCESS)
            killMyself()
        }
    }

    internal var timer: CountDownTimer = object : CountDownTimer((60 * 1000 + 500).toLong(), 1000) {
        @SuppressLint("SetTextI18n")
        override fun onTick(millisUntilFinished: Long) {
            if (millisUntilFinished / 1000 == 0L) {
                onFinish()
                return
            }
            tvClickToGet.text = (millisUntilFinished / 1000).toString() + "s" + resources.getString(R.string.re_acquisition)
            tvClickToGet.setTextColor(ContextCompat.getColor(baseContext, R.color.color_ADADAD))
            tvClickToGet.isClickable = false
        }

        override fun onFinish() {
            tvClickToGet.text = resources.getString(R.string.click_to_get)
            tvClickToGet.setTextColor(ContextCompat.getColor(baseContext, R.color.colorAccent))
            tvClickToGet.isClickable = true
        }
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

    override fun onDestroy() {
        super.onDestroy()
        timer.cancel()
    }
}
