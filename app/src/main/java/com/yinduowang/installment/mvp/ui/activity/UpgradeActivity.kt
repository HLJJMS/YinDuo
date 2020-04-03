package com.yinduowang.installment.mvp.ui.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.view.WindowManager
import androidx.core.content.FileProvider
import com.blankj.utilcode.util.SPUtils
import com.yinduowang.installment.R
import com.yinduowang.installment.app.constant.Constant
import com.yinduowang.installment.app.constant.SPConstant
import com.yinduowang.installment.mvp.model.event.UndoUpgradeEvent
import kotlinx.android.synthetic.main.activity_upgrade.*
import org.simple.eventbus.EventBus
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import kotlin.system.exitProcess


/**
 * @Description:升级弹窗
 * @Author:
 * @Date: 2019-10-28 09:04:29
 * @Version: 1.0, 2019-10-28
 * @LastEditors:
 * @LastEditTime:
 * @Deprecated: false
 */
class UpgradeActivity : Activity() {

    //    private var entity: AppVersionEntity? = null
    private var cancelUpdate = false
    private var downloadApkThread = DownloadApkThread()
    private var remark = ""
    private var must = false
    private var new_code = ""
    private var apkUrl = ""
    private var apkName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upgrade)

        remark = intent.getStringExtra("remark")
        must = intent.getBooleanExtra("must", false)
        new_code = intent.getStringExtra("new_code")
        apkUrl = intent.getStringExtra("apkUrl")

        updateVersionForce(remark, must, new_code)
    }

    //选择银行卡
    @SuppressLint("SetTextI18n")
    private fun updateVersionForce(remark: String, must: Boolean, new_code: String) {
        apkName = resources.getString(R.string.app_name) + new_code + ".apk"
        tv_updata_content?.movementMethod = ScrollingMovementMethod.getInstance()
        tv_updata_content?.text = remark
        tv_new_version.text = "版本 v${new_code}"
        if (must) {
            tvForceUpdata?.visibility = View.VISIBLE
            btn_versionupdate_next?.visibility = View.GONE
            btn_versionupdate_sure?.visibility = View.GONE
        } else {
            tvForceUpdata?.visibility = View.GONE
            btn_versionupdate_next?.visibility = View.VISIBLE
            btn_versionupdate_sure?.visibility = View.VISIBLE
        }
        tvForceUpdata?.setOnClickListener {
            if (apkUrl.isNotEmpty()) {
                downloadApk()
                tvForceUpdata?.visibility = View.GONE
                mProgress?.visibility = View.VISIBLE
                tv_updata_percent?.visibility = View.VISIBLE
            }
        }

        // 立即升级按钮
        btn_versionupdate_sure?.setOnClickListener {
            downloadApk()
            btn_versionupdate_sure?.visibility = View.GONE
            btn_versionupdate_next?.visibility = View.GONE
            tv_updata_percent?.visibility = View.VISIBLE
            mProgress?.visibility = View.VISIBLE
        }

        // 非强制升级 下次再说
        btn_versionupdate_next?.setOnClickListener {
            clContent.visibility = View.GONE
            clBackground.setBackgroundResource(R.color.transparent)
            finish()
        }
    }

    /**
     * 点击物理返回按钮
     * */
    override fun onBackPressed() {
        clContent.visibility = View.GONE
        clBackground.setBackgroundResource(R.color.transparent)
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        try {
            downloadApkThread.interrupt()
            if (must) {
                cancelUpdate = true
                exitProcess(0)
            } else {
                EventBus.getDefault().post(UndoUpgradeEvent())
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 安装 apk 文件
     *
     * @param apkFile
     */
    fun installApk(apkFile: File) {
        val installApkIntent = Intent()
        installApkIntent.action = Intent.ACTION_VIEW
        installApkIntent.addCategory(Intent.CATEGORY_DEFAULT)
        installApkIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            installApkIntent.setDataAndType(FileProvider.getUriForFile(applicationContext, "$packageName.file_provider", apkFile), "application/vnd.android.package-archive")
            installApkIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        } else {
            installApkIntent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive")
        }

        if (packageManager.queryIntentActivities(installApkIntent, 0).size > 0) {
            startActivity(installApkIntent)
        }
        SPUtils.getInstance().put(SPConstant.KEY_IS_NOT_FIRST_RUN, false)
    }

    /**
     * 下载apk文件
     */
    private fun downloadApk() {
        try {
            // 启动新线程下载软件
            downloadApkThread.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 下载文件线程
     */
    private inner class DownloadApkThread : Thread() {
        @SuppressLint("SetTextI18n")
        override fun run() {
            try {
                // 判断SD卡是否存在，并且是否具有读写权限
                if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {

                    val apkFile = File(getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)?.path, apkName)

                    val url = URL(apkUrl)
                    // 创建连接
                    val conn = url.openConnection() as HttpURLConnection
                    conn.connect()
                    // 获取文件大小
                    val length = conn.contentLength
                    // 创建输入流
                    val inputStream = conn.inputStream
                    val fos = FileOutputStream(apkFile)
                    var count = 0
                    // 缓存
                    val buf = ByteArray(1024)
                    // 写入到文件中
                    do {
                        val numread = inputStream.read(buf)
                        count += numread
                        // 计算进度条位置
                        val progress = (count.toFloat() / length * 100).toInt()
                        // 更新进度
                        runOnUiThread {
                            // 进度条更新进度
                            tv_updata_percent?.text = "升级中 ${progress}%···"
                            mProgress?.setProgress(progress)
                        }

                        if (numread <= 0) {
                            // 下载完成
                            installApk(apkFile)
                            break
                        }
                        // 写入文件
                        fos.write(buf, 0, numread)
                    } while (!cancelUpdate)// 点击取消就停止下载.
                    fos.close()
                    inputStream.close()
                }
            } catch (e: MalformedURLException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}
