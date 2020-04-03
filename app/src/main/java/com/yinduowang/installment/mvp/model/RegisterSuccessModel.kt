package com.yinduowang.installment.mvp.model

import android.app.Application
import com.blankj.utilcode.util.SPUtils
import com.google.gson.Gson
import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel
import com.yinduowang.installment.app.constant.SPConstant
import com.yinduowang.installment.app.utils.MD5Util
import com.yinduowang.installment.mvp.contract.RegisterSuccessContract
import com.yinduowang.installment.mvp.model.entity.BaseResponse
import com.yinduowang.installment.mvp.model.service.NewLoginRegisterService
import io.reactivex.Observable
import javax.inject.Inject


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/30/2019 08:45
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
class RegisterSuccessModel
@Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), RegisterSuccessContract.Model {
    @Inject
    lateinit var mGson: Gson;
    @Inject
    lateinit var mApplication: Application;

    override fun onDestroy() {
        super.onDestroy();
    }

    override fun closeVipPop(): Observable<BaseResponse<Any>> {
        val token = SPUtils.getInstance().getString(SPConstant.TOKEN)
        return mRepositoryManager.obtainRetrofitService(NewLoginRegisterService::class.java).closeVipPop(token, MD5Util.getMD5String(token))
    }
}
