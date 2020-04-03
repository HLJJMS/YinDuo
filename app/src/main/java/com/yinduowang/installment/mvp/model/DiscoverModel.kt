package com.yinduowang.installment.mvp.model

import android.app.Application
import com.google.gson.Gson
import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel

import com.jess.arms.di.scope.FragmentScope
import javax.inject.Inject

import com.yinduowang.installment.mvp.contract.DiscoverContract
import com.yinduowang.installment.mvp.model.entity.BaseResponse
import com.yinduowang.installment.mvp.model.service.NewLoginRegisterService
import io.reactivex.Observable


/**
 * @Description:
 * @Author:
 * @Date: 2019-11-13 11:28:50
 * @Version: appVersionName, 2019-11-13
 * @LastEditors:
 * @LastEditTime:
 * @Deprecated: false
 */
@FragmentScope
class DiscoverModel
@Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), DiscoverContract.Model {
    @Inject
    lateinit var mGson: Gson;
    @Inject
    lateinit var mApplication: Application;

    override fun onDestroy() {
        super.onDestroy();
    }

    //    获取客服电话
    override fun serviceTel(): Observable<BaseResponse<String>> {
        return mRepositoryManager.obtainRetrofitService<NewLoginRegisterService>(NewLoginRegisterService::class.java).serviceTel()
    }
}
