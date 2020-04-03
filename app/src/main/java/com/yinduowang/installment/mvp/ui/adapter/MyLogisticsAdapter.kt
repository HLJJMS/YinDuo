package com.yinduowang.installment.mvp.ui.adapter

import androidx.core.content.ContextCompat
import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.yinduowang.installment.R

import com.yinduowang.installment.mvp.model.entity.LogisticsBeanEntity
/**
 * Description：物流
 * Author：田羽衡
 * Version：<v1.0，2019/11/1 >
 * LastEditTime：2019/11/1
 * LastEditors:
 * Deprecated： false
 */

class MyLogisticsAdapter(data: List<LogisticsBeanEntity>?) : BaseQuickAdapter<LogisticsBeanEntity, BaseViewHolder>(R.layout.item_my_logistics, data) {
    override fun convert(helper: BaseViewHolder, item: LogisticsBeanEntity?) {
        if (helper?.layoutPosition == 0) {
            helper.setVisible(R.id.vLineUp, false);
            helper.setVisible(R.id.vLineDown, true)
                    .setVisible(R.id.vBottomLine, true)
                    .setBackgroundRes(R.id.vPoint, R.drawable.bg_logistice_point_blue)


            var point = helper.getView<View>(R.id.vPoint) as View
            var pointGray = helper.getView<View>(R.id.vPointGray) as View
            point.visibility = View.VISIBLE
            pointGray.visibility = View.INVISIBLE

            val title = helper.getView<View>(R.id.tvTitle) as TextView
            title.setTextColor(ContextCompat.getColor(mContext, R.color.color_333333))
            val paint = title.getPaint()
            paint.setFakeBoldText(true)
        } else if (helper?.layoutPosition == data.size - 1) {

            helper.setVisible(R.id.vLineUp, true);
            helper.setVisible(R.id.vLineDown, false)
                    .setVisible(R.id.vBottomLine, true)
                    .setBackgroundRes(R.id.vPoint, R.drawable.bg_logistice_point_gray);
            var point = helper.getView<View>(R.id.vPoint) as View
            var pointGray = helper.getView<View>(R.id.vPointGray) as View
            point.visibility = View.INVISIBLE
            pointGray.visibility = View.VISIBLE


            val title = helper.getView<View>(R.id.tvTitle) as TextView
            title.setTextColor(ContextCompat.getColor(mContext, R.color.color_9A9A9A))
            val paint = title.getPaint()
            paint.setFakeBoldText(false)
        } else {
            helper?.setVisible(R.id.vLineUp, true);
            helper?.setVisible(R.id.vLineDown, true)
                    ?.setVisible(R.id.vBottomLine, true)
                    ?.setBackgroundRes(R.id.vPoint, R.drawable.bg_logistice_point_gray);
            var point = helper?.getView<View>(R.id.vPoint) as View
            var pointGray = helper.getView<View>(R.id.vPointGray) as View
            point.visibility = View.INVISIBLE
            pointGray.visibility = View.VISIBLE


            val title = helper?.getView<View>(R.id.tvTitle) as TextView
            title.setTextColor(ContextCompat.getColor(mContext, R.color.color_9A9A9A))
            val paint = title.getPaint()
            paint.setFakeBoldText(false)
        }
        helper?.setText(R.id.tvTitle, item?.msg)
                ?.setText(R.id.tvTime, item?.time)


    }

}
