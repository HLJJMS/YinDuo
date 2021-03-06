package com.yinduowang.installment.di.module

import com.jess.arms.di.scope.ActivityScope

import dagger.Module
import dagger.Provides

import com.yinduowang.installment.mvp.contract.ShopAfterSaleContract
import com.yinduowang.installment.mvp.model.ShopAfterSaleModel


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/03/2019 14:30
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
//构建AfterSaleActivityModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
class ShopAfterSaleModule(private val view: ShopAfterSaleContract.View) {
    @ActivityScope
    @Provides
    fun provideAfterSaleActivityView(): ShopAfterSaleContract.View {
        return this.view
    }

    @ActivityScope
    @Provides
    fun provideAfterSaleActivityModel(modelShop: ShopAfterSaleModel): ShopAfterSaleContract.Model {
        return modelShop
    }
}
