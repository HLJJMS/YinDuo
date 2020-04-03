package com.yinduowang.installment.mvp.ui.activity


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.SPUtils
import com.jakewharton.rxbinding3.view.clicks
import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils
import com.umeng.analytics.MobclickAgent
import com.yinduowang.installment.R
import com.yinduowang.installment.app.constant.SPConstant
import com.yinduowang.installment.app.constant.UMCountConfig
import com.yinduowang.installment.app.utils.UMCountUtil
import com.yinduowang.installment.di.component.DaggerIntroductionComponent
import com.yinduowang.installment.di.module.IntroductionModule
import com.yinduowang.installment.mvp.contract.IntroductionContract
import com.yinduowang.installment.mvp.presenter.IntroductionPresenter
import com.youth.banner.transformer.DepthPageTransformer
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.activity_introduction.*
import java.util.*
import java.util.concurrent.TimeUnit


/**
 * ================================================
 * Description:引导页
 * <p>
 * Created by MVPArmsTemplate on 08/20/2019 16:44
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
class IntroductionActivity : BaseActivity<IntroductionPresenter>(), IntroductionContract.View {
    private var layoutList: MutableList<View> = ArrayList()
    var buttonWidth = 0
    var buttonHeight = 0
    var buttonBottom = 0

    var ivButton: View? = null

    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerIntroductionComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .introductionModule(IntroductionModule(this))
                .build()
                .inject(this)
    }


    override fun initView(savedInstanceState: Bundle?): Int {
        if (Build.VERSION.SDK_INT > 16 && Build.VERSION.SDK_INT < 19) {
            val v = this.window.decorView
            v.systemUiVisibility = View.GONE
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            BarUtils.setNavBarVisibility(this, false)
        }
        return R.layout.activity_introduction //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }


    @SuppressLint("CheckResult")
    override fun initData(savedInstanceState: Bundle?) {

        var viewOne = LayoutInflater.from(this@IntroductionActivity).inflate(R.layout.layout_page_imageview_one, null)
        var viewTwo = LayoutInflater.from(this@IntroductionActivity).inflate(R.layout.layout_page_imageview_two, null)
        var viewThree = LayoutInflater.from(this@IntroductionActivity).inflate(R.layout.layout_page_imageview_three, null)

        layoutList.add(viewOne)
        layoutList.add(viewTwo)
        layoutList.add(viewThree)
        ivButton = viewThree.findViewById<View>(R.id.ivButton)
        ivButton?.clicks()
                ?.throttleFirst(500, TimeUnit.MILLISECONDS)
                ?.subscribe(Consumer {
                    SPUtils.getInstance().put(SPConstant.KEY_IS_NOT_FIRST_RUN, true)
                    startActivity(Intent(this@IntroductionActivity, MainActivity::class.java))
                    UMCountUtil.instance()!!.onEvent(TAG, UMCountConfig.IntroductionActivity_btn, UMCountConfig.IntroductionActivity_btn_value)
                    finish()
                })


        //数据适配器
        val mPagerAdapter = object : PagerAdapter() {

            override//获取当前窗体界面数
            fun getCount(): Int {
                return layoutList.size
            }

            override//断是否由对象生成界面
            fun isViewFromObject(arg0: View, arg1: Any): Boolean {
                return arg0 === arg1
            }

            //是从ViewGroup中移出当前View
            override fun destroyItem(arg0: View, arg1: Int, arg2: Any) {
                (arg0 as ViewPager).removeView(layoutList.get(arg1))
            }

            //返回一个对象，这个对象表明了PagerAdapter适配器选择哪个对象放在当前的ViewPager中
            override fun instantiateItem(arg0: View, arg1: Int): Any {
                (arg0 as ViewPager).addView(layoutList.get(arg1))
                return layoutList.get(arg1)
            }
        }


        viewPager.setAdapter(mPagerAdapter)
        viewPager.setPageTransformer(true, DepthPageTransformer())
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(p0: Int) {

            }

            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {

            }

            override fun onPageSelected(i: Int) {
                when (i) {
                    0 -> {
                        ivButton?.visibility = GONE
                        UMCountUtil.instance()!!.onEvent(TAG, UMCountConfig.IntroductionActivity_page_one, UMCountConfig.IntroductionActivity_page_one_value)
                    }
                    1 -> {
                        ivButton?.visibility = GONE
                        UMCountUtil.instance()!!.onEvent(TAG, UMCountConfig.IntroductionActivity_page_two, UMCountConfig.IntroductionActivity_page_two_value)
                    }
                    2 -> {
                        ivButton?.visibility = View.VISIBLE
                        UMCountUtil.instance()!!.onEvent(TAG, UMCountConfig.IntroductionActivity_page_three, UMCountConfig.IntroductionActivity_page_three_value)
                    }
                }
            }

        })
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
        MobclickAgent.onPageStart("引导页") //统计页面(仅有Activity的应用中SDK自动调用，不需要单独写。"SplashScreen"为页面名称，可自定义)
        MobclickAgent.onResume(this)
    }

    public override fun onPause() {
        super.onPause()
        MobclickAgent.onPageEnd("引导页") // （仅有Activity的应用中SDK自动调用，不需要单独写）保证 onPageEnd 在onPause 之前调用,因为 onPause 中会保存信息。"SplashScreen"为页面名称，可自定义
        MobclickAgent.onPause(this)
    }
}
