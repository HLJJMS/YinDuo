package com.yinduowang.installment.mvp.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils
import com.yinduowang.installment.R
import com.yinduowang.installment.di.component.DaggerRegisterSuccessComponent
import com.yinduowang.installment.di.module.RegisterSuccessModule
import com.yinduowang.installment.mvp.contract.RegisterSuccessContract
import com.yinduowang.installment.mvp.model.event.ShowHomeShopEvent
import com.yinduowang.installment.mvp.presenter.RegisterSuccessPresenter
import kotlinx.android.synthetic.main.activity_register_success.*
import org.simple.eventbus.EventBus


/**
 * ================================================
 * Description:注册成功页面 接口控制是否显示
 * <p>
 * Created by MVPArmsTemplate on 09/30/2019 08:45
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
/**
 * 如果没presenter
 * 你可以这样写
 *
 * @ActivityScope(請注意命名空間) class NullObjectPresenterByActivity
 * @Inject constructor() : IPresenter {
 * override fun onStart() {
 * }
 *
 * override fun onDestroy() {
 * }
 * }
 */
class RegisterSuccessActivity : BaseActivity<RegisterSuccessPresenter>(), RegisterSuccessContract.View {
    // 是否是vip 0 vip 1非vip
    var vipStatus = ""
    var vipText = ""
    var isRegister = false

    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerRegisterSuccessComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .registerSuccessModule(RegisterSuccessModule(this))
                .build()
                .inject(this)
    }


    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_register_success //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }


    override fun initData(savedInstanceState: Bundle?) {

        titleBar.showTitleAndBack("注册成功")
//        titleBar.showBlackBack()
        vipStatus = intent.getStringExtra("vipStatus")
        vipText = intent.getStringExtra("vipText")
        isRegister = intent.getBooleanExtra("isRegister", false)
        if (vipStatus == "0") {
            clVip.visibility = View.VISIBLE
            tvViewHint.text = vipText
        } else {
            clVip.visibility = View.GONE
        }
        mPresenter?.closeVipPop()
    }

    override fun onBackPressed() {
        killMyself()
    }

    /**
     * 跳转主页
     * */
    private fun goMainView() {
//        if (isRegister) {
//            // 跳转首页并展示商城
//            EventBus.getDefault().post(ShowHomeShopEvent())
//            val intent = Intent(this, MainActivity::class.java)
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
//            startActivity(intent)
//        }
        killMyself()
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
