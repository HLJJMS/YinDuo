package com.yinduowang.installment.di.module

import com.jess.arms.di.scope.FragmentScope
import com.yinduowang.installment.mvp.contract.MineContract
import com.yinduowang.installment.mvp.model.MineModel
import dagger.Module
import dagger.Provides


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/19/2019 15:51
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
//构建NewMineModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
class MineModule(private val view: MineContract.View) {
    @FragmentScope
    @Provides
    fun provideNewMineView(): MineContract.View {
        return this.view
    }

    @FragmentScope
    @Provides
    fun provideNewMineModel(model: MineModel): MineContract.Model {
        return model
    }
}
