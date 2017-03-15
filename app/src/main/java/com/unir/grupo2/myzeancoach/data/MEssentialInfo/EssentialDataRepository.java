package com.unir.grupo2.myzeancoach.data.MEssentialInfo;

import com.unir.grupo2.myzeancoach.domain.model.TestListPojo;
import com.unir.grupo2.myzeancoach.domain.model.VideoListPojo;

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

public class EssentialDataRepository implements EssentialRepository {

    EssentialAPIService service;

    private static final EssentialDataRepository INSTANCE = new EssentialDataRepository();

    public static EssentialRepository getInstance() {
        return INSTANCE;
    }

    private EssentialDataRepository() {

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://demendezr.pythonanywhere.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)
                .build();
        service = retrofit.create(EssentialAPIService.class);
    }


    @Override
    public Observable<VideoListPojo> videos(String token) {
        return service.getVideos(token);
    }

    @Override
    public Observable<TestListPojo> tests(String token) {
        return service.getTest(token);
    }

  /*  @Override
    public Observable updateTest(String contentType, String token, String descriptionTest, int score) {
        return service.putTest(contentType,token, descriptionTest, score);
    }*/

    @Override
    public Observable<Void> updateVideo(String contentType, String token, RequestBody body) {
        return service.putVideo(contentType, token, body);
    }
}