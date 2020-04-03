package com.yinduowang.installment.mvp.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.SPUtils
import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils
import com.jess.arms.utils.Preconditions
import com.yinduowang.installment.R
import com.yinduowang.installment.app.constant.SPConstant
import com.yinduowang.installment.app.utils.LoadingUtils
import com.yinduowang.installment.app.widget.loadmore.FloatLoadMoreView
import com.yinduowang.installment.di.component.DaggerFloatDetailedComponent
import com.yinduowang.installment.di.module.FloatDetailedModule
import com.yinduowang.installment.mvp.contract.FloatDetailedContract
import com.yinduowang.installment.mvp.model.entity.LoanBannerEntity
import com.yinduowang.installment.mvp.presenter.FloatDetailedPresenter
import com.yinduowang.installment.mvp.ui.adapter.FLoatDetailedAdapter
import kotlinx.android.synthetic.main.activity_float_detailed.*
import java.util.*


/**
 * @Description:首页引流列表页面
 * @Author:张利超
 * @Date: 2019-10-23 10:11:28
 * @Version: v1.0, 2019-10-23
 * @LastEditors:张利超
 * @LastEditTime:2019-10-23 10:11:28
 * @Deprecated: false
 */
class FloatDetailedActivity : BaseActivity<FloatDetailedPresenter>(), FloatDetailedContract.View {
    /**
     * @description ：结束刷新回调
     * @lastEditor  ：张利超
     * @version     ：v1.0 ,2019/11/1
     * @param null
     * @return      ：
     * @deprecated  ： false
     */
    override fun onFinishRefresh() {
        smartRefreshLayout?.let { it.finishRefresh() }
        adapter?.let { it.loadMoreFail() }
    }

    private var headView: View? = null
    private var adapter: FLoatDetailedAdapter? = null
    //分页页数
    private var page: Int = 0
    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerFloatDetailedComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .floatDetailedModule(FloatDetailedModule(this))
                .build()
                .inject(this)
    }


    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_float_detailed //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    /**
     * 描  述：初始化控件
     * 修改人：张利超
     * 版本号：<v1.0，2019/10/30 13:44>
     * @param null
     * 返回值：
     * 修改时间：2019/10/30 13:44
     * 弃⽤： false
     */
    override fun initData(savedInstanceState: Bundle?) {
        myTitle.showTitle("更多借款")
        myTitle.setTitleTextColor(resources.getColor(R.color.color_333333))
        myTitle.showBlackBack()
        //初始化下拉刷新
        smartRefreshLayout.setOnRefreshListener {
            page = 1
            mPresenter!!.getFloatDetailedList(page)
        }
        adapter = FLoatDetailedAdapter(ArrayList(), "1")
        adapter!!.setOnLoadMoreListener({
            //初始化加载更多
            mPresenter!!.getFloatDetailedList(page)
        }, rv)
        headView = LayoutInflater.from(this@FloatDetailedActivity).inflate(R.layout.layout_header_float_details, null)
        adapter!!.setHeaderAndEmpty(true)
        var emptyView = LayoutInflater.from(this@FloatDetailedActivity).inflate(R.layout.layout_empty_float_list, null)
        adapter!!.setEmptyView(emptyView)
        adapter!!.setLoadMoreView(FloatLoadMoreView())
        rv!!.layoutManager = LinearLayoutManager(this@FloatDetailedActivity)
        rv!!.adapter = adapter
        smartRefreshLayout.autoRefresh()
    }


    override fun showLoading() {
        LoadingUtils.show(this)
    }

    override fun hideLoading() {
        LoadingUtils.dismiss(this)
    }

    override fun showMessage(message: String) {
        Preconditions.checkNotNull(message)
        ArmsUtils.snackbarText(message)
    }

    override fun launchActivity(intent: Intent) {
        Preconditions.checkNotNull(intent)
        ArmsUtils.startActivity(intent)
    }

    override fun killMyself() {
        finish()
    }

    /**
     * @description ：列表数据回调根据返回数据不同 判断加载方式
     * @lastEditor  ：张利超
     * @version     ：v1.0 ,2019/11/1
     * @param is_banner ：1为banner 0为引流列表 如果后台没有引流产品则返回banner数据则置空数据
     * @return      ：
     * @deprecated  ： false
     */
    override fun onResponseData(listBean: LoanBannerEntity) {
        if (listBean.is_banner != null && "0".equals(listBean.is_banner)) {
            listBean.type?.let {
                adapter?.type = it
            }
            SPUtils.getInstance().put(SPConstant.FLOAT_BUTTON_TYPE, listBean.type)
            if (page == 1) {//分页判断
//            adapter!!.addHeaderView(headView)
                adapter!!.setNewData(listBean.data_list)
            } else {
                if (listBean.data_list != null && listBean.data_list.size > 0) {
                    adapter!!.addData(listBean.data_list)
                    adapter!!.loadMoreComplete()
                } else {
                    adapter!!.loadMoreEnd(false)
                }
            }
            page++
        } else {
            adapter!!.setNewData(arrayListOf())
        }
    }
}
