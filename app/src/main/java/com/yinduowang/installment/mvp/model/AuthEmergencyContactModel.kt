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
import com.yinduowang.installment.mvp.contract.AuthEmergencyContactContract
import com.yinduowang.installment.mvp.model.entity.AuthEmergencyContactEntity
import com.yinduowang.installment.mvp.model.entity.BaseResponse
import com.yinduowang.installment.mvp.model.service.AuthenticationCenterService
import io.reactivex.Observable
import javax.inject.Inject


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/01/2019 08:38
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
class AuthEmergencyContactModel
@Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), AuthEmergencyContactContract.Model {
    override fun getData(): Observable<BaseResponse<AuthEmergencyContactEntity>> {
        var token = SPUtils.getInstance().getString(SPConstant.TOKEN)
        return mRepositoryManager.obtainRetrofitService(AuthenticationCenterService::class.java).getData(token, MD5Util.getMD5String(token))
    }

    override fun saveContacts(map: HashMap<String, String>): Observable<BaseResponse<Any>> {
        return mRepositoryManager.obtainRetrofitService(AuthenticationCenterService::class.java).saveContacts(map, SPUtils.getInstance().getString(SPConstant.TOKEN), SignUtils.getSign(map))
    }

    @Inject
    lateinit var mGson: Gson;
    @Inject
    lateinit var mApplication: Application;

    override fun onDestroy() {
        super.onDestroy();
    }


}
