package com.yinduowang.installment.mvp.ui.adapter

import android.content.Intent
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.yinduowang.installment.R
import com.yinduowang.installment.mvp.model.entity.DiscoverEntity
import com.yinduowang.installment.mvp.ui.activity.ArticleDetailsActivity


/**
 * @Description: 提测时显示的发现页面列表适配器
 * @Author:
 * @Date: 2019-11-13 11:28:50
 * @Version: 1.1, 2019-11-13
 * @LastEditors:
 * @LastEditTime:
 * @Deprecated: false
 */
class DiscoveryAdapter(data: List<DiscoverEntity>) : BaseQuickAdapter<DiscoverEntity, BaseViewHolder>(R.layout.item_discovery, data) {

    override fun convert(helper: BaseViewHolder, item: DiscoverEntity) {
        helper.setText(R.id.tv_content, item.name)
        helper.setText(R.id.tv_tag, item.type)
        helper.setText(R.id.tv_time, item.date)
        helper.setImageResource(R.id.iv_right, item.imgId)
        helper.itemView.setOnClickListener {
            val intent = Intent(mContext, ArticleDetailsActivity::class.java)
            intent.putExtra("entity", item)
            mContext.startActivity(intent)
        }
    }
}
