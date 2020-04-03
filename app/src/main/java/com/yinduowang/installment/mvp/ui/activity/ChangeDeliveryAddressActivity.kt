package com.yinduowang.installment.mvp.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import com.bigkoo.pickerview.builder.OptionsPickerBuilder
import com.bigkoo.pickerview.listener.OnOptionsSelectListener
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jakewharton.rxbinding3.view.clicks
import com.jakewharton.rxbinding3.widget.textChanges
import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils
import com.yinduowang.installment.R
import com.yinduowang.installment.app.utils.LoadingUtils
import com.yinduowang.installment.app.utils.StringUtil
import com.yinduowang.installment.app.utils.ToastUtils
import com.yinduowang.installment.app.widget.BaseDialog
import com.yinduowang.installment.di.component.DaggerChangeDliveryAddressComponent
import com.yinduowang.installment.di.module.ChangeDliveryAddressModule
import com.yinduowang.installment.mvp.contract.ChangeDliveryAddressContract
import com.yinduowang.installment.mvp.model.entity.DeliveryAddress
import com.yinduowang.installment.mvp.model.entity.MyProvince
import com.yinduowang.installment.mvp.presenter.ChangeDliveryAddressPresenter
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.activity_change_dlivery_address.*
import java.nio.charset.Charset
import java.util.*
import java.util.concurrent.TimeUnit


/**
 * @Description:我的-设置-编辑收货地址
 * @Author: 张利超
 * @Date: 2019-10-31 10:26:52
 * @Version: v1.0, 2019-10-31
 * @LastEditors:张利超
 * @LastEditTime: 2019-10-31 10:26:52
 * @Deprecated: false
 */
class ChangeDeliveryAddressActivity : BaseActivity<ChangeDliveryAddressPresenter>(), ChangeDliveryAddressContract.View {

    //省市及地址名字
    var provinceString = ""
    var cityNameString = ""
    var areaNameString = ""
    var isDefault = false
    //是否为默认收货地址
    var isDefaultVaule = ""
    var provinces = ArrayList<MyProvince>()
    var baseDialog: BaseDialog? = null
    val provinceNames = ArrayList<String>()
    val cityNames = ArrayList<List<String>>()
    val areaNames = ArrayList<List<List<String>>>()
    //收货地址id
    var id: String = ""
    var delAddress: DeliveryAddress? = null
    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerChangeDliveryAddressComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .changeDliveryAddressModule(ChangeDliveryAddressModule(this))
                .build()
                .inject(this)
    }


    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_change_dlivery_address //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    /**
     * @description ：初始化控件及数据
     * @lastEditor  ：张利超
     * @version     ：v1.0 ,2019/11/1
     * @param null
     * @return      ：
     * @deprecated  ： false
     */
    @SuppressLint("CheckResult")
    override fun initData(savedInstanceState: Bundle?) {
        id = intent.getStringExtra("id")
        //初始化弹窗
        initAlertView()
        //对返回键进行拦截如果此页面已进行编辑了那么需要弹出对话框
        layoutTitle.showTitleAndBack("编辑收货地址", View.OnClickListener {
            if (delAddress != null) {
                val isChange = delAddress?.name != etDeliveryName.text.toString().trim() || delAddress?.mobile != etDeliveryPhone.text.toString().trim()
                        || (delAddress?.province + delAddress?.city + delAddress?.area) != etDeliveryAddress.text.toString().trim().replace(" ", "")
                        || delAddress?.address != etDeliveryArea.text.toString().trim() || delAddress?.is_default != isDefaultVaule
                if (isChange) {
                    baseDialog?.let {
                        it.show()
                    }
                } else {
                    killMyself()
                }
            } else {
                killMyself()
            }
        })
        qmuiFrameLayout.radius = ArmsUtils.dip2px(this, 5f)
        qmuiFrameLayout.shadowElevation = ArmsUtils.dip2px(this, 5f)
        parseCityList()
        //收货人姓名
        etDeliveryName.textChanges()
                .subscribe(Consumer<CharSequence> { charSequence ->
                    tvSave.isEnabled = etDeliveryName.text.toString().trim().length > 0 && etDeliveryPhone.text.toString().trim().length > 0
                            && etDeliveryArea.text.toString().trim().length > 0 && etDeliveryAddress.text.toString().trim().length > 0 && !charSequence.toString().equals(delAddress?.name)
                })
        //收货人电话
        etDeliveryPhone.textChanges()
                .subscribe(Consumer<CharSequence> { charSequence ->
                    tvSave.isEnabled = etDeliveryName.text.toString().trim().length > 0 && etDeliveryPhone.text.toString().trim().length > 0
                            && etDeliveryArea.text.toString().trim().length > 0 && etDeliveryAddress.text.toString().trim().length > 0 && !charSequence.toString().equals(delAddress?.mobile)
                })
        //收货人详细地址
        etDeliveryArea.textChanges()
                .subscribe(Consumer<CharSequence> { charSequence ->
                    tvSave.isEnabled = etDeliveryName.text.toString().trim().length > 0 && etDeliveryPhone.text.toString().trim().length > 0
                            && etDeliveryArea.text.toString().trim().length > 0 && etDeliveryAddress.text.toString().trim().length > 0 && !charSequence.toString().equals(delAddress?.address)
                })
        //收货人区域
        etDeliveryAddress.textChanges()
                .subscribe(Consumer<CharSequence> { charSequence ->
                    tvSave.isEnabled = etDeliveryName.text.toString().trim().length > 0 && etDeliveryPhone.text.toString().trim().length > 0
                            && etDeliveryArea.text.toString().trim().length > 0 && etDeliveryAddress.text.toString().trim().length > 0 && !charSequence.toString().equals(delAddress?.province + " " + delAddress?.city + " " + delAddress?.address)
                })
        //选择城市
        etDeliveryAddress.clicks()
                .subscribe(Consumer {
                    hideInput()
                    showCityPicker()
                })
        //保存按钮
        tvSave.clicks().throttleFirst(500, TimeUnit.MILLISECONDS).subscribe {
            if (!StringUtil.isMobileNO(etDeliveryPhone.text.toString())) {
                ToastUtils.makeText(this, "请输入11位手机号码")
                return@subscribe
            }
            mPresenter?.let {
                it.run {
                    changeDeliveryOrChangeAddress(id, "", provinceString, cityNameString, areaNameString
                            , etDeliveryArea.text.toString().trim(), etDeliveryPhone.text.toString().trim(), etDeliveryName.text.toString().trim(), isDefaultVaule)
                }
            }

        }
        //是否为默认按钮点击
        ivCheckDefault.clicks()
                .subscribe(Consumer {
                    if (isDefaultVaule == "1") {
                        isDefaultVaule = "0"
                        ivCheckDefault.setImageResource(R.mipmap.ic_address_normal)
                    } else {
                        ivCheckDefault.setImageResource(R.mipmap.ic_address_press)
                        isDefaultVaule = "1"
                    }
                    tvSave.isEnabled = !isDefaultVaule.equals(delAddress?.is_default)
                })
        //获取此页信息
        mPresenter?.let {
            it.getDeliveryAddress(id)
        }
    }


    /**
     * @description ：修改成功回调并且关闭本页
     * @lastEditor  ：张利超
     * @version     ：v1.0 ,2019/11/1
     * @param null
     * @return      ：
     * @deprecated  ： false
     */
    override fun returnChangeSuccess() {
        killMyself()
    }

    /**
     * @description ：隐藏输入虚拟键盘
     * @lastEditor  ：张利超
     * @version     ：v1.0 ,2019/11/1
     * @param null
     * @return      ：
     * @deprecated  ： false
     */
    fun hideInput() {
        val view = this.getCurrentFocus()
        if (view != null) {
            val inputMethodManager = this.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view!!.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }


    /**
     * @description ：获取城市JsonList是从本地文件读取的值
     * @lastEditor  ：张利超
     * @version     ：v1.0 ,2019/11/1
     * @param null
     * @return      ：
     * @deprecated  ： false
     */
    private fun parseCityList() {


        Thread(object : Runnable {

            private var areaName1 = ArrayList<List<String>>()

            override fun run() {
                try {
                    val inputstrem = assets.open("city.txt")
                    val size = inputstrem.available()
                    val buffer = ByteArray(size)
                    inputstrem.read(buffer)
                    inputstrem.close()
                    provinces = Gson().fromJson(String(buffer, Charset.forName("UTF-8")), object : TypeToken<List<MyProvince>>() {

                    }.type)

                    for (province in provinces) {
                        provinceNames.add(province.name)
                        val citys = province.city
                        val cityName = ArrayList<String>()

                        for (city in citys) {
                            cityName.add(city.name)
                            val area = city.area
                            val areaName = ArrayList<String>()
                            areaName.addAll(area)
                            areaName1.add(areaName)
                        }
                        cityNames.add(cityName)
                        areaNames.add(areaName1)
                    }

                } catch (e: Exception) {
                    runOnUiThread { ToastUtils.makeText(this@ChangeDeliveryAddressActivity, "获取城市列表失败") }
                }

            }
        }).start()
    }


    /**
     * @description ：城市选择器弹窗初始化
     * @lastEditor  ：张利超
     * @version     ：v1.0 ,2019/11/1
     * @param null
     * @return      ：
     * @deprecated  ： false
     */
    private fun showCityPicker() {
        val options = OptionsPickerBuilder(this, OnOptionsSelectListener { options1, options2, options3, v ->
            val str = provinceNames[options1] + " " + cityNames[options1][options2] + " " + areaNames[options1][options2][options3]
            provinceString = provinceNames[options1]
            cityNameString = cityNames[options1][options2]
            areaNameString = areaNames[options1][options2][options3]
            etDeliveryAddress.setText(str)
        }).isDialog(false)
                .setCancelColor(ContextCompat.getColor(this, R.color.color_9A9A9A))
                .setSubmitColor(ContextCompat.getColor(this, R.color.colorAccent)).setOutSideCancelable(false)
                .build<Any>()
        options.setPicker(provinceNames as List<Any>?, cityNames as List<MutableList<Any>>?, areaNames as List<MutableList<MutableList<Any>>>?)
        options.show()
    }

    /**
     * @description ：返回本页面信息相关
     * @lastEditor  ：张利超
     * @version     ：v1.0 ,2019/11/1
     * @param null
     * @return      ：
     * @deprecated  ： false
     */
    override fun returnDelivery(deliveryAddress: DeliveryAddress) {
        delAddress = deliveryAddress
        etDeliveryName.setText(deliveryAddress.name)
        etDeliveryPhone.setText(deliveryAddress.mobile)
        etDeliveryAddress.setText(deliveryAddress.province + " " + deliveryAddress.city + " " + deliveryAddress.area)
        etDeliveryArea.setText(deliveryAddress.address)
        //is_default：1为默认地址，其他则不是
        if (deliveryAddress.is_default.equals("1")) {
            ivCheckDefault.setImageResource(R.mipmap.ic_address_press)
            isDefaultVaule = "1"
        } else {
            ivCheckDefault.setImageResource(R.mipmap.ic_address_normal)
            isDefaultVaule = "0"
        }
    }

    //    保存按钮禁用
    override fun buttonEnableFalse() {
        tvSave.isEnabled = false
    }


    /**
     * @description ：返回键监听弹窗 对返回键进行拦截如果此页面已进行编辑了那么需要弹出对话框
     * @lastEditor  ：张利超
     * @version     ：v1.0 ,2019/11/1
     * @param null
     * @return      ：
     * @deprecated  ： false
     */
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (KeyEvent.KEYCODE_BACK == event?.keyCode) {
            if (delAddress != null) {
                val isChange = delAddress?.name != etDeliveryName.text.toString().trim() || delAddress?.mobile != etDeliveryPhone.text.toString().trim()
                        || (delAddress?.province + delAddress?.city + delAddress?.area) != etDeliveryAddress.text.toString().trim().replace(" ", "")
                        || delAddress?.address != etDeliveryArea.text.toString().trim() || delAddress?.is_default != isDefaultVaule
                if (isChange) {
                    baseDialog?.let {
                        it.show()
                    }
                } else {
                    killMyself()
                }
            } else {
                killMyself()
            }
        }

        return super.onKeyDown(keyCode, event)
    }

    /**
     * @description ：返回拦截弹窗
     * @lastEditor  ：张利超
     * @version     ：v1.0 ,2019/11/1
     * @param null
     * @return      ：
     * @deprecated  ： false
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
