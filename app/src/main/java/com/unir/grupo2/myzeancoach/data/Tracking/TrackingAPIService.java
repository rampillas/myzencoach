package com.unir.grupo2.myzeancoach.data.Tracking;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Cesar on 20/04/2017.
 */

public interface TrackingAPIService {

    @POST("/userConnections/")
    Observable<Void> connections(@Header("Content-Type") String contentType,
                                            @Header("Authorization") String token,
                                            @Body RequestBody body);

}
