package com.yinduowang.installment.app.utils

import android.content.Context

import com.umeng.analytics.MobclickAgent

/**
 * tip: 每个应用至多添加500个自定义事件，每个event 的 key不能超过10个，每个key的取值不能超过1000个。
 */
class UMCountUtil(internal var context: Context) {


    //    public void onEvent(String tag, String eventId, Map<String, String> map) {
    //        MobclickAgent.onEvent(context, tag + "_" + eventId, map);
    //    }

    fun onEvent(tag: String, eventId: String, v: String) {
        //        HashMap<String, String> map = new HashMap<String, String>();
        //        map.put("isAuthentication", v);
        //        onEvent(tag, eventId, map);
        MobclickAgent.onEvent(context, tag + "_" + eventId, v)
    }

    companion object {
        private var umCountUtil: UMCountUtil? = null

        fun init(context: Context) {
            umCountUtil = UMCountUtil(context)
        }

        fun instance(): UMCountUtil? {
            return umCountUtil
        }
    }

}
