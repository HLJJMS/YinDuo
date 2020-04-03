package com.yinduowang.installment.mvp.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils
import com.yinduowang.installment.R
import com.yinduowang.installment.di.component.DaggerArticleDetailsComponent
import com.yinduowang.installment.di.module.ArticleDetailsModule
import com.yinduowang.installment.mvp.contract.ArticleDetailsContract
import com.yinduowang.installment.mvp.model.entity.DiscoverEntity
import com.yinduowang.installment.mvp.presenter.ArticleDetailsPresenter
import kotlinx.android.synthetic.main.activity_article_details.*
import java.io.BufferedReader
import java.io.InputStreamReader


/**
 * @Description:文章详情页面
 * @Author:
 * @Date: 2019-11-13 20:08:45
 * @Version: 1.0, 2019-11-13
 * @LastEditors:
 * @LastEditTime:
 * @Deprecated: false
 */
class ArticleDetailsActivity : BaseActivity<ArticleDetailsPresenter>(), ArticleDetailsContract.View {

    lateinit var entity: DiscoverEntity

    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerArticleDetailsComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .articleDetailsModule(ArticleDetailsModule(this))
                .build()
                .inject(this)
    }

    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_article_details //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    override fun initData(savedInstanceState: Bundle?) {
        entity = intent.getSerializableExtra("entity") as DiscoverEntity
        titleBar.showBlackBack()
        tvContentTitle.text = entity.name
        tv_tag.text = entity.type
        tv_time.text = entity.date
        tv_word_desc.text= entity.wordDesc
        initWebView()
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

    @SuppressLint("JavascriptInterface")
    private fun initWebView() {
        val webSettings = webView?.settings
        webSettings?.savePassword = false
        webSettings?.saveFormData = false

        webSettings?.setSupportZoom(false)
        webSettings?.allowFileAccess = false
        webSettings?.loadsImagesAutomatically = true
        webSettings?.layoutAlgorithm = com.tencent.smtt.sdk.WebSettings.LayoutAlgorithm.SINGLE_COLUMN
        webSettings?.javaScriptCanOpenWindowsAutomatically = true
        webSettings?.useWideViewPort = true
        webSettings?.displayZoomControls = true
        webSettings?.cacheMode = com.tencent.smtt.sdk.WebSettings.LOAD_NO_CACHE

        webView?.isVerticalScrollBarEnabled = false
        webView?.isHorizontalScrollBarEnabled = false

        webView?.isHorizontalFadingEdgeEnabled = false
        webView.setOnLongClickListener { true }

        val res = getFromAssets()
        val content = entity.content.replace("&nbsp;", " ")
        val format = String.format(res, content)
        webView.loadDataWithBaseURL(null, format, "text/html", "utf-8", null)

    }

    fun getFromAssets(): String {
        try {
            val inputReader = InputStreamReader(resources.assets.open("articleDetails.html"))
            val bufReader = BufferedReader(inputReader)
            var line = bufReader.readLine()
            val stringBuilder = StringBuilder()
            while (line.isNotEmpty()) {
                stringBuilder.append(line)
                line = if (line == "</html>") {
                    ""
                } else {
                    bufReader.readLine()
                }
            }
            return stringBuilder.toString()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return ""
    }
}
