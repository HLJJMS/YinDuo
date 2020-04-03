package com.yinduowang.installment.mvp.model

import android.app.Application
import com.blankj.utilcode.util.SPUtils
import com.google.gson.Gson
import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel

import com.jess.arms.di.scope.ActivityScope
import com.yinduowang.installment.app.constant.SPConstant
import com.yinduowang.installment.app.utils.MD5Util
import com.yinduowang.installment.app.utils.SignUtils
import javax.inject.Inject

import com.yinduowang.installment.mvp.contract.AddBankCardContract
import com.yinduowang.installment.mvp.model.entity.BaseResponse
import com.yinduowang.installment.mvp.model.entity.ConfirmBindEntity
import com.yinduowang.installment.mvp.model.entity.PreBindEntity
import com.yinduowang.installment.mvp.model.entity.VerificationUserInfoEntity
import com.yinduowang.installment.mvp.model.service.BankCardService
import io.reactivex.Observable


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/24/2019 14:11
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
class AddBankCardModel
@Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), AddBankCardContract.Model {
    @Inject
    lateinit var mGson: Gson;
    @Inject
    lateinit var mApplication: Application;

    override fun onDestroy() {
        super.onDestroy();
    }

    override fun getUserInfo(): Observable<BaseResponse<VerificationUserInfoEntity>> {
        val token = SPUtils.getInstance().getString(SPConstant.TOKEN)
        return mRepositoryManager.obtainRetrofitService(BankCardService::class.java).getUserInfo(token, MD5Util.getMD5String(token))
    }

    override fun preBind(bankCardNo: String, mobileNo: String): Observable<BaseResponse<PreBindEntity>> {
        val token = SPUtils.getInstance().getString(SPConstant.TOKEN)
        val map = mapOf("bankCardNo" to bankCardNo, "mobileNo" to mobileNo)
        return mRepositoryManager.obtainRetrofitService(BankCardService::class.java).preBind(token, SignUtils.getSign(map), bankCardNo, mobileNo)
    }

    override fun confirmBind(uniqueCode: String, authCode: String): Observable<BaseResponse<ConfirmBindEntity>> {
        val token = SPUtils.getInstance().getString(SPConstant.TOKEN)
        val map = mapOf("uniqueCode" to uniqueCode, "authCode" to authCode)
        return mRepositoryManager.obtainRetrofitService(BankCardService::class.java).confirmBind(token, SignUtils.getSign(map), uniqueCode, authCode)
    }
}
