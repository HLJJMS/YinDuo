package com.yinduowang.installment.di.module;

import com.yinduowang.installment.mvp.contract.BaofuWithholdContract;
import com.yinduowang.installment.mvp.model.BaofuWithholdModel;

import dagger.Binds;
import dagger.Module;


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
@Module
public abstract class BaofuWithholdModule {

    @Binds
    abstract BaofuWithholdContract.Model bindBaofuWithholdModel(BaofuWithholdModel model);
}