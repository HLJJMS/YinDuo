package com.yinduowang.installment.mvp.model.entity

import com.chad.library.adapter.base.entity.AbstractExpandableItem
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.yinduowang.installment.mvp.ui.adapter.DialogLoanQuickAdapter.Companion.TYPE_LEVEL_0
import com.yinduowang.installment.mvp.ui.adapter.DialogLoanQuickAdapter.Companion.TYPE_LEVEL_1
import java.io.Serializable
data class NewLoanEntity(
        val cycleType: String?,
        val isLogin: String?,
        val leastLoanFund: String?,
        val loanFundList: List<String>?,
        val loanPurposes: List<LoanPurpose>?,
        val loanSigningResponse: LoanSigningResponse?,
        val lonaDays: Int?,
        val maxLoanFund: String?,
        val noBorrowDays: Int?,
        val notBorrow: Int?
) : Serializable

data class LoanPurpose(
        val id: Int?,
        val purpose: String?,
        val status: String?
) : Serializable

data class LoanSigningResponse(
        val buttonText: String?,
        val buttonType: String?,
        val loanId: String?,
        val textOne: String?,
        val textTwo: String?,
        val type: String?
) : Serializable
data class ConfirmLoanEntity(
        val bankDefaultId: String?,
        val bankInfo: String?,
        val budgetMoneyInfo: List<BudgetMoneyInfo>?,
        val cardNoLastFour: String?,
        val configId: String?,
        val cycleType: String?,
        val isBindBank: String?,
        val loanMoney: String?,
        val lonaDays: Int?,
        val repaymentDay: String?,
        val valuesAddedStatus: String?,
        val bankCard: String?
) : Serializable

data class BudgetMoneyInfo(val budgetInfo: List<BudgetInfo>?, val repayFund: String?, val stage: String?) : AbstractExpandableItem<BudgetInfo>(), MultiItemEntity, Serializable {
    override fun getLevel(): Int {
        return 0
    }

    override fun getItemType(): Int {
        return TYPE_LEVEL_0
    }
}

data class BudgetInfo(val name: String?, val value: String?) : MultiItemEntity, Serializable {
    override fun getItemType(): Int {
        return TYPE_LEVEL_1
    }
}

data class FloatDetailedBean(var data_list: ArrayList<DataListBean>?) : Serializable


data class DataListBean(var source_id: String?,
                        var name: String?,
                        var url: String?,
                        var thumb: String?,
                        var desc: String?,
                        var total: String?,
                        var money_limit: String?,
                        var time: String?,
                        var pass_rate: String?)

data class LoanBannerEntity(
        val is_banner: String?,
        val count: String?,
        val data_list: ArrayList<Data>?,
        val type: String?
)

data class Data(
        val desc: String?,
        val money_limit: String?,
        val name: String?,
        val pass_rate: String?,
        val source_id: String?,
        val thumb: String?,
        val time: String?,
        val total: String?,
        val url: String?
) : MultiItemEntity {
    private var itemType = TYPE_BOTTOM_BANNER

    companion object {
        const val TYPE_BOTTOM_LIST = 10
        const val TYPE_BOTTOM_BANNER = 11
    }

    override fun getItemType(): Int {
        return itemType
    }


    fun setItemType(itemType: Int) {
        this.itemType = itemType
    }
}

data class Level0Item(val repayFund: String?, val stage: String?) : AbstractExpandableItem<Level1Item>(), MultiItemEntity, Serializable {
    override fun getLevel(): Int {
        return 0
    }

    override fun getItemType(): Int {
        return TYPE_LEVEL_0;
    }

}

data class Level1Item(val name: String?, val nameContantsSon: String?, val value: String?) : MultiItemEntity, Serializable {
    override fun getItemType(): Int {
        return TYPE_LEVEL_1
    }

}

data class ConfirmEntity(val isVip: String?, val url: String?, val loanId: String)
