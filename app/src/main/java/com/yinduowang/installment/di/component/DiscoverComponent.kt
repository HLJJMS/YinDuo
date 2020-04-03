package com.yinduowang.installment.di.component

import dagger.Component
import com.jess.arms.di.component.AppComponent

import com.yinduowang.installment.di.module.DiscoverModule

import com.jess.arms.di.scope.FragmentScope
import com.yinduowang.installment.mvp.ui.fragment.DiscoverFragment


/**
 * @Description:
 * @Author:
 * @Date: 2019-11-13 11:28:51
 * @Version: appVersionName, 2019-11-13
 * @LastEditors:
 * @LastEditTime:
 * @Deprecated: false
 */
@FragmentScope
@Component(modules = arrayOf(DiscoverModule::class), dependencies = arrayOf(AppComponent::class))
interface DiscoverComponent {
    fun inject(fragment: DiscoverFragment)
}
