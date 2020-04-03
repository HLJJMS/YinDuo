package com.yinduowang.installment.mvp.model

import android.app.Application
import com.blankj.utilcode.util.SPUtils
import com.google.gson.Gson
import com.jess.arms.di.scope.FragmentScope
import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel
import com.yinduowang.installment.app.constant.SPConstant
import com.yinduowang.installment.app.utils.SignUtils
import com.yinduowang.installment.mvp.contract.LoanContract
import com.yinduowang.installment.mvp.model.entity.BaseResponse
import com.yinduowang.installment.mvp.model.entity.LoanBannerEntity
import com.yinduowang.installment.mvp.model.entity.NewLoanEntity
import com.yinduowang.installment.mvp.model.service.NewLoanService
import io.reactivex.Observable
import javax.inject.Inject


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
@FragmentScope
class LoanModel
@Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), LoanContract.Model {
    override fun getSignContract(loanId: String): Observable<BaseResponse<Any>> {
        var map = mapOf("id" to loanId)
        return mRepositoryManager.obtainRetrofitService(NewLoanService::class.java).getSignContract(SPUtils.getInstance().getString(SPConstant.TOKEN), SignUtils.getSign(map), loanId)
    }

    override fun confirmApply(loanMoney: String): Observable<BaseResponse<Any>> {
        val token = SPUtils.getInstance().getString(SPConstant.TOKEN)
        return mRepositoryManager.obtainRetrofitService(NewLoanService::class.java).confirmApply(token, loanMoney)
    }

    override fun getLoanBanner(page: String, ac_type: String): Observable<LoanBannerEntity> {
        val token = SPUtils.getInstance().getString(SPConstant.TOKEN)
        var map = mapOf("page" to page, "ac_type" to ac_type)
        return mRepositoryManager.obtainRetrofitService(NewLoanService::class.java).getLoanBanner(page, ac_type, token)
    }

    override fun getLoanInfo1(): Observable<BaseResponse<NewLoanEntity>> {
        return mRepositoryManager.obtainRetrofitService(NewLoanService::class.java).getLoan(SPUtils.getInstance().getString(SPConstant.TOKEN))
    }

    @Inject
    lateinit var mGson: Gson;
    @Inject
    lateinit var mApplication: Application;

    override fun onDestroy() {
        super.onDestroy();
    }
}
