package com.yinduowang.installment.di.module

import com.jess.arms.di.scope.ActivityScope

import dagger.Module
import dagger.Provides

import com.yinduowang.installment.mvp.contract.BankCertificationContract
import com.yinduowang.installment.mvp.model.BankCertificationModel


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/03/2019 14:55
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
//构建BankCertificationModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
class BankCertificationModule(private val view: BankCertificationContract.View) {
    @ActivityScope
    @Provides
    fun provideBankCertificationView(): BankCertificationContract.View {
        return this.view
    }

    @ActivityScope
    @Provides
    fun provideBankCertificationModel(model: BankCertificationModel): BankCertificationContract.Model {
        return model
    }
}
