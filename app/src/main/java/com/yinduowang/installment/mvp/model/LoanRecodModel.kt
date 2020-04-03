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

import com.yinduowang.installment.mvp.contract.LoanRecoderContract
import com.yinduowang.installment.mvp.model.entity.BaseResponse
import com.yinduowang.installment.mvp.model.entity.LoanRecordEntity
import com.yinduowang.installment.mvp.model.service.NewLoanService
import io.reactivex.Observable


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/30/2019 20:22
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
class LoanRecodModel
@Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), LoanRecoderContract.Model {
    override fun getLoanRecordList(id: Int, loadType: String): Observable<BaseResponse<LoanRecordEntity>> {
        val map =HashMap<String,String>()
        map["id"]=id.toString()
        map["loadType"]=loadType
        return mRepositoryManager.obtainRetrofitService(NewLoanService::class.java).getLoanRecordList(id,loadType, SPUtils.getInstance().getString(SPConstant.TOKEN), SignUtils.getSign(map))
    }


    @Inject
    lateinit var mGson: Gson;
    @Inject
    lateinit var mApplication: Application;

    override fun onDestroy() {
        super.onDestroy();
    }
}
