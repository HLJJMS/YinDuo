package com.yinduowang.installment.app.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.yinduowang.installment.R;


/**
 * 认证中心 上部认证进度条
 */
public class NodeView extends RelativeLayout {

    private ImageView iv0;
    private ImageView iv1;
    private ImageView iv2;

    public NodeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {

        LayoutInflater.from(context).inflate(R.layout.layout_node_progress, this);

        iv0 = findViewById(R.id.iv0);
        iv1 = findViewById(R.id.iv1);
        iv2 = findViewById(R.id.iv2);

        iv0.setImageResource(R.drawable.shape_node_bg);
        iv1.setImageResource(R.drawable.shape_node_bg);
        iv2.setImageResource(R.drawable.shape_node_bg);
    }

    public void setProgress(int progress) {

        switch (progress) {
            case 0:
                iv0.setImageResource(R.drawable.shape_node_bg);
                iv1.setImageResource(R.drawable.shape_node_bg);
                iv2.setImageResource(R.drawable.shape_node_bg);
                break;
            case 1:
                iv0.setImageResource(R.drawable.shape_node_bg);
                iv1.setImageResource(R.drawable.shape_node_bg);
                iv2.setImageResource(R.drawable.shape_node_bg);

                iv0.setImageResource(R.drawable.shape_node_empty_layer_bg);
                break;
            case 2:
                iv0.setImageResource(R.drawable.shape_node_bg);
                iv1.setImageResource(R.drawable.shape_node_bg);
                iv2.setImageResource(R.drawable.shape_node_bg);

                iv0.setImageResource(R.drawable.shape_node_empty_layer_bg);
                iv1.setImageResource(R.drawable.shape_node_empty_layer_bg);
                break;
            case 3:

                iv0.setImageResource(R.drawable.shape_node_empty_layer_bg);
                iv1.setImageResource(R.drawable.shape_node_empty_layer_bg);
                iv2.setImageResource(R.drawable.shape_node_empty_layer_bg);
                break;
        }

    }


}
