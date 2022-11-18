package com.example.mystery1.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.View;

import com.example.mystery1.R;
import com.example.mystery1.control.remote.RequestLanguagesManager;
import com.example.mystery1.databinding.ActivitySettingsBinding;
import com.example.mystery1.models.CurrentTag;
import com.example.mystery1.models.Language;

import java.util.Locale;

public class SettingsActivity extends AppCompatActivity {
    private ActivitySettingsBinding binding;
    private RequestLanguagesManager requestLanguagesManager;

    public static void starter(Context context) {
        Intent intent = new Intent(context, SettingsActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_settings);

        requestLanguagesManager = new RequestLanguagesManager();

//        binding.switchDarkMode.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (binding.switchDarkMode.isChecked()) {
//                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//                } else {
//                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//                }
//            }
//        });

        String tagLanguage = getIntent().getStringExtra("tag");
        String language = getIntent().getStringExtra("language");

        if (language != null) {

        }

        SharedPreferences sharedPreferences = getSharedPreferences("SavedLanguage", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        CurrentTag currentTag = new CurrentTag();

        if (tagLanguage != null && language != null) {
            editor.putString("saved_language", language);
            editor.putString("saved_tag", tagLanguage);
            currentTag.setTextLanguage(language);
            currentTag.setTag(tagLanguage);

            @SuppressLint("HardwareIds") String android_id = Settings.Secure.getString(SettingsActivity.this.getContentResolver(),
                    Settings.Secure.ANDROID_ID);

            currentTag.setDeviceID(android_id);

            requestLanguagesManager.saveCurrentTag(currentTag);
            editor.apply();
        }

        setLocale(sharedPreferences.getString("saved_tag", ""));
        binding.languageSet.setText(sharedPreferences.getString("saved_language", ""));

        binding.settingLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LanguageActivity.starter(SettingsActivity.this);
                finish();
            }
        });

        binding.clickBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity2.starter(SettingsActivity.this);
                finish();
            }
        });
    }

    public void setLocale(String tag) {
        Resources resources = getResources();

        DisplayMetrics metrics = resources.getDisplayMetrics();

        Configuration configuration = resources.getConfiguration();

        if (tag != null) {
            configuration.locale = new Locale(tag);
        }

        resources.updateConfiguration(configuration, metrics);

        onConfigurationChanged(configuration);
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        binding.language.setText(R.string.language);
        binding.txtDarkMode.setText(R.string.dark_mode);
        binding.titleSettings.setText(R.string.settings);
    }
}