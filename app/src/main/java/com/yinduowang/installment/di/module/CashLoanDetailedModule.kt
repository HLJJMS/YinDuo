package com.yinduowang.installment.di.module

import com.jess.arms.di.scope.ActivityScope

import dagger.Module
import dagger.Provides

import com.yinduowang.installment.mvp.contract.CashLoanDetailedContract
import com.yinduowang.installment.mvp.model.CashLoanDetailedModel


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/31/2019 14:26
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
//构建CashLoanDetailedModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
class CashLoanDetailedModule(private val view: CashLoanDetailedContract.View) {
    @ActivityScope
    @Provides
    fun provideCashLoanDetailedView(): CashLoanDetailedContract.View {
        return this.view
    }

    @ActivityScope
    @Provides
    fun provideCashLoanDetailedModel(model: CashLoanDetailedModel): CashLoanDetailedContract.Model {
        return model
    }
}
