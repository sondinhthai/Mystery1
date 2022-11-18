package com.example.mystery1.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mystery1.R;
import com.example.mystery1.control.remote.RequestDocumentsManager;
import com.example.mystery1.control.rest.Callback;
import com.example.mystery1.databinding.BottomSheetBookmarkBinding;
import com.example.mystery1.models.Documents;
import com.example.mystery1.view.adapter.BookmarkAdapter;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.navigation.NavigationView;

import java.util.List;
import java.util.Locale;

public class BottomSheetBookmark extends BottomSheetDialog {
    private BottomSheetBookmarkBinding binding;
    private ImageView imvClose;
    private RecyclerView revBookmark;
    private TextView txtEmpty;

    private RequestDocumentsManager requestDocumentsManager;
    private BookmarkAdapter bookmarkAdapter;
    private LinearLayoutManager layoutManager;
    private TextView bookmarkTitle;

    private boolean checkLanguage;
    private int title;

    public BottomSheetBookmark(@NonNull Context context, int theme) {
        super(context, theme);
//        SharedPreferences sharedPreferences = context.getSharedPreferences("SavedLanguage", Context.MODE_PRIVATE);
//        String tagLanguage = sharedPreferences.getString("saved_tag", "");ư
//
//        if (tagLanguage != null) {
//            setLocale(tagLanguage, context);
//
        layoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
    }

    public void setCheckLanguage(boolean checkLanguage) {
        this.checkLanguage = checkLanguage;
    }

    public boolean isCheckLanguage() {
        return checkLanguage;
    }

    public int getBookmarkTitle() {
        return title;
    }

    public void setBookmarkTitle(int title) {
        this.title = title;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        getWindow().setGravity(Gravity.BOTTOM);
        setContentView(R.layout.bottom_sheet_bookmark);

        imvClose = findViewById(R.id.close);
        revBookmark = findViewById(R.id.bookmark_document);
        txtEmpty = findViewById(R.id.txt_empty);
        bookmarkTitle = findViewById(R.id.bookmark_title);

        bookmarkTitle.setText(getBookmarkTitle());

        requestDocumentsManager = new RequestDocumentsManager();

        revBookmark.setLayoutManager(layoutManager);
        bookmarkAdapter = new BookmarkAdapter();

        requestDocumentsManager.getAllBookmark(new Callback() {
            @Override
            public void getDocument(List<Documents> list) {
                super.getDocument(list);

                bookmarkAdapter.setDocuments(list);
                if (bookmarkAdapter.getDocuments().isEmpty()) {
                    txtEmpty.setVisibility(View.VISIBLE);
                }
                revBookmark.setAdapter(bookmarkAdapter);
            }
        });

        imvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
    }

//    public void setLocale(String tag, Context context) {
//        Resources resources = context.getResources();
//
//        DisplayMetrics metrics = resources.getDisplayMetrics();
//
//        Configuration configuration = resources.getConfiguration();
//
//        if (tag != null) {
//            configuration.locale = new Locale(tag);
//        }
//
//        resources.updateConfiguration(configuration, metrics);
//
//        onConfigurationChanged(configuration);
//    }
//
//    @Override
//    public void onConfigurationChanged(@NonNull Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//
//    }
}
