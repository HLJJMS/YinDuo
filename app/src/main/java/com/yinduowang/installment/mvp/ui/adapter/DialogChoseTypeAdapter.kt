package com.yinduowang.installment.mvp.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.yinduowang.installment.R
import com.yinduowang.installment.mvp.model.entity.LoanPurpose

class DialogChoseTypeAdapter(data: List<LoanPurpose>?) : BaseQuickAdapter<LoanPurpose, BaseViewHolder>(R.layout.layout_dialog_item_chose, data) {
    override fun convert(helper: BaseViewHolder, item: LoanPurpose?) {
        helper.setText(R.id.tv_bank_card,item?.purpose)
    }
}