package com.yinduowang.installment.mvp.ui.activity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.core.content.ContextCompat
import com.jakewharton.rxbinding3.view.clicks
import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils
import com.yinduowang.installment.R
import com.yinduowang.installment.app.utils.LoadingUtils
import com.yinduowang.installment.di.component.DaggerFeedBackComponent
import com.yinduowang.installment.di.module.FeedBackModule
import com.yinduowang.installment.mvp.contract.FeedBackContract
import com.yinduowang.installment.mvp.presenter.FeedBackPresenter
import kotlinx.android.synthetic.main.activity_feed_back.*
import java.util.concurrent.TimeUnit


/**
 * @Description:我的-设置-意见反馈
 * @Author: 张利超
 * @Date: 2019-10-31 10:26:52
 * @Version: v1.0, 2019-10-31
 * @LastEditors:张利超
 * @LastEditTime: 2019-10-31 10:26:52
 * @Deprecated: false
 */
class FeedBackActivity : BaseActivity<FeedBackPresenter>(), FeedBackContract.View {
    /**
     * 反馈意见是否为空
     */
    private var sign1 = false
    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerFeedBackComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .feedBackModule(FeedBackModule(this))
                .build()
                .inject(this)
    }


    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_feed_back //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }


    override fun initData(savedInstanceState: Bundle?) {
        titleBar.showTitleAndBack("意见反馈")
        inputFeedback.addTextChangedListener(inputWatcher)
        tvSubmit.clicks().throttleFirst(500, TimeUnit.MILLISECONDS).subscribe {
          mPresenter?.let{it.submitFeedBack(inputFeedback.text.toString().trim())}
        }
    }

    /**
     * @description ：输入监听判断字体字数是否大于限定值
     * @lastEditor  ：张利超
     * @version     ：v1.0 ,2019/11/1
     * @param null
     * @return      ：
     * @deprecated  ： false
     */
    private val inputWatcher = object : TextWatcher {

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            if (inputFeedback.text.length > 160) {
                number.text = "0"
                number.setTextColor((ContextCompat.getColor(baseContext, R.color.color_D0D0D0)))
            } else {
                number.text = inputFeedback.text.length.toString()
                number.setTextColor((ContextCompat.getColor(baseContext, R.color.color_365AF7)))
            }

            sign1 = inputFeedback.text.length > 0
            if (sign1)
                tvSubmit.isEnabled = true
            else
                tvSubmit.isEnabled = false
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int,
                                       after: Int) {

        }

        override fun afterTextChanged(s: Editable) {

        }
    }

    override fun showLoading() {
        LoadingUtils.show(this);
    }

    override fun hideLoading() {
        LoadingUtils.dismiss(this);
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
