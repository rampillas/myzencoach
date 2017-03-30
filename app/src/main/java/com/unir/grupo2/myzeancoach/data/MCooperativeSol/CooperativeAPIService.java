package com.unir.grupo2.myzeancoach.data.MCooperativeSol;

import com.unir.grupo2.myzeancoach.domain.model.Dilemma;
import com.unir.grupo2.myzeancoach.domain.model.DilemmaListPojo;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Cesar on 09/03/2017.
 */

public interface CooperativeAPIService {

    @POST("/solutions/dilemmas/{username}/getAllTypeDilemmas/")
    Observable<DilemmaListPojo> getAllDilemmas(@Path("username") String username,
                                               @Header("Content-Type") String contentType,
                                               @Header("Authorization") String token,
                                               @Body RequestBody body) ;

    @POST("/solutions/dilemmas/")
    Observable<Dilemma> createDilemma(@Header("Content-Type") String contentType,
                                      @Header("Authorization") String token,
                                      @Body RequestBody body) ;

    @POST("/solutions/dilemmas/{username}/changeDilemmaWithSuggestion/")
    Observable<Void> updateDilemma(@Path("username") String username,
                                               @Header("Content-Type") String contentType,
                                               @Header("Authorization") String token,
                                               @Body RequestBody body) ;

}