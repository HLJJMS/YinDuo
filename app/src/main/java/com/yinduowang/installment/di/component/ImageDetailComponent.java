package com.yinduowang.installment.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.yinduowang.installment.di.module.ImageDetailModule;
import com.yinduowang.installment.mvp.contract.ImageDetailContract;
import com.yinduowang.installment.mvp.ui.activity.ImageDetailActivity;

import dagger.BindsInstance;
import dagger.Component;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/29/2019 09:17
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = ImageDetailModule.class, dependencies = AppComponent.class)
public interface ImageDetailComponent {
    void inject(ImageDetailActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        ImageDetailComponent.Builder view(ImageDetailContract.View view);

        ImageDetailComponent.Builder appComponent(AppComponent appComponent);

        ImageDetailComponent build();
    }
}