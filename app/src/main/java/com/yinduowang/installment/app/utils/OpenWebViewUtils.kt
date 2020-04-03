package com.yinduowang.installment.app.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.yinduowang.installment.app.constant.Api
import com.yinduowang.installment.mvp.ui.activity.CommWebViewActivity

object OpenWebViewUtils {

    var obj = listOf(
            mapOf(//专区
                    "url" to "/classification/district",
                    "name" to "district"
            ),
            mapOf(//商城分类
                    "url" to "/classification/category",
                    "name" to "category"
            ),
            mapOf(//商品列表
                    "url" to "/classification/goodsList",
                    "name" to "normal"
            ),
            mapOf(//详情
                    "url" to "/classification/goodsDetail",
                    "name" to "detail"
            ))

    /**
     * 根据url判断网页标题类型
     * */
    fun openWebViewTypeFromUrl(context: Context, url: String, title: String?) {
        var titleType = ""
        for (map in obj) {
            if (map["url"]?.let { url.indexOf(it) } != -1) {
                titleType = map["name"].toString()
            }
        }

        val intent = Intent(context, CommWebViewActivity::class.java)
        intent.putExtra(CommWebViewActivity.KEY_URL_NAME, url)
        intent.putExtra(CommWebViewActivity.KEY_IS_AUTHENTICATION, titleType)
        context.startActivity(intent)
    }

    fun openWebViewGoodsList(context: Context, id: String) {
        val intent = Intent(context, CommWebViewActivity::class.java)
        val url = Api.CLASSIFICATION_GOODS_LIST + "?Id=$id"
        intent.putExtra(CommWebViewActivity.KEY_URL_NAME, url)
        intent.putExtra(CommWebViewActivity.KEY_IS_AUTHENTICATION, CommWebViewActivity.TYPE_TITLE_NORMAL)
        context.startActivity(intent)
    }

    fun openWebViewGoodsDetails(context: Context, id: String, isMine:Boolean) {
        val intent = Intent(context, CommWebViewActivity::class.java)
        var url = Api.CLASSIFICATION_GOODS_DETAIL + "?Id=$id"
        if (isMine) {
            url += "&order=1"
        }
        intent.putExtra(CommWebViewActivity.KEY_URL_NAME, url)
        intent.putExtra(CommWebViewActivity.KEY_IS_AUTHENTICATION, CommWebViewActivity.TYPE_TITLE_DETAIL)
        context.startActivity(intent)
    }

    fun openWebViewDistrict(context: Context, id: String) {
        val intent = Intent(context, CommWebViewActivity::class.java)
        val url = Api.CLASSIFICATION_DISTRICT + "?Id=$id"
        intent.putExtra(CommWebViewActivity.KEY_URL_NAME, url)
        intent.putExtra(CommWebViewActivity.KEY_IS_AUTHENTICATION, CommWebViewActivity.TYPE_TITLE_DISTRICT)
        context.startActivity(intent)
    }

    fun openWebViewGoods(context: Context) {
        val intent = Intent(context, CommWebViewActivity::class.java)
        val url = Api.CLASSIFICATION_CATEGORY + "?Id=undefined"
        intent.putExtra(CommWebViewActivity.KEY_URL_NAME, url)
        intent.putExtra(CommWebViewActivity.KEY_IS_AUTHENTICATION, CommWebViewActivity.TYPE_TITLE_CATEGORY)
        context.startActivity(intent)
    }

    /**
     * @param isForResult 是否使用返回码启动页面
     * @param context 上下文 不适用返回码
     * @param activity 上下文 使用返回码
     * @param url 网页地址
     * @param title 网页标题 可以为空
     * @param isAuthentication 打开网页标题类型 可以为空
     * @param javascriptType 打开网页 加载交互类型
     * @param isShowBottomBtn 是否显示底部按钮
     * @param haveParams 是否拼接所有参数
     * @param map 网页参数
     * @param addToken 是否拼接token参数
     * @param addType 是否拼接type参数
     * @param flags 启动模式
     * */
    private fun openWebViewBase(isForResult: Boolean, context: Context?, activity: Activity?, requestCode: Int?, url: String,
                                title: String?, isAuthentication: String?, javascriptType: String?, isShowBottomBtn: Boolean?,
                                haveParams: Boolean?, map: HashMap<String, String>?, addToken: Boolean?, addType: Boolean?, flags: Int?) {
//        var intent = Intent()
//        // 启动模式 是否使用startActivityForResult模式
//        if (isForResult) {
//            if (activity != null) {
//                intent.setClass(activity, CommWebViewActivity::class.java)
//            } else {
//                throw Exception("+++++++++++++ 启动网页 并带有返回码时 activity不可以为空 +++++++++++++")
//            }
//        } else {
//            if (context != null) {
//                intent.setClass(context, CommWebViewActivity::class.java)
//            } else {
//                throw Exception("+++++++++++++ 启动网页 context不可以为空 +++++++++++++")
//            }
//        }
//        // 网页地址
//        intent.putExtra(CommWebViewActivity.KEY_URL_NAME, url)
//        // 网页标题 为空则不传
//        title?.let { intent.putExtra(CommWebViewActivity.KEY_TITLE, it) }
//        // 网页标题类型 为空时默认打开外网页
//        isAuthentication.let { intent.putExtra(CommWebViewActivity.KEY_IS_AUTHENTICATION, it) }
//        // 交互类型 为空时默认使用商城交互类型
//        if (javascriptType == null) {
//            intent.putExtra(CommWebViewActivity.KEY_JAVASCRIPT_TYPE, CommWebViewActivity.TYPE_JAVASCRIPT_INSTALLMENT)
//        } else {
//            intent.putExtra(CommWebViewActivity.KEY_JAVASCRIPT_TYPE, javascriptType)
//        }
//        // 是否显示网页底部按钮 为空时默认不显示
//        if (isShowBottomBtn == null || !isShowBottomBtn) {
//            intent.putExtra(CommWebViewActivity.TYPE_IS_SHOW_BOTTOM_BTN, false)
//        } else {
//            intent.putExtra(CommWebViewActivity.TYPE_IS_SHOW_BOTTOM_BTN, true)
//        }
//        // 是否拼接参数 为空时默认为拼接
//        if (haveParams == null || haveParams) {
//            haveParams.let { intent.putExtra(CommWebViewActivity.KEY_NO_HAVE_PARAMS, true) }
//            // 自定义参数 为空则不传
//            map.let { intent.putExtra(CommWebViewActivity.KEY_URL_PARAMS, it) }
//            // 是否需要拼接token 为空时默认拼接
//            if (addToken == null || addToken) {
//                intent.putExtra(CommWebViewActivity.KEY_URL_ADD_TOKEN, true)
//            } else {
//                intent.putExtra(CommWebViewActivity.KEY_URL_ADD_TOKEN, false)
//            }
//            // 是否需要拼接type 为空时默认拼接
//            if (addType == null || addType) {
//                intent.putExtra(CommWebViewActivity.KEY_URL_ADD_TYPE, true)
//            } else {
//                intent.putExtra(CommWebViewActivity.KEY_URL_ADD_TYPE, false)
//            }
//        } else {
//            haveParams.let { intent.putExtra(CommWebViewActivity.KEY_NO_HAVE_PARAMS, false) }
//        }
//        // 添加启动标识 如果打开的页面不是从已有页面打开的，如接收到推送打开页面
//        flags?.let { intent.flags = it }
//        intent.putExtra(CommWebViewActivity.KEY_FROM_OPEN_WEB_VIEW_UTILS, true)
//        // 启动模式 是否使用startActivityForResult模式
//        if (isForResult) {
//            if (activity != null && requestCode != null) {
//                activity.startActivityForResult(intent, requestCode)
//            } else {
//                throw Exception("+++++++++++++ 启动网页 并带有返回码时 activity和requestCode不可以为空 +++++++++++++")
//            }
//        } else {
//            if (context != null) {
//                context.startActivity(intent)
//            } else {
//                throw Exception("+++++++++++++ 启动网页 context不可以为空 +++++++++++++")
//            }
//        }
    }
}