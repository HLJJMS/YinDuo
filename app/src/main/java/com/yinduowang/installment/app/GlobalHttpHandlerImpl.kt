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

import android.content.Intent
import com.blankj.utilcode.util.SPUtils
import com.jess.arms.http.GlobalHttpHandler
import com.jess.arms.integration.AppManager
import com.yinduowang.installment.app.constant.SPConstant
import com.yinduowang.installment.app.utils.ClearLoginDataUtil
import com.yinduowang.installment.mvp.model.event.ShowHomeShopEvent
import com.yinduowang.installment.mvp.ui.activity.LoginActivity
import com.yinduowang.installment.mvp.ui.activity.MainActivity
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import org.simple.eventbus.EventBus
import timber.log.Timber

/**
 * ================================================
 * 展示 [GlobalHttpHandler] 的用法
 *
 *
 * Created by JessYan on 04/09/2017 17:06
 * [Contact me](mailto:jess.yan.effort@gmail.com)
 * [Follow me](https://github.com/JessYanCoding)
 * ================================================
 */
class GlobalHttpHandlerImpl : GlobalHttpHandler {

    /**
     * 这里可以先客户端一步拿到每一次 Http 请求的结果, 可以先解析成 Json, 再做一些操作, 如检测到 token 过期后
     * 重新请求 token, 并重新执行请求
     *
     * @param httpResult 服务器返回的结果 (已被框架自动转换为字符串)
     * @param chain      [okhttp3.Interceptor.Chain]
     * @param response   [Response]
     * @return [Response]
     */
    override fun onHttpResultResponse(httpResult: String?, chain: Interceptor.Chain, response: Response): Response {
        /* 这里如果发现 token 过期, 可以先请求最新的 token, 然后在拿新的 token 放入 Request 里去重新请求
        注意在这个回调之前已经调用过 proceed(), 所以这里必须自己去建立网络请求, 如使用 Okhttp 使用新的 Request 去请求
        create a new request and modify it accordingly using the new token
        Request newRequest = chain.request().newBuilder().header("token", newToken)
                             .build();

        retry the request

        response.body().close();
        如果使用 Okhttp 将新的请求, 请求成功后, 再将 Okhttp 返回的 Response return 出去即可
        如果不需要返回新的结果, 则直接把参数 response 返回出去即可*/

        if (!httpResult.isNullOrEmpty()) {
            Timber.i("httpResult:%s", httpResult)
            try {
                val jsonObject = JSONObject(httpResult)
                // 如果接口返回code==L00001则表明登录失败，需要清空本地登录信息并且跳转到登录页面
                if ((jsonObject.has("code") && jsonObject.get("code") == "L00001") ||
                        (jsonObject.has("status") && jsonObject.get("status") == "L00001")) {
                    val topActivity = AppManager.getAppManager().topActivity
                    val isLogin = topActivity is LoginActivity
                    if (!isLogin) {
                        EventBus.getDefault().post(ShowHomeShopEvent())
                        SPUtils.getInstance().put(SPConstant.SHARE_TAG_USER_LOGIN_TO_SIGNOUT, true)
                        ClearLoginDataUtil.clearLoginStatus()
                        val intent = Intent(topActivity, MainActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                        intent.putExtra("goLogin", true)
                        intent.putExtra("goLoginMsg", jsonObject.getString("msg"))
                        topActivity?.startActivity(intent)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return response
    }

    /**
     * 这里可以在请求服务器之前拿到 [Request], 做一些操作比如给 [Request] 统一添加 token 或者 header 以及参数加密等操作
     *
     * @param chain   [okhttp3.Interceptor.Chain]
     * @param request [Request]
     * @return [Request]
     */
    override fun onHttpRequestBefore(chain: Interceptor.Chain, request: Request): Request {
        return request
    }
}
