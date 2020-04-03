package com.yinduowang.installment.mvp.ui.adapter

import android.widget.LinearLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.tencent.bugly.crashreport.CrashReport
import com.yinduowang.installment.R
import com.yinduowang.installment.mvp.model.entity.UserBank


class BankCardAdapter(data: List<UserBank>?) : BaseQuickAdapter<UserBank, BaseViewHolder>(R.layout.item_bankcard, data) {
    override fun convert(helper: BaseViewHolder, item: UserBank?) {
        try {
            helper.setText(R.id.tv_pay_back_style, item?.bankName)
            helper.setText(R.id.tvNameOne, item?.bankName)
            helper.setText(R.id.tvCard, item?.startCardNo + "   ****   ****   " + item?.endCardNo)
            helper.setText(R.id.tvCardOne, item?.startCardNo + "   ****   ****   " + item?.endCardNo)
            if (item?.defaultCard == "1") {
                helper.setGone(R.id.llContentOne, true)
                helper.setGone(R.id.llContentOther, false)
            } else {
                helper.setGone(R.id.llContentOne, false)
                helper.setGone(R.id.llContentOther, true)
            }

            val layoutParams = helper.itemView.layoutParams
            layoutParams?.height = LinearLayout.LayoutParams.WRAP_CONTENT
        } catch (e: Exception) {
            e.printStackTrace()
            CrashReport.postCatchedException(e)
        }
    }
}