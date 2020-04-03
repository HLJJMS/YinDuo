package com.yinduowang.installment.mvp.ui.activity

import android.content.Intent
import android.os.Bundle
import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils
import com.yinduowang.installment.R
import com.yinduowang.installment.di.component.DaggerShopConfirmReceivingGoodsComponent
import com.yinduowang.installment.di.module.ShopConfirmReceivingGoodsModule
import com.yinduowang.installment.mvp.contract.ShopConfirmReceivingGoodsContract
import com.yinduowang.installment.mvp.presenter.ShopConfirmReceivingGoodsPresenter
import kotlinx.android.synthetic.main.activity_shop_confirm_receiving_goods.*


/**
 * ================================================
 * Description:请添加收货地址
 * <p>
 * Created by MVPArmsTemplate on 08/07/2019 19:49
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */

class ShopConfirmReceivingGoodsActivity : BaseActivity<ShopConfirmReceivingGoodsPresenter>(), ShopConfirmReceivingGoodsContract.View {

    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerShopConfirmReceivingGoodsComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .shopConfirmReceivingGoodsModule(ShopConfirmReceivingGoodsModule(this))
                .build()
                .inject(this)
    }


    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_shop_confirm_receiving_goods //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }


    override fun initData(savedInstanceState: Bundle?) {
        titleBar.showTitleAndBack(R.string.confirm_receiving_good)
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
