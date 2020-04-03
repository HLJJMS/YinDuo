package com.yinduowang.installment.app.widget

import android.content.Context
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import com.yinduowang.installment.R

/**
 * 可以配置分隔符的输入框
 * 使用interval_location属性设置分隔符位置，如 ‘3，8’为在第4位和第九位设置分割符
 * 使用placeholder属性设置分割符样式
 * 最长输入仍然使用android:maxLength="11"设置，会根据分割位置数量自动添加最大长度
 * 获取内容时，不能使用getText()/text方式获取，需要使用getRealText()方法，去掉分割符
 * */
class AutomaticIntervalEditText(val mContext: Context, attrs: AttributeSet) : AppCompatEditText(mContext, attrs) {


    var locationArray: List<String>? = null
    var placeholder = ' '

    private val mBuilder = StringBuilder()
    private var delect = true
    var textWatcher: AutomaticIntervalTextWatcher? = null

    init {
        initAttribute(attrs)
        initView()
    }

    fun getRealText(): String {
        return super.getText().toString().replace(placeholder.toString(), "")
    }

    private fun initAttribute(attrs: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.AutomaticIntervalEditText)
        val locations = typedArray.getString(R.styleable.AutomaticIntervalEditText_interval_location)
        if (!locations.isNullOrEmpty())
            locationArray = locations.split(',')
        val spaceMark = typedArray.getString(R.styleable.AutomaticIntervalEditText_space_mark)
        if (!spaceMark.isNullOrEmpty()) {
            if (spaceMark.length == 1) {
                placeholder = spaceMark[0]
            } else if (spaceMark.length > 1) {
                throw Exception("占位符只能是一个字符")
            }
        }
        if (!locationArray.isNullOrEmpty()) {
            filters = arrayOf<InputFilter>(InputFilter.LengthFilter(getMaxLength() + locationArray!!.size))
        }
    }

    fun setlocationArray(locations: String) {
        locationArray = locations.split(',')
        if (!locationArray.isNullOrEmpty()) {
            filters = arrayOf<InputFilter>(InputFilter.LengthFilter(getMaxLength() + locationArray!!.size))
        }
    }

    private fun getMaxLength(): Int {
        if (!filters.isNullOrEmpty()) {
            val lengthFilter: InputFilter.LengthFilter = filters[0] as InputFilter.LengthFilter
            return lengthFilter.max
        }
        return 0
    }

    private fun initView() {
        addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (textWatcher != null) {
                    textWatcher!!.afterTextChanged(s)
                }
            }

            override fun beforeTextChanged(charSequence: CharSequence?, start: Int, count: Int, after: Int) {
                // 判断删除还是写入
                delect = after - count <= 0
                if (textWatcher != null) {
                    textWatcher!!.beforeTextChanged(charSequence, start, count, after)
                }
            }

            override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {
                var charSequence = charSequence.toString()
                // 间隔位置不为空时、内容有改变时添加占位符
                if (!locationArray.isNullOrEmpty() && charSequence != mBuilder.toString()) {
                    mBuilder.clear()
                    //因为在setText会重置为0，所以要在setText前记录光标的位置
                    val cursorIndex = selectionStart
                    val length = charSequence.length
                    for (i in 0 until length) {
                        val c = charSequence[i]
                        if (c != placeholder) {
                            if (!locationArray.isNullOrEmpty()) {
                                for (location in locationArray!!) {
                                    if (mBuilder.length == location.toInt()) {
                                        mBuilder.append(placeholder)
                                    }
                                }
                            }
                            mBuilder.append(c)
                        }
                    }
                    //可能会得到大于最大长度的字符串，将最后一个删除
                    if (!filters.isNullOrEmpty() && mBuilder.length > getMaxLength()) {
                        mBuilder.delete(getMaxLength(), mBuilder.length)
                    }
                    //计算光标的偏移量
                    val offset = calculateOffset(charSequence, cursorIndex)
                    setText(mBuilder.toString())
                    //光标位置,做最小值判断是为防止越界,也是为了调整光标位
                    setSelection((cursorIndex + offset).coerceAtMost(mBuilder.length))
                }
                if (textWatcher != null) {
                    textWatcher!!.onTextChanged(charSequence, start, before, count)
                }
            }
        })
    }

    /**
     * 计算光标位之前需要的偏移量
     */
    private fun calculateOffset(charSequence: CharSequence, cursorIndex: Int): Int {
        val length = charSequence.length
        var offset = 0//需要偏移的光标位数，负数表示向前调，正数表示向后偏移
        val adjustedLen = mBuilder.length//调整后长度
        val minLen = if (adjustedLen > length) length else adjustedLen
        for (i in 0 until minLen) {
            if (i > cursorIndex - 1) {
                break
            }
            //只需要考虑调整后字符类型不同的情况
            if (charSequence[i] == placeholder && mBuilder[i] != placeholder) {
                offset -= 1
            } else if (charSequence[i] != placeholder && mBuilder[i] == placeholder) {
                offset += 1
            }
        }

        if (isSeparationPosition(cursorIndex - 1) && delect) {
            offset--
        }

        return offset
    }

    /**
     * 是否分隔位置
     */
    private fun isSeparationPosition(index: Int): Boolean {
        if (!locationArray.isNullOrEmpty()) {
            for (location in locationArray!!) {
                if (index == location.toInt()) return true
            }
            return false
        } else {
            return false
        }
    }

    open class AutomaticIntervalTextWatcher {
        open fun afterTextChanged(s: Editable?) {}
        open fun beforeTextChanged(charSequence: CharSequence?, start: Int, count: Int, after: Int) {}
        open fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {}
    }
}