package com.yinduowang.installment.di.module

import com.jess.arms.di.scope.ActivityScope

import dagger.Module
import dagger.Provides

import com.yinduowang.installment.mvp.contract.MessageCenterContract
import com.yinduowang.installment.mvp.model.MessageCenterModel


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/23/2019 08:56
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
//构建MessageCenterModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
class MessageCenterModule(private val view: MessageCenterContract.View) {
    @ActivityScope
    @Provides
    fun provideMessageCenterView(): MessageCenterContract.View {
        return this.view
    }

    @ActivityScope
    @Provides
    fun provideMessageCenterModel(model: MessageCenterModel): MessageCenterContract.Model {
        return model
    }
}
