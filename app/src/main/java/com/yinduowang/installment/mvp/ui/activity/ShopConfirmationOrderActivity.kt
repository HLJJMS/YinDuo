package com.yinduowang.installment.mvp.ui.activity


import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.text.TextUtils
import android.view.KeyEvent
import android.view.View
import android.view.View.GONE
import androidx.core.content.ContextCompat
import com.google.gson.Gson
import com.jakewharton.rxbinding3.view.clicks
import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils
import com.qmuiteam.qmui.util.QMUIDisplayHelper
import com.yinduowang.installment.R
import com.yinduowang.installment.app.utils.LoadImageUtils
import com.yinduowang.installment.app.utils.LoadingUtils
import com.yinduowang.installment.app.widget.BaseDialog
import com.yinduowang.installment.di.component.DaggerShopConfirmationOrderComponent
import com.yinduowang.installment.di.module.ShopConfirmationOrderModule
import com.yinduowang.installment.mvp.contract.ShopConfirmationOrderContract
import com.yinduowang.installment.mvp.model.entity.ConfirmationOrder
import com.yinduowang.installment.mvp.model.entity.CreateOrderEntity
import com.yinduowang.installment.mvp.model.entity.DeliveryAddress
import com.yinduowang.installment.mvp.model.entity.OrderFee
import com.yinduowang.installment.mvp.presenter.ShopConfirmationOrderPresenter
import kotlinx.android.synthetic.main.activity_shop_confirmation_order.*
import timber.log.Timber
import java.util.concurrent.TimeUnit

/**
 * Description：商品订单详情确认
 * Author：田羽衡
 * Version：<v1.0，2019/11/1 >
 * LastEditTime：2019/11/1
 * LastEditors:
 * Deprecated： false
 */

class ShopConfirmationOrderActivity : BaseActivity<ShopConfirmationOrderPresenter>(), ShopConfirmationOrderContract.View {
    val REQUEST_CODE_ADDRESS = 1001
    val RESULT_CODE_ADDRESS = 1002
    var shopType = ""
    var addressId = ""
    var bean = CreateOrderEntity()
    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerShopConfirmationOrderComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .shopConfirmationOrderModule(ShopConfirmationOrderModule(this))
                .build()
                .inject(this)
    }


    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_shop_confirmation_order //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }


    override fun initData(savedInstanceState: Bundle?) {

        initView()
        getData()

    }

    fun initView() {
        titleBar.showTitleAndBack(resources.getString(R.string.confirmation_order), View.OnClickListener {
            backShopDetail()
        })
        vBg.clicks()
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe {

                    var intent = Intent(this@ShopConfirmationOrderActivity, DeliveryAddressActivity::class.java)
                    intent.putExtra("chooseAddress", true)
                    startActivityForResult(intent, CommWebViewActivity.REQUEST_CODE_ADDRESS)

                }
        tvOk.clicks()
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe {
                    judgeGoActivity()
                }
    }


    /**
     * @Description:  获取H5数据
     * @Author: ${田羽衡}
     * @Date:20191030
     * @Version: 1.1
     * @LastEditors:田羽衡
     * @LastEditTime:20191030
     * @Deprecated: false
     */
    fun getData() {
        val gson = Gson()
        bean = gson.fromJson(intent.getStringExtra("param"), CreateOrderEntity::class.java)
//        安全隐患（url自行拼接）
        LoadImageUtils.showImage(this, bean.thumb.toString(), ivImg, R.color.color_FAFAFA)
        tvShopName.text = bean.name
        if (!bean.af_name.equals("")) {
            shopType = bean.af_name.toString()
        }
        if (!bean.as_name.equals("")) {
            shopType = shopType + " " + bean.as_name.toString()
        }

        tvType.text = shopType
        tvNum.text = "×" + bean.num
        tvRmbMin.text = bean.price?.removeRange(0, bean.price.toString().length - 2)
        tvRmb.text = bean.price?.subSequence(0, bean.price.toString().length - 2)
        tvMonthRmb.text = bean.min_money?.subSequence(0, bean.min_money.toString().length - 3)
        tvMonthRmbMin.text = bean.min_money?.removeRange(0, bean.min_money.toString().length - 2)
        tvRmbTotal.text = "订单总额 ¥" + bean.total_num
        tvShopMaxTxt.text = "¥ " + bean.total_num
        tvOrderMaxTxt.text = "¥ " + bean.order_num
        if (bean.fee.equals("免邮")) {
            tvFareTxt.text = bean.fee
        } else {
            tvFareTxt.text = "¥ " + bean.fee
        }
        addressFail()
        if (!bean.addressId.toString().equals("")) {
            addressId = bean.addressId.toString()
        } else {
            showDialog()
        }

    }

    override fun onResume() {
        super.onResume()
        if(!addressId.equals("")){
            getAddressDetail()
        }

    }
    /**
     * Description 拦截返回按钮
     * Author  田羽衡
     * Version  <v1.0，2019/11/4 15:50>
     * params
     * return
     * LastEditTime  2019/11/4 15:50
     * Deprecated   false
     */

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            backShopDetail()
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


    /*
    * 描述：〈库存不足弹窗(1.1之后启用)〉
    * 修改⼈：〈田羽衡〉
    * 版本号：<1.1，20191022>
    * 参数：〈参数名：null〉
    * 修改时间：20191022
    * 弃⽤： <true>
    */
    override fun showDialogInsufficientStock() {
        BaseDialog(this)
                .builder()
                .setCancelable(false)
                .setTitle("商品已售罄")
                .setTitlePaddingTop(22f)
                .setTitlePaddingBottom(26f)
                .setTitleLineSpacingExtra(25f)
                .setBtnLeft("确定", 16f, ContextCompat.getColor(this, R.color.color_365AF7), Typeface.NORMAL, object : BaseDialog.BaseDialogBuilder.BtnClickListener {
                    override fun onClick(dialog: BaseDialog) {
                        finish()
                        val intent = Intent(this@ShopConfirmationOrderActivity, ShopAllOrderActivity::class.java)
                        intent.putExtra("intentType", "ShopConfirmationOrderActivity")
                        intent.putExtra("orderType", "99")
                        startActivity(intent)
                    }

                })
                .create()
                .show()
    }

    /**
     * @Description:  获取地址信息数据成功
     * @Author: ${田羽衡}
     * @Date:20191030
     * @Version: 1.1
     * @LastEditors:田羽衡
     * @LastEditTime:20191030
     * @Deprecated: false
     */
    override fun addressSuccess(bean: DeliveryAddress) {
        vBg.setPadding(0, QMUIDisplayHelper.dp2px(this, 22), 0, QMUIDisplayHelper.dp2px(this, 28))
        tvCity.visibility = View.VISIBLE
        tvName.visibility = View.VISIBLE
        tvPhone.visibility = View.VISIBLE
        tvAddAddress.text = bean.address
        tvCity.text = bean.province + bean.city + bean.area
        tvName.text = bean.name
        tvPhone.text = phoneNumberPsw(bean.mobile)
        addressId = bean.id
        if (bean.is_default.equals("1")) {
            tvIndex.visibility = View.VISIBLE
            tvCity.setPadding(QMUIDisplayHelper.dp2px(this, 49), 0, 0, 0)
        } else {
            tvIndex.visibility = GONE
            tvCity.setPadding(QMUIDisplayHelper.dp2px(this, 18), 0, 0, 0)
        }
        mPresenter?.getGoBuyMessage(this.bean.price?.replace(".", "")?.replace(",", "").toString(), this.bean.g_id.toString(), this.bean.af_id.toString(), this.bean.as_id.toString(), this.bean.num.toString(), addressId,this.bean.max_cycle.toString())
    }

    /**
     * @Description:  获取地址信息数据失败
     * @Author: ${田羽衡}
     * @Date:20191030
     * @Version: 1.1
     * @LastEditors:田羽衡
     * @LastEditTime:20191030
     * @Deprecated: false
     */

    override fun addressFail() {
        addressId = ""
        tvAddAddress.text = "请填写收货地址"
        tvIndex.visibility = GONE
        tvCity.visibility = GONE
        tvName.visibility = GONE
        tvPhone.visibility = GONE
        vBg.setPadding(0, QMUIDisplayHelper.dp2px(this, 26), 0, QMUIDisplayHelper.dp2px(this, 30))
    }


    /**
     * @Description:  提交订单效验通过，跳转至支付方式
     * @Author: ${田羽衡}
     * @Date:20191030
     * @Version: 1.1
     * @LastEditors:田羽衡
     * @LastEditTime:20191030
     * @Deprecated: false
     */
    override fun confirmationOrder(code: String) {
        Timber.i(code);
        var intent = Intent(this, PaymentTypeActivity::class.java)
        intent.putExtra("o_id", code)
        intent.putExtra("rmb", bean.order_num.toString())
        intent.putExtra("intentType", "ShopConfirmationOrderActivity")
        startActivity(intent)
        finish()
    }

    /**
     * @Description:  添加收货地址弹窗
     * @Author: ${田羽衡}
     * @Date:20191030
     * @Version: 1.1
     * @LastEditors:田羽衡
     * @LastEditTime:20191030
     * @Deprecated: false
     */
    fun showDialog() {
        BaseDialog(this)
                .builder()
                .setCancelable(false)
                .setTitle("请添加收货地址")
                .setBtnLeft("暂不添加")
                .setBtnRight("去添加", object : BaseDialog.BaseDialogBuilder.BtnClickListener {
                    override fun onClick(dialog: BaseDialog) {
                        var intent = Intent(this@ShopConfirmationOrderActivity, DeliveryAddressActivity::class.java)
                        intent.putExtra("chooseAddress", true)
                        intent.putExtra("addressId",addressId)
                        startActivityForResult(intent, CommWebViewActivity.REQUEST_CODE_ADDRESS)
                    }
                })
                .create()
                .show()
    }

    /**
     * @Description:  订单提交异常返回商品详情
     * @Author: ${田羽衡}
     * @Date:20191030
     * @Version: 1.1
     * @LastEditors:田羽衡
     * @LastEditTime:20191030
     * @Deprecated: false
     */
    override fun backShopDetail() {
        finish()
    }


    /**
     * @Description:   获取地址详情
     * @Author: ${田羽衡}
     * @Date:20191030
     * @Version: 1.1
     * @LastEditors:田羽衡
     * @LastEditTime:20191030
     * @Deprecated: false
     */
    fun getAddressDetail() {
        mPresenter?.getAddressDetail(addressId)
    }


    /**
     * @Description: 判断地址不为空，请求提交订单接口
     * @Author: ${田羽衡}
     * @Date:20191030
     * @Version: 1.1
     * @LastEditors:田羽衡
     * @LastEditTime:20191030
     * @Deprecated: false
     */
    fun judgeGoActivity() {
//        是否选择地址
        if (TextUtils.equals(addressId, "")) {
            showDialog()
        } else {
            mPresenter?.goBuy(bean.price?.replace(".", "")?.replace(",", "").toString(), bean.g_id.toString(), bean.af_id.toString(), bean.as_id.toString(), bean.num.toString(), addressId)
        }
    }

    /**
     * @Description:从地址页返回的时候带回数据
     * @Author: ${田羽衡}
     * @Date:20191030
     * @Version: 1.1
     * @LastEditors:田羽衡
     * @LastEditTime:20191030
     * @Deprecated: false
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (REQUEST_CODE_ADDRESS == requestCode && RESULT_CODE_ADDRESS == resultCode) {
            if (data != null) {
                addressId = data.getStringExtra("addressId")
            }
        }
    }

    /**
     * @Description: 手机号加星
     * @Author: ${田羽衡}
     * @Date: 20191030
     * @Version:1.1
     * @LastEditors:田羽衡
     * @LastEditTime:20191030
     * @Deprecated: false
     */
    fun phoneNumberPsw(number: String): String {
        return number.substring(0, 3) + "****" + number.substring(7, number.length);
    }

    /**
     * Description 获取订单信息运费接口成功
     * Author  田羽衡
     * Version  <v1.0，2019/11/5 15:34>
     * params
     * return
     * LastEditTime  2019/11/5 15:34
     * Deprecated   false
     */
    override fun getGoBuyMessageSuccess(bean: OrderFee) {
        if (bean.fee.equals("免邮")) {
            tvFareTxt.text = bean.fee
        } else {
            tvFareTxt.text = "¥ " + bean.fee
        }
        tvMonthRmb.text =   bean.month_money?.subSequence(0,   bean.month_money.toString().length - 3)
        tvMonthRmbMin.text =   bean.month_money?.removeRange(0,   bean.month_money.toString().length - 2)
    }


}



