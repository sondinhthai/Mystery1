package com.example.mystery1.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.bumptech.glide.Glide;
import com.example.mystery1.R;
import com.example.mystery1.databinding.ActivityDetailDocumentBinding;

public class DetailDocumentActivity extends AppCompatActivity {
    private ActivityDetailDocumentBinding binding;

    public static void starter(Context context, Bundle bundle) {
        Intent intent = new Intent(context, DetailDocumentActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @SuppressLint({"SetJavaScriptEnabled", "UseCompatLoadingForDrawables", "ResourceAsColor"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_document);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_document);

        String title = getIntent().getStringExtra(String.valueOf(R.string.title));
        String imgUrl = getIntent().getStringExtra(String.valueOf(R.string.imgUrl));
        String content = getIntent().getStringExtra(String.valueOf(R.string.content));

        Glide.with(this).load(imgUrl).into(binding.imgDocument);
        binding.titleDocument.setText(title);

        binding.contentDocument.setWebViewClient(new WebViewClient());
        binding.contentDocument.loadUrl(content);

        WebSettings webSettings =  binding.contentDocument.getSettings();
        webSettings.setJavaScriptEnabled(true);

        binding.scrollView.fullScroll(View.FOCUS_UP);
        binding.scrollView.pageScroll(View.FOCUS_UP);

        binding.clickBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}