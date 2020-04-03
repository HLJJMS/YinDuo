package com.yinduowang.installment.mvp.model.entity

import com.chad.library.adapter.base.entity.AbstractExpandableItem
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.yinduowang.installment.mvp.ui.adapter.DialogLoanQuickAdapter
import java.io.Serializable

data class ShopOrderEntity(
        val order_count: Int,
        val order_list: List<ShopOrder>
)

data class ShopOrder(
        val create_time: String,
        val goods_list: List<ShopGoods>,
        val id: String,
        val m_id: String,
        var order_status: String,
        var status_name: String,
        val time: String,
        val total_money: String,
        val sn: String
)

data class ShopGoods(
        val av_names: List<String>,
        val id: String,
        val name: String,
        val number: String,
        val price: String,
        val thumb: String
)

data class ShopOrderDetailedEntity(
        val address: String,
        val area: String,
        val city: String,
        val country: String,
        val fee: String,
        val goods_list: List<Goods>,
        val goods_price: String,
        val mobile: String,
        val name: String,
        val o_id: String,
        val type: String,
        val type_name: String,
        val province: String,
        val sn: String,
        val status: Int,
        val status_name: String,
        val time: String,
        val total_money: String,
        val tel: String
)

data class Goods(
        val av_names: List<String>,
        val id: String,
        val price: String,
        val name: String,
        val number: String,
        val s_thumb: String,
        val thumb: String
)
//确认订单

data class ConfirmationOrder(
        val o_id: String
)

//确认订单页获取订单运费
data class OrderFee(
        val month_money: String,
        val fee: String
)

//获取订单信息
data class GetOrderPay(
        val total_money: String,
        val month_money: String,
        val cycle: String,
        val price: String,
        val fund: String,
        val config_id: String,
        val interest: String,
        val rate: String,
        val repay_of_month: String,
        val refund_detail: List<GetOrderPayPopuwindowOut>,
        val pwd_status: String, //: 密码设置状态1已设置
        val cycle_list: List<String>
) : Serializable

//获取订单信息弹窗外层
data class GetOrderPayPopuwindowOut(
        val stage: String,
        val repayFund: String,
        val budgetInfo: List<GetOrderPayPopuwindowInner>

) : AbstractExpandableItem<GetOrderPayPopuwindowInner>(), MultiItemEntity, Serializable {
    override fun getLevel(): Int {
        return 0
    }

    override fun getItemType(): Int {
        return DialogLoanQuickAdapter.TYPE_LEVEL_0;
    }

}

//获取订单信息弹窗内层
data class GetOrderPayPopuwindowInner(
        val name: String,
        val value: String
) : MultiItemEntity, Serializable {
    override fun getItemType(): Int {
        return DialogLoanQuickAdapter.TYPE_LEVEL_1;
    }

}


//获取物流信息
data class GetLogisticsEntity(
        var name: String,
        var sn: String,
        var logistics_list: List<LogisticsBeanEntity>
)

//获取物流信息2
data class LogisticsBeanEntity(
        var msg: String,
        var time: String
)

//web月供详情
data class MonthDetailForWebEntity(
        var price: String,
        var fund: String,
        var interest: String,
        var min_money: String,
        var rate: String,
        var count: String,
        var cycle: String
)

//检查额度情况
data class CheckQuotaEntity(
        var buttonMsg: String,
        var pageMsg: String,
        var status: String,
        var valueAddedService: String
)