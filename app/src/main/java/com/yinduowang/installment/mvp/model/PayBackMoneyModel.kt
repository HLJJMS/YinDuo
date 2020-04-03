package com.yinduowang.installment.mvp.model

import android.app.Application
import com.blankj.utilcode.util.SPUtils
import com.google.gson.Gson
import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel
import com.yinduowang.installment.app.constant.SPConstant
import com.yinduowang.installment.app.utils.SignUtils
import com.yinduowang.installment.mvp.contract.PayBackMoneyContract
import com.yinduowang.installment.mvp.model.entity.*
import com.yinduowang.installment.mvp.model.service.BankCardService
import com.yinduowang.installment.mvp.model.service.NewRepaymentService
import io.reactivex.Observable
import retrofit2.http.FieldMap
import javax.inject.Inject


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
@ActivityScope
class PayBackMoneyModel
@Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), PayBackMoneyContract.Model {


    override fun getBankCardList(bankCardId: String): Observable<BaseResponse<BankCardListEntity>> {
        val map = mapOf("bankCardId" to bankCardId)
        return mRepositoryManager.obtainRetrofitService(BankCardService::class.java).getBankCardList(bankCardId, SPUtils.getInstance().getString(SPConstant.TOKEN), SignUtils.getSign(map))
    }
    override fun getPayBackDetailed(type: String, loanSumId: String, year: String, month: String, loanId: String): Observable<BaseResponse<PayBackMoney>> {
        val map = HashMap<String, String>()
        map["type"] = type
        if (!month.equals("")) {
            map["month"] = month
        }
        if (!loanSumId.equals("")) {
            map["loanSumId"] = loanSumId
        }
        if (!year.equals("")) {
            map["year"] = year
        }
        if (!loanId.equals("")) {
            map["loanId"] = loanId
        }
        return mRepositoryManager.obtainRetrofitService(NewRepaymentService::class.java).getPayBackDetailed(SPUtils.getInstance().getString(SPConstant.TOKEN), SignUtils.getSign(map), map)
    }

    //    预还款
    override fun preRepayBack(@FieldMap params: java.util.HashMap<String, String>): Observable<BaseResponse<PreRepayBack>> {
        return mRepositoryManager.obtainRetrofitService(NewRepaymentService::class.java).preRepayBack(params)
    }

    //    确认支付
    override fun confirmRepay(uniqueCode: String, repayFund: String, token: String, sign: String): Observable<BaseResponse<ConfirmRepayBean>> {
        return mRepositoryManager.obtainRetrofitService(NewRepaymentService::class.java).confirmRepay(uniqueCode, repayFund, token, sign)
    }

    @Inject
    lateinit var mGson: Gson;
    @Inject
    lateinit var mApplication: Application;

    override fun onDestroy() {
        super.onDestroy();
    }
}
