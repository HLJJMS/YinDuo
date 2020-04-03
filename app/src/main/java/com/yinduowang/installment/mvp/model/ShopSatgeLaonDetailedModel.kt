package com.yinduowang.installment.mvp.model

import android.app.Application
import com.google.gson.Gson
import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel
import com.yinduowang.installment.mvp.contract.ShopSatgeLaonDetailedContract
import com.yinduowang.installment.mvp.model.entity.BaseResponse
import com.yinduowang.installment.mvp.model.entity.LoanShopDetailsEntity
import com.yinduowang.installment.mvp.model.service.LoanShopDetailsService
import io.reactivex.Observable
import javax.inject.Inject


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/31/2019 14:25
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
class ShopSatgeLaonDetailedModel
@Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), ShopSatgeLaonDetailedContract.Model {
    override fun getData(token: String, id: String, sign: String): Observable<BaseResponse<LoanShopDetailsEntity>> {
        return mRepositoryManager.obtainRetrofitService(LoanShopDetailsService::class.java).getData(token, id, sign);
    }


    @Inject
    lateinit var mGson: Gson;
    @Inject
    lateinit var mApplication: Application;

    override fun onDestroy() {
        super.onDestroy();
    }


}
