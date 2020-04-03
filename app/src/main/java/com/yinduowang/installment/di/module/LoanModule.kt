package com.yinduowang.installment.di.module

import com.jess.arms.di.scope.FragmentScope
import com.yinduowang.installment.mvp.contract.LoanContract
import com.yinduowang.installment.mvp.model.LoanModel
import dagger.Module
import dagger.Provides


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/17/2019 13:27
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
//构建NewLoanModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
class LoanModule(private val view: LoanContract.View) {
    @FragmentScope
    @Provides
    fun provideNewLoanView(): LoanContract.View {
        return this.view
    }

    @FragmentScope
    @Provides
    fun provideNewLoanModel(model: LoanModel): LoanContract.Model {
        return model
    }
}
