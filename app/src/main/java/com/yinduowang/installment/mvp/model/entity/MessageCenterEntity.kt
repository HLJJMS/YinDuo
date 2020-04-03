package com.yinduowang.installment.mvp.model.entity

data class MessageCenterEntity(
        val list: List<MessageEntity>,
        val isExistsNextPage: String
)

data class MessageEntity(
//        val content: String,
//        val created: Int,
//        val deleted: Int,
//        val id: Int,
//        val status: Int,
//        val title: String,
//        val type: Int,
//        val userId: Int
//        private var id: Int = 0

        val id: Int,

        val userId: Int,

        val title: String,

        val content: String,

        var type: Int,

        var status: Int,

        var deleted: Int,

        val releaseTime: String,

        val createdString: String
)