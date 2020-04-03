package com.yinduowang.installment.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.yinduowang.installment.di.module.BaofuWithholdModule;
import com.yinduowang.installment.mvp.contract.BaofuWithholdContract;
import com.yinduowang.installment.mvp.ui.activity.BaofuWithholdActivity;

import dagger.BindsInstance;
import dagger.Component;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/05/2019 09:34
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = BaofuWithholdModule.class, dependencies = AppComponent.class)
public interface BaofuWithholdComponent {
    void inject(BaofuWithholdActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        BaofuWithholdComponent.Builder view(BaofuWithholdContract.View view);

        BaofuWithholdComponent.Builder appComponent(AppComponent appComponent);

        BaofuWithholdComponent build();
    }
}