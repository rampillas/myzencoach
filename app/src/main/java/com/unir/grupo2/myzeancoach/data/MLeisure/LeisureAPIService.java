package com.unir.grupo2.myzeancoach.data.MLeisure;

import com.unir.grupo2.myzeancoach.domain.model.Event;
import com.unir.grupo2.myzeancoach.domain.model.EventListPojo;
import com.unir.grupo2.myzeancoach.domain.model.InterestListPojo;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Cesar on 09/03/2017.
 */

public interface LeisureAPIService {

    @POST("/freetime/events/")
    Observable<Event> putEvent(@Header("Content-Type") String contentType,
                               @Header("Authorization") String token,
                               @Body RequestBody body);

    @POST("/freetime/events/{username}/addLikeToEvent/")
    Observable<Event> putLike(@Path("username") String username,
                              @Header("Content-Type") String contentType,
                              @Header("Authorization") String token,
                              @Body RequestBody body);

    @POST("/freetime/events/{username}/quitLikeToEvent/")
    Observable<Event> deleteLike(@Path("username") String username,
                                 @Header("Content-Type") String contentType,
                                 @Header("Authorization") String token,
                                 @Body RequestBody body);

    @POST("/freetime/events/{username}/addCommentToEvent/")
    Observable<Void> putComment(@Path("username") String username,
                                @Header("Content-Type") String contentType,
                                @Header("Authorization") String token,
                                @Body RequestBody body);

    @POST("/freetime/events/{username}/addInterestsUser/")
    Observable<Void> putInterest(@Path("username") String username,
                                 @Header("Content-Type") String contentType,
                                 @Header("Authorization") String token,
                                 @Body RequestBody body);

    @POST("/freetime/events/{username}/getInterestsUser/")
    Observable<InterestListPojo> getInterest(@Path("username") String username,
                                             @Header("Content-Type") String contentType,
                                             @Header("Authorization") String token);

    @POST("/freetime/events/{username}/getEvents/")
    Observable<EventListPojo> getEvents(@Path("username") String username,
                                        @Header("Content-Type") String contentType,
                                        @Header("Authorization") String token);

    @DELETE("/freetime/events/")
    Observable<Void> deleteEvent(@Header("Content-Type") String contentType,
                                @Header("Authorization") String token,
                                @Body RequestBody body);
}