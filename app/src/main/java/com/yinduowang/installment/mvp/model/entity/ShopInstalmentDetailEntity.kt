package com.yinduowang.installment.mvp.model.entity

import com.chad.library.adapter.base.entity.AbstractExpandableItem
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.yinduowang.installment.mvp.ui.adapter.NewCashInstalmentDetailAdapter
import com.yinduowang.installment.mvp.ui.adapter.ShopInstalmentDetailAdapter
import java.io.Serializable

//商城账单（分期详情）
data class ShopCashInstalmentDetailEntity(
        var loanId: String? = null,
        var remainingReturned: String? = null,
        var stageDetailsPlanlist: List<ShopStageDetailsPlanlistBean>? = null,
        var buttonType: String? = null
):Serializable

/**
 * loanId : 5
 * remainingReturned : 1,000.00
 * stageDetailsPlanlist : [{"amountDue":"1,000.00","curStage":3,"demurrage":"1,000.00","id":1,"interest":"1,000.00","overdueState":0,"principal":"1,000.00","repaymentState":0,"repaymentTime":"2019年01月01日","serviceCharge":"1,000.00","stage":6}]
 */


data class ShopStageDetailsPlanlistBean(
        var amountDue: String? = "",
        var curStage: String? = "",
        var demurrage: String? = "",
        var id: String? = "",
        var interest: String? = "",
        var overdueState: String? = "",
        var principal: String? = "",
        var repaymentState: String? = "",
        var repaymentTime: String? = "",
        var serviceCharge: String? = "",
        var stage: String? = "",
        var premium: String? = "",
        var billDetailList: List<ShopBillDetailListBean>? = null
) : AbstractExpandableItem<ShopBillDetailListBean>(), MultiItemEntity, Serializable {
    override fun getLevel(): Int {
        return 0
    }

    override fun getItemType(): Int {
        return NewCashInstalmentDetailAdapter.TYPE_LEVEL_0;
    }

}
//商城账单（分期详情里面）
data class ShopBillDetailListBean(
        var name: String? = "",
        var value: String? = ""
) : MultiItemEntity, Serializable {
    override fun getItemType(): Int {
        return NewCashInstalmentDetailAdapter.TYPE_LEVEL_1;
    }
}





