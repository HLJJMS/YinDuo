package com.yinduowang.installment.di.component

import dagger.Component
import com.jess.arms.di.component.AppComponent

import com.yinduowang.installment.di.module.PaymentTypeModule

import com.jess.arms.di.scope.ActivityScope
import com.yinduowang.installment.mvp.ui.activity.PaymentTypeActivity


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
@ActivityScope
@Component(modules = arrayOf(PaymentTypeModule::class), dependencies = arrayOf(AppComponent::class))
interface PaymentTypeComponent {
    fun inject(activity: PaymentTypeActivity)
}
