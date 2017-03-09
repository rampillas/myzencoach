package com.unir.grupo2.myzeancoach.data.MEssentialInfo;

import com.unir.grupo2.myzeancoach.data.pojo.VideoListPojo;

import retrofit2.http.GET;
import retrofit2.http.Header;
import rx.Observable;

/**
 * Created by Cesar on 09/03/2017.
 */

public interface VideosAPIService {

    @GET("essential_information/videos")
    Observable<VideoListPojo> getVideos(@Header("Authorization") String token);

}