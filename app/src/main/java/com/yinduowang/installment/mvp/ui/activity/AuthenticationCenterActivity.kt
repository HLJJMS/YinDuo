package com.yinduowang.installment.mvp.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.method.LinkMovementMethod
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.jakewharton.rxbinding3.view.clicks
import com.jakewharton.rxbinding3.widget.checkedChanges
import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils
import com.scwang.smartrefresh.layout.constant.RefreshState
import com.tencent.bugly.crashreport.CrashReport
import com.yinduowang.installment.R
import com.yinduowang.installment.app.constant.Api
import com.yinduowang.installment.app.constant.UMCountConfig
import com.yinduowang.installment.app.utils.LoadingUtils
import com.yinduowang.installment.app.utils.NoLineClickable
import com.yinduowang.installment.app.utils.UMCountUtil
import com.yinduowang.installment.di.component.DaggerAuthenticationCenterComponent
import com.yinduowang.installment.di.module.AuthenticationCenterModule
import com.yinduowang.installment.mvp.contract.AuthenticationCenterContract
import com.yinduowang.installment.mvp.model.entity.PerfectEntity
import com.yinduowang.installment.mvp.presenter.AuthenticationCenterPresenter
import kotlinx.android.synthetic.main.activity_authentication_center.*
import java.util.concurrent.TimeUnit


/**
 * ================================================
 * Description:认证中心
 * <p>
 * Created by MVPArmsTemplate on 07/24/2019 14:10
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
class AuthenticationCenterActivity : BaseActivity<AuthenticationCenterPresenter>(), AuthenticationCenterContract.View {
    /**
     * @description ：通过验证账户接口返回如果data为空跳转宝付页面
     * @lastEditor  ：张利超
     * @version     ：v1.0 ,2019/11/6
     * @param null
     * @return      ：
     * @deprecated  ： false
     */
    override fun jumpBaofu() {
        startActivity(Intent(this, BaofuWithholdActivity::class.java))
    }

    lateinit var style: SpannableStringBuilder
    var oneStart: Int = 0
    var twoStart: Int = 0
    var threeStart: Int = 0
    var threeEnd: Int = 0
    var agreementColor: Int = 0
    var btnCanClick = false
    val handler = Handler()
    val runnable = Runnable {
        mPresenter?.perfect()
    }

    lateinit var perfectEntity: PerfectEntity

    private val oneAgreement = "《电子签章及存证授权委托书》"
    private val twoAgreement = "《征信查询授权书》"
    private val threeAgreement = "《个人信息使用和第三方机构数据查询授权书》"
    private val oneUrl = Api.AGREEMENT_ELECTRONIC_SIGNATURE_AND_EXISTING_EVIDENCE_PROXY
    private val twoUrl = Api.AGREEMENT_CREDIT_QUERY_PROXY
    private val threeUrl = Api.AGREEMENT_PERSONAL_INFO_AND_DATA_QUERY_PROXY

    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerAuthenticationCenterComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .authenticationCenterModule(AuthenticationCenterModule(this))
                .build()
                .inject(this)
    }


    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_authentication_center //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }


    override fun initData(savedInstanceState: Bundle?) {
        titleBar.showTitle(R.string.authentication_center)
        titleBar.setTitleVisibility(View.GONE)
        titleBar.showBlackBack()
        titleBar.setBottomLineShow(false)
        tvTitle.text = resources.getString(R.string.authentication_center)

        smartRefreshLayout.setOnRefreshListener {
            handler.removeCallbacks(runnable)
            mPresenter?.perfect()
        }
        qmuiObservableScrollView.addOnScrollChangedListener { scrollView, scrollX, scrollY, oldScrollX, oldScrollY ->
            val displayDistance = ArmsUtils.dip2px(this, 90f)
            if (scrollY >= displayDistance) { // >=Toolbar高度的2.5倍时全显背景图
                titleBar.setTitleVisibility(View.VISIBLE)
                titleBar.setBottomLineShow(true)
            } else { // 小于Toolbar高度时不设置背景图
                titleBar.setTitleVisibility(View.GONE)
                titleBar.setBottomLineShow(false)
            }
        }
        initClick()
    }

    private fun initAgreement() {
        agreementColor = ContextCompat.getColor(this, R.color.colorAccent)
        val startAgreement = "同意"
        style = SpannableStringBuilder(startAgreement + oneAgreement + twoAgreement + threeAgreement)
        oneStart = startAgreement.length
        twoStart = startAgreement.length + oneAgreement.length
        threeStart = startAgreement.length + oneAgreement.length + twoAgreement.length
        threeEnd = startAgreement.length + oneAgreement.length + twoAgreement.length + threeAgreement.length
        initAgreementText()
    }

    @SuppressLint("CheckResult")
    fun initClick() {
        clPerson.clicks()
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe {
                    if (smartRefreshLayout.state == RefreshState.None) {
                        var intent = Intent(this, PersonalDetailActivity::class.java)
                        intent.putExtra("isAuthentication", perfectEntity.userInfoStatus == "0")
                        startActivity(intent)
                    }
                }
        clBankCard.clicks()
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe {
                    var a = smartRefreshLayout.state
                    if (smartRefreshLayout.state.equals(RefreshState.None))
                        if (::perfectEntity.isInitialized)
                            if (perfectEntity.accountOpenStatus.equals("0") && perfectEntity.bankStatus.equals("1") && perfectEntity.bankCardStatus.equals("-1")) {
//                                去宝付
                                startActivity(Intent(this@AuthenticationCenterActivity, BaofuWithholdActivity::class.java))
                            } else if (perfectEntity.accountOpenStatus.equals("-1") && perfectEntity.bankStatus.equals("1") && perfectEntity.bankCardStatus.equals("-1")) {
//                                去上饶
                                mPresenter?.openAccount()
                            } else if (perfectEntity.accountOpenStatus.equals("0") && perfectEntity.bankStatus.equals("0") && perfectEntity.bankCardStatus.equals("0")) {
//                                去银行卡
                                startActivity(Intent(this@AuthenticationCenterActivity, BankCardActivity::class.java))
                            }
                }
        clEmergencyContact.clicks()
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe {
                    if (smartRefreshLayout.state == RefreshState.None)
                        if (::perfectEntity.isInitialized) {
                            var intent = Intent(this, AuthEmergencyContactActivity::class.java)
                            intent.putExtra("isAuthentication", perfectEntity.contactStatus == "0")
                            startActivity(intent)
                        }
                }
//        clPhone.clicks()
//                .throttleFirst(500, TimeUnit.MILLISECONDS)
//                .subscribe {
//                    if (smartRefreshLayout.state == RefreshState.None)
//                        if (::perfectEntity.isInitialized) {
//                            var intent = Intent(this, PhoneOperatorCertificationActivity::class.java)
//                            intent.putExtra("isAuthentication", perfectEntity.operatorStatus == "0")
//                            startActivity(intent)
//                        }
//                }
        ivCheckDefault
                .checkedChanges()
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe { isChecked ->
                    if (smartRefreshLayout.state == RefreshState.None)
                        initBtn()
                }
        //我的额度按钮
        tvBtn.clicks()
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe {
                    if (smartRefreshLayout.state == RefreshState.None) {
                        mPresenter?.getQuota()
                    }
//                        startActivity(Intent(this, MyQuotaActivity::class.java))
                }
    }


    override fun showLoading() {
        LoadingUtils.show(this@AuthenticationCenterActivity)
    }

    override fun hideLoading() {
        LoadingUtils.dismiss(this@AuthenticationCenterActivity)
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

    override fun onResume() {
        super.onResume()
        smartRefreshLayout.autoRefresh()
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(runnable)
    }

    override fun perfect(perfectEntity: PerfectEntity) {
        try {
            this.perfectEntity = perfectEntity
            // 额度显示
            tvQuota.text = perfectEntity.creditCeiling
            // 根据数据显示认证状态 顺序显示 前一步认证没有完成 无法进行下一步认证
            perfectEntity.userInfoStatus?.let { initData(it, tvPerson, clPerson) }
            perfectEntity.bankStatus?.let { initData(it, tvBankCard, clBankCard) }
            perfectEntity.contactStatus?.let { initData(it, tvEmergencyContact, clEmergencyContact) }
//            perfectEntity.operatorStatus?.let { initData(it, tvPayBackMoney, clPhone) }

            // 底部协议及获取额度按钮显示
            if (perfectEntity.isAcquired == "0") {
                llBottom.visibility = View.VISIBLE
                // 设置按钮不可点击
                initAgreement()
                // 根据认证状态显示按钮状态
                initBtn()
            } else {
                llBottom.visibility = View.GONE
            }
        } catch (e: Exception) {
            e.printStackTrace()
            CrashReport.postCatchedException(e)
        }
    }

    override fun requestAgain(){
        // 延时十秒 调用一次接口
        handler.postDelayed(runnable, 10 * 1000)
    }

    override fun openAccountResult(url: String) {
        val intent = Intent(this, CommWebViewActivity::class.java)
        intent.putExtra(CommWebViewActivity.KEY_URL_NAME, url)
        intent.putExtra(CommWebViewActivity.KEY_JAVASCRIPT_TYPE, CommWebViewActivity.TYPE_JAVASCRIPT_JEL_LE_ME)
        intent.putExtra(CommWebViewActivity.KEY_NO_HAVE_PARAMS, false)
        intent.putExtra(CommWebViewActivity.KEY_WEB_TITLE, "开通银行存管")
        intent.putExtra(CommWebViewActivity.KEY_IS_AUTHENTICATION, CommWebViewActivity.TYPE_TITLE_NORMAL)
        startActivityForResult(intent, MainActivity.AGREE_TO_PRIVACY_POLICY_REQUEST)
    }

    override fun finishRefresh() {
        smartRefreshLayout.finishRefresh()
    }

    /**
     * @return 是否完成认证 可以点击
     * */
    private fun initData(status: String, text: TextView, constraintLayout: ConstraintLayout): Boolean {
        when (status) {
            "0" -> {// 已认证
                text.text = resources.getString(R.string.certified)
                text.setTextColor(ContextCompat.getColor(this, R.color.color_ADADAD))
                constraintLayout.isEnabled = true
                return true
            }
            "1" -> {// 去认证
                text.text = resources.getString(R.string.de_certification)
                text.setTextColor(ContextCompat.getColor(this, R.color.colorAccent))
                constraintLayout.isEnabled = true
                return true
            }
            else -> {// 未认证
                text.text = resources.getString(R.string.to_be_certified)
                text.setTextColor(ContextCompat.getColor(this, R.color.color_ADADAD))
                constraintLayout.isEnabled = false
                return false
            }
        }
    }

    private fun initBtn() {
        //根据返回状态判断底部按钮是否可点击
        if (::perfectEntity.isInitialized)
            perfectEntity?.let {
                tvBtn.isEnabled = "0".equals(perfectEntity.userInfoStatus) && "0".equals(perfectEntity.bankStatus) && "0".equals(perfectEntity.contactStatus)  && ivCheckDefault.isChecked
            }
    }

    /**
     * 设置协议颜色和点击事件
     */
    private fun initAgreementText() {
        setAgreementTextClickAndColorOne()
        setAgreementTextClickAndColorTwo()
        setAgreementTextClickAndColorThree()
        tvAgreement.text = style
        tvAgreement.movementMethod = LinkMovementMethod.getInstance()
    }

    private fun setAgreementTextClickAndColorThree() {
        style.setSpan(NoLineClickable(View.OnClickListener {
            UMCountUtil.instance()!!.onEvent(TAG, UMCountConfig.CertificationCenterActivity_agreement_three, UMCountConfig.CertificationCenterActivity_agreement_three_value)
            openAgreement(threeUrl)
        }), threeStart, threeEnd, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
        style.setSpan(ForegroundColorSpan(agreementColor), threeStart, threeEnd, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
    }

    private fun setAgreementTextClickAndColorTwo() {
        style.setSpan(NoLineClickable(View.OnClickListener {
            UMCountUtil.instance()!!.onEvent(TAG, UMCountConfig.CertificationCenterActivity_agreement_two, UMCountConfig.CertificationCenterActivity_agreement_two_value)
            openAgreement(twoUrl)
        }), twoStart, threeStart, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
        style.setSpan(ForegroundColorSpan(agreementColor), twoStart, threeStart, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
    }

    private fun setAgreementTextClickAndColorOne() {
        style.setSpan(NoLineClickable(View.OnClickListener {
            UMCountUtil.instance()!!.onEvent(TAG, UMCountConfig.CertificationCenterActivity_agreement_one, UMCountConfig.CertificationCenterActivity_agreement_one_value)
            openAgreement(oneUrl)
        }), oneStart, twoStart, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
        style.setSpan(ForegroundColorSpan(agreementColor), oneStart, twoStart, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
    }

    /**
     * 打开协议页面
     */
    private fun openAgreement(url: String) {
        val intent = Intent(this, CommWebViewActivity::class.java)
        intent.putExtra(CommWebViewActivity.KEY_URL_NAME, url)
        intent.putExtra(CommWebViewActivity.KEY_JAVASCRIPT_TYPE, CommWebViewActivity.TYPE_JAVASCRIPT_JEL_LE_ME)
        intent.putExtra(CommWebViewActivity.KEY_IS_AUTHENTICATION, CommWebViewActivity.TYPE_TITLE_NORMAL)
        startActivity(intent)
    }


}
