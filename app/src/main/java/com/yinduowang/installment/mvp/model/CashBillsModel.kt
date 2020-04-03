package com.yinduowang.installment.mvp.model

import android.app.Application
import com.blankj.utilcode.util.SPUtils
import com.google.gson.Gson
import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel

import com.jess.arms.di.scope.ActivityScope
import com.yinduowang.installment.app.constant.SPConstant
import com.yinduowang.installment.app.utils.MD5Util
import javax.inject.Inject

import com.yinduowang.installment.mvp.contract.CashBillsContract
import com.yinduowang.installment.mvp.model.entity.BaseResponse
import com.yinduowang.installment.mvp.model.entity.CashBillsEntity
import com.yinduowang.installment.mvp.model.service.BillsService
import io.reactivex.Observable


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/03/2019 15:47
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
class CashBillsModel
@Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), CashBillsContract.Model {
    override fun getCashBills(): Observable<BaseResponse<CashBillsEntity>> {
        val token = SPUtils.getInstance().getString(SPConstant.TOKEN)
        return mRepositoryManager.obtainRetrofitService(BillsService::class.java).getCashBills(token, MD5Util.getMD5String(token))
    }


    @Inject
    lateinit var mGson: Gson;
    @Inject
    lateinit var mApplication: Application;

    override fun onDestroy() {
        super.onDestroy();
    }
}
