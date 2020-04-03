package com.yinduowang.installment.mvp.ui.adapter

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import android.view.View
import android.widget.ImageView
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.jess.arms.utils.ArmsUtils
import com.qmuiteam.qmui.layout.QMUILinearLayout
import com.yinduowang.installment.R
import com.yinduowang.installment.app.utils.LoadImageUtils
import com.yinduowang.installment.app.utils.OpenWebViewUtils
import com.yinduowang.installment.mvp.model.entity.ShoppingMallEntity
import com.yinduowang.installment.mvp.model.entity.SpecialEntity
import com.youth.banner.Banner
import com.youth.banner.listener.OnBannerListener
import com.youth.banner.loader.ImageLoader


class ShoppingMallAdapter(data: ArrayList<ShoppingMallEntity>) : BaseMultiItemQuickAdapter<ShoppingMallEntity, BaseViewHolder>(data) {

    init {
        addItemType(ShoppingMallEntity.ITEM_VIEW_TYPE_TOP_TITLE, R.layout.item_shopping_mall_title)
        addItemType(ShoppingMallEntity.ITEM_VIEW_TYPE_BANNER, R.layout.item_shopping_mall_banner)
        addItemType(ShoppingMallEntity.ITEM_VIEW_TYPE_CLASSIFY, R.layout.item_shopping_mall_classify)
        addItemType(ShoppingMallEntity.ITEM_VIEW_TYPE_ACTIVITY, R.layout.item_shopping_mall_activity)
        addItemType(ShoppingMallEntity.ITEM_VIEW_TYPE_HOT_GOODS, R.layout.item_shopping_mall_hot_goods)
        addItemType(ShoppingMallEntity.ITEM_VIEW_TYPE_MALL_ZONE, R.layout.item_shopping_mall_zone)
        addItemType(ShoppingMallEntity.ITEM_VIEW_TYPE_END, R.layout.item_shopping_end)
    }

    override fun convert(helper: BaseViewHolder, item: ShoppingMallEntity?) {
        if (item == null) {
            return
        }
        when (helper.itemViewType) {
            ShoppingMallEntity.ITEM_VIEW_TYPE_TOP_TITLE -> {
                setTitle(helper, item)
            }
            ShoppingMallEntity.ITEM_VIEW_TYPE_BANNER -> {
                setBanner(helper, item)
            }
            ShoppingMallEntity.ITEM_VIEW_TYPE_CLASSIFY -> {
                setClassify(helper, item)
            }
            ShoppingMallEntity.ITEM_VIEW_TYPE_ACTIVITY -> {
                setActivity(item, helper)
            }
            ShoppingMallEntity.ITEM_VIEW_TYPE_HOT_GOODS -> {
                setHotGoods(helper, item)
            }
            ShoppingMallEntity.ITEM_VIEW_TYPE_MALL_ZONE -> {
                setMallZone(helper, item)
            }
            ShoppingMallEntity.ITEM_VIEW_TYPE_END -> {
            }
        }
    }

    private fun setMallZone(helper: BaseViewHolder, item: ShoppingMallEntity) {
        var specialEntity: SpecialEntity? = item.specialEntity ?: return
        helper.setText(R.id.tvMallZoneTitle, specialEntity?.name)
        helper.getView<View>(R.id.vMore).setOnClickListener {
            goDistrict(specialEntity?.id + "")
        }
        if (specialEntity?.banner_data != null && !specialEntity.banner_data?.thumb.isNullOrEmpty()) {
            val qmuiLinearLayout = helper.getView<QMUILinearLayout>(R.id.qmuiMallZoneBanner)
            qmuiLinearLayout.radius = ArmsUtils.dip2px(mContext, 3f)
            helper.setGone(R.id.qmuiMallZoneBanner, true)
            specialEntity.banner_data?.thumb?.let { LoadImageUtils.showImage(mContext, it, helper.getView(R.id.ivMallZoneBanner), R.color.color_FAFAFA) }
            helper.getView<View>(R.id.ivMallZoneBanner).setOnClickListener {
                OpenWebViewUtils.openWebViewDistrict(mContext, specialEntity.banner_data?.id + "")
            }
        } else {
            helper.setGone(R.id.qmuiMallZoneBanner, false)
        }
        val thumbList = specialEntity?.thumb_list
        if (thumbList != null) {
            var qmuiLinearLayout = helper.getView(R.id.qmuiLinearLayout) as QMUILinearLayout
            qmuiLinearLayout.shadowElevation = ArmsUtils.dip2px(mContext, 10f)
            qmuiLinearLayout.shadowAlpha = 0.35f
            qmuiLinearLayout.setBackgroundResource(R.color.white)

            val rvActivity = helper.getView<RecyclerView>(R.id.rvMallZoneActivity)
            rvActivity.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            val adapter = ShoppingMallZoneThumbAdapter(thumbList)
            rvActivity.adapter = adapter
        }
        val goodsList = specialEntity?.goods_list
        if (goodsList != null) {
            val rvMallZone = helper.getView<RecyclerView>(R.id.rvMallZone)
            rvMallZone.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            val adapter = ShoppingMallZoneAdapter(goodsList)
            rvMallZone.adapter = adapter
        }
    }

    private fun setHotGoods(helper: BaseViewHolder, item: ShoppingMallEntity) {
        val hotGoodsList = item.hotGoodsList
        if (hotGoodsList != null) {
            val rvHotGoods = helper.getView<RecyclerView>(R.id.rvHotGoods)
            val manager = LinearLayoutManager(mContext)
            manager.orientation = LinearLayoutManager.HORIZONTAL
            rvHotGoods.layoutManager = manager
            val adapter = ShoppingHotGoodsAdapter(hotGoodsList)
            rvHotGoods.adapter = adapter
        }
    }

    private fun setActivity(item: ShoppingMallEntity, helper: BaseViewHolder) {
        var qmuiLinearLayout = helper.itemView as QMUILinearLayout
        qmuiLinearLayout.shadowElevation = ArmsUtils.dip2px(mContext, 10f)
        qmuiLinearLayout.shadowColor = ContextCompat.getColor(mContext, R.color.black)
        qmuiLinearLayout.shadowAlpha = 0.35f
        qmuiLinearLayout.setBackgroundResource(R.color.white)
        val activityList = item.activityList
        if (activityList != null) {
            val rvActivity = helper.getView<RecyclerView>(R.id.rvActivity)
            rvActivity.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            val adapter = ShoppingActivityAdapter(activityList)
            rvActivity.adapter = adapter
        }
    }

    private fun setClassify(helper: BaseViewHolder, item: ShoppingMallEntity) {
        var categoryList = item.categoryList
        if (categoryList != null) {
            categoryList[0].icon?.let { LoadImageUtils.showImage(mContext, it, helper.getView(R.id.ivOne), R.drawable.shape_circle_gray) }
            helper.setText(R.id.tvOne, categoryList[0].name)
            categoryList[1].icon?.let { LoadImageUtils.showImage(mContext, it, helper.getView(R.id.ivTwo), R.drawable.shape_circle_gray) }
            helper.setText(R.id.tvTwo, categoryList[1].name)
            categoryList[2].icon?.let { LoadImageUtils.showImage(mContext, it, helper.getView(R.id.ivThree), R.drawable.shape_circle_gray) }
            helper.setText(R.id.tvThree, categoryList[2].name)
            categoryList[3].icon?.let { LoadImageUtils.showImage(mContext, it, helper.getView(R.id.ivFour), R.drawable.shape_circle_gray) }
            helper.setText(R.id.tvFour, categoryList[3].name)

            helper.getView<View>(R.id.vOne).setOnClickListener {
                goClassify(categoryList[0].id + "")
            }
            helper.getView<View>(R.id.vTwo).setOnClickListener {
                goClassify(categoryList[1].id + "")
            }
            helper.getView<View>(R.id.vThree).setOnClickListener {
                goClassify(categoryList[2].id + "")
            }
            helper.getView<View>(R.id.vFour).setOnClickListener {
                goClassify(categoryList[3].id + "")
            }
            helper.getView<View>(R.id.vMore).setOnClickListener {
                goGoods()
            }
        }
    }

    /**
     * 跳转专区
     * */
    private fun goDistrict(id: String) {
        OpenWebViewUtils.openWebViewDistrict(mContext, id)
    }

    /**
     * 跳转分类
     * */
    private fun goGoods() {
        OpenWebViewUtils.openWebViewGoods(mContext)
    }

    /**
     * 跳转分类
     * */
    private fun goClassify(id: String) {
        OpenWebViewUtils.openWebViewGoodsList(mContext, id)
    }

    private fun setBanner(helper: BaseViewHolder, item: ShoppingMallEntity) {
        var qmuiLinearLayout = helper.itemView as QMUILinearLayout
        qmuiLinearLayout.radius = ArmsUtils.dip2px(mContext, 4f)
        var homeBannerPager = helper.getView<Banner>(R.id.banner)
        homeBannerPager.setImageLoader(GlideImageLoader())
        homeBannerPager.setDelayTime(6000)
        homeBannerPager.setOnBannerListener(OnBannerListener { position: Int ->
            if (!item.bannerList.isNullOrEmpty() && item.bannerList?.get(position) != null && !item.bannerList?.get(position)?.url.isNullOrEmpty()) {
                var url = item.bannerList?.get(position)?.url
                var title = item.bannerList?.get(position)?.name
                url?.let { OpenWebViewUtils.openWebViewTypeFromUrl(mContext, it, title) }
            }
        })
        var list = ArrayList<String>()
        if (!item.bannerList.isNullOrEmpty()) {
            for (bannerEntity in item.bannerList!!) {
                bannerEntity.thumb?.let { list.add(it) }
            }
        }
        homeBannerPager.setImages(list)
        homeBannerPager.start()
    }

    private fun setTitle(helper: BaseViewHolder, item: ShoppingMallEntity) {
        helper.setText(R.id.tvTitle, item.title)
    }

    /**
     * banner图片加载
     */
    class GlideImageLoader : ImageLoader() {
        override fun displayImage(context: Context?, path: Any, imageView: ImageView?) {
            if (context != null && imageView != null)
                LoadImageUtils.showImage(context, path as String, imageView)
        }
    }

}