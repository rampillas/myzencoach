package com.unir.grupo2.myzeancoach.data.MWelfare;

import com.unir.grupo2.myzeancoach.domain.model.PlanListWelfarePojo;

import okhttp3.RequestBody;
import rx.Observable;

/**
 * Created by Cesar on 09/03/2017.
 */


public interface WelfareRepository {
    Observable<PlanListWelfarePojo> allPlans();
    Observable<Void> updateExercise(RequestBody body);
    Observable<Void> finishPlan(String username, RequestBody body);
}

