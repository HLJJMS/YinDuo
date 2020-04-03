package com.yinduowang.installment.di.module

import com.jess.arms.di.scope.ActivityScope
import com.yinduowang.installment.mvp.contract.SettingContract
import com.yinduowang.installment.mvp.model.SettingModel
import dagger.Module
import dagger.Provides


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/23/2019 10:50
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
//构建NewSettingModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
class SettingModule(private val view: SettingContract.View) {
    @ActivityScope
    @Provides
    fun provideNewSettingView(): SettingContract.View {
        return this.view
    }

    @ActivityScope
    @Provides
    fun provideNewSettingModel(model: SettingModel): SettingContract.Model {
        return model
    }
}
