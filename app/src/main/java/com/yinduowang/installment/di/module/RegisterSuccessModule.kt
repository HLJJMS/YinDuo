package com.yinduowang.installment.di.module

import com.jess.arms.di.scope.ActivityScope
import com.yinduowang.installment.mvp.contract.RegisterSuccessContract
import com.yinduowang.installment.mvp.model.RegisterSuccessModel
import dagger.Module
import dagger.Provides


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/30/2019 08:45
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
//构建RegisterSuccessModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
class RegisterSuccessModule(private val view: RegisterSuccessContract.View) {
    @ActivityScope
    @Provides
    fun provideRegisterSuccessView(): RegisterSuccessContract.View {
        return this.view
    }

    @ActivityScope
    @Provides
    fun provideRegisterSuccessModel(model: RegisterSuccessModel): RegisterSuccessContract.Model {
        return model
    }
}
