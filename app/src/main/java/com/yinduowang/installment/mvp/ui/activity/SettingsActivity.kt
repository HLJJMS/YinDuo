package com.yinduowang.installment.mvp.ui.activity

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.blankj.utilcode.util.SPUtils
import com.jakewharton.rxbinding3.view.clicks
import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils
import com.yinduowang.installment.R
import com.yinduowang.installment.app.constant.SPConstant
import com.yinduowang.installment.app.utils.ClearLoginDataUtil
import com.yinduowang.installment.app.utils.LoadingUtils
import com.yinduowang.installment.app.widget.BaseDialog
import com.yinduowang.installment.di.component.DaggerSettingComponent
import com.yinduowang.installment.di.module.SettingModule
import com.yinduowang.installment.mvp.contract.SettingContract
import com.yinduowang.installment.mvp.model.entity.PerfectEntity
import com.yinduowang.installment.mvp.model.event.HomeBottomLoginEvent
import com.yinduowang.installment.mvp.model.event.ShowHomeShopEvent
import com.yinduowang.installment.mvp.presenter.NewSettingPresenter
import kotlinx.android.synthetic.main.activity_pay_back_record.*
import kotlinx.android.synthetic.main.activity_setting.*
import org.simple.eventbus.EventBus
import java.util.concurrent.TimeUnit


/**
 * ================================================
 * Description:设置
 * <p>
 * Created by MVPArmsTemplate on 07/23/2019 10:50
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
class SettingsActivity : BaseActivity<NewSettingPresenter>(), SettingContract.View {


    var payPswType: String = ""
    //回调修类型
    override fun returnPayPswIsSet(type: String) {
        payPswType = type
        if (payPswType.equals("1")) {
            tvSetPswType.text = "修改支付密码"
        } else {
            tvSetPswType.text = "设置支付密码"
        }
    }

    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerSettingComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .settingModule(SettingModule(this))
                .build()
                .inject(this)
    }


    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_setting //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    override fun onResume() {
        super.onResume()
        mPresenter?.let { it.getVaildatePswSet() }
    }


    override fun initData(savedInstanceState: Bundle?) {
        if (SPUtils.getInstance().getBoolean(SPConstant.IS_TEST_VERSION)) {
            layoutCollectionComplains.visibility = View.GONE
            vTop.visibility = View.GONE
        }
        titlebar.showTitleAndBack("设置")
        //意见反馈
        layoutFeedback.clicks()
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe {
                    startActivity(Intent(this, FeedBackActivity::class.java))
                }
        //催收投诉
        layoutCollectionComplains.clicks()
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe {
                    startActivity(Intent(this, CollectionActivity::class.java))
                }
        //收货地址
        layoutDeliveryAddress.clicks()
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe {
                    startActivity(Intent(this, DeliveryAddressActivity::class.java))
                }
        //修改登录密码
        layoutChangeLoginPsw.clicks()
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe {
                    startActivity(Intent(this, ChangePasswordActivity::class.java))
                }
        //设置交易密码
        layoutSetPayPsw.clicks()
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe {
                    if (!TextUtils.equals(payPswType, "")) {
                        mPresenter?.perfect()
                    }
                }
        //退出按键
        tvLoginExit.clicks()
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe {
                    logoutConfirm()
                }
    }

    /*********************
     * 退出确认
     */
    private fun logoutConfirm() {
        BaseDialog(this)
                .builder()
                .setCancelable(false)
                .setTitle("您确认退出登录？")
                .setBtnLeft("取消")
                .setBtnRight("退出", object : BaseDialog.BaseDialogBuilder.BtnClickListener {
                    override fun onClick(dialog: BaseDialog) {
                        mPresenter?.let { it.loginOut() }
                    }
                })
                .create()
                .show()
    }

    override fun showLoading() {
        LoadingUtils.show(SettingsActivity@ this)
    }

    override fun hideLoading() {
        LoadingUtils.dismiss(SettingsActivity@ this)
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

    override fun perfect(perfectEntity: PerfectEntity) {
//        0是认证了
        if (perfectEntity.userInfoStatus.equals("0")) {
            val intent = Intent(this, SetTradePwdActivity::class.java)
            intent.putExtra("payPswType", payPswType)
            startActivity(intent)
        } else {

            showDialog()
        }

    }


    fun showDialog() {
        BaseDialog(this)
                .builder()
                .setCancelable(false)
                .setTitle("请您先完成个人信息认证")
                .setBtnRight("确定")
                .create()
                .show()
    }


    /**
     * 退出
     */
    override fun logOut() {
        EventBus.getDefault().post(ShowHomeShopEvent())
        EventBus.getDefault().post(HomeBottomLoginEvent(false))
        SPUtils.getInstance().put(SPConstant.SHARE_TAG_USER_LOGIN_TO_SIGNOUT, true)
        ClearLoginDataUtil.clearLoginStatus()
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.putExtra("goMy", true)
        startActivity(intent)
    }
}
