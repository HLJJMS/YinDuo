package com.yinduowang.installment.app.utils.gosetting

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView


class SpaceItemDecoration(val space: Int  //位移间距
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        if (parent.getChildAdapterPosition(view) % 3 == 0) {
            outRect.left = 0 //第一列左边贴边
        } else {
            if (parent.getChildAdapterPosition(view) % 3 == 1) {
                outRect.left = space//第二列移动一个位移间距
            } else {
                outRect.left = space * 2//由于第二列已经移动了一个间距，所以第三列要移动两个位移间距就能右边贴边，且item间距相等
            }
        }

        if (parent.getChildAdapterPosition(view) >= 3) {
            outRect.top = 17
        } else {
            outRect.top = 0
        }
    }

}