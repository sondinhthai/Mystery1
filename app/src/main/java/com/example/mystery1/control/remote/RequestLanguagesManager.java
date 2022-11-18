package com.example.mystery1.control.remote;

import android.util.Log;

import com.example.mystery1.control.rest.Callback;
import com.example.mystery1.models.CurrentTag;
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

public class RequestLanguagesManager {
    private Retrofit retrofit;

    public RequestLanguagesManager() {
        this.retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.29.153:8080/api/")
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();
    }

    public void getAllLanguages(Callback callback) {
        CallLanguage callLanguage = retrofit.create(CallLanguage.class);
        callLanguage.getAllLanguages().enqueue(new retrofit2.Callback<List<Language>>() {
            @Override
            public void onResponse(Call<List<Language>> call, Response<List<Language>> response) {
                callback.getLanguage(response.body());
            }

            @Override
            public void onFailure(Call<List<Language>> call, Throwable t) {

            }
        });
    }

    public void saveCurrentTag(CurrentTag currentTag){
        CallLanguage callLanguage = retrofit.create(CallLanguage.class);
        callLanguage.saveCurrentTag(currentTag).enqueue(new retrofit2.Callback<CurrentTag>() {
            @Override
            public void onResponse(Call<CurrentTag> call, Response<CurrentTag> response) {
                if (!response.isSuccessful()) {
                    return;
                }
            }

            @Override
            public void onFailure(Call<CurrentTag> call, Throwable t) {

            }
        });
    }

    public interface CallLanguage{
        @GET("Languages/all")
        Call<List<Language>> getAllLanguages();

        @POST("CurrentTag")
        Call<CurrentTag> saveCurrentTag(@Body CurrentTag currentTag);
    }
}
