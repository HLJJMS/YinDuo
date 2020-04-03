package com.yinduowang.installment.mvp.ui.activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import androidx.core.content.ContextCompat
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.alibaba.security.rp.RPSDK
import com.alibaba.security.rp.RPSDK.AUDIT
import com.alibaba.security.rp.RPSDK.RPCompletedListener
import com.amap.api.location.AMapLocationListener
import com.bigkoo.pickerview.listener.OnOptionsSelectListener
import com.jakewharton.rxbinding3.view.clicks
import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils
import com.jess.arms.utils.PermissionUtil
import com.tbruyelle.rxpermissions2.RxPermissions
import com.yinduowang.installment.R
import com.yinduowang.installment.app.utils.LoadImageUtils
import com.yinduowang.installment.app.utils.LoadingUtils
import com.yinduowang.installment.app.utils.PikerUtils
import com.yinduowang.installment.app.utils.ToastUtils
import com.yinduowang.installment.app.utils.amap.LocationUtils
import com.yinduowang.installment.app.utils.gosetting.PermissionSetting
import com.yinduowang.installment.app.widget.BaseDialog
import com.yinduowang.installment.app.widget.popwindow.BottomPopupWindow
import com.yinduowang.installment.di.component.DaggerPersonalDetailComponent
import com.yinduowang.installment.di.module.PersonalDetailModule
import com.yinduowang.installment.mvp.contract.PersonalDetailContract
import com.yinduowang.installment.mvp.model.entity.UserInfoAllEntity
import com.yinduowang.installment.mvp.presenter.PersonalDetailPresenter
import kotlinx.android.synthetic.main.activity_personal_detail.*
import java.util.concurrent.TimeUnit


/**
 * ================================================
 * Description:个人信息认证
 * <p>
 * Created by MVPArmsTemplate on 07/30/2019 09:00
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
class PersonalDetailActivity : BaseActivity<PersonalDetailPresenter>(), PersonalDetailContract.View {

    lateinit var userInfoAllEntity: UserInfoAllEntity
    // 是否完成了个人信息认证
    var isAuthentication: Boolean = false

    // 人脸识别 图片地址
    var faceUrl: String = ""
    // 身份证正面地址
    var identificationFrontUrl: String = ""
    // 身份证反面地址
    var identificationReverseUrl: String = ""

    lateinit var baseDialog: BaseDialog

    var etHomeAddressText: String = ""
    var gpsAddress = ""

    var popupWindowBottom: BottomPopupWindow? = null
    var twoButtonPopupWindow: BottomPopupWindow? = null
    lateinit var locationUtils: LocationUtils


    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerPersonalDetailComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .personalDetailModule(PersonalDetailModule(this))
                .build()
                .inject(this)
    }


    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_personal_detail //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }


    override fun initData(savedInstanceState: Bundle?) {
        titleBar.showTitleAndBack(R.string.personal_information, View.OnClickListener { finishPage() })
        mPresenter?.getUserInfoAll()
        isAuthentication = intent.getBooleanExtra("isAuthentication", false)
        initView()
        initClick()
        // 初始化城市列表
        PikerUtils.initCityList(this)
        // 初始化弹窗
        initAlertView()

        locationUtils = LocationUtils()
        locationUtils.startLocation()
        locationUtils.initLocation(this, AMapLocationListener { aMapLocation ->
            if (aMapLocation.errorCode == 0) {
                gpsAddress = aMapLocation.address
            }
        })
    }

    /**
     * 关闭页面 判断是否需要弹出保存弹出
     * */
    private fun finishPage() {
        if (isNotEmpty() && isChange()) {
            if (::baseDialog.isInitialized) {
                baseDialog.show()
            }
        } else {
            killMyself()
        }
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

    override fun onResume() {
        super.onResume()
    }

    fun initView() {
        popupWindowBottom = BottomPopupWindow(this, true)
        twoButtonPopupWindow = BottomPopupWindow(this, false)
    }

    private fun initBtn() {
        if (isAuthentication) {
            tvBtn.isEnabled = true
        } else {
            tvBtn.isEnabled = identificationFrontUrl.isNotEmpty() &&
                    identificationReverseUrl.isNotEmpty() &&
                    faceUrl.isNotEmpty() &&
                    tvCardName.text.toString().isNotEmpty() &&
                    tvCardNumber.text.toString().isNotEmpty() &&
                    tvDegree.text.toString().isNotEmpty() &&
                    tvHomeArea.text.toString().isNotEmpty() &&
                    etHomeAddress.text.toString().isNotEmpty()
        }
    }

    @SuppressLint("CheckResult")
    fun initClick() {
        // 身份证正面 点击事件
        ivIdCard.clicks().throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe {
                    requestPermission(false, 0)
//                    showActionSheet(false, 0)
                }
        // 身份证背面 点击事件
        ivIdCardTwo.clicks().throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe {
                    requestPermission(false, 1)
//                    showActionSheet(false, 1)
                }
        // 人脸识别图片 点击事件
        ivFace.clicks().throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe {
                    requestPermission(true, 0)
//                    showActionSheet(true, 0)
                }
        // 学历 点击事件
        llDegree.clicks().throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe {
                    hideInput()
                    showDegreePicker()
                }
        // 现居地址 点击事件
        llHomeArea.clicks().throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe {
                    hideInput()
                    showCityPicker()
                }
        // 详细地址 输入内容监听
        etHomeAddress.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                initBtn()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })

        etHomeAddress.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
//                有交点
                etHomeAddress.setText(etHomeAddressText)
            } else {
                etHomeAddressText = etHomeAddress.text.toString()
//                无焦点
                if (etHomeAddressText.length > 15) {
                    etHomeAddress.setText(etHomeAddressText.subSequence(0, etHomeAddressText.length - 3).toString() + "...")
                } else {
                    etHomeAddress.setText(etHomeAddressText)
                }
            }
        }


        // 现居时长 点击事件
        llLiveTime.clicks().throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe {
                    showLiveTimePicker()
                }
        // 婚姻状况 点击事件
        llMarriage.clicks().throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe {
                    showMarragePicker()
                }
        // 保存按钮 点击事件
        tvBtn.clicks().throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe {
                    save()
                }
    }


    //    判断使用哪个值作为详细地址（当etHomeAddress.text长度于etHomeAddressText长度相等时证明 etHomeAddressText 准确，反之etHomeAddress准确）
    fun getAddressText(): String {
        if (etHomeAddress.text.length == etHomeAddressText.length) {
            return etHomeAddressText
        } else {
            return etHomeAddress.text.toString()
        }
    }


    fun showThreeButtonPopup(isFace: Boolean, clickPosition: Int) {
        popupWindowBottom!!.show()
        popupWindowBottom!!.setOneButtontText("查看大图")
        popupWindowBottom!!.setTwoButtontText("进行认证")
        popupWindowBottom!!.setCancelButtontText("取消",true)
        popupWindowBottom!!.setOnButtonClick(object : BottomPopupWindow.ButtonClick {
            override fun setClick(id: Int) {
                var urls = arrayListOf<String>()
                when (id) {
                    BottomPopupWindow.ONE_BUTTON -> {
                        if (isFace) {
                            urls.add(faceUrl)
                        } else {
                            if (clickPosition == 0)
                                urls.add(identificationFrontUrl) else
                                urls.add(identificationReverseUrl)
                        }
                        showImage(urls, 0)
                    }
                    BottomPopupWindow.TWO_BUTTON -> {
                        toVerify()
                    }

                }
            }
        })
    }

    fun showTwoButtonPopup(isFace: Boolean, clickPosition: Int, txt: String, type: Int) {
        twoButtonPopupWindow!!.show()
        twoButtonPopupWindow!!.setOneButtontText(txt)
        twoButtonPopupWindow!!.setCancelButtontText("取消", true)
        twoButtonPopupWindow!!.setOnButtonClick(

                object : BottomPopupWindow.ButtonClick {
                    override fun setClick(id: Int) {
                        var urls = arrayListOf<String>()
                        if (id == BottomPopupWindow.ONE_BUTTON ) {
                            if (type == 1) {
                                toVerify()
                            } else {
                                if (isFace) {
                                    urls.add(faceUrl)
                                } else {
                                    if (clickPosition == 0)
                                        urls.add(identificationFrontUrl) else
                                        urls.add(identificationReverseUrl)
                                }
                                showImage(urls, 0)
                            }
                        }
                    }

                })
    }

    private fun showActionSheet(isFace: Boolean, clickPosition: Int) {
        if (isAuthentication) {
            showTwoButtonPopup(isFace, clickPosition, "查看大图", 0)
        } else {
            if (faceUrl.isNotEmpty()) {
                showThreeButtonPopup(isFace, clickPosition)
            } else {
                showTwoButtonPopup(isFace, clickPosition, "进行认证", 1)
            }
        }
    }

    /**
     * 跳转至人脸识别
     */
    private fun toVerify() {
        mPresenter?.getVerifyRPBasicToken()
    }

    private fun showImage(urls: ArrayList<String>, clickPosition: Int) {
        val intent = Intent(this@PersonalDetailActivity, ImageDetailActivity::class.java)
        intent.putStringArrayListExtra("urls", urls)
        intent.putExtra("position", clickPosition)
        startActivity(intent)
    }

    /**
     * 居住时长选择器
     * */
    private fun showLiveTimePicker() {
        if (::userInfoAllEntity.isInitialized) {
            val list = ArrayList<Any>()
            for (bean in userInfoAllEntity.liveTimeList) {
                list.add(bean.name)
            }
            PikerUtils.showOptionsPicker(this, resources.getString(R.string.selection_of_residence), list, OnOptionsSelectListener { options1, options2, options3, v ->
                tvLiveTime.text = list[options1] as String
                initBtn()
            })
        }
    }

    /**
     * 婚姻状况选择器
     * */
    private fun showMarragePicker() {
        if (::userInfoAllEntity.isInitialized) {
            val list = ArrayList<Any>()
            for (bean in userInfoAllEntity.maritalList) {
                list.add(bean.name)
            }
            PikerUtils.showOptionsPicker(this, resources.getString(R.string.selection_of_marital_status), list, OnOptionsSelectListener { options1, options2, options3, v ->
                tvMarriage.text = list[options1] as String
                initBtn()
            })
        }
    }

    private fun hideInput() {
        val view = this@PersonalDetailActivity.currentFocus
        if (view != null) {
            val inputMethodManager = this@PersonalDetailActivity.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }

    /**
     * 学历选择器
     */
    private fun showDegreePicker() {
        if (::userInfoAllEntity.isInitialized) {
            val list = ArrayList<Any>()
            for (bean in userInfoAllEntity.educationList) {
                list.add(bean.name)
            }

            PikerUtils.showOptionsPicker(this, resources.getString(R.string.selection_of_education), list, OnOptionsSelectListener { options1, options2, options3, v ->
                tvDegree.text = list[options1] as String
                initBtn()
            })
        }
    }


    /**
     * 城市选择器
     * */
    private fun showCityPicker() {
        if (PikerUtils.isInitCityList()) {
            PikerUtils.showCityPicker(this, resources.getString(R.string.selection_of_areas), object : PikerUtils.OnCitySelectListener {
                @SuppressLint("SetTextI18n")
                override fun onOptionsSelect(options1: String, options2: String, options3: String, v: View?) {
                    tvHomeArea.text = "$options1 $options2 $options3"
                    initBtn()
                }
            })
        } else {
            ToastUtils.makeText(this, resources.getString(R.string.selection_of_areas_error))
        }
    }

    fun requestPermission(isFace: Boolean, clickPosition: Int) {
        PermissionUtil.requestPermission(
                object : PermissionUtil.RequestPermission {
                    override fun onRequestPermissionSuccess() {
                        showActionSheet(isFace, clickPosition)
                    }

                    override fun onRequestPermissionFailure(permissions: List<String>) {
                        requestPermission(isFace, clickPosition)
                    }

                    override fun onRequestPermissionFailureWithAskNeverAgain(permissions: List<String>) {
                        //去设置相机弹窗样式
                        if (permissions[0] == Manifest.permission.CAMERA) {
                            showMsgFailureCameraDialog(this@PersonalDetailActivity)
                        } else {
                            PermissionSetting.showFailureWithAskNeverAgainDialog(this@PersonalDetailActivity, permissions[0])
                        }
                    }
                },
                RxPermissions(this),
                mPresenter?.mErrorHandler,
                Manifest.permission.CAMERA)
    }

    fun showMsgFailureCameraDialog(context: Context) {
        BaseDialog(context)
                .builder()
                .setTitle("需要打开您的相机权限！")
                .setCanceledOnTouchOutside(false)
                .setTitlePadding(0f, 22f,
                        0f, 26f)
                .setBtnLeft("取消", 16f, ContextCompat.getColor(context, R.color.color_9A9A9A))
                .setBtnRight("去设置", 16f, ContextCompat.getColor(context, R.color.color_365AF7), Typeface.NORMAL, object : BaseDialog.BaseDialogBuilder.BtnClickListener {
                    override fun onClick(dialog: BaseDialog) {
                        PermissionSetting.toPermissionSetting(context)
                    }
                })
                .create()
                .show()
    }

    /**
     * 根据接口返回数据 初始化页面显示内容
     * */
    override fun getUserInfoAll(userInfoAllEntity: UserInfoAllEntity) {
        this.userInfoAllEntity = userInfoAllEntity
        if (!userInfoAllEntity.identificationFrontUrl.isNullOrEmpty()) {
            identificationFrontUrl = userInfoAllEntity.identificationFrontUrl
            LoadImageUtils.showImage(this, userInfoAllEntity.identificationFrontUrl, ivIdCard, R.mipmap.ic_default_idcard_z)
        }
        if (!userInfoAllEntity.identificationReverseUrl.isNullOrEmpty()) {
            identificationReverseUrl = userInfoAllEntity.identificationReverseUrl
            LoadImageUtils.showImage(this, userInfoAllEntity.identificationReverseUrl, ivIdCardTwo, R.mipmap.ic_default_idcard_f)
        }
        if (!userInfoAllEntity.faceUrl.isNullOrEmpty()) {
            faceUrl = userInfoAllEntity.faceUrl
            LoadImageUtils.showImage(this, userInfoAllEntity.faceUrl, ivFace, R.mipmap.ic_default_face)
        }
        if (!userInfoAllEntity.username.isNullOrEmpty()) {
            tvCardName.text = userInfoAllEntity.username
        }
        if (!userInfoAllEntity.idCard.isNullOrEmpty()) {
            tvCardNumber.text = userInfoAllEntity.idCard
        }
        if (!userInfoAllEntity.education.isNullOrEmpty()) {
            tvDegree.text = userInfoAllEntity.education
        }
        for (entity in userInfoAllEntity.educationList) {
            if (entity.id.toString() == userInfoAllEntity.education) {
                tvDegree.text = entity.name
            }
        }
        if (!userInfoAllEntity.currentAddress.isNullOrEmpty()) {
            tvHomeArea.text = userInfoAllEntity.currentAddress
        }
        if (!userInfoAllEntity.detailedAddress.isNullOrEmpty()) {
            etHomeAddressText = userInfoAllEntity.detailedAddress
            if (userInfoAllEntity.detailedAddress.length > 15) {
                etHomeAddress.setText(etHomeAddressText.subSequence(0, etHomeAddressText.length - 3).toString() + "...")
            } else {
                etHomeAddress.setText(etHomeAddressText)
            }

        }
        if (!userInfoAllEntity.liveTime.isNullOrEmpty()) {
            tvLiveTime.text = userInfoAllEntity.liveTime
        }
        for (entity in userInfoAllEntity.liveTimeList) {
            if (entity.id.toString() == userInfoAllEntity.liveTime) {
                tvLiveTime.text = entity.name
            }
        }
        for (entity in userInfoAllEntity.maritalList) {
            if (entity.id.toString() == userInfoAllEntity.marital) {
                tvMarriage.text = entity.name
            }
        }

        initBtn()
    }


    /**
     * 判断个人信息内容 必填项是否都填写了
     * */
    private fun isNotEmpty(): Boolean {
        if (identificationFrontUrl.isNotEmpty() && identificationReverseUrl.isNotEmpty() && faceUrl.isNotEmpty() &&
                tvCardName.text.toString().isNotEmpty() && tvCardNumber.text.toString().isNotEmpty() &&
                tvDegree.text.toString().isNotEmpty() && tvHomeArea.text.toString().isNotEmpty() && etHomeAddress.text.toString().isNotEmpty()) {
            return true
        }
        return false
    }

    /**
     * 物理返回键监听
     * */
    override fun onBackPressed() {
        finishPage()
    }

    override fun onVerifyRPBasicTokenResult(aliyunToken: String) {
        try {
            RPSDK.start(aliyunToken, this@PersonalDetailActivity, object : RPCompletedListener {
                override fun onAuditResult(p0: AUDIT?, p1: String?) {
                    when (p0) {
                        AUDIT.AUDIT_PASS -> //认证通过
                            mPresenter!!.callbackVerifyRPBasic(aliyunToken)
                        AUDIT.AUDIT_FAIL -> //认证不通过
                            mPresenter!!.callbackVerifyRPBasic(aliyunToken)
                        AUDIT.AUDIT_NOT -> //未认证，通常是用户主动退出或者姓名身份证号实名校验不匹配等原因，导致未完成认证流程
                            when (p1) {
                                "3001" -> // 未完成认证，原因是：认证token无效或已过期
                                    ToastUtils.makeText(this@PersonalDetailActivity, "未认证，用户取消")
                                "3101", // 未完成认证，原因是：操作太频繁
                                "3102", // 未完成认证，原因是：认证已通过，重复提交
                                "3103", // 未完成认证，原因是：实名校验身份证号不合法
                                "3104", // 未完成认证，原因是：实名校验身份证号不存在
                                "3204" ->// 未完成认证，原因是：用户姓名身份证实名校验不匹配
                                    mPresenter!!.callbackVerifyRPBasic(aliyunToken)
                            }
                    }
                }
            })
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun callBackVerifyRPBasicResult(map: Map<String, String>) {
        mPresenter?.getUserInfoAll()
    }

    /**
     * 判断个人信息内容 是否改变
     * */
    private fun isChange(): Boolean {
        if (::userInfoAllEntity.isInitialized) {
            if (identificationFrontUrl != userInfoAllEntity.identificationFrontUrl ||
                    identificationReverseUrl != userInfoAllEntity.identificationReverseUrl ||
                    faceUrl != userInfoAllEntity.faceUrl ||
                    tvCardName.text.toString() != userInfoAllEntity.username ||
                    tvCardNumber.text.toString() != userInfoAllEntity.idCard ||
                    getEducation() != userInfoAllEntity.education ||
                    tvHomeArea.text.toString() != userInfoAllEntity.currentAddress ||
                    getAddressText() != userInfoAllEntity.detailedAddress ||
//                    etHomeAddress.text.toString() != userInfoAllEntity.detailedAddress ||
                    getLiveTime() != userInfoAllEntity.liveTime ||
                    getMarital() != userInfoAllEntity.marital) {
                return true
            }
        }
        return false
    }


    /**
     * 初始化Alert
     * */
    private fun initAlertView() {
        baseDialog = BaseDialog(this)
                .builder()
                .setCancelable(false)
                .setTitle("是否要保存修改？")
                .setBtnLeft("否", object : BaseDialog.BaseDialogBuilder.BtnClickListener {
                    override fun onClick(dialog: BaseDialog) {
                        finish()
                    }
                })
                .setBtnRight("是", object : BaseDialog.BaseDialogBuilder.BtnClickListener {
                    override fun onClick(dialog: BaseDialog) {

                        save()
                    }
                })
                .create()

    }

    /**
     * 保存个人信息
     * */
    fun save() {
        var map = hashMapOf<String, String>()
        map["realName"] = tvCardName.text.toString()
        map["idCardNo"] = tvCardNumber.text.toString()
        map["education"] = getEducation()
        map["residenceTime"] = getLiveTime()
        map["maritalStatus"] = getMarital()
        map["detailedAddress"] = getAddressText()
        map["currentAddress"] = tvHomeArea.text.toString()
        map["gpsAddress"] = gpsAddress
        mPresenter?.upadateUserinfo(map)
    }

    private fun getMarital(): String {
        for (entity in userInfoAllEntity.maritalList) {
            if (entity.name == tvMarriage.text.toString()) {
                return entity.id.toString()
            }
        }
        return ""
    }

    private fun getLiveTime(): String {
        for (entity in userInfoAllEntity.liveTimeList) {
            if (entity.name == tvLiveTime.text.toString()) {
                return entity.id.toString()
            }
        }
        return ""
    }

    private fun getEducation(): String {
        for (entity in userInfoAllEntity.educationList) {
            if (entity.name == tvDegree.text.toString()) {
                return entity.id.toString()
            }
        }
        return ""
    }

    override fun upadateUserinfoResult() {
        BaseDialog(this)
                .builder()
                .setCancelable(false)
                .setTitlePaddingTop(22f)
                .setTitlePaddingBottom(23f)
                .setTitleLineSpacingExtra(25f)
                .setTitle("保存成功")
                .setBtnRight("确定", object : BaseDialog.BaseDialogBuilder.BtnClickListener {
                    override fun onClick(dialog: BaseDialog) {
                        killMyself()
                    }
                })
                .create()
                .show()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::locationUtils.isInitialized)
            locationUtils.destroyLocation()
    }
}
