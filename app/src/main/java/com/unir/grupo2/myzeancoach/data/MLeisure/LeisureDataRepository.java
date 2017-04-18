package com.unir.grupo2.myzeancoach.data.MLeisure;

import com.unir.grupo2.myzeancoach.domain.model.Event;
import com.unir.grupo2.myzeancoach.domain.model.EventListPojo;
import com.unir.grupo2.myzeancoach.domain.model.InterestListPojo;
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

public class LeisureDataRepository implements LeisureRepository{

    LeisureAPIService service;

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
                .baseUrl(URL_SERVER)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)
                .build();
        service = retrofit.create(LeisureAPIService.class);
    }


    @Override
    public Observable<Event> createEvent(String token, RequestBody body) {
        return service.putEvent(Constants.CONTENT_TYPE,token,body);
    }

    @Override
    public Observable<Event> addLike(String userName, String token, RequestBody body) {
        return service.putLike(userName,Constants.CONTENT_TYPE,token,body);
    }

    @Override
    public Observable<Event> deleteLike(String userName, String token, RequestBody body) {
        return service.deleteLike(userName,Constants.CONTENT_TYPE,token,body);
    }

    @Override
    public Observable<Void> createComment(String userName, String token, RequestBody body) {
        return service.putComment(userName,Constants.CONTENT_TYPE,token,body);
    }

    @Override
    public Observable<Void> addInterest(String userName, String token, RequestBody body) {
        return service.putInterest(userName,Constants.CONTENT_TYPE,token,body);
    }

    @Override
    public Observable<Void> deleteAllInterest(String userName, String token, RequestBody body) {
        return service.deleteAllIntereses(userName,Constants.CONTENT_TYPE,token,body);
    }

    @Override
    public Observable<InterestListPojo> interests(String userName, String token) {
        return service.getInterest(userName,Constants.CONTENT_TYPE,token);
    }

    @Override
    public Observable<EventListPojo> events(String userName, String token) {
        return service.getEvents(userName,Constants.CONTENT_TYPE,token);
    }

    @Override
    public Observable<Void> removeEvent(String token, RequestBody body) {
        return service.deleteEvent(Constants.CONTENT_TYPE,token,body);
    }
}