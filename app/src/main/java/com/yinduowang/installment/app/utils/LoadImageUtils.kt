package com.yinduowang.installment.app.utils

import android.content.Context
import android.view.View
import android.widget.ImageView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso


/**
 * 下载图片用于展示
 */
object LoadImageUtils {
    fun showImage(context: Context, url: String, imageView: ImageView, placeHolder: Int) {
//        ArmsUtils.obtainAppComponentFromContext(context)
//                .imageLoader()
//                .loadImage(context, ImageConfigImpl
//                        .builder()
//                        .url(url)
//                        .errorPic(placeHolder)
//                        .placeholder(placeHolder)
//                        .imageView(imageView)
//                        .build())
        Picasso.with(context)
                .load(url)
                .placeholder(placeHolder)
                .error(placeHolder)
                .into(imageView)
    }

    fun showImage(context: Context?, url: String?, imageView: ImageView?) {
//        ArmsUtils.obtainAppComponentFromContext(context)
////                .imageLoader()
////                .loadImage(context, ImageConfigImpl
////                        .builder()
////                        .url(url)
////                        .imageView(imageView)
////                        .build())
        Picasso.with(context)
                .load(url)
                .into(imageView)
    }

    //根据资源是否加载成功来控制显隐
    fun downloadImageAftershow(context: Context, url: String, imageView: ImageView?) {
        Picasso.with(context).load(url).into(imageView, object : Callback {
            override fun onSuccess() {
                imageView?.let {
                    it.visibility = View.VISIBLE
                }
            }

            override fun onError() {
                imageView?.let {
                    it.visibility = View.GONE
                }
            }

        })
    }
}
