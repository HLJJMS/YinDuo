package com.yinduowang.installment.di.module

import com.jess.arms.di.scope.ActivityScope
import com.yinduowang.installment.mvp.contract.ApplyLoanSuccessContract
import com.yinduowang.installment.mvp.model.ApplyLoanSuccessModel
import dagger.Module
import dagger.Provides


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/30/2019 11:42
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
//构建NewApplyLoanSuccessModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
class ApplyLoanSuccessModule(private val view: ApplyLoanSuccessContract.View) {
    @ActivityScope
    @Provides
    fun provideNewApplyLoanSuccessView(): ApplyLoanSuccessContract.View {
        return this.view
    }

    @ActivityScope
    @Provides
    fun provideNewApplyLoanSuccessModel(model: ApplyLoanSuccessModel): ApplyLoanSuccessContract.Model {
        return model
    }
}
