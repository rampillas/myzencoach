package com.unir.grupo2.myzeancoach.domain.MWelfare;

import com.unir.grupo2.myzeancoach.data.MWelfare.WelfareDataRepository;
import com.unir.grupo2.myzeancoach.data.MWelfare.WelfareRepository;
import com.unir.grupo2.myzeancoach.domain.UseCase;
import com.unir.grupo2.myzeancoach.domain.model.PlanListWelfarePojo;
import com.unir.grupo2.myzeancoach.domain.model.PlanWelfare;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Cesar on 15/03/2017.
 */

public class AllPlansUseCase extends UseCase {

    public AllPlansUseCase() {
    }

    @Override
    protected Observable buildUseCaseObservable() {
        WelfareRepository repo = WelfareDataRepository.getInstance();
        return repo.allPlans().map(new Func1<PlanListWelfarePojo, List<PlanWelfare>>() {
            @Override
            public List<PlanWelfare> call(PlanListWelfarePojo planListWelfarePojo) {

                if (planListWelfarePojo.getCount() > 0){
                    return planListWelfarePojo.getPlans();
                }else{
                    return null;
                }
            }
        });
    }
}
