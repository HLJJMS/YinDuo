package com.yinduowang.installment.mvp.ui.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jakewharton.rxbinding3.view.clicks
import com.jess.arms.base.BaseFragment
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils
import com.jess.arms.utils.DeviceUtils
import com.jess.arms.utils.PermissionUtil
import com.tbruyelle.rxpermissions2.RxPermissions
import com.yinduowang.installment.R
import com.yinduowang.installment.app.constant.Api
import com.yinduowang.installment.app.utils.GotoMQChatUtils
import com.yinduowang.installment.app.utils.PhoneStatusUtils
import com.yinduowang.installment.app.utils.gosetting.PermissionSetting
import com.yinduowang.installment.app.widget.popwindow.BottomPopupWindow
import com.yinduowang.installment.di.component.DaggerDiscoverComponent
import com.yinduowang.installment.di.module.DiscoverModule
import com.yinduowang.installment.mvp.contract.DiscoverContract
import com.yinduowang.installment.mvp.model.entity.DiscoverEntity
import com.yinduowang.installment.mvp.presenter.DiscoverPresenter
import com.yinduowang.installment.mvp.ui.activity.CommWebViewActivity
import com.yinduowang.installment.mvp.ui.adapter.DiscoveryAdapter
import kotlinx.android.synthetic.main.fragment_discover.*
import java.util.concurrent.TimeUnit


/**
 * @Description:提测时显示的发现页面
 * @Author:
 * @Date: 2019-11-13 11:28:50
 * @Version: 1.1, 2019-11-13
 * @LastEditors:
 * @LastEditTime:
 * @Deprecated: false
 */
class DiscoverFragment : BaseFragment<DiscoverPresenter>(), DiscoverContract.View {
    companion object {
        fun newInstance(): DiscoverFragment {
            return DiscoverFragment()
        }

        val ImageIds = arrayOf(R.mipmap.ic_discover_1, R.mipmap.ic_discover_2, R.mipmap.ic_discover_3,
                R.mipmap.ic_discover_4, R.mipmap.ic_discover_5, R.mipmap.ic_discover_6,
                R.mipmap.ic_discover_7, R.mipmap.ic_discover_8)
    }

    lateinit var discoverDetails: Array<String>
    lateinit var discoverList: List<DiscoverEntity>
    var kefuPopupWindow: BottomPopupWindow? = null

    override fun setupFragmentComponent(appComponent: AppComponent) {
        DaggerDiscoverComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .discoverModule(DiscoverModule(this))
                .build()
                .inject(this)
    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_discover, container, false)
    }

    @SuppressLint("CheckResult")
    override fun initData(savedInstanceState: Bundle?) {

        discoverDetails = resources.getStringArray(R.array.discover_details)
        val type = object : TypeToken<List<DiscoverEntity>>() {}.type
        discoverList = Gson().fromJson(resources.getString(R.string.discover_list), type)
        for (index in ImageIds.indices) {
            discoverList[index].imgId = ImageIds[index]
            discoverList[index].content = discoverDetails[index]
        }

        if (!this::discoverList.isInitialized) {
            return
        }
        recyclerView.layoutManager = LinearLayoutManager(activity)
        val discoveryAdapter = DiscoveryAdapter(discoverList)
        val view = LayoutInflater.from(activity).inflate(R.layout.layout_header_discover, null)
        discoveryAdapter.addHeaderView(view)
        recyclerView.adapter = discoveryAdapter

        val layoutParams = vStatus.layoutParams
        layoutParams.height = DeviceUtils.getStatusBarHeight(activity)

        // 帮助中心
        view.findViewById<View>(R.id.ivHelp).clicks().throttleFirst(500, TimeUnit.MILLISECONDS).subscribe {
            val intent = Intent(activity, CommWebViewActivity::class.java)
            intent.putExtra(CommWebViewActivity.KEY_URL_NAME, Api.HELP_CENTER)
            intent.putExtra(CommWebViewActivity.KEY_JAVASCRIPT_TYPE, CommWebViewActivity.TYPE_JAVASCRIPT_JEL_LE_ME)
            intent.putExtra(CommWebViewActivity.KEY_IS_AUTHENTICATION, CommWebViewActivity.TYPE_TITLE_NORMAL)
            startActivity(intent)
        }
        // 我的客服
        view.findViewById<View>(R.id.ivKefu).clicks().throttleFirst(500, TimeUnit.MILLISECONDS).subscribe {
            mPresenter?.getServiceTel()
        }
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

    //    获取电话成功
    override fun getTelSuccess(tel: String) {
        showPopupwindow(tel)
    }

    fun showPopupwindow(tel: String) {
        kefuPopupWindow = BottomPopupWindow(activity as Activity, true)
        kefuPopupWindow!!.setOneButtontText("在线联系客服")
        kefuPopupWindow!!.setTwoButtontText("拨打电话：$tel")
        kefuPopupWindow!!.setOnButtonClick(
                object : BottomPopupWindow.ButtonClick {
                    override fun setClick(id: Int) {
                        if (id == BottomPopupWindow.ONE_BUTTON) {
                            mPresenter?.let {
                                GotoMQChatUtils.gotoMQChatCustomized(activity!!, PhoneStatusUtils.getIMEI(), it.mErrorHandler)
                            }
                        } else if (id == BottomPopupWindow.TWO_BUTTON) {
                            requestsForPermissionsCall(tel)
                        }
                    }
                })
        kefuPopupWindow!!.show()
    }

    //    打电话权限
    fun requestsForPermissionsCall(phoneNum: String) {
        PermissionUtil.requestPermission(
                object : PermissionUtil.RequestPermission {
                    override fun onRequestPermissionSuccess() {
                        // 设置初始化显示页面
                        val intent = Intent(Intent.ACTION_CALL)
                        intent.data = Uri.parse("tel:$phoneNum")
                        startActivity(intent)
                    }

                    override fun onRequestPermissionFailure(permissions: List<String>) {
                        requestsForPermissionsCall(phoneNum)
                    }

                    override fun onRequestPermissionFailureWithAskNeverAgain(permissions: List<String>) {
                        PermissionSetting.toPermissionSetting(activity as Activity)
                    }
                },
                RxPermissions(this),
                mPresenter?.mErrorHandler,
                Manifest.permission.CALL_PHONE
        )
    }
}
