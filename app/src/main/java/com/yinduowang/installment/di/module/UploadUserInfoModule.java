package com.yinduowang.installment.di.module;


import com.yinduowang.installment.mvp.contract.UploadUserInfoContract;
import com.yinduowang.installment.mvp.model.UploadUserInfoModel;

import dagger.Binds;
import dagger.Module;


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
@Module
public abstract class UploadUserInfoModule {

    @Binds
    abstract UploadUserInfoContract.Model bindUploadUserInfoModel(UploadUserInfoModel model);
}