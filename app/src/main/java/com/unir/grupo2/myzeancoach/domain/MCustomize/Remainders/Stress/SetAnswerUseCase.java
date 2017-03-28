package com.unir.grupo2.myzeancoach.domain.MCustomize.Remainders.Stress;

import com.unir.grupo2.myzeancoach.data.MCustomize.StressDataRepository;
import com.unir.grupo2.myzeancoach.data.MCustomize.StressRepository;
import com.unir.grupo2.myzeancoach.domain.UseCase;

import okhttp3.RequestBody;
import rx.Observable;

/**
 * Created by andres on 28/03/2017.
 */

public class SetAnswerUseCase extends UseCase {
    String token;
    RequestBody body;
    String username;

    public SetAnswerUseCase(String token, RequestBody body, String username) {
        this.token = token;
        this.body = body;
        this.username = username;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        StressRepository repo = StressDataRepository.getInstance();
        return repo.setAnswers(username, body,token);
    }
}
