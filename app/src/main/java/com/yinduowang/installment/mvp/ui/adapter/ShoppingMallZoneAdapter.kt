package com.yinduowang.installment.mvp.ui.adapter

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.jess.arms.utils.ArmsUtils
import com.qmuiteam.qmui.layout.QMUILinearLayout
import com.yinduowang.installment.R
import com.yinduowang.installment.app.utils.LoadImageUtils
import com.yinduowang.installment.app.utils.OpenWebViewUtils
import com.yinduowang.installment.mvp.model.entity.HotGoodsEntity


class ShoppingMallZoneAdapter(data: ArrayList<HotGoodsEntity>) : BaseQuickAdapter<HotGoodsEntity, BaseViewHolder>(R.layout.item_shopping_mall_zone_commodity, data) {
    override fun convert(helper: BaseViewHolder, item: HotGoodsEntity) {
        var qmuiLinearLayout = helper.getView(R.id.qmuiLinearLayout) as QMUILinearLayout
        qmuiLinearLayout.shadowElevation = ArmsUtils.dip2px(mContext, 5f)
        qmuiLinearLayout.shadowAlpha = 0.35f
        qmuiLinearLayout.setBackgroundResource(R.color.white)
        qmuiLinearLayout.radius = ArmsUtils.dip2px(mContext, 2f)
        var qmuiCommodity = helper.getView(R.id.qmuiCommodity) as QMUILinearLayout
        qmuiCommodity.radius = ArmsUtils.dip2px(mContext, 2f)

        var imageView = helper.getView<ImageView>(R.id.ivCommodity)
        var thumb = item.thumb
        if (thumb != null && imageView != null) {
            LoadImageUtils.showImage(mContext, thumb, imageView, R.color.color_FAFAFA)
        }
        helper.setText(R.id.tvCommodityName, item.name)
        helper.setText(R.id.tvAll, item.price)
        helper.setText(R.id.tvTerminally, item.min_money)
        helper.setText(R.id.tvPeriods, "×${item.max_cycle}期")
        if (data.size >= 2) {
            if (helper.layoutPosition == data.size - 1 || helper.layoutPosition == data.size - 2) {
                helper.setGone(R.id.vBottomEmpty, true)
            } else {
                helper.setGone(R.id.vBottomEmpty, false)
            }
        }
        helper.itemView.setOnClickListener {
            OpenWebViewUtils.openWebViewGoodsDetails(mContext, item.id + "", false)
        }
    }
}