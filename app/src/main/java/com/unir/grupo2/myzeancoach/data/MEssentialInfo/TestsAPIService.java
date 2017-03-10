package com.unir.grupo2.myzeancoach.data.MEssentialInfo;

import com.unir.grupo2.myzeancoach.domain.model.TestListPojo;

import retrofit2.http.GET;
import retrofit2.http.Header;
import rx.Observable;

/**
 * Created by Cesar on 10/03/2017.
 */

public interface TestsAPIService {

    @GET("essential_information/survey")
    Observable<TestListPojo> getTest(@Header("Authorization") String token);
}
