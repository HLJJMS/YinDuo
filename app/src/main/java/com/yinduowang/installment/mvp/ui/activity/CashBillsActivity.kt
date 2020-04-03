package com.yinduowang.installment.mvp.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import com.jakewharton.rxbinding3.view.clicks
import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils
import com.yinduowang.installment.R
import com.yinduowang.installment.app.utils.LoadingUtils
import com.yinduowang.installment.app.utils.PhoneStatusUtils
import com.yinduowang.installment.di.component.DaggerCashBillsComponent
import com.yinduowang.installment.di.module.CashBillsModule
import com.yinduowang.installment.mvp.contract.CashBillsContract
import com.yinduowang.installment.mvp.model.entity.CashBillsEntity
import com.yinduowang.installment.mvp.presenter.CashBillsPresenter
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.activity_cash_bills.*
import java.util.concurrent.TimeUnit


/**
 * @Description:我的-现金订单
 * @Author: 张利超
 * @Date: 2019-10-31 10:26:52
 * @Version: v1.0, 2019-10-31
 * @LastEditors:张利超
 * @LastEditTime: 2019-10-31 10:26:52
 * @Deprecated: true
 */
class CashBillsActivity : BaseActivity<CashBillsPresenter>(), CashBillsContract.View {
    private var loanId: String = "0"
    private var repaymentState:Int=0
    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerCashBillsComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .cashBillsModule(CashBillsModule(this))
                .build()
                .inject(this)
    }


    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_cash_bills //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }


    override fun initData(savedInstanceState: Bundle?) {
        val params = viewTop.getLayoutParams();
        params.width = LinearLayout.LayoutParams.MATCH_PARENT;
        params.height = PhoneStatusUtils.getStatusBarHeight(this)
        viewTop.layoutParams = params
        titleBar.showTitle("现金账单")
        titleBar.setTitleTextColorResources(R.color.white)
        titleBar.showWhiteBack()
        titleBar.setBottomLineShow(false)
        titleBar.setBackgroundAlpha(0)
        tvBillsMoney.clicks().throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(Consumer {
            var type:String=""
           if (repaymentState==0) {
               type="4"
           }else{
               type="5"
           }
            val intent = Intent(this, CashLoanDetailedActivity::class.java)
            intent.putExtra("loanId", loanId)
            intent.putExtra("type", type)
            startActivity(intent)
        })
        mPresenter?.let { it.getCashBills() }
    }

    override fun returnCashBills(cashBillsEntity: CashBillsEntity) {
        loanId = cashBillsEntity.id
        repaymentState=cashBillsEntity.repaymentState
        tvTitleDate.text = cashBillsEntity.cashBillMonth
        tvHadTurnBack.text = cashBillsEntity.repaidAmount
        tvBillsMoney.text = cashBillsEntity.billAmount
        tvDueMoney.text = cashBillsEntity.demurrage
        tvReturnDateHint.text = cashBillsEntity.repaymentDate
        tvHadReturnBackMoney.text = cashBillsEntity.stayRepaymentAmount

        //repaymentState (integer, optional): 还款状态 0、未到期 1、已到期 2、已逾期 3、已结清 4、无账单
        if (cashBillsEntity.repaymentState == 0) {
            tvSubmitPayReturn.text = "提前还款"
        } else if (cashBillsEntity.repaymentState == 1) {
            tvSubmitPayReturn.text = "立即还款"
        } else if (cashBillsEntity.repaymentState == 2) {
            tvSubmitPayReturn.text = "立即还款"
            ivHadDueDate.visibility = View.VISIBLE
            groupDueMoeny.visibility = View.VISIBLE
        } else if (cashBillsEntity.repaymentState == 3) {
            groupReturnBack.visibility = View.GONE
            groupNoBills.visibility = View.VISIBLE
            ivEmptyBill.setImageResource(R.mipmap.ic_return_back_bills)
            tvNoBills.text = "账单已还清"
        } else if (cashBillsEntity.repaymentState == 4) {
            groupReturnBack.visibility = View.GONE
            groupNoBills.visibility = View.VISIBLE
            ivEmptyBill.setImageResource(R.mipmap.ic_none_bills)
            tvNoBills.text = "暂无账单"
            tvNoBillsHint.text = "本月您暂无账单哦~"
            tvBillsMoney.isClickable = false
            ivBillArrow.visibility = View.GONE
            tvHadTurnBack.text = "0.00"
            tvBillsMoney.text = "0.00"
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
}
