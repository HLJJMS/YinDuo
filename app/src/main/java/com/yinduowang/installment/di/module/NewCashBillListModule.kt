package com.yinduowang.installment.di.module

import com.jess.arms.di.scope.ActivityScope
import com.yinduowang.installment.mvp.contract.NewCashBillListContract
import com.yinduowang.installment.mvp.model.NewCashBillListModel
import dagger.Module
import dagger.Provides


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/03/2019 15:21
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
//构建BillListModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
class NewCashBillListModule(private val view: NewCashBillListContract.View) {
    @ActivityScope
    @Provides
    fun provideBillListView(): NewCashBillListContract.View {
        return this.view
    }

    @ActivityScope
    @Provides
    fun provideBillListModel(model: NewCashBillListModel): NewCashBillListContract.Model {
        return model
    }
}
