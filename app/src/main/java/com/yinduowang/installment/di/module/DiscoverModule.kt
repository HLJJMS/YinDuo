package com.yinduowang.installment.di.module

import com.jess.arms.di.scope.FragmentScope

import dagger.Module
import dagger.Provides

import com.yinduowang.installment.mvp.contract.DiscoverContract
import com.yinduowang.installment.mvp.model.DiscoverModel


/**
 * @Description:
 * @Author:
 * @Date: 2019-11-13 11:28:51
 * @Version: appVersionName, 2019-11-13
 * @LastEditors:
 * @LastEditTime:
 * @Deprecated: false
 */
@Module
//构建DiscoverModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
class DiscoverModule(private val view: DiscoverContract.View) {
    @FragmentScope
    @Provides
    fun provideDiscoverView(): DiscoverContract.View {
        return this.view
    }

    @FragmentScope
    @Provides
    fun provideDiscoverModel(model: DiscoverModel): DiscoverContract.Model {
        return model
    }
}
