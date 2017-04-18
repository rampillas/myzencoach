package com.unir.grupo2.myzeancoach.data.MWelfare;

import com.unir.grupo2.myzeancoach.domain.model.PlanListWelfarePojo;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by Cesar on 09/03/2017.
 */

public interface WelfareAPIService {

    @GET
    Observable<PlanListWelfarePojo> getAllPlans
            (@Url String url,
             @Header("Authorization") String token);

    @PUT("/minfulness/plans/")
    Observable<Void> putExercise(@Header("Content-Type") String contentType,
                                 @Header("Authorization") String token,
                                 @Body RequestBody body);

    @POST("/minfulness/plans/{username}/finishPlan/")
    Observable<Void> finishPostPlan(@Path("username") String username,
                                    @Header("Content-Type") String contentType,
                                    @Header("Authorization") String token,
                                    @Body RequestBody body);

}