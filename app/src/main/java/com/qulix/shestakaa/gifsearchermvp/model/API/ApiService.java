package com.qulix.shestakaa.gifsearchermvp.model.API;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiService {

    private static final String BASE_URL = "http://api.giphy.com/v1/gifs/";
    private static Retrofit sRetrofit = null;

    public static Retrofit getClient() {

        if (sRetrofit == null) {
            sRetrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                                              .addConverterFactory(GsonConverterFactory.create())
                                              .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                                              .build();
        }

        return sRetrofit;

    }
}
