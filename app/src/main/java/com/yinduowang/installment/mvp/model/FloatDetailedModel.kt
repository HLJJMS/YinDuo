package com.yinduowang.installment.mvp.model

import android.app.Application
import com.blankj.utilcode.util.SPUtils
import com.google.gson.Gson
import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel
import com.yinduowang.installment.app.constant.SPConstant
import com.yinduowang.installment.app.utils.SignUtils
import com.yinduowang.installment.mvp.contract.FloatDetailedContract
import com.yinduowang.installment.mvp.model.entity.BaseResponse
import com.yinduowang.installment.mvp.model.entity.LoanBannerEntity
import com.yinduowang.installment.mvp.model.service.NewLoanService
import io.reactivex.Observable
import javax.inject.Inject


/**
 * @Description:
 * @Author:
 * @Date: 2019-10-23 10:11:28
 * @Version: appVersionName, 2019-10-23
 * @LastEditors:
 * @LastEditTime:
 * @Deprecated: false
 */
@ActivityScope
class FloatDetailedModel
@Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), FloatDetailedContract.Model {
    @Inject
    lateinit var mGson: Gson;
    @Inject
    lateinit var mApplication: Application;

    override fun getFloatDetailedList(page: String, ac_type: String): Observable<LoanBannerEntity> {
        val token = SPUtils.getInstance().getString(SPConstant.TOKEN)
        var map = mapOf("page" to page, "ac_type" to ac_type)
        return mRepositoryManager.obtainRetrofitService(NewLoanService::class.java).getFloatDetailedList(page, ac_type, token, SignUtils.getSign(map))
    }

    override fun onFloatDetailedAreaClick(token: String, sourceId: String, ac_type: String, type: String, ip: String): Observable<BaseResponse<Any>> {
        return mRepositoryManager.obtainRetrofitService(NewLoanService::class.java).onFloatDetailedAreaClick(token, sourceId, ac_type, type, ip)
    }

    override fun onDestroy() {
        super.onDestroy();
    }
}
