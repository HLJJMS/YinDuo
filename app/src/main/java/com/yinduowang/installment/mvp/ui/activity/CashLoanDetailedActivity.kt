package com.yinduowang.installment.mvp.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.method.LinkMovementMethod
import android.text.style.ForegroundColorSpan
import android.view.View
import androidx.core.content.ContextCompat
import com.jakewharton.rxbinding3.view.clicks
import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils
import com.yinduowang.installment.R
import com.yinduowang.installment.app.constant.Api
import com.yinduowang.installment.app.utils.LoadingUtils
import com.yinduowang.installment.app.utils.NoLineClickable
import com.yinduowang.installment.di.component.DaggerCashLoanDetailedComponent
import com.yinduowang.installment.di.module.CashLoanDetailedModule
import com.yinduowang.installment.mvp.contract.CashLoanDetailedContract
import com.yinduowang.installment.mvp.model.entity.BaseResponse
import com.yinduowang.installment.mvp.model.entity.CashLoanRecordEntity
import com.yinduowang.installment.mvp.presenter.CashLoanDetailedPresenter
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.activity_cash_loan_detailed.*
import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.ParseException
import java.util.concurrent.TimeUnit


/**
 * ================================================
 * Description:借款记录-现金借款详情
 * <p>
 * Created by MVPArmsTemplate on 07/31/2019 14:26
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
class CashLoanDetailedActivity : BaseActivity<CashLoanDetailedPresenter>(), CashLoanDetailedContract.View {


    var agreementColor: Int = 0
    lateinit var style: SpannableStringBuilder
    var oneStart: Int = 0
    var twoStart: Int = 0
    var threeStart: Int = 0
    var threeEnd: Int = 0
    var fourStart: Int = 0
    var fourEnd: Int = 0
    var fiveStart: Int = 0
    var fiveEnd: Int = 0
    var type: String = ""
    val oneAgreement = "《借款咨询服务协议》"
    val twoAgreement = "《个人消费借款合同》"
    val threeAgreement = "《平台服务协议》"
    val fourAgreement = "《授权扣款委托书》"
    var fiveAgreement = "《VIP会员增值服务协议》"
    val oneUrl = Api.AGREEMENT_LOAN_COUNSELING
    val twoUrl = Api.AGREEMENT_INDIVIDUAL_CONSUMPTION
    val threeUrl = Api.AGREEMENT_USER_SERVICE_PROTOCOL
    val fourUrl = Api.AGREEMENT_ENTRUST_DEDUCTION_PROXY
    var fiveUrl = Api.VIP_PROXY
    var loanId = 0
    val startAgreement = ""
    var cashLoanRecordEntity: CashLoanRecordEntity? = null
    //1是回到借款记录页面
    var backType = ""
    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerCashLoanDetailedComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .cashLoanDetailedModule(CashLoanDetailedModule(this))
                .build()
                .inject(this)
    }


    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_cash_loan_detailed //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }


    override fun initData(savedInstanceState: Bundle?) {
        loanId = intent.getIntExtra("loanId", 0)
        intent.extras?.getString("backType")?.let {
            backType = intent?.extras!!.getString("backType").toString()
        }
        titleBar.showTitleAndBack("借款详情", View.OnClickListener {
            if (backType.equals("1")) {
                var intent = Intent(this, LoanRecordActivity::class.java)
                intent.putExtra("backType", backType)
                startActivity(intent)
                finish()
            } else {
                finish()
            }
        })
        intent.getStringExtra("type")?.let { type = it }
        tv_repay.clicks().throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(Consumer {
            //type 6 现金借款详情还款
            mPresenter?.checkRepay("3", loanId.toString())
        })
    }

    override fun onResume() {
        super.onResume()
        mPresenter?.let {
            it.getCashLoanDetailed(loanId)
        }
    }
    private fun initWebUrlEntity(isFour: String) {

        agreementColor = resources.getColor(R.color.colorAccent)
        val oneAgreement = oneAgreement
        val twoAgreement = twoAgreement
        val threeAgreement = threeAgreement
        val fourAgreement = fourAgreement
        if (isFour.equals("0")) {
//            y有
            style = SpannableStringBuilder(startAgreement + oneAgreement + twoAgreement + threeAgreement + fourAgreement)
        } else {
//            没有
            style = SpannableStringBuilder(startAgreement + oneAgreement + twoAgreement + threeAgreement)
        }

        style = SpannableStringBuilder(startAgreement + oneAgreement + twoAgreement + threeAgreement + fourAgreement + fiveAgreement)
        oneStart = startAgreement.length
        twoStart = startAgreement.length + oneAgreement.length
        threeStart = startAgreement.length + oneAgreement.length + twoAgreement.length
        fourStart = startAgreement.length + oneAgreement.length + twoAgreement.length + threeAgreement.length
        fiveStart = startAgreement.length + oneAgreement.length + twoAgreement.length + threeAgreement.length + fourAgreement.length
        fiveEnd = startAgreement.length + oneAgreement.length + twoAgreement.length + threeAgreement.length + fourAgreement.length + fiveAgreement.length

        initAgreementText(isFour)
        cons_fadada.clicks().subscribe {
            mPresenter?.let {
                if (cashLoanRecordEntity != null && cashLoanRecordEntity?.loanSigningResponse != null && cashLoanRecordEntity?.loanSigningResponse?.loanId != null)
                    it.getSignContract(cashLoanRecordEntity?.loanSigningResponse?.loanId.toString())
            }
        }
    }

    /**
     * 设置协议颜色和点击事件
     */
    private fun initAgreementText(isFour: String) {
        setAgreementTextClickAndColor(1, oneUrl, oneStart, twoStart)
        setAgreementTextClickAndColor(2, twoUrl, twoStart, threeStart)
        setAgreementTextClickAndColor(3, threeUrl, threeStart, fourStart)
        setAgreementTextClickAndColor(4, fourUrl, fourStart, fiveStart)
        if (isFour == "0") {
//            y有
            setAgreementTextClickAndColor(5, fiveUrl, fiveStart, fiveEnd)
        }
        tvAgreement.text = style
        tvAgreement.movementMethod = LinkMovementMethod.getInstance()
    }

    /**
     * @description ：点击协议跳转以及颜色设置
     * @lastEditor  ：杨昆
     * @version     ：v1.1 ,2019/11/18
     * @param url   ：协议链接
     * @param start ：协议点击起始区域
     * @param end   ：协议点击结束区域
     * @return      ：
     * @deprecated  ： false
     */
    private fun setAgreementTextClickAndColor(index: Int, url: String, start: Int, end: Int) {
        style.setSpan(NoLineClickable(View.OnClickListener {
            when(index) {
                1 -> openAgreementOneAndTwo(url)
                2 -> openAgreementOneAndTwo(url)
                3 -> openAgreementThree(url)
                4 -> openAgreementFour(url)
                5 -> openAgreementFive(url)
            }
        }), start, end, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
        style.setSpan(ForegroundColorSpan(agreementColor), start, end, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
    }

    private fun openAgreementFive(url: String) {
        val intent = Intent(this@CashLoanDetailedActivity, CommWebViewActivity::class.java)
        intent.putExtra(CommWebViewActivity.KEY_URL_NAME, url)
        intent.putExtra(CommWebViewActivity.KEY_JAVASCRIPT_TYPE, CommWebViewActivity.TYPE_JAVASCRIPT_JEL_LE_ME)
        intent.putExtra(CommWebViewActivity.KEY_IS_AUTHENTICATION, CommWebViewActivity.TYPE_TITLE_NORMAL)
        val map = HashMap<String, String>()
        if (cashLoanRecordEntity != null) {
            map["id"] = loanId.toString()
        }
        intent.putExtra(CommWebViewActivity.KEY_URL_PARAMS, map)
        startActivity(intent)
    }

    private fun openAgreementFour(url: String) {
        val intent = Intent(this@CashLoanDetailedActivity, CommWebViewActivity::class.java)
        intent.putExtra(CommWebViewActivity.KEY_URL_NAME, url)
        intent.putExtra(CommWebViewActivity.KEY_JAVASCRIPT_TYPE, CommWebViewActivity.TYPE_JAVASCRIPT_JEL_LE_ME)
        intent.putExtra(CommWebViewActivity.KEY_IS_AUTHENTICATION, CommWebViewActivity.TYPE_TITLE_NORMAL)
        val map = HashMap<String, String>()
        if (cashLoanRecordEntity != null) {
            map["loanId"] = loanId.toString()
        }
        intent.putExtra(CommWebViewActivity.KEY_URL_PARAMS, map)
        startActivity(intent)
    }

    /**
     * @description ： 打开协议页面入口
     * @lastEditor  ：杨昆
     * @version     ：v1.0 ,2019/11/1
     * @param null
     * @return      ：
     * @deprecated  ： false
     */
    private fun openAgreementThree(url: String) {
        val intent = Intent(this@CashLoanDetailedActivity, CommWebViewActivity::class.java)
        intent.putExtra(CommWebViewActivity.KEY_URL_NAME, url)
        intent.putExtra(CommWebViewActivity.KEY_JAVASCRIPT_TYPE, CommWebViewActivity.TYPE_JAVASCRIPT_JEL_LE_ME)
        intent.putExtra(CommWebViewActivity.KEY_IS_AUTHENTICATION, CommWebViewActivity.TYPE_TITLE_NORMAL)
        val map = HashMap<String, String>()
        if (cashLoanRecordEntity != null) {
            map["id"] = loanId.toString()
        }
        intent.putExtra(CommWebViewActivity.KEY_URL_PARAMS, map)
        startActivity(intent)
    }

    private fun openAgreementOneAndTwo(url: String) {
        val intent = Intent(this@CashLoanDetailedActivity, CommWebViewActivity::class.java)
        intent.putExtra(CommWebViewActivity.KEY_URL_NAME, url)
        intent.putExtra(CommWebViewActivity.KEY_JAVASCRIPT_TYPE, CommWebViewActivity.TYPE_JAVASCRIPT_JEL_LE_ME)
        intent.putExtra(CommWebViewActivity.KEY_IS_AUTHENTICATION, CommWebViewActivity.TYPE_TITLE_NORMAL)
        val map = HashMap<String, String>()
        if (cashLoanRecordEntity != null) {
            map["loanId"] = loanId.toString()
        }
        intent.putExtra(CommWebViewActivity.KEY_URL_PARAMS, map)
        startActivity(intent)
    }

    @SuppressLint("SetTextI18n")
    override fun returnRecordEntity(cashLoanRecordEntity: CashLoanRecordEntity) {
        this.cashLoanRecordEntity = cashLoanRecordEntity
        initWebUrlEntity(cashLoanRecordEntity.vipStatus)
        tvStatus.text = cashLoanRecordEntity.loanState
        tvLoanMoney.text = "${num2thousand00(cashLoanRecordEntity.loanFund)}元"
        tvDueDateDays.text = "${cashLoanRecordEntity.overdueDays}天"
        tvDueDateMoney.text = "${num2thousand00(cashLoanRecordEntity.demurrage)}元"
        tvLoanTime.text = cashLoanRecordEntity.loanDays
        tvLoanDate.text = cashLoanRecordEntity.created
        tvLoanBank.text = cashLoanRecordEntity.bank
        tvRepaymentDue.text = "${num2thousand00(cashLoanRecordEntity.stayRepaymentAmount)}元"
        tvRepaymentMade.text = "${num2thousand00(cashLoanRecordEntity.repaidAmount)}元"
        tvTitleHint.text = cashLoanRecordEntity.hintInfo
        when (cashLoanRecordEntity.loanStatus) {
//            借款状态标识1审核中2审核未通过3待还款4还款中5已结清6已逾期 ,
            1 -> {
                viewStatus.setBackgroundColor(ArmsUtils.getColor(this, R.color.colorAccent))
            }
            2 -> {
                viewStatus.setBackgroundColor(ArmsUtils.getColor(this, R.color.color_FF3763))
            }
            3 -> {
                viewStatus.setBackgroundColor(ArmsUtils.getColor(this, R.color.color_FF7213))
                llBottomMoney.visibility = View.VISIBLE
                vZhanwei.visibility = View.VISIBLE
            }
            4 -> {
                viewStatus.setBackgroundColor(ArmsUtils.getColor(this, R.color.color_545DFF))
                tvTitleHint.visibility = View.VISIBLE
//                rlDueDays.visibility = View.VISIBLE
//                rlDueMoney.visibility = View.VISIBLE
                llBottomMoney.visibility = View.VISIBLE
            }
            5 -> {
                viewStatus.setBackgroundColor(ArmsUtils.getColor(this, R.color.color_ADADAD))
//                rlDueDays.visibility = View.VISIBLE
//                rlDueMoney.visibility = View.VISIBLE
                llBottomMoney.visibility = View.VISIBLE
            }
            6 -> {
                viewStatus.setBackgroundColor(ArmsUtils.getColor(this, R.color.color_FF3763))
//                rlDueDays.visibility = View.VISIBLE
//                rlDueMoney.visibility = View.VISIBLE
                llBottomMoney.visibility = View.VISIBLE
                vZhanwei.visibility = View.VISIBLE
            }
        }
//        判断逾期（1.1）
        if (cashLoanRecordEntity.demurrage.equals("0") || cashLoanRecordEntity.demurrage.equals("0.00")) {
            rlDueDays.visibility = View.GONE
            rlDueMoney.visibility = View.GONE
        } else {
            rlDueDays.visibility = View.VISIBLE
            rlDueMoney.visibility = View.VISIBLE
        }

        //底部签约状态显示逻辑
        //内容栏状态 0不显示 1显示
        if ("1".equals(cashLoanRecordEntity?.loanSigningResponse?.type)) {
            cons_fadada.visibility = View.VISIBLE
            tv_go_to_sign.text = cashLoanRecordEntity?.loanSigningResponse?.buttonText
            tv_sign_top.text = cashLoanRecordEntity?.loanSigningResponse?.textOne
            tv_sign_bottom.text = cashLoanRecordEntity?.loanSigningResponse?.textTwo
            //按钮状态 0不可点击 1可点击 2隐藏"
            if ("0".equals(cashLoanRecordEntity?.loanSigningResponse?.buttonType)) {
                tv_go_to_sign.setTextColor(ContextCompat.getColor(this, R.color.color_ADADAD))
                tv_go_to_sign.setBackgroundResource(R.drawable.bg_loan_sign_unable)
                iv_sign_head.setImageResource(R.mipmap.ic_sign_reviewing)
                tv_go_to_sign.visibility = View.VISIBLE
                cons_fadada.isClickable = false
            } else if ("1".equals(cashLoanRecordEntity?.loanSigningResponse?.buttonType)) {
                tv_go_to_sign.setTextColor(ContextCompat.getColor(this, R.color.colorAccent))
                tv_go_to_sign.setBackgroundResource(R.drawable.bg_loan_sign_able)
                iv_sign_head.setImageResource(R.mipmap.ic_sign_reviewing)
                tv_go_to_sign.visibility = View.VISIBLE
                cons_fadada.isClickable = true
            } else {
                tv_go_to_sign.visibility = View.GONE
                iv_sign_head.setImageResource(R.mipmap.ic_sign_review_complete)
                cons_fadada.isClickable = false
            }
        } else {
            cons_fadada.visibility = View.GONE
        }
    }

    /**
     * @description ：返回法大大签约url
     * @lastEditor  ：张利超
     * @version     ：v1.0 ,2019/11/27
     * @param null
     * @return      ：
     * @deprecated  ： false
     */
    override fun returnSignContract(response: BaseResponse<Any>) {
        if (!"".equals(response.data.toString())) {
            val intent = Intent(this, CommWebViewActivity::class.java)
            intent.putExtra(CommWebViewActivity.KEY_URL_NAME, response.data.toString())
            intent.putExtra(CommWebViewActivity.KEY_URL_ADD_TYPE, false)
            intent.putExtra(CommWebViewActivity.KEY_JAVASCRIPT_TYPE, CommWebViewActivity.TYPE_JAVASCRIPT_JEL_LE_ME)
            intent.putExtra(CommWebViewActivity.KEY_IS_AUTHENTICATION, CommWebViewActivity.TYPE_TITLE_NORMAL)
            startActivity(intent)
        }
    }
    override fun canRepay() {
        val intent = Intent(this, PayBackMoneyActivity::class.java)
        intent.putExtra("type", type)
        intent.putExtra("loanId", loanId.toString())
        startActivity(intent)
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

    fun num2thousand00(num: String?): String {
        var numString = ""
        if (num == null) {
            return numString
        }
        var nf = NumberFormat.getInstance()
        try {
            var df = DecimalFormat("###0.00")
            numString = df.format(nf.parse(num))
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return numString
    }

    /**
     * @description ：返回按键监听
     * @lastEditor  ：张利超
     * @version     ：v1.0 ,2019/11/27
     * @param null
     * @return      ：
     * @deprecated  ： false
     */
    override fun onBackPressed() {
        super.onBackPressed()
        if (backType.equals("1")) {
            var intent = Intent(this, LoanRecordActivity::class.java)
            intent.putExtra("backType", backType)
            startActivity(intent)
            finish()
        } else {
            finish()
        }
    }
}
