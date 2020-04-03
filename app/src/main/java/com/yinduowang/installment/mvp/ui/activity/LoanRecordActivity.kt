package com.yinduowang.installment.mvp.ui.activity

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
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
import com.yinduowang.installment.di.component.DaggerLoanRecoderComponent
import com.yinduowang.installment.di.module.LoanRecoderModule
import com.yinduowang.installment.mvp.contract.LoanRecoderContract
import com.yinduowang.installment.mvp.model.entity.LoanRecordEntity
import com.yinduowang.installment.mvp.model.entity.RecordInfoResponse
import com.yinduowang.installment.mvp.model.event.ShowMinePage
import com.yinduowang.installment.mvp.presenter.LoanRecodPresenter
import com.yinduowang.installment.mvp.ui.adapter.CashLoanAdapter
import com.yinduowang.installment.mvp.ui.adapter.ShopStageAdapter
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.activity_loan_recoder.*
import org.simple.eventbus.EventBus


/**
 * ================================================
 * Description:借款记录
 * <p>
 * Created by MVPArmsTemplate on 07/30/2019 20:22
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
class LoanRecordActivity : BaseActivity<LoanRecodPresenter>(), LoanRecoderContract.View {
    lateinit var shopStageAdapter: ShopStageAdapter
    lateinit var cashLoanAdapter: CashLoanAdapter
    //点击商城记录为true 现金为false
    private var isPreCasting = true
    private var c: Int = 0
    private var h: Int = 0
    //分页id初始化
    private var pageId: Int = 0
    private var backType = ""
    override fun finishRefresh() {
        smartRefreshLayout.finishRefresh()
    }

    var id: String = ""
    var recordEntity: List<RecordInfoResponse>? = null
    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerLoanRecoderComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .loanRecoderModule(LoanRecoderModule(this))
                .build()
                .inject(this)
    }


    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_loan_recoder //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }


    override fun initData(savedInstanceState: Bundle?) {
        initView()
    }

    /**
     * 描  述：初始化view控件等
     * 修改人：张利超
     * 版本号：<v1.0，2019/10/30 11:40>
     * @param null
     * 返回值：
     * 修改时间：2019/10/30 11:40
     * 弃⽤： false
     */
    private fun initView() {
        intent.extras?.getString("backType")?.let {
            backType = intent?.extras!!.getString("backType").toString()
        }
        if (SPUtils.getInstance().getBoolean(SPConstant.IS_TEST_VERSION)) {
            ll_history_appointment.visibility = View.GONE
        }
        //进入此页如果是借款页面进入我的
        if (backType.equals("1")) {
            EventBus.getDefault().post(ShowMinePage())
        }
        titleBar.showTitleAndBack("")
        //如果从借款首页签约结果页面进入借款记录则进入现金借款记录页面
        if (backType.equals("1")) {
            isPreCasting = false
            Log.e("点击cashloan按钮", isPreCasting.toString())
            tvShopStaging.setTextColor(ContextCompat.getColor(this, R.color.color_9A9A9A))
            vOneBottom.setVisibility(View.INVISIBLE)
            vTwoBottom.setVisibility(View.VISIBLE)
            tvCashLoan.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
            tvShopStaging.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL))
            tvCashLoan.setTextColor(ContextCompat.getColor(this, R.color.color_333333))
            recyclerViewOne.visibility = View.GONE
            recyclerViewTwo.visibility = View.VISIBLE
        }
//        qmuiLinearLayout.shadowElevation = ArmsUtils.dip2px(this as Context, 5f)
//        collapseToolbarLayout.elevation=ArmsUtils.dip2px(this as Context, 5f).toFloat()
        appBarLayout.addOnOffsetChangedListener(object : AppBarStateChangeListener() {
            override fun onStateChanged(appBarLayout: AppBarLayout?, state: State?) {
                if (state == State.EXPANDED) {
                    titleBar.setTitle("")
                } else if (state == State.COLLAPSED) {
                    titleBar.setTitle("借款记录")
                    //折叠状态
                }
            }

        })

        initShopStageAdapter()
        initCashLoanAdapter()
        smartRefreshLayout.setOnRefreshListener(OnRefreshListener {
            //两个页面公用一个刷新
            pageId = 0
            if (isPreCasting) {
                mPresenter?.let { it.getLoanRecordList(pageId, "2") }
            } else {
                mPresenter?.let { it.getLoanRecordList(pageId, "1") }
            }
        })

        smartRefreshLayout.setHeaderInsetStart(65f)
        recyclerViewOne.layoutManager = LinearLayoutManager(this)
        recyclerViewTwo.layoutManager = LinearLayoutManager(this)
        smartRefreshLayout.autoRefresh()
        //商城借款记录判断每个点击时间分别对字提颜色及状态做判断
        llShopStageRecord.clicks().subscribe(Consumer {
            if (smartRefreshLayout.state != RefreshState.None) return@Consumer
            isPreCasting = true
            Log.e("点击llshop按钮", isPreCasting.toString())
            tvShopStaging.setTextColor(ContextCompat.getColor(this, R.color.color_333333))
            vOneBottom.setVisibility(View.VISIBLE)
            vTwoBottom.setVisibility(View.INVISIBLE)
            tvCashLoan.setTextColor(ContextCompat.getColor(this, R.color.color_9A9A9A))
            tvShopStaging.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
            tvCashLoan.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL))
            recyclerViewOne.visibility = View.VISIBLE
            recyclerViewTwo.visibility = View.GONE
            smartRefreshLayout.autoRefresh()
        })
        //现金借款记录上方点击切换到现金借贷
        tvCashLoan.clicks().subscribe(Consumer {
            if (smartRefreshLayout.state != RefreshState.None) return@Consumer
            isPreCasting = false
            Log.e("点击cashloan按钮", isPreCasting.toString())
            tvShopStaging.setTextColor(ContextCompat.getColor(this, R.color.color_9A9A9A))
            vOneBottom.setVisibility(View.INVISIBLE)
            vTwoBottom.setVisibility(View.VISIBLE)
            tvCashLoan.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
            tvShopStaging.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL))
            tvCashLoan.setTextColor(ContextCompat.getColor(this, R.color.color_333333))
            recyclerViewOne.visibility = View.GONE
            recyclerViewTwo.visibility = View.VISIBLE
            smartRefreshLayout.autoRefresh()
        })

    }


    /**
     * 描  述： 记录返回值
     * 修改人：张利超
     * 版本号：<v1.0，2019/10/30 11:39>
     * @param loanRecordEntity
     * 返回值：
     * 修改时间：2019/10/30 11:39
     * 弃⽤： false
     */
    override fun returnLoanRecord(loanRecordEntity: LoanRecordEntity) {
        recordEntity = loanRecordEntity.recordInfoResponses
        if (isPreCasting) {
            //分期记录返回  分页处理
            if (pageId == 0) {
                Log.e("分期view", isPreCasting.toString())
                shopStageAdapter.setNewData(loanRecordEntity.recordInfoResponses)
            } else {
                shopStageAdapter.addData(loanRecordEntity.recordInfoResponses)
                if (loanRecordEntity.isExistsNextPage == 0) {
                    shopStageAdapter.loadMoreEnd()
                } else {
                    shopStageAdapter.loadMoreComplete()

                }
            }
        } else {
            //现金返回分页
            if (pageId == 0) {
                Log.e("现金view", isPreCasting.toString())
                cashLoanAdapter.setNewData(loanRecordEntity.recordInfoResponses)
            } else {
                cashLoanAdapter.addData(loanRecordEntity.recordInfoResponses)
                if (loanRecordEntity.isExistsNextPage == 0) {
                    cashLoanAdapter.loadMoreEnd()
                } else {
                    cashLoanAdapter.loadMoreComplete()

                }
            }

        }
        if (loanRecordEntity.recordInfoResponses.size > 0) {
            pageId = loanRecordEntity.recordInfoResponses.last().loanId
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            c = PhoneStatusUtils.getLocation(tvShopStaging)[2] / 2 + PhoneStatusUtils.getLocation(tvShopStaging)[0]
            h = PhoneStatusUtils.getLocation(tvCashLoan)[2] / 2 + PhoneStatusUtils.getLocation(tvCashLoan)[0]
        }
    }


    /**
     * 描  述：初始化商城借款adapter及加载更多页面
     * 修改人：张利超
     * 版本号：<v1.0，2019/10/30 11:42>
     * @param null
     * 返回值：
     * 修改时间：2019/10/30 11:42
     * 弃⽤： false
     */
    private fun initShopStageAdapter() {

        shopStageAdapter = ShopStageAdapter(arrayListOf())
        shopStageAdapter.setOnLoadMoreListener(BaseQuickAdapter.RequestLoadMoreListener {
            mPresenter?.let { it.getLoanRecordList(pageId, "2") }
        }, recyclerViewOne)
        shopStageAdapter.setLoadMoreView(CustomLoadMoreView())
        initEmptyView("暂无借款记录", shopStageAdapter)
        recyclerViewOne.adapter = shopStageAdapter
    }


    /**
     * 描  述：初始化现金借款adapter
     * 修改人：张利超
     * 版本号：<v1.0，2019/10/30 11:42>
     * @param null
     * 返回值：
     * 修改时间：2019/10/30 11:42
     * 弃⽤： false
     */
    private fun initCashLoanAdapter() {
        cashLoanAdapter = CashLoanAdapter(arrayListOf())
        cashLoanAdapter.setOnLoadMoreListener(BaseQuickAdapter.RequestLoadMoreListener {
            mPresenter?.let { it.getLoanRecordList(pageId, "1") }
        }, recyclerViewTwo)
        cashLoanAdapter.setLoadMoreView(CustomLoadMoreView())
        initEmptyView("暂无借款记录", cashLoanAdapter)
        recyclerViewTwo.adapter = cashLoanAdapter
    }

    /**
     * 描  述：初始化空view 两个adapter公用一个
     * 修改人：张利超
     * 版本号：<v1.0，2019/10/30 11:43>
     * @param null
     * 返回值：
     * 修改时间：2019/10/30 11:43
     * 弃⽤： false
     */
    private fun initEmptyView(msg: String, adapter: BaseQuickAdapter<*, *>) {
        val empty = LayoutInflater.from(this).inflate(R.layout.layout_empty_loan_recoder, null)
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

    /**
     * @description ：返回监听跳转到我的
     * @lastEditor  ：张利超
     * @version     ：v1.0 ,2019/11/27
     * @param null
     * @return      ：
     * @deprecated  ： false
     */
    override fun onBackPressed() {
        super.onBackPressed()
        if (backType.equals("1")) {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.putExtra("goMy", true)
            startActivity(intent)
            finish()
        } else {
            finish()
        }
    }
}
