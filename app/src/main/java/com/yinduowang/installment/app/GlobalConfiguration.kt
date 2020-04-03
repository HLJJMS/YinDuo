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
import androidx.fragment.app.FragmentManager
import com.jess.arms.base.delegate.AppLifecycles
import com.jess.arms.di.module.GlobalConfigModule
import com.jess.arms.http.imageloader.glide.GlideImageLoaderStrategy
import com.jess.arms.http.log.RequestInterceptor
import com.jess.arms.integration.ConfigModule
import com.yinduowang.installment.BuildConfig
import com.yinduowang.installment.app.constant.Api
import com.yinduowang.installment.app.constant.Constant
import com.yinduowang.installment.app.utils.http.CommonParametersIntercepter
import com.yinduowang.installment.app.utils.http.EncodeInterceptor
import com.yinduowang.installment.app.utils.http.SocketTimeoutIntercepter
import com.yinduowang.installment.app.utils.http.TrustAllCerts
import me.jessyan.progressmanager.ProgressManager
import me.jessyan.retrofiturlmanager.RetrofitUrlManager
import java.security.SecureRandom
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager


/**
 * ================================================
 * App 的全局配置信息在此配置, 需要将此实现类声明到 AndroidManifest 中
 * ConfigModule 的实现类可以有无数多个, 在 Application 中只是注册回调, 并不会影响性能 (多个 ConfigModule 在多 Module 环境下尤为受用)
 * ConfigModule 接口的实现类对象是通过反射生成的, 这里会有些性能损耗
 *
 * @see com.jess.arms.base.delegate.AppDelegate
 *
 * @see com.jess.arms.integration.ManifestParser
 *
 * @see [请配合官方 Wiki 文档学习本框架](https://github.com/JessYanCoding/MVPArms/wiki)
 *
 * @see [更新日志, 升级必看!](https://github.com/JessYanCoding/MVPArms/wiki/UpdateLog)
 *
 * @see [常见 Issues, 踩坑必看!](https://github.com/JessYanCoding/MVPArms/wiki/Issues)
 *
 * @see [MVPArms 官方组件化方案 ArmsComponent, 进阶指南!](https://github.com/JessYanCoding/ArmsComponent/wiki)
 * Created by JessYan on 12/04/2017 17:25
 * [Contact me](mailto:jess.yan.effort@gmail.com)
 * [Follow me](https://github.com/JessYanCoding)
 * ================================================
 */
class GlobalConfiguration : ConfigModule {
    //    public static String sDomain = Api.APP_DOMAIN;

    override fun applyOptions(context: Context, builder: GlobalConfigModule.Builder) {
        if (!BuildConfig.LOG_DEBUG) { //Release 时, 让框架不再打印 Http 请求和响应的信息
            builder.printHttpLogLevel(RequestInterceptor.Level.NONE)
        }

        builder.baseurl(Api.BASE_URL)
                //强烈建议自己自定义图片加载逻辑, 因为 arms-imageloader-glide 提供的 GlideImageLoaderStrategy 并不能满足复杂的需求
                //请参考 https://github.com/JessYanCoding/MVPArms/wiki#3.4
                .imageLoaderStrategy(GlideImageLoaderStrategy())
                //这里提供一个全局处理 Http 请求和响应结果的处理类, 可以比客户端提前一步拿到服务器返回的结果, 可以做一些操作, 比如 Token 超时后, 重新获取 Token
                .globalHttpHandler(GlobalHttpHandlerImpl())
                //用来处理 RxJava 中发生的所有错误, RxJava 中发生的每个错误都会回调此接口
                //RxJava 必须要使用 ErrorHandleSubscriber (默认实现 Subscriber 的 onError 方法), 此监听才生效
                .responseErrorListener(ResponseErrorListenerImpl())
                .gsonConfiguration {//这里可以自己自定义配置 Gson 的参数
                    context1, gsonBuilder ->
                    gsonBuilder
                            .serializeNulls()//支持序列化值为 null 的参数
                            .enableComplexMapKeySerialization()//支持将序列化 key 为 Object 的 Map, 默认只能序列化 key 为 String 的 Map
                }
                .retrofitConfiguration {//这里可以自己自定义配置 Retrofit 的参数, 甚至您可以替换框架配置好的 OkHttpClient 对象 (但是不建议这样做, 这样做您将损失框架提供的很多功能)
                    context1, retrofitBuilder ->
                    //                    retrofitBuilder.addConverterFactory(FastJsonConverterFactory.create());//比如使用 FastJson 替代 Gson
                }
                .okhttpConfiguration {//这里可以自己自定义配置 Okhttp 的参数
                    context1, okhttpBuilder ->
                    // 支持 Https
                    okhttpBuilder.sslSocketFactory(createSSLSocketFactory()!!, TrustAllCerts())
                    // 用于主机名验证的基接口 全部返还true
                    okhttpBuilder.hostnameVerifier { hostname, session -> true }
                    // 全局的读取超时时间
                    okhttpBuilder.readTimeout(Constant.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS)
                    // 全局的写入超时时间
                    okhttpBuilder.writeTimeout(Constant.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS)
                    // 全局的连接超时时间
                    okhttpBuilder.connectTimeout(Constant.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS)
                    // 拦截器 添加公共参数和header
                    okhttpBuilder.addInterceptor(SocketTimeoutIntercepter())
                    okhttpBuilder.addInterceptor(CommonParametersIntercepter())
                    okhttpBuilder.addInterceptor(EncodeInterceptor())
                    // 使用一行代码监听 Retrofit／Okhttp 上传下载进度监听,以及 Glide 加载进度监听, 详细使用方法请查看 https://github.com/JessYanCoding/ProgressManager
                    ProgressManager.getInstance().with(okhttpBuilder)
                    // 让 Retrofit 同时支持多个 BaseUrl 以及动态改变 BaseUrl, 详细使用方法请查看 https://github.com/JessYanCoding/RetrofitUrlManager
                    RetrofitUrlManager.getInstance().with(okhttpBuilder)
                }
                .rxCacheConfiguration {//这里可以自己自定义配置 RxCache 的参数
                    context1, rxCacheBuilder ->
                    rxCacheBuilder.useExpiredDataIfLoaderNotAvailable(true)
                    null
                }
    }

    override fun injectAppLifecycle(context: Context, lifecycles: MutableList<AppLifecycles>) {
        //AppLifecycles 中的所有方法都会在基类 Application 的对应生命周期中被调用, 所以在对应的方法中可以扩展一些自己需要的逻辑
        //可以根据不同的逻辑添加多个实现类
        lifecycles.add(AppLifecyclesImpl())
    }

    override fun injectActivityLifecycle(context: Context, lifecycles: MutableList<Application.ActivityLifecycleCallbacks>) {
        //ActivityLifecycleCallbacks 中的所有方法都会在 Activity (包括三方库) 的对应生命周期中被调用, 所以在对应的方法中可以扩展一些自己需要的逻辑
        //可以根据不同的逻辑添加多个实现类
        lifecycles.add(ActivityLifecycleCallbacksImpl())
    }

    override fun injectFragmentLifecycle(context: Context, lifecycles: MutableList<FragmentManager.FragmentLifecycleCallbacks>) {
        //FragmentLifecycleCallbacks 中的所有方法都会在 Fragment (包括三方库) 的对应生命周期中被调用, 所以在对应的方法中可以扩展一些自己需要的逻辑
        //可以根据不同的逻辑添加多个实现类
        lifecycles.add(FragmentLifecycleCallbacksImpl())
    }

    private fun createSSLSocketFactory(): SSLSocketFactory? {
        var ssfFactory: SSLSocketFactory? = null

        try {
            val sc = SSLContext.getInstance("TLS")
            sc.init(null, arrayOf<TrustManager>(TrustAllCerts()), SecureRandom())
            ssfFactory = sc.socketFactory
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return ssfFactory
    }
}
