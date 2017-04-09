package com.unir.grupo2.myzeancoach.domain.MLeisure;

import com.unir.grupo2.myzeancoach.data.MLeisure.LeisureDataRepository;
import com.unir.grupo2.myzeancoach.data.MLeisure.LeisureRepository;
import com.unir.grupo2.myzeancoach.domain.UseCase;

import okhttp3.RequestBody;
import rx.Observable;

/**
 * Created by Cesar on 15/03/2017.
 */

public class AddLikeUseCase extends UseCase {

    private String username;
    private String token;
    private RequestBody body;

    public AddLikeUseCase(String username, String token, RequestBody body) {
        this.username = username;
        this.token = token;
        this.body = body;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        LeisureRepository repo = LeisureDataRepository.getInstance();
        return repo.addLike(username, token, body).first();
    }
}
