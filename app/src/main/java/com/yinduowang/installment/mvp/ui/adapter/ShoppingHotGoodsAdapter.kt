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


class ShoppingHotGoodsAdapter(data: ArrayList<HotGoodsEntity>) : BaseQuickAdapter<HotGoodsEntity, BaseViewHolder>(R.layout.item_shopping_mall_hot_goods_two, data) {
    override fun convert(helper: BaseViewHolder, item: HotGoodsEntity?) {
        if (helper != null && item != null) {
            when {
                helper.layoutPosition == 0 -> {
                    helper.setGone(R.id.vStart, false)
                    helper.setGone(R.id.vEnd, false)
                }
                helper.layoutPosition == data.size - 1 -> {
                    helper.setGone(R.id.vStart, true)
                    helper.setGone(R.id.vEnd, true)
                }
                else -> {
                    helper.setGone(R.id.vStart, true)
                    helper.setGone(R.id.vEnd, false)
                }
            }

            var imageView = helper.getView<ImageView>(R.id.ivHotGoods)
            var thumb = item.thumb
            if (thumb != null && imageView != null) {
                LoadImageUtils.showImage(mContext, thumb, imageView, R.color.color_FAFAFA)
            }
            helper.setText(R.id.tvName, item.name)
            helper.setText(R.id.tvAll, item.price)
            helper.setText(R.id.tvTerminally, item.min_money)
            helper.setText(R.id.tvPeriods, "×${item.max_cycle}期")
            if (item.id != null) {
                helper.itemView.setOnClickListener {
                    OpenWebViewUtils.openWebViewGoodsDetails(mContext, item.id + "", false)
                }
            }
            val qmuiLinearLayout = helper.getView(R.id.qmuiLinearLayout) as QMUILinearLayout
            qmuiLinearLayout.radius = ArmsUtils.dip2px(mContext, 4f)
        }
    }
}