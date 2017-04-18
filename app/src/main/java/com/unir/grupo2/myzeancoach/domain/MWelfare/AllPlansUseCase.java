package com.unir.grupo2.myzeancoach.domain.MWelfare;

import com.unir.grupo2.myzeancoach.data.MWelfare.WelfareDataRepository;
import com.unir.grupo2.myzeancoach.data.MWelfare.WelfareRepository;
import com.unir.grupo2.myzeancoach.domain.UseCase;

import rx.Observable;

/**
 * Created by Cesar on 15/03/2017.
 */

public class AllPlansUseCase extends UseCase {

    private String url;
    private String token;

    public AllPlansUseCase(String url, String token) {
        this.url = url;
        this.token = token;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        WelfareRepository repo = WelfareDataRepository.getInstance();
        return repo.allPlans(url, token).first();
    }
}
