package com.yinduowang.installment.di.module

import com.jess.arms.di.scope.ActivityScope

import dagger.Module
import dagger.Provides

import com.yinduowang.installment.mvp.contract.PayBackMoneyContract
import com.yinduowang.installment.mvp.model.PayBackMoneyModel


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/05/2019 09:46
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
//构建PayBackMoneyModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
class PayBackMoneyModule(private val view: PayBackMoneyContract.View) {
    @ActivityScope
    @Provides
    fun providePayBackMoneyView(): PayBackMoneyContract.View {
        return this.view
    }

    @ActivityScope
    @Provides
    fun providePayBackMoneyModel(model: PayBackMoneyModel): PayBackMoneyContract.Model {
        return model
    }
}
