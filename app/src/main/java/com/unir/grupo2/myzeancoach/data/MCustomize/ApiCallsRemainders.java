package com.unir.grupo2.myzeancoach.data.MCustomize;

import com.unir.grupo2.myzeancoach.domain.model.RemaindersListPojo;
import com.unir.grupo2.myzeancoach.domain.model.RewardsListPojo;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Url;
import rx.Observable;

import static com.unir.grupo2.myzeancoach.domain.utils.Constants.BASE_URL_CUSTOMIZE_REMAINDERS;
import static com.unir.grupo2.myzeancoach.domain.utils.Constants.BASE_URL_CUSTOMIZE_REWARDS;

/**
 * Created by andres on 23/03/2017.
 */

public interface ApiCallsRemainders {
    @Headers("Content-Type: application/json")
    @GET(BASE_URL_CUSTOMIZE_REWARDS)
    Observable<RewardsListPojo> getRewards(@Header("Authorization") String token);

    @Headers("Content-Type: application/json")
    @GET
    Observable<RemaindersListPojo> getRemainders(@Url String url, @Header("Authorization") String token);

    @Headers("Content-Type: application/json")
    @POST(BASE_URL_CUSTOMIZE_REMAINDERS+"{username}/updateReminder/")
    Observable<Void> markAsCompleted(@Path("username") String user,
                                     @Header("Authorization") String token, @Body RequestBody rb);

    @Headers("Content-Type: application/json")
    @POST(BASE_URL_CUSTOMIZE_REWARDS)
    Observable<Void> setRewards(@Header("Authorization") String token,
                                @Body RequestBody body);

    @Headers("Content-Type: application/json")
    @POST(BASE_URL_CUSTOMIZE_REMAINDERS)
    Observable<Void> addRemainder(@Header("Authorization") String token,
                                  @Body RequestBody body);

    @Headers("Content-Type: application/json")
    @POST(BASE_URL_CUSTOMIZE_REMAINDERS+"{username}/addObservation/")
    Observable<Void> addObservations(@Path("username") String user,
                                     @Header("Authorization") String token,
                                     @Body RequestBody body);
}
