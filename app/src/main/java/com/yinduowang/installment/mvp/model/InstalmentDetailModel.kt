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

import com.yinduowang.installment.mvp.contract.InstalmentDetailContract
import com.yinduowang.installment.mvp.model.entity.BaseResponse
import com.yinduowang.installment.mvp.model.entity.ShopCashInstalmentDetailEntity
import com.yinduowang.installment.mvp.model.service.BillsService
import com.yinduowang.installment.mvp.model.service.PublicHttpService
import io.reactivex.Observable
import java.util.HashMap


/**
 * ================================================
 * Description:分期详情（商城）
 * <p>
 * Created by MVPArmsTemplate on 08/05/2019 09:23
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
class InstalmentDetailModel
@Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), InstalmentDetailContract.Model {
    override fun getList(token: String, sign: String, id: String): Observable<BaseResponse<ShopCashInstalmentDetailEntity>> {
        return mRepositoryManager.obtainRetrofitService(BillsService::class.java).getList(token,sign,id,"2")
    }

    @Inject
    lateinit var mGson: Gson;
    @Inject
    lateinit var mApplication: Application;

    override fun onDestroy() {
        super.onDestroy();
    }

    override fun checkRepay(type: String, loanId: String): Observable<BaseResponse<Any>> {
        val map = HashMap<String, String>()
        map["type"] =  type
        map["loanId"] = loanId
        map["sign"] = SignUtils.getSign(map)
        map["token"] =  SPUtils.getInstance().getString(SPConstant.TOKEN)
        return mRepositoryManager.obtainRetrofitService(PublicHttpService::class.java).checkRepay(map)
    }
}
