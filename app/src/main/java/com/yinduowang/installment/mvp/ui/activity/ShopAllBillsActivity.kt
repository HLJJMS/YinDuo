package com.yinduowang.installment.mvp.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.jakewharton.rxbinding3.view.clicks
import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils
import com.yinduowang.installment.R
import com.yinduowang.installment.app.utils.LoadingUtils
import com.yinduowang.installment.app.utils.PhoneStatusUtils
import com.yinduowang.installment.di.component.DaggerShopAllBillsComponent
import com.yinduowang.installment.di.module.ShopAllBillsModule
import com.yinduowang.installment.mvp.contract.ShopAllBillsContract
import com.yinduowang.installment.mvp.model.entity.ShopAllBillsEntity
import com.yinduowang.installment.mvp.presenter.ShopAllBillsPresenter
import com.yinduowang.installment.mvp.ui.adapter.ShopAllBillsAdapter
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.activity_shopa_all_bills.*
import java.util.concurrent.TimeUnit
/**
 * @Description:我的-商城账单-所有账单
 * @Author: 张利超
 * @Date: 2019-10-31 10:26:52
 * @Version: v1.0, 2019-10-31
 * @LastEditors:张利超
 * @LastEditTime: 2019-10-31 10:26:52
 * @Deprecated: false
 */
class ShopAllBillsActivity : BaseActivity<ShopAllBillsPresenter>(), ShopAllBillsContract.View {
    lateinit var adapter: ShopAllBillsAdapter
    //年份初始值
    private var year: String = ""
    //    商城账单标识符
    private var SHOP_TYPE = "2"
    //年月标识符
    private var flag: String = "0"
    private lateinit var ivEmptyView: ImageView
    private lateinit var tvEmptyHint: TextView
    //返回的实体类
    private var shopAllBills: ShopAllBillsEntity? = null
    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerShopAllBillsComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .shopAllBillsModule(ShopAllBillsModule(this))
                .build()
                .inject(this)
    }


    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_shopa_all_bills //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    /**
     * 描  述：初始化控件
     * 修改人：张利超
     * 版本号：<v1.0，2019/10/30 9:05>
     * 参  数：
     * 返回值：
     * 修改时间：2019/10/30 9:05
     * 弃⽤： false
     */
    override fun initData(savedInstanceState: Bundle?) {
//        year = intent.getStringExtra("year")
//        tvTitleDate.text = "${year}年"
        groupLastYear.visibility = View.GONE
        groupNextYear.visibility = View.GONE
        val params = viewTop.getLayoutParams();
        params.width = LinearLayout.LayoutParams.MATCH_PARENT;
        params.height = PhoneStatusUtils.getStatusBarHeight(this)
        viewTop.layoutParams = params
        titleBar.showTitle("全部账单")
        titleBar.showWhiteBack()
        titleBar.setTitleTextColorResources(R.color.white)
        titleBar.setBottomLineShow(false)
        titleBar.setBackgroundAlpha(0)
        val view = LayoutInflater.from(this).inflate(R.layout.layout_empty_shop_bills, null)
        ivEmptyView = view.findViewById(R.id.ivAllBillsEmpty)
        tvEmptyHint = view.findViewById(R.id.tvAllBillsHint)
        adapter = ShopAllBillsAdapter(arrayListOf())
        adapter.setEmptyView(view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        getAllBillsList()
        //上下一页的点击控制
        tvLastYear.clicks().throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(Consumer {
            flag = "-1"
            getAllBillsList()
        })
        tvNextYear.clicks().throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(Consumer {
            flag = "+1"
            getAllBillsList()
        })
        //点击某月进入商城账单首页
        adapter.setOnItemClickListener { adapter, view, position ->
            shopAllBills?.let {
                if (it.billList.size > 0) {
                    val month = it.billList.get(position).month
                    val intent = Intent(this, ShopBillsActivity::class.java)
                    intent.putExtra("year", year)
                    intent.putExtra("month", month)
                    startActivity(intent)
                }
            }
        }

    }

    /**
     * 描  述：接口的
     * 修改人：张利超
     * 版本号：<v1.0，2019/10/30 9:05>
     * 参  数：
     * 返回值：
     * 修改时间：2019/10/30 9:05
     * 弃⽤： false
     */
    private fun getAllBillsList() {
        mPresenter?.let { it.getShopAllBills(year, flag, SHOP_TYPE) }
    }

    //返回数据回调
    override fun returnBackAllBills(shopAllBillsEntity: ShopAllBillsEntity) {
        shopAllBills = shopAllBillsEntity
        ivEmptyView.setImageResource(R.mipmap.ic_empty_year_bill)
        tvEmptyHint.text = "本年度暂无账单哦~"
        shopAllBillsEntity.year?.let {
            year = it.toString()
            tvLastYear.text = "${it - 1}年"
            tvNextYear.text = "${it + 1}年"
            tvTitleDate.text = "${it}年"
        }
        //是否为最早的账单年份,0是1不是 ,是否为最晚的账单年份,0是1不是
        if (shopAllBillsEntity.isLast == "0") {
            groupLastYear.visibility = View.GONE
        } else {
            groupLastYear.visibility = View.VISIBLE
        }
        if (shopAllBillsEntity.isNext == "0") {
            groupNextYear.visibility = View.GONE
        } else {
            groupNextYear.visibility = View.VISIBLE
        }

        if (shopAllBillsEntity.isLast == "0" && shopAllBillsEntity.isNext == "0") {
            ivEmptyView.setImageResource(R.mipmap.ic_empty_bill)
            tvEmptyHint.text = "暂无账单，快去商城购物吧！"
        }

        adapter.setNewData(shopAllBillsEntity.billList)
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
