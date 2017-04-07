package com.unir.grupo2.myzeancoach.domain.MLeisure;

import com.unir.grupo2.myzeancoach.data.MEssentialInfo.EssentialDataRepository;
import com.unir.grupo2.myzeancoach.data.MEssentialInfo.EssentialRepository;
import com.unir.grupo2.myzeancoach.domain.UseCase;

import rx.Observable;

/**
 * Created by Cesar on 15/03/2017.
 */

public class GetInterestsUseCase extends UseCase {

    String token;

    public GetInterestsUseCase(String token) {
        this.token = token;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        EssentialRepository repo = EssentialDataRepository.getInstance();
        return repo.ranking(token).first();
    }
}
