package com.yinduowang.installment.mvp.contract

import com.jess.arms.mvp.IModel
import com.jess.arms.mvp.IView
import com.yinduowang.installment.mvp.model.entity.BaseResponse
import com.yinduowang.installment.mvp.model.entity.NewLoginEntity
import com.yinduowang.installment.mvp.model.entity.VerifyCodeEntity
import io.reactivex.Observable


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/16/2019 09:47
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
interface LoginContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View : IView{
        fun onVerifyCode()
        fun onLoginReturn(loginEntity: NewLoginEntity)
        fun getTelSuccess(tel:String)
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model : IModel{
        fun getVerifyCode(mobile:String,type:String): Observable<BaseResponse<VerifyCodeEntity>>
        fun verifyLogin(mobile: String, authCode: String, ip: String, gpsAddress: String, phoneModel: String): Observable<BaseResponse<NewLoginEntity>>
        fun passwordLogin(mobile: String, password: String, ip: String, gpsAddress: String, phoneModel: String): Observable<BaseResponse<NewLoginEntity>>
        fun serviceTel(): Observable<BaseResponse<String>>
    }

}
