package com.yinduowang.installment.mvp.model.entity

import com.chad.library.adapter.base.entity.AbstractExpandableItem
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.yinduowang.installment.mvp.ui.adapter.DialogLoanQuickAdapter
import java.io.Serializable


data class PayBackMoney(
        val bankId: String,
        val bankName: String,
        val bankNoLastFour: String,
        val isBindBank: String,
        val month: String,
        val repaymentMoney: String,
        val year: String,
        val type: String,
        val billDetailList: List<BillDetailList>
)


data class BillDetailList(
        val name: String,
        val value: String,
        val nameContantsSon: List<BillDetailListSon>,
        var isOpen: Boolean = false
): AbstractExpandableItem<BillDetailListSon>(), MultiItemEntity, Serializable {
    override fun getLevel(): Int {
        return 0
    }

    override fun getItemType(): Int {
        return DialogLoanQuickAdapter.TYPE_LEVEL_0;
    }
}
data class BillDetailListSon(
        val name: String,
        val value: String
): MultiItemEntity, Serializable {
    override fun getItemType(): Int {
        return DialogLoanQuickAdapter.TYPE_LEVEL_1;
    }

}


data class PreRepayBack(
        val message: String,
        val repayFund: Double,
        val status: String,
        val uniqueCode: String
)

//确认支付
data class ConfirmRepayBean(val message: String, val status: String)