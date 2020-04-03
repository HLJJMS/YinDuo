package com.yinduowang.installment.mvp.model.entity

data class UserInfoAllEntity(
        val currentAddress: String,// 现居地址
        val detailedAddress: String,// 详细地址
        val education: String,// 学历
        val educationList: List<UserInfoListEntity>,// 学历字典
        val faceUrl: String,// 人脸识别 图片地址
        val familyContactsList: List<UserInfoListEntity>,// 紧急联系人 亲属字典
        val idCard: String,// 身份证号
        val identificationFrontUrl: String,// 身份证正面地址
        val identificationReverseUrl: String,// 身份证反面地址
        val liveTime: String,// 居住时长
        val liveTimeList: List<UserInfoListEntity>,// 居住时长字典
        val marital: String,// 婚姻状况
        val maritalList: List<UserInfoListEntity>,// 婚姻状况字典
        val otherContactsList: List<UserInfoListEntity>,// 紧急联系人 朋友字典
        val username: String// 姓名
)

data class UserInfoListEntity(
        val id: Int,
        val name: String
)

/**
"currentAddress": "黑龙江省 哈尔滨市 南岗区",
"detailedAddress": "详细地址",
"education": "学历",
"educationList": "0未知 1博士 2硕士 3本科 4大专 5中专 6高中 7初中 8初中以下",
"faceUrl": "人脸地址",
"familyContactsList": "1父亲 2母亲 3儿子 4女儿 5兄弟 6姐妹 7配偶",
"idCard": "身份证",
"identificationFrontUrl": "身份证正面地址",
"identificationReverseUrl": "身份证反面地址",
"liveTime": "居住时长",
"liveTimeList": "0半年以内 1半年到一年 2一年以上",
"marital": "婚姻状况",
"maritalList": "0未婚 1已婚未育 2已婚已育 3离异 4其它",
"otherContactsList": " 8同学 9亲戚 10同事 11朋友 12其他",
"username": "张三"
 * */