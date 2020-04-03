package com.yinduowang.installment.di.module

import com.jess.arms.di.scope.ActivityScope
import com.yinduowang.installment.mvp.contract.AuthorizationResultsContract
import com.yinduowang.installment.mvp.model.AuthorizationResultsModel
import dagger.Module
import dagger.Provides


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/12/2019 20:23
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
//构建NewAuthorizationResultsModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
class AuthorizationResultsModule(private val view: AuthorizationResultsContract.View) {
    @ActivityScope
    @Provides
    fun provideNewAuthorizationResultsView(): AuthorizationResultsContract.View {
        return this.view
    }

    @ActivityScope
    @Provides
    fun provideNewAuthorizationResultsModel(model: AuthorizationResultsModel): AuthorizationResultsContract.Model {
        return model
    }
}
