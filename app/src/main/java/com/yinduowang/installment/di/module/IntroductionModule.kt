package com.yinduowang.installment.di.module

import com.jess.arms.di.scope.ActivityScope
import com.yinduowang.installment.mvp.contract.IntroductionContract
import com.yinduowang.installment.mvp.model.IntroductionModel
import dagger.Module
import dagger.Provides


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/20/2019 16:44
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
//构建NewIntroductionActivityModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
class IntroductionModule(private val view: IntroductionContract.View) {
    @ActivityScope
    @Provides
    fun provideNewIntroductionView(): IntroductionContract.View {
        return this.view
    }

    @ActivityScope
    @Provides
    fun provideNewIntroductionModel(model: IntroductionModel): IntroductionContract.Model {
        return model
    }
}
