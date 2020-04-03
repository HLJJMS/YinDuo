package com.yinduowang.installment.mvp.contract

import com.jess.arms.mvp.IView
import com.jess.arms.mvp.IModel


/**
 * @Description:
 * @Author:
 * @Date: 2019-11-13 20:08:45
 * @Version: appVersionName, 2019-11-13
 * @LastEditors:
 * @LastEditTime:
 * @Deprecated: false
 */
interface ArticleDetailsContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View : IView

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model : IModel

}
