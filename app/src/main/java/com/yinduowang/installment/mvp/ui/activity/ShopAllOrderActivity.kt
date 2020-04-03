package com.yinduowang.installment.mvp.ui.activity

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.text.TextUtils
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.google.android.material.appbar.AppBarLayout
import com.jakewharton.rxbinding3.view.clicks
import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.integration.AppManager
import com.jess.arms.utils.ArmsUtils
import com.scwang.smartrefresh.layout.constant.RefreshState
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import com.yinduowang.installment.R
import com.yinduowang.installment.app.utils.LoadingUtils
import com.yinduowang.installment.app.utils.OpenWebViewUtils
import com.yinduowang.installment.app.widget.AppBarStateChangeListener
import com.yinduowang.installment.app.widget.BaseDialog
import com.yinduowang.installment.app.widget.SpacesItemDecoration
import com.yinduowang.installment.app.widget.loadmore.OtherLoadMoreView
import com.yinduowang.installment.di.component.DaggerShopAllOrderComponent
import com.yinduowang.installment.di.module.ShopAllOrderModule
import com.yinduowang.installment.mvp.contract.ShopAllOrderContract
import com.yinduowang.installment.mvp.model.entity.ShopOrder
import com.yinduowang.installment.mvp.model.event.ShopAllOrderEvent
import com.yinduowang.installment.mvp.presenter.ShopAllOrderPresenter
import com.yinduowang.installment.mvp.ui.adapter.ShopAllOrderAdapter
import com.yinduowang.installment.mvp.ui.adapter.ShopOrderToPayAdapter
import com.yinduowang.installment.mvp.ui.adapter.ShopOrderToSendAdapter
import com.yinduowang.installment.mvp.ui.adapter.ShopOrderToTakeBackAdapter
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.activity_all_shop_order.*
import org.simple.eventbus.Subscriber

/**
 * @Description:我的-全部订单
 * @Author: 张利超
 * @Date: 2019-10-31 10:26:52
 * @Version: v1.0, 2019-10-31
 * @LastEditors:张利超
 * @LastEditTime: 2019-10-31 10:26:52
 * @Deprecated: false
 */

class ShopAllOrderActivity : BaseActivity<ShopAllOrderPresenter>(), ShopAllOrderContract.View {

    // 商品详情标题 标题浮于web页面上方 web页面紧接状态栏
    val TYPE_TITLE_DETAIL = "detail"

    val KEY_URL_NAME = "url"
    // web页面跳转标题类型
    val KEY_IS_AUTHENTICATION = "isAuthentication"
    lateinit var shopAllOrderAdapter: ShopAllOrderAdapter
    lateinit var shopOrderToPayAdapter: ShopOrderToPayAdapter
    lateinit var shopOrderToSendAdapter: ShopOrderToSendAdapter
    lateinit var shopOrderToTakeBackAdapter: ShopOrderToTakeBackAdapter
    var pageId: Int = 1
    //默认全部订单
    var orderType: String = "99"
    var intentType = ""
    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerShopAllOrderComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .shopAllOrderModule(ShopAllOrderModule(this))
                .build()
                .inject(this)
    }

    /**
     * @description ：全部订单会单个item view刷新
     * @lastEditor  ：张利超
     * @version     ：v1.0 ,2019/11/7
     * @param null
     * @return      ：
     * @deprecated  ： false
     */
    override fun setOnDataItemChange(orderid: String) {
        for (i in 0..(shopAllOrderAdapter.data as List<ShopOrder>).size - 1) {
            if (orderid.equals((shopAllOrderAdapter.data as List<ShopOrder>).get(i).id)) {
                var shopOrder = (shopAllOrderAdapter.data as List<ShopOrder>).get(i)
                shopOrder.order_status = "-1"
                shopOrder.status_name = "已取消"
                shopAllOrderAdapter.setData(i, shopOrder)
                break
            }
        }


    }

    //状态回调成功刷新列表
    override fun setOnRefresh() {
        smartRefreshLayout.autoRefresh()
        when (orderType) {
            "99" -> {
                recyclerViewZero.scrollToPosition(0)
            }
            "0" -> {  //待付款
                recyclerViewOne.scrollToPosition(0)
            }
            "1" -> {  //代发货
                recyclerViewTwo.scrollToPosition(0)
            }
            "2" -> { //待收货
                recyclerViewThree.scrollToPosition(0)
            }
        }
    }

    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_all_shop_order //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    override fun initData(savedInstanceState: Bundle?) {
        initView()
    }

    /**
     * @description ：初始化view方法
     * @lastEditor  ：张利超
     * @version     ：v1.0 ,2019/10/31
     * @param null
     * @return      ：
     * @deprecated  ： false
     */
    private fun initView() {
        intent.getStringExtra("intentType")?.let { intentType = it }
        intent.getStringExtra("orderType")?.let { orderType = it }
        titleBar.showTitleAndBack("", View.OnClickListener {
            //            if (intentType.equals("PaymentTypeActivity") || intentType.equals("ShopPayResultActivity")) {
//                val intent = Intent(this, MainActivity::class.java)
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
//                intent.putExtra("goShop", "shop")
//                startActivity(intent)
//                finish()
//            } else {
//                finish()
//            }
            AppManager.getAppManager().killActivity(CommWebViewActivity::class.java)
            finish()
        })
        appBarLayout.addOnOffsetChangedListener(object : AppBarStateChangeListener() {
            override fun onStateChanged(appBarLayout: AppBarLayout?, state: State?) {
                if (state == State.EXPANDED) {
                    titleBar.setTitle("")
                } else if (state == State.COLLAPSED) {
                    titleBar.setTitle("我的订单")
                    //折叠状态
                }
            }

        })
        initShopAllOrderAdapter()
        initShopOrderToPayAdapter()
        initShopOrderToSendAdapter()
        initShopOrderToTakeBackAdapter()
        smartRefreshLayout.setOnRefreshListener(OnRefreshListener {
            //两个页面公用一个刷新
            pageId = 1
            mPresenter?.let { it.getPayBackRecordList(pageId.toString(), orderType) }

        })
        smartRefreshLayout.setHeaderInsetStart(65f)
        recyclerViewZero.layoutManager = LinearLayoutManager(this@ShopAllOrderActivity)
        recyclerViewZero.addItemDecoration(SpacesItemDecoration(ArmsUtils.dip2px(this, 10f)))
        recyclerViewOne.layoutManager = LinearLayoutManager(this)
        recyclerViewOne.addItemDecoration(SpacesItemDecoration(ArmsUtils.dip2px(this, 10f)))
        recyclerViewTwo.layoutManager = LinearLayoutManager(this)
        recyclerViewTwo.addItemDecoration(SpacesItemDecoration(ArmsUtils.dip2px(this, 10f)))
        recyclerViewThree.layoutManager = LinearLayoutManager(this)
        recyclerViewThree.addItemDecoration(SpacesItemDecoration(ArmsUtils.dip2px(this, 10f)))
        //判断从哪里点击进入此页
        when (orderType) {
            "99" -> {
                smartRefreshLayout.autoRefresh()
            }
            "0" -> {  //待付款
                orderClick("0", false, true, false, false)
            }
            "1" -> {  //代发货
                orderClick("1", false, false, true, false)
            }
            "2" -> { //待收货
                orderClick("2", false, false, false, true)
            }
        }
        //全部订单
        llAllOrder.clicks().subscribe(Consumer {
            //防止当前在此标题然后再次点击此标题进刷新
            if (orderType != "99") {
                orderClick("99", true, false, false, false)
                recyclerViewZero.scrollToPosition(0)
            }
        })
        //待付款
        llToPay.clicks().subscribe(Consumer {
            //防止当前在此标题然后再次点击此标题进刷新
            if (orderType != "0") {
                orderClick("0", false, true, false, false)
                recyclerViewOne.scrollToPosition(0)
            }
        })
        //代发货
        llToSend.clicks().subscribe(Consumer {
            //防止当前在此标题然后再次点击此标题进刷新
            if (orderType != "1") {
                orderClick("1", false, false, true, false)
                recyclerViewTwo.scrollToPosition(0)
            }
        })
        //待收货
        llToBack.clicks().subscribe(Consumer {
            //防止当前在此标题然后再次点击此标题进刷新
            if (orderType != "2") {
                orderClick("2", false, false, false, true)
                recyclerViewThree.scrollToPosition(0)
            }
        })
    }

    /**
     * @description ：点击切换不同订单方法
     * @lastEditor  ：张利超
     * @version     ：v1.0 ,2019/10/31
     * @param {
     * type =99默认全部订单
     * zeroClick 是否全部订单, oneClick: 代付款, twoClick: 代发货, threeClick: 待收货
     *       }
     * @return      ：
     * @deprecated  ： false
     */
    private fun orderClick(type: String, zeroClick: Boolean, oneClick: Boolean, twoClick: Boolean, threeClick: Boolean) {
        if (smartRefreshLayout.state != RefreshState.None) return
        orderType = type
        tvAllOrder.setTextColor(if (zeroClick) ContextCompat.getColor(this,
                R.color.color_333333) else ContextCompat.getColor(this, R.color.color_9A9A9A))
        tvToPay.setTextColor(if (oneClick) ContextCompat.getColor(this,
                R.color.color_333333) else ContextCompat.getColor(this, R.color.color_9A9A9A))
        tvToSend.setTextColor(if (twoClick) ContextCompat.getColor(this,
                R.color.color_333333) else ContextCompat.getColor(this, R.color.color_9A9A9A))
        tvToBack.setTextColor(if (threeClick) ContextCompat.getColor(this,
                R.color.color_333333) else ContextCompat.getColor(this, R.color.color_9A9A9A))
        tvAllOrder.setTypeface(if (zeroClick) Typeface.defaultFromStyle(Typeface.BOLD)
        else Typeface.defaultFromStyle(Typeface.NORMAL))
        tvToPay.setTypeface(if (oneClick) Typeface.defaultFromStyle(Typeface.BOLD)
        else Typeface.defaultFromStyle(Typeface.NORMAL))
        tvToSend.setTypeface(if (twoClick) Typeface.defaultFromStyle(Typeface.BOLD)
        else Typeface.defaultFromStyle(Typeface.NORMAL))
        tvToBack.setTypeface(if (threeClick) Typeface.defaultFromStyle(Typeface.BOLD)
        else Typeface.defaultFromStyle(Typeface.NORMAL))
        recyclerViewZero.visibility = if (zeroClick) View.VISIBLE else View.GONE
        recyclerViewOne.visibility = if (oneClick) View.VISIBLE else View.GONE
        recyclerViewTwo.visibility = if (twoClick) View.VISIBLE else View.GONE
        recyclerViewThree.visibility = if (threeClick) View.VISIBLE else View.GONE
        vZeroPostion.visibility = if (zeroClick) View.VISIBLE else View.INVISIBLE
        vOnePostion.visibility = if (oneClick) View.VISIBLE else View.INVISIBLE
        vTwoPostion.visibility = if (twoClick) View.VISIBLE else View.INVISIBLE
        vThreePostion.visibility = if (threeClick) View.VISIBLE else View.INVISIBLE
        smartRefreshLayout.autoRefresh()
    }

    /**
     * @description ：请求结果回调
     * @lastEditor  ：张利超
     * @version     ：v1.0 ,2019/10/31
     * @param null
     * @return      ：
     * @deprecated  ： false
     */
    override fun returnShopOrderRecord(shopOrderList: List<ShopOrder>) {
        if (orderType.equals("99")) {
            //所有订单 分页处理
            setNewAdapter(shopAllOrderAdapter, shopOrderList)
        } else if (orderType.equals("0")) {
            //待付款返回分页
            setNewAdapter(shopOrderToPayAdapter, shopOrderList)
        } else if (orderType.equals("1")) {
            //代发货返回分页
            setNewAdapter(shopOrderToSendAdapter, shopOrderList)
        } else if (orderType.equals("2")) {
            //待收货
            setNewAdapter(shopOrderToTakeBackAdapter, shopOrderList)
        }
        pageId++
    }

    /**
     * @description ：设置返回数据
     * @lastEditor  ：张利超
     * @version     ：v1.0 ,2019/10/31
     * @param null
     * @return      ：
     * @deprecated  ： false
     */
    fun setNewAdapter(adapter: BaseQuickAdapter<ShopOrder, BaseViewHolder>, list: List<ShopOrder>) {
        if (pageId == 1) {
            adapter.setNewData(list)
        } else {
            adapter.addData(list)
            if (list.size > 0) {
                adapter.loadMoreComplete()
            } else {
                adapter.loadMoreEnd()
            }
        }

    }

    /**
     * @description ：网络连接失败处理
     * @lastEditor  ：张利超
     * @version     ：v1.0 ,2019/11/6
     * @param null
     * @return      ：
     * @deprecated  ： false
     */
    override fun finishRefresh() {
        smartRefreshLayout.finishRefresh()
        when (orderType) {
            "99" -> {
                shopAllOrderAdapter.loadMoreFail()
            }
            "0" -> {  //待付款
                shopOrderToPayAdapter.loadMoreFail()
            }
            "1" -> {  //代发货
                shopOrderToSendAdapter.loadMoreFail()
            }
            "2" -> { //待收货
                shopOrderToTakeBackAdapter.loadMoreFail()
            }
        }
    }

    /**
     * @description ：初始化全部订单adapter
     * @lastEditor  ：张利超
     * @version     ：v1.0 ,2019/10/31
     * @param null
     * @return      ：
     * @deprecated  ： false
     */
    private fun initShopAllOrderAdapter() {
        shopAllOrderAdapter = ShopAllOrderAdapter(arrayListOf())
        shopAllOrderAdapter.setOnLoadMoreListener(BaseQuickAdapter.RequestLoadMoreListener {
            mPresenter?.let { it.getPayBackRecordList(pageId.toString(), orderType) }
        }, recyclerViewZero)
        shopAllOrderAdapter.setLoadMoreView(OtherLoadMoreView())

        initEmptyView("", shopAllOrderAdapter)
        recyclerViewZero.adapter = shopAllOrderAdapter
        shopAllOrderAdapter.onItemChildClickListener = BaseQuickAdapter.OnItemChildClickListener { adapter, view, position ->
            if (view.id == R.id.tv_confirm_pay) {//确认收货
                if (TextUtils.equals((adapter.data[position] as ShopOrder).order_status, "2")) {
                    showAlertView((adapter.data[position] as ShopOrder).id, "您确认收到此订单产品？", "1")
                } else if (TextUtils.equals((adapter.data[position] as ShopOrder).order_status, "0")) { //点击进入立即付款

                    goPaymentTypeActivity((adapter.data[position] as ShopOrder).id)
                    //如果当取消订单或者审核失败订单那么点击会进入购买页 order_status=-1或者order_status=-3
                } else if (("-1".equals((adapter.data[position] as ShopOrder).order_status) || "-3".equals((adapter.data[position] as ShopOrder).order_status)) && !this.shopAllOrderAdapter.data.isNullOrEmpty()) {//点击进入再次购买
                    OpenWebViewUtils.openWebViewGoodsDetails(this@ShopAllOrderActivity, this.shopAllOrderAdapter.data[position].goods_list[0].id, true)
                }
            } else if (view.id == R.id.tv_cancle_order) {
                if (TextUtils.equals((adapter.data[position] as ShopOrder).order_status, "0")) {//取消订单
                    showAlertView((adapter.data[position] as ShopOrder).id, "您确认取消此订单？", "0")
                } else if (TextUtils.equals((adapter.data[position] as ShopOrder).order_status, "2") || TextUtils.equals((adapter.data[position] as ShopOrder).order_status, "3")) {//点击进入查看物流
                    val intent = Intent(this, MyLogisticsActivity::class.java)
                    intent.putExtra("orderId", (adapter.data[position] as ShopOrder).id)
                    startActivity(intent)
                }

            }
        }
    }

    /**
     * @description ：初始化待付款adapter
     * @lastEditor  ：张利超
     * @version     ：v1.0 ,2019/10/31
     * @param null
     * @return      ：
     * @deprecated  ： false
     */
    private fun initShopOrderToPayAdapter() {
        shopOrderToPayAdapter = ShopOrderToPayAdapter(arrayListOf())
        shopOrderToPayAdapter.setOnItemChildClickListener { adapter, view, position ->
            if (view.id == R.id.tv_confirm_pay) {//立即购买
                goPaymentTypeActivity((adapter.data[position] as ShopOrder).id)
            } else if (view.id == R.id.tv_cancle_order) {
                showAlertView((adapter.data[position] as ShopOrder).id, "您确认取消此订单？", "0")
            }
        }

        shopOrderToPayAdapter.setOnLoadMoreListener(BaseQuickAdapter.RequestLoadMoreListener {
            mPresenter?.let { it.getPayBackRecordList(pageId.toString(), orderType) }
        }, recyclerViewOne)
        shopOrderToPayAdapter.setLoadMoreView(OtherLoadMoreView())
        initEmptyView("", shopOrderToPayAdapter)
        recyclerViewOne.adapter = shopOrderToPayAdapter
    }

    /**
     * @description ：初始化代发货adapter
     * @lastEditor  ：张利超
     * @version     ：v1.0 ,2019/10/31
     * @param null
     * @return      ：
     * @deprecated  ： false
     */
    private fun initShopOrderToSendAdapter() {
        shopOrderToSendAdapter = ShopOrderToSendAdapter(arrayListOf())
        shopOrderToSendAdapter.setOnLoadMoreListener(BaseQuickAdapter.RequestLoadMoreListener {
            mPresenter?.let { it.getPayBackRecordList(pageId.toString(), orderType) }
        }, recyclerViewTwo)
        shopOrderToSendAdapter.setLoadMoreView(OtherLoadMoreView())
        initEmptyView("", shopOrderToSendAdapter)
        recyclerViewTwo.adapter = shopOrderToSendAdapter
    }

    /**
     * @description ：初始化待收货adapter
     * @lastEditor  ：张利超
     * @version     ：v1.0 ,2019/10/31
     * @param null
     * @return      ：
     * @deprecated  ： false
     */
    private fun initShopOrderToTakeBackAdapter() {
        shopOrderToTakeBackAdapter = ShopOrderToTakeBackAdapter(arrayListOf())
        shopOrderToTakeBackAdapter.setOnItemChildClickListener { adapter, view, position ->
            if (view.id == R.id.tv_confirm_pay) {//确认收货
                showAlertView((adapter.data[position] as ShopOrder).id, "您确认收到此订单产品？", "1")
            } else if (view.id == R.id.tv_cancle_order) {
                val intent = Intent(this, MyLogisticsActivity::class.java)
                intent.putExtra("orderId", (adapter.data[position] as ShopOrder).id)
                startActivity(intent)
            }
        }


        shopOrderToTakeBackAdapter.setOnLoadMoreListener(BaseQuickAdapter.RequestLoadMoreListener {
            mPresenter?.let { it.getPayBackRecordList(pageId.toString(), orderType) }
        }, recyclerViewThree)

        shopOrderToTakeBackAdapter.setLoadMoreView(OtherLoadMoreView())
        initEmptyView("", shopOrderToTakeBackAdapter)
        recyclerViewThree.adapter = shopOrderToTakeBackAdapter

    }

    private fun initEmptyView(msg: String, adapter: BaseQuickAdapter<*, *>) {
        val empty = LayoutInflater.from(this).inflate(R.layout.layout_empty_shop_order, null)
        adapter.setEmptyView(empty)
    }

    /**
     * @description ：点击订单列表的取消订单或者确认收货弹窗
     * @lastEditor  ：张利超
     * @version     ：v1.0 ,2019/10/31
     * @param {
     * o_id ：订单id ，type类型 0是取消订单，1是确认收货
     * }
     * @return      ：
     * @deprecated  ： false
     */
    private fun showAlertView(o_id: String, msg: String, type: String) {
        BaseDialog(this)
                .builder()
                .setCancelable(false)
                .setTitle(msg)
                .setBtnLeft("否")
                .setBtnRight("是", object : BaseDialog.BaseDialogBuilder.BtnClickListener {
                    override fun onClick(dialog: BaseDialog) {
                        if (type == "0") {
                            mPresenter?.let { it.cancelShopOrder(o_id, orderType) }
                        } else {
                            mPresenter?.let { it.confirmation(o_id) }
                        }
                    }
                })
                .create()
                .show()
    }

    /**
     * @description ：对返回按钮进行拦截拦截 判断是回商城页还是直接返回
     * @lastEditor  ：张利超
     * @version     ：v1.0 ,2019/10/31
     * @param null
     * @return      ：
     * @deprecated  ： true
     */
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && event?.getRepeatCount() == 0) { //按下的如果是BACK，同时没有重复
//            if (intentType.equals("PaymentTypeActivity") || intentType.equals("ShopPayResultActivity")) {
////                val intent = Intent(this, MainActivity::class.java)
////                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
////                intent.putExtra("goShop", "shop")
////                startActivity(intent)
////                finish()
////                return true;
////            }
            AppManager.getAppManager().killActivity(CommWebViewActivity::class.java)
            finish()
        }
        return super.onKeyDown(keyCode, event)
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

    override fun receivingGoodsSuccess() {
        setOnRefresh()
        startActivity(Intent(this, ShopConfirmReceivingGoodsActivity::class.java))
    }

    @Subscriber
    fun onMessageEvent(event: ShopAllOrderEvent) {
        setOnRefresh()
    }

    /**
     * @description ：检查额度回调成功进入回调付款方会
     * @lastEditor  ：张利超，田羽衡
     * @version     ：v1.0 ,2019/10/31
     * @param null
     * @return      ：
     * @deprecated  ： false
     */
    fun goPaymentTypeActivity(o_id: String) {
        val intent = Intent(this, PaymentTypeActivity::class.java)
        intent.putExtra("o_id", o_id)
        startActivity(intent)
    }

}


