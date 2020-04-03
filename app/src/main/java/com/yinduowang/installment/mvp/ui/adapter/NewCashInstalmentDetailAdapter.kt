package com.yinduowang.installment.mvp.ui.adapter

import android.widget.TextView
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.jess.arms.utils.ArmsUtils
import com.yinduowang.installment.R
import com.yinduowang.installment.mvp.model.entity.*

/**
 * Description：现金分期详情
 * Author：田羽衡
 * Version：<v1.0，2019/11/1 >
 * LastEditTime：2019/11/1
 * LastEditors:
 * Deprecated： false
 */
class NewCashInstalmentDetailAdapter(data: List<MultiItemEntity>?) : BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder>(data) {

    /**
     * Description：定义父子view标签
     * Author：田羽衡
     * Version：<v1.0，2019/10/31 10:36>
     * params:
     * return：
     * LastEditTime：2019/10/31 10:36
     * Deprecated： false
     */
    companion object {
        const val TYPE_LEVEL_0 = 0
        const val TYPE_LEVEL_1 = 1
    }
    /**
     * Description：/定义父子view layout
     * Author：田羽衡
     * Version：<v1.0，2019/10/31 10:34>
     * params:
     * return：
     * LastEditTime：2019/10/31 10:34
     * Deprecated： false
     */
    init {
        addItemType(TYPE_LEVEL_0, R.layout.item_new_instalment_detail)
        addItemType(TYPE_LEVEL_1, R.layout.item_new_instalment_detail_son)
    }


    override fun convert(helper: BaseViewHolder, item: MultiItemEntity?) {
        when (helper?.itemViewType) {
            TYPE_LEVEL_0 -> {
                val father = item as StageDetailsPlanlistBean
                helper?.setText(R.id.tvRmb, father?.amountDue)
                        ?.setText(R.id.tvMolecule, father?.curStage)
                        ?.setText(R.id.tvDenominator, "/" + father?.stage + "期")
                        ?.setText(R.id.tvTimes, "还款日 " + father?.repaymentTime)
                        ?.setTextColor(R.id.tvRmb, ArmsUtils.getColor(mContext, R.color.color_333333))
                        ?.setTextColor(R.id.tvMolecule, ArmsUtils.getColor(mContext, R.color.color_333333))
                        ?.setTextColor(R.id.tvDenominator, ArmsUtils.getColor(mContext, R.color.color_333333))
                        ?.setTextColor(R.id.tvTimes, ArmsUtils.getColor(mContext, R.color.color_9A9A9A))

                if (father?.repaymentState.equals("2")) {
                    helper?.setText(R.id.tvType, "已还款")?.setTextColor(R.id.tvType, ArmsUtils.getColor(mContext, R.color.color_D0D0D0))
                            ?.setTextColor(R.id.tvRmb, ArmsUtils.getColor(mContext, R.color.color_D0D0D0))
                            ?.setTextColor(R.id.tvMolecule, ArmsUtils.getColor(mContext, R.color.color_D0D0D0))
                            ?.setTextColor(R.id.tvDenominator, ArmsUtils.getColor(mContext, R.color.color_D0D0D0))
                            ?.setTextColor(R.id.tvTimes, ArmsUtils.getColor(mContext, R.color.color_D0D0D0))
                } else if (father?.overdueState.equals("1")) {
                    helper?.setText(R.id.tvType, "已逾期")?.setTextColor(R.id.tvType, ArmsUtils.getColor(mContext, R.color.color_FF3763))
                } else {
                    helper?.setText(R.id.tvType, "未还款")?.setTextColor(R.id.tvType, ArmsUtils.getColor(mContext, R.color.color_FF7213))
                }

                helper.itemView.setOnClickListener {
                    val pos = helper.adapterPosition
                    if (father.isExpanded) {
                        collapse(pos, true)
                    } else {
                        expand(pos, true)
                    }
                }
                if (father.repaymentState.equals("2")) {
                    if (father.isExpanded) {
                        helper.setImageResource(R.id.vTriangle, R.mipmap.ic_slices_gray_open)
                    } else {
                        helper.setImageResource(R.id.vTriangle, R.mipmap.ic_slices_gray_close)
                    }
                } else {
                    if (father.isExpanded) {
                        helper.setImageResource(R.id.vTriangle, R.mipmap.ic_slices_blue_open)
                    } else {
                        helper.setImageResource(R.id.vTriangle, R.mipmap.ic_slices_blue_close)
                    }
                }
            }
            TYPE_LEVEL_1 -> {
                val son = item as BillDetailListBean
//                记录父类repaymentState的值
                var repaymentState = ""
//                循环判断最上边的一个父类repaymentState的值
                for (index in helper.adapterPosition downTo 0) {
                    if (mData.get(index).itemType == 0) {
                        val father = mData.get(index) as StageDetailsPlanlistBean
                        repaymentState = father.repaymentState.toString()
                        break;
                    }
                }
//                判断是否还款“2”还款
                if (repaymentState.equals("2")) {
                    helper.setTextColor(R.id.tvName, ArmsUtils.getColor(mContext, R.color.color_D0D0D0))
                    helper.setTextColor(R.id.tvTxt, ArmsUtils.getColor(mContext, R.color.color_D0D0D0))
                } else {
                    helper.setTextColor(R.id.tvName, ArmsUtils.getColor(mContext, R.color.color_adadad))
                    helper.setTextColor(R.id.tvTxt, ArmsUtils.getColor(mContext, R.color.color_666666))
                }
                helper.setText(R.id.tvName, son.name)
                helper.setText(R.id.tvTxt, son.value)
                if (helper.adapterPosition + 1 < mData.size) {
                    if (mData[helper.adapterPosition + 1].itemType == TYPE_LEVEL_0) {
                        helper.setGone(R.id.line, true)
                    } else {
                        helper.setGone(R.id.line, false)
                    }
                } else {
                    helper.setGone(R.id.line, true)
                }
//                var item = helper.getView<TextView>(R.id.tvTxt)as TextView
//                if (mData[helper.adapterPosition -1].itemType == TYPE_LEVEL_0) {
//                    item.setPadding(0,ArmsUtils.dip2px(mContext,11f),0,0)
//                } else {
//                    item.setPadding(0,ArmsUtils.dip2px(mContext,7f),0,0)
//                }
            }
        }
    }
}
