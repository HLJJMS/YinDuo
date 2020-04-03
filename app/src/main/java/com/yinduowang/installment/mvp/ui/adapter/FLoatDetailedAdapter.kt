package com.yinduowang.installment.mvp.ui.adapter

import android.content.Intent
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.yinduowang.installment.R
import com.yinduowang.installment.app.utils.LoadImageUtils
import com.yinduowang.installment.mvp.model.entity.Data
import com.yinduowang.installment.mvp.ui.activity.CommWebViewActivity

/**
 * @Description:首页-悬浮引流列表适配器
 * @Author: 张利超
 * @Date: 2019-10-31 10:26:52
 * @Version: v1.0, 2019-10-31
 * @LastEditors:张利超
 * @LastEditTime: 2019-10-31 10:26:52
 * @Deprecated: false
 */
class FLoatDetailedAdapter(data: List<Data>?, private val ac_type: String) : BaseQuickAdapter<Data, BaseViewHolder>(R.layout.item_float_detailed, data) {
    var type = ""
    override fun convert(holder: BaseViewHolder, item: Data) {
        //        LoadImageUtils.showImage(mContext, item.getThumb(), holder.getView(R.id.iv_title));
        if (!TextUtils.isEmpty(item.thumb)) {
            LoadImageUtils.showImage(mContext, item.thumb!!, holder.getView<View>(R.id.iv_title) as ImageView, R.mipmap.ic_yinliu)
        }
        //title
        item.name?.let {
            holder.setText(R.id.tv_title, it)
        }
        //申请人数
        item.total?.let {
            holder.setText(R.id.tv_number_applicants, it)
        }
        //限额
        item.money_limit?.let {
            holder.setText(R.id.tv_money_limit, it)
        }
        //介绍
        item.desc?.let {
            holder.setText(R.id.time, it)
        }
        //通过率
        item.pass_rate?.let {
            holder.setText(R.id.textView11, "参考通过率：${it}%")
        }
        //点击跳转
        holder.getView<TextView>(R.id.tv_apply_now).setOnClickListener {
            val intent = Intent(mContext, CommWebViewActivity::class.java)
            intent.putExtra(CommWebViewActivity.KEY_URL_NAME, item.url)
            intent.putExtra(CommWebViewActivity.KEY_WEB_TITLE, item.name)
            intent.putExtra("yinliu", true)
            intent.putExtra("sourceId", item?.source_id)
            intent.putExtra("actype", ac_type)
            intent.putExtra("type", type)
            intent.putExtra(CommWebViewActivity.KEY_JAVASCRIPT_TYPE, CommWebViewActivity.TYPE_JAVASCRIPT_JEL_LE_ME)
            intent.putExtra(CommWebViewActivity.KEY_IS_AUTHENTICATION, CommWebViewActivity.TYPE_TITLE_NORMAL)
            mContext.startActivity(intent)
        }
    }

}
