package com.yinduowang.installment.mvp.contract;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.yinduowang.installment.mvp.model.entity.BaofuWithholdBeanNew;
import com.yinduowang.installment.mvp.model.entity.BaseResponse;
import com.yinduowang.installment.mvp.model.entity.PreBindBean;

import java.util.Map;

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
public interface BaofuWithholdContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void sensdSucess(PreBindBean baseResponse);

        void sendFail(String error);

        void submitSucess(Map<String, String> map);

        void setText(BaofuWithholdBeanNew baofuWithholdBean);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<BaseResponse<BaofuWithholdBeanNew>> getDate();

        Observable<BaseResponse<Map<String, String>>> submit(String uniqueCode, String authCode);

        Observable<BaseResponse<PreBindBean>> getCode(String phone, String bankCardNo);
    }
}
