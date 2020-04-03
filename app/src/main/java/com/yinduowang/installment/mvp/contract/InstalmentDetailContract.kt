package com.yinduowang.installment.mvp.contract

import com.jess.arms.mvp.IView
import com.jess.arms.mvp.IModel
import com.yinduowang.installment.mvp.model.entity.*
import io.reactivex.Observable


/**
 * ================================================
 * Description:分期详情（商城）
 * <p>
 * Created by MVPArmsTemplate on 08/05/2019 09:23
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
interface InstalmentDetailContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View : IView{
        fun getListSuccess(bean:ShopCashInstalmentDetailEntity)
        fun getListFail(msg:String)
        /**
         * 可以还款
         * */
        fun canRepay()
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model : IModel{
        fun getList(token: String, sign: String, id: String): Observable<BaseResponse<ShopCashInstalmentDetailEntity>>
        fun checkRepay(type: String,loanId:String): Observable<BaseResponse<Any>>
    }

}
