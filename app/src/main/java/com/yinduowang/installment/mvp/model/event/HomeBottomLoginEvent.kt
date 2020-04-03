package com.yinduowang.installment.mvp.model.event

class HomeBottomLoginEvent{
    var doClose : Boolean ? = null

    constructor(doClose: Boolean?) {
        this.doClose = doClose
    }
}