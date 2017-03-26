package com.unir.grupo2.myzeancoach.data.MCustomize;

import com.unir.grupo2.myzeancoach.domain.model.RemainderItem;
import com.unir.grupo2.myzeancoach.domain.model.RemaindersListPojo;
import com.unir.grupo2.myzeancoach.domain.model.RewardsItem;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

/**
 * Created by andres on 23/03/2017.
 */

public class RemaindersDataRepository implements RemaindersRepository {

    ApiCallsRemainders service;

    private static final RemaindersDataRepository INSTANCE = new RemaindersDataRepository();

    public static RemaindersRepository getInstance() {
        return INSTANCE;
    }

    private RemaindersDataRepository() {

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
        service = retrofit.create(ApiCallsRemainders.class);
    }

    @Override
    public Observable<RemaindersListPojo> allRemainders(String token) {
        return service.getRemainders(token);
    }

    @Override
    public Observable<Void> setIsFinished(String user, RequestBody body, String token) {
        return service.markAsCompleted(user, token, body);
    }

    @Override
    public Observable<Void> addObservations(String username, RequestBody body, String token) {
        return service.addObservations(username, token, body);
    }

    @Override
    public Observable<Void> addNewRemainder(String username, RequestBody body, String token) {
        return service.addRemainder(token, body);
    }

    @Override
    public Observable<Void> setRewards(RequestBody body, String token) {
        return service.setRewards(token, body);
    }

    @Override
    public Observable<RewardsItem> getRewards(String token) {
        return service.getRewards(token);
    }


}
