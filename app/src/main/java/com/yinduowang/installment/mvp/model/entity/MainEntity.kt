package com.yinduowang.installment.mvp.model.entity

import java.io.Serializable

data class AppVersionEntity(
        val apkUrl: String,
        val appid: String,
        val id: String,
        val isUpdate: String,
        val minVersion: String,
        val newVersion: String,
        val newVersionDescription: String,
        val newVersionName: String,
        val status: String,
        val testDownloadUrl: String,
        val type: String
) : Serializable