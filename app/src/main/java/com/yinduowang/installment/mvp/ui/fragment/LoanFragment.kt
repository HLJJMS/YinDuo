package com.yinduowang.installment.mvp.ui.fragment

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.jakewharton.rxbinding3.view.clicks
import com.jess.arms.base.BaseFragment
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils
import com.qmuiteam.qmui.layout.QMUIFrameLayout
import com.qmuiteam.qmui.util.QMUIDisplayHelper
import com.scwang.smartrefresh.layout.constant.RefreshState
import com.yinduowang.installment.R
import com.yinduowang.installment.app.utils.LoadingUtils
import com.yinduowang.installment.app.utils.ToastUtils
import com.yinduowang.installment.app.widget.loadmore.FloatLoadMoreView
import com.yinduowang.installment.di.component.DaggerLoanComponent
import com.yinduowang.installment.di.module.LoanModule
import com.yinduowang.installment.mvp.contract.LoanContract
import com.yinduowang.installment.mvp.model.entity.BaseResponse
import com.yinduowang.installment.mvp.model.entity.LoanBannerEntity
import com.yinduowang.installment.mvp.model.entity.LoanBottomEntity
import com.yinduowang.installment.mvp.model.entity.LoanBottomEntity.Companion.TYPE_BOTTOM_BANNER
import com.yinduowang.installment.mvp.model.entity.LoanBottomEntity.Companion.TYPE_BOTTOM_LIST
import com.yinduowang.installment.mvp.model.entity.NewLoanEntity
import com.yinduowang.installment.mvp.presenter.LoanPresenter
import com.yinduowang.installment.mvp.ui.activity.AuthenticationCenterActivity
import com.yinduowang.installment.mvp.ui.activity.CommWebViewActivity
import com.yinduowang.installment.mvp.ui.activity.LendConfirmLoanActivity
import com.yinduowang.installment.mvp.ui.activity.LoginActivity
import com.yinduowang.installment.mvp.ui.adapter.DialogChoseTypeAdapter
import com.yinduowang.installment.mvp.ui.adapter.LoanBottomAdapter
import com.yinduowang.installment.mvp.ui.service.UploadService
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.fragment_loan.*
import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.ParseException
import java.util.concurrent.TimeUnit

/**
 * @Description:主页-借款页
 * @Author:张利超
 * @Date: 2019-10-23 10:11:28
 * @Version: v1.0, 2019-10-23
 * @LastEditors:张利超
 * @LastEditTime:2019-10-23 10:11:28
 * @Deprecated: false
 */
class LoanFragment : BaseFragment<LoanPresenter>(), LoanContract.View {

    //分页页数
    private var page: Int = 1
    var loanBean: NewLoanEntity? = null
    //借款目的
    var loanType: String = ""
    //借款id
    var loanId: String = ""
    lateinit var tvLoanUse: TextView
    lateinit var tvApply: TextView
    lateinit var tvLoanMoney: TextView
    lateinit var tvLoanDate: TextView
    //法大大签约字段相关
    lateinit var ivSignHead: ImageView
    lateinit var tvSignTop: TextView
    lateinit var tvSignBottom: TextView
    lateinit var tvGoToSign: TextView
    lateinit var qimuiSignLayout: ConstraintLayout
    //选择金额bar
    lateinit var selectMoneyBar: SeekBar
    var loanMoney: String = ""
    //金额千分符格式化
    val df1 = DecimalFormat("####.00")
    var fundList = listOf<Int>()
    override fun finishRefresh() {
        loanSmartRefrsh.finishRefresh()
    }

    lateinit var adapter: LoanBottomAdapter

    companion object {
        fun newInstance(): LoanFragment {
            val fragment = LoanFragment()
            return fragment
        }
    }


    override fun setupFragmentComponent(appComponent: AppComponent) {
        DaggerLoanComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .loanModule(LoanModule(this))
                .build()
                .inject(this)
    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_loan, container, false);
    }

    /**
     * 描  述：初始化界面控件等
     * 修改人：张利超
     * 版本号：<v1.0，2019/10/30 13:32>
     * @param null
     * 返回值：
     * 修改时间：2019/10/30 13:32
     * 弃⽤： false
     */
    @SuppressLint("CheckResult")
    override fun initData(savedInstanceState: Bundle?) {
        recyclerView.layoutManager = LinearLayoutManager(activity)
        //ac_type 1商城首页 2借款首页
        adapter = LoanBottomAdapter(ArrayList(), "2")
        var headerView = LayoutInflater.from(activity).inflate(R.layout.layout_header_loan, null)
        headerView.findViewById<QMUIFrameLayout>(R.id.qmuiFrameLayout).radius = ArmsUtils.dip2px(mContext, 5f)
        headerView.findViewById<QMUIFrameLayout>(R.id.qmuiFrameLayout).shadowElevation = ArmsUtils.dip2px(mContext, 5f)
        qimuiSignLayout = headerView.findViewById<ConstraintLayout>(R.id.cons_fadada)
        qimuiSignLayout.clicks().subscribe {
            mPresenter?.let { it.getSignContract(loanBean?.loanSigningResponse?.loanId.toString()) }
        }
        ivSignHead = headerView.findViewById<ImageView>(R.id.iv_sign_head)
        tvSignTop = headerView.findViewById<TextView>(R.id.tv_sign_top)
        tvSignBottom = headerView.findViewById<TextView>(R.id.tv_sign_bottom)
        tvGoToSign = headerView.findViewById<TextView>(R.id.tv_go_to_sign)
        tvLoanDate = headerView.findViewById<TextView>(R.id.tv_loan_date)
        tvLoanUse = headerView.findViewById<TextView>(R.id.tv_Loan_Use)
        tvLoanMoney = headerView.findViewById<TextView>(R.id.tv_pay_back_money)
        selectMoneyBar = headerView.findViewById<SeekBar>(R.id.select_money_bar)
        tvApply = headerView.findViewById<TextView>(R.id.tvApply)
        adapter.setHeaderAndEmpty(true)
        adapter.setHeaderView(headerView)
        adapter.setLoadMoreView(FloatLoadMoreView())
        adapter.setOnLoadMoreListener({
            //初始化加载更多
            mPresenter?.getBottomList(page.toString())
        }, recyclerView)
        recyclerView.adapter = adapter
//        adapterShop.isHeaderViewAsFlow
        // 下拉刷新
        loanSmartRefrsh.setOnRefreshListener {
            page = 1
            // 网络请求
            mPresenter?.let { it.getLoanInfo() }
        }
        //选择类型窗口
        tvLoanUse.clicks().throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(Consumer {
            closeLoanType()
        })

        //立即申请按钮
        tvApply.clicks().throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(Consumer {
            UploadService.uploadUserInfo(activity, UploadService.UPLOAD_TYPE_GPS)
            if (TextUtils.equals(tvLoanUse.text.trim().toString(), "请选择")) {
                ToastUtils.makeText(activity, "请选择借款用途")
                return@Consumer
            }
            mPresenter?.let { it.confirmApply(loanMoney) }
        })
        initSeekBar()
        loanSmartRefrsh.autoRefresh()
    }

    private fun initSeekBar() {
        selectMoneyBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (loanBean == null || loanBean?.loanFundList.isNullOrEmpty()) {
                    tvLoanMoney.text = "0.00"
                    return
                }
                val size = loanBean!!.loanFundList!!.size - 1
                val progressIndex = progress * size / 100
                loanMoney = if (progressIndex > size) {
                    loanBean!!.loanFundList!![size]
                } else {
                    loanBean!!.loanFundList!![progressIndex]
                }
                tvLoanMoney.text = num2thousand00(loanMoney)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                //设置距离
//                    selectMoneyBar.setProgress(100)
//
            }

        })
    }

    /**
     * 描  述：底部数据根据类型不同展示不同数据 1：返回的是 banner  ， 0：返回的是引流列表公用一个头部
     * 修改人：张利超
     * 版本号：<v1.0，2019/10/30 13:35>
     * @param null
     * 返回值：
     * 修改时间：2019/10/30 13:35
     * 弃⽤： false
     */
    override fun returnBottomList(loanBannerEntity: LoanBannerEntity) {
        //1：返回的是 banner  ， 0：返回的是引流
        if ("1" == loanBannerEntity.is_banner) {
            if (loanBannerEntity.data_list != null) {
                val list = ArrayList<LoanBottomEntity>()
                //遍历返回的列表进行重传数据
                for (index in loanBannerEntity.data_list) {
                    var loanBottom = LoanBottomEntity()
                    loanBottom.data = index
                    loanBottom.itemType = TYPE_BOTTOM_BANNER
                    list.add(loanBottom)
                }
                adapter.setNewData(list)
                adapter.loadMoreEnd(true)
            }
        } else {
            loanBannerEntity.type?.let {
                adapter?.type = it
            }
            if (loanBannerEntity.data_list != null) {
                val list = ArrayList<LoanBottomEntity>()
                //遍历返回的列表进行重传数据
                for (index in loanBannerEntity.data_list) {
                    val loanBottom = LoanBottomEntity()
                    loanBottom.data = index
                    loanBottom.itemType = TYPE_BOTTOM_LIST
                    list.add(loanBottom)
                }
                if (page == 1) {//分页判断
                    adapter.setNewData(list)
                } else {
                    if (loanBannerEntity.data_list.size > 0) {
                        adapter.addData(list)
                        adapter.loadMoreComplete()
                    } else {
                        adapter.loadMoreComplete()
                        adapter.loadMoreEnd()
                    }
                }
                page++
            }
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
        val intent = Intent(mContext, CommWebViewActivity::class.java)
        intent.putExtra(CommWebViewActivity.KEY_URL_NAME, response.data.toString())
        intent.putExtra(CommWebViewActivity.KEY_URL_ADD_TYPE, false)
        intent.putExtra(CommWebViewActivity.KEY_JAVASCRIPT_TYPE, CommWebViewActivity.TYPE_JAVASCRIPT_JEL_LE_ME)
        intent.putExtra(CommWebViewActivity.KEY_IS_AUTHENTICATION, CommWebViewActivity.TYPE_TITLE_NORMAL)
        //判断是从首页进入的签约界面的
        intent.putExtra("isFromLoanPage", "0")
        mContext.startActivity(intent)
    }
    /**
     * 描  述：这个是返回上方头的借款数据进行赋值
     * 修改人：张利超
     * 版本号：<v1.0，2019/10/30 13:38>
     * @param null
     * 返回值：
     * 修改时间：2019/10/30 13:38
     * 弃⽤： false
     */
    @SuppressLint("SetTextI18n")
    override fun returnLoanInfo(loanEntity: NewLoanEntity) {
        loanBean = loanEntity
        if (!loanEntity.loanFundList.isNullOrEmpty() && loanEntity.loanFundList.size > 1) {
            selectMoneyBar.isEnabled = true
            tvLoanMoney.text = num2thousand00(loanEntity.loanFundList.last().toString())
            selectMoneyBar.progress = 100
            loanMoney = loanEntity.loanFundList.last()
        } else if (!loanEntity.loanFundList.isNullOrEmpty() && loanEntity.loanFundList.size == 1) {
            selectMoneyBar.isEnabled = false
            if (loanEntity.leastLoanFund.equals("0")) {
                selectMoneyBar.progress = 0
            } else {
                selectMoneyBar.progress = 100
            }
            tvLoanMoney.text = num2thousand00(loanEntity.leastLoanFund)
            loanMoney = loanEntity.loanFundList.first()
        } else if (loanEntity.loanFundList.isNullOrEmpty()) {
            selectMoneyBar.isEnabled = false
            selectMoneyBar.progress = 100
            tvLoanMoney.text = num2thousand00(loanEntity.leastLoanFund)
            loanMoney = "0"
        }
        tvLoanDate.text = "${loanEntity.lonaDays}${loanEntity.cycleType}"
        if ("0".equals(loanEntity.isLogin)) {
            when {
                loanEntity.notBorrow == 0 -> {//不可再借,0可借,1不可借,2表示多少天可在借
                    tvApply.text = "立即申请"
                    tvApply.isEnabled = true
                }
                loanEntity.notBorrow == 1 -> {
                    tvApply.text = "暂不可借"
                    tvApply.isEnabled = false
                }
                loanEntity.notBorrow == 2 -> {
                    tvApply.text = "${loanEntity.noBorrowDays}天后可再借"
                    tvApply.isEnabled = false
                }
            }
        }
        //底部签约状态显示逻辑
        //内容栏状态 0不显示 1显示
        if ("1".equals(loanEntity?.loanSigningResponse?.type)) {
            qimuiSignLayout.visibility = View.VISIBLE
            tvGoToSign.text = loanEntity?.loanSigningResponse?.buttonText
            tvSignTop.text = loanEntity?.loanSigningResponse?.textOne
            tvSignBottom.text = loanEntity?.loanSigningResponse?.textTwo
            //按钮状态 0不可点击 1可点击 2隐藏"
            if ("0".equals(loanEntity?.loanSigningResponse?.buttonType)) {
                tvGoToSign.setTextColor(ContextCompat.getColor(context!!, R.color.color_ADADAD))
                tvGoToSign.setBackgroundResource(R.drawable.bg_loan_sign_unable)
                ivSignHead.setImageResource(R.mipmap.ic_sign_reviewing)
                tvGoToSign.visibility = View.VISIBLE
                qimuiSignLayout.isClickable = false
            } else if ("1".equals(loanEntity?.loanSigningResponse?.buttonType)) {
                tvGoToSign.setTextColor(ContextCompat.getColor(context!!, R.color.colorAccent))
                tvGoToSign.setBackgroundResource(R.drawable.bg_loan_sign_able)
                ivSignHead.setImageResource(R.mipmap.ic_sign_reviewing)
                tvGoToSign.visibility = View.VISIBLE
                qimuiSignLayout.isClickable = true
            } else {
                tvGoToSign.visibility = View.GONE
                ivSignHead.setImageResource(R.mipmap.ic_sign_review_complete)
                qimuiSignLayout.isClickable = false
            }
        } else {
            qimuiSignLayout.visibility = View.GONE
        }
        //在这里调用PHP接口来展示底部banner或者引流列表
        mPresenter?.getBottomList(page.toString())
    }


    /**
     * 描  述：点击立即申请按钮返回相应状态信息
     * 修改人：张利超
     * 版本号：<v1.0，2019/10/30 13:40>
     * @param null
     * 返回值：
     * 修改时间：2019/10/30 13:40
     * 弃⽤： false
     */
    override fun returnConfirmApply(baseResponse: BaseResponse<Any>) {
        when {
            baseResponse.code == "200" -> {
                val intent = Intent(activity, LendConfirmLoanActivity::class.java)
                intent.putExtra("loanBean", loanBean)
                intent.putExtra("loanMoney", loanMoney)
                intent.putExtra("loanType", loanType)
                intent.putExtra("loanId", loanId)
                startActivity(intent)
            }
            baseResponse.code == "U00010" -> //该用户未获取额度  (去认证中心)
                startActivity(Intent(activity, AuthenticationCenterActivity::class.java))
            baseResponse.code == "100007" -> //该用户未登录,请跳转至登录页面
                startActivity(Intent(activity, LoginActivity::class.java))
            baseResponse.code == "l00022" -> {// 用户进入借钱页时，可用额度不足3000，数据库修改用户可用额度大于3000，点击借钱页立即申请，返回提示并刷新页面
                ToastUtils.makeText(activity, baseResponse.msg)
                loanSmartRefrsh.autoRefresh()
            }

            else -> {
                ToastUtils.makeText(activity, baseResponse.msg)
            }
        }
    }

    override fun setData(data: Any?) {

    }


    /**
     * 描  述：打开选择类型窗口函数
     * 修改人：张利超
     * 版本号：<v1.0，2019/10/30 13:41>
     * @param null
     * 返回值：
     * 修改时间：2019/10/30 13:41
     * 弃⽤： false
     */
    fun closeLoanType() {
        loanBean?.let {
            val view: View = LayoutInflater.from(activity).inflate(R.layout.layout_dialog_layout_chose_type, null)
            val dialog: AlertDialog = AlertDialog.Builder(activity).create()
            dialog.setCancelable(true)
            dialog.setCanceledOnTouchOutside(true);
            dialog.setView(view)
            val qmuilayout = view.findViewById<QMUIFrameLayout>(R.id.qmuilayout)
            // 调整dialog背景大小
            qmuilayout.radius = QMUIDisplayHelper.dp2px(activity, 8)
            val adapter = DialogChoseTypeAdapter(it.loanPurposes)
            val rvDialog = view.findViewById<RecyclerView>(R.id.rv_dialog)
            val window = dialog.window
            window?.setBackgroundDrawableResource(android.R.color.transparent)
            rvDialog.layoutManager = LinearLayoutManager(activity)
            rvDialog.adapter = adapter
            adapter.setOnItemClickListener(BaseQuickAdapter.OnItemClickListener { adapter, view, position ->
                loanType = "" + it.loanPurposes!![position].purpose
                loanId = "" + it.loanPurposes[position].id
                tvLoanUse.setText(it.loanPurposes[position].purpose)
                tvLoanUse.setTextColor(ContextCompat.getColor(mContext, R.color.color_333333))
                dialog.dismiss()
            })
            dialog.show()
            val attr = window?.attributes
            attr?.height = (ArmsUtils.getScreenHeidth(activity) * 0.54).toInt()
            attr?.width = (ArmsUtils.getScreenWidth(activity) * 0.73).toInt()
            window?.attributes = attr
        }
    }

    override fun showLoading() {
        LoadingUtils.show(activity)
    }

    override fun hideLoading() {
        LoadingUtils.dismiss(activity)
    }

    override fun showMessage(message: String) {
        ArmsUtils.snackbarText(message)
    }

    override fun launchActivity(intent: Intent) {
        ArmsUtils.startActivity(intent)
    }

    override fun killMyself() {

    }

    /**
     * 描  述：从其他页面进入到此页面需要刷新页面
     * 修改人：张利超
     * 版本号：<v1.0，2019/10/30 13:41>
     * @param null
     * 返回值：
     * 修改时间：2019/10/30 13:41
     * 弃⽤： false
     */
    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (isResumed) {
            if (isVisible) {
                // 在最前端显示
                mPresenter?.let { it.getLoanInfo() }
            } else {
                // 不在最前端显示
                if (loanSmartRefrsh.state != RefreshState.None) {
                    loanSmartRefrsh.finishRefresh()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (isVisible) {
            // 在最前端显示
            mPresenter?.let { it.getLoanInfo() }
        }
    }

    override fun onPause() {
        super.onPause()
        if (loanSmartRefrsh.state != RefreshState.None) {
            loanSmartRefrsh.finishRefresh()
        }
    }

    /**
     * 描  述：数字金额转千分符函数
     * 修改人：张利超
     * 版本号：<v1.0，2019/10/30 13:42>
     * @param null
     * 返回值：
     * 修改时间：2019/10/30 13:42
     * 弃⽤： false
     */
    fun num2thousand00(num: String?): String {
        var numString = ""
        if (num == null) {
            return numString
        }
        var nf = NumberFormat.getInstance();
        try {
            var df = DecimalFormat("#,##0.00");
            numString = df.format(nf.parse(num));
        } catch (e: ParseException) {
            e.printStackTrace();
        }
        return numString
    }


}
