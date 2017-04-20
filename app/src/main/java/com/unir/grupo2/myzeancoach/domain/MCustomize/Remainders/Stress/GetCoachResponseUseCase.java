package com.unir.grupo2.myzeancoach.domain.MCustomize.Remainders.Stress;

import com.unir.grupo2.myzeancoach.data.MCustomize.StressDataRepository;
import com.unir.grupo2.myzeancoach.data.MCustomize.StressRepository;
import com.unir.grupo2.myzeancoach.domain.UseCase;
import com.unir.grupo2.myzeancoach.domain.model.StressCoachResponse;
import com.unir.grupo2.myzeancoach.domain.model.StressCoachResponsePojo;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by andres on 28/03/2017.
 */

public class GetCoachResponseUseCase extends UseCase {
    String token;

    public GetCoachResponseUseCase(String token) {
        this.token = token;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        StressRepository repo = StressDataRepository.getInstance();
        return repo.getResponseCoach(token).map(new Func1<StressCoachResponsePojo, List<StressCoachResponse>>() {
            @Override
            public List<StressCoachResponse> call(StressCoachResponsePojo stressCoachResponsePojo) {

                List<StressCoachResponse> stressCoachResponses = new ArrayList<>();

                for (int i = 0; i < stressCoachResponsePojo.getCount(); i++) {
                    stressCoachResponses.add(stressCoachResponsePojo.getResults().get(i));
                }
                return stressCoachResponses;
            }
        });
    }
}
