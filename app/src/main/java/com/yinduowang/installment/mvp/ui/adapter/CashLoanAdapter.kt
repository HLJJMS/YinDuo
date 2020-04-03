package com.yinduowang.installment.mvp.ui.adapter

import android.content.Intent
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.jess.arms.utils.ArmsUtils
import com.yinduowang.installment.R
import com.yinduowang.installment.mvp.model.entity.RecordInfoResponse
import com.yinduowang.installment.mvp.ui.activity.CashLoanDetailedActivity

class CashLoanAdapter(data: List<RecordInfoResponse>?) : BaseQuickAdapter<RecordInfoResponse, BaseViewHolder>(R.layout.item_cash_loan_record, data) {
    override fun convert(helper: BaseViewHolder, item: RecordInfoResponse) {
        helper.setText(R.id.tv_pay_back_money,item.loanFund  )
        helper.setText(R.id.tv_pay_back_time,item.applicationTime  )
        helper.setText(R.id.tv_loan_status,item.deductStatus)
        helper.setText(R.id.tv_pay_back_money_right,"借款")
        helper.setText(R.id.tv_pay_back_money_left,"元")
        //借款状态标识1审核中2审核未通过3待还款4还款中5已结清6已逾期
        if ("1".equals(item.statusFlag)){
            helper.setTextColor(R.id.tv_loan_status, ArmsUtils.getColor(mContext,R.color.color_333333 ))
        } else if ("2".equals(item.statusFlag)){
            helper.setTextColor(R.id.tv_loan_status, ArmsUtils.getColor(mContext,R.color.color_FF3763 ))
        } else if ("3".equals(item.statusFlag)){
            helper.setTextColor(R.id.tv_loan_status, ArmsUtils.getColor(mContext,R.color.color_333333 ))
        }else if("4".equals(item.statusFlag)){
            helper.setTextColor(R.id.tv_loan_status, ArmsUtils.getColor(mContext,R.color.color_333333 ))
        }else if("5".equals(item.statusFlag)){
            helper.setTextColor(R.id.tv_loan_status, ArmsUtils.getColor(mContext,R.color.color_9A9A9A ))
        }else if("6".equals(item.statusFlag)){
            helper.setText(R.id.tv_loan_status,item.deductStatus+item.overDueDays+"天")
            helper.setTextColor(R.id.tv_loan_status, ArmsUtils.getColor(mContext,R.color.color_FF3763 ))
        }
        helper.itemView.setOnClickListener { v ->
            val intent = Intent(mContext, CashLoanDetailedActivity::class.java)
            intent.putExtra("type","3")
            intent.putExtra("loanId", item.loanId )
            mContext.startActivity(intent)
        }
    }
}