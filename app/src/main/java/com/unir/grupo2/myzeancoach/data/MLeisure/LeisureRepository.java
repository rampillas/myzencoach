package com.unir.grupo2.myzeancoach.data.MLeisure;

import com.unir.grupo2.myzeancoach.domain.model.Event;
import com.unir.grupo2.myzeancoach.domain.model.EventListPojo;
import com.unir.grupo2.myzeancoach.domain.model.InterestListPojo;

import okhttp3.RequestBody;
import rx.Observable;

/**
 * Created by Cesar on 09/03/2017.
 */


public interface LeisureRepository {
    Observable<Event> createEvent(String token, RequestBody body);
    Observable<Event> addLike(String userName, String token, RequestBody body);
    Observable<Event> deleteLike(String userName, String token, RequestBody body);
    Observable<Void> createComment(String userName, String token, RequestBody body);
    Observable<Void> addInterest(String userName, String token, RequestBody body);
    Observable<Void> deleteAllInterest(String userName, String token, RequestBody body);
    Observable<InterestListPojo> interests(String userName, String token);
    Observable<EventListPojo> events(String userName, String token);
    Observable<Void> removeEvent(String token, RequestBody body);
}

