package com.yinduowang.installment.mvp.ui.activity


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
import android.view.View.GONE
import android.widget.ImageView
import android.widget.PopupWindow
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.jakewharton.rxbinding3.view.clicks
import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.integration.AppManager
import com.jess.arms.utils.ArmsUtils
import com.qmuiteam.qmui.layout.IQMUILayout
import com.qmuiteam.qmui.layout.QMUIFrameLayout
import com.qmuiteam.qmui.util.QMUIDisplayHelper
import com.yinduowang.installment.R
import com.yinduowang.installment.app.constant.Api
import com.yinduowang.installment.app.utils.LoadingUtils
import com.yinduowang.installment.app.utils.NoLineClickable
import com.yinduowang.installment.app.utils.ToastUtils
import com.yinduowang.installment.app.widget.BaseDialog
import com.yinduowang.installment.app.widget.popwindow.PasswordInputPopupWindow
import com.yinduowang.installment.di.component.DaggerPaymentTypeComponent
import com.yinduowang.installment.di.module.PaymentTypeModule
import com.yinduowang.installment.mvp.contract.PaymentTypeContract
import com.yinduowang.installment.mvp.model.entity.GetOrderPay
import com.yinduowang.installment.mvp.model.entity.GetOrderPayPopuwindowOut
import com.yinduowang.installment.mvp.presenter.PaymentTypePresenter
import com.yinduowang.installment.mvp.ui.adapter.DialogRepaymentDetailForPaymentAdapter
import com.yinduowang.installment.mvp.ui.adapter.PaymentTypeAdapter
import kotlinx.android.synthetic.main.activity_payment_type.*
import java.util.concurrent.TimeUnit


/**
 * Description：支付方式activity
 * Author：田羽衡
 * Version：<v1.0，2019/11/1 >
 * LastEditTime：2019/11/1
 * LastEditors:
 * Deprecated： false
 */

class PaymentTypeActivity : BaseActivity<PaymentTypePresenter>(), PaymentTypeContract.View {


    lateinit var adapter: PaymentTypeAdapter
    var intentType = ""
    var config_id = ""
    var id = ""
    var rmb = ""
    var pwd_status = ""
    var cycle = ""
    var buttonTxt = ""
    var pageTxt = ""
    var code = ""
    var bean: GetOrderPay? = null
    var valueAddedService = ""
    lateinit var qmuilayout: QMUIFrameLayout
    lateinit var tvTotalTxt: TextView
    lateinit var myRmb: TextView
    lateinit var tvInterest: TextView
    lateinit var tvMonthlyInterest: TextView
    lateinit var tvPeriods: TextView
    lateinit var tvMonthRmb: TextView
    var rmbTotal: String = ""
    private var agreementColor: Int = 0
    private lateinit var style: SpannableStringBuilder
    private var oneStart: Int = 0
    private var oneEnd: Int = 0
    private var threeStart: Int = 0
    private var threeEnd: Int = 0
    private var twoEnd: Int = 0
    val oneAgreement = "《信用服务协议》"
    var twoAgreement = "《VIP会员增值服务协议》"
    val threeAgreement = "《失信风险警示》"
    val oneUrl = Api.AGREEMENT_SHOP_CREDIT
    val twoUrl = Api.VIP_PROXY
    val threeUrl = Api.AGREEMENT_LOST_LETTER
    var isfirstGet: Boolean = true
    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerPaymentTypeComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .paymentTypeModule(PaymentTypeModule(this))
                .build()
                .inject(this)
    }


    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_payment_type //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    override fun initData(savedInstanceState: Bundle?) {
        titleBar.showTitleAndBack(R.string.payment_type, true, View.OnClickListener {
            showQuitDailog()
        })
        intent.getStringExtra("o_id")?.let { id = it }
        intent.getStringExtra("rmb")?.let { rmb = it }
        intent.getStringExtra("intentType")?.let { intentType = it }

        adapter = PaymentTypeAdapter(arrayListOf())
        reTermCounts.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        reTermCounts.adapter = adapter
        adapter.setOnItemClickListener { adapter, view, position ->
            this.adapter.setAdapterPosition(position)
            mPresenter?.getOrderDetail(id, this.adapter.data.get(position))
        }
        //        已含利息弹窗
        viewss.clicks()
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe {
                    lookLoanDetailed()
                }
//        已含利息弹窗
        textView60.clicks()
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe {
                    lookLoanDetailed()
                }
        //        确认支付
        tvConfirmPay.clicks()
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe {
                    judgePay()
                }
//        授信额度支付
        clCheck.clicks()
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe() {
                    if (clCheck.tag.equals("true")) {
                        cbPayStyle.visibility = GONE
                        clCheck.tag = "false"
                    } else {
                        cbPayStyle.visibility = View.VISIBLE
                        clCheck.tag = "true"
                    }
                    checkedChange()

                }
    }

    /**
     * 描  述：三个协议并且修改字体边距
     * 修改人：张利超
     * 版本号：<v1.0，2019/10/30 10:59>
     * @param null
     * 返回值：
     * 修改时间：2019/10/30 11:05
     * 弃⽤： false
     */
    private fun initWebUrlEntity() {
        agreementColor = ContextCompat.getColor(this, R.color.colorAccent)
        cbAgreement.setOnCheckedChangeListener { buttonView, isChecked ->
            checkedChange()
        }
        cbAgreement.setChecked(true)
        if ("0".equals(valueAddedService)) {
            twoAgreement = "《VIP会员增值服务协议》"
        } else {
            twoAgreement = ""
        }
        var oneAgreement = oneAgreement
        var twoAgreement = twoAgreement
        var threeAgreement = threeAgreement
        var startAgreement = "同意"
        var centerAgreement = "并已查看"

        style = SpannableStringBuilder(startAgreement + oneAgreement + twoAgreement + centerAgreement + threeAgreement)
        oneStart = startAgreement.length
        oneEnd = startAgreement.length + oneAgreement.length
        twoEnd = startAgreement.length + oneAgreement.length + twoAgreement.length
        threeStart = startAgreement.length + oneAgreement.length + twoAgreement.length + centerAgreement.length
        threeEnd = startAgreement.length + oneAgreement.length + twoAgreement.length + centerAgreement.length + threeAgreement.length
        initAgreementText()
    }


    /**
     * 描  述：   判断协议是否同意和付款方式是否勾选（）
     * 修改人：田羽衡
     * 版本号：<v1.0，2019/10/30 10:59>
     * @param null
     * 返回值：
     * 修改时间：2019/10/30 11:05
     * 弃⽤： false
     */
    private fun checkedChange() {
        if (cbAgreement.isChecked == true && clCheck.tag.equals("true") && (code.equals("0") || code.equals("1"))) {
            tvConfirmPay.isEnabled = true
        } else {
            tvConfirmPay.isEnabled = false
        }
    }

    /**
     * 描  述：设置协议颜色和点击事件
     * 修改人：田羽衡
     * 版本号：<v1.0，2019/10/30 10:59>
     * @param null
     * 返回值：
     * 修改时间：2019/10/30 11:05
     * 弃⽤： false
     */
    private fun initAgreementText() {
        setAgreementTextClickAndColorOne()
        setAgreementTextClickAndColorTwo()
        setAgreementTextClickAndColorThree()
        tvPayAgreement.text = style
        tvPayAgreement.movementMethod = LinkMovementMethod.getInstance()
    }

    /**
     * 描  述：第一个协议点击事件
     * 修改人：田羽衡
     * 版本号：<v1.0，2019/10/30 10:59>
     * @param null
     * 返回值：
     * 修改时间：2019/10/30 11:05
     * 弃⽤： false
     */
    private fun setAgreementTextClickAndColorOne() {
        style.setSpan(NoLineClickable(View.OnClickListener {
            openAgreement(oneUrl)
        }), oneStart, oneEnd, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
        style.setSpan(ForegroundColorSpan(agreementColor), oneStart, oneEnd, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
    }

    /**
     * 描  述：第2个协议点击事件
     * 修改人：田羽衡
     * 版本号：<v1.0，2019/10/30 10:59>
     * @param null
     * 返回值：
     * 修改时间：2019/10/30 11:05
     * 弃⽤： false
     */
    private fun setAgreementTextClickAndColorTwo() {
        style.setSpan(NoLineClickable(View.OnClickListener {
            openAgreement(twoUrl)
        }), oneEnd, twoEnd, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
        style.setSpan(ForegroundColorSpan(agreementColor), oneEnd, twoEnd, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
    }

    /**
     * 描  述：第3个协议点击事件
     * 修改人：田羽衡
     * 版本号：<v1.0，2019/10/30 10:59>
     * @param null
     * 返回值：
     * 修改时间：2019/10/30 11:05
     * 弃⽤： false
     */
    private fun setAgreementTextClickAndColorThree() {
        style.setSpan(NoLineClickable(View.OnClickListener {
            openAgreement(threeUrl)
        }), threeStart, threeEnd, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)

        style.setSpan(ForegroundColorSpan(agreementColor), threeStart, threeEnd, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
    }


    /**
     * 描  述：打开协议页面
     * 修改人：田羽衡
     * 版本号：<v1.0，2019/10/30 10:59>
     * @param null
     * 返回值：
     * 修改时间：2019/10/30 11:05
     * 弃⽤： false
     */
    private fun openAgreement(url: String) {
        val intent = Intent(this, CommWebViewActivity::class.java)
        intent.putExtra(CommWebViewActivity.KEY_URL_NAME, url)
        intent.putExtra(CommWebViewActivity.KEY_JAVASCRIPT_TYPE, CommWebViewActivity.TYPE_JAVASCRIPT_JEL_LE_ME)
        intent.putExtra(CommWebViewActivity.KEY_IS_AUTHENTICATION, CommWebViewActivity.TYPE_TITLE_NORMAL)
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

    /**
     * 描  述：效验额度状态返回数据
     * 修改人：田羽衡
     * 版本号：<v1.0，2019/10/30 10:59>
     * @param null
     * 返回值：
     * 修改时间：2019/10/30 11:05
     * 弃⽤： false
     */
    override fun checkQuotaSuccess(code: String, button: String, txt: String, valueAddedService: String) {
        this.code = code
        pageTxt = txt
        buttonTxt = button
        textView64.setText(pageTxt)
        this.valueAddedService = valueAddedService
        initWebUrlEntity()
        if (code.equals("0") || code.equals("1")) {
            tvConfirmPay.setText(buttonTxt)
            tvConfirmPay.isEnabled = true
        } else {
            tvConfirmPay.setText(buttonTxt)
            tvConfirmPay.isEnabled = false
        }
    }

    override fun onResume() {
        super.onResume()
//        重置付款方式状态，阅读协议状态为true
        cbAgreement.isChecked = true
        cbPayStyle.visibility = View.VISIBLE
        clCheck.tag = "true"
        checkedChange()
        mPresenter?.getOrderDetail(id, cycle)
    }

    /**
     * 描  述：返回按钮拦截
     * 修改人：田羽衡
     * 版本号：<v1.0，2019/10/30 10:59>
     * @param null
     * 返回值：
     * 修改时间：2019/10/30 11:05
     * 弃⽤： false
     */
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && event?.getRepeatCount() == 0) { //按下的如果是BACK，同时没有重复
            showQuitDailog()
            return true;
        }
        return super.onKeyDown(keyCode, event)
    }


    /**
     * 描  述：获取订单详情成功回调
     * 修改人：田羽衡
     * 版本号：<v1.0，2019/10/30 10:59>
     * @param null
     * 返回值：
     * 修改时间：2019/10/30 11:05
     * 弃⽤： false
     */
    override fun getShopOredeDetailSuccess(bean: GetOrderPay) {
        this.bean = bean
        textView58.text = "￥" + bean.total_money
        pwd_status = bean.pwd_status
        cycle = bean.cycle
        adapter.setNewData(bean.cycle_list)
        rmbTotal = bean.total_money
        tv_pay_back_money.setText(bean.repay_of_month)
        textView64.setText(pageTxt)
        config_id = bean.config_id
//        判断是否为第一次加载如果为第一次加载走这个逻辑寻找默认期数
        if (isfirstGet) {
            isfirstGet = false
            adapter.setAdapterPosition(bean?.cycle_list.size - 1)
        }

    }


//    ——————————————————————————支付回调判断——————————————————


    /**
     * Description 回调toast返回并且去订单列表页
     * Author  田羽衡
     * Version  <v1.0，2019/11/5 14:37>
     * params
     * return
     * LastEditTime  2019/11/5 14:37
     * Deprecated   false
     */
    override fun toastAndGoOlderLlist(txt: String) {
        ToastUtils.makeText(this, txt)
        goOrderList()
    }


    /**
     * 描  述：支付成功
     * 修改人：田羽衡
     * 版本号：<v1.0，2019/10/30 10:59>
     * @param null
     * 返回值：
     * 修改时间：2019/10/30 11:05
     * 弃⽤： false
     */
    override fun postPaySuccess() {
        var intent = Intent(this@PaymentTypeActivity, ShopPayResultActivity::class.java)
        intent.putExtra("type", "success")
        intent.putExtra("rmbTotal", rmbTotal)
        intent.putExtra("o_id", id)
        startActivity(intent)
        killMyself()
    }


    /**
     * 描  述：  密码错误
     * 修改人：田羽衡
     * 版本号：<v1.0，2019/10/30 10:59>
     * @param null
     * 返回值：
     * 修改时间：2019/10/30 11:05
     * 弃⽤： false
     */
    override fun showPassWordError(txt: String) {
        BaseDialog(this)
                .builder()
                .setCancelable(false)
                .setTitle(txt)
                .setBtnLeft("重新输入", object : BaseDialog.BaseDialogBuilder.BtnClickListener {
                    override fun onClick(dialog: BaseDialog) {
                        inputPassword()
                    }
                })
                .setBtnLeftTextColor(ContextCompat.getColor(this, R.color.color_365AF7))
                .setBtnRight("忘记密码", object : BaseDialog.BaseDialogBuilder.BtnClickListener {
                    override fun onClick(dialog: BaseDialog) {
                        startActivity(Intent(this@PaymentTypeActivity, ForgetPayPasswordActivity::class.java))
                    }
                })
                .create()
                .show()
    }

    /**
     * 描  述：  密码错误五次
     * 修改人：田羽衡
     * 版本号：<v1.0，2019/10/30 10:59>
     * @param null
     * 返回值：
     * 修改时间：2019/10/30 11:05
     * 弃⽤： false
     */
    override fun showPassWordErrorFive(txt: String) {
        BaseDialog(this)
                .builder()
                .setCancelable(false)
                .setTitle(txt, 16f)
                .setTitlePaddingTop(22f)
                .setTitlePaddingBottom(26f)
                .setTitleLineSpacingExtra(25f)
                .setBtnLeft("我知道了", 16f, ContextCompat.getColor(this, R.color.color_365AF7), Typeface.NORMAL, object : BaseDialog.BaseDialogBuilder.BtnClickListener {
                    override fun onClick(dialog: BaseDialog) {
                        if (intentType.equals("ShopConfirmationOrderActivity")) {
                            var intent = Intent(this@PaymentTypeActivity, ShopPayResultActivity::class.java)
                            intent.putExtra("type", "fail")
                            intent.putExtra("rmbTotal", rmbTotal)
                            intent.putExtra("o_id", id)
                            startActivity(intent)
                            finish()
                        } else {
                            finish()
                        }
                    }
                })
                .create()
                .show()
    }

    /**
     * 描  述： 有逾期
     * 修改人：田羽衡
     * 版本号：<v1.0，2019/10/30 10:59>
     * @param null
     * 返回值：
     * 修改时间：2019/10/30 11:05
     * 弃⽤： false
     */

    override fun showOverdue(txt: String) {
        BaseDialog(this)
                .builder()
                .setCancelable(false)
                .setTitle(txt, 16f)
                .setTitlePadding(15f, 22f, 15f, 23f)
                .setTitleLineSpacingExtra(25f)
                .setBtnLeft("我知道了", 16f, ContextCompat.getColor(this, R.color.color_365AF7), Typeface.NORMAL, object : BaseDialog.BaseDialogBuilder.BtnClickListener {
                    override fun onClick(dialog: BaseDialog) {
                        if (intentType.equals("ShopConfirmationOrderActivity")) {
                            var intent = Intent(this@PaymentTypeActivity, ShopPayResultActivity::class.java)
                            intent.putExtra("type", "fail")
                            intent.putExtra("rmbTotal", rmbTotal)
                            intent.putExtra("o_id", id)
                            startActivity(intent)
                            finish()
                        } else {
                            finish()
                        }
                    }
                })
                .create()
                .show()
    }

    /**
     * 描  述：弹窗点我知道了去订单列表页
     * 修改人：田羽衡
     * 版本号：<v1.0，2019/10/30 10:59>
     * @param null
     * 返回值：
     * 修改时间：2019/10/30 11:05
     * 弃⽤： false
     */
    override fun dialogAndGoOrderList(txt: String) {
        BaseDialog(this)
                .builder()
                .setCancelable(false)
                .setTitle(txt, 16f)
                .setTitlePaddingTop(22f)
                .setTitlePaddingBottom(26f)
                .setTitleLineSpacingExtra(25f)
                .setBtnLeft("我知道了", 16f, ContextCompat.getColor(this, R.color.color_365AF7), Typeface.NORMAL, object : BaseDialog.BaseDialogBuilder.BtnClickListener {
                    override fun onClick(dialog: BaseDialog) {
                        goOrderList()
                    }

                })
                .create()
                .show()
    }

    /**
     * 描  述：设置密码
     * 修改人：田羽衡
     * 版本号：<v1.0，2019/10/30 10:59>
     * @param null
     * 返回值：
     * 修改时间：2019/10/30 11:05
     * 弃⽤： false
     */
    fun showSettingPassWord() {
        BaseDialog(this)
                .builder()
                .setCancelable(false)
                .setTitlePaddingTop(22f)
                .setTitlePaddingBottom(23f)
                .setTitleLineSpacingExtra(25f)
                .setTitle("为了保障您的账户安全\n" +
                        "请设置支付密码")
                .setBtnLeft("取消")
                .setBtnRight("立即设置", object : BaseDialog.BaseDialogBuilder.BtnClickListener {
                    override fun onClick(dialog: BaseDialog) {
                        val intent = Intent(this@PaymentTypeActivity, SetTradePwdActivity::class.java)
                        intent.putExtra("payPswType", "0")
                        startActivity(intent)
                    }
                })
                .create()
                .show()
    }

    /**
     * Description 关闭自身去订单列表页
     * Author  田羽衡
     * Version  <v1.0，2019/11/5 14:42>
     * params
     * return
     * LastEditTime  2019/11/5 14:42
     * Deprecated   false
     */
    fun goOrderList() {
        AppManager.getAppManager().killActivity(ShopAllOrderActivity::class.java)
        AppManager.getAppManager().killActivity(ShopOrderDetailedActivity::class.java)
        val intent = Intent(this@PaymentTypeActivity, ShopAllOrderActivity::class.java)
        intent.putExtra("intentType", "PaymentTypeActivity")
        intent.putExtra("orderType", "99")
        startActivity(intent)
        finish()
    }

    //    ——————————————————————————支付回调判断（结束）——————————————————


    /**
     * 描  述：返回弹窗
     * 修改人：田羽衡
     * 版本号：<v1.0，2019/10/30 10:59>
     * @param null
     * 返回值：
     * 修改时间：2019/10/30 11:05
     * 弃⽤： false
     */
    fun showQuitDailog() {
        if (intentType.equals("ShopConfirmationOrderActivity")) {
            BaseDialog(this)
                    .builder()
                    .setCancelable(false)
                    .setTitle("确认要放弃付款？\n订单将为您保留，请尽快支付")
                    .setTitlePaddingBottom(23f)
                    .setBtnLeft("确认离开", object : BaseDialog.BaseDialogBuilder.BtnClickListener {
                        override fun onClick(dialog: BaseDialog) {
                            var intent = Intent(this@PaymentTypeActivity, ShopPayResultActivity::class.java)
                            intent.putExtra("type", "fail")
                            intent.putExtra("rmbTotal", rmbTotal)
                            intent.putExtra("o_id", id)
                            startActivity(intent)
                            finish()
                        }
                    })
                    .setBtnRight("继续支付")
                    .create()
                    .show()
        } else {
            finish()
        }
    }

    /**
     * 描  述：点击支付按钮时候的判断
     * 修改人：田羽衡
     * 版本号：<v1.0，2019/10/30 10:59>
     * @param null
     * 返回值：
     * 修改时间：2019/10/30 11:05
     * 弃⽤： false
     */

    private fun judgePay() {
        if (code.equals("0")) {
            if (null == pwd_status || !pwd_status.equals("1")) {
                //   密码没设置去设置密码
                showSettingPassWord()
            } else {
                inputPassword()
            }
        } else {
            startActivity(Intent(this, AuthenticationCenterActivity::class.java))
        }
    }


    /**
     * 描  述：//还款详情弹窗
     * 修改人：田羽衡
     * 版本号：<v1.0，2019/10/30 10:59>
     * @param null
     * 返回值：
     * 修改时间：2019/10/30 11:05
     * 弃⽤： false
     */
    fun lookLoanDetailed() {
        val view: View = LayoutInflater.from(this).inflate(R.layout.layout_loan_detailed_dialog_for_payment, null)
        var popupWindow = PopupWindow(view, (ArmsUtils.getScreenWidth(this) * 0.73).toInt(), (ArmsUtils.getScreenHeidth(this) * 0.54).toInt())
        popupWindow!!.isFocusable = false
        popupWindow!!.setBackgroundDrawable(BitmapDrawable())
        qmuilayout = view!!.findViewById<QMUIFrameLayout>(R.id.qmuilayout)
        qmuilayout.radius = QMUIDisplayHelper.dp2px(this, 8)
        val adapter = DialogRepaymentDetailForPaymentAdapter(getDialogList())
        val rvDialog = view.findViewById<RecyclerView>(R.id.rv_dialog_loan_detailed)
        rvDialog.layoutManager = LinearLayoutManager(this)
        rvDialog.adapter = adapter
        view.findViewById<ImageView>(R.id.iv_cancle_chose).setOnClickListener {
            popupWindow!!.dismiss()
            setBackgroundAlpha(1f)
        }
        setBackgroundAlpha(0.5f)
        popupWindow!!.showAtLocation(view, Gravity.CENTER, 0, 0);
    }

    /**
     * Description：设置popupwindow显示的背景颜色
     * Author：田羽衡
     * Version：<v1.0，2019/11/1 9:53>
     * params:
     * return：
     * LastEditTime：2019/11/1 9:53
     * Deprecated： false
     */
    private fun setBackgroundAlpha(bgAlpha: Float) {
        val lp = window.attributes
        lp.alpha = bgAlpha
        window.attributes = lp
    }

    /**
     * 描  述：还款详情弹窗数据格式化
     * 修改人：田羽衡
     * 版本号：<v1.0，2019/10/30 10:59>
     * @param null
     * 返回值：
     * 修改时间：2019/10/30 11:05
     * 弃⽤： false
     */
    public fun getDialogList(): ArrayList<MultiItemEntity> {
        var list = arrayListOf<MultiItemEntity>()
        bean?.refund_detail?.forEach { index ->
            var lv0 = GetOrderPayPopuwindowOut(index.stage, index.repayFund, index.budgetInfo)
            lv0.budgetInfo.forEach { it ->
                lv0.addSubItem(it)
            }
            list.add(lv0)
        }
        return list;
    }


    /**
     * 描  述：//输入密码弹窗
     * 修改人：田羽衡
     * 版本号：<v1.0，2019/10/30 10:59>
     * @param null
     * 返回值：
     * 修改时间：2019/10/30 11:05
     * 弃⽤： false
     */
    fun inputPassword() {
        PasswordInputPopupWindow(this).setOnInputCompleteListener(object : PasswordInputPopupWindow.OnInputCompleteListener {
            override fun complete(password: String) {
                if (!TextUtils.isEmpty(password)) {
                    mPresenter?.postPay(id, rmbTotal, cycle, password, config_id)
                }
            }
        }).show()
    }

    /**
     * 描  述：支付返回通用弹窗
     * 修改人：田羽衡
     * 版本号：<v1.0，2019/10/30 10:59>
     * @param null
     * 返回值：
     * 修改时间：2019/10/30 11:05
     * 弃⽤： false
     */
    override fun showResultDialog(txt: String) {

        BaseDialog(this)
                .builder()
                .setCancelable(false)
                .setTitle(txt, 16f)
                .setTitlePaddingTop(22f)
                .setTitlePaddingBottom(26f)
                .setTitleLineSpacingExtra(25f)
                .setBtnLeft("我知道了", 16f, ContextCompat.getColor(this, R.color.color_365AF7), Typeface.NORMAL, object : BaseDialog.BaseDialogBuilder.BtnClickListener {
                    override fun onClick(dialog: BaseDialog) {
                        val intent = Intent(this@PaymentTypeActivity, ShopAllOrderActivity::class.java)
                        intent.putExtra("orderType", "99")
                        startActivity(intent)
                    }

                })
                .create()
                .show()
    }

}
