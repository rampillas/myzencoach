package com.unir.grupo2.myzeancoach.domain.MCustomize.Remainders.Remainders;

import com.unir.grupo2.myzeancoach.data.MCustomize.RemaindersDataRepository;
import com.unir.grupo2.myzeancoach.data.MCustomize.RemaindersRepository;
import com.unir.grupo2.myzeancoach.domain.UseCase;

import okhttp3.RequestBody;
import rx.Observable;

/**
 * Created by andres on 23/03/2017.
 */

public class SetRemainderFinishedUseCase extends UseCase {
    String user;
    String token;
    RequestBody body;

    public SetRemainderFinishedUseCase(String user, String token, RequestBody body) {
        this.user = user;
        this.token = token;
        this.body = body;
    }

    @Override
    protected Observable<Void> buildUseCaseObservable() {
        RemaindersRepository repo = RemaindersDataRepository.getInstance();
        return repo.setIsFinished(user, body, token);
    }
}
