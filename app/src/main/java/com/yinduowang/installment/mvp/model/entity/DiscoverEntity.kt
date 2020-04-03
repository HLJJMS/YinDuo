package com.yinduowang.installment.mvp.model.entity

import java.io.Serializable

/**
 * @Description: 发现页面 列表实体类
 * @Author: YangKun
 * @Date: 2019-11-13 16:35:13
 * @Version: 1.1, 2019-11-13
 * @LastEditors:
 * @LastEditTime:
 * @Deprecated: false
 */
data class DiscoverEntity(val name: String, val date: String, val type: String, var imgId: Int, var content: String, var wordDesc: String) : Serializable