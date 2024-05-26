package com.guoyuan.loginbuttonanimprac;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

import com.guoyuan.loginbuttonanimprac.databinding.ActivityJavaMainBinding;

public class JavaMainActivity extends AppCompatActivity {
    private ActivityJavaMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java_main);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_java_main);
        binding.button.setText("hahaha");
        /*binding.button.setOnClickListener(v -> {
            // 创建一个ObjectAnimator，改变按钮的layout_width属性
            ObjectAnimator animator = ObjectAnimator.ofInt(binding.button, "layout_width",
                    binding.button.getWidth(), 0);
            animator.setDuration(500); // 动画持续时间500毫秒
            animator.start();
        });*/
        binding.button.setOnClickListener(v -> {
            // 以自身为坐标点   参数： x轴的起始点,结束点   y轴的起始点,结束点   1：不缩放，这是缩放比例概念
            // ScaleAnimation sa = new ScaleAnimation(0,1,0,1);
            // 默认从左上角开始缩放   可以指定位置(在100,50的地方缩放)
            //ScaleAnimation sa = new ScaleAnimation(0,1,0,1,100,50);
            ScaleAnimation sa = new ScaleAnimation(1, 0, 1, 1, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            // 设置动画时长
            sa.setDuration(500);
            // 启动动画
            v.startAnimation(sa);

        });


    }
}