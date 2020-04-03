package com.yinduowang.installment.di.component

import dagger.Component
import com.jess.arms.di.component.AppComponent

import com.yinduowang.installment.di.module.InstalmentDetailModule

import com.jess.arms.di.scope.ActivityScope
import com.yinduowang.installment.di.module.NewCashInstalmentDetailModule
import com.yinduowang.installment.mvp.ui.activity.InstalmentDetailActivity
import com.yinduowang.installment.mvp.ui.activity.NewCashInstalmentDetailActivity


/**
 * ================================================
 * Description:
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
@Component(modules = arrayOf(NewCashInstalmentDetailModule::class), dependencies = arrayOf(AppComponent::class))
interface NewCashInstalmentDetailComponent {
    fun inject(activity: NewCashInstalmentDetailActivity)
}
