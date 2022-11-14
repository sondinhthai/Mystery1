package com.example.mystery1.util;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.example.mystery1.view.base.BaseActivity;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;

public class Utility {
    public static class Notice {
        public static void snack(View view, String data) {
            Snackbar snackbar = Snackbar.make(view, data, Snackbar.LENGTH_SHORT);
            snackbar.show();
        }
    }

    public static class Keyboard {
        public static void hideKeyBoard(Activity activity) {
            InputMethodManager inputMethodManager =
                    (InputMethodManager) activity.getSystemService(
                            BaseActivity.INPUT_METHOD_SERVICE);
            if (inputMethodManager.isAcceptingText()) {
                inputMethodManager.hideSoftInputFromWindow(
                        activity.getCurrentFocus().getWindowToken(),
                        0
                );
            }
        }
    }
}
