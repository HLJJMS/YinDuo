package com.yinduowang.installment.mvp.contract

import com.jess.arms.mvp.IModel
import com.jess.arms.mvp.IView
import com.yinduowang.installment.mvp.model.entity.BasePhpResponse
import com.yinduowang.installment.mvp.model.entity.BaseResponse
import com.yinduowang.installment.mvp.model.entity.CheckQuotaEntity
import com.yinduowang.installment.mvp.model.entity.GetOrderPay
import io.reactivex.Observable


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/05/2019 17:23
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
interface PaymentTypeContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View : IView {
        fun getShopOredeDetailSuccess(bean: GetOrderPay)
        fun postPaySuccess()
        fun showPassWordError(txt: String)
        fun showPassWordErrorFive(txt: String)
        fun showOverdue(txt: String)
        fun dialogAndGoOrderList(txt: String)
        fun showResultDialog(txt: String)
        fun checkQuotaSuccess(code:String,button:String,txt:String,valueAddedService:String)
        fun toastAndGoOlderLlist(txt:String)
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model : IModel {
        fun getShopDetailForPay(o_id: String, cycle: String, token: String, sign: String): Observable<BasePhpResponse<GetOrderPay>>
        fun postPay(o_id: String, token: String, total_money: String, cycle: String, password: String, config_id: String,
                    sign: String): Observable<BasePhpResponse<Any>>
        fun goCheckQuota(token: String, sign: String): Observable<BaseResponse<CheckQuotaEntity>>
    }

}
