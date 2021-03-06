package com.yinduowang.installment.mvp.model

import android.app.Application
import com.blankj.utilcode.util.SPUtils
import com.google.gson.Gson
import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel
import com.yinduowang.installment.app.constant.SPConstant
import com.yinduowang.installment.app.utils.SignUtils
import com.yinduowang.installment.mvp.contract.NewCashAllBillsContract
import com.yinduowang.installment.mvp.model.entity.BaseResponse
import com.yinduowang.installment.mvp.model.entity.NewCashAllBillsEntity
import com.yinduowang.installment.mvp.model.service.BillsService
import io.reactivex.Observable
import javax.inject.Inject


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/03/2019 14:21
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
class NewCashAllBillsModel
@Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), NewCashAllBillsContract.Model {
    override fun getCashAllBills(year: String, flag: String): Observable<BaseResponse<NewCashAllBillsEntity>> {
        val type = "1"
        var map = mapOf("year" to year, "flag" to flag
                , "type" to type)
        return mRepositoryManager.obtainRetrofitService(BillsService::class.java).getNewCashAllBills(SPUtils.getInstance().getString(SPConstant.TOKEN), SignUtils.getSign(map), year, flag, type)
    }

    @Inject
    lateinit var mGson: Gson;
    @Inject
    lateinit var mApplication: Application;

    override fun onDestroy() {
        super.onDestroy();
    }
}
