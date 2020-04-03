package com.yinduowang.installment.mvp.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.View
import android.view.View.GONE
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.jakewharton.rxbinding3.view.clicks
import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils
import com.yinduowang.installment.R
import com.yinduowang.installment.app.utils.LoadingUtils
import com.yinduowang.installment.app.utils.PhoneStatusUtils
import com.yinduowang.installment.app.utils.ToastUtils
import com.yinduowang.installment.di.component.DaggerNewCashInstalmentDetailComponent
import com.yinduowang.installment.di.module.NewCashInstalmentDetailModule
import com.yinduowang.installment.mvp.contract.NewCashInstalmentDetailContract
import com.yinduowang.installment.mvp.model.entity.NewCashInstalmentDetailEntity
import com.yinduowang.installment.mvp.model.entity.StageDetailsPlanlistBean
import com.yinduowang.installment.mvp.presenter.NewCashInstalmentDetailPresenter
import com.yinduowang.installment.mvp.ui.adapter.NewCashInstalmentDetailAdapter
import kotlinx.android.synthetic.main.activity_instalment_shop_detail.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

/**
 * Description：分期详情（现金）
 * Author：田羽衡
 * Version：<v1.0，2019/11/1 >
 * LastEditTime：2019/11/1
 * LastEditors:
 * Deprecated： false
 */
class NewCashInstalmentDetailActivity : BaseActivity<NewCashInstalmentDetailPresenter>(), NewCashInstalmentDetailContract.View {
    var id = "" //测试使用
    var buttonType = ""
    var loanId: String = ""
    var type: String = ""
    lateinit var adapter: NewCashInstalmentDetailAdapter
    val list = ArrayList<StageDetailsPlanlistBean>()
    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerNewCashInstalmentDetailComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .newCashInstalmentDetailModule(NewCashInstalmentDetailModule(this))
                .build()
                .inject(this)
    }


    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_instalment_cash_detail //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }


    override fun initData(savedInstanceState: Bundle?) {
        initView()
    }


    fun initView() {
        titleBar.showTitle("分期详情")
        titleBar.setTitleTextColorResources(R.color.white)
        titleBar.showWhiteBack()
        titleBar.setTitleBarBackground(R.color.transparent)
        setHeight();
        intent.getStringExtra("id").let { id = it }
        recycler.layoutManager = LinearLayoutManager(this)
        adapter = NewCashInstalmentDetailAdapter(list)
        recycler.adapter = adapter
        getList()

        tvRepayment.clicks()
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe {
                    mPresenter?.checkRepay("3", loanId)
                }

    }
    /**
     * Description：还款判断
     * Author：田羽衡
     * Version：<v1.0，2019/10/31 10:34>
     * params:
     * return：
     * LastEditTime：2019/10/31 10:34
     * Deprecated： false
     */
    override fun canRepay() {
        if (buttonType.equals("0")) {
            ToastUtils.makeText(this, "您有借款正在还款处理中，请稍后再试")
        } else {
            var intent = Intent(this, PayBackMoneyActivity::class.java)
            intent.putExtra("loanId", loanId)
            intent.putExtra("type", "3")
            startActivity(intent)
        }
    }

    override fun showLoading() {
        LoadingUtils.show(this)
    }

    override fun hideLoading() {
        LoadingUtils.dismiss(this)
    }

    fun setHeight() {
        vZhanwei.layoutParams.height = PhoneStatusUtils.getStatusBarHeight(this);
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


    /**
     * Description：获取列表信息失败
     * Author：田羽衡
     * Version：<v1.0，2019/10/31 10:34>
     * params:
     * return：
     * LastEditTime：2019/10/31 10:34
     * Deprecated： false
     */
    override fun getListFail(msg: String) {
        ToastUtils.makeText(this, msg)
    }


    /**
     * Description：//获取列表信息成功
     * Author：田羽衡
     * Version：<v1.0，2019/10/31 10:34>
     * params:
     * return：
     * LastEditTime：2019/10/31 10:34
     * Deprecated： false
     */
    override fun getListSuccess(bean: NewCashInstalmentDetailEntity) {
        tvRmb.text = bean.remainingReturned
        loanId = bean.loanId.toString()
        type = bean.buttonType.toString()
        buttonType = bean.buttonType.toString()
        if (buttonType.equals("2")) {
            tvRepayment.visibility = GONE
            bg.visibility = GONE
        } else {
            tvRepayment.visibility = View.VISIBLE
            bg.visibility = View.VISIBLE
        }
        adapter.setNewData(getItemList(bean.stageDetailsPlanlist as List<StageDetailsPlanlistBean>))
    }

    fun getList() {
        mPresenter?.getList(id);
    }
    /**
     * Description：格式化列表数据
     * Author：田羽衡
     * Version：<v1.0，2019/10/31 10:34>
     * params:
     * return：
     * LastEditTime：2019/10/31 10:34
     * Deprecated： false
     */
    fun getItemList(stageDetailsPlanlist: List<StageDetailsPlanlistBean>): ArrayList<MultiItemEntity> {
        var adapterList = arrayListOf<MultiItemEntity>()
        stageDetailsPlanlist?.forEach { index ->
            var lv0 = StageDetailsPlanlistBean(index.amountDue, index.curStage, index.demurrage, index.id, index.interest, index.overdueState, index.principal, index.repaymentState, index.repaymentTime, index.serviceCharge, index.stage, index.premium, index.billDetailList)
            lv0.billDetailList?.forEach { it ->
                lv0.addSubItem(it)
            }
            adapterList.add(lv0)
        }
        return adapterList;
    }
}
