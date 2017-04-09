package com.unir.grupo2.myzeancoach.domain.MLeisure;

import com.unir.grupo2.myzeancoach.data.MLeisure.LeisureDataRepository;
import com.unir.grupo2.myzeancoach.data.MLeisure.LeisureRepository;
import com.unir.grupo2.myzeancoach.domain.UseCase;

import okhttp3.RequestBody;
import rx.Observable;

/**
 * Created by Cesar on 15/03/2017.
 */

public class DeleteAllInteresesUseCase extends UseCase {

    String userName;
    String token;
    RequestBody body;

    public DeleteAllInteresesUseCase(String userName, String token, RequestBody body) {
        this.userName = userName;
        this.token = token;
        this.body = body;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        LeisureRepository repo = LeisureDataRepository.getInstance();
        return repo.deleteAllInterest(userName, token, body).first();
    }
}
