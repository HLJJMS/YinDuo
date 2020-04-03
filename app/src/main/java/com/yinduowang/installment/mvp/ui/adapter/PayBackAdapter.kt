package com.yinduowang.installment.mvp.ui.adapter

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.yinduowang.installment.R
import com.yinduowang.installment.mvp.model.entity.BillDetailList
import com.yinduowang.installment.mvp.model.entity.BillDetailListSon

class PayBackAdapter(data: List<MultiItemEntity>?) : BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder>(data) {

    companion object {
        const val TYPE_LEVEL_0 = 0
        const val TYPE_LEVEL_1 = 1
    }

    init {
        addItemType(DialogLoanQuickAdapter.TYPE_LEVEL_0, R.layout.item_pay_back_item)
        addItemType(DialogLoanQuickAdapter.TYPE_LEVEL_1, R.layout.item_pay_back_item_son)
    }


    override fun convert(helper: BaseViewHolder, item: MultiItemEntity?) {
        when (helper?.itemViewType) {
            TYPE_LEVEL_0 -> {
                val father = item as BillDetailList
                helper.setText(R.id.tvTitle, father.name)
                helper.setText(R.id.tvNameCharge, father.value)
                helper.setGone(R.id.ivTriangle, false)
                if (father.nameContantsSon.isNotEmpty()) {
                    helper.setVisible(R.id.ivTriangle, true)
                    if (father.isExpanded) {
                        helper.setImageResource(R.id.ivTriangle, R.mipmap.ic_triangle_bottom)
                    } else {
                        helper.setImageResource(R.id.ivTriangle, R.mipmap.ic_triangle_right)
                    }
                    for (index in helper.adapterPosition..mData.size) {
                        if (mData[index].itemType == 0) {
                            helper.setVisible(R.id.view_protect, true)
                            break
                        } else {
                            helper.setVisible(R.id.view_protect, false)
                        }
                    }
                    helper.itemView.setOnClickListener {
                        val pos = helper.adapterPosition
                        if (father.isExpanded) {
                            collapse(pos, true)
                        } else {
                            expand(pos, true)
                        }
                        notifyDataSetChanged()
                    }
                }

                if (helper.adapterPosition + 1 == mData.size) {
                    helper.setVisible(R.id.view_protect, false)
                } else {
                    helper.setVisible(R.id.view_protect, true)
                }
            }
            TYPE_LEVEL_1 -> {
                val son = item as BillDetailListSon
                helper.setText(R.id.tvName, son.name)
                helper.setText(R.id.tvTxt, son.value)

                if (helper.adapterPosition + 1 < mData.size) {
                    if (data[helper.layoutPosition - 1].itemType == DialogLoanQuickAdapter.TYPE_LEVEL_0) {
                        helper.setGone(R.id.vTop, true)
                    } else {
                        helper.setGone(R.id.vTop, false)
                    }
                    if (mData[helper.adapterPosition + 1].itemType == DialogLoanQuickAdapter.TYPE_LEVEL_0) {
                        helper.setGone(R.id.line, true)
                    } else {
                        helper.setGone(R.id.line, false)
                    }
                } else {
                    helper.setVisible(R.id.line, false)
                }
            }
        }
    }
}