package com.yinduowang.installment.mvp.ui.activity

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.Toast
import com.bigkoo.pickerview.listener.OnOptionsSelectListener
import com.blankj.utilcode.util.SPUtils
import com.jakewharton.rxbinding3.view.clicks
import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils
import com.jess.arms.utils.PermissionUtil
import com.tbruyelle.rxpermissions2.RxPermissions
import com.yinduowang.installment.R
import com.yinduowang.installment.app.constant.Constant
import com.yinduowang.installment.app.constant.SPConstant
import com.yinduowang.installment.app.utils.*
import com.yinduowang.installment.app.utils.gosetting.PermissionSetting
import com.yinduowang.installment.di.component.DaggerAuthEmergencyContactComponent
import com.yinduowang.installment.di.module.AuthEmergencyContactModule
import com.yinduowang.installment.mvp.contract.AuthEmergencyContactContract
import com.yinduowang.installment.mvp.model.entity.AuthEmergencyContactEntity
import com.yinduowang.installment.mvp.presenter.AuthEmergencyContactPresenter
import com.yinduowang.installment.mvp.ui.service.UploadService
import kotlinx.android.synthetic.main.activity_auth_emergency_contact.*
import java.util.concurrent.TimeUnit


/**
 * Description：紧急联系人activity
 * Author：田羽衡
 * Version：<v1.0，2019/11/1 >
 * LastEditTime：2019/11/1
 * LastEditors:
 * Deprecated： false
 */
class AuthEmergencyContactActivity : BaseActivity<AuthEmergencyContactPresenter>(), AuthEmergencyContactContract.View {


    private lateinit var contactList: MutableList<String>
    private val REQUEST_CODE = 1

    private var select_contact_name = ""
    private var select_contact_phone = ""
    private var select_contact_name2 = ""
    private var select_contact_phone2 = ""


    private var otherType = ""
    private var familyType = ""


    lateinit var entity: AuthEmergencyContactEntity

    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerAuthEmergencyContactComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .authEmergencyContactModule(AuthEmergencyContactModule(this))
                .build()
                .inject(this)
    }


    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_auth_emergency_contact //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }


    override fun initData(savedInstanceState: Bundle?) {
        titleBar.showTitleAndBack(R.string.emergent)
        initClick()
        mPresenter?.getData()
    }

    @SuppressLint("CheckResult")
    fun initClick() {
        contact.clicks()
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe {
                    if (::entity.isInitialized) {
                        var list = arrayListOf<Any>()
                        for (familyEntity in entity.dictionary.familyContactsList) {
                            list.add(familyEntity.name)
                        }
                        PikerUtils.showOptionsPicker(this, "选择关系", list, OnOptionsSelectListener { options1, options2, options3, v ->
                            contact_txt.text = list[options1] as String
                            familyType = entity.dictionary.familyContactsList[options1].id.toString()
                            initBtn()
                        })
                    }
                }
        name.clicks()
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe {
                    getPermission(Constant.CODE_GET_CONTACT)
                }
        contact_other.clicks()
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe {
                    if (::entity.isInitialized) {
                        var list = arrayListOf<Any>()
                        for (otherEntity in entity.dictionary.otherContactsList) {
                            list.add(otherEntity.name)
                        }
                        PikerUtils.showOptionsPicker(this, "选择关系", list, OnOptionsSelectListener { options1, options2, options3, v ->
                            contact_other_txt.text = list[options1] as String
                            otherType = entity.dictionary.otherContactsList[options1].id.toString()
                            initBtn()
                        })
                    }
                }
        name_other.clicks()
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe {
                    getPermission(Constant.CODE_GET_CONTACT2)
                }
        ok_bt.clicks()
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe {
                    val map = hashMapOf<String, String>()
                    map["familyType"] = familyType
                    map["familyName"] = select_contact_name
                    map["familyMobile"] = select_contact_phone
                    map["otherType"] = otherType
                    map["otherName"] = select_contact_name2
                    map["otherMobile"] = select_contact_phone2
                    mPresenter?.saveContacts(map)
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
    /**
     * Description：刚进入此页面时，从后台传来的数据填入到页面
     * Author：田羽衡
     * Version：<v1.0，2019/10/31 10:39>
     * params:
     * return：
     * LastEditTime：2019/10/31 10:39
     * Deprecated： false
     */
    override fun getData(entity: AuthEmergencyContactEntity) {
        this.entity = entity
        contact_txt.text = entity.contacts.familyTypeName
        name_txt.text = entity.contacts.familyName
        contact_other_txt.text = entity.contacts.otherTypeName
        name_other_txt.text = entity.contacts.otherName
        familyType = entity.contacts.familyType.toString()
        select_contact_name = entity.contacts.familyName
        select_contact_phone = entity.contacts.familyMobile
        otherType = entity.contacts.otherType.toString()
        select_contact_name2 = entity.contacts.otherName
        select_contact_phone2 = entity.contacts.otherMobile
        initBtn()
    }

    /**
     * Description：设置按钮是否可以点击
     * Author：田羽衡
     * Version：<v1.0，2019/10/31 10:40>
     * params:
     * return：
     * LastEditTime：2019/10/31 10:40
     * Deprecated： false
     */
    private fun initBtn() {
        ok_bt.isEnabled = contact_txt.text.toString().isNotEmpty() && name_txt.text.toString().isNotEmpty() &&
                contact_other_txt.text.toString().isNotEmpty() && name_other_txt.text.toString().isNotEmpty()
    }


    /**
     * Description：///获取通讯录权限
     * Author：田羽衡
     * Version：<v1.0，2019/10/31 10:40>
     * params:
     * return：
     * LastEditTime：2019/10/31 10:40
     * Deprecated： false
     */
    fun getPermission(code: Int) {
        PermissionUtil.requestPermission(
                object : PermissionUtil.RequestPermission {
                    override fun onRequestPermissionSuccess() {
                        val oldMillis = SPUtils.getInstance().getLong(SPConstant.UPLOAD_DATA_TIME)
                        val newMillis = System.currentTimeMillis()
                        val difference = (newMillis - oldMillis) > 1000 * 60 * 60 * 24
                        if (difference) {
                            SPUtils.getInstance().put(SPConstant.UPLOAD_DATA_TIME, newMillis)
                            // 上传 联系人
                            UploadService.uploadUserInfo(this@AuthEmergencyContactActivity, UploadService.UPLOAD_TYPE_ADDRESS_BOOK)
                        }
                        val intent = Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI)
                        intent.setType(ContactsContract.Contacts.CONTENT_TYPE)
                        startActivityForResult(intent, code)
                    }

                    override fun onRequestPermissionFailure(permissions: List<String>) {
                        getPermission(code)
                    }

                    override fun onRequestPermissionFailureWithAskNeverAgain(permissions: List<String>) {
                        PermissionSetting.showFailureWithAskNeverAgainDialog(this@AuthEmergencyContactActivity, permissions[0])
                    }
                },
                RxPermissions(this),
                mPresenter?.mErrorHandler,
                Manifest.permission.READ_CONTACTS)
    }


    /**
     * Description：选择联系人 返回结果
     * Author：田羽衡
     * Version：<v1.0，2019/10/31 10:40>
     * params:
     * return：
     * LastEditTime：2019/10/31 10:40
     * Deprecated： false
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constant.CODE_GET_CONTACT || requestCode == Constant.CODE_GET_CONTACT2) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    try {
                        ContactPhoneUtils.getContactPhoneInfo11(this, data, requestCode) { name, phone ->
                            setSelector(phone, requestCode, name)
                        }
                        initBtn()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }



    /**
     * Description：判断两次选择的是否为同意联系人   判断联系恶人是否合法
     * Author：田羽衡
     * Version：<v1.0，2019/10/31 10:40>
     * params:
     * return：
     * LastEditTime：2019/10/31 10:40
     * Deprecated： false
     */
    private fun setSelector(phone: String?, requestCode: Int, name: String) {
        if (!StringUtil.isMobileNO(StringUtil.toNum(phone))) {
            ToastUtils.makeText(this, "联系人手机号不合法，请重新选择")
            if (requestCode == Constant.CODE_GET_CONTACT) {
                select_contact_name = ""
                select_contact_phone = ""
                name_txt.text = select_contact_name
            } else if (requestCode == Constant.CODE_GET_CONTACT2) {
                select_contact_name2 = ""
                select_contact_phone2 = ""
                name_other_txt.text = select_contact_name2
            }
        } else {
            if (requestCode == Constant.CODE_GET_CONTACT) {
                if (name == select_contact_name2 || phone == select_contact_phone2) {
                    ToastUtils.makeText(this, "不可选择2个一样的紧急联系人，请重新选择")
                    select_contact_name = ""
                    select_contact_phone = ""
                    name_txt.text = select_contact_name
                } else {
                    select_contact_name = name
                    select_contact_phone = StringUtil.toNum(phone)
                    name_txt.text = select_contact_name
                }

            } else if (requestCode == Constant.CODE_GET_CONTACT2) {
                if (name == select_contact_name || StringUtil.toNum(phone) == select_contact_phone) {
                    ToastUtils.makeText(this, "不可选择2个一样的紧急联系人，请重新选择")
                    select_contact_name2 = ""
                    select_contact_phone2 = ""
                    name_other_txt.text = select_contact_name2
                } else {
                    select_contact_name2 = name
                    select_contact_phone2 = StringUtil.toNum(phone)
                    name_other_txt.text = select_contact_name2
                }
            }
        }
    }

    /**
     * 提交成功
     * */
    override fun success(msg: String) {
        ToastUtils.makeText(this, msg)
        finish()
    }


}
