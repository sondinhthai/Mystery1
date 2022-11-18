package com.example.mystery1.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.mystery1.R;
import com.example.mystery1.control.remote.RequestDocumentsManager;
import com.example.mystery1.control.rest.Callback;
import com.example.mystery1.databinding.ActivitySearchDocumentBinding;
import com.example.mystery1.models.Documents;
import com.example.mystery1.view.adapter.DocumentsAdapter;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class SearchDocumentActivity extends AppCompatActivity {
    private ActivitySearchDocumentBinding binding;
    private RequestDocumentsManager requestDocumentsManager;

    private DocumentsAdapter documentsAdapter;
    private List<Documents> documentsList;

    public static void starter(Context context) {
        Intent intent = new Intent(context, SearchDocumentActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_document);

        SharedPreferences sharedPreferences = getSharedPreferences("SavedLanguage", Context.MODE_PRIVATE);
        String tagLanguage = sharedPreferences.getString("saved_tag", "");

        binding = DataBindingUtil.setContentView(this, R.layout.activity_search_document);

        requestDocumentsManager = new RequestDocumentsManager();

        binding.searchDocument.requestFocus();

        documentsAdapter = new DocumentsAdapter();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        binding.revDocument.setLayoutManager(layoutManager);

        if (tagLanguage != null) {
            setLocale(tagLanguage);
        }

        requestDocumentsManager = new RequestDocumentsManager();
        requestDocumentsManager.getAllDocument(new Callback() {
            @Override
            public void getDocument(List<Documents> list) {
                super.getDocument(list);
                Collections.shuffle(list);
                documentsAdapter.setDocuments(list);
                binding.revDocument.setAdapter(documentsAdapter);
            }
        });

        binding.searchDocument.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                charSequence = charSequence.toString().toLowerCase();

                CharSequence finalCharSequence = charSequence;
                requestDocumentsManager.getAllDocument(new Callback() {
                    @Override
                    public void getDocument(List<Documents> list) {
                        super.getDocument(list);

                        documentsList = new ArrayList<>();
                        for (Documents documents : list) {
                            if (documents.getTitle().toLowerCase().contains(finalCharSequence)) {
                                documentsList.add(documents);
                            }
                        }

                        documentsAdapter.setDocuments(documentsList);
                        binding.revDocument.setAdapter(documentsAdapter);
                    }
                });
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.clickBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

        binding.searchDocument.setHint(R.string.document_hint);
    }
}