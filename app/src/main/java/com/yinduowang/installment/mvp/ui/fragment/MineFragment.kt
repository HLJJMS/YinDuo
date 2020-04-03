package com.yinduowang.installment.mvp.ui.fragment

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.ScrollView
import androidx.constraintlayout.widget.ConstraintLayout
import com.blankj.utilcode.util.SPUtils
import com.jakewharton.rxbinding3.view.clicks
import com.jess.arms.base.BaseFragment
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils
import com.scwang.smartrefresh.layout.constant.RefreshState
import com.yinduowang.installment.R
import com.yinduowang.installment.app.constant.Api
import com.yinduowang.installment.app.constant.SPConstant
import com.yinduowang.installment.app.widget.BaseDialog
import com.yinduowang.installment.di.component.DaggerMineComponent
import com.yinduowang.installment.di.module.MineModule
import com.yinduowang.installment.mvp.contract.MineContract
import com.yinduowang.installment.mvp.model.entity.MineEntity
import com.yinduowang.installment.mvp.model.event.ShowHomeShopEvent
import com.yinduowang.installment.mvp.presenter.MinePresenter
import com.yinduowang.installment.mvp.ui.activity.*
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.fragment_mine.*
import org.simple.eventbus.Subscriber
import java.util.concurrent.TimeUnit
import kotlin.isInitialized as isInitialized1


/**
 * ================================================
 * Description:主页-我的
 * <p>
 * Created by MVPArmsTemplate on 07/19/2019 15:51
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
open class MineFragment : BaseFragment<MinePresenter>(), MineContract.View {

    lateinit var mineEntity: MineEntity

    companion object {
        fun newInstance(): MineFragment {
            val fragment = MineFragment()
            return fragment
        }
    }

    @Subscriber
    fun onMessageEvent(event: ShowHomeShopEvent) {
        qmuiObservableScrollView.fullScroll(ScrollView.FOCUS_UP)
    }

    override fun setupFragmentComponent(appComponent: AppComponent) {
        DaggerMineComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .mineModule(MineModule(this))
                .build()
                .inject(this)
    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_mine, container, false)
    }

    override fun initData(savedInstanceState: Bundle?) {
        initView()
    }

    @SuppressLint("CheckResult")
    private fun initView() {
        groupUnAuthentication.visibility = GONE
        // 下拉刷新
        smartRefreshLayout.setOnRefreshListener {
            // 网络请求
            mPresenter?.let {
                it.myInfo()
            }
        }
        qmuiObservableScrollView.addOnScrollChangedListener { scrollView, scrollX, scrollY, oldScrollX, oldScrollY ->
            val displayDistance = ArmsUtils.dip2px(mContext, 90f)
            if (scrollY >= displayDistance) { // >=Toolbar高度的2.5倍时全显背景图
                clTopTitleBar.visibility = View.VISIBLE
            } else { // 小于Toolbar高度时不设置背景图
                clTopTitleBar.visibility = View.GONE
            }
        }
        // 右上角消息
        ivMessage.clicks()
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe {
                    if (smartRefreshLayout.state == RefreshState.None)
                        startActivity(Intent(activity, MessageCenterActivity::class.java))
                }
        // 右上向上滑动后的消息点击
        icMessageBlack.clicks()
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe {
                    if (smartRefreshLayout.state == RefreshState.None)
                        startActivity(Intent(activity, MessageCenterActivity::class.java))
                }
        // 全部订单
        tvAllShopOrder.clicks()
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe {
                    if (smartRefreshLayout.state == RefreshState.None) {
                        val intent = Intent(activity, ShopAllOrderActivity::class.java)
                        intent.putExtra("orderType", "99")
                        startActivity(intent)
                    }
                }
        // 代付款
        llPaymentOnBehalf.clicks()
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe {
                    if (smartRefreshLayout.state == RefreshState.None) {
                        val intent = Intent(activity, ShopAllOrderActivity::class.java)
                        intent.putExtra("orderType", "0")
                        startActivity(intent)
                    }
                }
        // 代发货
        llToBeShipped.clicks()
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe {
                    if (smartRefreshLayout.state == RefreshState.None) {
                        val intent = Intent(activity, ShopAllOrderActivity::class.java)
                        intent.putExtra("orderType", "1")
                        startActivity(intent)
                    }
                }
        // 待收货
        llToBeReceived.clicks()
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe {
                    if (smartRefreshLayout.state == RefreshState.None) {
                        val intent = Intent(activity, ShopAllOrderActivity::class.java)
                        intent.putExtra("orderType", "2")
                        startActivity(intent)
                    }
                }
        // 退换 售后
        llReturnAfterSale.clicks()
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe {
                    if (smartRefreshLayout.state == RefreshState.None)
                        startActivity(Intent(activity, ShopAfterSaleActivity::class.java))
                }
        // 我的额度
        clMyQuota.clicks()
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe {
                    //需要判断（1）未获取额度时，点击整栏，跳转至【认证中心】页；（2）已获取额度时，点击整栏，跳转至【我的额度】页；
                    if (smartRefreshLayout.state == RefreshState.None)
                    // 根据是否认证 打开弹窗或者跳转页面
                        if (::mineEntity.isInitialized1) {
                            if (mineEntity.quotaState.equals("1")) {
                                startActivity(Intent(activity, MyQuotaActivity::class.java))
                            } else {
                                startActivity(Intent(activity, AuthenticationCenterActivity::class.java))
                            }
                        }

                }
        // 借款记录
        clBorrowingRecord.clicks()
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe {
                    if (smartRefreshLayout.state == RefreshState.None)
                        startActivity(Intent(activity, LoanRecordActivity::class.java))
                }
        // 还款记录
        clPaymentHistory.clicks()
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe {
                    if (smartRefreshLayout.state == RefreshState.None)
                        startActivity(Intent(activity, PayBackRecordActivity::class.java))
                }
        // 认证中心
        clAuthenticationCenter.clicks()
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe {
                    if (smartRefreshLayout.state == RefreshState.None)
                        startActivity(Intent(activity, AuthenticationCenterActivity::class.java))
                }
        // 银行卡
        clBankCard.clicks()
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe {
                    if (smartRefreshLayout.state == RefreshState.None) {
                        // 根据是否认证 打开弹窗或者跳转页面
                        if (::mineEntity.isInitialized1) {
                            if (mineEntity.bankStatus == "0") {
                                startActivity(Intent(activity, BankCardActivity::class.java))
                            } else {
                                BaseDialog(activity as Activity)
                                        .builder()
                                        .setCancelable(false)
                                        .setTitle(R.string.please_first_authentication)
                                        .setBtnLeft(R.string.cancel)
                                        .setBtnRight(R.string.go_authentication, object : BaseDialog.BaseDialogBuilder.BtnClickListener {
                                            override fun onClick(dialog: BaseDialog) {
                                                startActivity(Intent(activity, AuthenticationCenterActivity::class.java))
                                            }
                                        })
                                        .create()
                                        .show()
                            }
                        }
                    }
                }
        // 帮助中心
        clHelpCenter.clicks()
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe {
                    if (smartRefreshLayout.state == RefreshState.None) {
//                        OpenWebViewUtils.openWebViewAgreement(activity as Context, Api.HELP_CENTER, null)

                        var intent = Intent(activity, CommWebViewActivity::class.java)
                        intent.putExtra(CommWebViewActivity.KEY_URL_NAME, Api.HELP_CENTER)
                        intent.putExtra(CommWebViewActivity.KEY_JAVASCRIPT_TYPE, CommWebViewActivity.TYPE_JAVASCRIPT_JEL_LE_ME)
                        intent.putExtra(CommWebViewActivity.KEY_IS_AUTHENTICATION, CommWebViewActivity.TYPE_TITLE_NORMAL)
                        startActivity(intent)
                    }
                }
        // 设置
        clSetting.clicks()
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe {
                    if (smartRefreshLayout.state == RefreshState.None)
                        startActivity(Intent(activity, SettingsActivity::class.java))
                }
        //商城账单
        viewShopRepay.clicks().throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(Consumer {
            if (smartRefreshLayout.state == RefreshState.None)
                startActivity(Intent(activity, ShopBillsActivity::class.java))
        })
        //现金账单账单
        viewCashRepay.clicks().throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(Consumer {
            if (smartRefreshLayout.state == RefreshState.None)
//                startActivity(Intent(activity, CashBillsActivity::class.java))
                startActivity(Intent(activity, NewCashBillsActivity::class.java))
        })
        ivMyAdBg.clicks()
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe {
                    if (smartRefreshLayout.state == RefreshState.None)
                        startActivity(Intent(activity, AuthenticationCenterActivity::class.java))
                }
        ivVip.clicks()
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe {
                    if (smartRefreshLayout.state == RefreshState.None) {
                        val intent = Intent(activity, CommWebViewActivity::class.java)
                        intent.putExtra(CommWebViewActivity.KEY_URL_NAME, Api.VIP_DETAIL)
                        intent.putExtra(CommWebViewActivity.KEY_JAVASCRIPT_TYPE, CommWebViewActivity.TYPE_JAVASCRIPT_JEL_LE_ME)
                        intent.putExtra(CommWebViewActivity.KEY_IS_AUTHENTICATION, CommWebViewActivity.TYPE_TITLE_NORMAL)
                        startActivity(intent)
                    }
                }
        smartRefreshLayout.autoRefresh()

    }

    /**
     * 描  述：我的页面返回函数 里边进行数据赋值
     * 修改人：张利超
     * 版本号：<v1.0，2019/10/30 13:43>
     * @param null
     * 返回值：
     * 修改时间：2019/10/30 13:43
     * 弃⽤： false
     */
    override fun getMineSuccess(mineEntity: MineEntity) {
        this.mineEntity = mineEntity
        // 手机号显示隐藏
        tv_pay_back_money.text = mineEntity.userMobile
        tvScorllTitle.text = mineEntity.userMobile
        tvMyAdTop.text = mineEntity.textOne
        tvMyAdBottom.text = mineEntity.textTwo
        tvMyAdBt.text = mineEntity.textButton
        tvApprovalVip.text = mineEntity.vipText
        if (mineEntity.vipStatus.equals("0")) {
            ivVipLogin.setImageResource(R.mipmap.ic_vip)
            tvUserDesc.text = "尊敬的小银号VIP"
        } else {
            ivVipLogin.setImageResource(R.mipmap.ic_vip_gray)
            tvUserDesc.text = "尊敬的小银号用户"
        }
        // 未读消息红点显示隐藏
        if (mineEntity.unreadMessage == "0") {
            ivUnreadMessage.visibility = GONE
            ivUnreadMessageBlack.visibility = View.GONE
        } else {
            ivUnreadMessage.visibility = View.VISIBLE
            ivUnreadMessageBlack.visibility = View.VISIBLE
        }
        // 代付款红点显示隐藏
        if (mineEntity.orderStateOne == "0") {
            ivPaymentOnBehalf.setImageResource(R.mipmap.ic_to_be_pay)
        } else {
            ivPaymentOnBehalf.setImageResource(R.mipmap.ic_to_be_pay_red_dot)
        }
        // 待发货红点显示隐藏
        if (mineEntity.orderStateTwo == "0") {
            ivToBeShipped.setImageResource(R.mipmap.ic_to_be_ship)
        } else {
            ivToBeShipped.setImageResource(R.mipmap.ic_to_be_ship_red_dot)
        }
        // 待收货红点显示隐藏
        if (mineEntity.orderStateThree == "0") {
            ivToBeReceived.setImageResource(R.mipmap.ic_to_be_receipt)
        } else {
            ivToBeReceived.setImageResource(R.mipmap.ic_to_be_receipt_red_dot)
        }
        // 根据是否获取额度 显示页面内容(根据显示内容调整item布局位置)
        var clMyQuotaLp = clMyQuota?.getLayoutParams() as ConstraintLayout.LayoutParams;
        if (mineEntity.quotaState == "-1" || mineEntity.quotaState == "0") {
            // 未获取 获取中
            if (SPUtils.getInstance().getBoolean(SPConstant.IS_TEST_VERSION)) {
                groupUnAuthentication.visibility = GONE
            } else {
                groupUnAuthentication.visibility = View.VISIBLE
            }
            clMyDown.visibility = GONE
            clMyQuotaLp.topMargin = ArmsUtils.dip2px(mContext, 13f)
            clMyQuotaLp.topToBottom = R.id.ivMyAdBg
            clMyQuota.layoutParams = clMyQuotaLp

        } else if (mineEntity.quotaState == "1") {
            // 以获取
            groupUnAuthentication.visibility = GONE
            clMyDown.visibility = View.VISIBLE
            // 设置我账单 现今待还和商城待还 金额
            tvMallReturned.text = mineEntity.shopNeedRepay
            tvCashRepaid.text = mineEntity.cashNeedRepay
            clMyQuotaLp.topToBottom = R.id.clMyDown
            clMyQuotaLp.topMargin = ArmsUtils.dip2px(mContext, 1f)
            clMyQuota.layoutParams = clMyQuotaLp
        }

        // 根据是否认证显示认证状态
        if (mineEntity.perfectStatus == "0") {
            tvAuthenticationStatus.text = "已认证"
        } else {
            tvAuthenticationStatus.text = "未认证"
        }
    }

    override fun finishRefresh() {
        smartRefreshLayout.finishRefresh()
    }


    override fun setData(data: Any?) {

    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun showMessage(message: String) {
        ArmsUtils.snackbarText(message)
    }

    override fun launchActivity(intent: Intent) {
        ArmsUtils.startActivity(intent)
    }

    override fun killMyself() {

    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (isResumed) {
            if (isVisible) {
                // 在最前端显示
                mPresenter?.let {
                    it.myInfo()
                }
            } else {
                // 不在最前端显示
                if (smartRefreshLayout.state != RefreshState.None) {
                    smartRefreshLayout.finishRefresh()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (isVisible) {
            // 网络请求
            mPresenter?.let {
                it.myInfo()
            }


//            // 在最前端显示
//            if (smartRefreshLayout.state == RefreshState.None) {
//                smartRefreshLayout.autoRefresh()
//            }
        }
    }

    override fun onPause() {
        super.onPause()
        if (smartRefreshLayout.state != RefreshState.None) {
            smartRefreshLayout.finishRefresh()
        }
    }

}
