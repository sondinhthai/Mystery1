package com.example.mystery1.control.remote;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.mystery1.control.rest.Callback;
import com.example.mystery1.models.Documents;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Part;
import retrofit2.http.Path;

public class RequestDocumentsManager {
    private Retrofit retrofit;

    public RequestDocumentsManager() {
        this.retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.29.153:8080/api/")
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    public void getAllDocument(Callback callback) {
        CallDocument callDocument = retrofit.create(CallDocument.class);
        callDocument.getAllDocument().enqueue(new retrofit2.Callback<List<Documents>>() {
            @Override
            public void onResponse(Call<List<Documents>> call, Response<List<Documents>> response) {
                callback.getDocument(response.body());
            }

            @Override
            public void onFailure(Call<List<Documents>> call, Throwable t) {

            }
        });
    }

    public void getDocument(OnFetchDataListener listener, String title) {
        CallDocument callDocument = retrofit.create(CallDocument.class);
        Call<List<Documents>> call = callDocument.getDocument(title);

        try {
            call.enqueue(new retrofit2.Callback<List<Documents>>() {
                @Override
                public void onResponse(@NonNull Call<List<Documents>> call, @NonNull Response<List<Documents>> response) {
                    if (!response.isSuccessful()) {
                        listener.onError("No data for this word " + title);
                    } else {
                        listener.onFetchData(response.body().get(0), response.message());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<List<Documents>> call, Throwable t) {
                    listener.onError("Request failed !!!");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface OnFetchDataListener {
        void onFetchData(Documents documents, String title);

        void onError(String title);
    }

    public interface CallDocument {
        @GET("Documents/all")
        Call<List<Documents>> getAllDocument();

        @GET("Documents/{title}")
        Call<List<Documents>> getDocument(
            @Path("title") String title
        );
    }
}
