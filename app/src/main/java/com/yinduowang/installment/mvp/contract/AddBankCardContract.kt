package com.yinduowang.installment.mvp.contract

import com.jess.arms.mvp.IView
import com.jess.arms.mvp.IModel
import com.yinduowang.installment.mvp.model.entity.BaseResponse
import com.yinduowang.installment.mvp.model.entity.ConfirmBindEntity
import com.yinduowang.installment.mvp.model.entity.PreBindEntity
import com.yinduowang.installment.mvp.model.entity.VerificationUserInfoEntity
import io.reactivex.Observable


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/24/2019 14:11
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
interface AddBankCardContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View : IView {
        fun getUserInfo(verificationUserInfoEntity: VerificationUserInfoEntity)
        fun preBind(preBindEntity: PreBindEntity)
        fun confirmBind(confirmBindEntity: ConfirmBindEntity)
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model : IModel {
        fun getUserInfo(): Observable<BaseResponse<VerificationUserInfoEntity>>
        fun preBind(bankCardNo: String, mobileNo: String): Observable<BaseResponse<PreBindEntity>>
        fun confirmBind(uniqueCode: String, authCode: String): Observable<BaseResponse<ConfirmBindEntity>>
    }

}
