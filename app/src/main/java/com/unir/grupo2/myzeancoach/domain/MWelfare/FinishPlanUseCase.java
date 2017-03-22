package com.unir.grupo2.myzeancoach.domain.MWelfare;

import com.unir.grupo2.myzeancoach.data.MWelfare.WelfareDataRepository;
import com.unir.grupo2.myzeancoach.data.MWelfare.WelfareRepository;
import com.unir.grupo2.myzeancoach.domain.UseCase;

import okhttp3.RequestBody;
import rx.Observable;

/**
 * Created by Cesar on 21/03/2017.
 */

public class FinishPlanUseCase extends UseCase {

    private RequestBody body;
    private String username;

    public FinishPlanUseCase(String username, RequestBody body) {
        this.body = body;
        this.username = username;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        WelfareRepository repo = WelfareDataRepository.getInstance();
        return repo.finishPlan(username,body).first();
    }
}
