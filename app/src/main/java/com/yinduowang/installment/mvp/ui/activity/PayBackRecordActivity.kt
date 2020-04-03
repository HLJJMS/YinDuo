package com.yinduowang.installment.mvp.ui.activity

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.SPUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.google.android.material.appbar.AppBarLayout
import com.jakewharton.rxbinding3.view.clicks
import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils
import com.scwang.smartrefresh.layout.constant.RefreshState
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import com.yinduowang.installment.R
import com.yinduowang.installment.app.constant.SPConstant
import com.yinduowang.installment.app.utils.LoadingUtils
import com.yinduowang.installment.app.utils.PhoneStatusUtils
import com.yinduowang.installment.app.widget.AppBarStateChangeListener
import com.yinduowang.installment.app.widget.loadmore.CustomLoadMoreView
import com.yinduowang.installment.di.component.DaggerPayBackRecordComponent
import com.yinduowang.installment.di.module.PayBackRecordModule
import com.yinduowang.installment.mvp.contract.PayBackRecordContract
import com.yinduowang.installment.mvp.model.entity.PayBackRecordEntity
import com.yinduowang.installment.mvp.presenter.PayBackRecordPresenter
import com.yinduowang.installment.mvp.ui.adapter.PayBackAllAdapter
import com.yinduowang.installment.mvp.ui.adapter.PayBackCashAdapter
import com.yinduowang.installment.mvp.ui.adapter.PayBackStageAdapter
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.activity_loan_recoder.*
import kotlinx.android.synthetic.main.activity_pay_back_record.*
import kotlinx.android.synthetic.main.activity_pay_back_record.appBarLayout
import kotlinx.android.synthetic.main.activity_pay_back_record.recyclerViewOne
import kotlinx.android.synthetic.main.activity_pay_back_record.recyclerViewTwo
import kotlinx.android.synthetic.main.activity_pay_back_record.smartRefreshLayout
import kotlinx.android.synthetic.main.activity_pay_back_record.titleBar
import kotlinx.android.synthetic.main.activity_pay_back_record.tvCashLoan

/**
 * ================================================
 * Description:我的-还款记录
 * <p>
 * Created by MVPArmsTemplate on 08/01/2019 10:04
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */

class PayBackRecordActivity : BaseActivity<PayBackRecordPresenter>(), PayBackRecordContract.View {
    lateinit var payBackAllAdapter: PayBackAllAdapter
    lateinit var payBackStageAdapter: PayBackStageAdapter
    lateinit var payBackCashAdapter: PayBackCashAdapter
    var titleStyle: String = "0"
    var c: Int = 0
    var h: Int = 0
    var pageId: String = "0"
    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerPayBackRecordComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .payBackRecordModule(PayBackRecordModule(this))
                .build()
                .inject(this)
    }


    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_pay_back_record //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }


    override fun initData(savedInstanceState: Bundle?) {
        initView()
    }

    private fun initView() {
        if (SPUtils.getInstance().getBoolean(SPConstant.IS_TEST_VERSION)) {
            llAllPayBack.visibility = View.GONE
            llPayBackCash.visibility = View.GONE
            vZeroPostion.visibility = View.GONE
            vOnePostion.visibility = View.VISIBLE
            tvPaySatge.setTextColor(ContextCompat.getColor(this, R.color.color_333333))
            tvPaySatge.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
        }
        titleBar.showTitleAndBack("")
        appBarLayout.addOnOffsetChangedListener(object : AppBarStateChangeListener() {
            override fun onStateChanged(appBarLayout: AppBarLayout, state: State) {
                if (state == State.EXPANDED) {
                    titleBar.setTitle("")
                } else if (state == State.COLLAPSED) {
                    titleBar.setTitle("还款记录")
                    //折叠状态
                }
            }


        })
        initPayBackAllAdapter()
        initPayBackCashAdapter()
        initPayBackStageAdapter()
        smartRefreshLayout.setOnRefreshListener(OnRefreshListener {
            //两个页面公用一个刷新
            pageId = "0"
            mPresenter?.let { it.getPayBackRecordList(pageId, titleStyle) }

        })

        smartRefreshLayout.setHeaderInsetStart(65f)
        recyclerViewZero.layoutManager = LinearLayoutManager(this)
        recyclerViewOne.layoutManager = LinearLayoutManager(this)
        recyclerViewTwo.layoutManager = LinearLayoutManager(this)
        smartRefreshLayout.autoRefresh()

        //判断每个点击时间分别对字提颜色及状态做判断
        llAllPayBack.clicks().subscribe(Consumer {
            if (smartRefreshLayout.state != RefreshState.None) return@Consumer
            titleStyle = "0"
            tvAllPayBack.setTextColor(ContextCompat.getColor(this, R.color.color_333333))
            vZeroPostion.setVisibility(View.VISIBLE)
            vOnePostion.setVisibility(View.INVISIBLE)
            vTwoPostion.setVisibility(View.INVISIBLE)
            tvCashLoan.setTextColor(ContextCompat.getColor(this, R.color.color_9A9A9A))
            tvPaySatge.setTextColor(ContextCompat.getColor(this, R.color.color_9A9A9A))
            tvAllPayBack.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
            tvCashLoan.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL))
            tvPaySatge.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL))
            recyclerViewZero.visibility = View.VISIBLE
            recyclerViewOne.visibility = View.GONE
            recyclerViewTwo.visibility = View.GONE
            smartRefreshLayout.autoRefresh()
        })

        //上方点击切换到现金借贷
        llPayBackCash.clicks().subscribe(Consumer {
            if (smartRefreshLayout.state != RefreshState.None) return@Consumer
            titleStyle = "1"
            tvCashLoan.setTextColor(ContextCompat.getColor(this, R.color.color_333333))
            tvPaySatge.setTextColor(ContextCompat.getColor(this, R.color.color_9A9A9A))
            tvAllPayBack.setTextColor(ContextCompat.getColor(this, R.color.color_9A9A9A))
            vZeroPostion.setVisibility(View.INVISIBLE)
            vOnePostion.setVisibility(View.INVISIBLE)
            vTwoPostion.setVisibility(View.VISIBLE)
            tvCashLoan.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
            tvPaySatge.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL))
            tvAllPayBack.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL))
            recyclerViewZero.visibility = View.GONE
            recyclerViewOne.visibility = View.GONE
            recyclerViewTwo.visibility = View.VISIBLE
            smartRefreshLayout.autoRefresh()
        })
//判断每个点击时间分别对字提颜色及状态做判断
        llPayBackStage.clicks().subscribe(Consumer {
            if (smartRefreshLayout.state != RefreshState.None) return@Consumer
            titleStyle = "2"
            tvPaySatge.setTextColor(ContextCompat.getColor(this, R.color.color_333333))
            vZeroPostion.setVisibility(View.INVISIBLE)
            vOnePostion.setVisibility(View.VISIBLE)
            vTwoPostion.setVisibility(View.INVISIBLE)
            tvCashLoan.setTextColor(ContextCompat.getColor(this, R.color.color_9A9A9A))
            tvAllPayBack.setTextColor(ContextCompat.getColor(this, R.color.color_9A9A9A))
            tvPaySatge.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
            tvCashLoan.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL))
            tvAllPayBack.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL))
            recyclerViewZero.visibility = View.GONE
            recyclerViewOne.visibility = View.VISIBLE
            recyclerViewTwo.visibility = View.GONE
            smartRefreshLayout.autoRefresh()
        })
    }

    //记录返回值
    override fun returnPayBackRecord(payBackRecordEntity: PayBackRecordEntity) {
        if (titleStyle.equals("0")) {
            //分期记录返回  分页处理
            if (pageId.equals("0")) {
                payBackAllAdapter.setNewData(payBackRecordEntity.paymentRecordlist)
            } else {
                payBackAllAdapter.addData(payBackRecordEntity.paymentRecordlist)
                if (payBackRecordEntity.isExistsNextPage == 0) {
                    payBackAllAdapter.loadMoreEnd()
                } else {
                    payBackAllAdapter.loadMoreComplete()

                }
            }
        } else if (titleStyle.equals("2")) {
            //现金返回分页
            if (pageId.equals("0")) {
                payBackStageAdapter.setNewData(payBackRecordEntity.paymentRecordlist)
            } else {
                payBackStageAdapter.addData(payBackRecordEntity.paymentRecordlist)
                if (payBackRecordEntity.isExistsNextPage == 0) {
                    payBackStageAdapter.loadMoreEnd()
                } else {
                    payBackStageAdapter.loadMoreComplete()

                }
            }

        } else if (titleStyle.equals("1")) {
            //现金返回分页
            if (pageId.equals("0")) {
                payBackCashAdapter.setNewData(payBackRecordEntity.paymentRecordlist)
            } else {
                payBackCashAdapter.addData(payBackRecordEntity.paymentRecordlist)
                if (payBackRecordEntity.isExistsNextPage == 0) {
                    payBackCashAdapter.loadMoreEnd()
                } else {
                    payBackCashAdapter.loadMoreComplete()

                }
            }
        }
        if (payBackRecordEntity.paymentRecordlist.size > 0) {
            pageId = payBackRecordEntity.paymentRecordlist.last().recordId.toString()
        }
    }

    override fun finishRefresh() {
        smartRefreshLayout.finishRefresh()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            c = PhoneStatusUtils.getLocation(tvAllPayBack)[2] / 2 + PhoneStatusUtils.getLocation(tvAllPayBack)[0]
            h = PhoneStatusUtils.getLocation(tvPaySatge)[2] / 2 + PhoneStatusUtils.getLocation(tvPaySatge)[0]
        }
    }

    //初始化全部还款记录adapter
    private fun initPayBackAllAdapter() {
        payBackAllAdapter = PayBackAllAdapter(arrayListOf())
        payBackAllAdapter.setOnLoadMoreListener(BaseQuickAdapter.RequestLoadMoreListener {
            mPresenter?.let { it.getPayBackRecordList(pageId, titleStyle) }
        }, recyclerViewZero)
        payBackAllAdapter.setLoadMoreView(CustomLoadMoreView())
        initEmptyView("暂无还款记录", payBackAllAdapter)
        recyclerViewZero.adapter = payBackAllAdapter
    }

    //初始化商城还款adapter
    private fun initPayBackCashAdapter() {
        payBackStageAdapter = PayBackStageAdapter(arrayListOf())
        payBackStageAdapter.setOnLoadMoreListener(BaseQuickAdapter.RequestLoadMoreListener {
            mPresenter?.let { it.getPayBackRecordList(pageId, titleStyle) }
        }, recyclerViewOne)
        payBackStageAdapter.setLoadMoreView(CustomLoadMoreView())
        initEmptyView("暂无还款记录", payBackStageAdapter)
        recyclerViewOne.adapter = payBackStageAdapter
    }

    //初始化现金还款adapter
    private fun initPayBackStageAdapter() {
        payBackCashAdapter = PayBackCashAdapter(arrayListOf())
        payBackCashAdapter.setOnLoadMoreListener(BaseQuickAdapter.RequestLoadMoreListener {
            mPresenter?.let { it.getPayBackRecordList(pageId, titleStyle) }
        }, recyclerViewTwo)
        payBackCashAdapter.setLoadMoreView(CustomLoadMoreView())
        initEmptyView("暂无还款记录", payBackCashAdapter)
        recyclerViewTwo.adapter = payBackCashAdapter
    }

    private fun initEmptyView(msg: String, adapter: BaseQuickAdapter<*, *>) {
        val empty = LayoutInflater.from(this).inflate(R.layout.layout_empty_pay_back_recoder, null)
        var emptyMsg = empty.findViewById<TextView>(R.id.tv_msg)
        emptyMsg.setText(msg)
        adapter.setEmptyView(empty)
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



