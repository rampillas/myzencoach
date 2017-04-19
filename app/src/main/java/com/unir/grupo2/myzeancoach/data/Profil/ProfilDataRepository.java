package com.unir.grupo2.myzeancoach.data.Profil;

import com.unir.grupo2.myzeancoach.domain.model.Emoticon;
import com.unir.grupo2.myzeancoach.domain.model.EmoticonListPojo;
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

public class ProfilDataRepository implements ProfilRepository {

    ProfilAPIService service;

    private static final ProfilDataRepository INSTANCE = new ProfilDataRepository();

    public static ProfilRepository getInstance() {
        return INSTANCE;
    }

    private ProfilDataRepository() {

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
        service = retrofit.create(ProfilAPIService.class);
    }

    @Override
    public Observable<EmoticonListPojo> emoticon(String token) {
        return service.getEmoticon(token);
    }

    @Override
    public Observable<Emoticon> updateEmoticon(String token, RequestBody body) {
        return service.createEmoticon(Constants.CONTENT_TYPE,token,body);
    }

}