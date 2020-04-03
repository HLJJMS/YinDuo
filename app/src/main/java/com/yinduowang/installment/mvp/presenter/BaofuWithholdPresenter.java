package com.yinduowang.installment.mvp.presenter;

import android.app.Application;
import android.text.TextUtils;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.yinduowang.installment.app.utils.RxUtils;
import com.yinduowang.installment.app.utils.ToastUtils;
import com.yinduowang.installment.mvp.contract.BaofuWithholdContract;
import com.yinduowang.installment.mvp.model.entity.BaofuWithholdBeanNew;
import com.yinduowang.installment.mvp.model.entity.BaseResponse;
import com.yinduowang.installment.mvp.model.entity.PreBindBean;

import java.util.Map;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;


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
public class BaofuWithholdPresenter extends BasePresenter<BaofuWithholdContract.Model, BaofuWithholdContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public BaofuWithholdPresenter(BaofuWithholdContract.Model model, BaofuWithholdContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }

    public void getCode(String phone, String bankCardNo) {
        mModel.getCode(phone, bankCardNo)
                .compose(RxUtils.INSTANCE.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<PreBindBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<PreBindBean> baseResponse) {
                        if (TextUtils.equals("200", baseResponse.getCode())) {
                            mRootView.sensdSucess(baseResponse.getData());
                        } else {
                            mRootView.sendFail(baseResponse.getMsg());
                        }
                    }
                });
    }

    public void submit(String uniqueCode, String authCode) {
        mModel.submit(uniqueCode, authCode)
                .compose(RxUtils.INSTANCE.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<Map<String, String>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<Map<String, String>> baseResponse) {
                        if (TextUtils.equals("200", baseResponse.getCode())) {
                            mRootView.submitSucess(baseResponse.getData());
                        } else {
                            ToastUtils.INSTANCE.makeText(mApplication, baseResponse.getMsg());
                        }
                    }
                });

    }

    public void getDate() {
        mModel.getDate()
                .compose(RxUtils.INSTANCE.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<BaofuWithholdBeanNew>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<BaofuWithholdBeanNew> baseResponse) {
                        if (TextUtils.equals("200", baseResponse.getCode())) {
                            mRootView.setText(baseResponse.getData());
                        } else {
                            ToastUtils.INSTANCE.makeText(mApplication, baseResponse.getMsg());
                        }
                    }
                });
    }
}
