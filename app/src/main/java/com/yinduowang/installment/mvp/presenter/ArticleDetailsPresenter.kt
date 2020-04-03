package com.yinduowang.installment.mvp.presenter

import android.app.Application

import com.jess.arms.integration.AppManager
import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.mvp.BasePresenter
import com.jess.arms.http.imageloader.ImageLoader
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import javax.inject.Inject

import com.yinduowang.installment.mvp.contract.ArticleDetailsContract


/**
 * @Description:文章详情页面
 * @Author:
 * @Date: 2019-11-13 20:08:45
 * @Version: 1.0, 2019-11-13
 * @LastEditors:
 * @LastEditTime:
 * @Deprecated: false
 */
@ActivityScope
class ArticleDetailsPresenter
@Inject
constructor(model: ArticleDetailsContract.Model, rootView: ArticleDetailsContract.View) :
        BasePresenter<ArticleDetailsContract.Model, ArticleDetailsContract.View>(model, rootView) {
    @Inject
    lateinit var mErrorHandler: RxErrorHandler
    @Inject
    lateinit var mApplication: Application
    @Inject
    lateinit var mImageLoader: ImageLoader
    @Inject
    lateinit var mAppManager: AppManager


    override fun onDestroy() {
        super.onDestroy();
    }
}
