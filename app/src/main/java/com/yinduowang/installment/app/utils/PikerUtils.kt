package com.yinduowang.installment.app.utils

import android.content.Context
import androidx.core.content.ContextCompat
import android.view.View
import com.bigkoo.pickerview.builder.OptionsPickerBuilder
import com.bigkoo.pickerview.listener.OnOptionsSelectListener
import com.google.gson.Gson
import com.tencent.bugly.crashreport.CrashReport
import com.yinduowang.installment.R
import com.yinduowang.installment.mvp.model.entity.MyProvince
import java.util.*

/**
 * 滚轮选择器 重写了 layout_pickerview_optionsoptions.xml 以便在title下方添加横线
 * */
object PikerUtils {

    lateinit var provinceNames: MutableList<Any>
    lateinit var cityNames: MutableList<List<Any>>
    lateinit var areaNames: MutableList<List<List<Any>>>

    /**
     * 单项滚轮选择器
     * */
    fun showOptionsPicker(context: Context, title: String, list: ArrayList<Any>, listener: OnOptionsSelectListener) {
        val options = OptionsPickerBuilder(context, listener)
                .isDialog(false)
                .setOutSideCancelable(false)
                .setCancelText("取消")
                .setSubmitText("确定")
                .setCancelColor(ContextCompat.getColor(context, R.color.color_9A9A9A))
                .setSubmitColor(ContextCompat.getColor(context, R.color.colorAccent))
                .setTitleBgColor(ContextCompat.getColor(context, R.color.white))
                .setLineSpacingMultiplier(1.8f)
                .setDividerColor(ContextCompat.getColor(context, R.color.color_33000000))
                .setTitleText(title)
                .setTitleSize(18)
                .setTitleColor(ContextCompat.getColor(context, R.color.color_333333))
                .build<Any>()
        options.setPicker(list)
        options.show()
    }

    /**
     * 城市选择器
     * 使用前需要在打开页面时先执行 parseCityList 以便获取城市三级列表
     * */
    fun showCityPicker(context: Context, title: String, listener: OnCitySelectListener) {
        val options = OptionsPickerBuilder(context, OnOptionsSelectListener { options1, options2, options3, v ->
            val one = provinceNames[options1] as String
            val two = cityNames[options1][options2] as String
            val three = areaNames[options1][options2][options3] as String
            listener.onOptionsSelect(one, two, three, v)
        })
                .isDialog(false)
                .setOutSideCancelable(false)
                .setCancelColor(ContextCompat.getColor(context, R.color.color_9A9A9A))
                .setSubmitColor(ContextCompat.getColor(context, R.color.colorAccent))
                .setTitleBgColor(ContextCompat.getColor(context, R.color.white))
                .setLineSpacingMultiplier(1.8f)
                .setDividerColor(ContextCompat.getColor(context, R.color.color_33000000))
                .setTitleText(title)
                .setTitleSize(18)
                .setTitleColor(ContextCompat.getColor(context, R.color.color_333333))
                .build<Any>()
        options.setPicker(provinceNames, cityNames, areaNames)
        options.show()
    }

    interface OnCitySelectListener {
        fun onOptionsSelect(options1: String, options2: String, options3: String, v: View?)
    }

    fun isInitCityList(): Boolean {
        return ::provinceNames.isInitialized && ::cityNames.isInitialized && ::areaNames.isInitialized
    }


    /**
     * 获取城市JsonList
     * */
    fun initCityList(context: Context) {
        if (!isInitCityList())
            Thread(Runnable {
                try {
                    val isCity = context.assets.open("city.txt")
                    val size = isCity.available()
                    val buffer = ByteArray(size)
                    isCity.read(buffer)
                    isCity.close()

                    val myProvinces = Gson().fromJson(String(buffer, Charsets.UTF_8), Array<MyProvince>::class.java).toMutableList()
                    provinceNames = arrayListOf()
                    cityNames = arrayListOf()
                    areaNames = arrayListOf()
                    for (province in myProvinces) {
                        provinceNames.add(province.name)
                        val cityName = arrayListOf<String>()
                        val areaName1 = arrayListOf<List<String>>()
                        for (city in province.city) {
                            cityName.add(city.name)
                            val area = city.area
                            val areaName = ArrayList<String>()
                            areaName.addAll(area)
                            areaName1.add(areaName)
                        }
                        cityNames.add(cityName)
                        areaNames.add(areaName1)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    CrashReport.postCatchedException(e)
                }
            }).start()
    }
}