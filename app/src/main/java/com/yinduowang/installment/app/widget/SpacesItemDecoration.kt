package com.yinduowang.installment.app.widget

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView


class SpacesItemDecoration(var space: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View,
                                parent: RecyclerView, state: RecyclerView.State) {
        if (state.itemCount != 1) {
            outRect.top = space
            outRect.left = space
            outRect.right = space
            outRect.bottom = 0
        }
    }
}