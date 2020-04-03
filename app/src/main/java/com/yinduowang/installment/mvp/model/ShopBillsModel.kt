package com.yinduowang.installment.mvp.model

import android.app.Application
import com.blankj.utilcode.util.SPUtils
import com.google.gson.Gson
import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel
import com.yinduowang.installment.app.constant.SPConstant
import com.yinduowang.installment.app.utils.SignUtils
import com.yinduowang.installment.mvp.contract.ShopBillsContract
import com.yinduowang.installment.mvp.model.entity.BaseResponse
import com.yinduowang.installment.mvp.model.entity.ShopBillsEntity
import com.yinduowang.installment.mvp.model.service.BillsService
import com.yinduowang.installment.mvp.model.service.PublicHttpService
import io.reactivex.Observable
import java.util.*
import javax.inject.Inject


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/03/2019 09:19
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
class ShopBillsModel
@Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), ShopBillsContract.Model {
    override fun getShopBills(year: String, month: String, flag: String): Observable<BaseResponse<ShopBillsEntity>> {
        val type = "2"//商城type为2 现金type为1
        val map= mapOf("year" to year,
                       "month" to month,
                "flag" to flag,
                "type" to type)
        return mRepositoryManager.obtainRetrofitService(BillsService::class.java).getShopBills(SPUtils.getInstance().getString(SPConstant.TOKEN), SignUtils.getSign(map), year, month, flag, type)
    }

    override fun checkRepay(type: String, loanSumId: String): Observable<BaseResponse<Any>> {
        val map = HashMap<String, String>()
        map["type"] = type
        map["loanSumId"] = loanSumId
        map["sign"] = SignUtils.getSign(map)
        map["token"] = SPUtils.getInstance().getString(SPConstant.TOKEN)
        return mRepositoryManager.obtainRetrofitService(PublicHttpService::class.java).checkRepay(map)
    }

    @Inject
    lateinit var mGson: Gson;
    @Inject
    lateinit var mApplication: Application;

    override fun onDestroy() {
        super.onDestroy();
    }
}
