package com.unir.grupo2.myzeancoach.data.MCustomize;

import com.unir.grupo2.myzeancoach.domain.model.RemaindersPojo;

import okhttp3.RequestBody;
import rx.Observable;

/**
 * Created by andres on 23/03/2017.
 */

public interface RemaindersRepository {
    Observable<RemaindersPojo> allRemainders(String token);
    Observable<Void> setIsFinished(String user,RequestBody body,String token);
    Observable<Void> addObservations(String username,RequestBody body,String token);
    Observable<Void> addNewRemainder(String username,RequestBody body,String token);
    Observable<Void> setRewards(RequestBody body,String token);
}
