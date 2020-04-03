package com.yinduowang.installment.di.module

import com.jess.arms.di.scope.ActivityScope

import dagger.Module
import dagger.Provides

import com.yinduowang.installment.mvp.contract.ArticleDetailsContract
import com.yinduowang.installment.mvp.model.ArticleDetailsModel


/**
 * @Description:
 * @Author:
 * @Date: 2019-11-13 20:08:45
 * @Version: appVersionName, 2019-11-13
 * @LastEditors:
 * @LastEditTime:
 * @Deprecated: false
 */
@Module
//构建ArticleDetailsModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
class ArticleDetailsModule(private val view: ArticleDetailsContract.View) {
    @ActivityScope
    @Provides
    fun provideArticleDetailsView(): ArticleDetailsContract.View {
        return this.view
    }

    @ActivityScope
    @Provides
    fun provideArticleDetailsModel(model: ArticleDetailsModel): ArticleDetailsContract.Model {
        return model
    }
}
