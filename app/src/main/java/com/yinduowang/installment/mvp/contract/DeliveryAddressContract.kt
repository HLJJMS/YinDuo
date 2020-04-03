package com.yinduowang.installment.mvp.contract

import com.jess.arms.mvp.IModel
import com.jess.arms.mvp.IView
import com.yinduowang.installment.mvp.model.entity.BasePhpResponse
import com.yinduowang.installment.mvp.model.entity.DeliveryAddress
import com.yinduowang.installment.mvp.model.entity.DeliveryAddressEntity
import io.reactivex.Observable


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/23/2019 13:56
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
interface DeliveryAddressContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View : IView {
        fun returnDeliveryAddressList(list: List<DeliveryAddress>)
        fun delAddressSuccess()
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model : IModel {
        fun getDeliveryAddressList(): Observable<BasePhpResponse<DeliveryAddressEntity>>

        fun delAddress(token: String, id: String, sign: String): Observable<BasePhpResponse<Any>>
    }

}
