package com.yinduowang.installment.mvp.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.yinduowang.installment.R
import com.yinduowang.installment.mvp.model.entity.UserBank

class DialogChoseBankCardAdapter(data: List<UserBank>?) : BaseQuickAdapter<UserBank, BaseViewHolder>(R.layout.layout_dialog_item_chose_bank, data) {
    override fun convert(helper: BaseViewHolder, item: UserBank?) {
        helper.setText(R.id.tv_bank_card,item?.bankName+"（${item?.endCardNo}）")
        if ("1".equals(item?.defaultCard)){
            helper.setGone(R.id.iv_default_mark,true)
        }else{
            helper.setGone(R.id.iv_default_mark,false)
        }

    }
}