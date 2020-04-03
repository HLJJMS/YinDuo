package com.yinduowang.installment.app.widget.popwindow;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.yinduowang.installment.R;
import com.yinduowang.installment.app.utils.LoadImageUtils;
import com.jess.arms.utils.ArmsUtils;

public class AdPopUpWindow extends PopupWindow {
    private Context context;

    private String url;

    private View rootView;

    public AdPopUpWindow(Context context, String url) {
        super(context);
        this.context = context;
        this.url = url;
        initView();
    }

    private void initView() {

        LayoutInflater mInflater = LayoutInflater.from(context);
        rootView = (View) mInflater.inflate(R.layout.layout_dialog_main_pop, null);

        LoadImageUtils.INSTANCE.showImage(context, url, rootView.findViewById(R.id.iv_ad));

        ImageView close = (ImageView) rootView.findViewById(R.id.iv_close);

//        int marginLeft = Integer.parseInt(left) * ArmsUtils.getScreenWidth(context) / 100 - ArmsUtils.dip2px(context, 25);
//        int marginTop = Integer.parseInt(top) * ArmsUtils.getScreenHeidth(context) / 100 - ArmsUtils.dip2px(context, 25);

//        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) close.getLayoutParams();
//        params.setMargins(marginLeft, marginTop, 0, 0);
//        close.setLayoutParams(params);
        //-- 关闭按钮的点击事件
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isShowing()) {
                    dismiss();
                }
            }
        });
        setContentView(rootView);
        setWidth(ArmsUtils.getScreenWidth(context));
        setHeight(ArmsUtils.getScreenHeidth(context));
        setBackgroundDrawable(new ColorDrawable(0x00000000));
    }

    public void setOnClicListener(View.OnClickListener listener) {
        rootView.setOnClickListener(listener);
    }

}
