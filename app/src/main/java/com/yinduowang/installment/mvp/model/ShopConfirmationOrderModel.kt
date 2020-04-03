package com.yinduowang.installment.mvp.model

import android.app.Application
import com.google.gson.Gson
import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel
import com.yinduowang.installment.mvp.contract.ShopConfirmationOrderContract
import com.yinduowang.installment.mvp.model.entity.*
import com.yinduowang.installment.mvp.model.service.NewSettingService
import com.yinduowang.installment.mvp.model.service.ShopService
import io.reactivex.Observable
import javax.inject.Inject


/**
 * ================================================
 * Description:商品订单确认
 * <p>
 * Created by MVPArmsTemplate on 08/05/2019 20:56
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
class ShopConfirmationOrderModel
@Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), ShopConfirmationOrderContract.Model {


    @Inject
    lateinit var mGson: Gson;
    @Inject
    lateinit var mApplication: Application;

    override fun onDestroy() {
        super.onDestroy();
    }

    override fun getDeliveryAddress(id: String, token: String, sign: String): Observable<BasePhpResponse<DeliveryAddress>> {
        return mRepositoryManager.obtainRetrofitService(NewSettingService::class.java).getDeliveryAddress(id, token, sign)
    }

    override fun goBuy(price:String,g_id: String, af_id: String, as_id: String, number: String, token: String, ma_id: String, sign: String): Observable<BasePhpResponse<ConfirmationOrder>> {
        return mRepositoryManager.obtainRetrofitService(ShopService::class.java).shopBuy(price,g_id, af_id, as_id, number, token, ma_id, sign)
    }
    override fun getGoBuy(price: String, g_id: String, af_id: String, as_id: String, number: String, token: String, ma_id: String,cycle: String, sign: String): Observable<BasePhpResponse<OrderFee>> {
        return mRepositoryManager.obtainRetrofitService(ShopService::class.java).getShopBuy(price,g_id, af_id, as_id, number, token, ma_id,cycle, sign)
    }
}
