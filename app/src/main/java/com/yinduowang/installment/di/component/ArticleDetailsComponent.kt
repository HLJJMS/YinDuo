package com.yinduowang.installment.di.component

import dagger.Component
import com.jess.arms.di.component.AppComponent

import com.yinduowang.installment.di.module.ArticleDetailsModule

import com.jess.arms.di.scope.ActivityScope
import com.yinduowang.installment.mvp.ui.activity.ArticleDetailsActivity


/**
 * @Description:
 * @Author:
 * @Date: 2019-11-13 20:08:45
 * @Version: appVersionName, 2019-11-13
 * @LastEditors:
 * @LastEditTime:
 * @Deprecated: false
 */
@ActivityScope
@Component(modules = arrayOf(ArticleDetailsModule::class), dependencies = arrayOf(AppComponent::class))
interface ArticleDetailsComponent {
    fun inject(activity: ArticleDetailsActivity)
}
