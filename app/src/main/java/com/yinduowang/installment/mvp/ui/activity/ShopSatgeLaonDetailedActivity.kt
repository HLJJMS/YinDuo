package com.yinduowang.installment.mvp.ui.activity

import android.content.Intent
import android.os.Bundle

import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.method.LinkMovementMethod
import android.text.style.ForegroundColorSpan
import android.view.View
import androidx.core.content.ContextCompat
import com.blankj.utilcode.util.SPUtils
import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils
import com.yinduowang.installment.R
import com.yinduowang.installment.app.constant.Api
import com.yinduowang.installment.app.constant.SPConstant
import com.yinduowang.installment.app.utils.LoadingUtils
import com.yinduowang.installment.app.utils.NoLineClickable
import com.yinduowang.installment.app.utils.PhoneStatusUtils
import com.yinduowang.installment.app.utils.SignUtils
import com.yinduowang.installment.di.component.DaggerShopSatgeLaonDetailedComponent
import com.yinduowang.installment.di.module.ShopSatgeLaonDetailedModule
import com.yinduowang.installment.mvp.contract.ShopSatgeLaonDetailedContract
import com.yinduowang.installment.mvp.model.entity.LoanShopDetailsEntity
import com.yinduowang.installment.mvp.presenter.ShopSatgeLaonDetailedPresenter
import kotlinx.android.synthetic.main.activity_shop_satge_laon_detailed.*
import java.util.*


/**
 * ================================================
 * Description:我的-借款记录-商城借款详情
 * <p>
 * Created by MVPArmsTemplate on 07/31/2019 14:25
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */

class ShopSatgeLaonDetailedActivity : BaseActivity<ShopSatgeLaonDetailedPresenter>(), ShopSatgeLaonDetailedContract.View {
    var id: String = ""
    val oneAgreement = "《信用服务协议》"
    val twoAgreement = "《失信风险警示》"
    val threeAgreement = "\n《VIP会员增值服务协议》"
    val oneUrl = Api.AGREEMENT_SHOP_CREDIT
    val twoUrl = Api.AGREEMENT_LOST_LETTER
    val threeUrl = Api.VIP_PROXY
    private var agreementColor: Int = 0
    private lateinit var style: SpannableStringBuilder
    private var oneStart: Int = 0
    private var oneEnd: Int = 0
    private var twoEnd: Int = 0
    private var threeEnd: Int = 0
    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerShopSatgeLaonDetailedComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .shopSatgeLaonDetailedModule(ShopSatgeLaonDetailedModule(this))
                .build()
                .inject(this)
    }


    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_shop_satge_laon_detailed //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }


    override fun initData(savedInstanceState: Bundle?) {
        title_xml.showTitleAndBack("借款详情")
        id = intent.getStringExtra("id").toString()
        val map = HashMap<String, String>()
        map["id"] = id
        val sign = SignUtils.getSign(map)
        mPresenter?.getData(SPUtils.getInstance().getString(SPConstant.TOKEN), id, sign)

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

    /**
     * Description：获取数据
     * Author：田羽衡
     * Version：<v1.0，2019/10/31 13:55>
     * params:
     * return：
     * LastEditTime：2019/10/31 13:55
     * Deprecated： false
     */
    override fun setData(bean: LoanShopDetailsEntity) {
        rmb.setText(bean?.loanFund)
        name_txt.setText(bean?.commodityName)
        pay_txt.setText(bean?.payMethod)
        time_txt.setText(bean?.applicationTime)
        market_txt.setText(bean?.orderNo)
        detail.setText(bean?.loanMsg)
//        借款详情状态标识1待还款,2借款已还清,3借款已逾期,4还款中 ,5审核中6审核未通过 ,
        if (bean.statusFlag.equals("1")) {
            img.visibility = View.GONE
        } else if (bean.statusFlag.equals("2")) {
            img.setImageResource(R.mipmap.ic_loan_end)
            img.visibility = View.VISIBLE
        } else if (bean.statusFlag.equals("3")) {
            img.setImageResource(R.mipmap.ic_loan_outtime)
            img.visibility = View.VISIBLE
        } else if (bean.statusFlag.equals("6")) {
            img.setImageResource(R.mipmap.ic_loan_nopass)
            img.visibility = View.VISIBLE
        } else {
            img.visibility = View.GONE
        }
        initWebUrlEntity(bean.vipStatus.toString())
    }


    /**
     * Description：协议相关内荣
     * Author：田羽衡
     * Version：<v1.0，2019/10/31 13:56>
     * params:
     * return：
     * LastEditTime：2019/10/31 13:56
     * Deprecated： false
     */
    private fun initWebUrlEntity(vipStatus: String) {
        agreementColor = ContextCompat.getColor(this, R.color.colorAccent)
        oneEnd = oneAgreement.length
        twoEnd = oneAgreement.length + twoAgreement.length
        threeEnd = twoEnd + threeAgreement.length
        if (vipStatus.equals("0")) {
            style = SpannableStringBuilder(oneAgreement + twoAgreement + threeAgreement)
        } else {
            style = SpannableStringBuilder(oneAgreement + twoAgreement)
        }
        initAgreementText(vipStatus)
    }

    /**
     * Description：设置协议颜色和点击事件
     * Author：田羽衡
     * Version：<v1.0，2019/10/31 13:56>
     * params:
     * return：
     * LastEditTime：2019/10/31 13:56
     * Deprecated： false
     */
    private fun initAgreementText(vipStatus: String) {
        setAgreementTextClickAndColorOne()
        setAgreementTextClickAndColorTwo()
        if (vipStatus.equals("0")) {
            setAgreementTextClickAndColorThree()
        }
        tvAgreement_txt.text = style
        tvAgreement_txt.movementMethod = LinkMovementMethod.getInstance()
    }

    /**
     * Description：第一个协议点击
     * Author：田羽衡
     * Version：<v1.0，2019/10/31 13:56>
     * params:
     * return：
     * LastEditTime：2019/10/31 13:56
     * Deprecated： false
     */
    private fun setAgreementTextClickAndColorOne() {
        style.setSpan(NoLineClickable(View.OnClickListener {
            openAgreement(oneUrl)
        }), oneStart, oneEnd, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
        style.setSpan(ForegroundColorSpan(agreementColor), oneStart, oneEnd, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
    }

    /**
     * Description：第二个协议点击
     * Author：田羽衡
     * Version：<v1.0，2019/10/31 13:56>
     * params:
     * return：
     * LastEditTime：2019/10/31 13:56
     * Deprecated： false
     */
    private fun setAgreementTextClickAndColorTwo() {
        style.setSpan(NoLineClickable(View.OnClickListener {
            openAgreementTwo(twoUrl)
        }), oneEnd, twoEnd, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
        style.setSpan(ForegroundColorSpan(agreementColor), oneEnd, twoEnd, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
    }

    /**第三个协议点击
     * Description：
     * Author：田羽衡
     * Version：<v1.0，2019/10/31 13:57>
     * params:
     * return：
     * LastEditTime：2019/10/31 13:57
     * Deprecated： false
     */
    private fun setAgreementTextClickAndColorThree() {
        style.setSpan(NoLineClickable(View.OnClickListener {
            openAgreementThree(threeUrl)
        }), twoEnd, threeEnd, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
        style.setSpan(ForegroundColorSpan(agreementColor), twoEnd, threeEnd, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
    }

    /**
     * Description：打开协议界面
     * Author：田羽衡
     * Version：<v1.0，2019/10/31 13:57>
     * params:
     * return：
     * LastEditTime：2019/10/31 13:57
     * Deprecated： false
     */
    private fun openAgreement(url: String) {
        val intent = Intent(this, CommWebViewActivity::class.java)
        intent.putExtra(CommWebViewActivity.KEY_URL_NAME, url)
        intent.putExtra(CommWebViewActivity.KEY_JAVASCRIPT_TYPE, CommWebViewActivity.TYPE_JAVASCRIPT_JEL_LE_ME)
        intent.putExtra(CommWebViewActivity.KEY_IS_AUTHENTICATION, CommWebViewActivity.TYPE_TITLE_NORMAL)
        val map = HashMap<String, String>()
        map["id"] = id
        intent.putExtra(CommWebViewActivity.KEY_URL_PARAMS, map)
        startActivity(intent)
    }

    /**
     * Description：打开协议界面
     * Author：田羽衡
     * Version：<v1.0，2019/10/31 13:57>
     * params:
     * return：
     * LastEditTime：2019/10/31 13:57
     * Deprecated： false
     */
    private fun openAgreementTwo(url: String) {
        val intent = Intent(this, CommWebViewActivity::class.java)
        intent.putExtra(CommWebViewActivity.KEY_URL_NAME, url)
        intent.putExtra(CommWebViewActivity.KEY_JAVASCRIPT_TYPE, CommWebViewActivity.TYPE_JAVASCRIPT_JEL_LE_ME)
        intent.putExtra(CommWebViewActivity.KEY_IS_AUTHENTICATION, CommWebViewActivity.TYPE_TITLE_NORMAL)
        startActivity(intent)
    }

    /**
     * Description：打开协议界面
     * Author：田羽衡
     * Version：<v1.0，2019/10/31 13:57>
     * params:
     * return：
     * LastEditTime：2019/10/31 13:57
     * Deprecated： false
     */
    private fun openAgreementThree(url: String) {
        val intent = Intent(this, CommWebViewActivity::class.java)
        intent.putExtra(CommWebViewActivity.KEY_URL_NAME, url)
        intent.putExtra(CommWebViewActivity.KEY_JAVASCRIPT_TYPE, CommWebViewActivity.TYPE_JAVASCRIPT_JEL_LE_ME)
        intent.putExtra(CommWebViewActivity.KEY_IS_AUTHENTICATION, CommWebViewActivity.TYPE_TITLE_NORMAL)
        val map = HashMap<String, String>()
        map["id"] = id
        intent.putExtra(CommWebViewActivity.KEY_URL_PARAMS, map)
        startActivity(intent)
    }
}
