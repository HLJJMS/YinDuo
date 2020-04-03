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
import com.yinduowang.installment.di.component.DaggerShopBillsComponent
import com.yinduowang.installment.di.module.ShopBillsModule
import com.yinduowang.installment.mvp.contract.ShopBillsContract
import com.yinduowang.installment.mvp.model.entity.ShopBillsEntity
import com.yinduowang.installment.mvp.presenter.ShopBillsPresenter
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.activity_shop_bills.*
import java.util.concurrent.TimeUnit


/**
 * ================================================
 * Description:我的-商城账单
 * <p>
 * Created by MVPArmsTemplate on 08/03/2019 09:19
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */

class ShopBillsActivity : BaseActivity<ShopBillsPresenter>(), ShopBillsContract.View {



    //年，月 ，上一个月，下一个月
    private var year: String = ""
    private var month: String = ""
    //借款id
    private var loanSumId: String = ""
    private var flag: String = "0"
    // ispay表示 是否到还款日,0是,1不是
    private var isPay: String = ""
    //prepayment true代表提前还款 flase 代表到期还款或逾期还款
    var prepayment: Boolean = false
    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerShopBillsComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .shopBillsModule(ShopBillsModule(this))
                .build()
                .inject(this)
    }


    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_shop_bills //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    /**
     * 描  述：初始化界面控件信息
     * 修改人：张利超
     * 版本号：<v1.0，2019/10/30 13:26>
     * @param null
     * 返回值：
     * 修改时间：2019/10/30 13:26
     * 弃⽤： false
     */
    @SuppressLint("ResourceAsColor")
    override fun initData(savedInstanceState: Bundle?) {
        titleBar.showTitle(R.string.shop_bill)
        titleBar.setTitleTextColor(R.color.white)
        titleBar.showBigIconWhiteBack(View.OnClickListener {
            goBackHome()
        })
        titleBar.setTitleTextColorResources(R.color.white)
        titleBar.setBottomLineShow(false)
        titleBar.setBackgroundAlpha(0)

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


        qmuiAllBills.shadowElevation = QMUIDisplayHelper.dp2px(this, 8)
//        getBillsInfo()
        tvLastMonth.clicks().throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(Consumer {
            flag = "-1"
            getBillsInfo()
        })
        tvNextMonth.clicks().throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(Consumer {
            flag = "+1"
            getBillsInfo()
        })
        //点击进入全部账单
        qmuiAllBills.clicks().throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(Consumer {
            val intent = Intent(this, ShopAllBillsActivity::class.java)
            intent.putExtra("year", year)
            startActivity(intent)
        })
        //提交按键
        tvSubmitPayReturn.clicks().throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(Consumer {
            if (prepayment) {
                mPresenter?.checkRepay("1", loanSumId)
            } else {
                mPresenter?.checkRepay("2", loanSumId)
            }
        })
        //账单金额
        tvBillsMoney.clicks().throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(Consumer {
            val intent = Intent(this, BillListActivity::class.java)
            intent.putExtra("id", loanSumId)
            intent.putExtra("year", year)
            intent.putExtra("month", month)
            startActivity(intent)
        })
    }


    /**
     * 描  述：每次账单请求的回调函数
     * 修改人：张利超
     * 版本号：<v1.0，2019/10/30 13:26>
     * @param null
     * 返回值：
     * 修改时间：2019/10/30 13:26
     * 弃⽤： false
     */
    override fun returnShopBillsInfo(shopBillsEntity: ShopBillsEntity) {
        year = shopBillsEntity.year
        month = shopBillsEntity.month
        loanSumId = shopBillsEntity.loanSumId
        isPay = shopBillsEntity.isPay
        // isEarly (string, optional): 当前账单是否为最早未结清账单,0不是最早1是最早 ,
        tvTitleDate.text = "${shopBillsEntity.year}年${shopBillsEntity.month}月"
        tvBillsMoney.text = shopBillsEntity.billMoney
        tvHadTurnBack.text = shopBillsEntity.alreadyPay
        tvDueMoney.text = shopBillsEntity.overdueFund
        //判断是否能点击进入账单详细
        if ("1" == shopBillsEntity.isHave) {
            tvBillsMoney.isClickable = true
            ivBillArrow.visibility = View.VISIBLE
        } else {
            tvBillsMoney.isClickable = false
            ivBillArrow.visibility = View.GONE
        }
        //isLast (string, optional): 是否是最早月份账单,0是1不是 ,
        if ("0" == shopBillsEntity.isLast) {
            groupLastMonth.visibility = View.GONE
        } else {
            groupLastMonth.visibility = View.VISIBLE
        }
        //isNext (string, optional): 是否是最晚月份账单,0是1不是 ,
        if ("0" == shopBillsEntity.isNext) {
            groupNextMonth.visibility = View.GONE
        } else {
            groupNextMonth.visibility = View.VISIBLE
        }
        //isOverdue (string, optional): 是否逾期,0未逾,1逾期 ,
        if ("1" == shopBillsEntity.isOverdue) {
            ivHadDueDate.visibility = View.VISIBLE
            groupDueMoeny.visibility = View.VISIBLE
        } else {
            groupDueMoeny.visibility = View.GONE
            ivHadDueDate.visibility = View.GONE

        }
        //isHave (string, optional): 当月是否有账单,0无1有 ,
        if ("1" == shopBillsEntity.isHave) {
            //isClean (string, optional): 当前查看账单是否结清,0还款中1已结清 ,
            if ("1" == shopBillsEntity.isClean) {
                ivHadDueDate.visibility = View.GONE
                groupReturnBack.visibility = View.GONE
                groupNoBills.visibility = View.VISIBLE
                ivEmptyBill.setImageResource(R.mipmap.ic_return_back_bills)
                tvNoBills.text = "账单已还清"
                tvNoBillsHint.text = "还款日"
                tvNoBillsHintEnd.text = shopBillsEntity.month + "月" + shopBillsEntity.repaymentDay + "日"
            } else {
                groupReturnBack.visibility = View.VISIBLE
                groupNoBills.visibility = View.GONE
                tvHadReturnBackMoney.text = shopBillsEntity.residuePay
                tvReturnDateHint.text = shopBillsEntity.month + "月" + shopBillsEntity.repaymentDay + "日"
                //isPay (string, optional): 是否到还款日,0是,1不是 ,
                if ("0" == shopBillsEntity.isPay) {
                    tvSubmitPayReturn.text = "立即还款"
                    prepayment = false
                } else {
                    tvSubmitPayReturn.text = "提前还款"
                    prepayment = true
                }
                if ("0" == shopBillsEntity.isEarly) {
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
     * 描  述：还款按钮校验接口回调判断是否可以点击还款到下一页
     * 修改人：张利超
     * 版本号：<v1.0，2019/10/30 13:27>
     * @param null
     * 返回值：
     * 修改时间：2019/10/30 13:27
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

    /**
     * 描  述：获取账单信息接口
     * 修改人：张利超
     * 版本号：<v1.0，2019/10/30 13:28>
     * @param null
     * 返回值：
     * 修改时间：2019/10/30 13:28
     * 弃⽤： false
     */
    fun getBillsInfo() {
        mPresenter?.let { it.getShopBills(year, month, flag) }
    }

    override fun onResume() {
        super.onResume()
        mPresenter?.let { it.getShopBills(year, month, "0") }
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
     * 描  述：根据界面返回按键或者物理返回按键，返回至我的页面
     * 修改人：张利超
     * 版本号：<v1.0，2019/10/30 13:29>
     * @param null
     * 返回值：
     * 修改时间：2019/10/30 13:29
     * 弃⽤： false
     */
    override fun goBackHome() {
        val intent = Intent(application, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        application.startActivity(intent)
    }
}
