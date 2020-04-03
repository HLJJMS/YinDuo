package com.yinduowang.installment.mvp.model

import android.app.Application
import com.blankj.utilcode.util.SPUtils
import com.google.gson.Gson
import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel
import com.yinduowang.installment.app.constant.SPConstant
import com.yinduowang.installment.app.utils.SignUtils
import com.yinduowang.installment.mvp.contract.ChangePasswordContract
import com.yinduowang.installment.mvp.model.entity.BaseResponse
import com.yinduowang.installment.mvp.model.service.NewSettingService
import io.reactivex.Observable
import javax.inject.Inject


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/23/2019 13:53
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
class ChangePasswordModel
@Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), ChangePasswordContract.Model {
    override fun changePwd( oldpw: String, newpw: String, newpwagain: String): Observable<BaseResponse<Any>> {
        val map =HashMap<String,String>()
        map["oldpw"]=oldpw
        map["newpw"]=newpw
        map["newpwagain"]=newpwagain
        return  mRepositoryManager.obtainRetrofitService(NewSettingService::class.java).changePwd(SPUtils.getInstance().getString(SPConstant.TOKEN), oldpw, newpw, newpwagain, SignUtils.getSign(map))
    }

    @Inject
    lateinit var mGson: Gson;
    @Inject
    lateinit var mApplication: Application;

    override fun onDestroy() {
        super.onDestroy();
    }
}
