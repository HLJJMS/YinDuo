package com.yinduowang.installment.mvp.model.entity

data class AuthEmergencyContactEntity(
        val contacts: Contacts,//联系人信息
        val dictionary: Dictionary//个人信息数据字典
)

data class Contacts(
        val familyMobile: String,// 直系亲属手机号
        val familyName: String,// 直系亲属姓名
        val familyType: Int,// 直系亲属类型
        val familyTypeName: String,// 直系亲属类型，1父亲 2母亲 3儿子 4女儿 5兄弟 6姐妹 7配偶 8同学 9亲戚 10同事 11朋友 12其他
        val otherMobile: String,// 其他联系人手机号
        val otherName: String,// 其他联系人姓名
        val otherType: Int,// 其他联系人类型
        val otherTypeName: String// 其他联系人类型，1父亲 2母亲 3儿子 4女儿 5兄弟 6姐妹 7配偶 8同学 9亲戚 10同事 11朋友 12其他
)

data class Dictionary(
        val educationList: List<DictionaryList>,// 学历
        val familyContactsList: List<DictionaryList>,//  直系联系人与本人关系
        val liveTimeList: List<DictionaryList>,// 居住时长
        val maritalList: List<DictionaryList>,// 婚姻状况
        val otherContactsList: List<DictionaryList>,// 其他联系人与本人关系
        val regions: List<Region>//
)

data class Region(
        var child: List<Region>,
        var created: Int,
        var id: Int,
        var name: String,
        var regionId: String,
        var regionParentId: String,
        var type: String,
        var updated: Int
)

data class DictionaryList(
        val id: Int,
        val name: String
)


data class QuotaBean(
        val quota: String, //弹窗
        val message: String//额度
)