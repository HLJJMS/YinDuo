package com.yinduowang.installment.di.component

import com.jess.arms.di.component.AppComponent
import com.jess.arms.di.scope.ActivityScope
import com.yinduowang.installment.di.module.LendConfirmLoanModule
import com.yinduowang.installment.mvp.ui.activity.LendConfirmLoanActivity
import dagger.Component


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
@ActivityScope
@Component(modules = arrayOf(LendConfirmLoanModule::class), dependencies = arrayOf(AppComponent::class))
interface LendConfirmLoanComponent {
    fun inject(activity: LendConfirmLoanActivity)
}
