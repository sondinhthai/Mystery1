package com.example.mystery1.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.mystery1.R;
import com.example.mystery1.control.remote.RequestLanguagesManager;
import com.example.mystery1.control.rest.Callback;
import com.example.mystery1.databinding.ActivityLanguageBinding;
import com.example.mystery1.models.Language;
import com.example.mystery1.view.adapter.LanguageAdapter;

import java.util.List;

public class LanguageActivity extends AppCompatActivity {
    private ActivityLanguageBinding binding;
    private RequestLanguagesManager requestLanguagesManager;
    private LanguageAdapter languageAdapter;

    public static void starter(Context context) {
        Intent intent = new Intent(context, LanguageActivity.class);
        context.startActivity(intent);
    }

    public static void finishAct(Activity activity){
        activity.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_language);

        requestLanguagesManager = new RequestLanguagesManager();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        binding.revLanguage.setLayoutManager(layoutManager);
        languageAdapter = new LanguageAdapter();

        requestLanguagesManager.getAllLanguages(new Callback() {
            @Override
            public void getLanguage(List<Language> list) {
                super.getLanguage(list);

                languageAdapter.setLanguages(list);
                binding.revLanguage.setAdapter(languageAdapter);
            }
        });
    }
}