package com.yinduowang.installment.mvp.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import androidx.recyclerview.widget.LinearLayoutManager
import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils
import com.yinduowang.installment.R
import com.yinduowang.installment.app.widget.loadmore.MessageCustomLoadMoreView
import com.yinduowang.installment.di.component.DaggerMessageCenterComponent
import com.yinduowang.installment.di.module.MessageCenterModule
import com.yinduowang.installment.mvp.contract.MessageCenterContract
import com.yinduowang.installment.mvp.model.entity.MessageEntity
import com.yinduowang.installment.mvp.presenter.MessageCenterPresenter
import com.yinduowang.installment.mvp.ui.adapter.MessageCenterAdapter
import kotlinx.android.synthetic.main.activity_message_center.*


/**
 * Description：消息中心
 * Author：田羽衡
 * Version：<v1.0，2019/11/1 >
 * LastEditTime：2019/11/1
 * LastEditors:
 * Deprecated： false
 */
class MessageCenterActivity : BaseActivity<MessageCenterPresenter>(), MessageCenterContract.View {
    /**
     * @description ：下拉加载失败时走
     * @lastEditor  ：张利超
     * @version     ：v1.0 ,2019/11/5
     * @param null
     * @return      ：
     * @deprecated  ： false
     */
    override fun onErrorFail() {
        adapter?.let { it.loadMoreFail() }
    }

    lateinit var adapter: MessageCenterAdapter
    fun isInitializedAdapter() = ::adapter.isInitialized
    var page = 1
    var releaseTime = ""
    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerMessageCenterComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .messageCenterModule(MessageCenterModule(this))
                .build()
                .inject(this)
    }


    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_message_center //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    /**
     * @description ：结束刷线 在onerror时也结束刷新
     * @lastEditor  ：张利超
     * @version     ：v1.0 ,2019/11/5
     * @param null
     * @return      ：
     * @deprecated  ： false
     */
    override fun refreshFinish() {
        smartRefreshLayout.finishRefresh()
    }


    override fun initData(savedInstanceState: Bundle?) {
        titleBar.showTitleAndBack(resources.getString(R.string.message_center))

        smartRefreshLayout.setOnRefreshListener {
            page = 1
            mPresenter?.let { it.getMessage(page, "") }
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MessageCenterAdapter(arrayListOf())
        adapter.setLoadMoreView(MessageCustomLoadMoreView())
        adapter.setOnLoadMoreListener({
            mPresenter?.let { it.getMessage(page, releaseTime) }
        }, recyclerView)
        recyclerView.adapter = adapter
        smartRefreshLayout.autoRefresh()
    }

    /**
     * Description：获取列表成功
     * Author：田羽衡
     * Version：<v1.0，2019/11/1 9:59>
     * params:
     * return：
     * LastEditTime：2019/11/1 9:59
     * LastEditDescription: 只修改了页数控制相关
     * Deprecated： false
     */
    override fun getMessageSuccess(messageList: List<MessageEntity>) {
        if (page == 1) {
            if (messageList.size > 0) {
                imageView11.visibility = GONE
                recyclerView.visibility = View.VISIBLE
                if (isInitializedAdapter()) {
                    adapter.setNewData(messageList)
                }
            } else {
                imageView11.visibility = View.VISIBLE
                recyclerView.visibility = GONE
            }
            if (!messageList.isNullOrEmpty()) {
                releaseTime = messageList.first().releaseTime
            }
        } else {
                adapter.addData(messageList)
            if (messageList.size > 0) {
                adapter.loadMoreComplete()
            } else {
                adapter.loadMoreEnd()
            }
        }
        page++
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
}
