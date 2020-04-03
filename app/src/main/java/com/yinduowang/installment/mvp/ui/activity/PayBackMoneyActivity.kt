package com.yinduowang.installment.mvp.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.PopupWindow
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.jakewharton.rxbinding3.view.clicks
import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils
import com.qmuiteam.qmui.layout.QMUIFrameLayout
import com.yinduowang.installment.R
import com.yinduowang.installment.app.utils.LoadingUtils
import com.yinduowang.installment.app.utils.ToastUtils
import com.yinduowang.installment.app.widget.BaseDialog
import com.yinduowang.installment.di.component.DaggerPayBackMoneyComponent
import com.yinduowang.installment.di.module.PayBackMoneyModule
import com.yinduowang.installment.mvp.contract.PayBackMoneyContract
import com.yinduowang.installment.mvp.model.entity.BillDetailList
import com.yinduowang.installment.mvp.model.entity.PayBackMoney
import com.yinduowang.installment.mvp.model.entity.UserBank
import com.yinduowang.installment.mvp.presenter.PayBackMoneyPresenter
import com.yinduowang.installment.mvp.ui.adapter.DialogChoseBankCardAdapter
import com.yinduowang.installment.mvp.ui.adapter.PayBackAdapter
import kotlinx.android.synthetic.main.activity_pay_back_money.*
import java.util.concurrent.TimeUnit

/**
 * Description：我的-账单-还款金额
 * Author：田羽衡
 * Version：<v1.0，2019/11/1 >
 * LastEditTime：2019/11/1
 * LastEditors:
 * Deprecated： false
 */
class PayBackMoneyActivity : BaseActivity<PayBackMoneyPresenter>(), PayBackMoneyContract.View {

    lateinit var adapterMain: PayBackAdapter
    private var year: String = ""
    private var month: String = ""
    private var loanSumId: String = ""
    private var type: String = ""
    private var bankID: String = ""
    private var newBankID: String = ""
    private var popupWindow: PopupWindow? = null
    var adapter: DialogChoseBankCardAdapter? = null
    var rvDialog: RecyclerView? = null
    var view: View? = null
    var loanId: String = ""
    var payBackMoney: PayBackMoney? = null
    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerPayBackMoneyComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .payBackMoneyModule(PayBackMoneyModule(this))
                .build()
                .inject(this)
    }


    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_pay_back_money //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }


    @SuppressLint("CheckResult")
    override fun initData(savedInstanceState: Bundle?) {
        titleBar.showTitleAndBack(R.string.pay_back_money)

        // type 传值类型 在之前页面传递过来 区分1商城账单提前还款,2商城账单到期还款,3分期详情全部还款,4现金账单提前还款,5现金账单到期还款,6现金借款详情还款
        intent.getStringExtra("type")?.let { type = it }
        intent.getStringExtra("year")?.let { year = it }
        intent.getStringExtra("month")?.let { month = it }
        intent.getStringExtra("loanSumId")?.let { loanSumId = it }
        intent.getStringExtra("loanId")?.let { loanId = it }
        constChoseBank.clicks()
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe {
                    mPresenter?.getBankCardList(newBankID)
                }
        tvConfirmApply.clicks()
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe {
                    if (tvBankStyle.text.equals("请添加银行卡")) {
                        ToastUtils.makeText(this, "请添加银行卡")
                    } else {
                        showAlertView()
                    }
                }
        rec.layoutManager = LinearLayoutManager(this)
    }

    override fun onResume() {
        super.onResume()
        mPresenter?.getPayBackDetailed(type, loanSumId, year, month, loanId)
    }

    /**
     * Description：还款警告弹窗
     * Author：田羽衡
     * Version：<v1.0，2019/11/1 10:10>
     * params:
     * return：
     * LastEditTime：2019/11/1 10:10
     * Deprecated： false
     */
    private fun showAlertView() {
        BaseDialog(this)
                .builder()
                .setCancelable(false)
                .setTitle("您确认还款？")
                .setBtnLeft("否")
                .setBtnRight("是", object : BaseDialog.BaseDialogBuilder.BtnClickListener {
                    override fun onClick(dialog: BaseDialog) {
                        payBackMoney?.let { mPresenter!!.prPayBack(it, loanId,loanSumId) }
                    }
                })
                .create()
                .show()
    }

    /**
     * Description：获取还款金额数据成功
     * Author：田羽衡
     * Version：<v1.0，2019/11/1 10:10>
     * params:
     * return：
     * LastEditTime：2019/11/1 10:10
     * Deprecated： false
     */
    override fun returnPayBackDetailed(payBackMoney: PayBackMoney) {
        this.payBackMoney = payBackMoney
        bankID = payBackMoney.bankId
        tvTotalCounts.text = payBackMoney.repaymentMoney
        adapterMain = PayBackAdapter(getItemList(payBackMoney.billDetailList))
        rec.adapter = adapterMain
        //根据是否绑定银行卡展示数据
        payBackMoney.isBindBank?.let {
            if ("1" == it) {
                tvBankStyle.text = "${payBackMoney.bankName}(${payBackMoney.bankNoLastFour})"
                tvBankStyle.setTextColor(ContextCompat.getColor(this, R.color.color_333333))
            } else {
                tvBankStyle.text = "请添加银行卡"
                tvBankStyle.setTextColor(ContextCompat.getColor(this, R.color.color_ADADAD))
            }
        }
    }




    /**
     * Description：//银行卡获取列表成功
     * Author：田羽衡
     * Version：<v1.0，2019/11/1 10:11>
     * params:
     * return：
     * LastEditTime：2019/11/1 10:11
     * Deprecated： false
     */
    override fun getBankCardListSuccess(userBanks: List<UserBank>) {
        choseBankCard(userBanks)
    }



    /**
     * Description：选择银行卡
     * Author：田羽衡
     * Version：<v1.0，2019/11/1 10:11>
     * params:
     * return：
     * LastEditTime：2019/11/1 10:11
     * Deprecated： false
     */
    fun choseBankCard(list: List<UserBank>) {

        if (popupWindow == null) {
            view = LayoutInflater.from(this).inflate(R.layout.layout_dialog_chose_bank_card, null)
            popupWindow = PopupWindow(view,
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    (ArmsUtils.getScreenHeidth(this) * 0.53).toInt());
            popupWindow!!.setFocusable(false);
            popupWindow!!.setBackgroundDrawable(BitmapDrawable());
//        popupWindow.setAnimationStyle(R.style.Popupwindow);
            val qmuilayout = view!!.findViewById(R.id.qmuilayout) as QMUIFrameLayout
            val title = view!!.findViewById(R.id.textView36) as TextView
            title.text = "选择银行卡"
            qmuilayout.radius = 8
            adapter = DialogChoseBankCardAdapter(list)
            val bottomView: View = LayoutInflater.from(this).inflate(R.layout.layout_dialog_chose_bank_bottom, null)
            bottomView.setOnClickListener {
                popupWindow!!.dismiss()
                setBackgroundAlpha(1F)
                this@PayBackMoneyActivity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                startActivityForResult(Intent(this, AddBankCardActivity::class.java), LendConfirmLoanActivity.REQUEST_CODE_ADD_BANK)
            }
            val cancelChose = view!!.findViewById<ImageView>(R.id.iv_cancle_chose)
            cancelChose.setOnClickListener {
                popupWindow!!.dismiss()
                this@PayBackMoneyActivity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                setBackgroundAlpha(1F)
            }
            rvDialog = view!!.findViewById<RecyclerView>(R.id.rv_dialog_bank)
            rvDialog!!.layoutManager = LinearLayoutManager(this)

            adapter!!.addFooterView(bottomView)
            rvDialog!!.adapter = adapter
            adapter!!.setOnItemClickListener(BaseQuickAdapter.OnItemClickListener { adapter, view, position ->
                bankID = list[position].id
                newBankID = bankID
                tvBankStyle.text = list[position].bankName + "(${list[position].endCardNo})"
                tvBankStyle.setTextColor(ContextCompat.getColor(this, R.color.color_333333))
                setBackgroundAlpha(1F)
                popupWindow!!.dismiss()
                this@PayBackMoneyActivity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            })
            setBackgroundAlpha(0.5F)
            popupWindow!!.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        } else {
            adapter!!.setNewData(list)
            setBackgroundAlpha(0.5F)
            popupWindow!!.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        }
        this@PayBackMoneyActivity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    //添加银行卡成功后回调
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == LendConfirmLoanActivity.REQUEST_CODE_ADD_BANK && resultCode == LendConfirmLoanActivity.RESULT_CODE_ADD_BANK_SUCCESS) {
//            mPresenter?.let { it.getBankCardList() }
//        }
    }

    /**
     * Description：popupw设置
     * Author：田羽衡
     * Version：<v1.0，2019/11/1 10:11>
     * params:
     * return：
     * LastEditTime：2019/11/1 10:11
     * Deprecated： false
     */
    fun setBackgroundAlpha(bgAlpha: Float) {
        val lp = window.attributes
        lp.alpha = bgAlpha
        window.attributes = lp
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

    //    支付结果返回  处理状态 1、处理中 2、失败 3、成功
    override fun payEnd(code: String) {
        var intent = Intent(this, PayBackMoneyResultActivity::class.java)
        intent.putExtra("code", code);
        intent.putExtra("goMy", true)
        intent.putExtra("RMB",payBackMoney?.repaymentMoney)
        startActivity(intent)


    }

    //    adapter数据重构
    fun getItemList(billDetailList: List<BillDetailList>): ArrayList<MultiItemEntity> {
        var adapterList = arrayListOf<MultiItemEntity>()
        billDetailList?.forEach { index ->
            var lv0 = BillDetailList(index.name, index.value, index.nameContantsSon)
            lv0.nameContantsSon?.forEach { it ->
                lv0.addSubItem(it)
            }
            adapterList.add(lv0)
        }
        return adapterList;
    }
}
