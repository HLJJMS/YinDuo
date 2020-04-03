package com.yinduowang.installment.di.module

import com.jess.arms.di.scope.ActivityScope

import dagger.Module
import dagger.Provides

import com.yinduowang.installment.mvp.contract.MyLogisticsContract
import com.yinduowang.installment.mvp.model.MyLogisticsModel


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/03/2019 10:20
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
//构建MyLogisticsModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
class MyLogisticsModule(private val view: MyLogisticsContract.View) {
    @ActivityScope
    @Provides
    fun provideMyLogisticsView(): MyLogisticsContract.View {
        return this.view
    }

    @ActivityScope
    @Provides
    fun provideMyLogisticsModel(model: MyLogisticsModel): MyLogisticsContract.Model {
        return model
    }
}
