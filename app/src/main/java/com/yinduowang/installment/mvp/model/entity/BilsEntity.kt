package com.yinduowang.installment.mvp.model.entity

data class ShopBillsEntity(
        val alreadyPay: String,
        val billMoney: String,
        val isClean: String,
        val isEarly: String,
        val isHave: String,
        val isLast: String,
        val isNext: String,
        val isOverdue: String,
        val isPay: String,
        val isPaying: String,
        val loanSumId: String,
        val month: String,
        val overdueFund: String,
        val repaymentDay: String,
        val residuePay: String,
        val year: String
)

data class NewCashBillsEntity(
        val alreadyPay: String,
        val billMoney: String,
        val isClean: String,
        val isEarly: String,
        val isHave: String,
        val isLast: String,
        val isNext: String,
        val isOverdue: String,
        val isPay: String,
        val isPaying: String,
        val loanSumId: String,
        val month: String,
        val overdueFund: String,
        val repaymentDay: String,
        val residuePay: String,
        val year: String
)

data class CashBillsEntity(
        val billAmount: String,
        val cashBillMonth: String,
        val dealStatus: Int,
        val demurrage: String,
        val id: String,
        val repaidAmount: String,
        val repaymentDate: String,
        val repaymentState: Int,
        val stayRepaymentAmount: String

)

data class BillListEntity(
        val isExistsNextPage: String,
        val year: String,
        val month: String,
        val list: List<ListBean>

)

data class ListBean(
        val goodsName: String,
        val amountDue: String,
        val id: String,
        val loanId: String,
        val stage: String,
        val curStage: String,
        val repaymentState: String,
        val overdueState: String,
        val loanNumber: String,
        val totalAmount: String,
        val demurrage: String,
        val principal: String,
        val interest: String,
        val serviceCharge: String
)


data class ShopAllBillsEntity(
        val billList: List<BillResponse>,
        val isHave: String,
        val isLast: String,
        val isNext: String,
        val year: Int
)

data class BillResponse(
        val alreadyPay: String,
        val billMoney: String,
        val isClean: String,
        val isOverdue: String,
        val isPresentMonth: String,
        val month: String,
        val residuePay: String
)

data class NewCashAllBillsEntity(
        val billList: List<NewCashBillResponse>,
        val isHave: String,
        val isLast: String,
        val isNext: String,
        val year: Int
)

data class NewCashBillResponse(
        val alreadyPay: String,
        val billMoney: String,
        val isClean: String,
        val isOverdue: String,
        val isPresentMonth: String,
        val month: String,
        val residuePay: String
)

data class NewCashBillListEntity(
        val isExistsNextPage: String,
        val year: String,
        val month: String,
        val list: List<ListBean>

)
