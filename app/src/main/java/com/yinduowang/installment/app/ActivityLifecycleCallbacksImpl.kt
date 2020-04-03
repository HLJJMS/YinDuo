/*
 * Copyright 2017 JessYan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.yinduowang.installment.app

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import com.alibaba.security.rp.activity.RPH5Activity
import com.blankj.utilcode.util.SPUtils
import com.bqs.risk.df.android.BqsDF
import com.bqs.risk.df.android.BqsParams
import com.bqs.risk.df.android.OnBqsDFListener
import com.meiqia.meiqiasdk.activity.MQConversationActivity
import com.qmuiteam.qmui.util.QMUIStatusBarHelper
import com.tencent.bugly.crashreport.CrashReport
import com.umeng.message.PushAgent
//import com.yinduowang.installment.app.BaseConfig.BAI_QI_SHI
//import com.yinduowang.installment.app.BaseConfig.BAI_QI_SHI_IS_TEST
import com.yinduowang.installment.app.constant.SPConstant
import com.yinduowang.installment.app.utils.LoadingUtils
import com.yinduowang.installment.mvp.ui.service.UploadService
import timber.log.Timber

/**
 * ================================================
 * 展示 [Application.ActivityLifecycleCallbacks] 的用法
 *
 *
 * Created by JessYan on 04/09/2017 17:14
 * [Contact me](mailto:jess.yan.effort@gmail.com)
 * [Follow me](https://github.com/JessYanCoding)
 * ================================================
 */
class ActivityLifecycleCallbacksImpl : Application.ActivityLifecycleCallbacks {

    override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
        Timber.i("$activity - onActivityCreated")
        activity?.let {
            QMUIStatusBarHelper.translucent(it)
            QMUIStatusBarHelper.setStatusBarLightMode(it)
            PushAgent.getInstance(it).onAppStart()
            LoadingUtils.init(it)
        }
    }

    override fun onActivityStarted(activity: Activity?) {
        Timber.i("$activity - onActivityStarted")
    }

    override fun onActivityResumed(activity: Activity?) {
        Timber.i("$activity - onActivityResumed")

        try {
            activity?.let {
//                if (SPUtils.getInstance().getString(SPConstant.TOKEN).isNotEmpty() && BqsDF.getInstance().canInitBqsSDK()) {
//                    initBqsDFSDK(activity)
//                }
                if (activity is RPH5Activity || activity is MQConversationActivity) {
                    setFitsSystemWindows(activity, true)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            CrashReport.postCatchedException(e)
        }
    }

    /**
     * 设置页面最外层布局 FitsSystemWindows 属性
     *
     * @param activity
     * @param value
     */
    fun setFitsSystemWindows(activity: Activity, value: Boolean) {
        val contentFrameLayout = activity.findViewById<View>(android.R.id.content) as ViewGroup
        val parentView = contentFrameLayout.getChildAt(0)
        parentView.fitsSystemWindows = value
    }

//    fun initBqsDFSDK(activity: Activity) {
//        //1、添加设备信息采集回调
//        BqsDF.getInstance().setOnBqsDFListener(object : OnBqsDFListener {
//            override fun onSuccess(tokenKey: String) {
//                //回调的tokenkey和通过BqsDF.getTokenKey()拿到的值都是一样的
//                Timber.i("白骑士SDK采集设备信息成功 tokenkey=$tokenKey")
//                UploadService.uploadUserInfo(activity, UploadService.UPLOAD_TYPE_BQSDF, tokenKey)
//            }
//
//            override fun onFailure(resultCode: String, resultDesc: String) {
//                Timber.i("白骑士SDK采集设备信息失败 resultCode=$resultCode resultDesc=$resultDesc")
//                UploadService.uploadUserInfo(activity, UploadService.UPLOAD_TYPE_ERROR, "白骑士SDK采集设备信息失败 resultCode=$resultCode resultDesc=$resultDesc")
//            }
//        })
//
//        //2、配置初始化参数
//        val params = BqsParams()
//        //商户编号
//        params.partnerId = BAI_QI_SHI
//        //false是生产环境 true是测试环境
//        params.isTestingEnv = BAI_QI_SHI_IS_TEST
//        //3、执行初始化
//        BqsDF.getInstance().initialize(activity, params)
//    }

    override fun onActivityPaused(activity: Activity?) {
        Timber.i("$activity - onActivityPaused")
    }

    override fun onActivityStopped(activity: Activity?) {
        Timber.i("$activity - onActivityStopped")
    }

    override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
        Timber.i("$activity - onActivitySaveInstanceState")
    }

    override fun onActivityDestroyed(activity: Activity?) {
        Timber.i("$activity - onActivityDestroyed")
        activity?.let {
            LoadingUtils.dismissNow(activity)
        }
    }
}
