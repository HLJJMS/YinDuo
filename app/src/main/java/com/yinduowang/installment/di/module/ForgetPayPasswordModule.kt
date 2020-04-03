package com.yinduowang.installment.di.module

import com.jess.arms.di.scope.ActivityScope

import dagger.Module
import dagger.Provides

import com.yinduowang.installment.mvp.contract.ForgetPayPasswordContract
import com.yinduowang.installment.mvp.model.ForgetPayPasswordModel


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/25/2019 10:54
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
//构建ForgetPayPasswordModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
class ForgetPayPasswordModule(private val view: ForgetPayPasswordContract.View) {
    @ActivityScope
    @Provides
    fun provideForgetPayPasswordView(): ForgetPayPasswordContract.View {
        return this.view
    }

    @ActivityScope
    @Provides
    fun provideForgetPayPasswordModel(model: ForgetPayPasswordModel): ForgetPayPasswordContract.Model {
        return model
    }
}
