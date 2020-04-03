package com.yinduowang.installment.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.yinduowang.installment.mvp.ui.service.UploadService;
import com.yinduowang.installment.di.module.UploadUserInfoModule;
import com.yinduowang.installment.mvp.contract.UploadUserInfoContract;

import dagger.BindsInstance;
import dagger.Component;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/14/2019 00:33
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = UploadUserInfoModule.class, dependencies = AppComponent.class)
public interface UploadUserInfoComponent {

    void inject(UploadService service);

    @Component.Builder
    interface Builder {
        @BindsInstance
        UploadUserInfoComponent.Builder view(UploadUserInfoContract.View view);

        UploadUserInfoComponent.Builder appComponent(AppComponent appComponent);

        UploadUserInfoComponent build();
    }
}