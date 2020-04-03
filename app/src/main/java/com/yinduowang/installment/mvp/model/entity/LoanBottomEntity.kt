package com.yinduowang.installment.mvp.model.entity

import com.chad.library.adapter.base.entity.MultiItemEntity

class LoanBottomEntity : MultiItemEntity {
    private var itemType = TYPE_BOTTOM_BANNER
    var data: Data? = null
    companion object {
        const val TYPE_BOTTOM_LIST = 10
        const val TYPE_BOTTOM_BANNER = 11
        const val TYPE_FLOAT_TOP = 12
    }


    override fun getItemType(): Int {
        return itemType
    }


    fun setItemType(itemType: Int) {
        this.itemType = itemType
    }
}