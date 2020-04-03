package com.yinduowang.installment.app.widget.popwindow;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.yinduowang.installment.R;

public class ServiceChargePopwindow {

    private View popView;
    private PopupWindow popWindow;
    private TextView sure;
    private TextView approve_fees, channel_service_fees, management_fees, platform_service_fees, desc;

    private Activity activity;

    public ServiceChargePopwindow(Activity activity, String account, String approveFees, String channelServiceFees, String managementFees, String platformServiceFees, String descStr) {
        this.activity = activity;


        serviceCharge(account, approveFees, channelServiceFees, managementFees, platformServiceFees, descStr);
    }

    /**
     * 服务费弹框
     */
    private void serviceCharge(String account, String approveFees, String channelServiceFees, String managementFees, String platformServiceFees, String descStr) {
        popView = activity.getLayoutInflater().inflate(R.layout.layout_pop_service_charge, null);
        sure = (TextView) popView.findViewById(R.id.sure);
        approve_fees = (TextView) popView.findViewById(R.id.approve_fees);
        channel_service_fees = (TextView) popView.findViewById(R.id.channel_service_fees);
        management_fees = (TextView) popView.findViewById(R.id.management_fees);
        platform_service_fees = (TextView) popView.findViewById(R.id.platform_service_fees);
        desc = (TextView) popView.findViewById(R.id.desc);

        approve_fees.setText(approveFees );
        channel_service_fees.setText(channelServiceFees );
        management_fees.setText(managementFees );
        platform_service_fees.setText(platformServiceFees );
        desc.setText(descStr);


        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWindow.dismiss();
            }
        });


        popWindow = new PopupWindow(popView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popWindow.setAnimationStyle(R.style.popwin_anim_style);
        //添加背景使其点击popupWindow以外的区域（包括返回键）消失，否则不消失
        //popWindow.setBackgroundDrawable(new ColorDrawable());
        popWindow.setOutsideTouchable(false);
        final WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = 0.5f;
        activity.getWindow().setAttributes(lp);
        popWindow.showAtLocation(activity.getWindow().getDecorView(), Gravity.CENTER, 0, 0);
        popWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                lp.alpha = 1.0f;
                activity.getWindow().setAttributes(lp);
            }
        });
    }

}
