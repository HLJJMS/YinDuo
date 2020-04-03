package com.yinduowang.installment.mvp.ui.activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.util.Patterns
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.JavascriptInterface
import android.widget.ImageView
import android.widget.PopupWindow
import android.widget.TextView
import com.blankj.utilcode.util.SPUtils
import com.google.gson.Gson
import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils
import com.jess.arms.utils.PermissionUtil
import com.qmuiteam.qmui.layout.IQMUILayout
import com.qmuiteam.qmui.layout.QMUIFrameLayout
import com.qmuiteam.qmui.util.QMUIDisplayHelper
import com.qmuiteam.qmui.widget.QMUIObservableScrollView
import com.tbruyelle.rxpermissions2.RxPermissions
import com.tencent.smtt.export.external.interfaces.SslError
import com.tencent.smtt.export.external.interfaces.SslErrorHandler
import com.tencent.smtt.export.external.interfaces.WebResourceError
import com.tencent.smtt.export.external.interfaces.WebResourceRequest
import com.tencent.smtt.sdk.WebChromeClient
import com.tencent.smtt.sdk.WebSettings
import com.tencent.smtt.sdk.WebView
import com.tencent.smtt.sdk.WebViewClient
import com.yinduowang.installment.R
import com.yinduowang.installment.app.BaseConfig
import com.yinduowang.installment.app.constant.JSConstant
import com.yinduowang.installment.app.constant.SPConstant
import com.yinduowang.installment.app.utils.*
import com.yinduowang.installment.app.utils.gosetting.PermissionSetting
import com.yinduowang.installment.app.widget.TitleBar
import com.yinduowang.installment.app.widget.popwindow.BottomPopupWindow
import com.yinduowang.installment.di.component.DaggerCommWebViewComponent
import com.yinduowang.installment.di.module.CommWebViewModule
import com.yinduowang.installment.mvp.contract.CommWebViewContract
import com.yinduowang.installment.mvp.model.entity.MonthDetailForWebEntity
import com.yinduowang.installment.mvp.model.event.ShowHomeShopEvent
import com.yinduowang.installment.mvp.presenter.CommWebViewPresenter
import org.simple.eventbus.EventBus
import timber.log.Timber


/**
 * ================================================
 * Description:公共webview
 * <p>
 * Created by MVPArmsTemplate on 07/24/2019 13:20
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Suppress("UNREACHABLE_CODE")
class CommWebViewActivity : BaseActivity<CommWebViewPresenter>(), CommWebViewContract.View {


    companion object {
        // 打开原生选择地址页面
        const val REQUEST_CODE_ADDRESS = 1001
        const val RESULT_CODE_ADDRESS = 1002

        //        商品详情页标志头
        const val SHOP_DETAIL_TITLE = "商品详情"

        const val KEY_URL_NAME = "url"
        // 参数map
        const val KEY_URL_PARAMS = "params"
        // 是否拼接参数 默认拼接
        const val KEY_NO_HAVE_PARAMS = "no_have_params"
        // js交互类型
        const val KEY_JAVASCRIPT_TYPE = "JavascriptType"
        // web页面跳转标题类型
        const val KEY_IS_AUTHENTICATION = "isAuthentication"
        // web页面标题
        const val KEY_WEB_TITLE = "webViewTitle"

        // 特效标题 页面向上滚动一定距离后显示标题 向下滚动一定距离后隐藏标题
        const val TYPE_TITLE_CATEGORY = "category"
        // 专区标题 沉浸式页面 web页面头部顶到状态栏下
        const val TYPE_TITLE_DISTRICT = "district"
        // 商品详情标题 标题浮于web页面上方 web页面紧接状态栏
        const val TYPE_TITLE_DETAIL = "detail"
        // 普通标题
        const val TYPE_TITLE_NORMAL = "normal"

        // url是否拼接token 不传默认为true
        const val KEY_URL_ADD_TOKEN = "key_url_add_token"
        // url是否拼接type 不传默认为true
        const val KEY_URL_ADD_TYPE = "key_url_add_type"
        // js类型 协议交互类型 选择此值时 KEY_URL_ADD_TYPE=false
        const val TYPE_JAVASCRIPT_JEL_LE_ME = "installment_installment"
        // js类型 商城交互类型
        const val TYPE_JAVASCRIPT_INSTALLMENT = "installment"
        // 普通标题 是否显示底部按钮
        const val TYPE_IS_SHOW_BOTTOM_BTN = "isShowBottomBtn"

    }

    // 是否从主页进入web 0为是
    private var isFromLoanPage = ""
    private var isRefresh: Boolean = false
    //    判断是不是刷新动作，如果是就不让其谁显示默认的加载图案
    var isClose = true
    var kefuPopupWindow: BottomPopupWindow? = null
    var popupView: View? = null
    var webViewTitle: String = ""
    var popupWindow: PopupWindow? = null
    lateinit var qmuilayout: QMUIFrameLayout
    lateinit var tvTotalTxt: TextView
    lateinit var myRmb: TextView
    lateinit var tvInterest: TextView
    lateinit var tvMonthlyInterest: TextView
    lateinit var tvPeriods: TextView
    lateinit var tvMonthRmb: TextView
    var llContent: View? = null
    var titleBar: TitleBar? = null
    var webView: WebView? = null
    var tvBtn: TextView? = null
    var llError: View? = null
    var tvRetryBtn: TextView? = null
    var qmuiObservableScrollView: QMUIObservableScrollView? = null
    var baseUrl: String = ""
    var showbigpic = 0
    var height: String = ""
    //点击是否跳登录判断
    var isClicklogin = false

    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerCommWebViewComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .commWebViewModule(CommWebViewModule(this))
                .build()
                .inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun initView(savedInstanceState: Bundle?): Int {
        // 根据type显示不同页面
        when (intent.getStringExtra(KEY_IS_AUTHENTICATION)) {
            // 特效头
            TYPE_TITLE_CATEGORY -> {
                return R.layout.activity_webview_normal //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
            }
            // 专区头
            TYPE_TITLE_DISTRICT -> {
                return R.layout.activity_webview_normal//如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
            }
            // 商品详情页
            TYPE_TITLE_DETAIL -> {
                return R.layout.activity_webview_detail //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
            }
            // 普通返回头
            TYPE_TITLE_NORMAL -> {
                return R.layout.activity_webview_normal //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
            }
            // 其他样式
            else -> {
                return R.layout.activity_webview_normal //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
            }
        }
    }


    @SuppressLint("CheckResult")
    override fun initData(savedInstanceState: Bundle?) {

        try {
            showLoading()
            llContent = findViewById<View>(R.id.llContent)
            titleBar = findViewById<TitleBar>(R.id.titleBar)
            webView = findViewById<WebView>(R.id.webView)
            tvBtn = findViewById<TextView>(R.id.tvBtn)
            llError = findViewById<View>(R.id.llError)
            tvRetryBtn = findViewById<TextView>(R.id.tvRetryBtn)
            qmuiObservableScrollView = findViewById<QMUIObservableScrollView>(R.id.qmuiObservableScrollView)
            intent.getStringExtra(KEY_WEB_TITLE)?.let { webViewTitle = it }
            // 根据type显示不同页面
            when (intent.getStringExtra(KEY_IS_AUTHENTICATION)) {
                // 特效头
                TYPE_TITLE_CATEGORY -> {
                    if (titleBar != null) {
                        titleBar?.showBlackBack()
                        titleBar?.setBottomLineShow(false)
                    }
                    if (titleBar != null && qmuiObservableScrollView != null) {
                        qmuiObservableScrollView?.addOnScrollChangedListener { scrollView, scrollX, scrollY, oldScrollX, oldScrollY ->
                            val displayDistance = ArmsUtils.dip2px(this, 90f)
                            if (scrollY >= displayDistance) { // >=Toolbar高度的2.5倍时全显背景图
                                titleBar?.showTitle(webViewTitle)
                                titleBar?.setBottomLineShow(true)
                            } else { // 小于Toolbar高度时不设置背景图
                                titleBar?.showTitle(webViewTitle)
                                titleBar?.setTitleVisibility(View.GONE)
                                titleBar?.setBottomLineShow(false)
                            }
                        }
                    }
                }
                // 专区头
                TYPE_TITLE_DISTRICT -> {
                    if (titleBar != null)
                        titleBar?.showBlackBack()
                }
                // 商品详情页
                TYPE_TITLE_DETAIL -> {
                    if (titleBar != null)
                        titleBar?.showBlackBack()
                }
                // 普通返回头
                TYPE_TITLE_NORMAL -> {
                    if (titleBar != null) {
                        titleBar?.showTitle(webViewTitle)
                        titleBar?.showBlackBack(View.OnClickListener {
                            // 如果显示底部按钮 则返回要传递参数
                            if (intent.getBooleanExtra(TYPE_IS_SHOW_BOTTOM_BTN, false)) {
                                normalBack(false)
                            } else {
                                killMyself()
                            }
                        })
                    }

                    // 判断是否显示底部按钮
                    if (tvBtn != null)
                        if (intent.getBooleanExtra(TYPE_IS_SHOW_BOTTOM_BTN, false)) {
                            tvBtn?.visibility = View.VISIBLE
                            tvBtn?.setOnClickListener {
                                normalBack(true)
                            }
                        } else {
                            tvBtn?.visibility = View.GONE
                        }
                }
                // 其他类型
                else -> {
                    if (titleBar != null)
                        titleBar?.showBlackBack(View.OnClickListener {
                            // 表示按返回键 时的操作
                            if (webView!!.canGoBack()) {
                                // webview后退
                                webView!!.goBack()
                            } else {
                                killMyself()
                            }
                        })
                }
            }
            init()
            popupwindowInitView()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        // 引流 用户点击后回执
        if (intent.getBooleanExtra("yinliu", false)) {
            mPresenter!!.onFloatDetailedClick(intent.getStringExtra("sourceId"), intent.getStringExtra("actype"), intent.getStringExtra("type"))
        }
        //获取图片的url地址
        intent.extras?.getString("isFromLoanPage")?.let {
            isFromLoanPage = intent.extras!!.getString("isFromLoanPage").toString()
        }
    }

    private fun normalBack(isAgree: Boolean) {
        SPUtils.getInstance().put(SPConstant.AGREE_TO_PRIVACY_POLICY, isAgree)
        var intent = Intent()
        intent.putExtra("showAgree", isAgree)
        setResult(MainActivity.AGREE_TO_PRIVACY_POLICY_RESULT, intent)
        killMyself()
    }

    fun init() {
        if (webView != null) {
            val webSettings = webView?.settings
            webSettings?.savePassword = false
            webSettings?.saveFormData = false
            webSettings?.setJavaScriptEnabled(true)
            webSettings?.setSupportZoom(false)
            webSettings?.allowFileAccess = false
            webSettings?.loadsImagesAutomatically = true
            webSettings?.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
            webSettings?.javaScriptCanOpenWindowsAutomatically = true
            webSettings?.useWideViewPort = true
            webSettings?.displayZoomControls = true
            webSettings?.cacheMode = WebSettings.LOAD_NO_CACHE
            //android 5.0后不支持https 和http 地址同时访问需加上以下
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                webSettings?.setMixedContentMode(WebSettings.LOAD_NORMAL);
            }
            webView?.isVerticalScrollBarEnabled = false
            webView?.isHorizontalScrollBarEnabled = false

            webView?.isHorizontalFadingEdgeEnabled = false

            webView?.webChromeClient = MyWebChromeClient()

            webView?.webViewClient = MyWebViewClient()
            initWebViewJavascript(webView)
            webView?.loadUrl(getUrl())
            webView?.setOnLongClickListener { true }


        }
    }

    private fun initWebViewJavascript(webView: WebView?) {
        var type = intent.getStringExtra(KEY_JAVASCRIPT_TYPE)
        if (type != null && type.isNotEmpty()) {
            webView?.addJavascriptInterface(JelLeMeJavaScriptInterface(), type)
        } else {
            webView?.addJavascriptInterface(InstallmentJavaScriptInterface(), TYPE_JAVASCRIPT_INSTALLMENT)
        }
    }

    private fun getUrl(): String? {
        baseUrl = intent.getStringExtra(KEY_URL_NAME)
        //        当KEY_IS_AUTHENTICATION值为空时候跳转外网
        var type = intent.getStringExtra(KEY_IS_AUTHENTICATION)
        if (type == null || type.isEmpty()) {
            return baseUrl
        } else {
            var noHaveParas = intent.getBooleanExtra(KEY_NO_HAVE_PARAMS, true)
            if (noHaveParas) {
                if (baseUrl.contains("?")) {
                    if (!baseUrl.endsWith("?")) {
                        baseUrl += "&"
                    }
                } else {
                    baseUrl += "?"
                }
                val type = addType()
                val token = addToken()
                val params = addParams()
                if (type.isNotEmpty()) {
                    baseUrl += type
                }
                if (token.isNotEmpty()) {
                    if (!baseUrl.endsWith("?") && !baseUrl.endsWith("&")) {
                        baseUrl += "&"
                    }
                    baseUrl += token
                }
                if (params.isNotEmpty()) {
                    if (!baseUrl.endsWith("?") && !baseUrl.endsWith("&")) {
                        baseUrl += "&"
                    }
                    baseUrl += params
                }
            }

            Timber.i("WebViewUrl:$baseUrl")
            return baseUrl
        }

    }


    /**
     * 拼接参数
     * */
    private fun addParams(): String {
        var params = "version=${if (SPUtils.getInstance().getBoolean(SPConstant.IS_TEST_VERSION)) {
            0
        } else {
            1
        }}"
        return if (intent.getSerializableExtra(KEY_URL_PARAMS) != null) {
            val paramsMap = intent.getSerializableExtra(KEY_URL_PARAMS) as Map<*, *>
            for (name in paramsMap.keys) {
                params += "&$name=${paramsMap[name]}"
            }
            params
        } else {
            params
        }
    }

    /**
     * 拼接token
     * */
    private fun addToken(): String {
        return if (urlAddToken()) {
            val token = SPUtils.getInstance().getString(SPConstant.TOKEN)
            if (token.isNotEmpty()) {
                "token=$token"
            } else {
                ""
            }
        } else {
            ""
        }
    }

    /**
     * 拼接type
     * */
    private fun addType(): String {
        var type = intent.getStringExtra(KEY_JAVASCRIPT_TYPE)
        return if (type != null && type.isNotEmpty() && type == TYPE_JAVASCRIPT_JEL_LE_ME) {
            // 协议类web 不需要拼接type
            ""
        } else {
            if (urlAddType()) {
                "type=Android"
            } else {
                ""
            }
        }
    }

    /**
     * 判断 url是否拼接token
     *
     * @return 默认拼接
     */
    private fun urlAddToken(): Boolean {
        return intent.getBooleanExtra(KEY_URL_ADD_TOKEN, true)
    }

    /**
     * 判断 url是否拼接type
     *
     * @return 默认拼接
     */
    private fun urlAddType(): Boolean {
        return intent.getBooleanExtra(KEY_URL_ADD_TYPE, true)
    }

    inner class JelLeMeJavaScriptInterface {
        @JavascriptInterface
        fun updateTitle(title: String) {
            this@CommWebViewActivity.runOnUiThread {
                if (titleBar != null) {
                    titleBar?.showTitle(title)
                    titleBar?.setTitleEllipsize(TextUtils.TruncateAt.END)
                }
            }
        }

        /**
         * 上饶银行开户 成功 显示下一步页面
         * */
        @JavascriptInterface
        fun resultsigin() {
            this@CommWebViewActivity.runOnUiThread {
                startActivity(Intent(this@CommWebViewActivity, BaofuWithholdActivity::class.java))
            }
        }

        /**
         * 上饶银行开户 失败 显示重新签约
         * */
        @JavascriptInterface
        fun resetsign() {
            this@CommWebViewActivity.runOnUiThread {
                mPresenter?.openAccount()
            }
        }

        /**
         * 上饶银行开户 处理中 显示刷新
         * */
        @JavascriptInterface
        fun refeshsign() {
            this@CommWebViewActivity.runOnUiThread {
                mPresenter?.openAccount()
            }
        }

        /**
         * Description：联系客服（弹窗三按钮）
         * Author：田羽衡
         * Version：<v1.0，2019/11/1 9:50>
         * params:电话号，flag没有用
         * return：
         * LastEditTime：2019/11/1 9:50
         * Deprecated： false
         */
        @JavascriptInterface
        fun findKeFu(flag: String, pnumber: String) {
            this@CommWebViewActivity.runOnUiThread {
                if (isClose) {
                    isClose = false
                    Handler().postDelayed({
                        isClose = true
                    }, 500)
                    showCustomerPopWindow(pnumber)
                }
            }
        }

        /**
         * Description：联系客服(标题文字)
         * Author：田羽衡
         * Version：<v1.0，2019/11/1 9:50>
         * params:电话号，flag没有用
         * return：
         * LastEditTime：2019/11/1 9:50
         * Deprecated： false
         */
        @JavascriptInterface
        fun oldfindKeFu(flag: String, pnumber: String) {
            this@CommWebViewActivity.runOnUiThread {
                if (titleBar != null) {
                    titleBar!!.setRightTextView("咨询客服", View.OnClickListener {
                        mPresenter?.mErrorHandler?.let {
                            GotoMQChatUtils.gotoMQChatCustomized(this@CommWebViewActivity, PhoneStatusUtils.getIMEI(), it)
                        }
                    })

                }

            }
        }

        // 跳转web
        @JavascriptInterface
        fun gowww(url: String) {
            this@CommWebViewActivity.runOnUiThread {
                var intent = Intent(this@CommWebViewActivity, CommWebViewActivity::class.java)
                intent.putExtra(KEY_URL_NAME, url)
                intent.putExtra(KEY_JAVASCRIPT_TYPE, TYPE_JAVASCRIPT_JEL_LE_ME)
                intent.putExtra(KEY_IS_AUTHENTICATION, TYPE_TITLE_NORMAL)
                startActivity(intent)
            }
        }


        @JavascriptInterface
        fun delUser(msg: String) {
            gotoLogin(msg)
        }

        /*
         *  法大大返回交互 签约失败
         */
        @JavascriptInterface
        fun pushback() {
            this@CommWebViewActivity.runOnUiThread {
                killMyself()
            }
        }

        /*
         *  法大大返回交互  签约中, 签约成功
         */
        @JavascriptInterface
        fun cashloandetail(loanId: String) {
            this@CommWebViewActivity.runOnUiThread {
                if ("0".equals(isFromLoanPage)) {
                    //交互非空判断
                    if (!loanId.isNullOrEmpty()) {
                        var intent = Intent(this@CommWebViewActivity, CashLoanDetailedActivity::class.java)
                        intent.putExtra("loanId", loanId.toInt())
                        intent.putExtra("type", "3")
                        intent.putExtra("backType", "1")
                        startActivity(intent)
                        killMyself()
                    }
                } else {
                    killMyself()
                }
            }
        }


    }

    /**
     * Description：登录跳转
     * Author：田羽衡
     * Version：<v1.0，2019/11/1 9:50>
     * params:电话号，flag没有用
     * return：
     * LastEditTime：2019/11/1 9:50
     * Deprecated： false
     */
    private fun gotoLogin(msg: String) {
        this@CommWebViewActivity.runOnUiThread {
            EventBus.getDefault().post(ShowHomeShopEvent())
            SPUtils.getInstance().put(SPConstant.SHARE_TAG_USER_LOGIN_TO_SIGNOUT, true)
            ClearLoginDataUtil.clearLoginStatus()
            val intent = Intent(this@CommWebViewActivity, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.putExtra("goLogin", true)
            intent.putExtra("goLoginMsg", msg)
            startActivity(intent)
        }
    }

    //判断是否登陆返回来进行刷新页面
    override fun onResume() {
        super.onResume()
        if (isClicklogin) {
            isClicklogin = false
            webView?.loadUrl(getUrl())
        }
        if (webViewTitle.equals(SHOP_DETAIL_TITLE)) {
            webView?.loadUrl(getUrl())
        }


    }

    inner class InstallmentJavaScriptInterface {

        /**
         * 跳转页面
         *
         * @param url 新页面url
         * @param type 新页面样式
         *        category 特效头
         *        district 专区头
         *        normal 普通返回头
         *        detail 商品详情页
         * */
        @JavascriptInterface
        fun jumpUrl(url: String, type: String) {
            this@CommWebViewActivity.runOnUiThread {
                //                OpenWebViewUtils.openWebViewGoodsDetails(this@CommWebViewActivity, url, type)

                var intent = Intent(this@CommWebViewActivity, CommWebViewActivity::class.java)
                intent.putExtra(KEY_URL_NAME, url)
                intent.putExtra(KEY_IS_AUTHENTICATION, type)
                startActivity(intent)
            }
        }

        /**
         * Description：选择收货地址
         * Author：田羽衡
         * Version：<v1.0，2019/11/1 9:50>
         * params:电话号，flag没有用
         * return：
         * LastEditTime：2019/11/1 9:50
         * Deprecated： false
         */
        @JavascriptInterface
        fun showAddress(url: String) {
            this@CommWebViewActivity.runOnUiThread {
                if (SPUtils.getInstance().getString(SPConstant.TOKEN).isNotEmpty()) {
//                    BaseDialog(this@CommWebViewActivity)
//                            .builder()
//                            .setCancelable(false)
//                            .setTitle("请添加收货地址")
//                            .setBtnLeft("暂不添加")
//                            .setBtnRight("去添加", object : BaseDialog.BaseDialogBuilder.BtnClickListener {
//                                override fun onClick(dialog: BaseDialog) {
//                                    var intent = Intent(this@CommWebViewActivity, DeliveryAddressActivity::class.java)
//                                    intent.putExtra("chooseAddress", true)
//                                    startActivityForResult(intent, Companion.REQUEST_CODE_ADDRESS)
//                                }
//                            })
//                            .create()
//                            .show()
                    var intent = Intent(this@CommWebViewActivity, DeliveryAddressActivity::class.java)
                    intent.putExtra("chooseAddress", true)
                    startActivityForResult(intent, Companion.REQUEST_CODE_ADDRESS)
                } else {
                    startActivity(Intent(this@CommWebViewActivity, LoginActivity::class.java))
                }

            }
        }

        /**
         * @param showHead 0 隐藏标题
         * */
        @JavascriptInterface
        fun showHead(showHead: Int) {
            this@CommWebViewActivity.runOnUiThread {
                if (titleBar != null)
                    if (showHead == 0) {
                        titleBar?.visibility = View.INVISIBLE
                    } else {
                        titleBar?.visibility = View.VISIBLE
                    }
            }
        }

        /**
         * 是否开启了大图显示弹窗
         * */
        @JavascriptInterface
        fun showBigpic(showHead: Int) {
            showbigpic = showHead
        }


        /**
         * 显示标题
         * */
        @JavascriptInterface
        fun detailToast(message: String) {
            this@CommWebViewActivity.runOnUiThread {
                ToastUtils.makeText(this@CommWebViewActivity, message)
            }
        }

        /**
         * 联系客服
         * */
        @JavascriptInterface
        fun contactService() {
            this@CommWebViewActivity.runOnUiThread {
                if (isClose) {
                    isClose = false
                    Handler().postDelayed({
                        isClose = true
                    }, 500)
                    mPresenter?.getServiceTel()
                }
            }
        }


        /**
         * 显示加载
         * */
        @JavascriptInterface
        fun showLoading(isShow: String) {
            this@CommWebViewActivity.runOnUiThread {
                if (isShow.equals("0")) {
                    hideLoading()
                } else {
                    showLoading()
                }
            }
        }

        /**
         * Description：下单
         * Author：田羽衡
         * Version：<v1.0，2019/11/1 9:52>
         * params:
         * return：
         * LastEditTime：2019/11/1 9:52
         * Deprecated： false
         */
        @JavascriptInterface
        fun createOrder(param: String, order: String) {
            this@CommWebViewActivity.runOnUiThread {
                if (SPUtils.getInstance().getString(SPConstant.TOKEN).isNotEmpty()) {
                    var intent = Intent(this@CommWebViewActivity, ShopConfirmationOrderActivity::class.java)
                    intent.putExtra("param", param)
                    intent.putExtra("order", order)
                    startActivity(intent)
//                    ToastUtils.makeText(this@CommWebViewActivity, "业务调整中，敬请期待！")
                } else {
                    isClicklogin = true
                    startActivity(Intent(this@CommWebViewActivity, LoginActivity::class.java))
                }
            }
        }


        /**
         * Description：登录
         * Author：田羽衡
         * Version：<v1.0，2019/11/1 9:52>
         * params:
         * return：
         * LastEditTime：2019/11/1 9:52
         * Deprecated： false
         */
        @JavascriptInterface
        fun login() {
            this@CommWebViewActivity.runOnUiThread {
                isClicklogin = true
                startActivity(Intent(this@CommWebViewActivity, LoginActivity::class.java))
            }
        }


        /**
         * Description：月供详情
         * Author：田羽衡
         * Version：<v1.0，2019/11/1 9:52>
         * params:
         * return：
         * LastEditTime：2019/11/1 9:52
         * Deprecated： false
         */
        @JavascriptInterface
        fun monthPay(param: String) {
            this@CommWebViewActivity.runOnUiThread {
                val gson = Gson()
                var bean = gson.fromJson(param, MonthDetailForWebEntity::class.java)
                tvTotalTxt.text = bean.price
                myRmb.text = "¥" + bean.fund
                tvInterest.text = "¥" + bean.interest
                tvMonthlyInterest.text = (bean.rate.toFloat() * 100).toInt().toString() + "%"
                tvPeriods.text = bean.cycle + "期"
                tvMonthRmb.text = "¥" + bean.min_money
                popupWindowShow()
            }
        }

        /**
         * 404错误页面返回
         * */
        @JavascriptInterface
        fun errorback() {
            this@CommWebViewActivity.runOnUiThread {
                if (webView!!.canGoBack()) {
                    webView?.goBack()//返回上个页面
                } else {
                    finish()
                }

            }
        }

        @JavascriptInterface
        fun delUser(msg: String) {
            gotoLogin(msg)
        }

        /**
         * 0 关闭下拉刷新 1开启下拉刷新
         * */
        @JavascriptInterface
        fun isRefresh(open: Int) {

        }
    }

    /**
     * 回调js公共类
     * @param jsMethod 方法名
     * @param map 参数列表
     * */
    fun javascriptInteractive(jsMethod: String, map: Map<String, String>?) {
        if (webView != null) {
            var json = ""
            if (map != null) {
                json = Gson().toJson(map)
            }
            webView?.loadUrl("javascript:appMethods({method:'$jsMethod',param:$json})")
        }
    }


    /**
     * Description：月供详情popupwindow
     * Author：田羽衡
     * Version：<v1.0，2019/11/1 9:53>
     * params:
     * return：
     * LastEditTime：2019/11/1 9:53
     * Deprecated： false
     */
    fun popupwindowInitView() {
        popupView = LayoutInflater.from(this).inflate(R.layout.layout_dialog_month_detail, null)
        popupWindow = PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        popupWindow!!.isFocusable = false
        popupWindow!!.setBackgroundDrawable(BitmapDrawable())
        qmuilayout = popupView!!.findViewById<QMUIFrameLayout>(R.id.qmuilayout)
        qmuilayout.radius = QMUIDisplayHelper.dp2px(this, 12)
        qmuilayout.hideRadiusSide = IQMUILayout.HIDE_RADIUS_SIDE_BOTTOM
        myRmb = popupView!!.findViewById<TextView>(R.id.myRmb)
        tvInterest = popupView!!.findViewById<TextView>(R.id.tvInterest)
        tvMonthlyInterest = popupView!!.findViewById<TextView>(R.id.tvMonthlyInterest)
        tvPeriods = popupView!!.findViewById<TextView>(R.id.tvPeriods)
        tvMonthRmb = popupView!!.findViewById<TextView>(R.id.tvMonthRmb)
        tvTotalTxt = popupView!!.findViewById<TextView>(R.id.tvTotalTxt)
        var close = popupView!!.findViewById<ImageView>(R.id.ivClose)
        close.setOnClickListener {
            popupWindow!!.dismiss()
            setBackgroundAlpha(1f)
        }
    }

    /**
     * Description：设置popupwindow显示的背景颜色
     * Author：田羽衡
     * Version：<v1.0，2019/11/1 9:53>
     * params:
     * return：
     * LastEditTime：2019/11/1 9:53
     * Deprecated： false
     */
    private fun setBackgroundAlpha(bgAlpha: Float) {
        val lp = window.attributes
        lp.alpha = bgAlpha
        window.attributes = lp
    }


    /**
     * Description：月供详情     popupwindow显示
     * Author：田羽衡
     * Version：<v1.0，2019/11/1 9:53>
     * params:
     * return：
     * LastEditTime：2019/11/1 9:53
     * Deprecated： false
     */
    fun popupWindowShow() {
        setBackgroundAlpha(0.5f)
        popupWindow!!.showAtLocation(popupView, Gravity.BOTTOM, 0, 0);
    }


    /**
     * Description：  显示联系客服弹窗
     * Author：田羽衡
     * Version：<v1.0，2019/11/1 9:53>
     * params:
     * return：
     * LastEditTime：2019/11/1 9:53
     * Deprecated： false
     */
    fun showCustomerPopWindow(phoneNum: String) {
        kefuPopupWindow = BottomPopupWindow(this@CommWebViewActivity, true)
        kefuPopupWindow!!.setOneButtontText("在线联系客服")
        kefuPopupWindow!!.setTwoButtontText("拨打电话：" + phoneNum)
        kefuPopupWindow!!.setOnButtonClick(
                object : BottomPopupWindow.ButtonClick {
                    override fun setClick(id: Int) {
                        if (id == BottomPopupWindow.ONE_BUTTON) {
                            mPresenter?.let {
                                GotoMQChatUtils.gotoMQChatCustomized(this@CommWebViewActivity, PhoneStatusUtils.getIMEI(), it?.mErrorHandler)
                            }
                        } else if (id == BottomPopupWindow.TWO_BUTTON) {
                            requestsForPermissionsCall(phoneNum)
                        }
                    }
                })
        kefuPopupWindow!!.show()
    }

    /**
     * Description：打电话权限
     * Author：田羽衡
     * Version：<v1.0，2019/11/1 9:53>
     * params:
     * return：
     * LastEditTime：2019/11/1 9:53
     * Deprecated： false
     */
    fun requestsForPermissionsCall(phoneNum: String) {
        PermissionUtil.requestPermission(
                object : PermissionUtil.RequestPermission {
                    @SuppressLint("MissingPermission")
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
                        PermissionSetting.toPermissionSetting(this@CommWebViewActivity)
                    }
                },
                RxPermissions(this),
                mPresenter?.mErrorHandler,
                Manifest.permission.CALL_PHONE
        )
    }

    internal inner class MyWebChromeClient : WebChromeClient() {
        override fun onProgressChanged(webView: WebView?, progress: Int) {
            super.onProgressChanged(webView, progress)
            if (progress == 100) {
                hideLoading()
            }
        }


        override fun onReceivedTitle(view: WebView?, title: String?) {
            super.onReceivedTitle(view, title)
            if (!isWebUrlLoation(title) && !Patterns.WEB_URL.matcher(title).matches() && null == intent.getStringExtra(KEY_WEB_TITLE)) {
                if (title != null) {
                    webViewTitle = title
                }

                if (intent.getStringExtra(KEY_IS_AUTHENTICATION) == TYPE_TITLE_CATEGORY) {
                    // 商品分类 不需要显示标题
                } else if (titleBar != null && title != null && title.isNotEmpty()) {
                    // 除了商品分类 其他页面 只要有titleBar都显示标题
                    titleBar?.showTitle(title)
                    titleBar?.setTitleEllipsize(TextUtils.TruncateAt.END)
                }
            }
        }
    }

    fun isWebUrlLoation(msg: String?): Boolean {
        return if (msg.isNullOrEmpty()) true
        else {
            msg.contains(BaseConfig.BASE_IP_JAVA.substring(8)) ||
                    msg.contains(BaseConfig.BASE_IP_PHP.substring(8)) ||
                    msg.contains(BaseConfig.BASE_IP_AGREEMENT.substring(8)) ||
                    msg.contains(BaseConfig.BASE_IP_SHOP.substring(8))
        }
    }

    internal inner class MyWebViewClient : WebViewClient() {
        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            Timber.i(view.toString() + " --> onPageStarted --> " + url)
            if (!isRefresh) {
                showLoading()
            }

        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            Timber.i(view.toString() + " --> onPageFinished --> " + url)
            hideLoading()
        }

        override fun onReceivedSslError(p0: WebView?, p1: SslErrorHandler?, p2: SslError?) {
            super.onReceivedSslError(p0, p1, p2)
            p1?.proceed()//绕过所有证书
        }

        override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
            super.onReceivedError(view, request, error)
            var errorCode = error?.errorCode
//            首次访问外网百度网页会返回错误码ERROR_UNSUPPORTED_SCHEME = -10; 其他网页正常
            if (errorCode != ERROR_CONNECT && errorCode != ERROR_HOST_LOOKUP) {
                if (llError != null)
                    llError?.visibility = View.VISIBLE
                if (llContent != null)
                    llContent?.visibility = View.GONE
                if (webView != null)
                    webView?.loadDataWithBaseURL(null, "", "text/html", "utf-8", null)
                if (tvRetryBtn != null && llError != null && llContent != null && webView != null)
                    tvRetryBtn?.setOnClickListener {
                        llError?.visibility = View.GONE
                        llContent?.visibility = View.VISIBLE
                        showLoading()
                        webView?.loadUrl(getUrl())
                    }
            }


        }
    }

    //    获取电话成功
    override fun getTelSuccess(tel: String) {
        showCustomerPopWindow(tel)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (REQUEST_CODE_ADDRESS == requestCode && RESULT_CODE_ADDRESS == resultCode) {
            if (data != null) {
                var map = mapOf("addressName" to data.getStringExtra("addressName"), "addressId" to data.getStringExtra("addressId"))
                javascriptInteractive(JSConstant.JS_METHOD_CHANGE_ADDRESS, map)
            }
        }
    }

    /**
     * 物理返回监听
     * */
    override fun onBackPressed() {
        try {
            if (showbigpic == 1) {
                webView?.loadUrl("javascript:appMethods({method:'closeLageImg',param:''})")
                showbigpic = 0
            } else {
                if (intent.getStringExtra(KEY_IS_AUTHENTICATION).isNullOrEmpty()) {
                    if (webView!!.canGoBack()) {
                        // webview后退
                        webView!!.goBack()
                    } else {
                        killMyself()
                    }
                } else if (intent.getStringExtra(KEY_IS_AUTHENTICATION) == TYPE_TITLE_NORMAL && intent.getBooleanExtra(TYPE_IS_SHOW_BOTTOM_BTN, false)) {
                    // 是普通头 并且显示底部按钮 返回要传递参数
                    normalBack(false)
                } else {
                    killMyself()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun openAccountResult(url: String) {
        if (webView != null) {
            webView?.loadUrl(baseUrl)
        }
    }


}