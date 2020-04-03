package com.yinduowang.installment.di.module

import com.jess.arms.di.scope.ActivityScope
import com.yinduowang.installment.mvp.contract.QuotaContract
import com.yinduowang.installment.mvp.model.MyQuotaModel
import dagger.Module
import dagger.Provides


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/30/2019 19:17
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
//构建MyQuotaModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
class MyQuotaModule(private val view: QuotaContract.View) {
    @ActivityScope
    @Provides
    fun provideMyQuotaView(): QuotaContract.View {
        return this.view
    }

    @ActivityScope
    @Provides
    fun provideMyQuotaModel(model: MyQuotaModel): QuotaContract.Model {
        return model
    }
}
