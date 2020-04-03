package com.yinduowang.installment.mvp.model

import android.app.Application
import com.blankj.utilcode.util.SPUtils
import com.google.gson.Gson
import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel
import com.yinduowang.installment.app.constant.SPConstant
import com.yinduowang.installment.app.utils.MD5Util
import com.yinduowang.installment.mvp.contract.CommWebViewContract
import com.yinduowang.installment.mvp.model.entity.BaseResponse
import com.yinduowang.installment.mvp.model.service.AuthenticationCenterService
import com.yinduowang.installment.mvp.model.service.NewLoanService
import com.yinduowang.installment.mvp.model.service.NewLoginRegisterService
import io.reactivex.Observable
import javax.inject.Inject


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/24/2019 13:20
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
class CommWebViewModel
@Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), CommWebViewContract.Model {
    @Inject
    lateinit var mGson: Gson;
    @Inject
    lateinit var mApplication: Application;

    override fun onDestroy() {
        super.onDestroy();
    }

    //    获取客服电话
    override fun serviceTel(): Observable<BaseResponse<String>> {
        return mRepositoryManager.obtainRetrofitService<NewLoginRegisterService>(NewLoginRegisterService::class.java).serviceTel();
    }

    override fun openAccount(): Observable<BaseResponse<Map<String, String>>> {
        val token = SPUtils.getInstance().getString(SPConstant.TOKEN)
        return mRepositoryManager.obtainRetrofitService(AuthenticationCenterService::class.java).openAccount(token, MD5Util.getMD5String(token))
    }

    override fun onFloatDetailedAreaClick(token: String, sourceId: String, ac_type: String, type: String, ip: String): Observable<BaseResponse<Any>> {
        return mRepositoryManager.obtainRetrofitService(NewLoanService::class.java).onFloatDetailedAreaClick(token, sourceId, ac_type, type, ip)
    }
}
