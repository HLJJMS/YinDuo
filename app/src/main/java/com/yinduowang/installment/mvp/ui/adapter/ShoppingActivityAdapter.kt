package com.yinduowang.installment.mvp.ui.adapter

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.yinduowang.installment.R
import com.yinduowang.installment.app.utils.LoadImageUtils
import com.yinduowang.installment.app.utils.OpenWebViewUtils
import com.yinduowang.installment.mvp.model.entity.ActivityEntity


class ShoppingActivityAdapter(data: ArrayList<ActivityEntity>) : BaseQuickAdapter<ActivityEntity, BaseViewHolder>(R.layout.item_shopping_mall_activity_two, data) {
    override fun convert(helper: BaseViewHolder, item: ActivityEntity?) {
        if (helper != null && item != null) {
            var imageView = helper.getView<ImageView>(R.id.ivActivity)
            var url = item.url
            var thumb = item.thumb
            if (thumb != null && imageView != null) {
                LoadImageUtils.showImage(mContext, thumb, imageView, R.color.color_FAFAFA)
            }
            if (url != null) {
                imageView.setOnClickListener {
                    OpenWebViewUtils.openWebViewTypeFromUrl(mContext, url, null)
                }
            }
            if (data.size >= 2)
                if (helper.layoutPosition == data.size - 1 || helper.layoutPosition == data.size - 2) {
                    helper.setGone(R.id.vBottomLine, false)
                } else {
                    helper.setGone(R.id.vBottomLine, true)
                }
        }
    }
}