package com.unir.grupo2.myzeancoach.domain.MWelfare;

import com.unir.grupo2.myzeancoach.data.MWelfare.WelfareDataRepository;
import com.unir.grupo2.myzeancoach.data.MWelfare.WelfareRepository;
import com.unir.grupo2.myzeancoach.domain.UseCase;

import okhttp3.RequestBody;
import rx.Observable;

/**
 * Created by Cesar on 18/03/2017.
 */

public class UpdateExerciseUseCase extends UseCase {

    private RequestBody body;
    private String token;

    public UpdateExerciseUseCase(RequestBody body, String token) {
        this.body = body;
        this.token = token;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        WelfareRepository repo = WelfareDataRepository.getInstance();
        return repo.updateExercise(body, token).first();
    }
}
