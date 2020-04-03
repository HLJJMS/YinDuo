package com.yinduowang.installment.di.module

import com.jess.arms.di.scope.ActivityScope
import com.yinduowang.installment.mvp.contract.CommWebViewContract
import com.yinduowang.installment.mvp.model.CommWebViewModel
import dagger.Module
import dagger.Provides


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/24/2019 13:20
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
//构建NewCommWebViewModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
class CommWebViewModule(private val view: CommWebViewContract.View) {
    @ActivityScope
    @Provides
    fun provideNewCommWebViewView(): CommWebViewContract.View {
        return this.view
    }

    @ActivityScope
    @Provides
    fun provideNewCommWebViewModel(model: CommWebViewModel): CommWebViewContract.Model {
        return model
    }
}
