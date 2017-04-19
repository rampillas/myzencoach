package com.unir.grupo2.myzeancoach.domain.MCustomize.Remainders.Stress;

import com.unir.grupo2.myzeancoach.data.MCustomize.StressDataRepository;
import com.unir.grupo2.myzeancoach.data.MCustomize.StressRepository;
import com.unir.grupo2.myzeancoach.domain.UseCase;

import rx.Observable;

/**
 * Created by andres on 28/03/2017.
 */

public class UpdateQuestionsListUseCase extends UseCase {
    String token;
    String url;

    public UpdateQuestionsListUseCase(String url,String token) {
        this.url = url;
        this.token = token;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        StressRepository repo = StressDataRepository.getInstance();
        return repo.getStressQuestions(url, token);
    }
}
