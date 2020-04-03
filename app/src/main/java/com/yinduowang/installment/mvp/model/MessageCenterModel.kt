package com.yinduowang.installment.mvp.model

import android.app.Application
import com.blankj.utilcode.util.SPUtils
import com.google.gson.Gson
import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel
import com.yinduowang.installment.app.constant.SPConstant
import com.yinduowang.installment.app.utils.SignUtils
import com.yinduowang.installment.mvp.contract.MessageCenterContract
import com.yinduowang.installment.mvp.model.entity.BaseResponse
import com.yinduowang.installment.mvp.model.entity.MessageCenterEntity
import com.yinduowang.installment.mvp.model.service.NewMineService
import io.reactivex.Observable
import javax.inject.Inject


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/23/2019 08:56
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
class MessageCenterModel
@Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), MessageCenterContract.Model {
    @Inject
    lateinit var mGson: Gson;
    @Inject
    lateinit var mApplication: Application;

    override fun getMessage(id: Int, releaseTime: String): Observable<BaseResponse<MessageCenterEntity>> {
        val map = HashMap<String, String>()
        map["page"] = id.toString()
        map["releaseTime"] = releaseTime
        return mRepositoryManager.obtainRetrofitService(NewMineService::class.java).getMessageCenter(id, releaseTime, SPUtils.getInstance().getString(SPConstant.TOKEN), SignUtils.getSign(map))
    }
    override fun onDestroy() {
        super.onDestroy();
    }
}
