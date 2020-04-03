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
 * @Description:我的-待收货订单适配器
 * @Author: 张利超
 * @Date: 2019-10-31 10:26:52
 * @Version: v1.0, 2019-10-31
 * @LastEditors:张利超
 * @LastEditTime: 2019-10-31 10:26:52
 * @Deprecated: false
 */
class ShopOrderToTakeBackAdapter(data: List<ShopOrder>) : BaseQuickAdapter<ShopOrder, BaseViewHolder>(R.layout.item_shop_back_delivery_order, data) {

    var type: StringBuffer = StringBuffer()

    override fun convert(helper: BaseViewHolder, item: ShopOrder) {
        //订单状态:默认99全部-2已退货-1已取消0待付款(已下单)1待发货(已支付)2待收货(已发货)3已完成(已收货)

        helper.setText(R.id.tv_status, item.status_name)
        helper.setText(R.id.tv_order_time, item.time)
        helper.setText(R.id.tv_total_money, "合计：¥${item.total_money}")
        //里边列表做非空判断
        item.goods_list.firstOrNull()?.let {
            it.av_names.firstOrNull()?.let { ib ->
                helper.setText(R.id.tv_av_names, ib)
            }
            helper.setText(R.id.tv_name, it.name)
            helper.setText(R.id.tv_price, "￥"+it.price)
            helper.setText(R.id.tv_counts, it.number)
            it.thumb?.let { ic ->
                LoadImageUtils.showImage(mContext, ic, helper.getView<ImageView>(R.id.iv_thumb))
            }
            helper.addOnClickListener(R.id.tv_confirm_pay)
            helper.addOnClickListener(R.id.tv_cancle_order)
        }

        type = StringBuffer()
        if (item.goods_list.size > 0) {
            for (item in item.goods_list.get(0).av_names) {
                type.append(item).append(" ")
            }
        }
        helper.setText(R.id.tv_av_names, type)
        helper.itemView.setOnClickListener {
            val intent = Intent(mContext, ShopOrderDetailedActivity::class.java)
            intent.putExtra("orderId", item.id)
            mContext.startActivity(intent)
            helper.setText(R.id.tv_av_names, type)
        }

    }
}