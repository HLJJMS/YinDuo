package com.yinduowang.installment.mvp.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.qmuiteam.qmui.widget.QMUIRadiusImageView
import com.yinduowang.installment.R
import com.yinduowang.installment.app.utils.LoadImageUtils
import com.yinduowang.installment.mvp.model.entity.Data

class LoanBannerAdapter(data: List<Data>) : BaseQuickAdapter<Data, BaseViewHolder>(R.layout.item_loan, data) {
    override fun convert(helper: BaseViewHolder, item: Data) {
        var qmuiRadiusImageView = helper.getView<QMUIRadiusImageView>(R.id.qmuiRadiusImageView)
        if (!"".equals(item.thumb))
            LoadImageUtils.showImage(mContext, item.thumb, qmuiRadiusImageView)
    }
}
