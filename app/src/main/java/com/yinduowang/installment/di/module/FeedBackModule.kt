package com.yinduowang.installment.di.module

import com.jess.arms.di.scope.ActivityScope
import com.yinduowang.installment.mvp.contract.FeedBackContract
import com.yinduowang.installment.mvp.model.FeedBackModel
import dagger.Module
import dagger.Provides


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/23/2019 13:51
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
//构建NewFeedBackModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
class FeedBackModule(private val view: FeedBackContract.View) {
    @ActivityScope
    @Provides
    fun provideNewFeedBackView(): FeedBackContract.View {
        return this.view
    }

    @ActivityScope
    @Provides
    fun provideNewFeedBackModel(model: FeedBackModel): FeedBackContract.Model {
        return model
    }
}
