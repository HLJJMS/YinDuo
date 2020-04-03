package com.yinduowang.installment.di.module

import com.jess.arms.di.scope.ActivityScope
import com.yinduowang.installment.mvp.contract.FloatDetailedContract
import com.yinduowang.installment.mvp.model.FloatDetailedModel
import dagger.Module
import dagger.Provides


/**
 * @Description:
 * @Author:
 * @Date: 2019-10-23 10:11:28
 * @Version: appVersionName, 2019-10-23
 * @LastEditors:
 * @LastEditTime:
 * @Deprecated: false
 */
@Module
//构建FloatDetailedModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
class FloatDetailedModule(private val view: FloatDetailedContract.View) {
    @ActivityScope
    @Provides
    fun provideFloatDetailedView(): FloatDetailedContract.View {
        return this.view
    }

    @ActivityScope
    @Provides
    fun provideFloatDetailedModel(model: FloatDetailedModel): FloatDetailedContract.Model {
        return model
    }
}
