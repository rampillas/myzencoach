package com.unir.grupo2.myzeancoach.data.LoginInterfaceRetrofit;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by andres on 28/02/2017.
 */

public class RetrofitClient {

    private static Retrofit retrofit = null;
    private static final RetrofitClient INSTANCE = new RetrofitClient();

    public RetrofitClient getInstancia() {
        return this.INSTANCE;
    }

    public static Retrofit getClient(String baseUrl) {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(baseUrl)
                    .build();
        }
        return retrofit;
    }



}