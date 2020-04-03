package com.yinduowang.installment.mvp.model.entity

data class DeliveryAddressEntity(
    val address_list: List<DeliveryAddress>
)

data class DeliveryAddress(
    val address: String,
    val area: String,
    val city: String,
    val country: String,
    val id: String,
    val is_default: String,
    val mobile: String,
    val name: String,
    val province: String
)
