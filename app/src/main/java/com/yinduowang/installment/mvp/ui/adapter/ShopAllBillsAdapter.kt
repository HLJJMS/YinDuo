package com.yinduowang.installment.mvp.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.jess.arms.utils.ArmsUtils
import com.yinduowang.installment.R
import com.yinduowang.installment.mvp.model.entity.BillResponse

class ShopAllBillsAdapter(data: List<BillResponse>) : BaseQuickAdapter<BillResponse, BaseViewHolder>(R.layout.item_all_bills, data) {
    override fun convert(helper: BaseViewHolder, item: BillResponse) {
        helper.setText(R.id.tvMonth, "${item.month}月")
        helper.setText(R.id.tvBillsMoney, item.billMoney)
        if (item.isClean == "1") {
            helper.setText(R.id.tvPayBackEnd, "已还清")
            helper.setGone(R.id.tvPayBackStart, false)
            helper.setGone(R.id.tvPayBackMoney, false)
            helper.setGone(R.id.tvPayBackEnd, true)
        } else {
            helper.setText(R.id.tvPayBackStart, "剩余")
            helper.setText(R.id.tvPayBackMoney, item.residuePay)
            helper.setText(R.id.tvPayBackEnd, "待还")
            helper.setGone(R.id.tvPayBackStart, true)
            helper.setGone(R.id.tvPayBackMoney, true)
            helper.setGone(R.id.tvPayBackEnd, true)
        }
        if (item.isOverdue == "1") {
            helper.setGone(R.id.ivHadDueDate, true)
        } else {
            helper.setGone(R.id.ivHadDueDate, false)
        }
        if (item.isPresentMonth == "1") {
            helper.setBackgroundRes(R.id.tvMonth, R.drawable.shape_current_month)
            helper.setTextColor(R.id.tvMonth, ArmsUtils.getColor(mContext, R.color.white))
        } else {
            helper.setBackgroundRes(R.id.tvMonth, R.drawable.shape_no_current_month)
            helper.setTextColor(R.id.tvMonth, ArmsUtils.getColor(mContext, R.color.color_9A9A9A))
        }
    }

}