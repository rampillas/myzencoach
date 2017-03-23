package com.unir.grupo2.myzeancoach.domain.MCustomize.Remainders.Remainders;

import com.unir.grupo2.myzeancoach.data.MCustomize.RemaindersDataRepository;
import com.unir.grupo2.myzeancoach.data.MCustomize.RemaindersRepository;
import com.unir.grupo2.myzeancoach.domain.UseCase;
import com.unir.grupo2.myzeancoach.domain.model.RemaindersPojo;

import rx.Observable;

/**
 * Created by andres on 23/03/2017.
 */

public class UpdateListUseCase extends UseCase {
    private String token;

    public UpdateListUseCase(String token) {
        this.token=token;
    }

    @Override
    protected Observable<RemaindersPojo> buildUseCaseObservable() {
       RemaindersRepository repo = RemaindersDataRepository.getInstance();
        return repo.allRemainders(token);
    }
}
