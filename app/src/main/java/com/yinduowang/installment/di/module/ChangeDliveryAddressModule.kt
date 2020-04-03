package com.yinduowang.installment.di.module

import com.jess.arms.di.scope.ActivityScope

import dagger.Module
import dagger.Provides

import com.yinduowang.installment.mvp.contract.ChangeDliveryAddressContract
import com.yinduowang.installment.mvp.model.ChangeDliveryAddressModel


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/29/2019 14:00
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
//构建ChangeDliveryAddressModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
class ChangeDliveryAddressModule(private val view: ChangeDliveryAddressContract.View) {
    @ActivityScope
    @Provides
    fun provideChangeDliveryAddressView(): ChangeDliveryAddressContract.View {
        return this.view
    }

    @ActivityScope
    @Provides
    fun provideChangeDliveryAddressModel(model: ChangeDliveryAddressModel): ChangeDliveryAddressContract.Model {
        return model
    }
}
