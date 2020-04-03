package com.yinduowang.installment.mvp.model.entity;

import java.io.Serializable;

public class LoanSuccessBean implements Serializable {

    private String money_amount;
    private String counter_fee;
    private String loan_term;
    private String detail_url;
    private String apply_date;
    private String interests;
    private DialogBean dialog;

    public String getMoney_amount() {
        return money_amount;
    }

    public void setMoney_amount(String money_amount) {
        this.money_amount = money_amount;
    }

    public String getCounter_fee() {
        return counter_fee;
    }

    public String getInterests() {
        return interests;
    }

    public void setInterests(String interests) {
        this.interests = interests;
    }

    public void setCounter_fee(String counter_fee) {
        this.counter_fee = counter_fee;
    }

    public String getLoan_term() {
        return loan_term;
    }

    public void setLoan_term(String loan_term) {
        this.loan_term = loan_term;
    }

    public String getDetail_url() {
        return detail_url;
    }

    public void setDetail_url(String detail_url) {
        this.detail_url = detail_url;
    }

    public String getApply_date() {
        return apply_date;
    }

    public void setApply_date(String apply_date) {
        this.apply_date = apply_date;
    }

    public DialogBean getDialog() {
        return dialog;
    }

    public void setDialog(DialogBean dialog) {
        this.dialog = dialog;
    }
}
