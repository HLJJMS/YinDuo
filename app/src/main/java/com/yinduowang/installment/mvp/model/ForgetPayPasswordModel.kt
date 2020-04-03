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

import com.yinduowang.installment.mvp.contract.ForgetPayPasswordContract
import com.yinduowang.installment.mvp.model.entity.BaseResponse
import com.yinduowang.installment.mvp.model.entity.VerifyCodeEntity
import com.yinduowang.installment.mvp.model.service.NewLoginRegisterService
import com.yinduowang.installment.mvp.model.service.NewSettingService
import io.reactivex.Observable


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/25/2019 10:54
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
class ForgetPayPasswordModel
@Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), ForgetPayPasswordContract.Model {
    override fun forgetPayPsw(mobile: String, authCode: String, idCard: String, realName: String): Observable<BaseResponse<Any>> {
        val map=HashMap<String,String>()
        map["mobile"]=mobile
        map["authCode"]=authCode
        map["idCard"]=idCard
        map["realName"]=realName
        return mRepositoryManager.obtainRetrofitService(NewSettingService::class.java).forgetPayPsw(mobile,authCode,idCard,realName, SPUtils.getInstance().getString(SPConstant.TOKEN), SignUtils.getSign(map))
    }
    override fun getVerifyCode(mobile:String,type:String): Observable<BaseResponse<VerifyCodeEntity>> {
        return mRepositoryManager.obtainRetrofitService(NewLoginRegisterService::class.java).getVerifyCode(mobile,type);
    }
    @Inject
    lateinit var mGson: Gson;
    @Inject
    lateinit var mApplication: Application;

    override fun onDestroy() {
        super.onDestroy();
    }
}
