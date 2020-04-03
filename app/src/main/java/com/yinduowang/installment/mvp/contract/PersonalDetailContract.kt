package com.yinduowang.installment.mvp.contract

import com.jess.arms.mvp.IModel
import com.jess.arms.mvp.IView
import com.yinduowang.installment.mvp.model.entity.BaseResponse
import com.yinduowang.installment.mvp.model.entity.UserInfoAllEntity
import io.reactivex.Observable


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/30/2019 09:00
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
interface PersonalDetailContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View : IView {
        fun getUserInfoAll(userInfoAllEntity: UserInfoAllEntity)
        fun onVerifyRPBasicTokenResult(aliyunToken: String)
        fun callBackVerifyRPBasicResult(map: Map<String, String>)
        fun upadateUserinfoResult()
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model : IModel {
        fun getUserInfoAll(): Observable<BaseResponse<UserInfoAllEntity>>

        fun getVerifyRPBasicToken(): Observable<BaseResponse<Map<String, String>>>

        fun callbackVerifyRPBasic(aliyunToken: String): Observable<BaseResponse<Map<String, String>>>

        fun upadateUserinfo(map: HashMap<String, String>): Observable<BaseResponse<Any>>
    }

}
