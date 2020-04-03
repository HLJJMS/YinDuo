package com.yinduowang.installment.app.utils

import java.util.*

object TimeUtils {
    /**
     * 获取年
     * @return
     */
    fun getYear(): Int {
        val cd = Calendar.getInstance()
        return cd.get(Calendar.YEAR)
    }
    /**
     * 获取月
     * @return
     */
    fun getMonth(): Int {
        val cd = Calendar.getInstance()
        return cd.get(Calendar.MONTH) + 1
    }
    /**
     * 获取日
     * @return
     */
    fun getDay(): Int {
        val cd = Calendar.getInstance()
        return cd.get(Calendar.DATE)
    }
    /**
     * 获取时
     * @return
     */
    fun getHour(): Int {
        val cd = Calendar.getInstance()
        return cd.get(Calendar.HOUR)
    }
    /**
     * 获取分
     * @return
     */
    fun getMinute(): Int {
        val cd = Calendar.getInstance()
        return cd.get(Calendar.MINUTE)
    }
    /**
     * 获取当前时间的时间戳
     * @return
     */
    fun getCurrentTimeMillis(): Long {
        return System.currentTimeMillis()
    }

}