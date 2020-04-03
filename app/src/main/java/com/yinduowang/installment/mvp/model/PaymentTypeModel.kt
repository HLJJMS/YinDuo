package com.yinduowang.installment.mvp.model

import android.app.Application
import com.google.gson.Gson
import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel
import com.yinduowang.installment.mvp.contract.PaymentTypeContract
import com.yinduowang.installment.mvp.model.entity.BasePhpResponse
import com.yinduowang.installment.mvp.model.entity.BaseResponse
import com.yinduowang.installment.mvp.model.entity.CheckQuotaEntity
import com.yinduowang.installment.mvp.model.entity.GetOrderPay
import com.yinduowang.installment.mvp.model.service.ShopService
import io.reactivex.Observable
import javax.inject.Inject


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/05/2019 17:23
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
class PaymentTypeModel
@Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), PaymentTypeContract.Model {

    @Inject
    lateinit var mGson: Gson;
    @Inject
    lateinit var mApplication: Application;

    override fun onDestroy() {
        super.onDestroy();
    }

    override fun getShopDetailForPay(o_id: String, cycle: String, token: String, sign: String): Observable<BasePhpResponse<GetOrderPay>> {
        return mRepositoryManager.obtainRetrofitService(ShopService::class.java).getShopDetailForPay(o_id, cycle, token, sign)
    }

    override fun postPay(o_id: String, token: String, total_money: String, cycle: String, password: String, config_id: String, sign: String): Observable<BasePhpResponse<Any>> {
        return mRepositoryManager.obtainRetrofitService(ShopService::class.java).postPay(o_id, token, total_money, cycle, password, config_id, sign)
    }

    override fun goCheckQuota(token: String, sign: String): Observable<BaseResponse<CheckQuotaEntity>> {
        return mRepositoryManager.obtainRetrofitService(ShopService::class.java).checkQuota(token, sign)
    }
}
