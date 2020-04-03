package com.yinduowang.installment.mvp.ui.adapter

import android.content.Intent
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.jess.arms.utils.ArmsUtils
import com.qmuiteam.qmui.layout.QMUILinearLayout
import com.yinduowang.installment.R
import com.yinduowang.installment.app.utils.LoadImageUtils
import com.yinduowang.installment.mvp.model.entity.LoanBottomEntity
import com.yinduowang.installment.mvp.model.entity.LoanBottomEntity.Companion.TYPE_BOTTOM_BANNER
import com.yinduowang.installment.mvp.model.entity.LoanBottomEntity.Companion.TYPE_BOTTOM_LIST
import com.yinduowang.installment.mvp.ui.activity.CommWebViewActivity

/**
 * @Description:借款首页-适配器
 * @Author:张利超
 * @Date: 2019-10-23 10:11:28
 * @Version: v1.0, 2019-10-23
 * @LastEditors:张利超
 * @LastEditTime:2019-10-23 10:11:28
 * @Deprecated: false
 */
class LoanBottomAdapter(data: ArrayList<LoanBottomEntity>, var ac_type: String) : BaseMultiItemQuickAdapter<LoanBottomEntity, BaseViewHolder>(data) {
    var type = ""
    override fun convert(helper: BaseViewHolder, item: LoanBottomEntity) {
        when (helper.itemViewType) {
            TYPE_BOTTOM_BANNER -> {
                setBanner(helper, item)
            }
            TYPE_BOTTOM_LIST -> {
                setFloatList(helper, item)
            }

        }
    }

    init {
        addItemType(TYPE_BOTTOM_BANNER, R.layout.item_loan)
        addItemType(TYPE_BOTTOM_LIST, R.layout.item_float_detailed)
    }

    private fun setBanner(helper: BaseViewHolder, item: LoanBottomEntity) {
        var qmuiRadiusImageView = helper.getView<ImageView>(R.id.qmuiRadiusImageView)
        var qmuill = helper.getView<QMUILinearLayout>(R.id.qmuill)
        qmuill.radius = ArmsUtils.dip2px(mContext, 6f)
        LoadImageUtils.showImage(mContext, item.data?.thumb, qmuiRadiusImageView)
        //点击跳转进入web页面
        helper.itemView.setOnClickListener {
            if (!item.data?.url.isNullOrEmpty()) {
                val intent = Intent(mContext, CommWebViewActivity::class.java)
                intent.putExtra(CommWebViewActivity.KEY_URL_NAME, item.data?.url)
                intent.putExtra(CommWebViewActivity.KEY_URL_ADD_TYPE, false)
                intent.putExtra(CommWebViewActivity.KEY_JAVASCRIPT_TYPE, CommWebViewActivity.TYPE_JAVASCRIPT_JEL_LE_ME)
                intent.putExtra(CommWebViewActivity.KEY_IS_AUTHENTICATION, CommWebViewActivity.TYPE_TITLE_NORMAL)
                mContext.startActivity(intent)
            }
        }
    }

    private fun setFloatList(helper: BaseViewHolder, item: LoanBottomEntity) {
        if (helper.adapterPosition == 1) {
            helper.setGone(R.id.iv_yinliu_top, true)
        } else {
            helper.setGone(R.id.iv_yinliu_top, false)
        }
        if (!TextUtils.isEmpty(item.data?.thumb)) {
            LoadImageUtils.showImage(mContext, item.data?.thumb!!, helper.getView<View>(R.id.iv_title) as ImageView, R.mipmap.ic_yinliu)
        }
        //title
        item.data?.name?.let {
            helper.setText(R.id.tv_title, it)
        }
        //申请人数
        item.data?.total?.let {
            helper.setText(R.id.tv_number_applicants, it)
        }
        //限额
        item.data?.money_limit?.let {
            helper.setText(R.id.tv_money_limit, it)
        }
        //贷款介绍
        item.data?.desc.let {
            helper.setText(R.id.time, it)
        }
        //通过率
        item.data?.pass_rate?.let {
            helper.setText(R.id.textView11, "参考通过率：${it}%")
        }
        //点击跳转
        helper.getView<TextView>(R.id.tv_apply_now).setOnClickListener {
            val intent = Intent(mContext, CommWebViewActivity::class.java)
            intent.putExtra(CommWebViewActivity.KEY_URL_NAME, item.data?.url)
            intent.putExtra(CommWebViewActivity.KEY_WEB_TITLE, item.data?.name)
            intent.putExtra("yinliu", true)
            intent.putExtra("sourceId", item?.data?.source_id)
            intent.putExtra("actype", ac_type)
            intent.putExtra("type", type)
            intent.putExtra(CommWebViewActivity.KEY_JAVASCRIPT_TYPE, CommWebViewActivity.TYPE_JAVASCRIPT_JEL_LE_ME)
            intent.putExtra(CommWebViewActivity.KEY_IS_AUTHENTICATION, CommWebViewActivity.TYPE_TITLE_NORMAL)
            mContext.startActivity(intent)
        }
    }
}
