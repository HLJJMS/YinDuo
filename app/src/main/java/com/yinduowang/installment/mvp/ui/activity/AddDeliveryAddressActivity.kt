package com.yinduowang.installment.mvp.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.jakewharton.rxbinding3.view.clicks
import com.jakewharton.rxbinding3.widget.textChanges
import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils
import com.yinduowang.installment.R
import com.yinduowang.installment.app.utils.LoadingUtils
import com.yinduowang.installment.app.utils.PikerUtils
import com.yinduowang.installment.app.utils.StringUtil
import com.yinduowang.installment.app.utils.ToastUtils
import com.yinduowang.installment.app.widget.BaseDialog
import com.yinduowang.installment.di.component.DaggerAddDeliveryAddressComponent
import com.yinduowang.installment.di.module.AddDeliveryAddressModule
import com.yinduowang.installment.mvp.contract.AddDeliveryAddressContract
import com.yinduowang.installment.mvp.model.entity.MyProvince
import com.yinduowang.installment.mvp.presenter.AddDeliveryAddressPresenter
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.activity_add_delivery_address.*
import java.util.concurrent.TimeUnit

/**
 * @Description:我的-设置-收货地址-添加收货地址
 * @Author: 张利超
 * @Date: 2019-10-31 10:26:52
 * @Version: v1.0, 2019-10-31
 * @LastEditors:张利超
 * @LastEditTime: 2019-10-31 10:26:52
 * @Deprecated: false
 */

class AddDeliveryAddressActivity : BaseActivity<AddDeliveryAddressPresenter>(), AddDeliveryAddressContract.View {
    //省份
    var provinceString = ""
    //城市
    var cityNameString = ""
    //位置
    var areaNameString = ""
    var isDefault = false
    //是否是默认值
    var isDefaultVaule = "0"
    lateinit var provinces: List<MyProvince>
    var baseDialog: BaseDialog? = null

    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerAddDeliveryAddressComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .addDeliveryAddressModule(AddDeliveryAddressModule(this))
                .build()
                .inject(this)
    }


    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_add_delivery_address //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    /**
     * 描  述：初始化页面问题信息
     * 修改人：张利超
     * 版本号：<v1.0，2019/10/30 17:15>
     * @param null
     * 返回值：
     * 修改时间：2019/10/30 17:15
     * 弃⽤： false
     */
    @SuppressLint("CheckResult")
    override fun initData(savedInstanceState: Bundle?) {
        initAlertView()
        layoutTitle.showTitleAndBack("新建收货地址", View.OnClickListener {
            //如果对添加地址进行了编辑则弹出度返回弹窗
            var isNotEmpty = etDeliveryName.text.toString().trim().length > 0
                    || etDeliveryPhone.text.toString().trim().length > 0
                    || etDeliveryArea.text.toString().trim().length > 0
                    || etDeliveryAddress.text.toString().trim().length > 0
            if (isNotEmpty) {
                baseDialog?.let {
                    it.show()
                }
            } else {
                finish()
            }
        })
        qmuiFrameLayout.radius = ArmsUtils.dip2px(this, 5f)
        qmuiFrameLayout.shadowElevation = ArmsUtils.dip2px(this, 5f)
        PikerUtils.initCityList(this)
        //收货人姓名监听
        etDeliveryName.textChanges()
                .subscribe(Consumer<CharSequence> { charSequence ->
                    tvSave.isEnabled = etDeliveryName.text.toString().trim().length > 0 && etDeliveryPhone.text.toString().trim().length > 0
                            && etDeliveryArea.text.toString().trim().length > 0 && etDeliveryAddress.text.toString().trim().length > 0
                })
        //收货人电话监听
        etDeliveryPhone.textChanges()
                .subscribe(Consumer<CharSequence> { charSequence ->
                    tvSave.isEnabled = etDeliveryName.text.toString().trim().length > 0 && etDeliveryPhone.text.toString().trim().length > 0
                            && etDeliveryArea.text.toString().trim().length > 0 && etDeliveryAddress.text.toString().trim().length > 0
                })
        //收货人位置监听
        etDeliveryArea.textChanges()
                .subscribe(Consumer<CharSequence> { charSequence ->
                    tvSave.isEnabled = etDeliveryName.text.toString().trim().length > 0 && etDeliveryPhone.text.toString().trim().length > 0
                            && etDeliveryArea.text.toString().trim().length > 0 && etDeliveryAddress.text.toString().trim().length > 0
                })
        //收货人详细位置监听
        etDeliveryAddress.textChanges()
                .subscribe(Consumer<CharSequence> { charSequence ->
                    tvSave.isEnabled = etDeliveryName.text.toString().trim().length > 0 && etDeliveryPhone.text.toString().trim().length > 0
                            && etDeliveryArea.text.toString().trim().length > 0 && etDeliveryAddress.text.toString().trim().length > 0
                })
        // 收货人地址选择点击按钮
        etDeliveryAddress.clicks()
                .subscribe(Consumer {
                    hideInput()
                    showCityPicker()
                })
        //保存按钮点击事件
        tvSave.clicks().throttleFirst(500, TimeUnit.MILLISECONDS).subscribe {
            if (!StringUtil.isMobileNO(etDeliveryPhone.text.toString())) {
                ToastUtils.makeText(this, "请输入11位手机号码")
                return@subscribe
            }
            mPresenter?.let {
                it.run {
                    addDeliveryOrChangeAddress("", provinceString, cityNameString, areaNameString
                            , etDeliveryArea.text.toString().trim(), etDeliveryPhone.text.toString().trim(), etDeliveryName.text.toString().trim(), isDefaultVaule)
                }
            }

        }
        //默认收货地址监听
        ivCheckDefault.clicks()
                .subscribe(Consumer {
                    if (isDefaultVaule == "1") {
                        isDefaultVaule = "0"
                        ivCheckDefault.setImageResource(R.mipmap.ic_address_normal)
                    } else {
                        ivCheckDefault.setImageResource(R.mipmap.ic_address_press)
                        isDefaultVaule = "1"
                    }
                })
    }


    /**
     * 描  述：保存成功回调
     * 修改人：张利超
     * 版本号：<v1.0，2019/10/30 17:18>
     * @param null
     * 返回值：
     * 修改时间：2019/10/30 17:18
     * 弃⽤： false
     */
    override fun returnChangeSuccess() {
        killMyself()
    }


    /**
     * 描  述： //城市选择器弹窗用的三方
     * 修改人：张利超
     * 版本号：<v1.0，2019/10/30 17:18>
     * @param null
     * 返回值：
     * 修改时间：2019/10/30 17:18
     * 弃⽤： false
     */
    private fun showCityPicker() {
        if (PikerUtils.isInitCityList()) {
            PikerUtils.showCityPicker(this, resources.getString(R.string.selection_of_areas), object : PikerUtils.OnCitySelectListener {
                @SuppressLint("SetTextI18n")
                override fun onOptionsSelect(options1: String, options2: String, options3: String, v: View?) {
                    provinceString = options1
                    cityNameString = options2
                    areaNameString = options3
                    etDeliveryAddress.text = "$options1 $options2 $options3"
                }
            })
        } else {
            ToastUtils.makeText(this, resources.getString(R.string.selection_of_areas_error))
        }
    }

    /**
     * 描  述：未保存就退出弹窗
     * 修改人：张利超
     * 版本号：<v1.0，2019/10/30 17:18>
     * @param null
     * 返回值：
     * 修改时间：2019/10/30 17:18
     * 弃⽤： false
     */
    private fun initAlertView() {
        baseDialog = BaseDialog(this)
                .builder()
                .setCancelable(false)
                .setTitle("修改的信息还未保存，\n确认现在返回吗？")
                .setTitlePaddingBottom(23f)
                .setBtnLeft("否")
                .setBtnRight("是", object : BaseDialog.BaseDialogBuilder.BtnClickListener {
                    override fun onClick(dialog: BaseDialog) {
                        finish()
                    }
                })
                .create()

    }

    /**
     * 描  述：隐藏系统键盘方法
     * 修改人：张利超
     * 版本号：<v1.0，2019/10/30 17:19>
     * @param null
     * 返回值：
     * 修改时间：2019/10/30 17:19
     * 弃⽤： false
     */
    fun hideInput() {
        val view = this.getCurrentFocus()
        if (view != null) {
            val inputMethodManager = this.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view!!.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }


    /**
     * 描  述：返回键监听弹窗
     * 修改人：张利超
     * 版本号：<v1.0，2019/10/30 17:19>
     * @param null
     * 返回值：
     * 修改时间：2019/10/30 17:19
     * 弃⽤： false
     */
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (KeyEvent.KEYCODE_BACK == event?.keyCode) {
            //如果对添加地址进行了编辑则弹出度画框
            var isNotEmpty = etDeliveryName.text.toString().trim().length > 0 ||
                    etDeliveryPhone.text.toString().trim().length > 0 ||
                    etDeliveryArea.text.toString().trim().length > 0 ||
                    etDeliveryAddress.text.toString().trim().length > 0
            if (isNotEmpty) {
                baseDialog?.let {
                    it.show()
                }
            } else {
                finish()
            }
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
}
