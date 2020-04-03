package com.yinduowang.installment.mvp.ui.adapter

import android.content.Intent
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.jess.arms.utils.ArmsUtils
import com.yinduowang.installment.R
import com.yinduowang.installment.app.utils.LoadImageUtils
import com.yinduowang.installment.mvp.model.entity.ShopOrder
import com.yinduowang.installment.mvp.ui.activity.ShopOrderDetailedActivity

/**
 * @Description:我的-全部订单适配器
 * @Author: 张利超
 * @Date: 2019-10-31 10:26:52
 * @Version: v1.0, 2019-10-31
 * @LastEditors:张利超
 * @LastEditTime: 2019-10-31 10:26:52
 * @Deprecated: false
 */
class ShopAllOrderAdapter(data: List<ShopOrder>) : BaseQuickAdapter<ShopOrder, BaseViewHolder>(R.layout.item_shop_all_order, data) {
    var type: StringBuffer = StringBuffer()

    override fun convert(helper: BaseViewHolder, item: ShopOrder) {
        //订单状态:默认99全部-3审核驳回状态-2已退货-1已取消0待付款(已下单)1待发货(已支付)2待收货(已发货)3已完成(已收货)
        if (item.order_status.equals("-3")) {
            helper.setGone(R.id.tv_confirm_pay, true)
            helper.setText(R.id.tv_confirm_pay, "再次购买")
            helper.setGone(R.id.tv_cancle_order, false)
            helper.setTextColor(R.id.tv_status, ArmsUtils.getColor(mContext, R.color.color_9A9A9A))
        } else if (item.order_status.equals("-2")) {
            helper.setGone(R.id.tv_confirm_pay, true)
            helper.setText(R.id.tv_confirm_pay, "再次购买")
            helper.setGone(R.id.tv_cancle_order, false)
            helper.setTextColor(R.id.tv_status, ArmsUtils.getColor(mContext, R.color.color_9A9A9A))
        } else if (item.order_status.equals("-1")) {
            helper.setGone(R.id.tv_confirm_pay, true)
            helper.setText(R.id.tv_confirm_pay, "再次购买")
            helper.setGone(R.id.tv_cancle_order, false)
            helper.setTextColor(R.id.tv_status, ArmsUtils.getColor(mContext, R.color.color_9A9A9A))
        } else if (item.order_status.equals("0")) {
            helper.setTextColor(R.id.tv_status, ArmsUtils.getColor(mContext, R.color.color_FF7213))
            helper.setGone(R.id.tv_confirm_pay, true)
            helper.setGone(R.id.tv_cancle_order, true)
            helper.setText(R.id.tv_cancle_order, "取消订单")
            helper.setTextColor(R.id.tv_cancle_order, ArmsUtils.getColor(mContext, R.color.color_9A9A9A))
            helper.setText(R.id.tv_confirm_pay, "立即付款")
        } else if (item.order_status.equals("1")) {
            helper.setTextColor(R.id.tv_status, ArmsUtils.getColor(mContext, R.color.color_FF7213))
            helper.setGone(R.id.tv_confirm_pay, false)
            helper.setGone(R.id.tv_cancle_order, false)
        } else if (item.order_status.equals("2")) {
            helper.setTextColor(R.id.tv_status, ArmsUtils.getColor(mContext, R.color.color_FF7213))
            helper.setGone(R.id.tv_confirm_pay, true)
            helper.setGone(R.id.tv_cancle_order, true)
            helper.setText(R.id.tv_confirm_pay, "确认收货")
            helper.setText(R.id.tv_cancle_order, "查看物流")
            helper.setTextColor(R.id.tv_cancle_order, ArmsUtils.getColor(mContext, R.color.color_333333))
        } else if (item.order_status.equals("3")) {
            helper.setTextColor(R.id.tv_status, ArmsUtils.getColor(mContext, R.color.color_9A9A9A))
            helper.setGone(R.id.tv_cancle_order, true)
            helper.setGone(R.id.tv_confirm_pay, false)
            helper.setText(R.id.tv_cancle_order, "查看物流")
            helper.setTextColor(R.id.tv_cancle_order, ArmsUtils.getColor(mContext, R.color.color_333333))
        } else if (item.order_status.equals("4")) {
            helper.setTextColor(R.id.tv_status, ArmsUtils.getColor(mContext, R.color.color_365AF7))
            helper.setGone(R.id.tv_cancle_order, false)
            helper.setGone(R.id.tv_confirm_pay, false)
            helper.setTextColor(R.id.tv_cancle_order, ArmsUtils.getColor(mContext, R.color.color_333333))
        } else {
            helper.setTextColor(R.id.tv_status, ArmsUtils.getColor(mContext, R.color.color_9A9A9A))
            helper.setGone(R.id.tv_confirm_pay, false)
            helper.setGone(R.id.tv_cancle_order, false)
        }
        helper.setText(R.id.tv_status, item.status_name)
        helper.setText(R.id.tv_order_time, item.time)
        helper.setText(R.id.tv_total_money, "合计：¥${item.total_money}")
        //里边列表做非空判断
        item.goods_list.firstOrNull()?.let {
            it.av_names.firstOrNull()?.let { ib ->
                helper.setText(R.id.tv_av_names, ib)
            }
            helper.setText(R.id.tvName, it.name)
            helper.setText(R.id.tv_price, "￥"+it.price)
            helper.setText(R.id.tv_counts, it.number)
            it.thumb?.let { ic ->
                LoadImageUtils.showImage(mContext, ic, helper.getView<ImageView>(R.id.iv_thumb))
            }
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
        }
        helper.addOnClickListener(R.id.tv_cancle_order)
        helper.addOnClickListener(R.id.tv_confirm_pay)
    }
}