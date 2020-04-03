package com.yinduowang.installment.mvp.presenter;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.text.TextUtils;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.DeviceUtils;
import com.yinduowang.installment.BuildConfig;
import com.yinduowang.installment.app.constant.Api;
import com.yinduowang.installment.app.utils.amap.LocationUtils;
import com.yinduowang.installment.mvp.contract.UploadUserInfoContract;
import com.yinduowang.installment.mvp.model.entity.AppVersionEntity;
import com.yinduowang.installment.mvp.model.entity.BaseResponse;
import com.yinduowang.installment.mvp.model.event.NeedUpgradeEvent;
import com.yinduowang.installment.mvp.ui.activity.UpgradeActivity;

import org.simple.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;
import timber.log.Timber;


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
public class UploadUserInfoPresenter extends BasePresenter<UploadUserInfoContract.Model, UploadUserInfoContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;
    private LocationUtils locationUtils;

    @Inject
    public UploadUserInfoPresenter(UploadUserInfoContract.Model model, UploadUserInfoContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
        locationUtils.destroyLocation();
    }

    public void uploadGPS() {
        if (locationUtils == null) {
            locationUtils = new LocationUtils();
        }
        locationUtils.initLocation(mApplication.getApplicationContext(), new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (aMapLocation.getErrorCode() == 0) {
                    Map<String, String> map = new HashMap<>();
                    map.put("longitude", String.valueOf(aMapLocation.getLongitude()));
                    map.put("latitude", String.valueOf(aMapLocation.getLatitude()));
                    map.put("address", aMapLocation.getAddress());
                    map.put("client_type", "1");
                    map.put("device_info", android.os.Build.MODEL);
                    map.put("app_version", BuildConfig.VERSION_NAME);

                    mModel.updataAllGPS(map)
                            .retryWhen(new RetryWithDelay(3, 2))
                            .subscribe(new ErrorHandleSubscriber<BaseResponse>(mErrorHandler) {
                                @Override
                                public void onNext(BaseResponse baseResponse) {
                                    Timber.i("上传GPS结果" + baseResponse.toString());
                                }

                                @Override
                                public void onError(Throwable t) {
                                    super.onError(t);
                                }
                            });
                }
            }
        });
        locationUtils.startLocation();
    }

    public void uploadAddressBookToServer() {
        mModel.uploadAddressBookToServer(mModel.getAllContactInfo())
                .retryWhen(new RetryWithDelay(3, 2))
                .subscribe(new ErrorHandleSubscriber<BaseResponse>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        Timber.i("上传联系人结果" + baseResponse.toString());
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                    }
                });
    }

    public void updataAllAppList() {
        mModel.updataAllAppList(mModel.getAllAppList())
                .retryWhen(new RetryWithDelay(3, 2))
                .subscribe(new ErrorHandleSubscriber<BaseResponse>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        Timber.i("上传App列表结果" + baseResponse.toString());
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                    }
                });
    }

    public void submitTokenkey(String tokenkey) {
        mModel.saveFingerprint(tokenkey)
                .retryWhen(new RetryWithDelay(3, 2))
                .subscribe(new ErrorHandleSubscriber<BaseResponse>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        if (TextUtils.equals(baseResponse.getCode(), Api.RequestSuccess)) {
                            Timber.i("白骑士SDK 设备指纹上传结果 --> result : 成功");
                        } else {
                            Timber.i("白骑士SDK 设备指纹上传结果 --> result : 失败");
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        Timber.i("设备指纹上传结果 --> result : 成功");
                    }
                });
    }

    public void updataAllSms() {
        mModel.updataAllSms(mModel.getAllMsm())
                .retryWhen(new RetryWithDelay(3, 2))
                .subscribe(new ErrorHandleSubscriber<BaseResponse>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        Timber.i("上传短信结果" + baseResponse.toString());
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                    }
                });
    }

    public void updataPage(String pageName, String imei) {
        mModel.updataPage(pageName, imei)
                .subscribe(new ErrorHandleSubscriber<BaseResponse>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        Timber.i("上传 页面统计结果 --> " + pageName + " --> " + baseResponse.toString());
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                    }
                });
    }

    public void updataError(String errorInfo) {
        mModel.updataError(errorInfo)
                .subscribe(new ErrorHandleSubscriber<BaseResponse>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        Timber.i("上传 错误信息结果" + baseResponse.toString() + " updata msg --> " + errorInfo);
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                    }
                });
    }

    public void checkVersion() {
        mModel.checkVersion()
                .subscribe(new ErrorHandleSubscriber<BaseResponse<AppVersionEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<AppVersionEntity> baseResponse) {
                        AppVersionEntity entity = baseResponse.getData();
                        int clientVersion = DeviceUtils.getVersionCode(mApplication);
                        boolean must = false;
                        boolean isUpgrade = false;
                        if (!TextUtils.isEmpty(entity.getNewVersion()) && clientVersion < java.lang.Double.valueOf(entity.getNewVersion())) {//手机版本低于服务器版本
                            if (!TextUtils.isEmpty(entity.isUpdate()) && TextUtils.equals("1" , entity.isUpdate())) {// 强制更新 0 不强制 1 强制
                                must = true;
                                isUpgrade = true;
                            } else {
                                if (!TextUtils.isEmpty(entity.getMinVersion()) && clientVersion < java.lang.Double.valueOf(entity.getMinVersion())) {//最低版本号
                                    must = true;
                                    isUpgrade = true;
                                } else {
                                    must = false;
                                    isUpgrade = true;
                                }
                            }
                        }

                        if (isUpgrade) {
                            EventBus.getDefault().post(new NeedUpgradeEvent());
                            Activity activity = AppManager.getAppManager().getTopActivity();
                            if (activity != null) {
                                Intent intent = new Intent(activity, UpgradeActivity.class);

                                intent.putExtra("remark", entity.getNewVersionDescription());
                                intent.putExtra("must", must);
                                intent.putExtra("new_code", entity.getNewVersionName());
                                intent.putExtra("apkUrl", entity.getApkUrl());
                                activity.startActivity(intent);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                    }
                });
    }
}
