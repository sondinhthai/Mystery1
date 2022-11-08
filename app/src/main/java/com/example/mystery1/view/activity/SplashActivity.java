package com.example.mystery1.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.os.Handler;

import com.example.mystery1.R;
import com.example.mystery1.databinding.ActivitySplashBinding;

public class SplashActivity extends AppCompatActivity {
    private ActivitySplashBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash);

        new Handler().postDelayed(() -> {
            MainActivity.starter(SplashActivity.this);
        }, 8000);
    }
}