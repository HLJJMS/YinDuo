package com.yinduowang.installment.mvp.ui.adapter

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.yinduowang.installment.R
import com.yinduowang.installment.app.utils.LoadImageUtils
import com.yinduowang.installment.mvp.model.entity.ShopOrder

/**
 * Description：售后列表
 * Author：田羽衡
 * Version：<v1.0，2019/11/1 >
 * LastEditTime：2019/11/1
 * LastEditors:
 * Deprecated： false
 */
class ShopAfterSaleAdapter(data: List<ShopOrder>?) : BaseQuickAdapter<ShopOrder, BaseViewHolder>(R.layout.item_after_sale, data) {
    override fun convert(helper: BaseViewHolder, item: ShopOrder?) {
        helper?.setText(R.id.tvId, "订单编号: " + item?.sn)
        //里边列表做非空判断
        item?.goods_list?.firstOrNull()?.let {
            helper?.setText(R.id.tvName, item?.goods_list.get(0).name)
            var img = helper?.getView<ImageView>(R.id.ivImg) as ImageView
            if (it.thumb != null) {
                LoadImageUtils.showImage(mContext, it.thumb, img, R.color.color_F6F6F6)
            }
        }
    }
}
