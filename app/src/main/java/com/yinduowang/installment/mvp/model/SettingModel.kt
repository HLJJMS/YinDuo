package com.yinduowang.installment.mvp.model

import android.app.Application
import com.blankj.utilcode.util.SPUtils
import com.google.gson.Gson
import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel
import com.yinduowang.installment.app.constant.SPConstant
import com.yinduowang.installment.app.utils.MD5Util
import com.yinduowang.installment.app.utils.SignUtils
import com.yinduowang.installment.mvp.contract.SettingContract
import com.yinduowang.installment.mvp.model.entity.BaseResponse
import com.yinduowang.installment.mvp.model.entity.PerfectEntity
import com.yinduowang.installment.mvp.model.service.AuthenticationCenterService
import com.yinduowang.installment.mvp.model.service.NewSettingService
import io.reactivex.Observable
import javax.inject.Inject


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/23/2019 10:50
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
class SettingModel
@Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), SettingContract.Model {


    override fun loginOut(): Observable<BaseResponse<Any>> {
        val map=HashMap<String,String>()
        return mRepositoryManager.obtainRetrofitService(NewSettingService::class.java).loginOut(SPUtils.getInstance().getString(SPConstant.TOKEN), SignUtils.getSign(map));
    }

    override fun vaildatePswSet(): Observable<BaseResponse<Any>> {
        val map=HashMap<String,String>()
        return mRepositoryManager.obtainRetrofitService(NewSettingService::class.java).validatePayPswSet(SPUtils.getInstance().getString(SPConstant.TOKEN), SignUtils.getSign(map));
    }


    override fun perfect(): Observable<BaseResponse<PerfectEntity>> {
        val token = SPUtils.getInstance().getString(SPConstant.TOKEN)
        return mRepositoryManager.obtainRetrofitService(AuthenticationCenterService::class.java).perfect(token, MD5Util.getMD5String(token))

    }
    @Inject
    lateinit var mGson: Gson;
    @Inject
    lateinit var mApplication: Application;

    override fun onDestroy() {
        super.onDestroy();
    }
}
