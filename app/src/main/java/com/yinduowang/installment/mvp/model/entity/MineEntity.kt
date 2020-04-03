package com.yinduowang.installment.mvp.model.entity

import java.io.Serializable

data class MineEntity(var cashNeedRepay: String = "0",// 现金待还
                      var orderStateFour: String = "0",// 退换数量
                      var orderStateOne: String = "0",// 待付款数量
                      var orderStateThree: String = "0",// 待收货数量
                      var orderStateTwo: String = "0",// 待发货数量
                      var perfectStatus: String = "-1",// 认证状态-1 未完成 0 完成
                      var bankStatus : String = "-1",// 认证状态-1 未完成 0 完成(10.10新增)
                      var quotaState: String = "-1",// 额度状态 -1未获取额度 0 额度获取中 1 已获取额度
                      var shopNeedRepay: String = "0",// 商城待还
                      var unreadMessage: String = "0",// 未读消息数量
                      var userMobile: String = "",// 手机号
                      var textOne: String = "",// 我的账单固定文字第一行
                      var textTwo: String = "",//  我的账单固定文字第二行
                      var textButton: String = "",//我的账单按钮文字 ,
                      val vipStatus: String = "",
                      val vipText: String = ""

) : Serializable

data class QuotaEntity(
        val availableQuota: String,
        val msg: String,
        val quota: String,
        val usableCashFund: String,
        val usableShopFund: String,
        val usedQuota: String
)