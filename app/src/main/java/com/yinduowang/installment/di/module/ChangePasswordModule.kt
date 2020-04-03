package com.yinduowang.installment.di.module

import com.jess.arms.di.scope.ActivityScope
import com.yinduowang.installment.mvp.contract.ChangePasswordContract
import com.yinduowang.installment.mvp.model.ChangePasswordModel
import dagger.Module
import dagger.Provides


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/23/2019 13:53
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
//构建NewChangePasswordModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
class ChangePasswordModule(private val view: ChangePasswordContract.View) {
    @ActivityScope
    @Provides
    fun provideNewChangePasswordView(): ChangePasswordContract.View {
        return this.view
    }

    @ActivityScope
    @Provides
    fun provideNewChangePasswordModel(model: ChangePasswordModel): ChangePasswordContract.Model {
        return model
    }
}
