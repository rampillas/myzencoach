package com.unir.grupo2.myzeancoach.data.MWelfare;

import com.unir.grupo2.myzeancoach.domain.model.PlanListWelfarePojo;

import rx.Observable;

/**
 * Created by Cesar on 09/03/2017.
 */


public interface WelfareRepository {
    Observable<PlanListWelfarePojo> allPlans();
}

