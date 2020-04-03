package com.yinduowang.installment.di.module

import com.jess.arms.di.scope.ActivityScope
import com.yinduowang.installment.mvp.contract.PersonalDetailContract
import com.yinduowang.installment.mvp.model.PersonalDetailModel
import dagger.Module
import dagger.Provides


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/30/2019 09:00
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
//构建NewPersonalDetailModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
class PersonalDetailModule(private val view: PersonalDetailContract.View) {
    @ActivityScope
    @Provides
    fun provideNewPersonalDetailView(): PersonalDetailContract.View {
        return this.view
    }

    @ActivityScope
    @Provides
    fun provideNewPersonalDetailModel(model: PersonalDetailModel): PersonalDetailContract.Model {
        return model
    }
}
