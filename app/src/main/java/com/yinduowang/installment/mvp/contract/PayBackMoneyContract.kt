package com.yinduowang.installment.mvp.contract

import com.jess.arms.mvp.IModel
import com.jess.arms.mvp.IView
import com.yinduowang.installment.mvp.model.entity.*
import io.reactivex.Observable
import retrofit2.http.FieldMap
import java.util.*


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/05/2019 09:46
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
interface PayBackMoneyContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View : IView {
        fun getBankCardListSuccess(userBanks: List<UserBank>)
        fun returnPayBackDetailed(payBackMoney: PayBackMoney)
        fun payEnd(code: String)
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model : IModel {
        fun getBankCardList(bankCardId: String): Observable<BaseResponse<BankCardListEntity>>
        fun getPayBackDetailed(type: String, loanSumId: String,
                               year: String, month: String, loanId: String): Observable<BaseResponse<PayBackMoney>>

        fun preRepayBack(@FieldMap params: HashMap<String, String>): Observable<BaseResponse<PreRepayBack>>
        fun confirmRepay(uniqueCode: String, repayFund: String, token: String, sign: String): Observable<BaseResponse<ConfirmRepayBean>>
    }

}