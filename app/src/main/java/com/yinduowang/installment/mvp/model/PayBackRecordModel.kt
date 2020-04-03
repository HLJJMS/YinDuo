package com.yinduowang.installment.mvp.model

import android.app.Application
import com.blankj.utilcode.util.SPUtils
import com.google.gson.Gson
import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel

import com.jess.arms.di.scope.ActivityScope
import com.yinduowang.installment.app.constant.SPConstant
import com.yinduowang.installment.app.utils.SignUtils
import javax.inject.Inject

import com.yinduowang.installment.mvp.contract.PayBackRecordContract
import com.yinduowang.installment.mvp.model.entity.BaseResponse
import com.yinduowang.installment.mvp.model.entity.PayBackRecordEntity
import com.yinduowang.installment.mvp.model.service.NewLoanService
import io.reactivex.Observable


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/01/2019 10:04
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
class PayBackRecordModel
@Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), PayBackRecordContract.Model {
    @Inject
    lateinit var mGson: Gson;
    @Inject
    lateinit var mApplication: Application;
    override fun getPayBackRecordList(id: String, type: String): Observable<BaseResponse<PayBackRecordEntity>> {
        val map = mapOf( "id" to id,"type" to type)
        return mRepositoryManager.obtainRetrofitService(NewLoanService::class.java).getPayBackRecord(id,type, SPUtils.getInstance().getString(SPConstant.TOKEN), SignUtils.getSign(map))
    }

    override fun onDestroy() {
        super.onDestroy();
    }
}
