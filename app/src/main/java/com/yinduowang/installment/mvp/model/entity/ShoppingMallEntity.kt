package com.yinduowang.installment.mvp.model.entity

import com.chad.library.adapter.base.entity.MultiItemEntity

class ShoppingMallEntity : MultiItemEntity {

    var title: String? = ""
    var bannerList: ArrayList<BannerEntity>? = null
    var categoryList: ArrayList<CategoryEntity>? = null
    var activityList: ArrayList<ActivityEntity>? = null
    var hotGoodsList: ArrayList<HotGoodsEntity>? = null
    var specialEntity: SpecialEntity? = null


    companion object {
        // 标题
        const val ITEM_VIEW_TYPE_TOP_TITLE = 0
        // 轮播图
        const val ITEM_VIEW_TYPE_BANNER = ITEM_VIEW_TYPE_TOP_TITLE + 1
        // 分类
        const val ITEM_VIEW_TYPE_CLASSIFY = ITEM_VIEW_TYPE_BANNER + 1
        // 活动
        const val ITEM_VIEW_TYPE_ACTIVITY = ITEM_VIEW_TYPE_CLASSIFY + 1
        // 热卖单品
        const val ITEM_VIEW_TYPE_HOT_GOODS = ITEM_VIEW_TYPE_ACTIVITY + 1
        // 商城专区
        const val ITEM_VIEW_TYPE_MALL_ZONE = ITEM_VIEW_TYPE_HOT_GOODS + 1
        // 落地
        const val ITEM_VIEW_TYPE_END = ITEM_VIEW_TYPE_MALL_ZONE + 1
    }

    private var itemType = ITEM_VIEW_TYPE_TOP_TITLE

    fun setItemType(itemType: Int) {
        this.itemType = itemType
    }

    override fun getItemType(): Int {
        return itemType
    }

}