package com.yinduowang.installment.app.widget;

import android.content.Context;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;

import com.yinduowang.installment.R;

//短信验证码计时器View
public class DonwTimerView extends AppCompatTextView {
    //    时间总数单位秒
    private int timeTotal = 60;
    //    时间间隔单位毫秒
    private int timeSpace = 1000;
    //    是和否可点级
    private boolean clickable = true;
    private CountDownTimer countDownTimer;
    private Context context;

    public DonwTimerView(Context context) {
        super(context);
        this.context = context;
        timerTool();

    }

    public DonwTimerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        timerTool();
    }

    public DonwTimerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        timerTool();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_UP:
//                if (clickable) {
//                    clickable = false;
//                    countDownTimer.start();
//                }
//                break;
//        }
        return super.onTouchEvent(event);
    }


    public void startTimer() {
        if (clickable) {
            clickable = false;
            setClickable(false);
            countDownTimer.start();
        }
    }

    public void stopTimer() {
        countDownTimer.cancel();
    }


    public void setTimeTotalAndTimeSpace(int timeTotal, int timeSpace) {
        this.timeTotal = timeTotal;
        this.timeSpace = timeSpace;
        timerTool();
    }

    public void timerTool() {
        countDownTimer = new CountDownTimer(timeTotal * 1000 + 500, timeSpace) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (millisUntilFinished / 1000 == 0L) {
                    onFinish();
                    return;
                }
                if (millisUntilFinished / 1000 > 9) {
                    setText(millisUntilFinished / 1000 + "秒重新获取");
                } else {
                    setText("0" + millisUntilFinished / 1000 + "秒重新获取");
                }
                setTextColor(ContextCompat.getColor(context, R.color.color_999999));
            }

            @Override
            public void onFinish() {
                clickable = true;
                setClickable(true);
                setText("重新获取");
                setTextColor(ContextCompat.getColor(context, R.color.colorAccent));

            }
        };
    }


}
