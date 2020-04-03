package com.yinduowang.installment.mvp.model.entity

import java.io.Serializable

data class LoanRecordEntity(
        val isExistsNextPage: Int,
        val recordInfoResponses: List<RecordInfoResponse>
)

data class RecordInfoResponse(
        val applicationTime: String,
        val deductStatus: String,
        val loanFund: String,
        val loanId: Int,
        val loanNumber: String,
        val overDueDays: String,
        val statusFlag: String
)

data class CashLoanRecordEntity(
        val bank: String,
        val created: String,
        val demurrage: String,
        val hintInfo: String,
        val loanDays: String,
        val loanFund: String,
        val loanState: String,
        val loanStatus: Int,
        val overdueDays: String,
        val repaidAmount: String,
        val stayRepaymentAmount: String,
        val vipStatus: String,
        val loanSigningResponse: LoanSigning
)

data class LoanSigning(
        val buttonText: String?,
        val buttonType: String?,
        val loanId: String?,
        val textOne: String?,
        val textTwo: String?,
        val type: String?
) : Serializable
data class PayBackRecordEntity(
        val isExistsNextPage: Int,
        val paymentRecordlist: List<PaymentRecord>
)

data class PaymentRecord(
        val repaymentType: String,
        val repaymentFund: String,
        val repaymentDate: String,
        val recordId: Int
)
