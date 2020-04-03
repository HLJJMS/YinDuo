package com.yinduowang.installment.mvp.model.service

import com.yinduowang.installment.app.constant.Api
import com.yinduowang.installment.mvp.model.entity.BaseResponse
import io.reactivex.Observable
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import java.util.*

interface PublicHttpService {

    /**
     * 检查是否可以还款
     *
     * @param type 1分期账单提前还款,2分期账单到期还款,3分期详情全部还款
     * */
    @FormUrlEncoded
    @POST(Api.REPAY_PAGE_CHECK_REPAY)
    fun checkRepay(@FieldMap map: HashMap<String, String>): Observable<BaseResponse<Any>>
}