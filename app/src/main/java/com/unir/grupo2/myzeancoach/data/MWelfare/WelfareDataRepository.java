package com.unir.grupo2.myzeancoach.data.MWelfare;

import com.unir.grupo2.myzeancoach.domain.model.PlanListWelfarePojo;
import com.unir.grupo2.myzeancoach.domain.utils.Constants;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

import static com.unir.grupo2.myzeancoach.domain.utils.Constants.URL_SERVER;

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
                .baseUrl(URL_SERVER)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)
                .build();
        service = retrofit.create(WelfareAPIService.class);
    }

    @Override
    public Observable<PlanListWelfarePojo> allPlans(String url, String token) {
        return service.getAllPlans(url, token);
    }

    @Override
    public Observable<Void> updateExercise(RequestBody body, String token) {
        return service.putExercise(Constants.CONTENT_TYPE,token,body);
    }

    @Override
    public Observable<Void> finishPlan(String username, String token,RequestBody body) {
        return service.finishPostPlan(username, Constants.CONTENT_TYPE,token,body);
    }

}