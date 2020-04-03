package com.yinduowang.installment.mvp.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.jakewharton.rxbinding3.view.clicks
import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils
import com.yinduowang.installment.R
import com.yinduowang.installment.app.utils.LoadingUtils
import com.yinduowang.installment.app.utils.ToastUtils
import com.yinduowang.installment.app.widget.MeetLabelNewView
import com.yinduowang.installment.di.component.DaggerCollectionComponent
import com.yinduowang.installment.di.module.CollectionModule
import com.yinduowang.installment.mvp.contract.CollectionContract
import com.yinduowang.installment.mvp.model.entity.CollectionEntity
import com.yinduowang.installment.mvp.model.entity.Type
import com.yinduowang.installment.mvp.presenter.CollectionPresenter
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import kotlinx.android.synthetic.main.activity_collection.*
import java.util.concurrent.TimeUnit


/**
 * ================================================
 * Description:催收投诉
 * <p>
 * Created by MVPArmsTemplate on 07/23/2019 13:52
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */

class CollectionActivity : BaseActivity<CollectionPresenter>(), CollectionContract.View {

    /**
     * 反馈意见是否为空
     */
    var typeId = -1
    private var sign1 = false
    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerCollectionComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .collectionModule(CollectionModule(this))
                .build()
                .inject(this)
    }


    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_collection //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }


    @SuppressLint("CheckResult")
    override fun initData(savedInstanceState: Bundle?) {
        titleBar.showTitleAndBack("催收投诉")
        inputFeedback.addTextChangedListener(inputWatcher)

        tvSubmit.clicks()
                .throttleFirst(500, TimeUnit.MILLISECONDS).subscribe {
                    if (typeId == -1) {
                        ToastUtils.makeText(this, "请选择类型")
                        return@subscribe
                    }
                    mPresenter?.let { it.submitCollectionComplains(typeId.toString(), inputFeedback.text.toString().trim()) }
                }
        mPresenter?.let { it.getCollectionComplains() }

    }

    private val inputWatcher = object : TextWatcher {

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            if (inputFeedback.text.length > 160) {
                number.text = "0"
                number.setTextColor((ContextCompat.getColor(baseContext, R.color.color_D0D0D0)))
            } else {
                number.text = inputFeedback.text.length.toString()
                number.setTextColor((ContextCompat.getColor(baseContext, R.color.color_365AF7)))
            }
            sign1 = inputFeedback.text.isNotEmpty()
            tvSubmit.isEnabled = sign1
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun afterTextChanged(s: Editable) {}
    }

    override fun returnColletionYtpe(data: CollectionEntity) {
        var tagAdapter = object : TagAdapter<Type>(data.typeList) {
            override fun getView(parent: FlowLayout, position: Int, bean: Type): View {
                return MeetLabelNewView(
                        View.OnClickListener {},
                        View.OnClickListener {
                            typeId = bean.id
                            val selectedList = tagFlowLayout.selectedList
                            selectedList.clear()
                            selectedList.add(position)
                            setSelectedList(selectedList)
                        },
                        bean.name,
                        this@CollectionActivity)
            }
        }
        tagFlowLayout.adapter = tagAdapter
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
}
