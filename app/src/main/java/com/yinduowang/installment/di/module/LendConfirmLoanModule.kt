package com.yinduowang.installment.di.module

import com.jess.arms.di.scope.ActivityScope
import com.yinduowang.installment.mvp.contract.LendConfirmLoanContract
import com.yinduowang.installment.mvp.model.LendConfirmLoanModel
import dagger.Module
import dagger.Provides


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/27/2019 17:44
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
//构建NewLendConfirmLoanModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
class LendConfirmLoanModule(private val view: LendConfirmLoanContract.View) {
    @ActivityScope
    @Provides
    fun provideNewLendConfirmLoanView(): LendConfirmLoanContract.View {
        return this.view
    }

    @ActivityScope
    @Provides
    fun provideNewLendConfirmLoanModel(model: LendConfirmLoanModel): LendConfirmLoanContract.Model {
        return model
    }
}
