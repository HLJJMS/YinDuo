package com.yinduowang.installment.mvp.model

import android.app.Application
import com.blankj.utilcode.util.SPUtils
import com.google.gson.Gson
import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel
import com.yinduowang.installment.app.constant.SPConstant
import com.yinduowang.installment.app.utils.SignUtils
import com.yinduowang.installment.mvp.contract.SetTradePwdContract
import com.yinduowang.installment.mvp.model.entity.BaseResponse
import com.yinduowang.installment.mvp.model.service.NewSettingService
import io.reactivex.Observable
import java.util.*
import javax.inject.Inject


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/23/2019 14:03
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
class SetTradePwdModel
@Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), SetTradePwdContract.Model {
    override fun setPayPsw(newpw: String, againnewpw: String, isSetted: Boolean): Observable<BaseResponse<Any>> {
        val map = HashMap<String, String>()
        map["newpw"] = newpw
        map["againnewpw"] = againnewpw
        //判断如果是已设置过密码则走setPayOldPsw这个接口不是则走setPayPsw
        if (isSetted) {
            return mRepositoryManager.obtainRetrofitService(NewSettingService::class.java).setPayOldPsw(newpw, againnewpw, SPUtils.getInstance().getString(SPConstant.TOKEN), SignUtils.getSign(map))
        } else {
            return mRepositoryManager.obtainRetrofitService(NewSettingService::class.java).setPayPsw(newpw, againnewpw, SPUtils.getInstance().getString(SPConstant.TOKEN), SignUtils.getSign(map))
        }
    }

    override fun changePayPsw(oldpw: String): Observable<BaseResponse<Any>> {
        val map = HashMap<String, String>()
        map["oldpw"] = oldpw
        return mRepositoryManager.obtainRetrofitService(NewSettingService::class.java).changePayPsw(SPUtils.getInstance().getString(SPConstant.TOKEN), SignUtils.getSign(map),oldpw)
    }

    @Inject
    lateinit var mGson: Gson;
    @Inject
    lateinit var mApplication: Application;

    override fun onDestroy() {
        super.onDestroy();
    }
}
