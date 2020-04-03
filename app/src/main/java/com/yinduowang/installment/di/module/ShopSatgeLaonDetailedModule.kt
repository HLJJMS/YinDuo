package com.yinduowang.installment.di.module

import com.jess.arms.di.scope.ActivityScope

import dagger.Module
import dagger.Provides

import com.yinduowang.installment.mvp.contract.ShopSatgeLaonDetailedContract
import com.yinduowang.installment.mvp.model.ShopSatgeLaonDetailedModel


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/31/2019 14:25
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
//构建ShopSatgeLaonDetailedModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
class ShopSatgeLaonDetailedModule(private val view: ShopSatgeLaonDetailedContract.View) {
    @ActivityScope
    @Provides
    fun provideShopSatgeLaonDetailedView(): ShopSatgeLaonDetailedContract.View {
        return this.view
    }

    @ActivityScope
    @Provides
    fun provideShopSatgeLaonDetailedModel(model: ShopSatgeLaonDetailedModel): ShopSatgeLaonDetailedContract.Model {
        return model
    }
}
