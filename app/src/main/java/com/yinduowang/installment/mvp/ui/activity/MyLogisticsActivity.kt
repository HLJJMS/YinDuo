package com.yinduowang.installment.mvp.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils
import com.yinduowang.installment.R
import com.yinduowang.installment.app.utils.LoadingUtils
import com.yinduowang.installment.di.component.DaggerMyLogisticsComponent
import com.yinduowang.installment.di.module.MyLogisticsModule
import com.yinduowang.installment.mvp.contract.MyLogisticsContract
import com.yinduowang.installment.mvp.model.entity.GetLogisticsEntity
import com.yinduowang.installment.mvp.presenter.MyLogisticsPresenter
import com.yinduowang.installment.mvp.ui.adapter.MyLogisticsAdapter
import kotlinx.android.synthetic.main.activity_my_logistics.*
import java.util.*


/**
 * Description：物流信息
 * Author：田羽衡
 * Version：<v1.0，2019/11/1 >
 * LastEditTime：2019/11/1
 * LastEditors:
 * Deprecated： false
 */
class MyLogisticsActivity : BaseActivity<MyLogisticsPresenter>(), MyLogisticsContract.View {


    lateinit var adapter: MyLogisticsAdapter
    val list = ArrayList<String>()
    var orderId = ""
    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerMyLogisticsComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .myLogisticsModule(MyLogisticsModule(this))
                .build()
                .inject(this)
    }


    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_my_logistics //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }


    override fun initData(savedInstanceState: Bundle?) {
        titleBar.showTitleAndBack(resources.getString(R.string.logistics_detail))
        intent.getStringExtra("orderId").let { orderId = it }
        adapter = MyLogisticsAdapter(arrayListOf())
        rec.layoutManager = LinearLayoutManager(this)
        rec.adapter = adapter
        mPresenter?.getData(orderId)
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
     * Description：物流信息请求成功回调
     * Author：田羽衡
     * Version：<v1.0，2019/10/30 17:55>
     * params:GetLogisticsEntity
     * return：null
     * LastEditTime：2019/10/30 17:55
     * Deprecated： false
     */
    override fun success(bean: GetLogisticsEntity) {
        tvId.text = "物流单号：" + bean.sn
        tvName.text = "物流公司：" + bean.name
        adapter.addData(bean.logistics_list)
    }
}
