package com.unir.grupo2.myzeancoach.data.MEssentialInfo;

import com.unir.grupo2.myzeancoach.data.pojo.VideoListPojo;

import rx.Observable;

/**
 * Created by Cesar on 09/03/2017.
 */


public interface VideosRepository {
    Observable<VideoListPojo> videos(String token);
}

