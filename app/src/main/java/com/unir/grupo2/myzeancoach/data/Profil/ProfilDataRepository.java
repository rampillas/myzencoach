package com.unir.grupo2.myzeancoach.data.Profil;

import com.unir.grupo2.myzeancoach.domain.model.EmoticonListPojo;

import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
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
                .baseUrl("http://demendezr.pythonanywhere.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)
                .build();
        service = retrofit.create(ProfilAPIService.class);
    }

    @Override
    public Observable<EmoticonListPojo> emoticon() {
        return service.getEmoticon("Bearer XID9TUxqU76zWc2wWDMqVFy2dFDdrK");
    }

    @Override
    public Observable<EmoticonListPojo> updateEmoticon() {
        String text = "{\n" +
                "\t\"name\":\"sat\",\n" +
                "\t\"is_positive\": false,\n" +
                "\t\"date\":\"2017-03-02\"\n" +
                "}\n";

        RequestBody body =
                RequestBody.create(MediaType.parse("text/plain"), text);

        return service.createEmoticon("application/json","Bearer XID9TUxqU76zWc2wWDMqVFy2dFDdrK","happy", true, "asd");
    }

}