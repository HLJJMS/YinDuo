package com.yinduowang.installment.mvp.model

import android.app.Application
import com.google.gson.Gson
import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel

import com.jess.arms.di.scope.ActivityScope
import javax.inject.Inject

import com.yinduowang.installment.mvp.contract.ArticleDetailsContract


/**
 * @Description:
 * @Author:
 * @Date: 2019-11-13 20:08:45
 * @Version: appVersionName, 2019-11-13
 * @LastEditors:
 * @LastEditTime:
 * @Deprecated: false
 */
@ActivityScope
class ArticleDetailsModel
@Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), ArticleDetailsContract.Model {
    @Inject
    lateinit var mGson: Gson;
    @Inject
    lateinit var mApplication: Application;

    override fun onDestroy() {
        super.onDestroy();
    }
}
