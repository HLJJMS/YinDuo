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

import android.app.Application
import android.content.Context
import android.content.Intent
import androidx.multidex.MultiDex
import butterknife.ButterKnife
import com.alibaba.security.rp.RPSDK
import com.amap.api.location.AMapLocationClient
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.Utils
import com.jess.arms.base.delegate.AppLifecycles
import com.meiqia.core.MQManager
import com.meiqia.core.callback.OnInitCallback
import com.meiqia.meiqiasdk.imageloader.MQImage
import com.meiqia.meiqiasdk.util.MQConfig
import com.tencent.bugly.Bugly
import com.tencent.bugly.Bugly.applicationContext
import com.tencent.bugly.beta.Beta
import com.tencent.bugly.beta.interfaces.BetaPatchListener
import com.tencent.smtt.sdk.QbSdk
import com.tencent.smtt.sdk.QbSdk.PreInitCallback
import com.umeng.analytics.AnalyticsConfig
import com.umeng.analytics.MobclickAgent
import com.umeng.commonsdk.UMConfigure
import com.umeng.message.IUmengRegisterCallback
import com.umeng.message.PushAgent
import com.umeng.message.UmengMessageHandler
import com.umeng.message.UmengNotificationClickHandler
import com.umeng.message.entity.UMessage
import com.yinduowang.installment.BuildConfig
import com.yinduowang.installment.app.constant.SPConstant
import com.yinduowang.installment.app.utils.MQGlideImageLoader4
import com.yinduowang.installment.app.utils.UMCountUtil
import com.yinduowang.installment.mvp.ui.activity.CommWebViewActivity
import com.yinduowang.installment.mvp.ui.activity.MainActivity
import timber.log.Timber

/**
 * ================================================
 * 展示 [AppLifecycles] 的用法
 *
 *
 * Created by JessYan on 04/09/2017 17:12
 * [Contact me](mailto:jess.yan.effort@gmail.com)
 * [Follow me](https://github.com/JessYanCoding)
 * ================================================
 */
class AppLifecyclesImpl : AppLifecycles {


    /**
     * 注册友盟相关
     *
     * @param application
     * @return
     */
    private var url: String? = null

    override fun attachBaseContext(base: Context) {
        MultiDex.install(base)  //这里比 onCreate 先执行,常用于 MultiDex 初始化,插件化框架的初始化
        // 安装tinker
        Beta.installTinker()
    }

    override fun onCreate(application: Application) {
        initBugly(application)
        if (BuildConfig.LOG_DEBUG) {
            // Timber初始化
            Timber.plant(Timber.DebugTree())
            // view注入开启debug
            ButterKnife.setDebug(true)
        }
        //注册UMENG
        initUM(application)
        //Utils初始化
        Utils.init(application)
        //高德定位初始化
        AMapLocationClient.setApiKey(BaseConfig.AMAP_API_KEY)
        //美洽初始化
        initMQ()
        // 阿里云 实人认证 初始化
        RPSDK.initialize(application)
        // 腾讯X5游览器
        initTencenX5(application)
    }

    private fun initMQ() {
        MQConfig.init(applicationContext, BaseConfig.MQ_API_KEY, object : OnInitCallback {
            override fun onSuccess(p0: String?) {
                MQManager.setDebugMode(true)
            }

            override fun onFailure(p0: Int, p1: String?) {

            }
        })
        MQManager.setDebugMode(true)
        MQImage.setImageLoader(MQGlideImageLoader4())
    }

    /**
     * 腾讯X5内核游览器
     */
    private fun initTencenX5(application: Application) {
        //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
        val cb: PreInitCallback = object : PreInitCallback {
            override fun onViewInitFinished(arg0: Boolean) {
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                Timber.i("腾讯X5游览器 onViewInitFinished is %s", arg0)
            }

            override fun onCoreInitFinished() {}
        }
        //x5内核初始化接口
        QbSdk.initX5Environment(application, cb)
    }

    private fun initUM(application: Application) {
        /*设置channel*/
        val channel = AnalyticsConfig.getChannel(application)
        Timber.i("友盟渠道号:$channel")
        //        UMCountUtil.init(getApplicationContext());
        /**
         * 初始化common库
         * 参数1:上下文，不能为空
         * 参数2:【友盟+】 AppKey
         * 参数3:【友盟+】 Channel
         * 参数4:设备类型，UMConfigure.DEVICE_TYPE_PHONE为手机、UMConfigure.DEVICE_TYPE_BOX为盒子，默认为手机
         * 参数5:Push推送业务的secret
         */
        UMConfigure.init(application, BaseConfig.UM_API_KEY, channel, UMConfigure.DEVICE_TYPE_PHONE, BaseConfig.UM_SECRET)
        // 选用LEGACY_MANUAL页面采集模式  下边接口删除
        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.LEGACY_MANUAL)
        UMConfigure.setLogEnabled(true)
        //获取消息推送代理示例
        val mPushAgent = PushAgent.getInstance(application)
        //注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(object : IUmengRegisterCallback {
            override fun onSuccess(deviceToken: String) {
                //注册成功会返回deviceToken deviceToken是推送消息的唯一标志
                Timber.i("友盟注册成功：deviceToken：-------->  $deviceToken")
            }

            override fun onFailure(s: String, s1: String) {
                Timber.e("友盟注册失败：-------->  s:$s,s1:$s1")
            }
        })
        val messageHandler = object : UmengMessageHandler() {
            override fun dealWithNotificationMessage(context: Context, msg: UMessage) {
                //调用super则会走通知展示流程，不调用super则不展示通知
                super.dealWithNotificationMessage(context, msg)
            }
        }
        mPushAgent.messageHandler = messageHandler

        val handler = object : UmengNotificationClickHandler() {
            override fun launchApp(context: Context, msg: UMessage?) {
                if (msg?.extra != null) {
                    val set = msg.extra.entries// 取得键值对的对象set集合
                    for ((key, value) in set) {// 遍历键值对的集合
                        if ("url" == key) {
                            url = value
                            break
                        }
                    }
                    url?.let {
                        val intent = Intent(application, CommWebViewActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        intent.putExtra(CommWebViewActivity.KEY_JAVASCRIPT_TYPE, CommWebViewActivity.TYPE_JAVASCRIPT_JEL_LE_ME)
                        intent.putExtra(CommWebViewActivity.KEY_URL_NAME, url)
                        intent.putExtra(CommWebViewActivity.KEY_IS_AUTHENTICATION, CommWebViewActivity.TYPE_TITLE_NORMAL)
                        application.startActivity(intent)
                    }
                } else {
                    val intent = Intent(application, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    application.startActivity(intent)
                }
                super.launchApp(context, msg)
            }

        }
        mPushAgent.notificationClickHandler = handler
        UMCountUtil.init(application)
    }

    private fun initBugly(application: Application) {
        // 设置是否开启热更新能力，默认为true
        Beta.enableHotfix = true
        // 设置是否自动下载补丁，默认为true
        Beta.canAutoDownloadPatch = true
        // 设置是否自动合成补丁，默认为true
        Beta.canAutoPatch = true
        // 设置是否提示用户重启，默认为false
        Beta.canNotifyUserRestart = false

        // 补丁回调接口
        Beta.betaPatchListener = object : BetaPatchListener {
            override fun onPatchReceived(patchFile: String) {}

            override fun onDownloadReceived(savedLength: Long, totalLength: Long) {}

            override fun onDownloadSuccess(msg: String) {}

            override fun onDownloadFailure(msg: String) {}

            override fun onApplySuccess(msg: String) {}

            override fun onApplyFailure(msg: String) {}

            override fun onPatchRollback() {

            }
        }

        // 设置开发设备，默认为false，上传补丁如果下发范围指定为“开发设备”，需要调用此接口来标识开发设备
        Bugly.setIsDevelopmentDevice(application, SPUtils.getInstance().getBoolean(SPConstant.SHARED_PRE_IS_DEVELOPMENT_DEVICE, false))
        Bugly.init(application, BaseConfig.BUGLY_KEY, BaseConfig.BUGLY_IS_DEBUG)
    }

    override fun onTerminate(application: Application) {

    }
}
