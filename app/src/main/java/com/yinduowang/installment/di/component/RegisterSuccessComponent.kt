package com.yinduowang.installment.di.component

import com.jess.arms.di.component.AppComponent
import com.jess.arms.di.scope.ActivityScope
import com.yinduowang.installment.di.module.RegisterSuccessModule
import com.yinduowang.installment.mvp.ui.activity.RegisterSuccessActivity
import dagger.Component


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/30/2019 08:45
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = arrayOf(RegisterSuccessModule::class), dependencies = arrayOf(AppComponent::class))
interface RegisterSuccessComponent {
    fun inject(activity: RegisterSuccessActivity)
}
