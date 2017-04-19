package com.unir.grupo2.myzeancoach.data.MEssentialInfo;

import com.unir.grupo2.myzeancoach.domain.model.Ranking;
import com.unir.grupo2.myzeancoach.domain.model.TestListPojo;
import com.unir.grupo2.myzeancoach.domain.model.VideoListPojo;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PUT;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by Cesar on 09/03/2017.
 */

public interface EssentialAPIService {

    @GET()
    Observable<VideoListPojo> getVideos
            (@Url String url,
             @Header("Authorization") String token);

    @GET()
    Observable<TestListPojo> getTest
            (@Url String url,
             @Header("Authorization") String token);

    @PUT("/essential_information/survey/")
    Observable<Void> putTest(@Header("Content-Type") String contentType,
                             @Header("Authorization") String token,
                             @Body RequestBody body);

    @PUT("/essential_information/videos/")
    Observable<Void> putVideo(@Header("Content-Type") String contentType,
                              @Header("Authorization") String token,
                              @Body RequestBody body);

    @GET("/essential_information/survey/{username}/ranking/")
    Observable<List<Ranking>> getRanking(@Header("Authorization") String token);

}