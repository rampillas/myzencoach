package com.unir.grupo2.myzeancoach.data.MWelfare;

import com.unir.grupo2.myzeancoach.domain.model.PlanListWelfarePojo;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

/**
 * Created by Cesar on 09/03/2017.
 */

public class WelfareDataRepository implements WelfareRepository {

    WelfareAPIService service;

    private static final WelfareDataRepository INSTANCE = new WelfareDataRepository();

    public static WelfareRepository getInstance() {
        return INSTANCE;
    }

    private WelfareDataRepository() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        interceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://demendezr.pythonanywhere.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)
                .build();
        service = retrofit.create(WelfareAPIService.class);
    }

    @Override
    public Observable<PlanListWelfarePojo> allPlans() {
        return service.getAllPlans("Bearer XID9TUxqU76zWc2wWDMqVFy2dFDdrK");
    }

    @Override
    public Observable<Void> updateExercise(RequestBody body) {
        return service.putExercise("application/json","Bearer XID9TUxqU76zWc2wWDMqVFy2dFDdrK",body);
    }

    @Override
    public Observable<Void> finishPlan(String username, RequestBody body) {
        return service.finishPostPlan(username, "application/json","Bearer XID9TUxqU76zWc2wWDMqVFy2dFDdrK",body);
    }

}