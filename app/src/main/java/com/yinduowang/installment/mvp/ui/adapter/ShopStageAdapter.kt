package com.yinduowang.installment.mvp.ui.adapter

import android.content.Intent
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.jess.arms.utils.ArmsUtils
import com.yinduowang.installment.R
import com.yinduowang.installment.mvp.model.entity.RecordInfoResponse
import com.yinduowang.installment.mvp.ui.activity.ShopSatgeLaonDetailedActivity

class ShopStageAdapter(data: List<RecordInfoResponse>) : BaseQuickAdapter<RecordInfoResponse, BaseViewHolder>(R.layout.item_shop_stage_record, data) {
    override fun convert(helper: BaseViewHolder, item: RecordInfoResponse) {
        helper.setText(R.id.tv_pay_back_style, item.loanNumber)
        helper.setText(R.id.tv_pay_back_money, item?.loanFund)
        helper.setText(R.id.tv_pay_back_time, item.applicationTime)
        helper.setText(R.id.tv_loan_status, item.deductStatus)
        helper.setText(R.id.tv_pay_back_money_left, "元")
//        1审核中2审核未通过3待还款4还款中5已结清6已逾期

        if ("1".equals(item.statusFlag)) {
            helper.setTextColor(R.id.tv_loan_status, ArmsUtils.getColor(mContext, R.color.color_333333))
        } else if ("2".equals(item.statusFlag)) {
            helper.setTextColor(R.id.tv_loan_status, ArmsUtils.getColor(mContext, R.color.color_FF3763))
        } else if ("3".equals(item.statusFlag)) {
            helper.setTextColor(R.id.tv_loan_status, ArmsUtils.getColor(mContext, R.color.color_9A9A9A))
        } else if ("4".equals(item.statusFlag)) {
            helper.setTextColor(R.id.tv_loan_status, ArmsUtils.getColor(mContext, R.color.color_9A9A9A))
        } else if ("5".equals(item.statusFlag)) {
            helper.setTextColor(R.id.tv_loan_status, ArmsUtils.getColor(mContext, R.color.color_9A9A9A))
        } else if ("6".equals(item.statusFlag)) {
            helper.setTextColor(R.id.tv_loan_status, ArmsUtils.getColor(mContext, R.color.color_FF3763))
        }
        helper.itemView.setOnClickListener { v ->
            val intent = Intent(mContext, ShopSatgeLaonDetailedActivity::class.java)
            intent.putExtra("id", item.loanId.toString())
            mContext.startActivity(intent)
        }
    }
}