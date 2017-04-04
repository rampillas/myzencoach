package com.unir.grupo2.myzeancoach.data.MCooperativeSol;

import com.unir.grupo2.myzeancoach.domain.model.Dilemma;
import com.unir.grupo2.myzeancoach.domain.model.DilemmaListPojo;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

/**
 * Created by Cesar on 27/03/2017.
 */

public class CooperativeDataRepository implements CooperativeRepository{

    CooperativeAPIService service;
    private static final String CONTENT_TYPE = "application/json";

    private static final CooperativeDataRepository INSTANCE = new CooperativeDataRepository();

    public static CooperativeDataRepository getInstance() {
        return INSTANCE;
    }

    private CooperativeDataRepository() {

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
        service = retrofit.create(CooperativeAPIService.class);
    }

    @Override
    public Observable<DilemmaListPojo> allDilemmas(String userName, String token, RequestBody body) {
        return service.getAllDilemmas(userName,CONTENT_TYPE,token,body);
    }

    @Override
    public Observable<Dilemma> addDilemma(String token, RequestBody body) {
        return service.createDilemma(CONTENT_TYPE,token,body);
    }

    @Override
    public Observable<Void> amendDilemma(String userName, String token, RequestBody body) {
        return service.updateDilemma(userName,CONTENT_TYPE,token,body);
    }

    @Override
    public Observable<Void> addComment(String userName, String token, RequestBody body) {
        return service.createComment(userName,CONTENT_TYPE,token,body);
    }

    @Override
    public Observable<Void> addPros(String userName, String token, RequestBody body) {
        return service.createPros(userName,CONTENT_TYPE,token,body);
    }

    @Override
    public Observable<Void> addCons(String userName, String token, RequestBody body) {
        return service.createCons(userName,CONTENT_TYPE,token,body);
    }

    @Override
    public Observable<Void> addLikeDilemma(String userName, String token, RequestBody body) {
        return service.addLike(userName,CONTENT_TYPE,token,body);
    }
}
