package com.yinduowang.installment.mvp.ui.adapter

import android.text.TextUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.jess.arms.utils.ArmsUtils
import com.yinduowang.installment.R
import com.yinduowang.installment.mvp.model.entity.ListBean

/**
 * Description：现金账单详情
 * Author：田羽衡
 * Version：<v1.0，2019/11/1 >
 * LastEditTime：2019/11/1
 * LastEditors:
 * Deprecated： false
 */
class NewCashBillAdapter(data: List<ListBean>?) : BaseQuickAdapter<ListBean, BaseViewHolder>(R.layout.item_bill_list, data) {
    override fun convert(helper: BaseViewHolder, item: ListBean?) {
        helper?.setText(R.id.tvTitle, "借款" + item!!.principal + "元")
                ?.setText(R.id.tvTimes, "第" + item!!.curStage + "/" + item!!.stage + "期 | ")
        if (TextUtils.equals(item!!.repaymentState, "2")) {
            helper?.setText(R.id.tvType, "已还款")
                    ?.setTextColor(R.id.tvType, ArmsUtils.getColor(mContext, R.color.color_adadad))
                    ?.setText(R.id.tvRmbFrontWord, "本期已还清")
                    ?.setGone(R.id.tvRmb, false)

        } else {

            helper?.setText(R.id.tvRmbFrontWord, "本期待还")
                    ?.setVisible(R.id.tvRmb, true)
                    ?.setText(R.id.tvRmb, item!!.totalAmount)
            if (TextUtils.equals(item!!.overdueState, "1")) {
                helper?.setText(R.id.tvType, "已逾期")
                        ?.setTextColor(R.id.tvType, ArmsUtils.getColor(mContext, R.color.color_FF3763))//颜色不对带ui
            } else {
                helper?.setText(R.id.tvType, "未还款")
                        ?.setTextColor(R.id.tvType, ArmsUtils.getColor(mContext, R.color.color_FF7213))
            }

        }
    }


}
