package com.yinduowang.installment.di.component

import dagger.Component
import com.jess.arms.di.component.AppComponent

import com.yinduowang.installment.di.module.MyQuotaModule

import com.jess.arms.di.scope.ActivityScope
import com.yinduowang.installment.mvp.ui.activity.MyQuotaActivity


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/30/2019 19:17
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = arrayOf(MyQuotaModule::class), dependencies = arrayOf(AppComponent::class))
interface MyQuotaComponent {
    fun inject(activity: MyQuotaActivity)
}
