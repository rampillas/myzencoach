package com.unir.grupo2.myzeancoach.domain.MCustomize.Remainders.Remainders;

import com.unir.grupo2.myzeancoach.data.MCustomize.RemaindersDataRepository;
import com.unir.grupo2.myzeancoach.data.MCustomize.RemaindersRepository;
import com.unir.grupo2.myzeancoach.domain.UseCase;

import rx.Observable;

/**
 * Created by andres on 23/03/2017.
 */

public class UpdateListUseCase extends UseCase {
    private String token;
    private String url;

    public UpdateListUseCase(String url, String token) {
        this.token = token;
        this.url = url;
    }


    @Override
    protected Observable buildUseCaseObservable() {
        RemaindersRepository repo = RemaindersDataRepository.getInstance();
        return repo.allRemainders(url, token);
    }
}
