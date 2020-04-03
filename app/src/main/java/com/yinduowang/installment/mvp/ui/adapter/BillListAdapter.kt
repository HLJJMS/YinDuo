package com.yinduowang.installment.mvp.ui.adapter

import androidx.core.content.ContextCompat
import android.text.TextUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.jess.arms.utils.ArmsUtils
import com.yinduowang.installment.R
import com.yinduowang.installment.mvp.model.entity.ListBean
/*
* 描述：〈商城账单明细adapter〉
* 创建⼈：〈田羽衡〉
* 时间：〈20191023〉
* 版本号：<1.1,20191023>
* 修改⼈：：〈田羽衡〉
* 修改时间<1.1,20191023>
* 弃⽤： <false>
*/

class BillListAdapter(data: List<ListBean>?) : BaseQuickAdapter<ListBean, BaseViewHolder>(R.layout.item_bill_list, data) {
    override fun convert(helper: BaseViewHolder, item: ListBean?) {
        helper?.setText(R.id.tvRmb, item!!.amountDue)
                ?.setText(R.id.tvTitle, item!!.goodsName)
                ?.setText(R.id.tvTimes, "第" + item!!.curStage + "/" + item!!.stage + "期 | ")
        if (TextUtils.equals(item!!.repaymentState, "2")) {
            helper?.setText(R.id.tvType,"已还款")
                    ?.setTextColor(R.id.tvType, ArmsUtils.getColor(mContext, R.color.color_adadad))
        }else{
            if(TextUtils.equals(item!!.overdueState ,"1")  ){
                helper?.setText(R.id.tvType,"已逾期")
                        ?.setTextColor(R.id.tvType, ArmsUtils.getColor(mContext, R.color.color_FF3763))//颜色不对带ui
            }else{
                helper?.setText(R.id.tvType,"未还款")
                        ?.setTextColor(R.id.tvType, ArmsUtils.getColor(mContext, R.color.color_FF7213))
            }


        }
    }


}
