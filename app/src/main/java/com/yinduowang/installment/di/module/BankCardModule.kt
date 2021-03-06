package com.yinduowang.installment.di.module

import com.jess.arms.di.scope.ActivityScope
import com.yinduowang.installment.mvp.contract.BankCardContract
import com.yinduowang.installment.mvp.model.BankCardModel
import dagger.Module
import dagger.Provides


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/24/2019 14:09
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
//构建NewBankCardModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
class BankCardModule(private val view: BankCardContract.View) {
    @ActivityScope
    @Provides
    fun provideNewBankCardView(): BankCardContract.View {
        return this.view
    }

    @ActivityScope
    @Provides
    fun provideNewBankCardModel(model: BankCardModel): BankCardContract.Model {
        return model
    }
}
