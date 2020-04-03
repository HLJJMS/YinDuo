package com.yinduowang.installment.di.module

import com.jess.arms.di.scope.ActivityScope

import dagger.Module
import dagger.Provides

import com.yinduowang.installment.mvp.contract.CashBillsContract
import com.yinduowang.installment.mvp.model.CashBillsModel


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/03/2019 15:47
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
//构建CashBillsModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
class CashBillsModule(private val view: CashBillsContract.View) {
    @ActivityScope
    @Provides
    fun provideCashBillsView(): CashBillsContract.View {
        return this.view
    }

    @ActivityScope
    @Provides
    fun provideCashBillsModel(model: CashBillsModel): CashBillsContract.Model {
        return model
    }
}
