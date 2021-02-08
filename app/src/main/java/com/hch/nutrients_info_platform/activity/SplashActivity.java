package com.hch.nutrients_info_platform.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.hch.nutrients_info_platform.R;

public class SplashActivity extends AppCompatActivity {

    Animation anim;
    TextView mTextview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
        mTextview=(TextView) findViewById(R.id.textview1); // Declare an imageView to show the animation.
        anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_splash_textview); // Create the animation.
        mTextview.startAnimation(anim);
        anim.setAnimationListener(new Animation.AnimationListener() {
            //애니메이션 시작되기 전 호출
            @Override
            public void onAnimationStart(Animation animation) {
            }

            //애니메이션이 끝났을 때 호출
            @Override
            public void onAnimationEnd(Animation animation) {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                overridePendingTransition(R.anim.anim_splash_out_top, R.anim.anim_splash_in_down);
                finish();
                // HomeActivity.class is the activity to go after showing the splash screen.
            }

            //애니메이션이 반복될 때 호출
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }

}
