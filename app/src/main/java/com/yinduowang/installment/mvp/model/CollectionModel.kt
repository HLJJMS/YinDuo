package com.yinduowang.installment.mvp.model

import android.app.Application
import com.blankj.utilcode.util.SPUtils
import com.google.gson.Gson
import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel
import com.yinduowang.installment.app.constant.SPConstant
import com.yinduowang.installment.app.utils.SignUtils
import com.yinduowang.installment.mvp.contract.CollectionContract
import com.yinduowang.installment.mvp.model.entity.BaseResponse
import com.yinduowang.installment.mvp.model.entity.CollectionEntity
import com.yinduowang.installment.mvp.model.service.NewSettingService
import io.reactivex.Observable
import java.util.*
import javax.inject.Inject


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/23/2019 13:52
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
class CollectionModel
@Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), CollectionContract.Model {
    override fun getCollectionType(): Observable<BaseResponse<CollectionEntity>> {
        return mRepositoryManager.obtainRetrofitService(NewSettingService::class.java).getCollectionType()
    }

    override fun collectionComplaint(typeId: String, content: String): Observable<BaseResponse<Any>> {
        val map = HashMap<String, String>()
        map["typeId"] = typeId
        map["content"] = content
        return  mRepositoryManager.obtainRetrofitService(NewSettingService::class.java).collectionComplaint(SPUtils.getInstance().getString(SPConstant.TOKEN), SignUtils.getSign(map),typeId, content)
    }

    @Inject
    lateinit var mGson: Gson;
    @Inject
    lateinit var mApplication: Application;

    override fun onDestroy() {
        super.onDestroy();
    }
}
