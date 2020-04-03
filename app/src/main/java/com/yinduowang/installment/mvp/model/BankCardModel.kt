package com.yinduowang.installment.mvp.model

import android.app.Application
import com.blankj.utilcode.util.SPUtils
import com.google.gson.Gson
import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel
import com.yinduowang.installment.app.constant.SPConstant
import com.yinduowang.installment.app.utils.SignUtils
import com.yinduowang.installment.mvp.contract.BankCardContract
import com.yinduowang.installment.mvp.model.entity.BankCardListEntity
import com.yinduowang.installment.mvp.model.entity.BaseResponse
import com.yinduowang.installment.mvp.model.service.BankCardService
import io.reactivex.Observable
import javax.inject.Inject


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/24/2019 14:09
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
class BankCardModel
@Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), BankCardContract.Model {
    @Inject
    lateinit var mGson: Gson;
    @Inject
    lateinit var mApplication: Application;

    override fun onDestroy() {
        super.onDestroy();
    }

    override fun getBankCardList(): Observable<BaseResponse<BankCardListEntity>> {
        val token = SPUtils.getInstance().getString(SPConstant.TOKEN)
        val map = mapOf("bankCardId" to "")
        return mRepositoryManager.obtainRetrofitService(BankCardService::class.java).getBankCardList("", token, SignUtils.getSign(map))
    }
}
