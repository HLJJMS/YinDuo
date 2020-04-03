package com.yinduowang.installment.mvp.model

import android.app.Application
import com.google.gson.Gson
import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel
import com.yinduowang.installment.mvp.contract.LoginContract
import com.yinduowang.installment.mvp.model.entity.BaseResponse
import com.yinduowang.installment.mvp.model.entity.NewLoginEntity
import com.yinduowang.installment.mvp.model.entity.VerifyCodeEntity
import com.yinduowang.installment.mvp.model.service.NewLoginRegisterService
import io.reactivex.Observable
import javax.inject.Inject


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/16/2019 09:47
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
class LoginModel
@Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), LoginContract.Model {
    //    获取客服电话
    override fun serviceTel(): Observable<BaseResponse<String>> {
        return mRepositoryManager.obtainRetrofitService<NewLoginRegisterService>(NewLoginRegisterService::class.java).serviceTel()
    }

    override fun verifyLogin(mobile: String, authCode: String, ip: String, gpsAddress: String, phoneModel: String): Observable<BaseResponse<NewLoginEntity>> {
        return mRepositoryManager.obtainRetrofitService<NewLoginRegisterService>(NewLoginRegisterService::class.java).verifyLogin(mobile, authCode, ip, gpsAddress, phoneModel)
    }

    override fun getVerifyCode(mobile: String, type: String): Observable<BaseResponse<VerifyCodeEntity>> {
        return mRepositoryManager.obtainRetrofitService(NewLoginRegisterService::class.java).getVerifyCode(mobile, type)
    }

    override fun passwordLogin(mobile: String, password: String, ip: String, gpsAddress: String, phoneModel: String): Observable<BaseResponse<NewLoginEntity>> {
        return mRepositoryManager.obtainRetrofitService(NewLoginRegisterService::class.java).passwordLogin(mobile, password, ip, gpsAddress, phoneModel)
    }

    @Inject
    lateinit var mGson: Gson;
    @Inject
    lateinit var mApplication: Application;

    override fun onDestroy() {
        super.onDestroy();
    }
}
