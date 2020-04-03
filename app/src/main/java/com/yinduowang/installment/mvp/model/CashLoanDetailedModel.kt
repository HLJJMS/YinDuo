package com.yinduowang.installment.mvp.model

import android.app.Application
import com.blankj.utilcode.util.SPUtils
import com.google.gson.Gson
import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel
import com.yinduowang.installment.app.constant.SPConstant
import com.yinduowang.installment.app.utils.SignUtils
import com.yinduowang.installment.mvp.contract.CashLoanDetailedContract
import com.yinduowang.installment.mvp.model.entity.BaseResponse
import com.yinduowang.installment.mvp.model.entity.CashLoanRecordEntity
import com.yinduowang.installment.mvp.model.service.NewLoanService
import com.yinduowang.installment.mvp.model.service.PublicHttpService
import io.reactivex.Observable
import java.util.*
import javax.inject.Inject


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/31/2019 14:26
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
class CashLoanDetailedModel
@Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), CashLoanDetailedContract.Model {
    override fun getSignContract(loanId: String): Observable<BaseResponse<Any>> {
        var map = mapOf("id" to loanId)
        return mRepositoryManager.obtainRetrofitService(NewLoanService::class.java).getSignContract(SPUtils.getInstance().getString(SPConstant.TOKEN), SignUtils.getSign(map), loanId)
    }

    override fun getCashLoanDetailed(id: Int): Observable<BaseResponse<CashLoanRecordEntity>> {
        val map = mapOf("id" to id.toString())
        return mRepositoryManager.obtainRetrofitService(NewLoanService::class.java).getCashLoanDetailed(id, SPUtils.getInstance().getString(SPConstant.TOKEN), SignUtils.getSign(map))
    }

    @Inject
    lateinit var mGson: Gson;
    @Inject
    lateinit var mApplication: Application;

    override fun onDestroy() {
        super.onDestroy();
    }

    override fun checkRepay(type: String, loanId: String): Observable<BaseResponse<Any>> {
        val map = HashMap<String, String>()
        map["type"] =  type
        map["loanId"] = loanId
        map["sign"] = SignUtils.getSign(map)
        map["token"] =  SPUtils.getInstance().getString(SPConstant.TOKEN)
        return mRepositoryManager.obtainRetrofitService(PublicHttpService::class.java).checkRepay(map)
    }
}
