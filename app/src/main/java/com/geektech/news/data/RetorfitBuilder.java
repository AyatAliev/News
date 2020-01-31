package com.geektech.news.data;

import com.google.gson.Gson;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetorfitBuilder {

    private static RetrofitService service;
    private static OkHttpClient client;

    public static RetrofitService getService(){
        if (service == null) {
            service = buildRetorift();
        }
        return service;
    }

    private static RetrofitService buildRetorift(){
        return new Retrofit.Builder()
                .baseUrl("http://data.fixer.io/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(getClient())
                .build()
                .create(RetrofitService.class);
    }

    private static OkHttpClient getClient(){
        if (client == null)
            client = bilderClient();
        return client;
    }

    private static OkHttpClient bilderClient() {
        return new OkHttpClient.Builder()
                .callTimeout(5, TimeUnit.SECONDS)
                .readTimeout(20,TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .build();
    }


}
