package com.yinduowang.installment.di.module

import com.jess.arms.di.scope.ActivityScope
import com.yinduowang.installment.mvp.contract.ForgetPasswordContract
import com.yinduowang.installment.mvp.model.ForgetPasswordModel
import dagger.Module
import dagger.Provides


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/16/2019 13:52
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
//构建NewForgetPasswordModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
class ForgetPasswordModule(private val view: ForgetPasswordContract.View) {
    @ActivityScope
    @Provides
    fun provideNewForgetPasswordView(): ForgetPasswordContract.View {
        return this.view
    }

    @ActivityScope
    @Provides
    fun provideNewForgetPasswordModel(model: ForgetPasswordModel): ForgetPasswordContract.Model {
        return model
    }
}
