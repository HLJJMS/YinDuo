package com.yinduowang.installment.mvp.model;

import android.app.Application;

import com.blankj.utilcode.util.SPUtils;
import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.yinduowang.installment.app.constant.SPConstant;
import com.yinduowang.installment.app.utils.MD5Util;
import com.yinduowang.installment.app.utils.SignUtils;
import com.yinduowang.installment.mvp.contract.BaofuWithholdContract;
import com.yinduowang.installment.mvp.model.entity.BaofuWithholdBeanNew;
import com.yinduowang.installment.mvp.model.entity.BaseResponse;
import com.yinduowang.installment.mvp.model.entity.PreBindBean;
import com.yinduowang.installment.mvp.model.service.BaofuServive;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/05/2019 09:34
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class BaofuWithholdModel extends BaseModel implements BaofuWithholdContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public BaofuWithholdModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }


    @Override
    public Observable<BaseResponse<BaofuWithholdBeanNew>> getDate() {
        String token = SPUtils.getInstance().getString(SPConstant.TOKEN);
        return mRepositoryManager.obtainRetrofitService(BaofuServive.class).getDate(MD5Util.getMD5String(token), token);
    }

    @Override
    public Observable<BaseResponse<Map<String, String>>> submit(String uniqueCode, String authCode) {
        Map<String, String> map = new HashMap<>();
        map.put("uniqueCode", uniqueCode);
        map.put("authCode", authCode);
        String token = SPUtils.getInstance().getString(SPConstant.TOKEN);
        return mRepositoryManager.obtainRetrofitService(BaofuServive.class).submit(SignUtils.INSTANCE.getSign(map), token, uniqueCode, authCode);
    }

    @Override
    public Observable<BaseResponse<PreBindBean>> getCode(String phone, String bankCardNo) {
        Map<String, String> map = new HashMap<>();
        map.put("mobileNo", phone);
        map.put("bankCardNo", bankCardNo);
        String token = SPUtils.getInstance().getString(SPConstant.TOKEN);
        return mRepositoryManager.obtainRetrofitService(BaofuServive.class).getCode(bankCardNo, phone, SignUtils.INSTANCE.getSign(map), token);
    }


}