package com.yinduowang.installment.mvp.ui.adapter

import android.widget.LinearLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.tencent.bugly.crashreport.CrashReport
import com.yinduowang.installment.R
import com.yinduowang.installment.mvp.model.entity.UserBank


class ChooseDeliveryAddressAdapter(data: List<String>?) : BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_choose_delivery_address, data) {

    lateinit var chooseAddressListener: ChooseAddressListener

    override fun convert(helper: BaseViewHolder, item: String?) {
        try {
            var namsdf = "盛开的积分开始将${item}速度快解放SDK${item}胜多负少的$item"
            helper.setText(R.id.tvId, item)
            helper.setText(R.id.tvName, namsdf)
            if (item != null)
                helper.itemView?.setOnClickListener {
                    if (::chooseAddressListener.isInitialized) {
                        chooseAddressListener.choose(item, namsdf)
                    }
                }
        } catch (e: Exception) {
            e.printStackTrace()
            CrashReport.postCatchedException(e)
        }
    }

    interface ChooseAddressListener {
        fun choose(addressId: String, addressName: String)
    }
}