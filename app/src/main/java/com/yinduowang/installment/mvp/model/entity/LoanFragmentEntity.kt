package com.yinduowang.installment.mvp.model.entity

data class LoanFragmentEntity(
    val id: String,
    val list: List<ItemEntity>,
    val tags: String
)

data class ItemEntity(
    val desp: String,
    val index: Int,
    val title: String,
    val titleright: String,
    val url: String
)