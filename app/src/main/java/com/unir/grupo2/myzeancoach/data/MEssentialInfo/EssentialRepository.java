package com.unir.grupo2.myzeancoach.data.MEssentialInfo;

import com.unir.grupo2.myzeancoach.domain.model.TestListPojo;
import com.unir.grupo2.myzeancoach.domain.model.VideoListPojo;

import okhttp3.RequestBody;
import rx.Observable;

/**
 * Created by Cesar on 09/03/2017.
 */


public interface EssentialRepository {
    Observable<VideoListPojo> videos(String token);
    Observable<TestListPojo> tests(String token);
    //Observable<Void> updateTest(String contentType, String token, String descriptionTest, int score);
    Observable<Void> updateVideo(String contentType, String token, RequestBody body);
}

