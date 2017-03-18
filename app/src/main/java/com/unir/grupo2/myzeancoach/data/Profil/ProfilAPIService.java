package com.unir.grupo2.myzeancoach.data.Profil;

import com.unir.grupo2.myzeancoach.domain.model.Emoticon;
import com.unir.grupo2.myzeancoach.domain.model.EmoticonListPojo;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Cesar on 09/03/2017.
 */

public interface ProfilAPIService {

    @GET("/emoticon/")
    Observable<EmoticonListPojo> getEmoticon(@Header("Authorization") String token);

    @POST("/emoticon/")
    Observable<Emoticon> createEmoticon(@Header("Content-Type") String contentType,
                                        @Header("Authorization") String token,
                                        @Body RequestBody body);

}