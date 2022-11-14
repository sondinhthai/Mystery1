package com.example.mystery1.view.base;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mystery1.util.Executor;

import java.util.concurrent.ExecutorService;

public class BaseActivity extends AppCompatActivity {
    protected ExecutorService executorService;

    public BaseActivity() {
        this.executorService = Executor.getInstance();
    }
}
