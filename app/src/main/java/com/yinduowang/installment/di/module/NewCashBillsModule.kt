package com.yinduowang.installment.di.module

import com.jess.arms.di.scope.ActivityScope
import com.yinduowang.installment.mvp.contract.NewCashBillsContract
import com.yinduowang.installment.mvp.model.NewCashBillsModel
import dagger.Module
import dagger.Provides


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/03/2019 09:19
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
//构建ShopAllBillsModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
class NewCashBillsModule(private val view: NewCashBillsContract.View) {
    @ActivityScope
    @Provides
    fun provideNewCashBillsView(): NewCashBillsContract.View {
        return this.view
    }

    @ActivityScope
    @Provides
    fun provideNewCashBillsModel(model: NewCashBillsModel): NewCashBillsContract.Model {
        return model
    }
}
