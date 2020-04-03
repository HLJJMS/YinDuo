package com.yinduowang.installment.mvp.ui.activity

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import com.blankj.utilcode.util.SPUtils
import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils
import com.jess.arms.utils.PermissionUtil
import com.tbruyelle.rxpermissions2.RxPermissions
import com.umeng.analytics.MobclickAgent
import com.yinduowang.installment.R
import com.yinduowang.installment.app.constant.Constant
import com.yinduowang.installment.app.constant.SPConstant
import com.yinduowang.installment.app.utils.gosetting.PermissionSetting
import com.yinduowang.installment.di.component.DaggerSplashComponent
import com.yinduowang.installment.di.module.SplashModule
import com.yinduowang.installment.mvp.contract.SplashContract
import com.yinduowang.installment.mvp.model.event.NeedUpgradeEvent
import com.yinduowang.installment.mvp.model.event.UndoUpgradeEvent
import com.yinduowang.installment.mvp.presenter.SplashPresenter
import com.yinduowang.installment.mvp.ui.service.UploadService
import org.simple.eventbus.Subscriber


/**
 * ================================================
 * Description:欢迎页
 * <p>
 * Created by MVPArmsTemplate on 10/11/2019 16:00
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
class SplashActivity : BaseActivity<SplashPresenter>(), SplashContract.View {

    var permissions = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE)

    var handler: Handler? = null
    var runnable: Runnable? = null
    var waitToFinish = false
    var getTestStatus = false

    @Subscriber
    fun onMessageEvent(event: NeedUpgradeEvent) {
        if (handler != null && runnable != null) {
            handler!!.removeCallbacks(runnable)
        }
    }

    @Subscriber
    fun onMessageEvent(event: UndoUpgradeEvent) {
        if (handler != null && runnable != null) {
            handler!!.postDelayed(runnable, 500)
        }
    }

    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerSplashComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .splashModule(SplashModule(this))
                .build()
                .inject(this)
    }


    override fun initView(savedInstanceState: Bundle?): Int {
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        return R.layout.activity_splash //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }


    override fun initData(savedInstanceState: Bundle?) {
        if (intent.flags and Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT != 0) {
            finish()
            return
        }

        requestPermission()
    }

    private fun initView() {
        UploadService.uploadUserInfo(this, UploadService.CHECK_VERSION)

        // 设置初始化显示页面
        if (!SPUtils.getInstance().getBoolean(SPConstant.INSTALL_APP_FROM_PHP_TO_JAVA)) {
            SPUtils.getInstance().clear()
            SPUtils.getInstance().put(SPConstant.INSTALL_APP_FROM_PHP_TO_JAVA, true)
        }
        handler = Handler()
        runnable = Runnable {
            waitToFinish = true
            if (getTestStatus) {
                finishSplash()
            }
        }
        handler!!.postDelayed(runnable, (2 * 1000).toLong())
    }

    private fun finishSplash() {
        if (SPUtils.getInstance().getBoolean(SPConstant.KEY_IS_NOT_FIRST_RUN)) {
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
        } else {
            startActivity(Intent(this@SplashActivity, IntroductionActivity::class.java))
        }
        this@SplashActivity.finish()
    }

    private fun requestPermission() {
        PermissionUtil.requestPermission(
                object : PermissionUtil.RequestPermission {
                    override fun onRequestPermissionSuccess() {
                        requestPermissionTwo()
                    }

                    override fun onRequestPermissionFailure(permissions: List<String>) {
                        requestPermissionTwo()
                    }

                    override fun onRequestPermissionFailureWithAskNeverAgain(permissions: List<String>) {
                        requestPermissionTwo()
                    }
                },
                RxPermissions(this),
                mPresenter?.mErrorHandler,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION)
    }

    private fun requestPermissionTwo() {
        PermissionUtil.requestPermission(
                object : PermissionUtil.RequestPermission {
                    override fun onRequestPermissionSuccess() {
                        requestPermissionThree()
                    }

                    override fun onRequestPermissionFailure(permissions: List<String>) {
                        requestPermissionTwo()
                    }

                    override fun onRequestPermissionFailureWithAskNeverAgain(permissions: List<String>) {
                        PermissionSetting.toPermissionSetting(this@SplashActivity)
                    }
                },
                RxPermissions(this),
                mPresenter?.mErrorHandler,
                Manifest.permission.READ_PHONE_STATE)
    }

    private fun requestPermissionThree() {
        PermissionUtil.requestPermission(
                object : PermissionUtil.RequestPermission {
                    override fun onRequestPermissionSuccess() {
                        mPresenter?.isTestVersion()
                        initView()
                    }

                    override fun onRequestPermissionFailure(permissions: List<String>) {
                        requestPermissionThree()
                    }

                    override fun onRequestPermissionFailureWithAskNeverAgain(permissions: List<String>) {
                        PermissionSetting.toPermissionSetting(this@SplashActivity)
                    }
                },
                RxPermissions(this),
                mPresenter?.mErrorHandler,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
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

    override fun onResume() {
        super.onResume()
        MobclickAgent.onPageStart("欢迎") //统计页面(仅有Activity的应用中SDK自动调用，不需要单独写。"SplashScreen"为页面名称，可自定义)
        MobclickAgent.onResume(this)
    }

    public override fun onPause() {
        super.onPause()
        MobclickAgent.onPageEnd("欢迎") // （仅有Activity的应用中SDK自动调用，不需要单独写）保证 onPageEnd 在onPause 之前调用,因为 onPause 中会保存信息。"SplashScreen"为页面名称，可自定义
        MobclickAgent.onPause(this)
    }

    override fun isTestVersion(isTestVersion: Boolean) {
        SPUtils.getInstance().put(SPConstant.IS_TEST_VERSION, isTestVersion)
        getTestStatus = true
        if (waitToFinish) {
            finishSplash()
        }
    }
}
