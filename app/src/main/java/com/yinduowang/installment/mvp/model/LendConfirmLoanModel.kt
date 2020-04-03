package com.yinduowang.installment.mvp.model

import android.app.Application
import com.blankj.utilcode.util.SPUtils
import com.google.gson.Gson
import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel
import com.yinduowang.installment.app.constant.SPConstant
import com.yinduowang.installment.app.utils.SignUtils
import com.yinduowang.installment.mvp.contract.LendConfirmLoanContract
import com.yinduowang.installment.mvp.model.entity.BankCardListEntity
import com.yinduowang.installment.mvp.model.entity.BaseResponse
import com.yinduowang.installment.mvp.model.entity.ConfirmEntity
import com.yinduowang.installment.mvp.model.entity.ConfirmLoanEntity
import com.yinduowang.installment.mvp.model.service.BankCardService
import com.yinduowang.installment.mvp.model.service.NewLoanService
import com.yinduowang.installment.mvp.model.service.NewSettingService
import io.reactivex.Observable
import java.util.*
import javax.inject.Inject


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/27/2019 17:44
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
class LendConfirmLoanModel
@Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), LendConfirmLoanContract.Model {


    override fun getBankCardList(bankCardId: String): Observable<BaseResponse<BankCardListEntity>> {
        val map = mapOf("bankCardId" to bankCardId)
        return mRepositoryManager.obtainRetrofitService(BankCardService::class.java).getBankCardList(bankCardId, SPUtils.getInstance().getString(SPConstant.TOKEN), SignUtils.getSign(map))
    }

    override fun getLoanApplyPage(loanFund: String): Observable<BaseResponse<ConfirmLoanEntity>> {
        val token = SPUtils.getInstance().getString(SPConstant.TOKEN)
        val map = mapOf("loanFund" to loanFund)
        return mRepositoryManager.obtainRetrofitService(NewLoanService::class.java).getLoanApplyPage(token, loanFund, SignUtils.getSign(map))
    }

    override fun confirmApplyLoan(password: String, loanPurposeId: String, loanMoney: String, type: String, stage: String, bankId: String, configId: String, lonaDays: String): Observable<BaseResponse<ConfirmEntity>> {
        val map = HashMap<String, String>()
        map["password"] = password
        map["loanMoney"] = loanMoney
        map["type"] = type
        map["bankId"] = bankId
        map["loanPurposeId"] = loanPurposeId
        map["configId"] = configId
        map["stage"] = lonaDays
        return mRepositoryManager.obtainRetrofitService(NewLoanService::class.java).confirmApplyLoan(map, SPUtils.getInstance().getString(SPConstant.TOKEN), SignUtils.getSign(map))
    }

    override fun vaildatePswSet(): Observable<BaseResponse<Any>> {
        val map = HashMap<String, String>()
        return mRepositoryManager.obtainRetrofitService(NewSettingService::class.java).validatePayPswSet(SPUtils.getInstance().getString(SPConstant.TOKEN), SignUtils.getSign(map));
    }

    @Inject
    lateinit var mGson: Gson;
    @Inject
    lateinit var mApplication: Application;

    override fun onDestroy() {
        super.onDestroy();
    }
}
