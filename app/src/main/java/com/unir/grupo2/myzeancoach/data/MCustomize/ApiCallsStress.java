package com.unir.grupo2.myzeancoach.data.MCustomize;

import com.unir.grupo2.myzeancoach.domain.model.StressCoachResponsePojo;
import com.unir.grupo2.myzeancoach.domain.model.StressQuestionsListPojo;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Url;
import rx.Observable;

import static com.unir.grupo2.myzeancoach.domain.utils.Constants.BASE_URL_CUSTOMIZE_FOLLOWUP;
import static com.unir.grupo2.myzeancoach.domain.utils.Constants.BASE_URL_CUSTOMIZE_STRESS;

/**
 * Created by andres on 28/03/2017.
 */

public interface ApiCallsStress {
    @Headers("Content-Type: application/json")
    @POST(BASE_URL_CUSTOMIZE_STRESS)
    Observable<Void> setQuestion(@Header("Authorization") String token, @Body RequestBody body);

    @Headers("Content-Type: application/json")
    @GET
    Observable<StressQuestionsListPojo> getQuestions(@Url String url, @Header("Authorization") String token);

    @Headers("Content-Type: application/json")
    @POST(BASE_URL_CUSTOMIZE_STRESS+"{username}/registerAnswerUser/")
    Observable<Void> setAnswer(@Path("username") String user,
                                     @Header("Authorization") String token, @Body RequestBody rb);

    @Headers("Content-Type: application/json")
    @GET(BASE_URL_CUSTOMIZE_FOLLOWUP)
    Observable<StressCoachResponsePojo> getCoachResponse(@Header("Authorization") String token);

}
