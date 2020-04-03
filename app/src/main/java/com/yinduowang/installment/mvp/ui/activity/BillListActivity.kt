package com.yinduowang.installment.mvp.ui.activity


import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils
import com.yinduowang.installment.R
import com.yinduowang.installment.app.widget.loadmore.CustomLoadMoreView
import com.yinduowang.installment.di.component.DaggerBillListComponent
import com.yinduowang.installment.di.module.BillListModule
import com.yinduowang.installment.mvp.contract.BillListContract
import com.yinduowang.installment.mvp.model.entity.BillListEntity
import com.yinduowang.installment.mvp.presenter.BillListPresenter
import com.yinduowang.installment.mvp.ui.adapter.BillListAdapter
import kotlinx.android.synthetic.main.activity_bill_list.*



/**
 * Description：账单明细activity
 * Author：田羽衡
 * Version：<v1.0，2019/11/1 >
 * LastEditTime：2019/11/1
 * LastEditors:
 * Deprecated： false
 */

class BillListActivity : BaseActivity<BillListPresenter>(), BillListContract.View {
    private var year: String = ""
    private var month: String = ""
    //    id代表默认页数，0是第一页，第二页id是第一页最后一条数据的id以此类推
    private var id: String = "0"
    //    商城账单标识符
    private var SHOP_TYPE = "2"
    lateinit var adapter: BillListAdapter
    lateinit var bean: BillListEntity
    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerBillListComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .billListModule(BillListModule(this))
                .build()
                .inject(this)
    }


    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_bill_list //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }


    override fun initData(savedInstanceState: Bundle?) {
        titlebar.showTitleAndBack(R.string.bill_list_title)
        aboutRecycler()
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
     * Description：获取列表成功
     * Author：田羽衡
     * Version：<v1.0，2019/10/31 10:38>
     * params:
     * return：
     * LastEditTime：2019/10/31 10:38
     * Deprecated： false
     */

    override fun getListSuccess(bean: BillListEntity) {
        var size = bean.list.size
        if (size == 0) {
            adapter.loadMoreEnd()
        } else {
            adapter.addData(bean.list)
            id = bean.list.get(size - 1).id
            adapter.loadMoreComplete()
        }

    }

    override fun getListFail() {
        adapter.loadMoreEnd()
    }

    /**
     * Description：关于recycler 的设置
     * Author：田羽衡
     * Version：<v1.0，2019/10/31 10:38>
     * params:
     * return：
     * LastEditTime：2019/10/31 10:38
     * Deprecated： false
     */
    fun aboutRecycler() {
        recycler.layoutManager = LinearLayoutManager(this)
        adapter = BillListAdapter(arrayListOf())
        recycler.adapter = adapter
        adapter.setLoadMoreView(CustomLoadMoreView())
        year = intent.getStringExtra("year")
        month = intent.getStringExtra("month")
        mPresenter?.getBillList(year, month, id, SHOP_TYPE);
        adapter.setOnLoadMoreListener(BaseQuickAdapter.RequestLoadMoreListener {
            mPresenter?.getBillList(year, month, id, SHOP_TYPE);
        }, recycler)
        adapter.setLoadMoreView(CustomLoadMoreView())
        adapter.setOnItemClickListener(BaseQuickAdapter.OnItemClickListener({ adapter, view, position ->
            var intent = Intent(this, InstalmentDetailActivity::class.java)
            intent.putExtra("id", this.adapter.data.get(position).loanId.toString())
            startActivity(intent)
        }))
    }
}
