package com.yinduowang.installment.mvp.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import com.jakewharton.rxbinding3.view.clicks
import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils
import com.qmuiteam.qmui.util.QMUIDisplayHelper
import com.yinduowang.installment.R
import com.yinduowang.installment.app.utils.LoadingUtils
import com.yinduowang.installment.app.utils.PhoneStatusUtils
import com.yinduowang.installment.di.component.DaggerNewCashBillsComponent
import com.yinduowang.installment.di.module.NewCashBillsModule
import com.yinduowang.installment.mvp.contract.NewCashBillsContract
import com.yinduowang.installment.mvp.model.entity.NewCashBillsEntity
import com.yinduowang.installment.mvp.presenter.NewCashBillsPresenter
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.activity_shop_bills.*
import java.util.concurrent.TimeUnit

/**
 * @Description:我的-现金账单
 * @Author: 张利超
 * @Date: 2019-10-31 10:26:52
 * @Version: v1.0, 2019-10-31
 * @LastEditors:张利超
 * @LastEditTime: 2019-10-31 10:26:52
 * @Deprecated: false
 */
class NewCashBillsActivity : BaseActivity<NewCashBillsPresenter>(), NewCashBillsContract.View {


    //年，月 ，上一个月，下一个月
    private var year: String = ""
    private var month: String = ""
    private var loanSumId: String = ""
    private var flag: String = "0"
    // 是否到还款日,0是,1不是
    private var isPay: String = ""
    //立即还款 为true 提前还款为false
    var prepayment: Boolean = false
    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerNewCashBillsComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .newCashBillsModule(NewCashBillsModule(this))
                .build()
                .inject(this)
    }


    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_new_cash_bills //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    /**
     * 描  述：初始化控件及标题
     * 修改人：张利超
     * 版本号：<v1.0，2019/10/30 17:06>
     * @param null
     * 返回值：
     * 修改时间：2019/10/30 17:06
     * 弃⽤： false
     */
    @SuppressLint("ResourceAsColor")
    override fun initData(savedInstanceState: Bundle?) {
        //标题初始化
        titleBar.showTitle("现金账单")
        titleBar.setTitleTextColor(R.color.white)
        titleBar.showBigIconWhiteBack(View.OnClickListener {
            goBackHome()
        })
        titleBar.setTitleTextColorResources(R.color.white)
        titleBar.setBottomLineShow(false)
        titleBar.setBackgroundAlpha(0)
        //从上页面获取年月 如果没有默认为空
        if (intent.getStringExtra("year") != null) {
            year = intent.getStringExtra("year")
        }
        if (intent.getStringExtra("month") != null) {
            month = intent.getStringExtra("month")
        }
        val params = viewTop.getLayoutParams();
        params.width = LinearLayout.LayoutParams.MATCH_PARENT;
        params.height = PhoneStatusUtils.getStatusBarHeight(this)
        viewTop.layoutParams = params
        //全部订订背景阴影效果
        qmuiAllBills.shadowElevation = QMUIDisplayHelper.dp2px(this, 8)
//        getBillsInfo()
        //上一月按钮
        tvLastMonth.clicks().throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(Consumer {
            flag = "-1"
            getBillsInfo()
        })
        //下一页按钮
        tvNextMonth.clicks().throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(Consumer {
            flag = "+1"
            getBillsInfo()
        })
        //全部账单整个栏目点击按钮
        qmuiAllBills.clicks().throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(Consumer {
            val intent = Intent(this, NewCashAllBillsActivity::class.java)
            intent.putExtra("year", year)
            startActivity(intent)
        })
        //还款校验按钮
        tvSubmitPayReturn.clicks().throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(Consumer {
            //立即还款 为true 提前还款为false
            if (prepayment) {
                mPresenter?.checkRepay("1", loanSumId)
            } else {
                mPresenter?.checkRepay("2", loanSumId)
            }
        })
        tvBillsMoney.clicks().throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(Consumer {
            val intent = Intent(this, NewCashBillListActivity::class.java)
            intent.putExtra("id", loanSumId)
            intent.putExtra("year", year)
            intent.putExtra("month", month)
            startActivity(intent)
        })
    }


    /** 返回账单信息
     * 描  述：
     * 修改人：张利超
     * 版本号：<v1.0，2019/10/30 17:10>
     * 返回值：
     * 修改时间：2019/10/30 17:10
     * 弃⽤： false
     */
    override fun returnCashBillsInfo(cashBillsEntity: NewCashBillsEntity) {
        year = cashBillsEntity.year
        month = cashBillsEntity.month
        loanSumId = cashBillsEntity.loanSumId
        isPay = cashBillsEntity.isPay
        // isEarly (string, optional): 当前账单是否为最早未结清账单,0不是最早1是最早 ,
        tvTitleDate.text = "${cashBillsEntity.year}年${cashBillsEntity.month}月"
        tvBillsMoney.text = cashBillsEntity.billMoney
        tvHadTurnBack.text = cashBillsEntity.alreadyPay
        tvDueMoney.text = cashBillsEntity.overdueFund
        //判断是否能点击进入账单详细
        if ("1" == cashBillsEntity.isHave) {
            tvBillsMoney.isClickable = true
            ivBillArrow.visibility = View.VISIBLE
        } else {
            tvBillsMoney.isClickable = false
            ivBillArrow.visibility = View.GONE
        }
        //isLast (string, optional): 是否是最早月份账单,0是1不是 ,
        if ("0" == cashBillsEntity.isLast) {
            groupLastMonth.visibility = View.GONE
        } else {
            groupLastMonth.visibility = View.VISIBLE
        }
        //isNext (string, optional): 是否是最晚月份账单,0是1不是 ,
        if ("0" == cashBillsEntity.isNext) {
            groupNextMonth.visibility = View.GONE
        } else {
            groupNextMonth.visibility = View.VISIBLE
        }
        //isOverdue (string, optional): 是否逾期,0未逾,1逾期 ,
        if ("1" == cashBillsEntity.isOverdue) {
            ivHadDueDate.visibility = View.VISIBLE
            groupDueMoeny.visibility = View.VISIBLE
        } else {
            groupDueMoeny.visibility = View.GONE
            ivHadDueDate.visibility = View.GONE

        }
        //isHave (string, optional): 当月是否有账单,0无1有 ,
        if ("1" == cashBillsEntity.isHave) {
            //isClean (string, optional): 当前查看账单是否结清,0还款中1已结清 ,
            if ("1" == cashBillsEntity.isClean) {
                ivHadDueDate.visibility = View.GONE
                groupReturnBack.visibility = View.GONE
                groupNoBills.visibility = View.VISIBLE
                ivEmptyBill.setImageResource(R.mipmap.ic_return_back_bills)
                tvNoBills.text = "账单已还清"
                tvNoBillsHint.text = "还款日"
                tvNoBillsHintEnd.text = cashBillsEntity.month + "月" + cashBillsEntity.repaymentDay + "日"
            } else {
                groupReturnBack.visibility = View.VISIBLE
                groupNoBills.visibility = View.GONE
                tvHadReturnBackMoney.text = cashBillsEntity.residuePay
                tvReturnDateHint.text = cashBillsEntity.month + "月" + cashBillsEntity.repaymentDay + "日"
                //isPay (string, optional): 是否到还款日,0是,1不是 ,
                if ("0" == cashBillsEntity.isPay) {
                    tvSubmitPayReturn.text = "立即还款"
                    prepayment = false
                } else {
                    tvSubmitPayReturn.text = "提前还款"
                    prepayment = true
                }
                if ("0" == cashBillsEntity.isEarly) {
                    tvSubmitPayReturn.isEnabled = false
                } else {
                    tvSubmitPayReturn.isEnabled = true
                }
            }
        } else {
            groupReturnBack.visibility = View.GONE
            groupNoBills.visibility = View.VISIBLE
            ivEmptyBill.setImageResource(com.yinduowang.installment.R.mipmap.ic_none_bills)
            tvNoBills.text = "暂无账单"
            tvNoBillsHint.text = "本月您暂无账单哦~"
            tvNoBillsHintEnd.text = ""
            tvHadTurnBack.text = "0.00"
            tvBillsMoney.text = "0.00"
        }


    }

    /**
     * 描  述：校验按钮回调成功跳转还款金额页面
     * 修改人：张利超
     * 版本号：<v1.0，2019/10/30 17:12>
     * @param null
     * 返回值：
     * 修改时间：2019/10/30 17:12
     * 弃⽤： false
     */
    override fun canRepay() {
        var type: String = ""
        //type 1商城账单提前还款,2商城账单到期还款
        if (isPay == "0") type = "2" else type = "1"
        val intent = Intent(this, PayBackMoneyActivity::class.java)
        intent.putExtra("year", year)
        intent.putExtra("month", month)
        intent.putExtra("loanSumId", loanSumId)
        intent.putExtra("type", type)
        startActivity(intent)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            goBackHome()
            return true
        } else {
            return super.onKeyDown(keyCode, event)
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return super.onTouchEvent(event)
    }

    fun getBillsInfo() {
        mPresenter?.let { it.getCashBills(year, month, flag) }
    }

//       描述：〈返回页面刷新〉
//      修改⼈：〈田羽衡〉
//       版本号：<1.0，20191017>
//       参数：〈参数名：null〉
//      返回值： null
//      修改时间：20191017
//      弃⽤： <false>

    override fun onResume() {
        super.onResume()
        mPresenter?.let { it.getCashBills(year, month, "0") }
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
     * 描  述：返回至我的framgent
     * 修改人：张利超
     * 版本号：<v1.0，2019/10/30 17:12>
     * @param null
     * 返回值：
     * 修改时间：2019/10/30 17:12
     * 弃⽤： false
     */
    override fun goBackHome() {
        val intent = Intent(application, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        application.startActivity(intent)
    }
}
