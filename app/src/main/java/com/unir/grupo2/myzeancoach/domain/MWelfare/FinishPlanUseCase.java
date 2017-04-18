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

    private String username;
    private String token;
    private RequestBody body;

    public FinishPlanUseCase(String username, String token, RequestBody body) {
        this.username = username;
        this.token = token;
        this.body = body;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        WelfareRepository repo = WelfareDataRepository.getInstance();
        return repo.finishPlan(username,token,body).first();
    }
}
