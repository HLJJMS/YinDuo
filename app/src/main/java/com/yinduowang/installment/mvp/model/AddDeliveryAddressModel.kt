package com.yinduowang.installment.mvp.model

import android.app.Application
import com.blankj.utilcode.util.SPUtils
import com.google.gson.Gson
import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel
import com.yinduowang.installment.app.constant.Constant
import com.yinduowang.installment.app.constant.SPConstant
import com.yinduowang.installment.app.utils.SignUtils
import com.yinduowang.installment.mvp.contract.AddDeliveryAddressContract
import com.yinduowang.installment.mvp.model.entity.BasePhpResponse
import com.yinduowang.installment.mvp.model.service.NewSettingService
import io.reactivex.Observable
import javax.inject.Inject


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/26/2019 14:06
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
class AddDeliveryAddressModel
@Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), AddDeliveryAddressContract.Model {
    @Inject
    lateinit var mGson: Gson;
    @Inject
    lateinit var mApplication: Application;

    override fun changeOraddDeliveryAddress(id: String, province: String, city: String, area: String, address: String, mobile: String, name: String, is_default: String): Observable<BasePhpResponse<Any>> {
        val map = mapOf(
                "id" to id,
                "province" to province,
                "city" to city,
                "area" to area,
                "address" to address,
                "mobile" to mobile,
                "name" to name,
                "is_default" to is_default)

        return mRepositoryManager.obtainRetrofitService(NewSettingService::class.java).changeOraddDeliveryAddress(
                id, province, city, area, address, mobile, name, is_default
                ,SPUtils.getInstance().getString(SPConstant.TOKEN), SignUtils.getSign(map))
    }

    override fun onDestroy() {
        super.onDestroy();
    }
}
