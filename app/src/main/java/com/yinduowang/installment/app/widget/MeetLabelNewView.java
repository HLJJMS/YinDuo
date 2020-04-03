package com.yinduowang.installment.app.widget;

import android.content.Context;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yinduowang.installment.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 新同学 填写资料 标签VIEW
 */
public class MeetLabelNewView extends FrameLayout {

    TextView tvLabelView;

    RelativeLayout rootviewLabelView;
    private LayoutInflater mInflater;
    private View mView;
    private OnClickListener onDeleteClickListener;
    private String label;
    private OnClickListener onSelectListener;


    public MeetLabelNewView(OnClickListener onDeleteClickListener, OnClickListener onSelectListener, String label, Context context) {
        this(context, null);
        this.onDeleteClickListener = onDeleteClickListener;
        this.onSelectListener = onSelectListener;
        this.label = label;
        initview();
        new EditText(context).setFilters(new InputFilter[]{ new  InputFilter.LengthFilter(10)});
    }

    public MeetLabelNewView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MeetLabelNewView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initview() {
        if (mView == null) {
            mInflater = LayoutInflater.from(getContext());
            mView = mInflater.inflate(R.layout.layout_meet_label, null);
            tvLabelView = mView.findViewById(R.id.tv_label_view);
            rootviewLabelView = mView.findViewById(R.id.rootview_label_view);
            rootviewLabelView.setOnClickListener(onSelectListener);
            tvLabelView.setText(label);
            LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
            addView(mView, lp);
        }
    }
}
