package com.unir.grupo2.myzeancoach.data.MEssentialInfo;

import com.unir.grupo2.myzeancoach.domain.model.TestListPojo;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

/**
 * Created by Cesar on 10/03/2017.
 */

public class TestsDataRepository implements TestsRepository{

    TestsAPIService service;

    private static final TestsDataRepository INSTANCE = new TestsDataRepository();

    public static TestsRepository getInstance() {
        return INSTANCE;
    }

    private TestsDataRepository() {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://demendezr.pythonanywhere.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)
                .build();
        service = retrofit.create(TestsAPIService.class);
    }


    @Override
    public Observable<TestListPojo> tests(String token) {
        return service.getTest(token);
    }
}
