package com.yinduowang.installment.mvp.model.entity

class InstalmentDetailEntity {

    /**
     * loanId : 5
     * remainingReturned : 1,000.00
     * stageDetailsPlanlist : [{"amountDue":"1,000.00","curStage":3,"demurrage":"1,000.00","id":1,"interest":"1,000.00","overdueState":0,"principal":"1,000.00","repaymentState":0,"repaymentTime":"2019年01月01日","serviceCharge":"1,000.00","stage":6}]
     */

    var loanId: String? = null
    var remainingReturned: String? = null
    var stageDetailsPlanlist: List<StageDetailsPlanlistBean>? = null
    var buttonType: String? = null
    class StageDetailsPlanlistBean {
        /**
         * amountDue : 1,000.00
         * curStage : 3
         * demurrage : 1,000.00
         * id : 1
         * interest : 1,000.00
         * overdueState : 0
         * principal : 1,000.00
         * repaymentState : 0
         * repaymentTime : 2019年01月01日
         * serviceCharge : 1,000.00
         * stage : 6
         */

        var amountDue: String? =""
        var curStage: String? = ""
        var demurrage: String? = ""
        var id: String? = ""
        var interest: String? = ""
        var overdueState: String? = ""
        var principal: String? = ""
        var repaymentState: String? = ""
        var repaymentTime: String? = ""
        var serviceCharge: String? = ""
        var stage: String? = ""
    }
}
