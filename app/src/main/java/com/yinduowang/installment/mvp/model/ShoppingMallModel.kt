package com.yinduowang.installment.mvp.model

import android.app.Application
import com.blankj.utilcode.util.SPUtils
import com.google.gson.Gson
import com.jess.arms.di.scope.FragmentScope
import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel
import com.yinduowang.installment.app.constant.SPConstant
import com.yinduowang.installment.mvp.contract.ShoppingMallContract
import com.yinduowang.installment.mvp.model.entity.BaseResponse
import com.yinduowang.installment.mvp.model.entity.HomeShoppingMallEntity
import com.yinduowang.installment.mvp.model.entity.LoginMessage
import com.yinduowang.installment.mvp.model.service.NewLoginRegisterService
import io.reactivex.Observable
import javax.inject.Inject


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/25/2019 13:10
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
class ShoppingMallModel
@Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), ShoppingMallContract.Model {
    @Inject
    lateinit var mGson: Gson;
    @Inject
    lateinit var mApplication: Application;

    override fun onDestroy() {
        super.onDestroy();
    }

    override fun getLoginMessage(): Observable<BaseResponse<LoginMessage>> {
        return mRepositoryManager.obtainRetrofitService(NewLoginRegisterService::class.java).getLoginMessage()
    }

    override fun getShoppingMallData(): Observable<HomeShoppingMallEntity> {
        val token = SPUtils.getInstance().getString(SPConstant.TOKEN)
        return mRepositoryManager.obtainRetrofitService(NewLoginRegisterService::class.java).getShoppingMallData(token)
    }
}
