package com.yinduowang.installment.di.module

import com.jess.arms.di.scope.ActivityScope

import dagger.Module
import dagger.Provides

import com.yinduowang.installment.mvp.contract.ShopPayResultContract
import com.yinduowang.installment.mvp.model.ShopPayResultModel


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/09/2019 14:36
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
//构建ShopPayResultModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
class ShopPayResultModule(private val view: ShopPayResultContract.View) {
    @ActivityScope
    @Provides
    fun provideShopPayResultView(): ShopPayResultContract.View {
        return this.view
    }

    @ActivityScope
    @Provides
    fun provideShopPayResultModel(model: ShopPayResultModel): ShopPayResultContract.Model {
        return model
    }
}
