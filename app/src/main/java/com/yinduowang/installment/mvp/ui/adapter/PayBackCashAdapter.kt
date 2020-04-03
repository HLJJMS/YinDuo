package com.yinduowang.installment.mvp.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.yinduowang.installment.R
import com.yinduowang.installment.mvp.model.entity.PaymentRecord
class PayBackCashAdapter(data: List<PaymentRecord>) : BaseQuickAdapter<PaymentRecord, BaseViewHolder>(R.layout.item_pay_back_cash_record, data) {
    override fun convert(helper: BaseViewHolder, item: PaymentRecord) {
        helper.setText(R.id.tv_pay_back_style, item.repaymentType)
        helper.setText(R.id.tv_pay_back_money,item.repaymentFund   )
        helper.setText(R.id.tv_pay_back_time,item.repaymentDate   )

    }
}