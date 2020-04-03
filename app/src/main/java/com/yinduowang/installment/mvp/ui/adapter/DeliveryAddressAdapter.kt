package com.yinduowang.installment.mvp.ui.adapter

import android.view.View
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.yinduowang.installment.R
import com.yinduowang.installment.app.utils.StringUtil
import com.yinduowang.installment.mvp.model.entity.DeliveryAddress

class DeliveryAddressAdapter(data: List<DeliveryAddress>?) : BaseQuickAdapter<DeliveryAddress, BaseViewHolder>(R.layout.item_delivery_address, data) {
    override fun convert(helper: BaseViewHolder, item: DeliveryAddress?) {
        var name = item?.name ?: ""
        if (name.length > 3) {
            name = name.substring(0, 3) + "..."
        }
        helper.setText(R.id.tv_pay_back_style, name)
        var mobile = item?.mobile
        if (mobile != null && StringUtil.isMobileNO(mobile)) {
            mobile = StringUtil.changeMobile(mobile)
            helper.setText(R.id.tv_pay_back_money, mobile)
        }
        helper.setText(R.id.tv_pay_back_time, item?.province + item?.city + item?.area + " " + item?.address)
        if (item?.is_default.equals("1")) {
            helper.setGone(R.id.tv_loan_status, true)
        } else {
            helper.setGone(R.id.tv_loan_status, false)
        }
        helper.getView<ImageView>(R.id.ivEditAddress)?.setOnClickListener {
            listener?.let { ib ->
                ib.onClick(it, helper.adapterPosition)
            }
        }
    }

    fun setOnItemImageListener(mListener: onImageClickListener) {
        listener = mListener
    }

    interface onImageClickListener {
        fun onClick(v: View, position: Int)
    }

    public var listener: onImageClickListener? = null
}