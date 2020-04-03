package com.yinduowang.installment.mvp.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils
import com.yinduowang.installment.R
import com.yinduowang.installment.app.widget.loadmore.OtherLoadMoreView
import com.yinduowang.installment.di.component.DaggerShopAfterSaleComponent
import com.yinduowang.installment.di.module.ShopAfterSaleModule
import com.yinduowang.installment.mvp.contract.ShopAfterSaleContract
import com.yinduowang.installment.mvp.model.entity.ShopOrder
import com.yinduowang.installment.mvp.presenter.ShopAfterSalePresenter
import com.yinduowang.installment.mvp.ui.adapter.ShopAfterSaleAdapter
import kotlinx.android.synthetic.main.activity_after_sale.*
import java.util.*


/**
 * Description：售后、退还
 * Author：田羽衡
 * Version：<v1.0，2019/11/1 >
 * LastEditTime：2019/11/1
 * LastEditors:
 * Deprecated： false
 */

class ShopAfterSaleActivity : BaseActivity<ShopAfterSalePresenter>(), ShopAfterSaleContract.View {
    var page = 1
    lateinit var adapterShop: ShopAfterSaleAdapter
    val list = ArrayList<String>()
    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerShopAfterSaleComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .shopAfterSaleModule(ShopAfterSaleModule(this))
                .build()
                .inject(this)
    }


    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_after_sale //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }


    override fun initData(savedInstanceState: Bundle?) {
        titleBar.showTitleAndBack(R.string.after_sale_title)
        recycler.layoutManager = LinearLayoutManager(this)
        adapterShop = ShopAfterSaleAdapter(arrayListOf())
        recycler.adapter = adapterShop
        adapterShop.setLoadMoreView(OtherLoadMoreView())
        val empty = LayoutInflater.from(this).inflate(R.layout.layout_empty_shop_after_shop, null)
        adapterShop.setEmptyView(empty)
        adapterShop.setOnLoadMoreListener(BaseQuickAdapter.RequestLoadMoreListener {
            getData()

        }, recycler)
        smartRefreshLayout.setOnRefreshListener {
            page = 1
            getData()
        }

        smartRefreshLayout.autoRefresh()
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
        finish()
    }

    /**
     * Description：数据获取成
     * Author：田羽衡
     * Version：<v1.0，2019/10/31 10:33>
     * params:
     * return：
     * LastEditTime：2019/10/31 10:33
     * Deprecated： false
     */
    override fun getListSuccess(shopOrderList: List<ShopOrder>) {
        if (shopOrderList.size == 0) {
            adapterShop.loadMoreEnd()
        } else if (page == 1) {
            adapterShop.setNewData(shopOrderList)
            adapterShop.loadMoreComplete()
        } else {
            adapterShop.addData(shopOrderList)
            adapterShop.loadMoreComplete()
        }
        page++
        smartRefreshLayout.finishRefresh()
    }


    /**
     * Description：数据获取失败
     * Author：田羽衡
     * Version：<v1.0，2019/10/31 10:33>
     * params:
     * return：
     * LastEditTime：2019/10/31 10:33
     * Deprecated： false
     */
    override fun getListFail() {
        adapterShop.loadMoreFail()
        smartRefreshLayout.finishRefresh()
    }


    /**
     * Description： 获取列表数据
     * Author：田羽衡
     * Version：<v1.0，2019/10/31 10:33>
     * params:
     * return：
     * LastEditTime：2019/10/31 10:33
     * Deprecated： false
     */
    fun getData() {
        mPresenter?.getPayBackRecordList(page.toString())
    }

}
