package com.yinduowang.installment.mvp.ui.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils
import com.scwang.smartrefresh.layout.constant.RefreshState
import com.yinduowang.installment.R
import com.yinduowang.installment.app.utils.LoadingUtils
import com.yinduowang.installment.di.component.DaggerBankCardComponent
import com.yinduowang.installment.di.module.BankCardModule
import com.yinduowang.installment.mvp.contract.BankCardContract
import com.yinduowang.installment.mvp.model.entity.UserBank
import com.yinduowang.installment.mvp.presenter.BankCardPresenter
import com.yinduowang.installment.mvp.ui.adapter.BankCardAdapter
import kotlinx.android.synthetic.main.activity_bank_card.*


/**
 * ================================================
 * Description:已绑银行卡列表
 * <p>
 * Created by MVPArmsTemplate on 07/24/2019 14:09
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
class BankCardActivity : BaseActivity<BankCardPresenter>(), BankCardContract.View {



    lateinit var bankCardAdapter: BankCardAdapter
    fun isInitializedBankCardAdapter() = ::bankCardAdapter.isInitialized

    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerBankCardComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .bankCardModule(BankCardModule(this))
                .build()
                .inject(this)
    }


    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_bank_card //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }


    override fun initData(savedInstanceState: Bundle?) {
        // 显示标题 和 返回按钮
        titleBar.showTitleAndBack(R.string.bankcard)
        // 显示右上角 添加图标 点击跳转添加银行卡页面
        titleBar.showRightAddButton(View.OnClickListener { startActivity(Intent(this@BankCardActivity, AddBankCardActivity::class.java)) })
        recyclerView.layoutManager = LinearLayoutManager(this@BankCardActivity)
        // 下拉刷新
        bankSmartRefrsh.setOnRefreshListener {
            // 网络请求
            mPresenter?.getBankCardList()
        }
        bankCardAdapter = BankCardAdapter(arrayListOf())
        val emptyView = LayoutInflater.from(this).inflate(R.layout.layout_empty_bank_card_list, null)
        bankCardAdapter.emptyView = emptyView
        recyclerView.adapter = bankCardAdapter
    }

    override fun getBankCardListSuccess(userBanks: List<UserBank>) {
        Handler().postDelayed({ bankSmartRefrsh.setEnableRefresh(true) },1000)
        if (isInitializedBankCardAdapter()) {
            bankCardAdapter.setNewData(userBanks)
        }
    }

    override fun showLoading() {
        if (bankSmartRefrsh.state == RefreshState.None) {
            LoadingUtils.show(this@BankCardActivity)
        }
    }

    override fun hideLoading() {
        LoadingUtils.dismiss(this@BankCardActivity)
        bankSmartRefrsh.finishRefresh()
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

    override fun onResume() {
        super.onResume()
        bankSmartRefrsh.setEnableRefresh(false)
        mPresenter?.getBankCardList()
    }
}
