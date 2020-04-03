package com.yinduowang.installment.mvp.contract

import com.jess.arms.mvp.IModel
import com.jess.arms.mvp.IView
import com.yinduowang.installment.mvp.model.entity.BaseResponse
import com.yinduowang.installment.mvp.model.entity.LoanBannerEntity
import io.reactivex.Observable


/**
 * @Description:
 * @Author:
 * @Date: 2019-10-23 10:11:28
 * @Version: appVersionName, 2019-10-23
 * @LastEditors:
 * @LastEditTime:
 * @Deprecated: false
 */
interface FloatDetailedContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View : IView {
        fun onResponseData(listBean: LoanBannerEntity)
        fun onFinishRefresh()
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model : IModel {
        fun getFloatDetailedList(page: String, ac_type: String): Observable<LoanBannerEntity>
        fun onFloatDetailedAreaClick(token: String, sourceId: String, ac_type: String, type: String, ip: String): Observable<BaseResponse<Any>>
    }

}