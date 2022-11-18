package com.example.mystery1.control.remote;

import android.util.Log;

import com.example.mystery1.control.rest.Callback;
import com.example.mystery1.models.Documents;
import com.example.mystery1.models.Language;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public class RequestCheckLanguage {
    private Retrofit retrofit;

    public RequestCheckLanguage() {
        this.retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.29.153:8081/")
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();
    }

    public void checkLanguage(String tag) {
        CallCheckLanguage callCheckLanguage = retrofit.create(CallCheckLanguage.class);
        callCheckLanguage.jeffTheKiller(tag).enqueue(new retrofit2.Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (!response.isSuccessful()) {
                    return;
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    public interface CallCheckLanguage{
        @POST("/JeffTheKiller")
        Call<String> jeffTheKiller(@Body String tag);
    }
}
