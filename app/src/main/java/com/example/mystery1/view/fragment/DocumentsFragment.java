package com.example.mystery1.view.fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.mystery1.R;
import com.example.mystery1.control.remote.RequestDocumentsManager;
import com.example.mystery1.control.rest.Callback;
import com.example.mystery1.databinding.FragmentDocumentsBinding;
import com.example.mystery1.models.Documents;
import com.example.mystery1.view.BottomSheetBookmark;
import com.example.mystery1.view.activity.SearchDocumentActivity;
import com.example.mystery1.view.adapter.DocumentsAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class DocumentsFragment extends Fragment {
    private FragmentDocumentsBinding binding;

    private DocumentsAdapter documentsAdapter;
    private RequestDocumentsManager requestDocumentsManager;
    private BottomSheetBookmark bottomSheetBookmark;

    private ProgressDialog progressDialog;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_documents, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("SavedLanguage", Context.MODE_PRIVATE);
        String tagLanguage = sharedPreferences.getString("saved_tag", "");

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        binding.revDocument.setLayoutManager(layoutManager);
        documentsAdapter = new DocumentsAdapter();

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Loading...");
        progressDialog.show();
        setListDocuments();

        binding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                binding.swipeRefresh.setRefreshing(false);
                setListDocuments();
            }
        });

        binding.navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_search:
                        SearchDocumentActivity.starter(getContext());
                        return true;
                    case R.id.action_bookmark:
                        bottomSheetBookmark = new BottomSheetBookmark(getContext(), R.style.MaterialDialogSheet);
                        if (tagLanguage != null) {
                            setLocale(tagLanguage);
                        }
                        bottomSheetBookmark.show();
                        return true;
                }
                return false;
            }
        });
    }

    public void setListDocuments() {
        requestDocumentsManager = new RequestDocumentsManager();
        requestDocumentsManager.getAllDocument(new Callback() {
            @Override
            public void getDocument(List<Documents> list) {
                super.getDocument(list);
                Collections.shuffle(list);
                documentsAdapter.setDocuments(list);
                binding.revDocument.setAdapter(documentsAdapter);
                progressDialog.dismiss();
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

        MenuItem menuSearch = binding.navigation.getMenu().findItem(R.id.action_search);
        menuSearch.setTitle(R.string.menu_search);

        MenuItem menuBookmark = binding.navigation.getMenu().findItem(R.id.action_bookmark);
        menuBookmark.setTitle(R.string.menu_bookmark);

//        bottomSheetBookmark.getBookmarkTitle().setText(R.string.bookmarks);
        bottomSheetBookmark.setCheckLanguage(true);
        bottomSheetBookmark.setBookmarkTitle(R.string.bookmarks);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}