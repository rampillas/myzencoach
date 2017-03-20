package com.unir.grupo2.myzeancoach.data.MWelfare;

import com.unir.grupo2.myzeancoach.domain.model.PlanListWelfarePojo;

import retrofit2.http.GET;
import retrofit2.http.Header;
import rx.Observable;

/**
 * Created by Cesar on 09/03/2017.
 */

public interface WelfareAPIService {

    @GET("/minfulness/plans/")
    Observable<PlanListWelfarePojo> getAllPlans(@Header("Authorization") String token);

}