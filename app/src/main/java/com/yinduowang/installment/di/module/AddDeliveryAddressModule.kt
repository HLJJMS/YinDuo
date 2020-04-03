package com.yinduowang.installment.di.module

import com.jess.arms.di.scope.ActivityScope

import dagger.Module
import dagger.Provides

import com.yinduowang.installment.mvp.contract.AddDeliveryAddressContract
import com.yinduowang.installment.mvp.model.AddDeliveryAddressModel


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
@Module
//构建AddDeliveryAddressModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
class AddDeliveryAddressModule(private val view: AddDeliveryAddressContract.View) {
    @ActivityScope
    @Provides
    fun provideAddDeliveryAddressView(): AddDeliveryAddressContract.View {
        return this.view
    }

    @ActivityScope
    @Provides
    fun provideAddDeliveryAddressModel(model: AddDeliveryAddressModel): AddDeliveryAddressContract.Model {
        return model
    }
}
