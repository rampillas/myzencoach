package com.unir.grupo2.myzeancoach.data.MEssentialInfo;

import com.unir.grupo2.myzeancoach.domain.model.Ranking;
import com.unir.grupo2.myzeancoach.domain.model.TestListPojo;
import com.unir.grupo2.myzeancoach.domain.model.VideoListPojo;

import java.util.List;

import okhttp3.RequestBody;
import rx.Observable;

/**
 * Created by Cesar on 09/03/2017.
 */


public interface EssentialRepository {
    Observable<VideoListPojo> videos(String url, String token);
    Observable<TestListPojo> tests(String url, String token);
    Observable<Void> updateTest(String token, RequestBody body);
    Observable<Void> updateVideo(String token, RequestBody body);
    Observable<List<Ranking>> ranking(String token);
}

