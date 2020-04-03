package com.yinduowang.installment.app.utils
import android.Manifest
import androidx.fragment.app.FragmentActivity
import com.jess.arms.utils.PermissionUtil
import com.meiqia.meiqiasdk.activity.MQConversationActivity
import com.meiqia.meiqiasdk.util.MQIntentBuilder
import com.tbruyelle.rxpermissions2.RxPermissions
import com.yinduowang.installment.app.utils.gosetting.PermissionSetting
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import java.util.HashMap

object GotoMQChatUtils {
    fun gotoMQChat(activity: FragmentActivity, tel: String, mErrorHandler: RxErrorHandler) {
        PermissionUtil.requestPermission(
                object : PermissionUtil.RequestPermission {
                    override fun onRequestPermissionSuccess() {
                        // 设置初始化显示页面
                        val clientInfo = HashMap<String, String>()
//                        clientInfo["name"] = name
                        clientInfo["tel"] = tel
//                        clientInfo["avatar"] = avatar
                        val MQIntent = MQIntentBuilder(activity, MQConversationActivity::class.java).setClientInfo(clientInfo).build()
                        activity.startActivity(MQIntent)
                    }

                    override fun onRequestPermissionFailure(permissions: List<String>) {
                        ToastUtils.makeText(activity,"麦克风权限获取失败")
                    }

                    override fun onRequestPermissionFailureWithAskNeverAgain(permissions: List<String>) {
                        PermissionSetting.toPermissionSetting(activity)
                    }
                },
                RxPermissions(activity),
                mErrorHandler,
                Manifest.permission.RECORD_AUDIO
               )


    }
    fun gotoMQChatCustomized(activity: FragmentActivity, customizedId:String, mErrorHandler: RxErrorHandler) {
        PermissionUtil.requestPermission(
                object : PermissionUtil.RequestPermission {
                    override fun onRequestPermissionSuccess() {
                        // 设置初始化显示页面
                        val clientInfo = HashMap<String, String>()
                        clientInfo["name"] = "游客"
                        val MQIntent = MQIntentBuilder(activity, MQConversationActivity::class.java).setCustomizedId(customizedId).setClientInfo(clientInfo).build()
                        activity.startActivity(MQIntent)
                    }

                    override fun onRequestPermissionFailure(permissions: List<String>) {
                        ToastUtils.makeText(activity,"麦克风权限获取失败")
                    }

                    override fun onRequestPermissionFailureWithAskNeverAgain(permissions: List<String>) {
                        PermissionSetting.toPermissionSetting(activity)
                    }
                },
                RxPermissions(activity),
                mErrorHandler,
                Manifest.permission.RECORD_AUDIO
        )


    }

}
