package com.yinduowang.installment.mvp.ui.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentTransaction
import com.blankj.utilcode.util.SPUtils
import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils
import com.qmuiteam.qmui.span.QMUITouchableSpan
import com.qmuiteam.qmui.widget.dialog.QMUIDialog
import com.yinduowang.installment.R
import com.yinduowang.installment.app.constant.Api
import com.yinduowang.installment.app.constant.SPConstant
import com.yinduowang.installment.app.utils.ToastUtils
import com.yinduowang.installment.app.widget.HomeBottomView
import com.yinduowang.installment.di.component.DaggerMainComponent
import com.yinduowang.installment.di.module.MainModule
import com.yinduowang.installment.mvp.contract.MainContract
import com.yinduowang.installment.mvp.model.event.ShowHomeShopEvent
import com.yinduowang.installment.mvp.model.event.ShowMinePage
import com.yinduowang.installment.mvp.presenter.MainPresenter
import com.yinduowang.installment.mvp.ui.fragment.DiscoverFragment
import com.yinduowang.installment.mvp.ui.fragment.LoanFragment
import com.yinduowang.installment.mvp.ui.fragment.MineFragment
import com.yinduowang.installment.mvp.ui.fragment.ShoppingMallFragment
import kotlinx.android.synthetic.main.activity_main.*
import org.simple.eventbus.Subscriber
import java.util.*


/**
 * ================================================
 * Description:主页
 * <p>
 * Created by MVPArmsTemplate on 07/16/2019 15:50
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
class MainActivity : BaseActivity<MainPresenter>(), MainContract.View {


    companion object {
        const val AGREE_TO_PRIVACY_POLICY_REQUEST = 4267
        const val AGREE_TO_PRIVACY_POLICY_RESULT = 4268
        val TAG_FRAGMENT = arrayOf("OneFragment", "TwoFragment", "ThreeFragment")
        val MENU_NAMES = arrayOf("商城", "借钱", "我的")
        val MENU_NAMES_TEST_VERSION = arrayOf("商城", "发现", "我的")
    }

    private var shoppingMallFragment: ShoppingMallFragment? = null
    private var loanFragment: LoanFragment? = null
    private var discoverFragment: DiscoverFragment? = null
    private var mineFragment: MineFragment? = null

    // 记录是否有首次按键
    private var mBackKeyPressed = false

    lateinit var privacyPolicyContentMsg: String
    lateinit var style: SpannableStringBuilder

    private lateinit var qmuiDialog: QMUIDialog
    private var bottomHeight = 0

    @Subscriber
    fun onMessageEvent(event: ShowHomeShopEvent) {
        homeBottomView.status = HomeBottomView.TYPE_ONE
    }

    @Subscriber
    fun onGotoMineEvent(event: ShowMinePage) {
        homeBottomView.status = HomeBottomView.TYPE_THREE
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        // 开启一个Fragment事务管理器
        val fragmentManager = supportFragmentManager
        if (savedInstanceState != null) {
            shoppingMallFragment = fragmentManager.findFragmentByTag(TAG_FRAGMENT[0]) as ShoppingMallFragment
            loanFragment = fragmentManager.findFragmentByTag(TAG_FRAGMENT[1]) as LoanFragment
            mineFragment = fragmentManager.findFragmentByTag(TAG_FRAGMENT[2]) as MineFragment
        }
        super.onCreate(savedInstanceState)
    }

    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerMainComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .mainModule(MainModule(this))
                .build()
                .inject(this)
    }

    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_main //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }


    override fun initData(savedInstanceState: Bundle?) {
        // 判断是否是提测版本
        if (SPUtils.getInstance().getBoolean(SPConstant.IS_TEST_VERSION)) {
            homeBottomView.setMenuNames(MENU_NAMES_TEST_VERSION)
        } else {
            homeBottomView.setMenuNames(MENU_NAMES)
        }
        homeBottomView.setHomeBottomBtnChangeListener(object : HomeBottomView.HomeBottomBtnChangeListener {
            override fun change(newStatus: Int, oldStatus: Int): Boolean {
                if (newStatus == HomeBottomView.TYPE_ONE) {
                    showOnePage()
                } else if (newStatus == HomeBottomView.TYPE_TWO) {
                    showTwoPage()
                } else if (newStatus == HomeBottomView.TYPE_THREE) {
                    // 判断是否登录 如果登录了 则展示我的页面 没有登录跳转登录
                    if (SPUtils.getInstance().getString(SPConstant.TOKEN).isNotEmpty()) {
                        mPresenter?.isAvailable()
                    } else {
                        startActivity(Intent(this@MainActivity, LoginActivity::class.java))
                        return false
                    }
                }
                return true
            }
        })


        var isAgree = SPUtils.getInstance().getBoolean(SPConstant.AGREE_TO_PRIVACY_POLICY)
        initAgreeMsg(isAgree)
        showAgree(isAgree)
        showLoanFragment()
        //测量底部导航栏高度
        var w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        var h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        homeBottomView.measure(w, h)
        bottomHeight = homeBottomView.measuredHeight
    }


    /**
     * 判断是否进入登录页
     */
    private fun isGoToLogin(intent: Intent) {
        if (intent.getBooleanExtra("goLogin", false)) {
            ToastUtils.makeText(this, intent.getStringExtra("goLoginMsg"))
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        /**
         * 判断是否进入商城页
         */
        try {
            if (intent != null) {
                isGoToLogin(intent)
                if (null != intent.getStringExtra("goShop")) {
                    homeBottomView.status = HomeBottomView.TYPE_ONE
                } else if (intent.getBooleanExtra("goMy", false)) {
                    homeBottomView.status = HomeBottomView.TYPE_THREE
                }

            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 初始化意思协议弹窗文本内容及点击事件
     * */
    private fun initAgreeMsg(isAgree: Boolean) {
        if (!isAgree) {
            privacyPolicyContentMsg = resources.getString(R.string.privacy_policy_content_msg)
            var privacyPolicy = resources.getString(R.string.privacy_policy)
            var index = privacyPolicyContentMsg.indexOf(privacyPolicy)
            style = SpannableStringBuilder(privacyPolicyContentMsg)
            style.setSpan(object : QMUITouchableSpan(ContextCompat.getColor(this, R.color.colorAccent), ContextCompat.getColor(this, R.color.colorPrimary), Color.WHITE, Color.WHITE) {
                override fun onSpanClick(widget: View) {
                    qmuiDialog.dismiss()
                    gotoActivity()
                }
            }, index, index + privacyPolicy.length, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
        }
    }

    /**
     * 展示第三页
     * */
    private fun showThreePage() {
        // 开启一个Fragment事务管理器
        val transaction = supportFragmentManager.beginTransaction()
        // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
        hideFragments(transaction)
        // 判断是否进行了初始化 如果进行了初始化则直接显示 没有初始化需要初始化在显示
        if (mineFragment != null) {
            transaction.show(mineFragment!!)
        } else {
            mineFragment = MineFragment.newInstance()
            transaction.add(R.id.mainContent, mineFragment!!, TAG_FRAGMENT[2])
        }
        transaction.commitAllowingStateLoss()
    }

    /**
     * 展示第二页
     * */
    private fun showTwoPage() {
        // 开启一个Fragment事务管理器
        val transaction = supportFragmentManager.beginTransaction()
        // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
        hideFragments(transaction)
        // 判断是否是提测版本
        if (SPUtils.getInstance().getBoolean(SPConstant.IS_TEST_VERSION)) {
            // 判断是否进行了初始化 如果进行了初始化则直接显示 没有初始化需要初始化在显示
            if (discoverFragment != null) {
                transaction.show(discoverFragment!!)
            } else {
                discoverFragment = DiscoverFragment.newInstance()
                transaction.add(R.id.mainContent, discoverFragment!!, TAG_FRAGMENT[1])
            }
        } else {
            // 判断是否进行了初始化 如果进行了初始化则直接显示 没有初始化需要初始化在显示
            if (loanFragment != null) {
                transaction.show(loanFragment!!)
            } else {
                loanFragment = LoanFragment.newInstance()
                transaction.add(R.id.mainContent, loanFragment!!, TAG_FRAGMENT[1])
            }
        }
        transaction.commitAllowingStateLoss()
    }

    /**
     * 展示第一页
     * */
    private fun showOnePage() {
        // 开启一个Fragment事务管理器
        val transaction = supportFragmentManager.beginTransaction()
        // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
        hideFragments(transaction)
        // 判断是否进行了初始化 如果进行了初始化则直接显示 没有初始化需要初始化在显示
        if (shoppingMallFragment != null) {
            transaction.show(shoppingMallFragment!!)
        } else {
            shoppingMallFragment = ShoppingMallFragment.newInstance()
            transaction.add(R.id.mainContent, shoppingMallFragment!!, TAG_FRAGMENT[0])
        }
        transaction.commitAllowingStateLoss()

//        测试生成token
//        var map = hashMapOf<String,String>()
//        map["token"] = "e365f4eefbf2879d04d57f7e2c8ddde0"
//        map["loanId"] = "1184"
//        map["uniqueCode"] = "1"
//        map["authCode"] = "1"
//        map["repayFund"] = "100"
//        map["channelType"] = "4"
//        var sign = SignUtils.getSign(map)
//        println(sign)
    }


    /**
     * 将所有的Fragment都置为隐藏状态。
     *
     * @param transaction 用于对Fragment执行操作的事务
     */
    private fun hideFragments(transaction: FragmentTransaction) {
        if (shoppingMallFragment != null) {
            transaction.hide(shoppingMallFragment!!)
        }
        if (loanFragment != null) {
            transaction.hide(loanFragment!!)
        }
        if (discoverFragment != null) {
            transaction.hide(discoverFragment!!)
        }
        if (mineFragment != null) {
            transaction.hide(mineFragment!!)
        }
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

    override fun onBackPressed() {
        if (!mBackKeyPressed) {
            ToastUtils.makeText(this, "再按一次退出程序")
            mBackKeyPressed = true
            //延时两秒，如果超出则擦错第一次按键记录
            Timer().schedule(object : TimerTask() {
                override fun run() {
                    mBackKeyPressed = false
                }
            }, 2000)
        } else {//退出程序
            ArmsUtils.exitApp()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // 打开隐私协议页面 是否点击了同意协议按钮
        if (requestCode == AGREE_TO_PRIVACY_POLICY_REQUEST && resultCode == AGREE_TO_PRIVACY_POLICY_RESULT) {
            var isAgree = false
            if (data != null) {
                isAgree = data.getBooleanExtra("showAgree", false)
            }

            showAgree(isAgree)
        }
    }

    /**
     * 判断是否展示隐私协议弹窗
     * */
    private fun showAgree(isAgree: Boolean) {
        if (isAgree) {
            // 设置初始化显示页面
//            mPresenter?.getAppUpdateInfo()
//            requestPermission()
        } else {
            if (::style.isInitialized) {
                qmuiDialog = QMUIDialog.MessageDialogBuilder(this@MainActivity)
                        .setTitle("隐私政策")
                        .setCancelable(false)
                        .setCanceledOnTouchOutside(false)
                        .setMessage(style)
                        .addAction("阅读") { dialog, index ->
                            gotoActivity()
                            dialog.dismiss()
                        }
                        .create(R.style.PrivacyPolicyDialog)
                qmuiDialog.show()
            } else {
                initAgreeMsg(isAgree)
                showAgree(isAgree)
            }
        }
    }

    /**
     * 跳转隐私协议授权页面
     * */
    private fun gotoActivity() {
        val intent = Intent(this, CommWebViewActivity::class.java)
        intent.putExtra(CommWebViewActivity.KEY_URL_NAME, Api.AGREEMENT_PRIVACY_PROTOCOL)
        intent.putExtra(CommWebViewActivity.KEY_JAVASCRIPT_TYPE, CommWebViewActivity.TYPE_JAVASCRIPT_JEL_LE_ME)
        intent.putExtra(CommWebViewActivity.TYPE_IS_SHOW_BOTTOM_BTN, true)
        intent.putExtra(CommWebViewActivity.KEY_IS_AUTHENTICATION, CommWebViewActivity.TYPE_TITLE_NORMAL)
        startActivityForResult(intent, AGREE_TO_PRIVACY_POLICY_REQUEST)
    }

    //从还款跳转商城页面
    fun showLoanFragment() {
        homeBottomView.status = HomeBottomView.TYPE_ONE
    }

    //    用户没有被禁用
    override fun isAvailableSuccese() {
        showThreePage()
    }

    fun getBottomNavagationHeight(): Int {
        return bottomHeight
    }
}
