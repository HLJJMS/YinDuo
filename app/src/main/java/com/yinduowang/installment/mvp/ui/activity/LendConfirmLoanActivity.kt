package com.yinduowang.installment.mvp.ui.activity

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Typeface
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.TextUtils
import android.text.method.LinkMovementMethod
import android.text.style.ForegroundColorSpan
import android.view.*
import android.widget.ImageView
import android.widget.PopupWindow
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.jakewharton.rxbinding3.view.clicks
import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils
import com.qmuiteam.qmui.layout.IQMUILayout.HIDE_RADIUS_SIDE_BOTTOM
import com.qmuiteam.qmui.layout.QMUIFrameLayout
import com.qmuiteam.qmui.util.QMUIDisplayHelper
import com.yinduowang.installment.R
import com.yinduowang.installment.app.constant.Api
import com.yinduowang.installment.app.utils.LoadingUtils
import com.yinduowang.installment.app.utils.NoLineClickable
import com.yinduowang.installment.app.utils.StringUtil
import com.yinduowang.installment.app.utils.ToastUtils
import com.yinduowang.installment.app.widget.BaseDialog
import com.yinduowang.installment.app.widget.popwindow.PasswordInputPopupWindow
import com.yinduowang.installment.di.component.DaggerLendConfirmLoanComponent
import com.yinduowang.installment.di.module.LendConfirmLoanModule
import com.yinduowang.installment.mvp.contract.LendConfirmLoanContract
import com.yinduowang.installment.mvp.model.entity.*
import com.yinduowang.installment.mvp.presenter.LendConfirmLoanPresenter
import com.yinduowang.installment.mvp.ui.adapter.DialogChoseBankCardAdapter
import com.yinduowang.installment.mvp.ui.adapter.DialogLoanQuickAdapter
import kotlinx.android.synthetic.main.activity_lend_confirm_loan.*
import java.util.concurrent.TimeUnit


/**
 * @Description:借款首页-确认申请页面
 * @Author: 张利超
 * @Date: 2019-10-31 10:26:52
 * @Version: v1.0, 2019-10-31
 * @LastEditors:张利超
 * @LastEditTime: 2019-10-31 10:26:52
 * @Deprecated: false
 */

class LendConfirmLoanActivity : BaseActivity<LendConfirmLoanPresenter>(), LendConfirmLoanContract.View {
    var loanBean: NewLoanEntity? = null
    var loanType: String = ""
    var loanId: String = ""
    var loanMoney: String = ""
    private var agreementColor: Int = 0
    private lateinit var style: SpannableStringBuilder
    private var oneStart: Int = 0
    private var twoStart: Int = 0
    private var threeStart: Int = 0
    private var fourStart: Int = 0
    private var fiveStart: Int = 0
    private var fiveEnd: Int = 0

    private var bankID: String = ""
    //此id为了更换默认银行卡从新赋值
    private var newBankID: String = ""
    private var popupWindow: PopupWindow? = null
    var confirmLoan: ConfirmLoanEntity? = null
    var adapter: DialogChoseBankCardAdapter? = null
    var rvDialog: RecyclerView? = null
    var view: View? = null

    val oneAgreement = "《借款咨询服务协议》"
    val twoAgreement = "《个人消费借款合同》"
    val threeAgreement = "《平台服务协议》"
    val fourAgreement = "《授权扣款委托书》"
    var fiveAgreement = "《VIP会员增值服务协议》"
    var startAgreement = "我已阅读并同意"
    val oneUrl = Api.AGREEMENT_LOAN_COUNSELING
    val twoUrl = Api.AGREEMENT_INDIVIDUAL_CONSUMPTION
    val threeUrl = Api.AGREEMENT_USER_SERVICE_PROTOCOL
    val fourUrl = Api.AGREEMENT_ENTRUST_DEDUCTION_PROXY
    var fiveUrl = Api.VIP_PROXY
    var bankCard = ""
    var bankName = ""
    var bank = ""

    companion object {
        val REQUEST_CODE_ADD_BANK: Int = 23045
        val RESULT_CODE_ADD_BANK_SUCCESS: Int = 23046
    }

    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerLendConfirmLoanComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .lendConfirmLoanModule(LendConfirmLoanModule(this))
                .build()
                .inject(this)
    }


    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_lend_confirm_loan //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    /**
     * @description ：初始化控件信息
     * @lastEditor  ：张利超
     * @version     ：v1.0 ,2019/10/31
     * @param null
     * @return      ：
     * @deprecated  ： false
     */
    @SuppressLint("CheckResult")
    override fun initData(savedInstanceState: Bundle?) {
        titleBar.showTitleAndBack(resources.getString(R.string.loanString))
        loanBean = intent.getSerializableExtra("loanBean") as NewLoanEntity?
        loanType = intent.getStringExtra("loanType")
        loanId = intent.getStringExtra("loanId")
        loanMoney = intent.getStringExtra("loanMoney")
        conLoanDetailed.clicks()
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe { unit ->
                    confirmLoan?.let {
                        if (it.budgetMoneyInfo != null && it.budgetMoneyInfo.size != 0)
                            lookLoanDetailed()
                    }
                }

        tvConfirmApply.clicks()
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe { unit ->
                    if (confirmLoan == null) {
                        return@subscribe
                    }
                    //验证码是否选择了银行卡
                    if (tvBankId.text.equals("")) {
                        ToastUtils.makeText(this, "请添加银行卡")
                        return@subscribe
                    }
                    //这里去验证调用接口执行下一步输入密码步骤
                    toVerifyPayWord()
                }
        constChoseBank.clicks()
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe { unit ->
                    mPresenter?.let { it.getBankCardList(newBankID) }
                }
    }

    /**
     * @description ：返回此页进行刷新页面
     * @lastEditor  ：张利超
     * @version     ：v1.0 ,2019/10/31
     * @param null
     * @return      ：
     * @deprecated  ： false
     */
    override fun onResume() {
        super.onResume()
        if (!"".equals(loanMoney))
            mPresenter?.let { it.getLoanApplyPage(loanMoney) }
    }

    private fun gotoSetttings() {
        ToastUtils.makeText(this, "短信权限请求失败，请到设置中给予权限")
    }

    /**
     * @description ：验证支付密码弹窗
     * @lastEditor  ：张利超
     * @version     ：v1.0 ,2019/10/31
     * @param {
     * payPswType：1为修改交易密码，非1则为设置交易密码
     * }
     * @return      ：
     * @deprecated  ： false
     */
    fun showSetPassWord(payPswType: String) {
        BaseDialog(this)
                .builder()
                .setCancelable(false)
                .setTitle("为了保障您的账户安全\n请设置支付密码")
                .setTitlePadding(0f, 22f,
                        0f, 23f)
                .setBtnLeft("取消", 16f, ContextCompat.getColor(this, R.color.color_9A9A9A))
                .setBtnRight("立即设置", 16f, ContextCompat.getColor(this, R.color.color_365AF7), Typeface.NORMAL, object : BaseDialog.BaseDialogBuilder.BtnClickListener {
                    override fun onClick(dialog: BaseDialog) {
                        val intent = Intent(applicationContext, SetTradePwdActivity::class.java)
                        intent.putExtra("payPswType", payPswType)
                        startActivity(intent)
                    }
                })
                .create()
                .show()
    }

    /**
     * @description ：验证密码接口
     * @lastEditor  ：张利超
     * @version     ：v1.0 ,2019/10/31
     * @param null
     * @return      ：
     * @deprecated  ： false
     */
    fun toVerifyPayWord() {
        mPresenter?.let {
            it.getVaildatePswSet()
        }
    }


    /**
     * @description ：查看借款详细弹窗
     * @lastEditor  ：张利超
     * @version     ：v1.0 ,2019/10/31
     * @param null
     * @return      ：
     * @deprecated  ： false
     */
    fun lookLoanDetailed() {
        loanBean?.let {
            val view: View = LayoutInflater.from(this).inflate(R.layout.layout_loan_detailed_dialog, null)
            view.minimumWidth = (ArmsUtils.getScreenWidth(this) * 0.73).toInt()
            view.minimumHeight = (ArmsUtils.getScreenHeidth(this) * 0.54).toInt()
            val dialog: AlertDialog = AlertDialog.Builder(this).create()
            dialog.setCancelable(false)
            dialog.setView(view)
            val qmuilayout = view.findViewById<QMUIFrameLayout>(R.id.qmuilayout)
            // 调整dialog背景大小
            qmuilayout.radius = QMUIDisplayHelper.dp2px(this, 8)
            val adapter = DialogLoanQuickAdapter(getDialogList())
            val rvDialog = view.findViewById<RecyclerView>(R.id.rv_dialog_loan_detailed)
            val window = dialog.window
            view.findViewById<ImageView>(R.id.iv_cancle_chose).setOnClickListener {
                dialog.dismiss()
            }
            window?.setBackgroundDrawableResource(android.R.color.transparent)
            rvDialog.layoutManager = LinearLayoutManager(this)
            rvDialog.adapter = adapter
            dialog.show()
            val attr = window?.attributes
            attr?.height = (ArmsUtils.getScreenHeidth(this) * 0.54).toInt()
            attr?.width = (ArmsUtils.getScreenWidth(this) * 0.73).toInt()
            window?.attributes = attr
        }
    }

    /**
     * @description ：提交申请并且弹出输入密码弹窗
     * @lastEditor  ：张利超
     * @version     ：v1.0 ,2019/10/31
     * @param null
     * @return      ：
     * @deprecated  ： false
     */
    private fun toLoan() {
        loanBean?.let {
            //输入密码弹窗
            var loanMoney = loanMoney.replace(",", "")
            PasswordInputPopupWindow(this).setOnInputCompleteListener(object : PasswordInputPopupWindow.OnInputCompleteListener {
                override fun complete(password: String) {
                    if (!TextUtils.isEmpty(password)) {
                        confirmLoan?.let {
                            it.configId?.let { it1 ->
                                mPresenter?.confirmApplyLoan(password, loanId, loanMoney, "1", "", bankID, it1, confirmLoan?.lonaDays.toString())
                            }
                        }

                    }
                }
            }).show()
        }
    }


    /**
     * @description ：回调验证是否设置了交易密码如果没有设置则进入设置页面否则进输入密码弹窗页面
     * @lastEditor  ：张利超
     * @version     ：v1.0 ,2019/10/31
     * @param {
     * payPswType：是否设置了交易密码 1代表已设置
     * }
     * @return      ：
     * @deprecated  ： false
     */
    override fun returnPayPswIsSet(payPswType: String) {
        if (payPswType.equals("1")) {
            toLoan()
        } else {
            if (!TextUtils.equals(payPswType, "")) {
                showSetPassWord(payPswType)
            }
        }
    }


    /**
     * @description ：添加银行卡成功后回调
     * @lastEditor  ：张利超
     * @version     ：v1.0 ,2019/10/31
     * @param null
     * @return      ：
     * @deprecated  ： true
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == REQUEST_CODE_ADD_BANK && resultCode == RESULT_CODE_ADD_BANK_SUCCESS) {
//            mPresenter?.let { it.getLoanApplyPage(loanMoney) }
//            mPresenter?.let { it.getBankCardList(newBankID) }
//        }
    }

    /**
     * @description ：初始化协议相关信息
     * @lastEditor  ：张利超
     * @version     ：v1.0 ,2019/10/31
     * @param null
     * @return      ：
     * @deprecated  ： false
     */
    private fun initWebUrlEntity() {
        agreementColor = ContextCompat.getColor(this, R.color.colorAccent)
        cbAgreement.setOnCheckedChangeListener { buttonView, isChecked ->
            checkedChange(isChecked)
        }
        cbAgreement.setChecked(true)
        if (confirmLoan != null) {
            if ("0".equals(confirmLoan?.valuesAddedStatus)) {
                fiveAgreement = "《VIP会员增值服务协议》"
            } else {
                fiveAgreement = ""
            }
        }
        style = SpannableStringBuilder(startAgreement + oneAgreement + twoAgreement + threeAgreement + fourAgreement + fiveAgreement)
        oneStart = startAgreement.length
        twoStart = startAgreement.length + oneAgreement.length
        threeStart = startAgreement.length + oneAgreement.length + twoAgreement.length
        fourStart = startAgreement.length + oneAgreement.length + twoAgreement.length + threeAgreement.length
        fiveStart = startAgreement.length + oneAgreement.length + twoAgreement.length + threeAgreement.length + fourAgreement.length
        fiveEnd = startAgreement.length + oneAgreement.length + twoAgreement.length + threeAgreement.length + fourAgreement.length + fiveAgreement.length

        initAgreementText()
    }


    private fun checkedChange(isChecked: Boolean) {
        tvConfirmApply.isEnabled = isChecked
    }

    /**
     * @description ：设置协议颜色和点击事件
     * @lastEditor  ：杨昆
     * @version     ：v1.1 ,2019/11/18
     * @param null
     * @return      ：
     * @deprecated  ： false
     */
    private fun initAgreementText() {
        setAgreementTextClickAndColor(1, oneUrl, oneStart, twoStart)
        setAgreementTextClickAndColor(2, twoUrl, twoStart, threeStart)
        setAgreementTextClickAndColor(3, threeUrl, threeStart, fourStart)
        setAgreementTextClickAndColor(4, fourUrl, fourStart, fiveStart)
        if (confirmLoan != null) {
            if ("0".equals(confirmLoan?.valuesAddedStatus)) {
                setAgreementTextClickAndColor(5, fiveUrl, fiveStart, fiveEnd)
            }
        }

        tvLoanAgreement.text = style
        tvLoanAgreement.movementMethod = LinkMovementMethod.getInstance()
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
            when (index) {
                1 -> openAgreementOne(url)
                2 -> openAgreementTwo(url)
                3 -> openAgreementThree(url)
                4 -> openAgreementFour(url)
                5 -> openAgreementFive(url)
            }
        }), start, end, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
        style.setSpan(ForegroundColorSpan(agreementColor), start, end, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
    }


    /**
     * @description ：点击选择银行卡弹窗
     * @lastEditor  ：张利超
     * @version     ：v1.0 ,2019/10/31
     * @param null
     * @return      ：
     * @deprecated  ： false
     */
    private fun choseBankCard(list: List<UserBank>) {

        if (popupWindow == null) {
            view = LayoutInflater.from(this).inflate(R.layout.layout_dialog_chose_bank_card, null)
            popupWindow = PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, (ArmsUtils.getScreenHeidth(this) * 0.53).toInt())
            popupWindow!!.isFocusable = false
            popupWindow!!.setBackgroundDrawable(BitmapDrawable())
            val qmuilayout = view!!.findViewById<QMUIFrameLayout>(R.id.qmuilayout)
            qmuilayout.setRadius(QMUIDisplayHelper.dp2px(this, 12), HIDE_RADIUS_SIDE_BOTTOM)
            adapter = DialogChoseBankCardAdapter(list)
            val bottomView: View = LayoutInflater.from(this).inflate(R.layout.layout_dialog_chose_bank_bottom, null)
            bottomView.setOnClickListener {
                popupWindow!!.dismiss()
                this@LendConfirmLoanActivity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                setBackgroundAlpha(1F)
                startActivityForResult(Intent(this, AddBankCardActivity::class.java), REQUEST_CODE_ADD_BANK)
            }
            val cancelChose = view!!.findViewById<ImageView>(R.id.iv_cancle_chose)
            cancelChose.setOnClickListener {
                popupWindow!!.dismiss()
                this@LendConfirmLoanActivity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                setBackgroundAlpha(1F)
            }
            rvDialog = view!!.findViewById<RecyclerView>(R.id.rv_dialog_bank)
            rvDialog!!.layoutManager = LinearLayoutManager(this)

            adapter!!.addFooterView(bottomView)
            rvDialog!!.adapter = adapter
            adapter!!.setOnItemClickListener(BaseQuickAdapter.OnItemClickListener { adapter, view, position ->
                bankID = list[position].id
                newBankID = bankID
                bankCard = list[position].bankCard
                bankName = list[position].bankName
                bank = list[position].bankName + "(" + list[position].bankCard + ")"
                tvBankId.text = list[position].bankName + "(${list[position].endCardNo})"
                setBackgroundAlpha(1F)
                popupWindow!!.dismiss()
                this@LendConfirmLoanActivity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            })
            setBackgroundAlpha(0.5F)
            popupWindow!!.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        } else {
            adapter!!.setNewData(list)
            setBackgroundAlpha(0.5F)
            popupWindow!!.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        }
        this@LendConfirmLoanActivity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    /**
     * @description ：弹窗虚化背景颜色
     * @lastEditor  ：张利超
     * @version     ：v1.0 ,2019/10/31
     * @param null
     * @return      ：
     * @deprecated  ： false
     */
    private fun setBackgroundAlpha(bgAlpha: Float) {
        val lp = window.attributes
        lp.alpha = bgAlpha
        window.attributes = lp
    }

    private fun openAgreementFive(url: String) {
        val intent = Intent(this@LendConfirmLoanActivity, CommWebViewActivity::class.java)
        intent.putExtra(CommWebViewActivity.KEY_URL_NAME, url)
        intent.putExtra(CommWebViewActivity.KEY_JAVASCRIPT_TYPE, CommWebViewActivity.TYPE_JAVASCRIPT_JEL_LE_ME)
        intent.putExtra(CommWebViewActivity.KEY_IS_AUTHENTICATION, CommWebViewActivity.TYPE_TITLE_NORMAL)
        startActivity(intent)
    }

    private fun openAgreementFour(url: String) {
        val intent = Intent(this@LendConfirmLoanActivity, CommWebViewActivity::class.java)
        intent.putExtra(CommWebViewActivity.KEY_URL_NAME, url)
        intent.putExtra(CommWebViewActivity.KEY_JAVASCRIPT_TYPE, CommWebViewActivity.TYPE_JAVASCRIPT_JEL_LE_ME)
        intent.putExtra(CommWebViewActivity.KEY_IS_AUTHENTICATION, CommWebViewActivity.TYPE_TITLE_NORMAL)
        val map = HashMap<String, String>()
        confirmLoan?.let {
            map["bankName"] = bankName
            map["bankCard"] = bankCard
        }
        intent.putExtra(CommWebViewActivity.KEY_URL_PARAMS, map)
        startActivity(intent)
    }

    private fun openAgreementThree(url: String) {
        val intent = Intent(this@LendConfirmLoanActivity, CommWebViewActivity::class.java)
        intent.putExtra(CommWebViewActivity.KEY_URL_NAME, url)
        intent.putExtra(CommWebViewActivity.KEY_JAVASCRIPT_TYPE, CommWebViewActivity.TYPE_JAVASCRIPT_JEL_LE_ME)
        intent.putExtra(CommWebViewActivity.KEY_IS_AUTHENTICATION, CommWebViewActivity.TYPE_TITLE_NORMAL)
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
    private fun openAgreementTwo(url: String) {
        val intent = Intent(this@LendConfirmLoanActivity, CommWebViewActivity::class.java)
        intent.putExtra(CommWebViewActivity.KEY_URL_NAME, url)
        intent.putExtra(CommWebViewActivity.KEY_JAVASCRIPT_TYPE, CommWebViewActivity.TYPE_JAVASCRIPT_JEL_LE_ME)
        intent.putExtra(CommWebViewActivity.KEY_IS_AUTHENTICATION, CommWebViewActivity.TYPE_TITLE_NORMAL)
        val map = HashMap<String, String>()
        map["amount"] = loanMoney
        map["purpose"] = loanType
        confirmLoan?.let {
            map["loanDays"] = it.lonaDays.toString()
            map["bankCard"] = bankCard
        }
        intent.putExtra(CommWebViewActivity.KEY_URL_PARAMS, map)
        startActivity(intent)
    }

    private fun openAgreementOne(url: String) {
        val intent = Intent(this@LendConfirmLoanActivity, CommWebViewActivity::class.java)
        intent.putExtra(CommWebViewActivity.KEY_URL_NAME, url)
        intent.putExtra(CommWebViewActivity.KEY_JAVASCRIPT_TYPE, CommWebViewActivity.TYPE_JAVASCRIPT_JEL_LE_ME)
        intent.putExtra(CommWebViewActivity.KEY_IS_AUTHENTICATION, CommWebViewActivity.TYPE_TITLE_NORMAL)
        startActivity(intent)
    }


    /**
     * @description ： 输入密码回调及弹窗展示
     * @lastEditor  ：张利超
     * @version     ：v1.0 ,2019/10/31
     * @param null
     * @return      ：
     * @deprecated  ： false
     */
    override fun returnConfirmLoan(baseResponse: BaseResponse<ConfirmEntity>) {
        if (baseResponse.code.equals(Api.RequestSuccess)) {
            val intent = Intent(this, ApplyLoanSuccessActivity::class.java)
            baseResponse.data?.let {
                intent.putExtra("isVip", it.isVip)
                intent.putExtra("url", it.url)
                intent.putExtra("loanId", it.loanId)
            }
            startActivity(intent)
            finish()
        } else if (baseResponse.code.equals("100011")) {
            //密码输入错误
            passWordDialogWrong()
        } else if (baseResponse.code.equals("100010")) {
            //密码输入五次错误展示对话框
            fiveTimesWordDialog()
        } else {
            ToastUtils.makeText(this, baseResponse.msg)
        }

    }

    /**
     * @description ：输入测试密码上限弹窗
     * @lastEditor  ：张利超
     * @version     ：v1.0 ,2019/10/31
     * @param null
     * @return      ：
     * @deprecated  ： false
     */
    private fun fiveTimesWordDialog() {
        BaseDialog(this)
                .builder()
                .setCancelable(false)
                .setTitle("错误次数已达上限，请明天再试")
                .setBtnLeft("我知道了", ContextCompat.getColor(this, R.color.color_545DFF))
                .create()
                .show()
    }

    /**
     * @description ：输入密码错误弹窗
     * @lastEditor  ：张利超
     * @version     ：v1.0 ,2019/10/31
     * @param null
     * @return      ：
     * @deprecated  ： false
     */
    private fun passWordDialogWrong() {
        BaseDialog(this)
                .builder()
                .setCancelable(false)
                .setTitle("支付密码不正确")
                .setBtnLeftTextColorResources(R.color.colorAccent)
                .setBtnLeft("重新输入", object : BaseDialog.BaseDialogBuilder.BtnClickListener {
                    override fun onClick(dialog: BaseDialog) {
                        toLoan()
                    }
                })
                .setBtnRight("忘记密码", object : BaseDialog.BaseDialogBuilder.BtnClickListener {
                    override fun onClick(dialog: BaseDialog) {
                        startActivity(Intent(this@LendConfirmLoanActivity, ForgetPayPasswordActivity::class.java))
                    }
                })
                .create()
                .show()
    }

    /**
     * @description ：银行卡获取列表
     * @lastEditor  ：张利超
     * @version     ：v1.0 ,2019/10/31
     * @param null
     * @return      ：
     * @deprecated  ： false
     */
    override fun getBankCardListSuccess(userBanks: List<UserBank>) {
        choseBankCard(userBanks)
    }


    /**
     * @description ：进入页面返回值
     * @lastEditor  ：张利超
     * @version     ：v1.0 ,2019/10/31
     * @param null
     * @return      ：
     * @deprecated  ： false
     */
    override fun getLoanApplyPage(confirmLoanEntity: ConfirmLoanEntity) {
        confirmLoan = confirmLoanEntity
        bankCard = confirmLoan!!.bankCard.toString()
        bankName = confirmLoan!!.bankInfo.toString()
        bank = confirmLoan!!.bankInfo.toString() + "(" + confirmLoan!!.cardNoLastFour.toString() + ")"
        bankID = confirmLoanEntity.bankDefaultId.toString()
        tv_pay_back_money.text = StringUtil.num2thousand00(confirmLoanEntity.loanMoney)
        tv_loan_date.text = "" + confirmLoanEntity.lonaDays + confirmLoanEntity.cycleType
        tvLoanRate.text = confirmLoanEntity.repaymentDay
        if (confirmLoanEntity.isBindBank.equals("1")) {
            tvBankId.text = confirmLoanEntity.bankInfo + "(${confirmLoanEntity.cardNoLastFour})"
        } else {
            tvBankId.text = ""
            tvBankId.hint = "请添加银行卡"
        }
        initWebUrlEntity()
    }

    public fun getDialogList(): ArrayList<MultiItemEntity> {
        var list = arrayListOf<MultiItemEntity>()
        confirmLoan?.budgetMoneyInfo?.forEach { index ->
            var lv0 = BudgetMoneyInfo(index.budgetInfo, index.repayFund, index.stage)
            lv0.budgetInfo!!.forEach { it ->
                lv0.addSubItem(it)
            }
            list.add(lv0)
        }
        return list
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

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        popupWindow?.let {
            if (it.isShowing)
                return false
        }
        return super.dispatchTouchEvent(ev)
    }
}
