package com.yinduowang.installment.mvp.model

import android.app.Application
import com.google.gson.Gson
import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel
import com.yinduowang.installment.mvp.contract.RegisterContract
import com.yinduowang.installment.mvp.model.entity.BaseResponse
import com.yinduowang.installment.mvp.model.entity.RegisterEntity
import com.yinduowang.installment.mvp.model.entity.VerifyCodeEntity
import com.yinduowang.installment.mvp.model.service.NewLoginRegisterService
import io.reactivex.Observable
import javax.inject.Inject


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/16/2019 14:09
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
class RegisterModel
@Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), RegisterContract.Model {
    @Inject
    lateinit var mGson: Gson;
    @Inject
    lateinit var mApplication: Application;

    override fun register(mobile: String, authCode: String, password: String, ip: String, gpsAddress: String, phoneModel: String): Observable<BaseResponse<RegisterEntity>> {
        //客户端类型，1 pc, 2 ios, 3 android
        val clientType = "3"
        return mRepositoryManager.obtainRetrofitService(NewLoginRegisterService::class.java).register(mobile, authCode, password, ip, gpsAddress, phoneModel, clientType)
    }
    override fun getVerifyCode(mobile:String,type:String): Observable<BaseResponse<VerifyCodeEntity>> {
        return mRepositoryManager.obtainRetrofitService(NewLoginRegisterService::class.java).getVerifyCode(mobile,type);
    }
    override fun onDestroy() {
        super.onDestroy();
    }
}
