package com.yinduowang.installment.mvp.model.entity

data class HomeShoppingMallEntity(var banner_list: ArrayList<BannerEntity>?,
                                  var category_list: ArrayList<CategoryEntity>?,
                                  var activity_list: ArrayList<ActivityEntity>?,
                                  var hot_goods_list: ArrayList<HotGoodsEntity>?,
                                  var special_list: ArrayList<SpecialEntity>?,
                                  var tan: String?,
                                  var tan_thumb: String?)

data class BannerEntity(var name: String?, var thumb: String?, var url: String?, var url_type: String?)

data class CategoryEntity(var c_id: String?, var name: String?, var icon: String?, var id: String?)

data class ActivityEntity(var name: String?, var thumb: String?, var url: String?)

data class HotGoodsEntity(var id: String?, var name: String?, var thumb: String?, var cycles: String?,
                          var price: String?, var max_cycle: String?, var min_money: String?)

data class SpecialEntity(var id: String?, var t_id: String?, var name: String?, var banner_data: SpecialBannerEntity?,
                         var thumb_list: ArrayList<SpecialBannerEntity>, var goods_list: ArrayList<HotGoodsEntity>)

data class SpecialBannerEntity(var id: String?, var name: String?, var thumb: String?)