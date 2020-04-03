package com.yinduowang.installment.mvp.model

import android.app.Application
import com.google.gson.Gson
import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel

import com.jess.arms.di.scope.ActivityScope
import com.yinduowang.installment.app.utils.MD5Util
import javax.inject.Inject

import com.yinduowang.installment.mvp.contract.MyLogisticsContract
import com.yinduowang.installment.mvp.model.entity.BasePhpResponse
import com.yinduowang.installment.mvp.model.entity.BaseResponse
import com.yinduowang.installment.mvp.model.entity.GetLogisticsEntity
import com.yinduowang.installment.mvp.model.service.AuthenticationCenterService
import com.yinduowang.installment.mvp.model.service.ShopService
import io.reactivex.Observable


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/03/2019 10:20
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
class MyLogisticsModel
@Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), MyLogisticsContract.Model {
    override fun logisticsDetail(o_id: String, token: String, sign: String): Observable<BasePhpResponse<GetLogisticsEntity>> {
        return mRepositoryManager.obtainRetrofitService(ShopService::class.java).logisticsDetail(o_id, token, sign)
    }

    @Inject
    lateinit var mGson: Gson;
    @Inject
    lateinit var mApplication: Application;

    override fun onDestroy() {
        super.onDestroy();
    }
}
