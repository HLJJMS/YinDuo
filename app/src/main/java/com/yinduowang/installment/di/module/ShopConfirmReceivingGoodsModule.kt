package com.yinduowang.installment.di.module

import com.jess.arms.di.scope.ActivityScope

import dagger.Module
import dagger.Provides

import com.yinduowang.installment.mvp.contract.ShopConfirmReceivingGoodsContract
import com.yinduowang.installment.mvp.model.ShopConfirmReceivingGoodsModel


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/07/2019 19:49
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
//构建ShopConfirmReceivingGoodsModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
class ShopConfirmReceivingGoodsModule(private val view: ShopConfirmReceivingGoodsContract.View) {
    @ActivityScope
    @Provides
    fun provideShopConfirmReceivingGoodsView(): ShopConfirmReceivingGoodsContract.View {
        return this.view
    }

    @ActivityScope
    @Provides
    fun provideShopConfirmReceivingGoodsModel(model: ShopConfirmReceivingGoodsModel): ShopConfirmReceivingGoodsContract.Model {
        return model
    }
}
