package com.unir.grupo2.myzeancoach.data.MWelfare;

import com.unir.grupo2.myzeancoach.domain.model.PlanListWelfarePojo;

import okhttp3.RequestBody;
import rx.Observable;

/**
 * Created by Cesar on 09/03/2017.
 */


public interface WelfareRepository {
    Observable<PlanListWelfarePojo> allPlans(String url, String token);
    Observable<Void> updateExercise(RequestBody body,String token);
    Observable<Void> finishPlan(String username,String token, RequestBody body);
}

