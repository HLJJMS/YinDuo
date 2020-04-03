package com.yinduowang.installment.di.module

import com.jess.arms.di.scope.ActivityScope

import dagger.Module
import dagger.Provides

import com.yinduowang.installment.mvp.contract.PaymentTypeContract
import com.yinduowang.installment.mvp.model.PaymentTypeModel


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/05/2019 17:23
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
//构建PaymentTypeModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
class PaymentTypeModule(private val view: PaymentTypeContract.View) {
    @ActivityScope
    @Provides
    fun providePaymentTypeView(): PaymentTypeContract.View {
        return this.view
    }

    @ActivityScope
    @Provides
    fun providePaymentTypeModel(model: PaymentTypeModel): PaymentTypeContract.Model {
        return model
    }
}
