package com.unir.grupo2.myzeancoach.data.MCustomize;

import com.unir.grupo2.myzeancoach.domain.model.StressCoachResponsePojo;
import com.unir.grupo2.myzeancoach.domain.model.StressQuestionsListPojo;

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
 * Created by andres on 28/03/2017.
 */

public class StressDataRepository implements StressRepository {

    ApiCallsStress service;

    private static final StressDataRepository INSTANCE = new StressDataRepository();

    public static StressDataRepository getInstance() {
        return INSTANCE;
    }

    private StressDataRepository() {

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
        service = retrofit.create(ApiCallsStress.class);
    }


    @Override
    public Observable<Void> addQuestions(RequestBody body, String token) {
        return service.setQuestion(token, body);
    }

    @Override
    public Observable<StressQuestionsListPojo> getStressQuestions(String url,String token) {
        return service.getQuestions(url,token);
    }

    @Override
    public Observable<Void> setAnswers(String username, RequestBody body, String token) {
        return service.setAnswer(username, token, body);
    }

    @Override
    public Observable<StressCoachResponsePojo> getResponseCoach(String token) {
        return service.getCoachResponse(token);
    }
}