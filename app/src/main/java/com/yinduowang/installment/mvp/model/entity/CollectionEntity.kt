package com.yinduowang.installment.mvp.model.entity

import java.io.Serializable

data class CollectionEntity(
   val typeList: List<Type>
)

data class Type(
    val created: Int,
    val id: Int,
    val name: String,
    val sort: Int,
    val status: Int,
    val updated: Int
)