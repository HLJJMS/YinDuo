package com.yinduowang.installment.mvp.ui.activity

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.jakewharton.rxbinding3.view.clicks
import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils
import com.yinduowang.installment.R
import com.yinduowang.installment.app.utils.LoadingUtils
import com.yinduowang.installment.app.widget.BaseDialog
import com.yinduowang.installment.di.component.DaggerDeliveryAddressComponent
import com.yinduowang.installment.di.module.DeliveryAddressModule
import com.yinduowang.installment.mvp.contract.DeliveryAddressContract
import com.yinduowang.installment.mvp.model.entity.DeliveryAddress
import com.yinduowang.installment.mvp.presenter.DeliveryAddressPresenter
import com.yinduowang.installment.mvp.ui.adapter.DeliveryAddressAdapter
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.activity_delivery_address.*
import java.util.concurrent.TimeUnit

/**
 * @Description:我的-设置-收货地址列表
 * @Author: 张利超
 * @Date: 2019-10-31 10:26:52
 * @Version: v1.0, 2019-10-31
 * @LastEditors:张利超
 * @LastEditTime: 2019-10-31 10:26:52
 * @Deprecated: false
 */

class DeliveryAddressActivity : BaseActivity<DeliveryAddressPresenter>(), DeliveryAddressContract.View {

    lateinit var dialog: BaseDialog
    //地址列表适配器
    val deliveryAdapter: DeliveryAddressAdapter = DeliveryAddressAdapter(arrayListOf())
    var mList: List<DeliveryAddress>? = null
    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerDeliveryAddressComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .deliveryAddressModule(DeliveryAddressModule(this))
                .build()
                .inject(this)
    }


    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_delivery_address //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    /**
     * @description ：初始化控件信息
     * @lastEditor  ：张利超
     * @version     ：v1.0 ,2019/11/1
     * @param null
     * @return      ：
     * @deprecated  ： false
     */
    override fun initData(savedInstanceState: Bundle?) {
        layoutTitle.showTitleAndBack("收货地址")
        rvDeliveryAddress.layoutManager = LinearLayoutManager(this)
        rvDeliveryAddress.adapter = deliveryAdapter
        val view = LayoutInflater.from(this).inflate(R.layout.layout_no_data_delivery_address, null)
        view.findViewById<View>(R.id.tvAddAddress).setOnClickListener {
            startActivity(Intent(this, AddDeliveryAddressActivity::class.java))
        }
        //地址列表每条点击事件跳转到修改收货地址页面
        deliveryAdapter.setOnItemImageListener(object : DeliveryAddressAdapter.onImageClickListener {
            override fun onClick(v: View, position: Int) {
                //解决收货地址点击事件冲突问题
                if (!mList.isNullOrEmpty()) {
                    mList!![position].id?.let {
                        val intent = Intent(this@DeliveryAddressActivity, ChangeDeliveryAddressActivity::class.java)
                        intent.putExtra("id", it)
                        startActivity(intent)
                    }
                }
            }

        })
        deliveryAdapter.setEmptyView(view)
        //跳转到添加收货地址页面
        tvEditDeliverAdd.clicks().throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(Consumer {
            startActivity(Intent(this, AddDeliveryAddressActivity::class.java))
        })
        //长按每条列表弹出删除弹窗
        deliveryAdapter.setOnItemLongClickListener { adapter, view, position ->
            dialogCreate(deliveryAdapter.data.get(position).id)
        }

    }

    /**
     * @description ：每次此页都刷新界面数据
     * @lastEditor  ：张利超
     * @version     ：v1.0 ,2019/11/7
     * @param null
     * @return      ：
     * @deprecated  ： false
     */
    override fun onResume() {
        super.onResume()
        mPresenter?.let { it.getDeliveryAddressList() }
    }
    /**
     * @description ：收货地址列表返回
     * @lastEditor  ：张利超
     * @version     ：v1.0 ,2019/11/1
     * @param null
     * @return      ：
     * @deprecated  ： false
     */
    override fun returnDeliveryAddressList(list: List<DeliveryAddress>) {
        mList = list
        if (list.size > 0) {
            tvEditDeliverAdd.visibility = View.VISIBLE
        } else {
            tvEditDeliverAdd.visibility = View.GONE
        }
        deliveryAdapter.setNewData(list)
        // 判断是否是从web页面打开 用于选择收货地址
        if (intent.getBooleanExtra("chooseAddress", false)) {
            deliveryAdapter.setOnItemClickListener { adapter, view, position ->
                    var intent = Intent()
                    intent.putExtra("addressId", list[position].id)
                    intent.putExtra("addressName", list[position].province + list[position].city + list[position].area + list[position].address)
                    setResult(CommWebViewActivity.RESULT_CODE_ADDRESS, intent)
                    killMyself()
            }
        }

    }
    /**
     * @description ：  删除弹窗出事画并弹出
     * @lastEditor  ：张利超
     * @version     ：v1.0 ,2019/11/1
     * @param null
     * @return      ：
     * @deprecated  ： false
     */
    fun dialogCreate(id: String): Boolean {
        BaseDialog(this)
                .builder()
                .setCancelable(false)
                .setTitle("您确认删除此收货地址？")
                .setBtnLeft("否")
                .setBtnRight("是", 16f, ContextCompat.getColor(this, R.color.color_365AF7), Typeface.NORMAL, object : BaseDialog.BaseDialogBuilder.BtnClickListener {
                    override fun onClick(dialog: BaseDialog) {
                        mPresenter?.delAddress(id)
                    }
                })
                .create()
                .show()
        return true
    }


    //    删除成功
    override fun delAddressSuccess() {
        mPresenter?.let { it.getDeliveryAddressList() }
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
