package com.yinduowang.installment.mvp.contract

import com.jess.arms.mvp.IModel
import com.jess.arms.mvp.IView
import com.yinduowang.installment.mvp.model.entity.BaseResponse
import com.yinduowang.installment.mvp.model.entity.LoanBannerEntity
import com.yinduowang.installment.mvp.model.entity.NewLoanEntity
import io.reactivex.Observable


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/17/2019 13:27
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
interface LoanContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View : IView{
        fun returnLoanInfo(loanEntity: NewLoanEntity)
        fun returnConfirmApply(baseResponse: BaseResponse<Any>)
        fun finishRefresh()
        fun returnSignContract(response: BaseResponse<Any>)
        fun returnBottomList(loanBannerEntity: LoanBannerEntity)
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model : IModel{
        fun getSignContract(loanId: String): Observable<BaseResponse<Any>>
        fun getLoanInfo1():Observable<BaseResponse<NewLoanEntity>>
        fun confirmApply(loanMoney: String): Observable<BaseResponse<Any>>
        fun getLoanBanner(page: String, ac_type: String): Observable<LoanBannerEntity>
    }
}
