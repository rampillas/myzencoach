package com.unir.grupo2.myzeancoach.domain.MCustomize.Remainders.Remainders;

import com.unir.grupo2.myzeancoach.data.MCustomize.RemaindersDataRepository;
import com.unir.grupo2.myzeancoach.data.MCustomize.RemaindersRepository;
import com.unir.grupo2.myzeancoach.domain.UseCase;

import okhttp3.RequestBody;
import rx.Observable;

/**
 * Created by andres on 23/03/2017.
 */

public class NewRemainderUserCase extends UseCase {
    String user;
    String token;
    RequestBody body;

    public NewRemainderUserCase(String user, String token, RequestBody body) {
        this.user = user;
        this.token = token;
        this.body = body;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        RemaindersRepository repo = RemaindersDataRepository.getInstance();
        return repo.addNewRemainder(user, body, token);
    }
}
