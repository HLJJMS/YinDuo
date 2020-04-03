package com.yinduowang.installment.di.component

import com.jess.arms.di.component.AppComponent
import com.jess.arms.di.scope.ActivityScope
import com.yinduowang.installment.di.module.FloatDetailedModule
import com.yinduowang.installment.mvp.ui.activity.FloatDetailedActivity
import dagger.Component


/**
 * @Description:
 * @Author:
 * @Date: 2019-10-23 10:11:28
 * @Version: appVersionName, 2019-10-23
 * @LastEditors:
 * @LastEditTime:
 * @Deprecated: false
 */
@ActivityScope
@Component(modules = arrayOf(FloatDetailedModule::class), dependencies = arrayOf(AppComponent::class))
interface FloatDetailedComponent {
    fun inject(activity: FloatDetailedActivity)
}
