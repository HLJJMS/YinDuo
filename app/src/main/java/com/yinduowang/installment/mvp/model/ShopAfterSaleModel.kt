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

import com.yinduowang.installment.mvp.contract.ShopAfterSaleContract
import com.yinduowang.installment.mvp.model.entity.BasePhpResponse
import com.yinduowang.installment.mvp.model.entity.ShopOrderEntity
import com.yinduowang.installment.mvp.model.service.ShopService
import io.reactivex.Observable


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/03/2019 14:30
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
class ShopAfterSaleModel
@Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), ShopAfterSaleContract.Model {
    @Inject
    lateinit var mGson: Gson;
    @Inject
    lateinit var mApplication: Application;

    override fun onDestroy() {
        super.onDestroy();
    }
    override fun getList(page: String, status: String,limit:String): Observable<BasePhpResponse<ShopOrderEntity>> {
        val map = mapOf( "page" to page,
                "retreat_status" to status,
                "limit" to limit
        )
        return mRepositoryManager.obtainRetrofitService(ShopService::class.java).getAfterOrder(limit,page,status, SPUtils.getInstance().getString(SPConstant.TOKEN), SignUtils.getSign(map))
    }
}
