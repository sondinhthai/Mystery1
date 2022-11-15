package com.example.mystery1.view.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import com.example.mystery1.R;
import com.example.mystery1.control.remote.RequestDocumentsManager;
import com.example.mystery1.control.rest.Callback;
import com.example.mystery1.databinding.ActivityMain2Binding;
import com.example.mystery1.models.Documents;
import com.example.mystery1.util.Utility;
import com.example.mystery1.view.adapter.DocumentsAdapter;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuAdapter;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.SearchView;
import androidx.customview.widget.Openable;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import nl.psdcompany.duonavigationdrawer.views.DuoDrawerLayout;
import nl.psdcompany.duonavigationdrawer.views.DuoMenuView;
import nl.psdcompany.duonavigationdrawer.widgets.DuoDrawerToggle;

public class MainActivity2 extends AppCompatActivity implements RequestDocumentsManager.OnFetchDataListener{
    public static void starter(Context context) {
        Intent intent = new Intent(context, MainActivity2.class);
        context.startActivity(intent);
    }

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMain2Binding binding;
    private RequestDocumentsManager requestDocumentsManager;
    private DocumentsAdapter documentsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main2);

        requestDocumentsManager = new RequestDocumentsManager();
        documentsAdapter = new DocumentsAdapter();

        setSupportActionBar(binding.appBarMain.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
//        navigationView.setBackgroundColor(getResources().getColor(R.color.black));
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_activity2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_settings:
                SettingsActivity.starter(MainActivity2.this);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
    }

    private void showData(Documents document){
        List<Documents> documents = new ArrayList<>();
        documents.add(document);
        documentsAdapter.setDocuments(documents);
    }

    @Override
    public void onFetchData(Documents documents, String title) {
        if (documents == null) {
            Utility.Notice.snack(binding.getRoot(), "No data");
            return;
        } else {
            showData(documents);
        }
    }

    @Override
    public void onError(String title) {

    }
}