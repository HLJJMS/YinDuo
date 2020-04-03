package com.yinduowang.installment.di.module

import com.jess.arms.di.scope.ActivityScope
import com.yinduowang.installment.mvp.contract.AuthEmergencyContactContract
import com.yinduowang.installment.mvp.model.AuthEmergencyContactModel
import dagger.Module
import dagger.Provides


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/01/2019 08:38
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
//构建NewAuthEmergencyContactModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
class AuthEmergencyContactModule(private val view: AuthEmergencyContactContract.View) {
    @ActivityScope
    @Provides
    fun provideNewAuthEmergencyContactView(): AuthEmergencyContactContract.View {
        return this.view
    }

    @ActivityScope
    @Provides
    fun provideNewAuthEmergencyContactModel(model: AuthEmergencyContactModel): AuthEmergencyContactContract.Model {
        return model
    }
}
