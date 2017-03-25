package com.unir.grupo2.myzeancoach.data.MCustomize;

import com.unir.grupo2.myzeancoach.domain.model.RemaindersListPojo;
import com.unir.grupo2.myzeancoach.domain.model.RewardsItem;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by andres on 23/03/2017.
 */

public interface ApiCallsRemainders {
    @Headers("Content-Type: application/json")
    @GET("personalization/rewards")
    Observable<RewardsItem> getRewards(@Header("Authorization") String token);

    @Headers("Content-Type: application/json")
    @GET("personalization/reminders")
    Observable<RemaindersListPojo> getRemainders(@Header("Authorization") String token);

    @Headers("Content-Type: application/json")
    @POST("personalization/reminders/{username}/updateReminder/")
    Observable<Void> markAsCompleted(@Path("username") String user,
                                     @Header("Authorization") String token, @Body RequestBody rb);

    @Headers("Content-Type: application/json")
    @POST("personalization/rewards/")
    Observable<Void> setRewards(@Header("Authorization") String token,
                                @Body RequestBody body);

    @Headers("Content-Type: application/json")
    @POST("personalization/reminders/<username>/addObservation/")
    Observable<Void> addRemainder(@Path("username") String user,
                                  @Header("Authorization") String token,
                                  @Body RequestBody body);

    @Headers("Content-Type: application/json")
    @POST("personalization/reminders/<username>/addObservation/{username}")
    Observable<Void> addObservations(@Path("username") String user,
                                     @Header("Authorization") String token,
                                     @Body RequestBody body);
}
