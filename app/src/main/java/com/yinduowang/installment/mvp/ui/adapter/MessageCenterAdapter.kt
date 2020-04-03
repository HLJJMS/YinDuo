package com.yinduowang.installment.mvp.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.yinduowang.installment.R
import com.yinduowang.installment.mvp.model.entity.MessageCenterEntity
import com.yinduowang.installment.mvp.model.entity.MessageEntity
/**
 * Description：消息中心
 * Author：田羽衡
 * Version：<v1.0，2019/11/1 >
 * LastEditTime：2019/11/1
 * LastEditors:
 * Deprecated： false
 */
class MessageCenterAdapter(data: List<MessageEntity>?) : BaseQuickAdapter<MessageEntity, BaseViewHolder>(R.layout.item_message_center, data) {
    override fun convert(helper: BaseViewHolder, item: MessageEntity) {
        helper.setText(R.id.tvTime,item.createdString)
        helper.setText(R.id.tvMsg,item.content)
    }
}