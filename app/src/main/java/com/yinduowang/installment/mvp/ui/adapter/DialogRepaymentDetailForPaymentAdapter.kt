package com.yinduowang.installment.mvp.ui.adapter

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.yinduowang.installment.R
import com.yinduowang.installment.mvp.model.entity.BudgetInfo
import com.yinduowang.installment.mvp.model.entity.BudgetMoneyInfo
import com.yinduowang.installment.mvp.model.entity.GetOrderPayPopuwindowInner
import com.yinduowang.installment.mvp.model.entity.GetOrderPayPopuwindowOut

class DialogRepaymentDetailForPaymentAdapter(data: List<MultiItemEntity>?) : BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder>(data) {
    companion object {
        const val TYPE_LEVEL_0 = 0
        const val TYPE_LEVEL_1 = 1
    }

    init {
        addItemType(TYPE_LEVEL_0, R.layout.layout_dialog_item_loan_detailed)
        addItemType(TYPE_LEVEL_1, R.layout.layout_dialog_item_item_loan_detailed)
    }

    override fun convert(helper: BaseViewHolder, item: MultiItemEntity?) {
        when (helper?.itemViewType) {
            TYPE_LEVEL_0 -> {
                val budgetMoneyInfo = item as GetOrderPayPopuwindowOut
                helper.setText(R.id.tv_stage_num, budgetMoneyInfo.stage).setText(R.id.tv_stage_money, budgetMoneyInfo.repayFund)
                helper.itemView.setOnClickListener {
                    val pos = helper.adapterPosition
                    if (budgetMoneyInfo.isExpanded) {
                        collapse(pos, true)
                    } else {
                        expand(pos, true)
                    }
                }
                if (budgetMoneyInfo.isExpanded) {
                    helper.setImageResource(R.id.iv_arrow, R.mipmap.ic_arrow_top)
                } else {
                    helper.setImageResource(R.id.iv_arrow, R.mipmap.ic_arrow_bottom)
                }
            }
            TYPE_LEVEL_1 -> {
                val budgetInfo = item as GetOrderPayPopuwindowInner
                helper.setText(R.id.tv_principal, budgetInfo.name).setText(R.id.tv_principa_money, budgetInfo.value)
                if (helper.adapterPosition + 1 < mData.size) {
                    if (mData[helper.adapterPosition + 1].itemType == TYPE_LEVEL_0) {
                        helper.setGone(R.id.holderView, true)
                    } else {
                        helper.setGone(R.id.holderView, false)
                    }
                } else {
                    helper.setGone(R.id.holderView, true)
                }
            }
        }
    }
}
