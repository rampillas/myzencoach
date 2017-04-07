package com.unir.grupo2.myzeancoach.data.MLeisure;

import com.unir.grupo2.myzeancoach.domain.model.Event;
import com.unir.grupo2.myzeancoach.domain.model.EventListPojo;
import com.unir.grupo2.myzeancoach.domain.model.InterestListPojo;

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

public class LeisureDataRepository implements LeisureRepository{

    LeisureAPIService service;
    private static final String CONTENT_TYPE = "application/json";

    private static final LeisureDataRepository INSTANCE = new LeisureDataRepository();

    public static LeisureRepository getInstance() {
        return INSTANCE;
    }

    private LeisureDataRepository() {

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
        service = retrofit.create(LeisureAPIService.class);
    }


    @Override
    public Observable<Event> createEvent(String token, RequestBody body) {
        return service.putEvent(CONTENT_TYPE,token,body);
    }

    @Override
    public Observable<Event> addLike(String userName, String token, RequestBody body) {
        return service.putLike(userName,CONTENT_TYPE,token,body);
    }

    @Override
    public Observable<Event> deleteLike(String userName, String token, RequestBody body) {
        return service.deleteLike(userName,CONTENT_TYPE,token,body);
    }

    @Override
    public Observable<Void> createComment(String userName, String token, RequestBody body) {
        return service.putComment(userName,CONTENT_TYPE,token,body);
    }

    @Override
    public Observable<Void> addInterest(String userName, String token, RequestBody body) {
        return service.putInterest(userName,CONTENT_TYPE,token,body);
    }

    @Override
    public Observable<InterestListPojo> interests(String userName, String token) {
        return service.getInterest(userName,CONTENT_TYPE,token);
    }

    @Override
    public Observable<EventListPojo> events(String userName, String token) {
        return service.getEvents(userName,CONTENT_TYPE,token);
    }

    @Override
    public Observable<Void> removeEvent(String token, RequestBody body) {
        return service.deleteEvent(CONTENT_TYPE,token,body);
    }
}