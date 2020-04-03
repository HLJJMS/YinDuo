package com.yinduowang.installment.app.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.EditText


class EndCursorEditText : EditText {
    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context) : super(context) {}

    override fun onSelectionChanged(selStart: Int, selEnd: Int) {
        super.onSelectionChanged(selStart, selEnd)
        //保证光标始终在最后面
        if (selStart == selEnd) {//防止不能多选
            setSelection(text.length)
        }

    }
}
