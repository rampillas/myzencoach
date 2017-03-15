package com.unir.grupo2.myzeancoach.data.MEssentialInfo;

import com.unir.grupo2.myzeancoach.domain.model.TestListPojo;
import com.unir.grupo2.myzeancoach.domain.model.VideoListPojo;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PUT;
import rx.Observable;

/**
 * Created by Cesar on 09/03/2017.
 */

public interface EssentialAPIService {

    @GET("essential_information/videos")
    Observable<VideoListPojo> getVideos(@Header("Authorization") String token);

    @GET("essential_information/survey")
    Observable<TestListPojo> getTest(@Header("Authorization") String token);

   /* @FormUrlEncoded
    @PUT("essential_information/survey")
    Observable<Void> putTest(@Header("Content-Type") String contentType,
                                        @Header("Authorization") String token,
                                        @Field("description") String descriptionTest,
                                        @Field("score") int score);*/

    @PUT("/essential_information/videos/")
    Observable<Void> putVideo(@Header("Content-Type") String contentType,
                              @Header("Authorization") String token,
                              @Body RequestBody body);

}