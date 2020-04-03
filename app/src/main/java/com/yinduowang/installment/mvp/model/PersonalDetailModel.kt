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
import com.yinduowang.installment.mvp.contract.PersonalDetailContract
import com.yinduowang.installment.mvp.model.entity.BaseResponse
import com.yinduowang.installment.mvp.model.entity.UserInfoAllEntity
import com.yinduowang.installment.mvp.model.service.AuthenticationCenterService
import io.reactivex.Observable
import javax.inject.Inject


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/30/2019 09:00
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
class PersonalDetailModel
@Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), PersonalDetailContract.Model {
    @Inject
    lateinit var mGson: Gson;
    @Inject
    lateinit var mApplication: Application;

    override fun onDestroy() {
        super.onDestroy();
    }

    override fun getUserInfoAll(): Observable<BaseResponse<UserInfoAllEntity>> {
        val token = SPUtils.getInstance().getString(SPConstant.TOKEN)
        return mRepositoryManager.obtainRetrofitService(AuthenticationCenterService::class.java).getUserInfoAll(token, MD5Util.getMD5String(token))
    }

    override fun getVerifyRPBasicToken(): Observable<BaseResponse<Map<String, String>>> {
        val token = SPUtils.getInstance().getString(SPConstant.TOKEN)
        return mRepositoryManager.obtainRetrofitService(AuthenticationCenterService::class.java).getVerifyRPBasicToken(token, MD5Util.getMD5String(token))
    }

    override fun callbackVerifyRPBasic(aliyunToken: String): Observable<BaseResponse<Map<String, String>>> {
        val token = SPUtils.getInstance().getString(SPConstant.TOKEN)
        val map = HashMap<String, String>()
        map["aliyunToken"] = aliyunToken
        return mRepositoryManager.obtainRetrofitService(AuthenticationCenterService::class.java).callbackVerifyRPBasicToken(token, SignUtils.getSign(map), aliyunToken)
    }

    override fun upadateUserinfo(map: HashMap<String, String>): Observable<BaseResponse<Any>> {
        return mRepositoryManager.obtainRetrofitService(AuthenticationCenterService::class.java).upadateUserinfo(SPUtils.getInstance().getString(SPConstant.TOKEN), SignUtils.getSign(map), map)
    }
}
