package com.example.mystery1.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.bumptech.glide.Glide;
import com.example.mystery1.R;
import com.example.mystery1.control.remote.RequestDocumentsManager;
import com.example.mystery1.control.rest.Callback;
import com.example.mystery1.databinding.ActivityDetailDocumentBinding;
import com.example.mystery1.models.Documents;

import java.util.List;

public class DetailDocumentActivity extends AppCompatActivity {
    private ActivityDetailDocumentBinding binding;
    private RequestDocumentsManager requestDocumentsManager;

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

        requestDocumentsManager = new RequestDocumentsManager();

        String title = getIntent().getStringExtra(String.valueOf(R.string.title));
        String imgUrl = getIntent().getStringExtra(String.valueOf(R.string.imgUrl));
        String content = getIntent().getStringExtra(String.valueOf(R.string.content));

        Glide.with(this).load(imgUrl).into(binding.imgDocument);
        binding.titleDocument.setText(title);

        binding.contentDocument.setWebViewClient(new WebViewClient());

        WebSettings webSettings = binding.contentDocument.getSettings();
        webSettings.setJavaScriptEnabled(true);

        @SuppressLint("HardwareIds") String android_id = Settings.Secure.getString(DetailDocumentActivity.this.getContentResolver(),
                Settings.Secure.ANDROID_ID);

        binding.contentDocument.loadUrl(content + "/" + android_id);

        binding.scrollView.fullScroll(View.FOCUS_UP);
        binding.scrollView.pageScroll(View.FOCUS_UP);

        Documents documents = new Documents(title, content, imgUrl);

        requestDocumentsManager.getAllBookmark(new Callback() {
            @Override
            public void getDocument(List<Documents> list) {
                super.getDocument(list);

                for (Documents documents1 : list) {
                    if (documents1.getTitle().equals(documents.getTitle())) {
                        binding.clickBookmark.setChecked(true);
                    }
                }

            }
        });

        binding.clickBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.clickBookmark.isChecked()) {
                    requestDocumentsManager.saveBookmarks(documents);
                } else {
                    requestDocumentsManager.deleteBookmarks(documents);
                }
            }
        });



        binding.clickBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}