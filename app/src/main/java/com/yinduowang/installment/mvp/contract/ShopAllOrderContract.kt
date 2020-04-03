package com.yinduowang.installment.mvp.contract

import com.jess.arms.mvp.IModel
import com.jess.arms.mvp.IView
import com.yinduowang.installment.mvp.model.entity.BasePhpResponse
import com.yinduowang.installment.mvp.model.entity.ShopOrder
import com.yinduowang.installment.mvp.model.entity.ShopOrderEntity
import io.reactivex.Observable


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/01/2019 13:59
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
interface ShopAllOrderContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View : IView {
        fun returnShopOrderRecord(shopOrderList: List<ShopOrder>)
        fun finishRefresh()
        fun receivingGoodsSuccess()
        fun setOnRefresh()
        fun setOnDataItemChange(orderid: String)
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model : IModel {
        fun getShopOrderRecord(page: String, status: String, limit: String): Observable<BasePhpResponse<ShopOrderEntity>>
        fun cancelShopOrder(o_id: String): Observable<BasePhpResponse<Any>>
        fun confirmation(o_id: String): Observable<BasePhpResponse<Any>>
    }
}
