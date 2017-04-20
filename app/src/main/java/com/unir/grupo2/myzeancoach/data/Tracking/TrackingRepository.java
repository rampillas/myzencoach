package com.unir.grupo2.myzeancoach.data.Tracking;

import okhttp3.RequestBody;
import rx.Observable;

/**
 * Created by Cesar on 20/04/2017.
 */

public interface TrackingRepository {

    Observable<Void> connectionCount(String token, RequestBody body);
}
