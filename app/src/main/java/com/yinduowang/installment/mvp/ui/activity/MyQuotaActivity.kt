package com.yinduowang.installment.mvp.ui.activity

import android.content.Intent
import android.os.Bundle
import com.jakewharton.rxbinding3.view.clicks
import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils
import com.yinduowang.installment.R
import com.yinduowang.installment.app.utils.LoadingUtils
import com.yinduowang.installment.di.component.DaggerMyQuotaComponent
import com.yinduowang.installment.di.module.MyQuotaModule
import com.yinduowang.installment.mvp.contract.QuotaContract
import com.yinduowang.installment.mvp.model.entity.QuotaEntity
import com.yinduowang.installment.mvp.presenter.MyQuotaPresenter
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.activity_bank_card.titleBar
import kotlinx.android.synthetic.main.activity_my_quota.*
import java.util.concurrent.TimeUnit


/**
 * ================================================
 * Description:我的额度
 * <p>
 * Created by MVPArmsTemplate on 07/30/2019 19:17
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */

class MyQuotaActivity : BaseActivity<MyQuotaPresenter>(), QuotaContract.View {


    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerMyQuotaComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .myQuotaModule(MyQuotaModule(this))
                .build()
                .inject(this)
    }


    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_my_quota //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }


    override fun initData(savedInstanceState: Bundle?) {
        titleBar.showTitleAndBack(R.string.myquota)
        mPresenter?.let {
            it.getMyQuota()
        }
        tvFindLoanRecoder.clicks().throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(Consumer {
            startActivity(Intent(this, LoanRecordActivity::class.java))
        })

    }


    override fun showLoading() {
        LoadingUtils.show(this)
    }

    override fun hideLoading() {
        LoadingUtils.dismiss(this)
    }

    override fun showMessage(message: String) {
        ArmsUtils.snackbarText(message)
    }

    override fun launchActivity(intent: Intent) {
        ArmsUtils.startActivity(intent)
    }

    override fun killMyself() {
        finish()
    }

    override fun reutrnMyQuota(quotaEntity: QuotaEntity) {
        tvTotalQuota.text = quotaEntity.quota
        tvAvailableQuota.text = quotaEntity.availableQuota
        tvHadUsed.text = quotaEntity.usedQuota
        tvWarn.text = quotaEntity.msg
    }
}
