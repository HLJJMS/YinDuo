package com.yinduowang.installment.mvp.ui.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.TranslateAnimation
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.SPUtils
import com.google.gson.Gson
import com.jakewharton.rxbinding3.view.clicks
import com.jess.arms.base.BaseFragment
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils
import com.jess.arms.utils.DeviceUtils
import com.qmuiteam.qmui.util.QMUIDisplayHelper
import com.scwang.smartrefresh.layout.constant.RefreshState
import com.yinduowang.installment.R
import com.yinduowang.installment.app.constant.Constant
import com.yinduowang.installment.app.constant.SPConstant
import com.yinduowang.installment.app.utils.LoadImageUtils
import com.yinduowang.installment.app.utils.ViewUtil
import com.yinduowang.installment.di.component.DaggerShoppingMallComponent
import com.yinduowang.installment.di.module.ShoppingMallModule
import com.yinduowang.installment.mvp.contract.ShoppingMallContract
import com.yinduowang.installment.mvp.model.entity.HomeShoppingMallEntity
import com.yinduowang.installment.mvp.model.entity.ShoppingMallEntity
import com.yinduowang.installment.mvp.model.event.HomeBottomLoginEvent
import com.yinduowang.installment.mvp.presenter.ShoppingMallPresenter
import com.yinduowang.installment.mvp.ui.activity.FloatDetailedActivity
import com.yinduowang.installment.mvp.ui.activity.LoginActivity
import com.yinduowang.installment.mvp.ui.activity.MainActivity
import com.yinduowang.installment.mvp.ui.adapter.ShoppingMallAdapter
import com.yinduowang.installment.mvp.ui.service.UploadService
import kotlinx.android.synthetic.main.fragment_shopping_mall.*
import org.simple.eventbus.Subscriber

/**
 * ================================================
 * Description:主页-商城
 * <p>
 * Created by MVPArmsTemplate on 07/25/2019 13:10
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
class ShoppingMallFragment : BaseFragment<ShoppingMallPresenter>(), ShoppingMallContract.View {

    var adapter: ShoppingMallAdapter? = null
    var list: ArrayList<ShoppingMallEntity> = ArrayList()
    var layoutManager: LinearLayoutManager? = null
    var showLabel = -1
    var isClickFloat = false
    private var newX: Int = 0
    private var newY: Int = 0
    private var lastX: Int = 0
    private var lastY: Int = 0
    private var marginTop = 0
    private var downLong: Long = 0
    private var isLeft = false
    private var bottomNavigationHeight = 0
    @Subscriber
    fun onMessageEvent(event: HomeBottomLoginEvent) {
        if (event.doClose == true) {
            cl.visibility = View.GONE
        } else {
            cl.visibility = View.VISIBLE
            mPresenter?.getLoginMessage()
        }
    }

    companion object {
        fun newInstance(): ShoppingMallFragment {
            val fragment = ShoppingMallFragment()
            return fragment
        }
    }

    override fun setupFragmentComponent(appComponent: AppComponent) {
        DaggerShoppingMallComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .shoppingMallModule(ShoppingMallModule(this))
                .build()
                .inject(this)
    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_shopping_mall, container, false)
    }

    @SuppressLint("CheckResult")
    override fun initData(savedInstanceState: Bundle?) {

        vStatus.layoutParams.height = DeviceUtils.getStatusBarHeight(mContext)

        smartRefreshLayout.setOnRefreshListener {
            mPresenter?.getShoppingMallData()
        }
        ivClose.clicks().subscribe {
            cl.visibility = View.GONE
        }
        btGOLogin.clicks().subscribe {
            startActivity(Intent(activity!!, LoginActivity::class.java))
        }
        ivGoTop.clicks().subscribe {
            recyclerView.smoothScrollToPosition(0)
        }
        layoutManager = LinearLayoutManager(activity!!)
        recyclerView.layoutManager = layoutManager
        adapter = ShoppingMallAdapter(list)
        adapter?.setEmptyView(R.layout.layout_skeleton_shopping_mall, recyclerView as ViewGroup)
        recyclerView.adapter = adapter
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (layoutManager != null) {
                    if (layoutManager?.findFirstVisibleItemPosition()!! > showLabel) {
                        ivGoTop.visibility = View.VISIBLE
                    } else {
                        ivGoTop.visibility = View.GONE
                    }
                }
            }
        })
        val cache = SPUtils.getInstance().getString(Constant.getCacheHomeShoppingMall(activity!! as Context))
        if (!cache.isNullOrEmpty()) {
            val entity = Gson().fromJson<HomeShoppingMallEntity>(cache, HomeShoppingMallEntity::class.java)
            getShoppingMallDataSuccess(entity, true)
        }

        if (SPUtils.getInstance().getString(SPConstant.TOKEN).isEmpty()) {
            mPresenter?.getLoginMessage()
        }
        //计算首页向上图标的高度
        bottomNavigationHeight = (activity as MainActivity).getBottomNavagationHeight() + ArmsUtils.dip2px(activity!!, 49f + 46f)
        initImageFloat()
    }

    override fun setData(data: Any?) {

    }

    override fun showLoading() {

    }

    override fun hideLoading() {
        smartRefreshLayout.finishRefresh()
    }

    override fun showMessage(message: String) {
        ArmsUtils.snackbarText(message)
    }

    override fun launchActivity(intent: Intent) {
        ArmsUtils.startActivity(intent)
    }

    override fun killMyself() {

    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (isResumed) {
            if (isVisible) {
                uploadData()
                if (smartRefreshLayout.state == RefreshState.None) {
                    mPresenter?.getShoppingMallData()
                }
            } else {
                // 不在最前端显示
                if (smartRefreshLayout.state != RefreshState.None) {
                    smartRefreshLayout.finishRefresh()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
            uploadData()
            if (smartRefreshLayout.state == RefreshState.None) {
                //点击悬浮窗按钮返回此页刷新页面
                if (isClickFloat) {
                    isClickFloat = false
                } else {
                    mPresenter?.getShoppingMallData()
                }
            }
    }

    private fun uploadData() {
        // 上传GPS
        UploadService.uploadUserInfo(activity!!, UploadService.UPLOAD_TYPE_GPS)
        if (SPUtils.getInstance().getString(SPConstant.TOKEN).isNotEmpty()) {
            // 每次成功调调用 进行用户信息收集
            val oldMillis = SPUtils.getInstance().getLong(SPConstant.UPLOAD_DATA_TIME)
            val newMillis = System.currentTimeMillis()
            val difference = (newMillis - oldMillis) > 1000 * 60 * 60 * 24

            // 检查联系人权限
            activity!!?.let {
                if (ContextCompat.checkSelfPermission(it, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
                    if (difference) {
                        SPUtils.getInstance().put(SPConstant.UPLOAD_DATA_TIME, newMillis)
                        // 上传 联系人
                        UploadService.uploadUserInfo(activity!!, UploadService.UPLOAD_TYPE_ADDRESS_BOOK)
                    }
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        if (smartRefreshLayout.state != RefreshState.None) {
            smartRefreshLayout.finishRefresh()
        }
    }

    override fun getLoginMessageSuccess(title: String, message: String) {
        cl.visibility = View.VISIBLE
        tvTitleBottom.text = title
        tvDetail.text = message
    }

    override fun getShoppingMallDataSuccess(entity: HomeShoppingMallEntity?, isCache: Boolean) {
        if (entity == null) return
        ivFloatIcon.visibility = View.VISIBLE
        if (!isCache && !entity.tan.isNullOrEmpty() && entity.tan == "1") {
            if (!TextUtils.isEmpty(entity.tan_thumb)) {
                LoadImageUtils.downloadImageAftershow(mContext, entity.tan_thumb ?: "", ivFloatIcon)
            }
        } else {
            ivFloatIcon.visibility = View.GONE
        }

        list.clear()
        // 标题
        initTitle()
        // 轮播图
        initBanner(entity)
        // 分类
        initClassify(entity)
        // 活动
        initActivity(entity)
        // 热卖单品
        initHotGoods(entity)
        // 专区
        initSpecial(entity)
        // 落底
        initEnd()

        adapter?.setNewData(list)
    }

    private fun initEnd() {
        val shoppingMallEntity = ShoppingMallEntity()
        shoppingMallEntity.itemType = ShoppingMallEntity.ITEM_VIEW_TYPE_END
        list.add(shoppingMallEntity)
    }

    private fun initSpecial(homeShoppingMallEntity: HomeShoppingMallEntity?) {
        var specialList = homeShoppingMallEntity?.special_list
        if (!specialList.isNullOrEmpty()) {
            for (specialEntity in specialList) {
                val shoppingMallEntity = ShoppingMallEntity()
                shoppingMallEntity.itemType = ShoppingMallEntity.ITEM_VIEW_TYPE_MALL_ZONE
                shoppingMallEntity.specialEntity = specialEntity
                list.add(shoppingMallEntity)
                if (showLabel == -1)
                    showLabel = list.size - 2
            }
        }
    }

    private fun initHotGoods(homeShoppingMallEntity: HomeShoppingMallEntity?) {
        if (!homeShoppingMallEntity?.hot_goods_list.isNullOrEmpty()) {
            val shoppingMallEntity = ShoppingMallEntity()
            shoppingMallEntity.itemType = ShoppingMallEntity.ITEM_VIEW_TYPE_HOT_GOODS
            shoppingMallEntity.hotGoodsList = homeShoppingMallEntity?.hot_goods_list
            list.add(shoppingMallEntity)
        }
    }

    private fun initActivity(homeShoppingMallEntity: HomeShoppingMallEntity?) {
        if (homeShoppingMallEntity != null && !homeShoppingMallEntity.activity_list.isNullOrEmpty()) {
            val shoppingMallEntity = ShoppingMallEntity()
            shoppingMallEntity.itemType = ShoppingMallEntity.ITEM_VIEW_TYPE_ACTIVITY
            shoppingMallEntity.activityList = homeShoppingMallEntity.activity_list
            list.add(shoppingMallEntity)
        }
    }

    private fun initClassify(homeShoppingMallEntity: HomeShoppingMallEntity?) {
        val shoppingMallEntity = ShoppingMallEntity()
        shoppingMallEntity.itemType = ShoppingMallEntity.ITEM_VIEW_TYPE_CLASSIFY
        shoppingMallEntity.categoryList = homeShoppingMallEntity?.category_list
        list.add(shoppingMallEntity)
    }

    private fun initBanner(homeShoppingMallEntity: HomeShoppingMallEntity?) {
        if (!homeShoppingMallEntity?.banner_list.isNullOrEmpty()) {
            val shoppingMallEntity = ShoppingMallEntity()
            shoppingMallEntity.itemType = ShoppingMallEntity.ITEM_VIEW_TYPE_BANNER
            shoppingMallEntity.bannerList = homeShoppingMallEntity?.banner_list
            list.add(shoppingMallEntity)
        }
    }

    private fun initImageFloat() {
        //初始化悬浮窗位置
        var layoutParams_up = ivFloatIcon.getLayoutParams() as FrameLayout.LayoutParams
        layoutParams_up.leftMargin = ArmsUtils.getScreenWidth(activity!!) - layoutParams_up.width
        layoutParams_up.topMargin = getAppScreenHeight() - (layoutParams_up.height + bottomNavigationHeight)
        ivFloatIcon.setLayoutParams(layoutParams_up)
        ivFloatIcon.setClickable(true)
        ivFloatIcon.setOnTouchListener(View.OnTouchListener { v, event ->
            val rawX = event.rawX.toInt()
            val rawY = event.rawY.toInt()
            when (event.action) {
                MotionEvent.ACTION_UP -> {
                    newX = rawX
                    newY = rawY
                    if (!(Math.abs(newX - lastX) > ArmsUtils.dip2px(activity!!!!, 5f) || Math.abs(newY - lastY) > ArmsUtils.dip2px(activity!!, 5f)) && System.currentTimeMillis() - downLong < 150) {
                        activity!!.startActivity(Intent(activity!!, FloatDetailedActivity::class.java))
                        isClickFloat = true
                        if (isLeft) {
                            layoutParams_up.leftMargin = 0
                            v.layoutParams = layoutParams_up
                        } else {
                            layoutParams_up.leftMargin = ArmsUtils.getScreenWidth(activity!!) - v.width
                            v.layoutParams = layoutParams_up
                        }
                    } else {
                        if (v.left <= (ArmsUtils.getScreenWidth(activity!!) - v.width) / 2) {
                            val animation = TranslateAnimation(0f, (-layoutParams_up.leftMargin).toFloat(), 0f, 0f)
                            animation.interpolator = LinearInterpolator()
                            animation.duration = 100
                            animation.setAnimationListener(object : Animation.AnimationListener {
                                override fun onAnimationStart(animation: Animation) {}

                                override fun onAnimationEnd(animation: Animation) {
                                    layoutParams_up.leftMargin = 0
                                    v.layoutParams = layoutParams_up
                                    isLeft = true
                                    animation.cancel()
                                    v.clearAnimation()
                                }

                                override fun onAnimationRepeat(animation: Animation) {

                                }
                            })
                            v.startAnimation(animation)
                        } else {
                            val animation = TranslateAnimation(0f, (ArmsUtils.getScreenWidth(activity!!) - v.width - layoutParams_up.leftMargin).toFloat(), 0f, 0f)
                            animation.duration = 100
                            animation.setAnimationListener(object : Animation.AnimationListener {
                                override fun onAnimationStart(animation: Animation) {

                                }

                                override fun onAnimationEnd(animation: Animation) {
                                    layoutParams_up.leftMargin = ArmsUtils.getScreenWidth(activity!!) - v.width
                                    v.layoutParams = layoutParams_up
                                    isLeft = false
                                    animation.cancel()
                                    v.clearAnimation()
                                }

                                override fun onAnimationRepeat(animation: Animation) {

                                }
                            })
                            v.startAnimation(animation)
                        }
                    }
                }
                MotionEvent.ACTION_MOVE -> {
                    val layoutParams_down = v.layoutParams as FrameLayout.LayoutParams
                    if (isLeft) {//判断初始是否在左边
                        if (rawX - lastX > 0 && rawX - lastX <= ArmsUtils.getScreenWidth(activity) - ivFloatIcon.getWidth()) {//正常范围
                            layoutParams_down.leftMargin = rawX - lastX
                        } else if (rawX - lastX > ArmsUtils.getScreenWidth(activity) - ivFloatIcon.getWidth()) {//向右超出屏幕
                            layoutParams_down.leftMargin = ArmsUtils.getScreenWidth(activity) - ivFloatIcon.getWidth()
                        } else {//向左超出屏幕
                            layoutParams_down.leftMargin = 0
                        }
                        //可用区域=屏幕的真实高度-状态栏-底部导航栏-虚拟菜单栏 85是图标的高度 首页底部菜单高度((MainActivity) getActivity()).getBottomNavigationHeight()
                        if (marginTop + (rawY - lastY) > QMUIDisplayHelper.getStatusBarHeight(activity) && marginTop + (rawY - lastY) <= getAppScreenHeight() - ivFloatIcon.getHeight() - bottomNavigationHeight) {
                            layoutParams_down.topMargin = marginTop + (rawY - lastY)
                        } else if (marginTop + (rawY - lastY) <= QMUIDisplayHelper.getStatusBarHeight(activity)) {//上滑超出屏幕
                            layoutParams_down.topMargin = QMUIDisplayHelper.getStatusBarHeight(activity)
                        } else {//下滑超出屏幕
                            //可用区域=屏幕的真实高度-状态栏-底部导航栏-虚拟菜单栏 85是图标的高度 首页底部菜单高度((MainActivity) getActivity()).getBottomNavigationHeight()
                            layoutParams_down.topMargin = getAppScreenHeight() - ivFloatIcon.getHeight() - bottomNavigationHeight
                        }
                    } else {
                        if (rawX - lastX > 0) {//向右超出屏幕
                            layoutParams_down.leftMargin = ArmsUtils.getScreenWidth(activity) - v.width
                        } else if (rawX - lastX <= 0 && rawX - lastX > -(ArmsUtils.getScreenWidth(activity) - ivFloatIcon.getWidth())) {//正常范围
                            layoutParams_down.leftMargin = ArmsUtils.getScreenWidth(activity) - v.width + (rawX - lastX)
                        } else {//向左超出屏幕
                            layoutParams_down.leftMargin = 0
                        }
                        //可用区域=屏幕的真实高度-状态栏-底部导航栏-虚拟菜单栏 85是图标的高度 首页底部菜单高度((MainActivity) getActivity()).getBottomNavigationHeight()
                        if (marginTop + (rawY - lastY) > QMUIDisplayHelper.getStatusBarHeight(activity) && marginTop + (rawY - lastY) <= getAppScreenHeight() - ivFloatIcon.getHeight() - bottomNavigationHeight) {
                            layoutParams_down.topMargin = marginTop + (rawY - lastY)
                        } else if (marginTop + (rawY - lastY) <= QMUIDisplayHelper.getStatusBarHeight(activity)) {
                            layoutParams_down.topMargin = QMUIDisplayHelper.getStatusBarHeight(activity)
                        } else {
                            //可用区域=屏幕的真实高度-状态栏-底部导航栏-虚拟菜单栏 85是图标的高度 首页底部菜单高度((MainActivity) getActivity()).getBottomNavigationHeight()
                            layoutParams_down.topMargin = getAppScreenHeight() - ivFloatIcon.getHeight() - bottomNavigationHeight
                        }
                    }
                    v.layoutParams = layoutParams_down
                }
                MotionEvent.ACTION_DOWN -> {
                    lastX = rawX
                    lastY = rawY
                    marginTop = v.top
                    downLong = System.currentTimeMillis()
                }
            }
            true
        })
    }

    //获取APP可用高度来确定底部滑动位置
    fun getAppScreenHeight(): Int {
        return QMUIDisplayHelper.getUsefulScreenHeight(activity) - ViewUtil.getNavigationBarHeight(activity!!)
    }

    private fun initTitle() {
        val shoppingMallEntity = ShoppingMallEntity()
        shoppingMallEntity.itemType = ShoppingMallEntity.ITEM_VIEW_TYPE_TOP_TITLE
        shoppingMallEntity.title = "商城"
        list.add(shoppingMallEntity)
    }
}
