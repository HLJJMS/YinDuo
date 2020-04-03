package com.yinduowang.installment.mvp.ui.adapter

import android.content.Intent
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.yinduowang.installment.R
import com.yinduowang.installment.app.utils.LoadImageUtils
import com.yinduowang.installment.mvp.model.entity.ShopOrder
import com.yinduowang.installment.mvp.ui.activity.ShopOrderDetailedActivity

/**
 * @Description:我的-代付款订单适配器
 * @Author: 张利超
 * @Date: 2019-10-31 10:26:52
 * @Version: v1.0, 2019-10-31
 * @LastEditors:张利超
 * @LastEditTime: 2019-10-31 10:26:52
 * @Deprecated: false
 */
class ShopOrderToPayAdapter(data: List<ShopOrder>) : BaseQuickAdapter<ShopOrder, BaseViewHolder>(R.layout.item_shop_to_pay, data) {
    var type: StringBuffer = StringBuffer()

    override fun convert(helper: BaseViewHolder, item: ShopOrder) {
        helper.setText(R.id.tv_status, item.status_name)
        helper.setText(R.id.tv_order_time, item.time)
        helper.setText(R.id.tv_total_money, "合计：¥${item.total_money}")
        //里边列表做非空判断
        item.goods_list.firstOrNull()?.let {
            it.av_names.firstOrNull()?.let { ib ->
                helper.setText(R.id.tv_av_names, ib)
            }
            helper.setText(R.id.tvName, it.name)
            helper.setText(R.id.tv_price,"￥" + it.price)
            helper.setText(R.id.tv_counts, it.number)
            it.thumb?.let { ic ->
                LoadImageUtils.showImage(mContext, ic, helper.getView<ImageView>(R.id.iv_thumb))
            }
        }
        helper.itemView.setOnClickListener {
            val intent = Intent(mContext, ShopOrderDetailedActivity::class.java)
            intent.putExtra("orderId", item.id)
            mContext.startActivity(intent)
        }
        helper.addOnClickListener(R.id.tv_confirm_pay)
        helper.addOnClickListener(R.id.tv_cancle_order)
        type = StringBuffer()
        if (item.goods_list.size > 0) {
            for (item in item.goods_list.get(0).av_names) {
                type.append(item).append(" ")
            }
        }
        helper.setText(R.id.tv_av_names, type)
    }

}