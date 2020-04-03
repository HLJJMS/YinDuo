package com.yinduowang.installment.di.module

import com.jess.arms.di.scope.FragmentScope

import dagger.Module
import dagger.Provides

import com.yinduowang.installment.mvp.contract.ShoppingMallContract
import com.yinduowang.installment.mvp.model.ShoppingMallModel


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/25/2019 13:10
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
//构建ShoppingMallModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
class ShoppingMallModule(private val view: ShoppingMallContract.View) {
    @FragmentScope
    @Provides
    fun provideShoppingMallView(): ShoppingMallContract.View {
        return this.view
    }

    @FragmentScope
    @Provides
    fun provideShoppingMallModel(model: ShoppingMallModel): ShoppingMallContract.Model {
        return model
    }
}
