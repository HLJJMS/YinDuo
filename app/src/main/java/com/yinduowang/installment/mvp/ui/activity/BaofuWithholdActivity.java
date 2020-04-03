package com.yinduowang.installment.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.umeng.analytics.MobclickAgent;
import com.yinduowang.installment.R;
import com.yinduowang.installment.app.constant.Api;
import com.yinduowang.installment.app.constant.UMCountConfig;
import com.yinduowang.installment.app.utils.LoadingUtils;
import com.yinduowang.installment.app.utils.StringUtil;
import com.yinduowang.installment.app.utils.ToastUtils;
import com.yinduowang.installment.app.utils.UMCountUtil;
import com.yinduowang.installment.app.widget.AutomaticIntervalEditText;
import com.yinduowang.installment.app.widget.DonwTimerView;
import com.yinduowang.installment.app.widget.TitleBar;
import com.yinduowang.installment.di.component.DaggerBaofuWithholdComponent;
import com.yinduowang.installment.mvp.contract.BaofuWithholdContract;
import com.yinduowang.installment.mvp.model.entity.BaofuWithholdBeanNew;
import com.yinduowang.installment.mvp.model.entity.PreBindBean;
import com.yinduowang.installment.mvp.presenter.BaofuWithholdPresenter;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:代扣授权 银行卡绑定
 * <p>
 * Created by MVPArmsTemplate on 03/05/2019 09:34
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class BaofuWithholdActivity extends BaseActivity<BaofuWithholdPresenter> implements BaofuWithholdContract.View {

    @BindView(R.id.titleBar)
    TitleBar titlebar;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.card_number)
    AutomaticIntervalEditText cardNumber;
    @BindView(R.id.bank_number)
    EditText bankNumber;
    @BindView(R.id.mobile_number)
    TextView mobileNumber;
    @BindView(R.id.code_number)
    EditText codeNumber;
    @BindView(R.id.tv_next)
    TextView tvNext;
    @BindView(R.id.ck_agreement)
    CheckBox ckAgreement;
    @BindView(R.id.tv_loan_agreement)
    TextView tvLoanAgreement;
    @BindView(R.id.rl_agreement)
    LinearLayout rlAgreement;
    @BindView(R.id.tv_code)
    DonwTimerView tvCode;


    private String uniqueCode;
    private boolean bankNumberNoEmpty;
    private boolean mobileNumberNoEmpty;
    private boolean codeNumberNoEmpty;
    private boolean ckbox = true;
    private String bankName = "";
    private String bankCard = "";

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerBaofuWithholdComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_baofu_withhold; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        titlebar.showTitleAndBack("代扣授权");
        rlAgreement.setVisibility(View.VISIBLE);
        initAgreement();
        tvNext.setText("确认授权");
        bankNumber.addTextChangedListener(bankNumberTextWatcher);
        mobileNumber.addTextChangedListener(mobileNumberTextWatcher);
        codeNumber.addTextChangedListener(codeNumberTextWatcher);
        mPresenter.getDate();
    }

    private void initAgreement() {
        ckAgreement.setOnCheckedChangeListener(listener);
    }

    private TextWatcher bankNumberTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            bankNumberNoEmpty = !TextUtils.isEmpty(s.toString());
            initBtnStatus();
        }
    };
    private TextWatcher mobileNumberTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            mobileNumberNoEmpty = !TextUtils.isEmpty(s.toString());
            initBtnStatus();
        }
    };
    private TextWatcher codeNumberTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            codeNumberNoEmpty = !TextUtils.isEmpty(s.toString()) && s.toString().length() > 0;
            initBtnStatus();
        }
    };

    private CompoundButton.OnCheckedChangeListener listener = (buttonView, isChecked) -> {
        ckbox = isChecked;
        initBtnStatus();
    };


    private void initBtnStatus() {
        if (bankNumberNoEmpty && mobileNumberNoEmpty && codeNumberNoEmpty && ckbox) {
            tvNext.setEnabled(true);
        } else {
            tvNext.setEnabled(false);
        }
    }

    @Override
    public void showLoading() {
        LoadingUtils.show(this);
    }

    @Override
    public void hideLoading() {
        LoadingUtils.dismiss(this);
    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.tv_next, R.id.tv_loan_agreement, R.id.tv_code})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_next:
                //确认授权
                if (TextUtils.isEmpty(uniqueCode)) {
                    ToastUtils.INSTANCE.makeText(this, "请先获取验证码");
                    return;
                }
                //添加友盟点击事件统计
                UMCountUtil.Companion.instance().onEvent(TAG, UMCountConfig.INSTANCE.getReBindingBankCard_btn(), UMCountConfig.INSTANCE.getReBindingBankCard_value());
                mPresenter.submit(uniqueCode, codeNumber.getText().toString());
                break;
            case R.id.tv_loan_agreement:
                //添加友盟点击事件统计
                UMCountUtil.Companion.instance().onEvent(TAG, UMCountConfig.INSTANCE.getReBindingBankCardAgreement_btn(), UMCountConfig.INSTANCE.getReBindingBankCardAgreement_value());
                //打开协议
                Intent intent = new Intent(this, CommWebViewActivity.class);
                intent.putExtra(CommWebViewActivity.KEY_URL_NAME, Api.AGREEMENT_ENTRUST_DEDUCTION_PROXY);
                intent.putExtra(CommWebViewActivity.KEY_JAVASCRIPT_TYPE, CommWebViewActivity.TYPE_JAVASCRIPT_JEL_LE_ME);
                intent.putExtra(CommWebViewActivity.KEY_IS_AUTHENTICATION, CommWebViewActivity.TYPE_TITLE_NORMAL);
                HashMap<String, String> map = new HashMap<>();
                map.put("bankName", bankName);
                map.put("bankCard", bankCard);
                intent.putExtra(CommWebViewActivity.KEY_URL_PARAMS, map);
                startActivity(intent);
                break;
            case R.id.tv_code:
                //获取验证码
                if (bankNumber.getText().toString().isEmpty()) {
                    ToastUtils.INSTANCE.makeText(this, R.string.please_fill_bankcard_number);
                    return;
                }
                if (bankNumber.getText().toString().trim().length() != 19 && bankNumber.getText().toString().trim().length() != 16) {
                    ToastUtils.INSTANCE.makeText(this, R.string.bank_card_number_can_only_be_16_or_19_digits);
                    return;
                }
                if (mobileNumber.getText().toString().isEmpty()) {
                    ToastUtils.INSTANCE.makeText(this, R.string.please_fill_phone_number);
                    return;
                }
                if (!StringUtil.isMobileNO(mobileNumber.getText().toString())) {
                    ToastUtils.INSTANCE.makeText(this, R.string.incorrect_format_of_mobile_phone_number);
                    return;
                }
                //添加友盟点击事件统计
                UMCountUtil.Companion.instance().onEvent(TAG, UMCountConfig.INSTANCE.getReBindingBankCardGetCode_btn(), UMCountConfig.INSTANCE.getReBindingBankCardGetCode_value());
                mPresenter.getCode(mobileNumber.getText().toString(), bankNumber.getText().toString());
                break;
        }
    }

    @Override
    public void sensdSucess(PreBindBean baseResponse) {
        ToastUtils.INSTANCE.makeText(this, baseResponse.getMessage());
        if (baseResponse.getStatus() == 1) {
            uniqueCode = baseResponse.getUniqueCode();
            tvCode.startTimer();
        } else {
            tvCode.stopTimer();
        }
    }

    @Override
    public void sendFail(String error) {
        //验证码发送失败
        tvCode.stopTimer();
        ToastUtils.INSTANCE.makeText(this, error);
    }

    @Override
    public void submitSucess(Map<String, String> map) {
        //处理状态 1、处理中 2、失败 3、成功 4、需要继续绑定其他渠道（再次调用预绑定接口）
        if (TextUtils.equals(map.get("status"), "4")) {
            codeNumber.setText("");
            tvCode.stopTimer();
            mPresenter.getCode(mobileNumber.getText().toString(), bankNumber.getText().toString());
        } else {
            //确认授权 去结果页面
            Intent intent = new Intent(this, AuthorizationResultsActivity.class);
            intent.putExtra("status", map.get("status"));
            intent.putExtra("errorMsg", map.get("msg"));
            startActivity(intent);
        }
    }

    @Override
    public void setText(BaofuWithholdBeanNew baofuWithholdBean) {
        bankCard = baofuWithholdBean.getCardNo();
        bankName = baofuWithholdBean.getBank();

        //设置内容
        name.setText(baofuWithholdBean.getName());
        cardNumber.setText(baofuWithholdBean.getIdentification());
        initBtnStatus();
    }

    @Override
    protected void onDestroy() {
        tvCode.stopTimer();
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("代扣授权"); //统计页面(仅有Activity的应用中SDK自动调用，不需要单独写。"SplashScreen"为页面名称，可自定义)

        MobclickAgent.onResume(this);          //统计时长
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("代扣授权"); // （仅有Activity的应用中SDK自动调用，不需要单独写）保证 onPageEnd 在onPause 之前调用,因为 onPause 中会保存信息。"SplashScreen"为页面名称，可自定义
        MobclickAgent.onPause(this);
    }
}
